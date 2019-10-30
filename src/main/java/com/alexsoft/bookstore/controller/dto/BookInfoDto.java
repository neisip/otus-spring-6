package com.alexsoft.bookstore.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookInfoDto {
    private String id;
    private String title;
    private String genre;
    private String authorName;
    private List<CommentInfoDto> comments;
}
