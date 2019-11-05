package com.alexsoft.bookstore.utils;

import com.alexsoft.bookstore.controller.dto.CommentInfoDto;
import com.alexsoft.bookstore.domain.Comment;
import lombok.NonNull;
import lombok.val;

import java.util.Optional;

class CommentMappingUtil {

    static @NonNull Optional<Comment> mapCommentInfoToComment(@NonNull final CommentInfoDto commentInfoDto) {
        if (commentInfoDto.getText() == null) {
            return Optional.empty();
        }

        val c = new Comment();
        c.setText(commentInfoDto.getText());
        c.setId(commentInfoDto.getId());
        return Optional.of(c);
    }


    static @NonNull Optional<CommentInfoDto> mapCommentToCommentInfo(@NonNull final Comment comment) {
        if (comment.getId() == null || comment.getText() == null) {
            return Optional.empty();
        }

        val info = new CommentInfoDto();
        info.setId(comment.getId());
        info.setText(comment.getText());
        return Optional.of(info);
    }
}
