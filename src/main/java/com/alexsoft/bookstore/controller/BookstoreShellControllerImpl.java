package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.domain.Genre;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import com.alexsoft.bookstore.repository.genre.GenreRepository;
import com.alexsoft.bookstore.service.CommentService;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.PrintStream;
import java.util.Collections;

import static org.springframework.shell.standard.ShellOption.NULL;


@ShellComponent
public class BookstoreShellControllerImpl implements BookstoreShellController {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentService commentService;
    private final PrintStream consoleOutput;

    public BookstoreShellControllerImpl(AuthorRepository authorRepository,
                                        BookRepository bookRepository,
                                        GenreRepository genreRepository,
                                        ConsoleContext consoleContext,
                                        CommentService commentService) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.consoleOutput = consoleContext.getOutput();
        this.commentService = commentService;
    }

    @Override
    @ShellMethod(value = "Show all books", key = {"sb"})
    public void showBooks() {
        bookRepository.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all genres", key = {"sg"})
    public void showGenres() {
        genreRepository.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all authors", key = {"sa"})
    public void showAuthors() {
        authorRepository.getAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all library", key = {"sal"})
    public void showAll() {
        bookRepository.getAll().forEach(book -> {
            consoleOutput.println(book);
            consoleOutput.println(book.getAuthor());
            consoleOutput.println(book.getGenre());
            book.getCommentList().forEach(consoleOutput::println);
        });
    }

    @Override
    @ShellMethod(value = "Show book by author name", key = {"bban"})
    public void showBooksByAuthorName(@ShellOption("-n") String name) {
        bookRepository.getBooksByAuthorName(name).forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show book by genre title", key = {"bbgt"})
    public void showBooksByGenreTitle(@ShellOption("-t") String title) {
        bookRepository.getBooksByGenreTitle(title).forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Insert book", key = {"ab"})
    public void addBook(
            @ShellOption("-t") String title,
            @ShellOption(value = {"-an"}, defaultValue = NULL) String authorName,
            @ShellOption(value = {"-gt"}, defaultValue = NULL) String genreTitle
    ) {

        if (title == null) {
            return;
        }

        val book = new Book();
        book.setTitle(title);

        if (authorName != null) {
            val author = new Author();
            author.setName(authorName);
            book.setAuthor(author);
        }

        if (genreTitle != null) {
            val genre = new Genre();
            genre.setTitle(genreTitle);
            book.setGenre(genre);
        }

        bookRepository.save(book);
    }

    @Override
    @ShellMethod(value = "Add comment to book with title ", key = {"actb"})
    public void addCommentToBookWithTitleAndAuthor(@ShellOption("-ct") String commentText,
                                                   @ShellOption("-t") String title,
                                                   @ShellOption("-a") String author) {
        commentService.addCommentToBookWithTitleAndAuthorName(commentText, title, author);
    }

    @Override
    @ShellMethod(value = "Add genre", key = {"ag"})
    public void addGenre( @ShellOption("-t") String title) {
        val g = new Genre();
        g.setTitle(title);
        genreRepository.save(g);
    }

    @Override
    @ShellMethod(value = "Add author", key = {"aa"})
    public void addAuthor(@ShellOption("-n") String name) {
        val g = new Author();
        g.setName(name);
        authorRepository.save(g);
    }

    @Override
    @ShellMethod(value = "Remove genre by title", key = {"rg"})
    public void removeGenreByTitle(String title) {
        genreRepository.deleteByTitle(title);
    }

    @Override
    @ShellMethod(value = "Remove author by name", key = {"ra"})
    public void removeAuthorByName(String name) {
        authorRepository.deleteByName(name);
    }

    @Override
    @ShellMethod(value = "Remove book by title", key = {"rb"})
    public void removeBookByTitle(String title) {
        bookRepository.deleteByTitle(title);
    }
}
