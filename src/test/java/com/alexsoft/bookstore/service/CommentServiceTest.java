package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.changelog.InitBookstoreDbChangelog;
import com.alexsoft.bookstore.config.AppConfig;
import com.alexsoft.bookstore.config.MongoProperties;
import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.event.BookMongoCascadeEventListener;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.book.BookRepositoryCustomImpl;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import lombok.val;
import lombok.var;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"com.alexsoft.bookstore.repository"})
@Import({CommentServiceImpl.class,
        BookMongoCascadeEventListener.class, BookRepositoryCustomImpl.class})
class CommentServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentService sut;

    @Test
    void commentIsAttachedToExistingBook(@Autowired MongoTemplate mongoTemplate) {
        //given
        val bookTitle = "123";
        val bookAuthorName = "123";
        val b = new Book();
        b.setTitle("123");
        b.setAuthor(new Author("123"));
        mongoTemplate.save(b);

        val mtd = "Delete me!";
        val books = bookRepository.findAll();
        val book = books.get(0);

        //when
        sut.addCommentToBookWithTitleAndAuthorName(mtd, book.getTitle(), book.getAuthor().getName());
        val ob  = bookRepository.findById(book.getId());
        //then
        val comments = ob.get().getCommentList();
        assertEquals(mtd, comments.get(comments.size() - 1).getText());
    }
}