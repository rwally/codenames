package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.formation.DAO.IDAOCase;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public class DAOCaseHibernate extends DAOHibernate implements IDAOCase{

	@Override
	public List<Case> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return em.createQuery("select c from Case c", Case.class).getResultList();
	
	}

	@Override
	public Case findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return em.find(Case.class, id);
	}

	@Override
	public Case save(Case entity) throws SQLException {
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
	public void delete(Case entity) throws SQLException {
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
		Case caseASupprimer= new Case();
		caseASupprimer.setId(id);
		delete(caseASupprimer);
		
	}

	@Override
	public Optional<Case> findByNom(String nom) {
		// TODO Auto-generated method stub
		TypedQuery<Case> myQuery = em.createQuery("select c from Case c where c.libelle = :nom", Case.class);
		myQuery.setParameter("nom", nom);
		try {
			
			return Optional.of(myQuery.getSingleResult());
			
		}catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}


	
	


}
