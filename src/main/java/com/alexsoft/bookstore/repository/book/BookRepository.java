package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
    Mono<Void> deleteByTitle(String title);
    Mono<Book> findOneByTitle(String title);
    Flux<Book> findBooksByGenre(String genre);
}
