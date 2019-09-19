package com.alexsoft.bookstore.repository.common;

import java.util.List;

public interface BaseDao<E> {

    boolean insert(E entity);
    List<E> getAll();
    Boolean contains(long id);
}
