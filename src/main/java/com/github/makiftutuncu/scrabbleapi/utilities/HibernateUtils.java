package com.github.makiftutuncu.scrabbleapi.utilities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        } catch (Exception e) {
            logger.error("Database operation failed!", e);
            throw e;
        }
    }

    public static <T> T withTransactionSession(Function<Session, T> action) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T result = action.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            logger.error("Database transaction failed!", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
