package com.alexsoft.bookstore.utils.mappers;

import com.alexsoft.bookstore.domain.AuthorDO;

import java.util.*;

public class AuthorMapper implements Mapper<AuthorDO> {

    @Override
    public Map<String, Object> getMapFor(AuthorDO entity) {
        if (entity == null) return Collections.emptyMap();

        Map<String, Object> m = new HashMap<>();
        m.put("name", entity.getName());
        m.put("gmt_create", new Date());
        m.put("id", entity.getId());
        return m;
    }
}