package ch.service;

import ch.hevs.businessobject.*;

import java.util.List;

public interface BookServiceLocal {

    List<Book> getBooksByCategory(String categoryName);
    List<Book> getBooksByWriter(String writerName);
    List<Book> getBooksByTitle(String title);
    List<Category> getAllCategories();
    List<Writer> getAllWriters();
    List<Book> getAllBooks();
    void addBook(Book book);
    void updateBook(Book book);
    void deleteBook(Long bookId);
}