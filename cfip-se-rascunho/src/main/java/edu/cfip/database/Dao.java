package edu.cfip.database;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import edu.cfip.model.Conta;

public class Dao {
	private EntityManager em;
	public Dao() {
		em=Persistence.createEntityManagerFactory("PU_CFIP").createEntityManager();
		System.out.println("EntityManager criado");
	}
	public void incluir(Object entidade) {
		em.getTransaction().begin();
		em.persist(entidade);
		em.getTransaction().commit();
	}
	public static void main(String[] args) {
		Dao d = new Dao();
		Conta c = new Conta();
		c.setNome("TESTE");
		d.incluir(c);
		System.out.println("Salvou");
	}
}
