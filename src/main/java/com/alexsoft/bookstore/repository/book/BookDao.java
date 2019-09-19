package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.BookDO;
import com.alexsoft.bookstore.repository.common.BaseDao;

import java.util.List;

public interface BookDao extends BaseDao<BookDO> {
    boolean deleteByTitle(String title);
    List<BookDO> getBooksByAuthorName(String name);
    List<BookDO> getBooksByGenreTitle(String title);
}
