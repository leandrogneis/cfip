package edu.cfip.database;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import edu.cfip.model.Conta;
import edu.cfip.model.Natureza;

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
		c.setNome("CONTA");
		Natureza n = new Natureza();
		n.setNome("NATUREZA");
		em.getTransaction().begin();
		em.persist(c);
		em.persist(n);
		em.getTransaction().commit();
		factory.close();
	}
}
