package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryCustom {
    List<Book> findBooksByAuthor_Name(String name);
    Optional<Book> findOneByTitleAndAuthor_Name(String title, String authorName);
}
