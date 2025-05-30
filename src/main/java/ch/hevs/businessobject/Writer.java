package ch.hevs.businessobject;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.REMOVE)
    private List<Book> books;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
        book.setWriter(this); // Maintenir la cohérence bidirectionnelle
    }

    public void removeBook(Book book) {
        if (books != null) {
            books.remove(book);
            book.setWriter(null); // Supprimer la relation bidirectionnelle
        }
    }
}