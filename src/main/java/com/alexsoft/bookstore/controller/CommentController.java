package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import com.alexsoft.bookstore.service.CommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book/{id}/")
public class CommentController {

    private final @NonNull BookRepository bookRepository;
    private final @NonNull CommentService commentService;
    private final @NonNull CommentRepository commentRepository;

    @DeleteMapping("comment/{comment_id}/delete")
    public @NonNull String deleteCommentById(@PathVariable("id") @NonNull final String id,
                                             @PathVariable("comment_id") @NonNull final String commentId) {

        commentRepository.deleteById(commentId);
        return "redirect:/book/" + id ;
    }

    @PostMapping("comment/add")
    public @NonNull String addComment(@PathVariable("id") @NonNull final String id,
                                             @NonNull final Comment comment,
                                             @NonNull final BindingResult bindingResult) {

        if (!bindingResult.hasErrors()) {
            bookRepository.findById(id).ifPresent(book -> {
                commentService.addCommentToBookWithTitleAndAuthorName(comment.getText(), book.getTitle(), book.getAuthor().getName());
            });
        }
        return "redirect:/book/" + id;
    }

}
