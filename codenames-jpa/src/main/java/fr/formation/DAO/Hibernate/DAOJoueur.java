package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.List;

import fr.formation.DAO.IDAOJoueur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

public class DAOJoueur extends DAOHibernate implements IDAOJoueur {

	@Override
	public List<Joueur> findAll() throws SQLException {
		return  em
				.createQuery("select j from Joueur j", Joueur.class)
				.getResultList();
	}

	@Override
	public Joueur findById(Integer id) throws SQLException {
		return em.find(Joueur.class, id);
	}

	@Override
	public Joueur save(Joueur entity) throws SQLException {
		try {
			if(entity.getId() == 0) {//AJOUT
				em.getTransaction().begin(); //Démarrage TX
				em.persist(entity);
				em.getTransaction().commit();
			}
			else {//UPDATE
				em.getTransaction().begin(); //Démarrage TX
				entity = em.merge(entity);
				em.getTransaction().commit();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback(); //Problème => annulation
		}
		
		return entity;
	}

	@Override
	public void delete(Joueur entity) throws SQLException {
		em.getTransaction().begin();
		try {
			em.remove(em.merge(entity));
			em.getTransaction().commit();
		}
		catch(Exception e){
			e.printStackTrace();
			em.getTransaction().rollback(); //Problème => annulation
		}		
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		Joueur joueurASupprimer = new Joueur();
		joueurASupprimer.setId(id);
		delete(joueurASupprimer);
		
	}

	@Override
	public Joueur findByUsername(String username) {
		try {	
			return  em
					.createQuery("select j from Joueur j where j.username = :username", Joueur.class)
					.setParameter("username", username)
					.getSingleResult();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
