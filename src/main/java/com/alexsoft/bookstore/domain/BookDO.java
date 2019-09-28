package com.alexsoft.bookstore.domain;

import com.alexsoft.bookstore.utils.LazyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDO implements IdProvider {
    private Long id;
    private String title;

    private LazyEntity<AuthorDO> authorDO;
    private LazyEntity<GenreDO> genreDO;


    @Override
    public String toString() {
        return "BookDO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorId=" + authorDO.getId() +
                ", genreId=" + genreDO.getId() +
                '}';
    }
}
