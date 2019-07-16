package com.github.makiftutuncu.scrabbleapi.utilities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public class HibernateUtils {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtils.class.getName());

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            logger.error("Cannot build Hibernate session factory!", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static <T> T withSession(Function<Session, T> action) {
        try (Session session = getSessionFactory().openSession()) {
            return action.apply(session);
        }
    }
}
