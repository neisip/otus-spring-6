package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends MongoRepository<Author, String> {
    void deleteByName(String name);
    Optional<Author> findOneByName(String name);
    List<Author> findAllByName(String name);
}
