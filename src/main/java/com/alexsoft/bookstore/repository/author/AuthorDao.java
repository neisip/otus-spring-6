package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.repository.common.BaseDao;

public interface AuthorDao extends BaseDao<AuthorDO> {
    AuthorDO getById(Long id);
    boolean deleteByName(String name);
}
