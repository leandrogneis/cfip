package edu.cfip.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.cfip.model.Conta;

public class Dao {
	public Dao() {
		
	}
	public void incluir(Object entidade) {
		
	}
	public static void main(String[] args) {
		EntityManagerFactory factory =Persistence.createEntityManagerFactory("PU_CFIP");
		EntityManager em=factory.createEntityManager();
		System.out.println("EntityManager criado");
		Conta c = new Conta();
		c.setNome("NOVO");
		em.getTransaction().begin();
		em.persist(c);
		em.getTransaction().commit();
		factory.close();
	}
}
