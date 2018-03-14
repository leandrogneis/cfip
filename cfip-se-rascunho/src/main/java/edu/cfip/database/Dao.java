package edu.cfip.database;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Dao {
	private EntityManager em;
	public Dao() {
		em=Persistence.createEntityManagerFactory("PU_CFIP").createEntityManager();
		System.out.println("EntityManager criado");
	}
	public static void main(String[] args) {
		new Dao();
	}
}
