package com.alexsoft.bookstore.repository.author;

import com.alexsoft.bookstore.domain.Author;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    static private Logger logger = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(long id) {
        try {
            return Optional.ofNullable(em.find(Author.class, id));
        } catch (Exception e) {
            logger.error("find by id "+ id + " failed", e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public boolean deleteByName(String name) {
        try {
            val query = em.createQuery("DELETE FROM Author a WHERE a.name = :authorName ");
            query.setParameter("authorName", name);
            return query.executeUpdate() <= 0;
        } catch (Exception e) {
            logger.error("deleteByName for name " + name + " failed", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean save(Author entity) {
        if (findById(entity.getId()).isPresent()) {
            return em.merge(entity) != null;
        } else {
            try {
                em.persist(entity);
                return true;
            } catch (Exception e) {
                logger.error("saving entity " + entity.getId() + " failed", e);
                return false;
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAll() {
        try {
            return em.createQuery("select a from Author a", Author.class).getResultList();
        } catch (Exception e) {
            logger.error("finding all authors failed", e);
            return Collections.emptyList();
        }
    }

}
