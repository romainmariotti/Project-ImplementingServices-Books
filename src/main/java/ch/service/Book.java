package ch.service;

import ch.hevs.businessobject.*;
import java.util.List;

public interface Book {

    List<Book> getBooksByCategory(String categoryName);
    List<Book> getBooksByWriter(String writerName);
    List<Book> getBooksByTitle(String title);
}