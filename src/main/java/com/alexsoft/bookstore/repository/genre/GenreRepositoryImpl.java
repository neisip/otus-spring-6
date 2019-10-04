package com.alexsoft.bookstore.repository.genre;

import com.alexsoft.bookstore.domain.Genre;
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
public class GenreRepositoryImpl implements GenreRepository {
    static private Logger logger = LoggerFactory.getLogger(GenreRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean deleteByTitle(String title) {
        try {
            val query = em.createQuery("DELETE FROM Genre g WHERE g.title = :genreTitle ");
            query.setParameter("genreTitle", title);
            return query.executeUpdate() <= 0;
        } catch (Exception e) {
            logger.error("deleteByName for title " + title + " failed", e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean save(Genre genre) {
        if (findById(genre.getId()).isPresent()) {
            return em.merge(genre) != null;
        } else {
            try {
                em.persist(genre);
                return true;
            } catch (Exception e) {
                logger.error("saving genre " + genre.getId() + " failed", e);
                return false;
            }
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Genre> getAll() {
        try {
            return em.createQuery("select g from Genre g", Genre.class).getResultList();
        } catch (Exception e) {
            logger.error("finding all genres failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(long id) {
        try {
            return Optional.ofNullable(em.find(Genre.class, id));
        } catch (Exception e) {
            logger.error("find by id " + id + " failed", e);
            return Optional.empty();
        }
    }
}
