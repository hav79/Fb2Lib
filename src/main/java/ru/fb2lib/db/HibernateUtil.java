package ru.fb2lib.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by hav on 25.01.16.
 */
public class HibernateUtil {
//    private static SessionFactory sessionFactory = null;
    private static EntityManagerFactory managerFactory;

//    public static SessionFactory getSessionFactory() {
//        if (sessionFactory == null)
//            createSessionFactory();
//        return sessionFactory;
//    }

    public static void setTestEntityManagerFactory() {
        managerFactory = Persistence.createEntityManagerFactory("Fb2LibTest");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (managerFactory == null)
            managerFactory = Persistence.createEntityManagerFactory("Fb2Lib");
        return managerFactory; // .createEntityManager();
    }

    public static EntityManager createEntityManager() {
        return managerFactory.createEntityManager();
    }

//    private static void createSessionFactory() {
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure()
//                .build();
//        try {
//            sessionFactory = new MetadataSources(registry)
//                    .buildMetadata()
//                    .buildSessionFactory();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getCause().getMessage());
//            StandardServiceRegistryBuilder.destroy(registry);
//        }
//    }
}
