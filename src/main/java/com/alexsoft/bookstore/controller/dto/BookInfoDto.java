package com.alexsoft.bookstore.controller.dto;

import com.alexsoft.bookstore.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookInfoDto {
    private String id;
    private String title;
    private String genre;
    private String authorName;

}
