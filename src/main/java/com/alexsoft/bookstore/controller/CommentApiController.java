package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import com.alexsoft.bookstore.service.CommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book/{id}/comment")
public class CommentApiController {

    private final @NonNull BookRepository bookRepository;
    private final @NonNull CommentService commentService;
    private final @NonNull CommentRepository commentRepository;


    @PostMapping("/add")
    public @NonNull Mono<ResponseEntity<String>> addComment(@PathVariable("id") @NonNull final String id,
                                                            @RequestBody @NonNull final Comment comment) {

        return bookRepository.findById(id)
                .flatMap(book ->
                        commentService.addCommentToBookWithTitleAndAuthorName(
                                comment.getText(),
                                book.getTitle(),
                                book.getAuthor().getName()))
                .map(c -> ResponseEntity.ok(c.getId()));

    }

    @DeleteMapping("/{comment_id}")
    public @NonNull Mono<ResponseEntity<Void>> deleteCommentById(@PathVariable("id") @NonNull final String id,
                                                           @PathVariable("comment_id") @NonNull final String commentId) {

        return bookRepository.findById(id)
                .map(b -> {
                    b.getCommentList().removeIf(comment -> comment.getId().equals(commentId));
                    return bookRepository.save(b);

                }).then(commentRepository.deleteById(id))
                .map(e -> ResponseEntity.ok().<Void>build())
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
