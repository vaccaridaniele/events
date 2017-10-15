package org.e2d.smartfarm.util;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	static Logger logger = Logger.getLogger(HibernateUtil.class);

	public static SessionFactory sessionFactory;

	private HibernateUtil() {
	}

	public static synchronized SessionFactory getSessionFactory() throws FileNotFoundException, IOException {
		if (sessionFactory == null) {
			logger.debug("CREAZIONE DI HIBERNATE SESSION FACTORY");
			Configuration configuration = new Configuration();
			configuration.configure();
			StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			logger.debug("HIBERNATE SESSION FACTORY CREATA");
		}
		return sessionFactory;
	}
}