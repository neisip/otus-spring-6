package com.alexsoft.bookstore.utils;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import lombok.val;

import java.util.Optional;

public class BookMappingUtil {

    public static Optional<Book> mapBookInfoToBook(BookInfoDto bookInfoDto) {
        if (bookInfoDto == null) {
            return Optional.empty();
        }

        val title = bookInfoDto.getTitle();
        val genre = bookInfoDto.getGenre();
        val authorName = bookInfoDto.getAuthorName();
        if (title != null && genre != null && authorName != null) {
            val b = new Book();
            b.setTitle(title);
            b.setGenre(genre);
            b.setAuthor(new Author(authorName));

            return Optional.of(b);
        }
        return Optional.empty();
    }

    public static Optional<BookInfoDto> mapBookToBookInfoDto(Book book) {
        if (book == null) {
            return Optional.empty();
        }
        val id = book.getId();
        val title = book.getTitle();
        val genre = book.getGenre();
        val authorName = book.getAuthor().getName();
        if (id != null && title != null && genre != null && authorName != null) {
            val b = new BookInfoDto();
            b.setId(id);
            b.setTitle(title);
            b.setGenre(genre);
            b.setAuthorName(authorName);

            return Optional.of(b);
        }
        return Optional.empty();
    }
}
