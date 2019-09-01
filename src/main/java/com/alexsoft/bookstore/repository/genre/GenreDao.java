package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.GenreDO;
import com.alexsoft.bookstore.repository.common.BaseDao;

public interface GenreDao extends BaseDao<GenreDO> {
    void deleteByTitle(String title);
}
