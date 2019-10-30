package com.alexsoft.bookstore.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BookDetailController {

    @RequestMapping("/book/{id}")
    public @NonNull String bookDetail() {
        return "bookDetail";
    }
}
