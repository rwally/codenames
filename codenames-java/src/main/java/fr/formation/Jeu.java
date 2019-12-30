package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.formation.DAO.IDAOCase;
import fr.formation.DAO.IDAOGrille;
import fr.formation.DAO.IDAOMot;
import fr.formation.DAO.Hibernate.DAOCaseHibernate;
import fr.formation.DAO.Hibernate.DAOGrilleHibernate;
import fr.formation.DAO.Hibernate.DAOMotHibernate;
import fr.formation.model.Case;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Role;

public class Jeu {
	
	public static  Grille creationGrille() {
		// Init de la liste de mots
		IDAOMot daoMot = new DAOMotHibernate();
		List<Mot> listeMots = new ArrayList<Mot>();

		// Init de la liste de cases
		IDAOCase daoCase = new DAOCaseHibernate();
		List<Case> cases = new ArrayList<Case>();

		// Init de la grille
		IDAOGrille daoGrille = new DAOGrilleHibernate();
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.moyen);

		// Creation de la liste de mots
		try {
			listeMots = daoMot.creerListeMots(grille);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Creation de la liste de cases avec les mots/couleurs
		cases = daoCase.creerListeCase(listeMots, grille);

		// affection de la liste de cases a la grille et inverse
		grille.setCases(cases);
		try {
			daoGrille.save(grille);
			for (Case c : cases) {
				c.setGrille(grille);
				daoCase.save(c);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grille;

	}

	public static void afficherGrille(Grille grille, Participation participant) {

		int k = 0;
		for (int i = 0; i < grille.getDifficulte().getValeur(); i++) {
			System.out.println();
			for (int j = 0; j < grille.getDifficulte().getValeur(); j++) {
				System.out.print(grille.getCases().get(k).getMot().getLibelle());
				if (grille.getCases().get(k).isTrouver() || participant.getRole() == Role.master) {
					System.out.print(" " + grille.getCases().get(k).getCouleur() + "\t");
				} else {
					System.out.print("\t");
				}
				k++;
			}
		}
	}

}
