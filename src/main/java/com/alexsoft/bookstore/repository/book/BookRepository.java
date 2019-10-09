package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional
    void deleteByTitle(String title);

    @Override
    @EntityGraph(value = "BookEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    @Transactional(readOnly = true)
    List<Book> findAll();

    @Transactional(readOnly = true)
    @EntityGraph(value = "BookEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findBooksByAuthorName(String name);

    @Transactional(readOnly = true)
    @EntityGraph(value = "BookEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Book> findBookByAuthorNameAndTitle(String authorName, String title);

    @Transactional(readOnly = true)
    @EntityGraph(value = "BookEntityGraph", type = EntityGraph.EntityGraphType.LOAD)
    List<Book> findBooksByGenreTitle(String genreTitle);
}
