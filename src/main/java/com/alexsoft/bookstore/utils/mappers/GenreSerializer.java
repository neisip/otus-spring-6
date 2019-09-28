package com.alexsoft.bookstore.utils.mappers;

import com.alexsoft.bookstore.domain.GenreDO;

import java.util.*;

public class GenreSerializer implements Serializer<GenreDO> {
    @Override
    public Map<String, Object> getMapFor(GenreDO entity) {
        if (entity == null) return Collections.emptyMap();

        Map<String, Object> m = new HashMap<>();
        m.put("id", entity.getId());
        m.put("gmt_create", new Date());
        m.put("title", entity.getTitle());
        return m;
    }
}
