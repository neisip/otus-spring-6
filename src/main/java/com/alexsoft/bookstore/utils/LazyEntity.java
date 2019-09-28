package com.alexsoft.bookstore.utils;

import com.alexsoft.bookstore.domain.IdProvider;
import org.springframework.lang.NonNull;

import java.util.function.Function;

public class LazyEntity<Entity extends IdProvider> implements IdProvider {

    private Long id;
    public Long getId() {
        if (id == null) {
            return entity.getId();
        } else {

            return id;
        }
    }

    private Entity entity;
    private Function<Long, Entity> supplier;

    public LazyEntity(long id, Function<Long, Entity> supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public LazyEntity(@NonNull Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        if (entity != null) {
            return entity;
        } else if (supplier != null){
            return supplier.apply(id);
        } else {
            return null;
        }
    }
}
