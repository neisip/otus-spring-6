package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


public interface GenreRepository extends CrudRepository<Genre, Long> {
    @Transactional
    void deleteByTitle(String title);
}
