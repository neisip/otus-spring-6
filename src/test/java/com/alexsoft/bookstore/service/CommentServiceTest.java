package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.book.BookRepositoryImpl;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentServiceTest {

    @Autowired
    private EntityManager em;

    private BookRepository bookRepository;
    private CommentService sut;

    @PostConstruct
    void setup() {
        bookRepository = new BookRepositoryImpl(em);
        sut = new CommentServiceImpl(bookRepository);
    }

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
