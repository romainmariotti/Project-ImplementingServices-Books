package ch.service;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import jakarta.annotation.PostConstruct;

@Stateful
@Named("historyBean")
@SessionScoped
public class HistoryBean implements History, Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<String> history = new ArrayList<>();

    @PostConstruct
    public void init() {
        history = new ArrayList<>();
    }

    @Override
    public void addAction(String action) {
        if (history == null) {
            history = new ArrayList<>();
        }
        // Ajouter un horodatage à l'action
        String timestamp = new Date().toString();
        history.add(timestamp + " - " + action);
        System.out.println("Added action to history: " + action); // Debug log
    }

    @Override
    public List<String> getHistory() {
        if (history == null) {
            history = new ArrayList<>();
        }
        System.out.println("Getting history, size: " + history.size()); // Debug log
        return history;
    }
    
    
}