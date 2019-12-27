package fr.formation.DAO.Hibernate;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import fr.formation.DAO.IDAOMot;
import fr.formation.model.Grille;
import fr.formation.model.Mot;


public class DAOMotHibernate extends DAOHibernate implements IDAOMot{

	
	@Override
	public List<Mot> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return em.createQuery("select m from Mot m", Mot.class).getResultList();
	}

	@Override
	public Mot findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return em.find(Mot.class, id);
	}

	@Override
	public Mot save(Mot entity) throws SQLException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			if(entity.getId()==0) {
				em.persist(entity);
			}
			else {
				entity=em.merge(entity);
			}
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		
		return entity;
	}

	@Override
	public void delete(Mot entity) throws SQLException {
		// TODO Auto-generated method stub
		try {
			tx.begin();
			em.remove(em.merge(entity));
			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Mot motASupprimer= new Mot();
		motASupprimer.setId(id);
		delete(motASupprimer);
		
	}

	@Override
	public Optional<Mot> findByNom(String nom) {
		// TODO Auto-generated method stub
		TypedQuery<Mot> myQuery = em.createQuery("select m from Mot m where m.libelle = :nom", Mot.class);
		myQuery.setParameter("nom", nom);
		try {
			
			return Optional.of(myQuery.getSingleResult());
			
		}catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public List<Mot> creerListeMots(Grille maGrille){
		int taille = maGrille.getDifficulte().getValeur()*maGrille.getDifficulte().getValeur();
		TypedQuery<Mot> myQuery = em.createQuery("select m from Mot m limit :taille", Mot.class);
		myQuery.setParameter("taille", taille);

		return myQuery.getResultList();
	}

}
