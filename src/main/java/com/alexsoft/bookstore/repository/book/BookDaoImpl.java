package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.domain.BookDO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookDaoImpl implements BookDao {
    private NamedParameterJdbcOperations operations;

    private RowMapper<BookDO> mapping = (rs, rowNum) -> new BookDO(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getLong("author_id"),
            rs.getLong("genre_id"));

    public BookDaoImpl(NamedParameterJdbcOperations operations) {
        this.operations = operations;
    }

    @Override
    public void deleteByTitle(String title) {
        operations.update("DELETE FROM books WHERE title = :title ", Collections.singletonMap("title", title));
    }

    @Override
    public void showBooksByAuthorName(String name) {
        List<BookDO> books = operations.query("SELECT * FROM books JOIN authors ON books.author_id = authors.id WHERE authors.name = :name",Collections.singletonMap("name", name), mapping);
        if(books != null && !books.isEmpty()) {
            books.forEach(System.out::println);
        }
    }

    @Override
    public void showBooksByGenreTitle(String title) {
        List<BookDO> books = operations.query("SELECT * FROM books JOIN genres ON books.genre_id = genres.id WHERE genres.title = :title",Collections.singletonMap("title", title), mapping);
        if(books != null && !books.isEmpty()) {
            books.forEach(System.out::println);
        }
    }

    @Override
    public void insert(BookDO entity) {

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", entity.getId());
        m.put("gmt_create", new Date());
        m.put("title", entity.getTitle());
        m.put("author_id", entity.getAuthorId());
        m.put("genre_id", entity.getGenreId());

        String stmt = contains(entity.getId())?
                "UPDATE books SET title = :title, author_id = :author_id, genre_id = :genre_id, gmt_create = :gmt_create WHERE id = :id":
                "INSERT INTO books (id, title, author_id, genre_id, gmt_create) VALUES (:id, :title, :author_id, :genre_id, :gmt_create)";
        operations.update(stmt, m);
    }
//
//    @Override
//    public void delete(long id) {
//        operations.update("DELETE FROM books WHERE id = :id ", Collections.singletonMap("id", id));
//    }

    @Override
    public List<BookDO> getAll() {
        return operations.query("SELECT * FROM books", mapping);
    }


    @Override
    public Boolean contains(long id) {
        return operations.queryForObject("SELECT EXISTS(SELECT 1 FROM books WHERE id = :id)", Collections.singletonMap("id", id), Boolean.class);
    }


//    @Override
//    public BookDO get(long id) {
//        return operations.queryForObject("SELECT * FROM books WHERE id = :id ",
//                Collections.singletonMap("id", id),
//                mapping);
//    }
}