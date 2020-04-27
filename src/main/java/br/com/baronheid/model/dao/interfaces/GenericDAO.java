package main.java.br.com.baronheid.model.dao.interfaces;

import java.util.List;

public interface GenericDAO<T,K> {

    T register(T entity);

    void update(T entity);

    T search(K id);

    void delete(K id) throws Exception;

    List<T> listObjects();
}
