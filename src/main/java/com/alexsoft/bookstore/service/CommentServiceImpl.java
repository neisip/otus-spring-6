package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private @NonNull
    final BookRepository bookRepository;
    private @NonNull
    final CommentRepository commentRepository;

    @Override
    public @NonNull Mono<Comment> addCommentToBookWithTitleAndAuthorName(
            @NonNull final String commentText,
            @NonNull final String bookTitle,
            @NonNull final String authorName) {

        return bookRepository
                .findOneByTitleAndAuthor_Name(bookTitle, authorName)
                .flatMap(book -> {
                    val c = new Comment();
                    c.setText(commentText);

                    Mono<Comment> commentMono = commentRepository.save(c);
                    return commentMono.flatMap(comment -> {
                        val commentList = book.getCommentList();
                        if (commentList != null) {
                            commentList.add(c);
                        } else {
                            book.setCommentList(Collections.singletonList(comment));
                        }
                        return bookRepository.save(book).then(Mono.just(comment));
                    });
                });
    }
}
