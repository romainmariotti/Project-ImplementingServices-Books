package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setCategory(this); // Maintenir la cohérence bidirectionnelle
    }

    public void removeBook(Book book) {
        if (books != null) {
            books.remove(book);
            book.setCategory(null); // Supprimer la relation bidirectionnelle
        }
    }
}