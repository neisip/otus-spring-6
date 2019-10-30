package com.alexsoft.bookstore.utils;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import com.alexsoft.bookstore.controller.dto.CommentInfoDto;
import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.domain.Comment;
import lombok.val;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

            val commentInfoDtos = bookInfoDto.getComments();
            if (commentInfoDtos != null && !commentInfoDtos.isEmpty()) {
                List<Comment> c = commentInfoDtos
                        .stream()
                        .map(CommentMappingUtil::mapCommentInfoToComment)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                b.setCommentList(c);
            }

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

            val comments = book.getCommentList();
            if (comments != null && !comments.isEmpty()) {
                List<CommentInfoDto> commentInfoDtos = comments
                        .stream()
                        .map(CommentMappingUtil::mapCommentToCommentInfo)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                b.setComments(commentInfoDtos);
            }

            return Optional.of(b);
        }
        return Optional.empty();
    }


}
