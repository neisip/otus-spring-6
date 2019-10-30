package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepositoryCustom {
    Flux<Book> findBooksByAuthor_Name(String name);
    Mono<Book> findOneByTitleAndAuthor_Name(String title, String authorName);
}
