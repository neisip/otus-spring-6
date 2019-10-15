package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.service.CommentService;
import lombok.val;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.PrintStream;

import static org.springframework.shell.standard.ShellOption.NULL;


@ShellComponent
public class BookstoreShellControllerImpl implements BookstoreShellController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CommentService commentService;
    private final PrintStream consoleOutput;

    public BookstoreShellControllerImpl(AuthorRepository authorRepository,
                                        BookRepository bookRepository,
                                        ConsoleContext consoleContext,
                                        CommentService commentService) {

        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.consoleOutput = consoleContext.getOutput();
        this.commentService = commentService;
    }

    @Override
    @ShellMethod(value = "Show all books", key = {"sb"})
    public void showBooks() {
        bookRepository.findAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all authors", key = {"sa"})
    public void showAuthors() {
        authorRepository.findAll().forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show all library", key = {"sal"})
    public void showAll() {
        bookRepository.findAll().forEach(book -> {
            consoleOutput.println(book);
            consoleOutput.println(book.getAuthor());
            consoleOutput.println(book.getGenre());
            if (book.getCommentList() != null) {
                book.getCommentList().forEach(consoleOutput::println);
            }
        });
    }

    @Override
    @ShellMethod(value = "Show book by author name", key = {"bban"})
    public void showBooksByAuthorName(String name) {
        bookRepository.findBooksByAuthor_Name(name).forEach(consoleOutput::println);
    }

    @Override
    @ShellMethod(value = "Show book by genre title", key = {"bbgt"})
    public void showBooksByGenreTitle(String title) {
        bookRepository.findBooksByGenre(title).forEach(consoleOutput::println);
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
            book.setGenre(genreTitle);
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
    @ShellMethod(value = "Add author", key = {"aa"})
    public void addAuthor(String name) {
        val g = new Author();
        g.setName(name);
        authorRepository.save(g);
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
