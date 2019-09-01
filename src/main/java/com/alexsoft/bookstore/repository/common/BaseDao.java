package com.alexsoft.bookstore.repository.common;

import java.util.List;

public interface BaseDao<E> {

    void insert(E entity);
   // void delete(long id);
    List<E> getAll();
    Boolean contains(long id);
}
