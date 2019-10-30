package com.alexsoft.bookstore.controller;

import com.alexsoft.bookstore.controller.dto.BookInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("book", new BookInfoDto());
        return "index";
    }
}
