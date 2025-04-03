package ch.hevs.test;

import org.junit.Test;
import ch.hevs.businessobject.*;
import jakarta.persistence.*;
import junit.framework.TestCase;

public class PopulateDB extends TestCase {

    @Test
    public void test() {
        EntityTransaction tx = null;
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU_unitTest");
            EntityManager em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Create categories
            Category c1 = new Category(); c1.setName("Science Fiction");
            Category c2 = new Category(); c2.setName("Fantasy");
            Category c3 = new Category(); c3.setName("Mystery");
            Category c4 = new Category(); c4.setName("Biography");
            Category c5 = new Category(); c5.setName("History");

            // Create writers
            Writer w1 = new Writer(); w1.setFirstname("Isaac"); w1.setLastname("Asimov");
            Writer w2 = new Writer(); w2.setFirstname("J.K."); w2.setLastname("Rowling");
            Writer w3 = new Writer(); w3.setFirstname("Agatha"); w3.setLastname("Christie");
            Writer w4 = new Writer(); w4.setFirstname("Walter"); w4.setLastname("Isaacson");
            Writer w5 = new Writer(); w5.setFirstname("Yuval Noah"); w5.setLastname("Harari");
            Writer w6 = new Writer(); w6.setFirstname("George"); w6.setLastname("Orwell");
            Writer w7 = new Writer(); w7.setFirstname("J.R.R."); w7.setLastname("Tolkien");

            // Create books
            Book b1 = new Novel(); b1.setTitle("Foundation"); b1.setWriter(w1); b1.setCategory(c1);
            Book b2 = new Novel(); b2.setTitle("Harry Potter"); b2.setWriter(w2); b2.setCategory(c2);
            Book b3 = new Novel(); b3.setTitle("Murder on the Orient Express"); b3.setWriter(w3); b3.setCategory(c3);
            Book b4 = new Novel(); b4.setTitle("Steve Jobs"); b4.setWriter(w4); b4.setCategory(c4);
            Book b5 = new Novel(); b5.setTitle("Sapiens"); b5.setWriter(w5); b5.setCategory(c5);
            Book b6 = new Novel(); b6.setTitle("1984"); b6.setWriter(w6); b6.setCategory(c1);
            Book b7 = new Novel(); b7.setTitle("The Hobbit"); b7.setWriter(w7); b7.setCategory(c2);
            Book b8 = new Magazine(); b8.setTitle("National Geographic"); b8.setWriter(w5); b8.setCategory(c5);
            Book b9 = new Comic(); b9.setTitle("Batman"); b9.setWriter(w3); b9.setCategory(c3);
            Book b10 = new Magazine(); b10.setTitle("Time"); b10.setWriter(w4); b10.setCategory(c4);

            // Persist data
            em.persist(c1); em.persist(c2); em.persist(c3); em.persist(c4); em.persist(c5);
            em.persist(w1); em.persist(w2); em.persist(w3); em.persist(w4); em.persist(w5); em.persist(w6); em.persist(w7);
            em.persist(b1); em.persist(b2); em.persist(b3); em.persist(b4); em.persist(b5);
            em.persist(b6); em.persist(b7); em.persist(b8); em.persist(b9); em.persist(b10);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
        }
    }
}
