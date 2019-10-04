package com.alexsoft.bookstore.repository.common;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<E> {

    boolean save(E entity);

    List<E> getAll();

    Optional<E> findById(long id);
}
