package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import fr.formation.DAO.IDAOGrille;
import fr.formation.model.Case;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public class DAOGrilleHibernate extends DAOHibernate implements IDAOGrille{

	@Override
	public List<Grille> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return em.createQuery("select g from Grille g", Grille.class).getResultList();
	}

	@Override
	public Grille findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return em.find(Grille.class, id);
	}

	@Override
	public Grille save(Grille entity) throws SQLException {
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
	public void delete(Grille entity) throws SQLException {
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
		Grille grilleASupprimer= new Grille();
		grilleASupprimer.setId(id);
		delete(grilleASupprimer);
		
	}

	@Override
	public Optional<Grille> findByNom(String nom) throws SQLException {
		// TODO Auto-generated method stub
		TypedQuery<Grille> myQuery = em.createQuery("select g from Grille g where g.libelle = :nom", Grille.class);
		myQuery.setParameter("nom", nom);
		try {
			
			return Optional.of(myQuery.getSingleResult());
			
		}catch(Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public Grille creerGrille(ArrayList<Case> cases) {
		// TODO Auto-generated method stub
		Grille grille = new Grille();
		Collections.shuffle(cases);
		grille.setCases(cases);
		try {
			save(grille);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return grille;
	}

}
