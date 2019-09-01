package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.AuthorDO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations operations;
    private RowMapper<AuthorDO> mapping = (rs, rowNum) -> new AuthorDO(
            rs.getLong("id"),
            rs.getString("name"));

    public AuthorDaoImpl(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public void insert(AuthorDO entity) {

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("name", entity.getName());
        m.put("gmt_create", new Date());
        m.put("id", entity.getId());
        String stmt = contains(entity.getId())?
                "UPDATE authors SET name = :name, gmt_create = :gmt_create WHERE id = :id" :
                "INSERT INTO authors (id, name, gmt_create) VALUES (:id, :name, :gmt_create)";
        operations.update(stmt, m);
    }
//
//    @Override
//    public void delete(long id) {
//        operations.update("DELETE FROM authors WHERE id = :id ", Collections.singletonMap("id", id));
//    }

    @Override
    public List<AuthorDO> getAll() {
        return operations.query("SELECT * FROM authors", mapping);
    }

    @Override
    public Boolean contains(long id) {
        return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM authors WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
    }
//    @Override
//    public AuthorDO get(long id) {
//        return operations.queryForObject("SELECT * FROM authors WHERE id = :id ",
//                Collections.singletonMap("id", id),
//                mapping);
//    }

    @Override
    public void deleteByName(String name) {
        operations.update("DELETE FROM authors WHERE name = :name ", Collections.singletonMap("name", name));
    }
}
