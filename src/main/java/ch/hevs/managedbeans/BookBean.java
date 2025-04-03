package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.service.BookServiceLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named("bookBean")
@SessionScoped
public class BookBean implements Serializable {

    @EJB
    private BookServiceLocal bookService;

    private List<Book> books;
    private List<Book> filteredBooks; // New list for filtered results
    private Book selectedBook;
    private String selectedType; // "Magazine", "Comic", or "Novel"
    private List<Category> categories;
    private List<Writer> writers;

    
    
    // Filter properties
    private String categoryFilter;
    private String writerFilter;
    private String titleFilter;
    
    

    @PostConstruct
    public void init() {
        books = bookService.getAllBooks(); // Charger tous les livres par défaut
        filteredBooks = books; // Initialize with all books
        categories = bookService.getAllCategories();
        writers = bookService.getAllWriters();
    }
    
    
    
    public String addBook(Book book) {
        try {
            // Set type-specific properties based on the selected type
            if ("Magazine".equals(selectedType)) {
                Magazine magazine = (Magazine) book;
                magazine.setReleaseFrequency(getAsMagazine().getReleaseFrequency());
                bookService.addBook(magazine);
            } 
            else if ("Comic".equals(selectedType)) {
                Comic comic = (Comic) book;
                comic.setColorized(getAsComic().isColorized());
                bookService.addBook(comic);
            } 
            else if ("Novel".equals(selectedType)) {
                Novel novel = (Novel) book;
                novel.setPocketSize(getAsNovel().isPocketSize());
                bookService.addBook(novel);
            }
            
            // Refresh the book list
            books = bookService.getBooksByCategory("All");
            filteredBooks = books;
            
            // Reset the form
            selectedBook = null;
            selectedType = null;
            
            return "viewBooks.xhtml?faces-redirect=true"; // Redirect to view page
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Stay on same page if error occurs
        }
    }
    
    
    
    
    public void deleteBook(Book book) {
        try {
            // Remove from database
            bookService.deleteBook(book.getId());
            
            // Remove from current lists
            books.remove(book);
            filteredBooks.remove(book);
            
            // Show success message
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Success", "Book deleted successfully"));
        } catch (Exception e) {
            // Show error message
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Error", "Could not delete book: " + e.getMessage()));
            e.printStackTrace();
        }
    }
    
    
    
    
 // Called when book type is selected
    public void onTypeChange() {
        if ("Magazine".equals(selectedType)) {
            selectedBook = new Magazine();
        } else if ("Comic".equals(selectedType)) {
            selectedBook = new Comic();
        } else if ("Novel".equals(selectedType)) {
            selectedBook = new Novel();
        }
    }
    
 // Getters and setters
    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
        onTypeChange(); // Update selectedBook when type changes
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Writer> getWriters() {
        return writers;
    }
    
    public Magazine getAsMagazine() {
        return (selectedBook instanceof Magazine) ? (Magazine) selectedBook : null;
    }

    public Comic getAsComic() {
        return (selectedBook instanceof Comic) ? (Comic) selectedBook : null;
    }

    public Novel getAsNovel() {
        return (selectedBook instanceof Novel) ? (Novel) selectedBook : null;
    }
    
 // Filter methods
    public void filterByCategory() {
        if (categoryFilter == null || categoryFilter.isEmpty()) {
            filteredBooks = books;
        } else {
            filteredBooks = bookService.getBooksByCategory(categoryFilter);
        }
    }

    public void filterByWriter() {
        if (writerFilter == null || writerFilter.isEmpty()) {
            filteredBooks = books;
        } else {
            filteredBooks = bookService.getBooksByWriter(writerFilter);
        }
    }

    public void filterByTitle() {
        if (titleFilter == null || titleFilter.isEmpty()) {
            filteredBooks = books;
        } else {
            filteredBooks = bookService.getBooksByTitle(titleFilter);
        }
    }


    // Getters and setters
    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getFilteredBooks() {
        return filteredBooks;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(String categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public String getWriterFilter() {
        return writerFilter;
    }

    public void setWriterFilter(String writerFilter) {
        this.writerFilter = writerFilter;
    }

    public String getTitleFilter() {
        return titleFilter;
    }

    public void setTitleFilter(String titleFilter) {
        this.titleFilter = titleFilter;
    }
}

   
