package br.com.livingrio.tests;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.livingrio.model.Region;

public class InsertTest {

	
	public static void main(String[] args) {
		EntityManagerFactory f = Persistence.createEntityManagerFactory("livingrio");
		EntityManager em = f.createEntityManager();
		
		em.getTransaction().begin();
		
		Region region = new Region();
	//	region.setName("Sul");
		
		em.persist(region);
		
		em.getTransaction().commit();
		
		em.close();
		
	}
	
}
