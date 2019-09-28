package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.domain.GenreDO;
import com.alexsoft.bookstore.repository.author.AuthorDao;
import com.alexsoft.bookstore.repository.book.BookDao;
import com.alexsoft.bookstore.repository.genre.GenreDao;
import com.alexsoft.bookstore.utils.LazyEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.PrintStream;

import static org.springframework.shell.standard.ShellOption.NULL;


@ShellComponent
public class BookstoreShellControllerImpl implements BookstoreShellController {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final PrintStream consoleOutput;

    public BookstoreShellControllerImpl(AuthorDao authorDao,
                                        BookDao bookDao,
                                        GenreDao genreDao,
                                        @Qualifier("Bookstore_CO")
                                                PrintStream consoleOutput) {
        this.authorDao = authorDao;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.consoleOutput = consoleOutput;
    }

    @Override
    @ShellMethod(value = "Show all books", key = {"sb"})
    public void showBooks() {
        bookDao.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all genres", key = {"sg"})
    public void showGenres() {
        genreDao.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all authors", key = {"sa"})
    public void showAuthors() {
        authorDao.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all library", key = {"sal"})
    public void showAll() {
        bookDao.getAll().forEach(bookDO -> {
            consoleOutput.println(bookDO);
            consoleOutput.println(bookDO.getAuthorDO().getEntity());
            consoleOutput.println(bookDO.getGenreDO().getEntity());
        });
    }

    @Override
    @ShellMethod(value = "Show book by author name", key = {"bban"})
    public void showBooksByAuthorName(@ShellOption("-n") String name) {
        bookDao.getBooksByAuthorName(name).forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show book by genre title", key = {"bbgt"})
    public void showBooksByGenreTitle(@ShellOption("-t") String title) {
        bookDao.getBooksByGenreTitle(title).forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"ab"})
    public void addBook(@ShellOption("-id") Long id,
                        @ShellOption("-t") String title,
                        @ShellOption(value = {"-ai"}, defaultValue = NULL) Long authorId,
                        @ShellOption(value = {"-gi"}, defaultValue = NULL) Long genreId) {
        bookDao.insert(
                new BookDO(
                        id,
                        title,
                        new LazyEntity<>(authorId, null),
                        new LazyEntity<>(genreId, null)
                ));
    }

    @Override
    @ShellMethod(value = "Add genre", key = {"ag"})
    public void addGenre(@ShellOption("-id") Long id,
                         @ShellOption("-t") String title) {
        genreDao.insert(new GenreDO(id, title));
    }

    @Override
    @ShellMethod(value = "Add author", key = {"aa"})
    public void addAuthor(@ShellOption("-id") Long id, @ShellOption("-n") String name) {
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
