package ch.hevs.businessobject;

import jakarta.persistence.Entity;

@Entity
public class Comic extends Book {

    private boolean isColorized;

    public boolean isColorized() {
        return isColorized;
    }

    public void setColorized(boolean isColorized) {
        this.isColorized = isColorized;
    }
}