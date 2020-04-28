package main.java.br.com.baronheid.model.dao;

import main.java.br.com.baronheid.config.singleton.EntityManagerSingletonFactory;
import main.java.br.com.baronheid.model.dao.interfaces.GenericDAO;
import main.java.br.com.baronheid.model.exceptions.DatabaseException;
import main.java.br.com.baronheid.model.exceptions.EntityNotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAOImpl<T, K> implements GenericDAO<T, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> clazz;

    @SuppressWarnings(value = "unchecked")
    public GenericDAOImpl() {
        entityManager = EntityManagerSingletonFactory.getInstance().createEntityManager();
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public T register(final Object entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
        } catch (DatabaseException databaseException) {
            throw new WebApplicationException(Response.Status.CONFLICT);
        } catch (Exception exception) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        commit();
        return (T) entity;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public T update(final Object entity) {
        T mergedObject;
        try {
            entityManager.getTransaction().begin();
            mergedObject = (T) entityManager.merge(entity);
            commit();
        } catch (WebApplicationException webApplicationException) {
            throw new DatabaseException("Unable to update");
        }
        return mergedObject;
    }

    @Override
    public T search(final K id) {
        T objectFound = null;
        try {
            objectFound = entityManager.find(clazz, id);
        } catch (EntityNotFoundException e) {
            e.badRequest();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        entityManager.close();
        return objectFound;
    }

    @Override
    public void delete(final K id) {
        Object objectToDelete = search(id);
        if (objectToDelete != null) {
            try {
                entityManager.getTransaction().begin();
                entityManager.remove(objectToDelete);
            } catch (EntityNotFoundException e) {
                e.badRequest();
            } catch (Exception e) {
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
            commit();
        } else throw new EntityNotFoundException("Unable to find given object");
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<T> listObjects() {
        List<T> objects = null;
        try {
            objects = entityManager.createQuery("from " + clazz.getName()).getResultList();
        } catch (EntityNotFoundException entityNotFoundException) {
            entityNotFoundException.badRequest();
        } catch (InternalServerErrorException e) {
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        entityManager.close();
        return objects;
    }

    public void commit() {
        try {
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception e) {
            if (entityManager != null && entityManager.getTransaction().isActive()) entityManager
                    .getTransaction().rollback();
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        } finally {
            if (entityManager != null && entityManager.isOpen()) entityManager.close();
        }
    }
}
