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

	@Override
	public List<Case> creerListeCase(List<Mot> listeMots, Grille maGrille) {
		// TODO Auto-generated method stub
		ArrayList<Case> cases = new ArrayList<Case>();
		int taille = 0, r = 0,b = 0,w = 0; //rouge bleu w=blanc
		
		switch(maGrille.getDifficulte().getValeur()) {
			case 4: taille=16;
					r=6;
					b=11;
					w=16;
					break;
					
			case 5: taille=25;
					r=9;
					b=17;
					w=24;
					break;
					
			case 6 :taille=36;
					r=12;
					b=23;
					w=33;
					break;
		} 
		
		
		for(int i = 0; i < taille; i++) {
				Case maCase = new Case();
				maCase.setMot(listeMots.get(0));
				listeMots.remove(0);
				if(i<r) {
					maCase.setCouleur(Couleur.rouge);
				}else if(i<b) {
					maCase.setCouleur(Couleur.bleu);
				}else if(i<w) {
					maCase.setCouleur(Couleur.blanc);
				}else {
					maCase.setCouleur(Couleur.noir);
				}			
				
				try {
					save(maCase);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cases.add(maCase);
		}
		
		Collections.shuffle(cases);
		
		return cases;
		
	}
	
	


}
