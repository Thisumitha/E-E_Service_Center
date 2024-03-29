package dao.util;

import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory=createSessionFactory();

    private static SessionFactory createSessionFactory() {
        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();


        Metadata metadata = new MetadataSources(standardRegistry)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Type.class)
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(OrderDetail.class)
                .addAnnotatedClass(Orders.class)
                .addAnnotatedClass(RepairItem.class)
                .addAnnotatedClass(RepairaPartsDetails.class)
                .addAnnotatedClass(Employers.class)
                .addAnnotatedClass(Access.class)

                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();
    }
    public static Session getSession(){
        return sessionFactory.openSession();
    }
    public static Session getSession(ServiceRegistry serviceRegistry) {
        return new Configuration().configure().buildSessionFactory(serviceRegistry).openSession();
    }

}
