package com.alexsoft.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDO {
    private Long id;
    private String title;
    private Long authorId;
    private Long genreId;
}
