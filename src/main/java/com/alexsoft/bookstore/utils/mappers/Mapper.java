package com.alexsoft.bookstore.utils.mappers;

import java.util.Map;

public interface Mapper<T> {
    Map<String, Object> getMapFor(T entity);
}
