package ch.service;

import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.Writer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BookService implements BookServiceLocal {

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


    public void addBook(Book book) {
        em.persist(book);
    }


    public void updateBook(Book book) {
        em.merge(book);
    }


    public void deleteBook(Long bookId) {
        Book book = em.find(Book.class, bookId);
        if (book != null) {
            em.remove(book);
        }
    }

    public List<Category> getAllCategories() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    public List<Writer> getAllWriters() {
        return em.createQuery("SELECT w FROM Writer w", Writer.class).getResultList();
    }

    public List<Book> getAllBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }
}