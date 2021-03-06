package dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by mathenge on 11/1/2017.
 */
public abstract class HibernateWrapper {

    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/library"
            + "?autoReconnect=true&createDatabaseIfNotExist=true";

    public static SessionFactory sessionFactory = configureSessionFactory();

    static SessionFactory configureSessionFactory() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            configuration.setProperty("hibernate.connection.url", CONNECTION_URL);
            StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(ssrb.build());
        } catch (Exception ex) {
            Logger.getLogger(HibernateWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
