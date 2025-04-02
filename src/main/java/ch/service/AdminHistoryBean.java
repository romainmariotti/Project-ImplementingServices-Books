package ch.service;

import jakarta.ejb.Stateful;
import java.util.ArrayList;
import java.util.List;

@Stateful
public class AdminHistoryBean implements AdminHistory {

    private List<String> history = new ArrayList<>();

    public void addAction(String action) {
        history.add(action);
    }

    public List<String> getHistory() {
        return history;
    }
}