package com.alexsoft.bookstore.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentInfoDto {
    private String id;
    private String text;
}
