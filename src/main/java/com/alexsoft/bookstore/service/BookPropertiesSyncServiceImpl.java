package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookPropertiesSyncServiceImpl implements BookPropertiesSyncService {

    private final @NonNull AuthorRepository authorRepository;
    private final @NonNull CommentRepository commentRepository;

    @Override
    public @NonNull Mono<Book> syncBookForSaving(@NonNull Book book) {
        val authors = saveAuthorsFrom(book);
        val comments = saveCommentsFrom(book);
        return Mono.zip(authors, comments, (a, c) -> {
            book.setAuthor(a);
            book.setCommentList(c);
            return book;
        });
    }

    private Mono<Author> saveAuthorsFrom(@NonNull final Book book) {
        val a = book.getAuthor();
        if (a != null && a.getId() == null) {
            return authorRepository.save(a);
        }
        return Mono.empty();
    }

    private Mono<List<Comment>> saveCommentsFrom(@NonNull final Book book) {

        val comments = book.getCommentList();

        if (comments != null && !comments.isEmpty()) {

            val nonEmptyComments = comments
                    .stream()
                    .filter(c -> Objects.isNull(c.getId()))
                    .collect(Collectors.toList());
            return commentRepository
                    .saveAll(nonEmptyComments)
                    .buffer()
                    .next();
        }
        return Mono.empty();
    }

}
