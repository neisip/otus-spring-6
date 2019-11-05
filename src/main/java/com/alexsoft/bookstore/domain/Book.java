package com.alexsoft.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Book {

    private @Id
    String id;

    private String title;

    private
    Author author;

    private String genre;

    private List<Comment> commentList;

}