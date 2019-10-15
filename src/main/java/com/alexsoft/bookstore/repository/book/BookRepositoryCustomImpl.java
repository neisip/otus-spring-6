package com.alexsoft.bookstore.repository.book;


import com.alexsoft.bookstore.domain.Author;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.author.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import sun.jvm.hotspot.code.Location;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;
    private AuthorRepository authorRepository;

    public BookRepositoryCustomImpl(AuthorRepository authorRepository, MongoTemplate mongoTemplate) {

        this.authorRepository = authorRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Book> findBooksByAuthor_Name(String name) {
        List<Author> authorsByName = authorRepository.findAllByName(name);
        val books = new LinkedList<Book>();
        authorsByName.forEach(a -> {

            books.addAll(findBooksByAuthorId(a.getId()));
        });
        return books;
    }

    private List<Book> findBooksByAuthorId(String authorId) {
        val q = new Query();
        q.addCriteria(Criteria.where("author.id").is(authorId));
        return mongoTemplate.find(q, Book.class);
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor_Name(String title, String authorName) {

        List<Author> authorsByName = authorRepository.findAllByName(authorName);
        for (Author a :
                authorsByName) {
            val id = a.getId();
            val ob = findOneByTitleAndAuthorId(title, id);
            if (ob.isPresent()) {
                return ob;
            }
        }
        return Optional.empty();
    }

    private Optional<Book> findOneByTitleAndAuthorId(String title, String id) {
        val q = new Query();
        q.addCriteria(Criteria.where("author.id").is(id).and("title").is(title));
        return Optional.ofNullable(mongoTemplate.findOne(q, Book.class));
    }
}
