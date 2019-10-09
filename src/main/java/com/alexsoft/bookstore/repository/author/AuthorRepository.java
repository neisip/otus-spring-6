package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Transactional
    void deleteByName(String name);
}
