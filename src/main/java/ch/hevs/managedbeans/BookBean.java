package ch.hevs.managedbeans;

import ch.hevs.businessobject.*;
import ch.service.BookServiceLocal;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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

    private Long selectedCategoryId;
    private Long selectedWriterId;
    
    
    // Filter properties
    private String categoryFilter;
    private String writerFilter;
    private String titleFilter;
    
    

    @PostConstruct
    public void init() {
    	refreshBooks();
    }
    
    
    private void refreshBooks() {
        books = bookService.getAllBooks();
        filteredBooks = new ArrayList<>(books);
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
            books = bookService.getAllBooks();
            filteredBooks = new ArrayList<>(books);
            
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
    
    
    
    public String prepareModifyBook(Book book) {
        this.selectedBook = book;
        
        // Determine the book type
        if (book instanceof Magazine) {
            selectedType = "Magazine";
        } else if (book instanceof Comic) {
            selectedType = "Comic";
        } else if (book instanceof Novel) {
            selectedType = "Novel";
        }
        
        return "modifyBook.xhtml?faces-redirect=true";
    }

    public String modifyBook() {
        try {
            // Récupérer la catégorie et l'écrivain sélectionnés à partir de leurs IDs
            Category selectedCategory = bookService.findCategoryById(selectedCategoryId);
            Writer selectedWriter = bookService.findWriterById(selectedWriterId);
            
            // Mettre à jour la catégorie et l'écrivain
            selectedBook.setCategory(selectedCategory);
            selectedBook.setWriter(selectedWriter);
            
            // Le reste de votre code existant...
            bookService.updateBook(selectedBook);
            
            // Refresh book list
            books = bookService.getAllBooks();
            filteredBooks = books;
            
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Livre modifié avec succès"));
            
            return "viewBooks?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", e.getMessage()));
            return null;
        }
    }
    
    
    public String viewBookDetails(Book book) {
        this.selectedBook = book;
        
        // Determine the book type for proper display
        if (book instanceof Magazine) {
            selectedType = "Magazine";
        } else if (book instanceof Comic) {
            selectedType = "Comic";
        } else if (book instanceof Novel) {
            selectedType = "Novel";
        }
        
        return "bookDetails.xhtml?faces-redirect=true";
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
    
 // Filtering methods
    public void filterByCategory() {
        if (categoryFilter == null || categoryFilter.isEmpty()) {
            filteredBooks = books; // Show all if no filter
        } else {
            filteredBooks = bookService.getBooksByCategory(categoryFilter);
        }
        // Also apply other active filters
        applyWriterFilter();
        applyTitleFilter();
    }

    public void filterByWriter() {
        if (writerFilter == null || writerFilter.isEmpty()) {
            filteredBooks = books; // Show all if no filter
        } else {
            filteredBooks = bookService.getBooksByWriter(writerFilter);
        }
        // Also apply other active filters
        applyCategoryFilter();
        applyTitleFilter();
    }

    public void filterByTitle() {
        if (titleFilter == null || titleFilter.isEmpty()) {
            filteredBooks = books; // Show all if no filter
        } else {
            filteredBooks = bookService.getBooksByTitle(titleFilter);
        }
        // Also apply other active filters
        applyCategoryFilter();
        applyWriterFilter();
    }
    
 // Helper methods for combined filtering
    private void applyCategoryFilter() {
        if (categoryFilter != null && !categoryFilter.isEmpty()) {
            filteredBooks = filteredBooks.stream()
                .filter(b -> b.getCategory() != null && 
                       categoryFilter.equals(b.getCategory().getName()))
                .collect(Collectors.toList());
        }
    }

    private void applyWriterFilter() {
        if (writerFilter != null && !writerFilter.isEmpty()) {
            filteredBooks = filteredBooks.stream()
                .filter(b -> b.getWriter() != null && 
                       writerFilter.equals(b.getWriter().getFirstname() + " " + b.getWriter().getLastname()))
                .collect(Collectors.toList());
        }
    }

    private void applyTitleFilter() {
        if (titleFilter != null && !titleFilter.isEmpty()) {
            filteredBooks = filteredBooks.stream()
                .filter(b -> b.getTitle() != null && 
                       b.getTitle().toLowerCase().contains(titleFilter.toLowerCase()))
                .collect(Collectors.toList());
        }
    }
    
    
    public void applyFilters() {
        // Start fresh with all books
        filteredBooks = new ArrayList<>(books);
        
        // Apply each filter in sequence
        applyCategoryFilter();
        applyWriterFilter();
        applyTitleFilter();
    }
    
    
    public void resetFilters() {
        categoryFilter = null;
        writerFilter = null;
        titleFilter = null;
        filteredBooks = books;
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

    public Long getSelectedCategoryId() {
        return selectedBook != null && selectedBook.getCategory() != null ? 
               selectedBook.getCategory().getId() : selectedCategoryId;
    }

    public void setSelectedCategoryId(Long id) {
        this.selectedCategoryId = id;
    }

    public Long getSelectedWriterId() {
        return selectedBook != null && selectedBook.getWriter() != null ? 
               selectedBook.getWriter().getId() : selectedWriterId;
    }

    public void setSelectedWriterId(Long id) {
        this.selectedWriterId = id;
    }

    public String logout() {
        // Invalide la session courante
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        // Redirige vers la page de login
        return "/login.xhtml?faces-redirect=true";
    }
}


