package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.Genre;
import com.alexsoft.bookstore.repository.common.BaseRepository;

public interface GenreRepository extends BaseRepository<Genre> {
    boolean deleteByTitle(String title);
    boolean save(Genre genre);

}
