package ch.service;

import java.util.List;

import jakarta.ejb.Local;

@Local
public interface History {

    void addAction(String action);

    List<String> getHistory();
}