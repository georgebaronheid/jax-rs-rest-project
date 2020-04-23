package main.java.br.com.baronheid.model.dao;

import main.java.br.com.baronheid.config.singleton.EntityManagerSingletonFactory;
import main.java.br.com.baronheid.model.dao.interfaces.GenericDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAOImpl<T,K> implements GenericDAO<T,K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<T> clazz;

    public GenericDAOImpl() {
        entityManager = EntityManagerSingletonFactory.getInstance().createEntityManager();
        clazz =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void register(Object entity) {
        entityManager.persist(entity);
    }

    @Override
    public void update(Object entity) {
        entityManager.merge(entity);
    }

    @Override
    public T search(K id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public void delete(K id) throws Exception {
        Object objectToDelete = search(id);
        if (objectToDelete != null) entityManager.remove(objectToDelete);
        else throw new Exception("Unable to find given object");
    }

    @Override
    public List<T> listObjects() {
        return entityManager.createQuery("from" + clazz.getName()).getResultList();
    }
}
