package com.alexsoft.bookstore.service;

public interface CommentService {
    void addCommentToBookWithTitleAndAuthorName(String commentText, String bookTitle, String authorName);
}
