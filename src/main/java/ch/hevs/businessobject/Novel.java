package ch.hevs.businessobject;

import jakarta.persistence.Entity;

@Entity
public class Novel extends Book {

    private boolean isPocketSize;

    public boolean isPocketSize() {
        return isPocketSize;
    }

    public void setPocketSize(boolean isPocketSize) {
        this.isPocketSize = isPocketSize;
    }
}