package org.e2d.smartfarm.rest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.e2d.smartfarm.pojo.SfErrorsLog;
import org.e2d.smartfarm.util.EmailUtil;
import org.e2d.smartfarm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Path("/emailutils")
@Produces("application/json")
@Consumes("application/json")
public class EmailUtilEndpoint {

	/**
	 * http://localhost:8080/smartfarm/rest/emailutils/sendMails
	 * 
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@GET
	@Path("/sendMails")
	public Response sendMails() throws AddressException, MessagingException, FileNotFoundException, IOException {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String queryString = "select e from SfErrorsLog e where e.notified= 0";
		List<SfErrorsLog> errorsLogList = session.createQuery(queryString).list();
		if (errorsLogList.size() == 0) {
			return Response.ok().build();
		}
		String messaggio = "";
		Properties prop = new Properties();
		prop.load(new FileInputStream(System.getProperty("user.home") + "/smartfarm.cfg"));
		String sensorErrorEmail = prop.getProperty("sensorErrorEmail").toString().trim();
		{
			session.beginTransaction();
			for (SfErrorsLog errorLog : errorsLogList) {
				messaggio = messaggio + "<b>id:" + errorLog.getId() + " " + errorLog.getDescription()
						+ "</b><br>httpstatus: " + errorLog.getHttpStatus() + "<br>json: " + errorLog.getJson()
						+ "<br><br>";
				errorLog.setNotified(1);
				session.update(errorLog);
			}
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendEmail(sensorErrorEmail, "Si sono verificati errori nei datalogger", messaggio);
			session.getTransaction().commit();
		}
		return Response.ok().build();
	}
}