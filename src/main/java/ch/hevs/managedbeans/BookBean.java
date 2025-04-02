package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.service.BookBean;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("bookBean")
@SessionScoped
public class BookBean implements Serializable {

    private String selectedType;
    private Book book;
    private List<Category> categories;
    private List<Writer> writers;

    @EJB
    private BookBean bookService;

    @PostConstruct
    public void init() {
        categories = bookService.getAllCategories();
        writers = bookService.getAllWriters();
    }

    public void onTypeChange() {
        switch (selectedType) {
            case "Magazine":
                book = new Magazine();
                break;
            case "Comic":
                book = new Comic();
                break;
            case "Novel":
                book = new Novel();
                break;
            default:
                book = null;
        }
    }

    public void saveBook() {
        bookService.addBook(book);
    }

    // Getters and setters
    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Writer> getWriters() {
        return writers;
    }
}