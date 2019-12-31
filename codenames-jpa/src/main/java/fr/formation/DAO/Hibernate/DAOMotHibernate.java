package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import fr.formation.DAO.IDAOMot;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public class DAOMotHibernate extends DAOHibernate implements IDAOMot {

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
			if (entity.getId() == 0) {
				em.persist(entity);
			} else {
				entity = em.merge(entity);
			}
			tx.commit();
		} catch (Exception e) {
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
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}

	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Mot motASupprimer = new Mot();
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

		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	@Override
	public List<Mot> creerListeMots(Grille maGrille) {

		List<Mot> mots = new ArrayList<Mot>();
		int taille = maGrille.getDifficulte().getValeur() * maGrille.getDifficulte().getValeur();
		boolean motsOK = false;

		while (!motsOK) {
			motsOK = true;
			try {
				int compteur = 0;
				List <Mot> tousLesMots = this.findAll();
				for (Mot m : tousLesMots) {
					if (!m.isUsed()) {
						compteur++;
					}
				}
					
					if (compteur >= taille) {
						List<Mot> motsNotUsed = em.createQuery("select m from Mot m where m.used = 0", Mot.class).getResultList();
						
						List<Integer> nombresRandom = new Random()
								.ints(1,motsNotUsed.size())
								.distinct()
								.limit(taille)
								.boxed()
								.collect(Collectors.toList());
					
					for (int i = 0; i < taille; i++) {
								Mot mot = motsNotUsed.get((nombresRandom.get(i)));
								mots.add(mot);
								mot.setUsed(true);
								save(mot);
					}
				} 
				else {
					motsOK = false;
					for (Mot m : tousLesMots) {
						m.setUsed(false);
						save(m);
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

//		for (Mot m : mots) {
//			try {
//				save(m);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		return mots;
	}

}
