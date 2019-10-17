package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.exceptions.NotFoundException;
import com.alexsoft.bookstore.domain.Book;
import com.alexsoft.bookstore.domain.Comment;
import com.alexsoft.bookstore.repository.book.BookRepository;
import com.alexsoft.bookstore.repository.comment.CommentRepository;
import com.alexsoft.bookstore.service.CommentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book/{id}")
public class BookDetailController {

    private final @NonNull BookRepository bookRepository;

    @GetMapping("")
    public @NonNull String bookDetail(@PathVariable("id") @NonNull final String id,
                                      @NonNull final Model model) {
        val b = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", b);
        model.addAttribute("comment", new Comment());

        return "bookDetail";
    }

    @PutMapping("/update")
    public @NonNull String editBook(@PathVariable("id") @NonNull final String id,
                                    @NonNull final Book book) {
        val b = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        val genre = book.getGenre();
        if (genre != null) {
            b.setGenre(genre);
        }
        bookRepository.save(b);

        return "redirect:/book/" + id;
    }

}
