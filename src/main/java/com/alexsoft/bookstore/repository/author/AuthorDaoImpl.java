package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.utils.mappers.AuthorSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    static private Logger logger = LoggerFactory.getLogger(AuthorDaoImpl.class);

    private final NamedParameterJdbcOperations operations;
    private RowMapper<AuthorDO> mapping = (rs, rowNum) -> new AuthorDO(
            rs.getLong("id"),
            rs.getString("name"));

    public AuthorDaoImpl(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public boolean insert(AuthorDO entity) {

        Map<String, Object> m = new AuthorSerializer().getMapFor(entity);
        String stmt = contains(entity.getId()) ?
                "UPDATE authors SET name = :name, gmt_create = :gmt_create WHERE id = :id" :
                "INSERT INTO authors (id, name, gmt_create) VALUES (:id, :name, :gmt_create)";
        try {
            return operations.update(stmt, m) != 0;
        } catch (Exception e) {
            logger.error("insert for entity: " + entity.getId() + "failed ", e);
            return false;
        }
    }

    @Override
    public List<AuthorDO> getAll() {
        try {
            return operations.query("SELECT * FROM authors", mapping);
        } catch (Exception e) {
            logger.error("getAll failed ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean contains(long id) {
        try {
            return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM authors WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
        } catch (Exception e) {
            logger.error("contains for id " + id + " failed", e);
            return false;
        }
    }

    @Override
    public boolean deleteByName(String name) {
        try {
            return operations.update("DELETE FROM authors WHERE name = :name", Collections.singletonMap("name", name)) != 0;
        } catch (Exception e) {
            logger.error("deleteByName for name " + name + " failed", e);
            return false;
        }
    }

    @Override
    public AuthorDO getById(Long id) {
        try {
            return operations.queryForObject("SELECT * FROM authors WHERE id = :id", Collections.singletonMap("id", id), mapping);
        } catch (Exception e) {
            logger.error("getById for id: " + id + "failed", e);
            return null;
        }
    }
}
