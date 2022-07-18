package lk.ijse.hibernate.util;

import lk.ijse.hibernate.entity.Login;
import lk.ijse.hibernate.entity.Reserve;
import lk.ijse.hibernate.entity.Room;
import lk.ijse.hibernate.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private SessionFactory sessionFactory;

    private FactoryConfiguration(){
        Properties properties  = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("lk/ijse/hibernate/resources/hibernate.properties"));
            Configuration configuration = new Configuration().mergeProperties(properties)
                    .addAnnotatedClass(Login.class)
                    .addAnnotatedClass(Student.class)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Reserve.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (IOException e) {}
    }

    public static FactoryConfiguration getInstance(){
        return factoryConfiguration == null? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
