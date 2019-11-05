package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
    Mono<Void> deleteByName(String name);
    Mono<Author> findOneByName(String name);
    Flux<Author> findAllByName(String name);
}
