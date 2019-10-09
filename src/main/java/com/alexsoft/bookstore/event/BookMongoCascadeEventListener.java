package com.alexsoft.bookstore.event;

import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;

@Component
public class BookMongoCascadeEventListener extends AbstractMongoEventListener<Book> {

    private AuthorRepository authorRepository;
    private CommentRepository commentRepository;
    private BookRepository bookRepository;

    public BookMongoCascadeEventListener(AuthorRepository authorRepository,
                                         CommentRepository commentRepository,
                                         BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        super.onBeforeConvert(event);
        val book = event.getSource();
        if (book.getAuthor() != null) {
            authorRepository.save(book.getAuthor());
        }

        val commentList = book.getCommentList();
        if (commentList != null && !commentList.isEmpty()) {
            commentRepository.saveAll(commentList);
        }
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        super.onBeforeDelete(event);
        val id = getIdFromEvent(event);
        if (id == null) {
            return;
        }
        bookRepository.findById(id).ifPresent(b -> {
            val commentList = b.getCommentList();
            if (commentList != null && !commentList.isEmpty()) {
                commentRepository.deleteAll(commentList);
            }
        });
    }

    private String getIdFromEvent(AbstractDeleteEvent<Book> event) {
        val document = event.getSource();
        Object id = document.get("_id");
        if (id == null) {
            val titleObj = document.get("title");
            if (titleObj != null) {
                val ob = bookRepository.findOneByTitle(titleObj.toString());
                if (ob.isPresent()) {
                    return ob.get().getId();
                }
            }
            return null;
        } else {
            return id.toString();
        }
    }
}
