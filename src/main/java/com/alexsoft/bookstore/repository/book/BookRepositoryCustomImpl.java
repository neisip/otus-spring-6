package com.alexsoft.bookstore.repository.book;


import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @Autowired
    private ReactiveMongoOperations operations;
    private AuthorRepository authorRepository;

    public BookRepositoryCustomImpl(AuthorRepository authorRepository, ReactiveMongoOperations operations) {
        this.authorRepository = authorRepository;
        this.operations = operations;
    }

    @Override
    public Flux<Book> findBooksByAuthor_Name(String name) {
        return authorRepository
                .findAllByName(name)
                .flatMap(a -> findBooksByAuthorId(a.getId()));
    }

    private Flux<Book> findBooksByAuthorId(String authorId) {
        val q = new Query();
        q.addCriteria(Criteria.where("author.id").is(authorId));
        return operations.find(q, Book.class);
    }

    @Override
    public Mono<Book> findOneByTitleAndAuthor_Name(String title, String authorName) {
        return authorRepository
                .findAllByName(authorName)
                .flatMap(a -> findOneByTitleAndAuthorId(title, a.getId()))
                .next();
    }

    private Mono<Book> findOneByTitleAndAuthorId(String title, String id) {
        val q = new Query();
        q.addCriteria(Criteria.where("author.id").is(id).and("title").is(title));
        return operations.findOne(q, Book.class);
    }
}
