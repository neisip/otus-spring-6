package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    void deleteByTitle(String title);
    Optional<Book> findOneByTitle(String title);
    List<Book> findBooksByGenre(String genre);

}
