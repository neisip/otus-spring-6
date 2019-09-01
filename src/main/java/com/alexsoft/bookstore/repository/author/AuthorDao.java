package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.AuthorDO;
import com.alexsoft.bookstore.repository.common.BaseDao;

public interface AuthorDao extends BaseDao<AuthorDO> {

    void deleteByName(String name);
}
