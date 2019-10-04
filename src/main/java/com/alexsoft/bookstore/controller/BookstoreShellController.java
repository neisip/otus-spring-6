package com.alexsoft.bookstore.controller;

import org.springframework.shell.standard.ShellOption;

public interface BookstoreShellController {
    void showBooks();

    void showGenres();

    void showAuthors();

    void showAll();

    void showBooksByAuthorName(String name);

    void showBooksByGenreTitle(String title);


    void addBook(
            String title,
            String authorName,
            String genreTitle
    );

    void addCommentToBookWithTitleAndAuthor(String commentText, String title, String author);

    void addGenre(String title);

    void addAuthor(String name);

    void removeGenreByTitle(String title);

    void removeAuthorByName(String name);

    void removeBookByTitle(String title);

}
