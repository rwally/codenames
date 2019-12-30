package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.List;

import fr.formation.DAO.IDAOEquipe;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;

public class DAOEquipeHibernate extends DAOHibernate implements IDAOEquipe{

	@Override
	public List<Equipe> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return  em
				.createQuery("select e from Equipe e", Equipe.class)
				.getResultList();
	}

	@Override
	public Equipe findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return em.find(Equipe.class, id);
	}

	@Override
	public Equipe save(Equipe entity) throws SQLException {
		try {
			if(entity.getId() == 0) {//AJOUT
				tx.begin(); //Démarrage TX
				em.persist(entity);
				tx.commit();
			}
			else {//UPDATE
				tx.begin(); //Démarrage TX
				entity = em.merge(entity);
				tx.commit();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback(); //Problème => annulation
		}
		
		return entity;
	}

	@Override
	public void delete(Equipe entity) throws SQLException {
		// TODO Auto-generated method stub
		tx.begin();
		try {
			em.remove(em.merge(entity));
			tx.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			tx.rollback(); //Problème => annulation
		}
		
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Equipe equipeASupprimer = new Equipe();
		equipeASupprimer.setId(id);
		delete(equipeASupprimer);
		
	}

	@Override
	public Equipe findByNom(String nom) {
		// TODO Auto-generated method stub
		try {	
			return  em
					.createQuery("select e from Equipe e where e.nom = :nom", Equipe.class)
					.setParameter("nom", nom)
					.getSingleResult();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
