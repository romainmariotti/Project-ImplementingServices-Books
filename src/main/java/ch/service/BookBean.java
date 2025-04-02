package ch.service;

import ch.hevs.businessobject.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookBean implements Book {

    @PersistenceContext(unitName = "bookPU")
    private EntityManager em;

    public List<Book> getBooksByCategory(String categoryName) {
        return em.createQuery("SELECT b FROM Book b WHERE b.category.name = :categoryName", Book.class)
                 .setParameter("categoryName", categoryName)
                 .getResultList();
    }

    public List<Book> getBooksByWriter(String writerName) {
        return em.createQuery("SELECT b FROM Book b WHERE CONCAT(b.writer.firstname, ' ', b.writer.lastname) = :writerName", Book.class)
                 .setParameter("writerName", writerName)
                 .getResultList();
    }

    public List<Book> getBooksByTitle(String title) {
        return em.createQuery("SELECT b FROM Book b WHERE b.title LIKE :title", Book.class)
                 .setParameter("title", "%" + title + "%")
                 .getResultList();
    }
}