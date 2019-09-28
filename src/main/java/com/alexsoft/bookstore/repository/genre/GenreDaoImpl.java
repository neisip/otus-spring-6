package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.GenreDO;
import com.alexsoft.bookstore.utils.mappers.GenreSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class GenreDaoImpl implements GenreDao {

    static private Logger logger = LoggerFactory.getLogger(GenreDaoImpl.class);
    private NamedParameterJdbcOperations operations;

    private RowMapper<GenreDO> mapping = (rs, rowNum) -> new GenreDO(
            rs.getLong("id"),
            rs.getString("title"));

    public GenreDaoImpl(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public boolean insert(GenreDO entity) {
        Map<String, Object> m = new GenreSerializer().getMapFor(entity);

        String stmt = contains(entity.getId()) ?
                "UPDATE genres SET title = :title, gmt_create = :gmt_create WHERE id = :id" :
                "INSERT INTO genres (id, title, gmt_create) VALUES (:id, :title, :gmt_create)";
        try {
            return operations.update(stmt, m) != 0;
        } catch (Exception e) {
            logger.error("Got exception when executing update for entity " + entity.getId(), e);
            return false;
        }
    }

    @Override
    public List<GenreDO> getAll() {
        try {
            return operations.query("SELECT * FROM genres", mapping);
        } catch (Exception e) {
            logger.error("Got exception while trying to get all genres", e);
            return Collections.emptyList();
        }
    }

    @Override
    public GenreDO getById(Long id) {
        try {
            return operations.queryForObject("SELECT * FROM genres WHERE id = :id", Collections.singletonMap("id", id), mapping);
        } catch (Exception e) {
            logger.error("getById for id: " + id + "failed", e);
            return null;
        }
    }

    @Override
    public Boolean contains(long id) {
        try {
            return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM genres WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
        } catch (Exception e) {
            logger.error("Failed to check if genres contains " + id, e);
            return false;
        }
    }

    @Override
    public boolean deleteByTitle(String title) {
        try {
            return operations.update("DELETE FROM genres WHERE title = :title ", Collections.singletonMap("title", title)) != 0;
        } catch (Exception e) {
            logger.error("Failed to delete genre by title " + title, e);
            return false;
        }
    }
}
