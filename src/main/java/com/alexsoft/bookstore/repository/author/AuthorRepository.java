package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.repository.common.BaseRepository;

public interface AuthorRepository extends BaseRepository<Author> {

    boolean deleteByName(String name);
}
