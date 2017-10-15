package org.e2d.smartfarm.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class DbUtil {

	public static DataSource ds = null;

	static Logger logger = Logger.getLogger(DbUtil.class);

	protected Statement statement = null;
	protected ResultSet resultSet = null;
	protected PreparedStatement preparedStatement = null;
	protected Connection connection = null;
	protected CallableStatement callableStatement = null;

	protected void getConnection() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/smartfarm");
			connection = ds.getConnection();
		} catch (Exception ex) {
			logger.fatal("Initial SessionFactory Connection failed." + ex);
		}
	}

	protected void closeConnection() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (Exception e) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (Exception e) {
			}
		}
		if (callableStatement != null) {
			try {
				callableStatement.close();
			} catch (Exception e) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
			}
		}
	}

	public Connection getJdbcConnection()
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		logger.debug("CREAZIONE CONNESSIONE JDBC");
		Connection connection;
		Properties prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("user.home") + "/smartfarm.cfg"));
		String host = prop.getProperty("host").toString().trim();
		String username = prop.getProperty("username").toString().trim();
		String password = prop.getProperty("password").toString().trim();
		String driver = prop.getProperty("driver").toString().trim();
		Class.forName(driver);
		connection = DriverManager.getConnection(host, username, password);
		logger.debug("CONNESSIONE JDBC CREATA");
		return connection;
	}

	public static Connection getDataSourceConnection() throws Exception {
		if (ds == null) {
			logger.debug("CREAZIONE DATASOURCE");
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/events");
			logger.debug("DATASOURCE CREATO");
		}
		return ds.getConnection();
	}

	public static Connection getDbUniSourceConnection() throws Exception {
		if (ds == null) {
			logger.debug("CREAZIONE DATASOURCE DBUNI");
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/smartfarm");
			logger.debug("DATASOURCE DBUNI CREATO");
		}
		return ds.getConnection();
	}
}