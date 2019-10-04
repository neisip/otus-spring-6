package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.book.BookRepositoryImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@Import({BookRepositoryImpl.class, CommentServiceImpl.class})
public class CommentServiceTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentService sut;

    @Test
    void commentIsAttachedToExistingBook() {
        //given
        val mtd = "Delete me!";
        val books = bookRepository.getAll();
        val book = books.get(0);

        //when
        sut.addCommentToBookWithTitleAndAuthorName(mtd, book.getTitle(), book.getAuthor().getName());

        //then
        val comments = book.getCommentList();

        assertEquals(mtd, comments.get(comments.size() - 1).getText());
    }
}
