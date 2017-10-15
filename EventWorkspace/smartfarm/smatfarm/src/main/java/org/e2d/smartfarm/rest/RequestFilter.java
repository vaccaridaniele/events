package org.e2d.smartfarm.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.e2d.smartfarm.pojo.SfUsers;
import org.e2d.smartfarm.util.Constants;
import org.e2d.smartfarm.util.EmailUtil;
import org.e2d.smartfarm.util.HibernateUtil;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Provider
public class RequestFilter implements ContainerRequestFilter {

	Logger logger = Logger.getLogger(RequestFilter.class);
	// private Long requestId;
	private HashMap<String, SfUsers> authenticatedUsers = new HashMap<String, SfUsers>();
	private SessionFactory sessionFactory;

	// http://www.makeinjava.com/logging-filter-using-jersey/
	// http://www.makeinjava.com/what-is-filter-in-enterprise-applications-real-world-analogy/

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		if (requestContext.getUriInfo().getPath().contains(Constants.SENSOR_REQUEST)) {
			// TODO aggiungere controllo batteria transponder
			// (public.fm_transponders) e mandare email di avviso
			if (!isValidSensor(requestContext)) {
				Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).build();
				requestContext.abortWith(unauthorizedStatus);
				return;
			}

			/*
			 * questo per rileggere i dati nella FilterResponse in caso di bad
			 * request (dati troncati) la chiamata non arriva ad elaborare i
			 * dati, ma passa direttamente alla response
			 */
			String json = getEntityBody(requestContext);
			requestContext.getHeaders().add("json", json);

			/*
			 * controllo validità dati. La richiesta non viene abortita perchè
			 * comunque nell'array json ci possono essere dati corretti
			 */
			try {
				// t1b, t2b, t3b 0 = OK 1 = KO
			} catch (Exception e) {
				logger.error(e);
			}
			/*
			 * controllo livello batterie
			 */
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(json);
				JSONArray array = (JSONArray) obj;
				for (int i = 0; i < array.size(); i++) {
					JSONObject objElemnt = (JSONObject) array.get(i);
					String battery1 = objElemnt.get("t1b").toString();
					String battery2 = objElemnt.get("t2b").toString();
					String battery3 = objElemnt.get("t3b").toString();
					if ((battery1 != null && battery1.equals("1")) || (battery2 != null && battery2.equals("1"))
							|| (battery3 != null && battery3.equals("1"))) {
						EmailUtil emailUtil = new EmailUtil();
						String messaggio = "";
						String imei = objElemnt.get("imei").toString();
						try {
							Properties prop = new Properties();
							prop.load(new FileInputStream(System.getProperty("user.home") + "/smartfarm.cfg"));
							String sensorErrorEmail = prop.getProperty("sensorErrorEmail").toString().trim();
							emailUtil.sendEmail(sensorErrorEmail, "transponder low level battery imei: " + imei,
									messaggio);
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
				}

			} catch (ParseException e) {
			}

		}
		// TODO implementare con Json Web Token
		if (requestContext.getUriInfo().getPath().contains(Constants.SECURED_URL_PREFIX)) {
		}
	}

	/**
	 * Controllo se l'utente del trasmettitore esiste
	 * 
	 * @param requestContext
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private boolean isValidSensor(ContainerRequestContext requestContext) throws UnsupportedEncodingException {
		String authToken = null;
		try {
			List<String> authHeader = requestContext.getHeaders().get(Constants.AUTHORIZATION_HEADER);
			authToken = authHeader.get(0);
		} catch (Exception e) {
			return false;
		}
		authToken = authToken.replaceFirst(Constants.AUTHORIZATION_HEADER_PREFIX, "");
		byte[] decoded = Base64.decodeBase64(authToken);
		String decodedString = new String(decoded, "UTF-8");
		StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
		String username = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		SfUsers user = authenticatedUsers.get(username);
		if (user != null && user.getLPsw().equals(password)) {
			return true;
		}
		if (sessionFactory == null) {
			try {
				sessionFactory = HibernateUtil.getSessionFactory();
			} catch (Exception e) {
				logger.fatal("impossibile autenticare l'utente ", e);
			}
		}
		Session session = sessionFactory.openSession();
		String hql = "FROM SfUsers u where u.LName = '" + username + "'";
		Query query = session.createQuery(hql);
		List users = query.list();
		if (users.size() > 0) {
			user = (SfUsers) users.get(0);
			if (user.getLPsw().equals(password)) {
				authenticatedUsers.put(user.getLName(), user);
				return true;
			}
		}
		return false;
	}

	private String getEntityBody(ContainerRequestContext requestContext) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = requestContext.getEntityStream();
		final StringBuilder b = new StringBuilder();
		try {
			ReaderWriter.writeTo(in, out);
			byte[] requestEntity = out.toByteArray();
			if (requestEntity.length == 0) {
				b.append("").append("\n");
			} else {
				b.append(new String(requestEntity)).append("\n");
			}
			requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
		} catch (IOException ex) {
		}
		String test = b.toString();
		return test;
	}
}