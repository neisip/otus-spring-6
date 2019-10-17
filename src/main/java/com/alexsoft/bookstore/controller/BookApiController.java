package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import com.alexsoft.bookstore.controller.exceptions.NotFoundException;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.utils.BookMappingUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookApiController {

    private final @NonNull BookRepository bookRepository;


    @GetMapping("/list")
    public @NonNull ResponseEntity<List<BookInfoDto>> bookList() {

        List<BookInfoDto> books = bookRepository.findAll()
                .stream()
                .map(BookMappingUtil::mapBookToBookInfoDto)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        return ResponseEntity.ok(books);
    }

    @PostMapping("/add")
    public @NonNull ResponseEntity<String> addBook(@RequestBody @NonNull final BookInfoDto book) {
        val ob = BookMappingUtil.mapBookInfoToBook(book);
        String id = null;
        if (ob.isPresent()) {
            id = bookRepository.save(ob.get()).getId();
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(id);
    }


    @DeleteMapping("/{id}")
    public @NonNull ResponseEntity<Void> deleteBookById(@PathVariable("id") @NonNull final String id) {
        val ob = bookRepository.findById(id);
        if (ob.isPresent()) {
            bookRepository.delete(ob.get());
        } else {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
