package com.alexsoft.bookstore.controller;

public interface BookstoreShellController {
    void showBooks();

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


    void addAuthor(String name);


    void removeAuthorByName(String name);

    void removeBookByTitle(String title);

}
