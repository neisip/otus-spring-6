package com.alexsoft.bookstore.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "BookWithAuthorAndGenreAndComment",
        attributeNodes = {@NamedAttributeNode(value = "author"),
                @NamedAttributeNode(value = "genre"),
                @NamedAttributeNode(value = "commentList")})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private List<Comment> commentList;


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + author.getId() +
                ", genreId=" + genre.getId() +
                '}';
    }
}
