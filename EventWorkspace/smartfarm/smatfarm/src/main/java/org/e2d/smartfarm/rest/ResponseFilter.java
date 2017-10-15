package org.e2d.smartfarm.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;
import org.e2d.smartfarm.pojo.SfErrorsLog;
import org.e2d.smartfarm.util.Constants;
import org.e2d.smartfarm.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

	Logger logger = Logger.getLogger(ResponseFilter.class);
	private String sensorErrorEmail;

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		if (requestContext.getUriInfo().getPath().contains(Constants.SENSOR_REQUEST)
				&& responseContext.getStatus() == 400) // bad request
		{
			if (sensorErrorEmail == null) {
				Properties prop = new Properties();
				prop.load(new FileInputStream(System.getProperty("user.home") + "/smartfarm.cfg"));
				sensorErrorEmail = prop.getProperty("sensorErrorEmail").toString().trim();
			}
			String json = requestContext.getHeaderString("json");
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			SfErrorsLog errorLog = new SfErrorsLog();
			errorLog.setDate(new Date());
			errorLog.setHttpStatus((short) responseContext.getStatus());
			errorLog.setDescription(responseContext.getStatusInfo().getReasonPhrase());
			errorLog.setJson(json);
			errorLog.setEmail(sensorErrorEmail);
			errorLog.setNotified(0);
			session.beginTransaction();
			session.save(errorLog);
			session.getTransaction().commit();
		}
	}
}