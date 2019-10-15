package com.alexsoft.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Comment {

    private @Id
    String id;

    private String text;

    public Comment(String text) {
        this.text = text;
    }
}
