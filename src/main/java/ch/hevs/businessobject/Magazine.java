package ch.hevs.businessobject;

import jakarta.persistence.Entity;

@Entity
public class Magazine extends Book {

    private String releaseFrequency;

    public String getReleaseFrequency() {
        return releaseFrequency;
    }

    public void setReleaseFrequency(String releaseFrequency) {
        this.releaseFrequency = releaseFrequency;
    }
}