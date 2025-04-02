package ch.service;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface AdminHistory {

    void addAction(String action);

    List<String> getHistory();
}