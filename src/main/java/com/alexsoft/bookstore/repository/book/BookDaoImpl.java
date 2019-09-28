package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.domain.GenreDO;
import com.alexsoft.bookstore.repository.author.AuthorDao;
import com.alexsoft.bookstore.repository.genre.GenreDao;
import com.alexsoft.bookstore.utils.LazyEntity;
import com.alexsoft.bookstore.utils.mappers.BookSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {
    static private Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);
    private NamedParameterJdbcOperations operations;
    private GenreDao genreDao;
    private AuthorDao authorDao;

    private RowMapper<BookDO> mapping = (rs, rowNum) -> new BookDO(
            rs.getLong("id"),
            rs.getString("title"),
            new LazyEntity<AuthorDO>(rs.getLong("author_id"), authorDao::getById),
            new LazyEntity<GenreDO>(rs.getLong("genre_id"), genreDao::getById));

    public BookDaoImpl(NamedParameterJdbcOperations operations,
                       GenreDao genreDao,
                       AuthorDao authorDao) {
        this.operations = operations;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    @Override
    public boolean deleteByTitle(String title) {
        try {
            return operations.update("DELETE FROM books WHERE title = :title ", Collections.singletonMap("title", title)) != 0;
        } catch (Exception e) {
            logger.error("Failed to delete book by title " + title, e);
            return false;
        }
    }

    @Override
    public List<BookDO> getBooksByAuthorName(String name) {
        try {
            return operations.query("SELECT * FROM books JOIN authors ON books.author_id = authors.id WHERE authors.name = :name", Collections.singletonMap("name", name), mapping);
        } catch (Exception e) {
            logger.error("Failed to get book by name " + name, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<BookDO> getBooksByGenreTitle(String title) {
        try {
            return operations.query("SELECT * FROM books JOIN genres ON books.genre_id = genres.id WHERE genres.title = :title", Collections.singletonMap("title", title), mapping);
        } catch (Exception e) {
            logger.error("Failed to get book by genre title  " + title, e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean insert(BookDO entity) {

        Map<String, Object> m = new BookSerializer().getMapFor(entity);

        String stmt = contains(entity.getId()) ?
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id, gmt_create = :gmt_create WHERE id = :id" :
                "INSERT INTO books (id, title, author_id, genre_id, gmt_create) VALUES (:id, :title, :author_id, :genre_id, :gmt_create)";
        try {
            return operations.update(stmt, m) != 0;
        } catch (Exception e) {
            logger.error("Failed to insert entity " + entity.getId(), e);
            return false;
        }
    }

    @Override
    public List<BookDO> getAll() {
        try {
            return operations.query("SELECT * FROM books", mapping);
        } catch (Exception e) {
            logger.error("Failed to get all books", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Boolean contains(long id) {
        try {
            return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM books WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
        } catch (Exception e) {
            logger.error("Failed to check if id contains " + id, e);
            return false;
        }
    }

}