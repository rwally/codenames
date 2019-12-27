package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

public class DAOUtilisateurHibernate extends DAOHibernate implements IDAOUtilisateur {

	@Override
	public List<Utilisateur> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return  em
				.createQuery("select u from Utilisateur u", Utilisateur.class)
				.getResultList();
	}

	@Override
	public Utilisateur findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return em.find(Utilisateur.class, id);
	}
	
	public Optional<Utilisateur> findByNom(String nom) {
		// TODO Auto-generated method stub
		try {
//			
//			
			return  Optional.of(em
					.createQuery("select u from Utilisateur u where u.nom = :nom", Utilisateur.class)
					.setParameter("nom", nom)
					.getSingleResult());
		}
		catch (Exception e){
			e.printStackTrace();
			return Optional.empty();
		}
		
	}
	
	public Utilisateur findByUsername(String username) {
		// TODO Auto-generated method stub
		try {
//			
//			
			return  em
					.createQuery("select u from Utilisateur u where u.username = :username", Utilisateur.class)
					.setParameter("username", username)
					.getSingleResult();
		}
		catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Utilisateur save(Utilisateur entity) throws SQLException {
		// TODO Auto-generated method stub
		try {
			if(entity.getId() == 0) {//AJOUT
				em.getTransaction().begin(); //Démarrage TX
				em.persist(entity);
				em.getTransaction().commit();
			}
			else {//UPDATE
				em.getTransaction().begin(); //Démarrage TX
				entity =  em.merge(entity);
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
	public void delete(Utilisateur entity) throws SQLException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		Utilisateur utilisateurASupprimer = new Joueur();
		utilisateurASupprimer.setId(id);
		delete(utilisateurASupprimer);
	}

	
}
