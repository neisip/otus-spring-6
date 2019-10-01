package com.alexsoft.bookstore.repository.comment;

import com.alexsoft.bookstore.domain.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    static private Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean save(Comment entity) {
        try {
            if (entity.getId() > 0) {
                return em.merge(entity) != null;
            } else {
                em.persist(entity);
                return true;
            }
        } catch (Exception e) {
            logger.error("saving entity " + entity.getId() + " failed", e);
            return false;
        }
    }
}
