package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.domain.GenreDO;

public interface BookstoreShellController {
    void showBooks();
    void showGenres();
    void showAuthors();
    void showBooksByAuthorName(String name);
    void showBooksByGenreTitle(String title);


    void addBook(Long id, String title, Long authorId, Long genreId);
    void addGenre(Long id, String title);
    void addAuthor(Long id, String name);
    void removeGenreByTitle(String title);
    void removeAuthorByName(String name);
    void removeBookByTitle(String title);
}
