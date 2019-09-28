package com.alexsoft.bookstore.utils.mappers;

import com.alexsoft.bookstore.domain.BookDO;

import java.util.*;

public class BookMapper implements Mapper<BookDO> {

    @Override
    public Map<String, Object> getMapFor(BookDO entity) {
        if (entity == null) return Collections.emptyMap();

        Map<String, Object> m = new HashMap<>();
        m.put("id", entity.getId());
        m.put("gmt_create", new Date());
        m.put("title", entity.getTitle());
        m.put("author_id", entity.getAuthorDO().getId());
        m.put("genre_id", entity.getGenreDO().getId());
        return m;
    }
}
