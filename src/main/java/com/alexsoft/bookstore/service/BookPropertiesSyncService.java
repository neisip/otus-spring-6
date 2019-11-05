package com.alexsoft.bookstore.service;

import com.alexsoft.bookstore.domain.Book;
import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface BookPropertiesSyncService {

    @NonNull Mono<Book> syncBookForSaving(@NonNull final Book book);

}
