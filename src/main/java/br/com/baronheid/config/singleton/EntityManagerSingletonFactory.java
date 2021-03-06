package main.java.br.com.baronheid.config.singleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingletonFactory {

    private static EntityManagerFactory entityManagerFactory;

    // We do not intend to allow this object to be created directly
    private EntityManagerSingletonFactory(){}

    // This static method generates an entity manager to supply persistence to the application
    public static EntityManagerFactory getInstance() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("ORACLE_UNIT");
        }
        return entityManagerFactory;
    }

}
