package com.josepadron.quinielaapp.repositories;

import java.util.List;

public interface CrudRepositoryI<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T entity);
    void update(T entity);
    void delete(T entity);
    void deleteById(ID id);
}
