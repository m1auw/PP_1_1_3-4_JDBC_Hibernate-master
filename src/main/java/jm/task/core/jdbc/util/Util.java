package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbusers";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "lkhgow08owxz5";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DB_DRIVER);
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
                config.setProperty("hibernate.connection.driver_class", DB_DRIVER);
                config.setProperty("hibernate.connection.url", DB_URL);
                config.setProperty("hibernate.connection.username", DB_USER);
                config.setProperty("hibernate.connection.password", DB_PASSWORD);
                config.setProperty("hibernate.hbm2ddl.auto", "update");

                config.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();

                sessionFactory = config.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
