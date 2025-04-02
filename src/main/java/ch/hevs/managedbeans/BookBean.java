package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.service.BookServiceLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named("bookBean")
@SessionScoped
public class BookBean implements Serializable {

    @EJB
    private BookServiceLocal bookService;

    private List<Book> books;
    private Book selectedBook;

    @PostConstruct
    public void init() {
        books = bookService.getBooksByCategory("All"); // Charger tous les livres par défaut
    }

    public void addBook(Book book) {
        bookService.addBook(book);
        books = bookService.getBooksByCategory("All"); // Rafraîchir la liste
    }

    public void updateBook(Book book) {
        bookService.updateBook(book);
        books = bookService.getBooksByCategory("All"); // Rafraîchir la liste
    }

    public void deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
        books = bookService.getBooksByCategory("All"); // Rafraîchir la liste
    }

    // Getters et setters
    public List<Book> getBooks() {
        return books;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }
}