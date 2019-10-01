package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.common.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends BaseRepository<Book> {
    boolean deleteByTitle(String title);
    List<Book> getBooksByAuthorName(String name);
    Optional<Book> findBookByAuthorNameAndTitle(String authorName, String title);
    List<Book> getBooksByGenreTitle(String title);

}
