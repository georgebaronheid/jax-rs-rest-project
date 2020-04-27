package main.java.br.com.baronheid.model.dao;

import main.java.br.com.baronheid.config.singleton.EntityManagerSingletonFactory;
import main.java.br.com.baronheid.model.dao.interfaces.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAOImpl<T, K> implements GenericDAO<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> clazz;

    @SuppressWarnings(value = "unchecked")
    public GenericDAOImpl() {
        entityManager = EntityManagerSingletonFactory.getInstance().createEntityManager();
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public T register(Object entity) {
        entityManager.persist(entity);
        commit();
        return (T) entity;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public void update(Object entity) {
        T mergedObject = (T) entityManager.merge(entity);
        commit();
//        return mergedObject;
    }

    @Override
    public T search(K id) {
        T objectFound = entityManager.find(clazz, id);
        entityManager.close();
        return objectFound;
    }

    @Override
    public void delete(K id) throws Exception {
        Object objectToDelete = search(id);
        if (objectToDelete != null) {
            entityManager.remove(objectToDelete);
            commit();
        } else throw new Exception("Unable to find given object");
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<T> listObjects() {
        List<T> objects = entityManager.createQuery("from " + clazz.getName()).getResultList();
        entityManager.close();
        return objects;
    }

    public void commit() {
        try {
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
