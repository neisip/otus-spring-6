package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.domain.GenreDO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GenreDaoImpl implements GenreDao{
    private NamedParameterJdbcOperations operations;

    private RowMapper<GenreDO> mapping = (rs, rowNum) -> new GenreDO(
            rs.getLong("id"),
            rs.getString("title"));

    public GenreDaoImpl(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public void insert(GenreDO entity) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", entity.getId());
        m.put("gmt_create", new Date());
        m.put("title", entity.getTitle());

        String stmt = contains(entity.getId()) ?
                "UPDATE genres SET title = :title, gmt_create = :gmt_create WHERE id = :id":
                "INSERT INTO genres (id, title, gmt_create) VALUES (:id, :title, :gmt_create)";
        operations.update(stmt, m);
    }
//
//    @Override
//    public void delete(long id) {
//        operations.update("DELETE FROM genres WHERE id = :id ", Collections.singletonMap("id", id));
//    }

    @Override
    public List<GenreDO> getAll() {
        return operations.query("SELECT * FROM genres", mapping);
    }

    @Override
    public Boolean contains(long id) {
        return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM genres WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
    }

//    @Override
//    public GenreDO get(long id) {
//        return operations.queryForObject("SELECT * FROM genres WHERE id = :id ",
//                Collections.singletonMap("id", id),
//                mapping);
//
//    }


    @Override
    public void deleteByTitle(String title) {
        operations.update("DELETE FROM genres WHERE title = :title ", Collections.singletonMap("title", title));
    }
}
