package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.exceptions.NotFoundException;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.repository.book.BookRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final @NonNull BookRepository bookRepository;

    @GetMapping("/list")
    public @NonNull String bookList(@NonNull final Model model) {
        val books = bookRepository.findAll();
        model.addAttribute("book", new Book());
        model.addAttribute("books", books);
        return "books";
    }

    @PostMapping("/add")
    public @NonNull String addBook(@NonNull final Book book,
                                   @NonNull final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books";
        }
        bookRepository.save(book);
        return "redirect:/book/list";
    }


    @DeleteMapping("/delete/{id}")
    public @NonNull String deleteBookById(@PathVariable("id") @NonNull final String id) {
        val b = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        bookRepository.delete(b);
        return "redirect:/book/list";
    }
}
