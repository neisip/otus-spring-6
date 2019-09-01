package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.domain.GenreDO;
import com.alexsoft.bookstore.repository.author.AuthorDao;
import com.alexsoft.bookstore.repository.book.BookDao;
import com.alexsoft.bookstore.repository.genre.GenreDao;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static org.springframework.shell.standard.ShellOption.NULL;


@ShellComponent
public class BookstoreShellControllerImpl implements BookstoreShellController {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;

    public BookstoreShellControllerImpl(AuthorDao authorDao,
                                        BookDao bookDao,
                                        GenreDao genreDao) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @Override
    @ShellMethod(value = "Show all books", key = {"sb"})
    public void showBooks() {
        bookDao.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod(value = "Show all genres", key = {"sg"})
    public void showGenres() {
        genreDao.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod(value = "Show all authors", key = {"sa"})
    public void showAuthors() {
        authorDao.getAll().forEach(System.out::println);
    }

    @Override
    @ShellMethod(value = "Show book by author name", key = {"bban"})
    public void showBooksByAuthorName(@ShellOption("-n")String name) {
        bookDao.showBooksByAuthorName(name);
    }

    @Override
    @ShellMethod(value = "Show book by genre title", key = {"bbgt"})
    public void showBooksByGenreTitle(@ShellOption("-t") String title) {
        bookDao.showBooksByGenreTitle(title);
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"ab"})
    public void addBook(@ShellOption("-id") Long id,
                        @ShellOption("-t") String title,
                        @ShellOption(value = {"-ai"}, defaultValue = NULL) Long authorId,
                        @ShellOption(value = {"-gi"}, defaultValue = NULL) Long genreId) {
        bookDao.insert(new BookDO(id, title, authorId, genreId));
    }
    @Override
    @ShellMethod(value = "Add genre", key = {"ag"})
    public void addGenre(@ShellOption("-id")  Long id,
                         @ShellOption("-t")  String title) {
        genreDao.insert(new GenreDO(id, title));
    }
    @Override
    @ShellMethod(value = "Add author", key = {"aa"})
    public void addAuthor(@ShellOption("-id")  Long id, @ShellOption("-n")  String name) {
        authorDao.insert(new AuthorDO(id, name));
    }

    @Override
    @ShellMethod(value = "Remove genre by title", key = {"rg"})
    public void removeGenreByTitle(String title) {
        genreDao.deleteByTitle(title);
    }

    @Override
    @ShellMethod(value = "Remove author by title", key = {"ra"})
    public void removeAuthorByName(String name) {
        authorDao.deleteByName(name);
    }

    @Override
    @ShellMethod(value = "Remove book by title", key = {"rb"})
    public void removeBookByTitle(String title) {
        bookDao.deleteByTitle(title);
    }

}
