package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.repository.book.BookRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@EnableJpaRepositories(basePackageClasses = BookRepository.class)
@Import(CommentServiceImpl.class)
public class CommentServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentService sut;

    @Test
    void commentIsAttachedToExistingBook() {
        //given
        val mtd = "Delete me!";
        val books = bookRepository.findAll();
        val book = books.get(0);

        //when
        sut.addCommentToBookWithTitleAndAuthorName(mtd, book.getTitle(), book.getAuthor().getName());

        //then
        val comments = book.getCommentList();

        assertEquals(mtd, comments.get(comments.size() - 1).getText());
    }
}