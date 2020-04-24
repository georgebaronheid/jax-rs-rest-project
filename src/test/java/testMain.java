package test.java;

import main.java.br.com.baronheid.model.entity.User;
import main.java.br.com.baronheid.config.singleton.EntityManagerSingletonFactory;

import javax.persistence.EntityManager;

public class testMain {
    public static void main(String[] args) {
        EntityManager entityManager  = null;

        User user = new User();
        user.setEmail("any_email1231@provider.net");
        user.setName("Average Joe221");

        try {
            entityManager = EntityManagerSingletonFactory.getInstance().createEntityManager();
//            Get transaction is required when making actions that alters the table
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
//            This process is required to ensure atomicity
            if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
            e.printStackTrace();
            System.out.println("Register did not succeed");
        } finally {
            entityManager.close();
        }
    }
}
