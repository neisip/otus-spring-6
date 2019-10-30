package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import com.alexsoft.bookstore.controller.exceptions.NotFoundException;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.utils.BookMappingUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book/{id}")
public class BookDetailApiController {

    private final @NonNull BookRepository bookRepository;

    @GetMapping("/detail")
    public @NonNull Mono<BookInfoDto> bookDetailById(@PathVariable("id") @NonNull final String id) {

        return bookRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException()))
                .map(BookMappingUtil::mapBookToBookInfoDto)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }
}
