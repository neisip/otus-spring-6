package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.book.BookRepository;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CommentServiceImpl implements CommentService {

    private final BookRepository bookRepository;

    public CommentServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addCommentToBookWithTitleAndAuthorName(String commentText, String bookTitle, String authorName) {
        bookRepository.findBookByAuthorNameAndTitle(authorName, bookTitle).ifPresent(b -> {
            val c = new Comment();
            c.setText(commentText);
            val commentList = b.getCommentList();
            if (commentList != null) {
                commentList.add(c);
            } else {
                b.setCommentList(Collections.singletonList(c));
            }
            bookRepository.save(b);
        });
    }
}
