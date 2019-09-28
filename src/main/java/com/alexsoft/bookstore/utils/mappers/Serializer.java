package com.alexsoft.bookstore.utils.mappers;

import java.util.Map;

public interface Serializer<T> {
    Map<String, Object> getMapFor(T entity);
}
