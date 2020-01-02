package fr.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.model.Case;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Role;

@Service
public class Jeu {
	
	@Autowired
	IDAOMot daoMot;
	
	@Autowired
	IDAOCase daoCase;
	
	@Autowired
	IDAOGrille daoGrille;
	
	public static  Grille creationGrille() {
		// Init de la liste de mots
		List<Mot> listeMots = new ArrayList<Mot>();

		// Init de la liste de cases
		List<Case> cases = new ArrayList<Case>();

		// Init de la grille
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