package main.java.br.com.baronheid.model.dao;

import main.java.br.com.baronheid.config.singleton.EntityManagerSingletonFactory;
import main.java.br.com.baronheid.model.dao.interfaces.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        checkEntityManager();
        entityManager.persist(entity);
        commit();
        return (T) entity;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public void update(Object entity) {
        checkEntityManager();
        T mergedObject = (T) entityManager.merge(entity);
        commit();
//        return mergedObject;
    }

    @Override
    public T search(K id) {
        checkEntityManager();
        T objectFound = entityManager.find(clazz, id);
        entityManager.close();
        return objectFound;
    }

    @Override
    public void delete(K id) throws Exception {
        checkEntityManager();
        Object objectToDelete = search(id);
        if (objectToDelete != null) {
            entityManager.remove(objectToDelete);
            commit();
        } else throw new Exception("Unable to find given object");
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<T> listObjects() {
        checkEntityManager();
        List<T> objects = entityManager.createQuery("from " + clazz.getName()).getResultList();
        entityManager.close();
        return objects;
    }

    public void commit() {
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    private void checkEntityManager() {
        if (!entityManager.isOpen()){
            entityManager = EntityManagerSingletonFactory.getInstance().createEntityManager();
        }
    }
}
