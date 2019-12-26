package fr.formation.DAO.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class DAOHibernate {
	
//	protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmusicUnit"); 
//	protected EntityManager em = emf.createEntityManager();
	
	
	protected static EntityManagerFactory emf = null;
	protected EntityManager em = null;
	protected EntityTransaction tx = null;
	
	public DAOHibernate() {
		if(emf == null) {//Cr�ation de EMF si non existant
			emf = Persistence.createEntityManagerFactory("CodeNameUnit");
		}
		
		if(emf != null) {//Cr�ation de EM pour chaque instance
			this.em = emf.createEntityManager();
			this.tx=em.getTransaction();
		}
	}
	
	public static void close() {
		if(emf != null) {
			emf.close();
			emf=null;
		}
	}

}
