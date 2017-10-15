package org.event.rest;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.e2d.smartfarm.mypojo.Mymessage;
import org.e2d.smartfarm.util.DbUtil;
import org.e2d.smartfarm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Path("/test")
@Produces("application/json")
@Consumes("application/json")
public class Test {
	static Logger logger = Logger.getLogger(Test.class);

	/**
	 * http://localhost:8080/smartwald/rest/test/jdbc
	 * 
	 * @return
	 */
	@GET
	@Path("/jdbc")
	public Response jdbcConnection() {
		logger.info("test jelastic jdbc connection");
		Mymessage mymessage = new Mymessage();
		addMessage(mymessage, "test jelastic jdbc connection");
		DbUtil cu = new DbUtil();
		Connection connection = null;
		try {
			connection = cu.getJdbcConnection();
		} catch (Exception e) {
			logger.fatal(e);
			addMessage(mymessage, "ERRORE " + e.getMessage());
			return Response.ok(mymessage).build();
		}
		if (connection != null) {
			// return Response.status(Status.NOT_FOUND).build();
			addMessage(mymessage, "connessione jdbc ok");
			return Response.ok(mymessage).build();
		} else {
			addMessage(mymessage, "connessione KO");
			return Response.ok(mymessage).build();
		}
	}

	/**
	 * http://localhost:8080/smartfarm/rest/test/hibernate
	 * 
	 * @return
	 */
	@GET
	@Path("/hibernate")
	public Response hibernateConnection() {
		logger.info("test jelastic hibernate connection");
		Mymessage mymessage = new Mymessage();
		addMessage(mymessage, "test jelastic hibernate connection");
		try {
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			session.close();
			if (session != null) {
				addMessage(mymessage, "connessione hibernate ok");
				return Response.ok(mymessage).build();
			} else {
				addMessage(mymessage, "sessione impostata a null");
				return Response.ok(mymessage).build();
			}
		} catch (Exception e) {
			logger.fatal(e);
			addMessage(mymessage, "ERRORE " + e.getMessage());
			return Response.ok(mymessage).build();
		}
	}

	/**
	 * http://localhost:8080/events/rest/test/datasource
	 * 
	 * @return
	 */
	@GET
	@Path("/datasource")
	public Response datasourceConnection() {
		logger.info("test jelastic datasource connection");
		Mymessage mymessage = new Mymessage();
		addMessage(mymessage, "test datasource connection");
		DbUtil cu = new DbUtil();
		Connection connection = null;
		try {
			connection = cu.getDataSourceConnection();
		} catch (Exception e) {
			logger.fatal(e);
			addMessage(mymessage, "ERRORE " + e.getMessage());
			return Response.ok(mymessage).build();
		}
		if (connection != null) {
			// return Response.status(Status.NOT_FOUND).build();
			addMessage(mymessage, "connessione datasource ok");
			return Response.ok(mymessage).build();
		} else {
			addMessage(mymessage, "connessione KO");
			return Response.ok(mymessage).build();
		}
	}

	/**
	 * http://localhost:8080/smartwald/rest/test/setProperties
	 * 
	 * @return
	 */
	// @GET
	// @Path("/setProperties")
	// public Response setProperties() {
	// Properties prop = new Properties();
	// OutputStream output = null;
	// Mymessage mymessage = new Mymessage();
	// try {
	// String path = System.getProperty("user.home") + "/mydb.cfg";
	// output = new FileOutputStream(path);
	// prop.setProperty("username", "smartwald");
	// prop.setProperty("password", "Maggio_2016");
	// prop.setProperty("driver", "org.postgresql.Driver");
	// prop.setProperty("host", "localhost:5432/pippo");
	// prop.store(output, null);
	// mymessage.setMessage("file properties " + path + " creato");
	// return Response.ok(mymessage).build();
	// } catch (IOException io) {
	// io.printStackTrace();
	// mymessage.setMessage("file properties KO " + io.getMessage());
	// return Response.ok(mymessage).build();
	// } finally {
	// if (output != null) {
	// try {
	// output.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	/**
	 * http://localhost:8080/smartwald/rest/test/props
	 * 
	 * @return
	 */
	@GET
	@Path("/props")
	public Response props() {
		logger.info("test properties values");
		Mymessage mymessage = new Mymessage();
		addMessage(mymessage, "servizio rest/test/props chiamato");
		String path = System.getProperty("user.home") + "/mydb.cfg";
		addMessage(mymessage, "cerco file " + path);
		try {
			Properties prop = new Properties();

			prop.load(new FileInputStream(path));

			String host = prop.getProperty("host").toString().trim();
			String url = "jdbc:postgresql://" + host;
			addMessage(mymessage, "host: " + url);

			String username = prop.getProperty("username").toString().trim();
			addMessage(mymessage, " username: " + username);

			String password = prop.getProperty("password").toString().trim();
			addMessage(mymessage, " password: " + password);

			String driver = prop.getProperty("driver").toString().trim();
			addMessage(mymessage, " driver: " + driver);

			String lookup = prop.getProperty("lookup").toString().trim();
			addMessage(mymessage, " lookup: " + lookup);

			return Response.ok(mymessage).build();
		} catch (Exception e) {
			logger.fatal(e);
			addMessage(mymessage, "ERRORE " + e.getMessage() + " file properties: " + path);

			return Response.ok(mymessage).build();
		}
	}

	private void addMessage(Mymessage mymessage, String message) {
		if (mymessage.getMessage() == null) {
			mymessage.setMessage("");
		}
		String newMessage = mymessage.getMessage() + message + " *** ";
		mymessage.setMessage(newMessage);

	}

	/**
	 * http://localhost:8080/smartfarm/rest/test/restful
	 * 
	 * @return
	 */
	@GET
	@Path("/restful")
	public Response hello() {
		logger.info("test restful response");
		Mymessage mymessage = new Mymessage();
		mymessage.setCode(0);
		mymessage.setMessage("test rest smartfarm response OK");
		return Response.ok(mymessage).build();
	}

	/**
	 * http://localhost:8080/smartwald/rest/test/selectjdbc
	 * 
	 * @return
	 */
	@GET
	@Path("/selectjdbc")
	public Response selectjdbc() {
		Mymessage mymessage = new Mymessage();
		addMessage(mymessage, "test selectjdbc");
		DbUtil cu = new DbUtil();
		Connection connection = null;
		try {
			connection = cu.getJdbcConnection();

		} catch (Exception e) {
			logger.fatal(e);
			addMessage(mymessage, "ERRORE " + e.getMessage());
			return Response.ok(mymessage).build();
		}
		if (connection != null) {
			// return Response.status(Status.NOT_FOUND).build();
			addMessage(mymessage, "connessione jdbc ok");
			return Response.ok(mymessage).build();
		} else {
			addMessage(mymessage, "connessione KO");
			return Response.ok(mymessage).build();
		}
	}
}