package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.domain.Comment;
import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface CommentService {
    @NonNull Mono<Comment> addCommentToBookWithTitleAndAuthorName(
            @NonNull final String commentText,
            @NonNull final String bookTitle,
            @NonNull final String authorName);
}
