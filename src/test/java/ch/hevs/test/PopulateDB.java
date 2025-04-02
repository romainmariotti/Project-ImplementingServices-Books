package ch.hevs.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.hevs.businessobject.Book;
import ch.hevs.businessobject.Category;
import ch.hevs.businessobject.Comic;
import ch.hevs.businessobject.Magazine;
import ch.hevs.businessobject.Novel;
import ch.hevs.businessobject.Writer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;

public class PopulateDB extends TestCase {

	@Test
	public void test() throws SQLException, ClassNotFoundException {
		
		EntityTransaction tx = null;
		try {
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU_unitTest");
			EntityManager em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();
			
			
			Category c1 = new Category();
			c1.setName("Fantastique");
			
			Writer w1 = new Writer();
			w1.setFirstname("Jean");
			w1.setLastname("Dupont");
			
			Magazine m1 = new Magazine();
			m1.setTitle("Le Petit Prince");
			//m1.setWriter(w1);
			//m1.setCategory(c1);
			
			
			
			c1.addBook(m1);
			w1.addBook(m1);
			
			
			
			em.persist(m1);
			em.persist(c1);
			em.persist(w1);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
