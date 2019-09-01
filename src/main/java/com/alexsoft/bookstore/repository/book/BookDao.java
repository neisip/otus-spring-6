package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.repository.common.BaseDao;

import java.util.List;

public interface BookDao extends BaseDao<BookDO> {
    void deleteByTitle(String title);
    void showBooksByAuthorName(String name);
    void showBooksByGenreTitle(String title);
}
