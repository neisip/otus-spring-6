package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.service.BookPropertiesSyncService;
import com.alexsoft.bookstore.utils.BookMappingUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookApiController {

    private final @NonNull BookRepository bookRepository;
    private final @NonNull BookPropertiesSyncService bookPropertiesSyncService;

    @GetMapping("/list")
    public @NonNull Flux<BookInfoDto> bookList() {
        return bookRepository
                .findAll()
                .map(BookMappingUtil::mapBookToBookInfoDto)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    @PostMapping("/add")
    public @NonNull Mono<ResponseEntity<String>> addBook(@RequestBody @NonNull final BookInfoDto book) {
        val ob = BookMappingUtil.mapBookInfoToBook(book);
        if (ob.isPresent()) {
            val passedBook = ob.get();

            return bookPropertiesSyncService
                    .syncBookForSaving(passedBook)
                    .then(bookRepository.save(passedBook))
                    .map(b -> {
                        val id = b.getId();
                        return ResponseEntity.ok(id);
                    });
        } else {
            return Mono.just(ResponseEntity.unprocessableEntity().build());
        }
    }

    @DeleteMapping("/{id}")
    public @NonNull Mono<ServerResponse> deleteBookById(@PathVariable("id") @NonNull final String id) {
        return bookRepository
                .findById(id)
                .flatMap(bookRepository::delete)
                .then()
                .flatMap(e -> ServerResponse.ok().build())
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    @PostMapping(value = "/{id}")
    public @NonNull Mono<ResponseEntity<Void>> updateBookFromInfo(
            @PathVariable("id") @NonNull final String id,
            @RequestBody @NonNull final BookInfoDto info) {

        return bookRepository.findById(id)
                .map(book -> {
                    book.setGenre(info.getGenre());
                    return book;
                }).flatMap(bookRepository::save)
                .map(b -> ResponseEntity.ok().build());

    }

}
