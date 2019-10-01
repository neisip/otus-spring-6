package com.alexsoft.bookstore.repository.book;

import com.alexsoft.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    static private Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public boolean deleteByTitle(String title) {
        try {
            val query = em.createQuery("DELETE FROM Book b WHERE b.title = :bookTitle ");
            query.setParameter("bookTitle", title);
            return query.executeUpdate() <= 0;
        } catch (Exception e) {
            logger.error("deleteByTitle for title " + title + " failed", e);
            return false;
        }
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("BookWithAuthorAndGenreAndComment");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.author.name = :name", Book.class);
            query.setParameter("name", name);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Couldn't get books for author " + name, e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Book> findBookByAuthorNameAndTitle(String authorName, String title) {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("BookWithAuthorAndGenreAndComment");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.author.name = :name AND b.title = :title", Book.class);
            query.setParameter("name", authorName);
            query.setParameter("title", title);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            logger.error("Couldn't get books for author name  " + authorName + " title " + title, e);
            return Optional.empty();
        }

    }

    @Override
    public List<Book> getBooksByGenreTitle(String title) {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("BookWithAuthorAndGenreAndComment");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.genre.title = :title", Book.class);
            query.setParameter("title", title);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Couldn't get books for genre  " + title, e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public boolean save(Book entity) {
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

    @Override
    public List<Book> getAll() {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("BookWithAuthorAndGenreAndComment");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Couldn't get list of books");
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        try {
            EntityGraph<?> entityGraph = em.getEntityGraph("BookWithAuthorAndGenreAndComment");
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE b.id = :book_id", Book.class);
            query.setParameter("book_id", id);
            query.setHint("javax.persistence.fetchgraph", entityGraph);
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            logger.error("Couldn't find book " + id, e);
            return Optional.empty();
        }
    }
}
