package com.alexsoft.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDO implements IdProvider {
    private Long id;
    private String title;
}
