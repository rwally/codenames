package fr.formation.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.formation.DAO.DAOMotSQL;

public class Partie {
	private List<Case> plateau;
	private List<Participation> joueurs;
	
	public Grille creationGrille() {
		// CREATION DE LA GRILLE

		// Init de la liste de mots
		DAOMotSQL daoMot = new DAOMotSQL();
		ArrayList<Mot> listeMots = new ArrayList<Mot>();

		// Init de la liste de cases
		Case maCase = new Case();
		ArrayList<Case> cases = new ArrayList<Case>();

		// Init de la grille
		Grille grille = new Grille();
		grille.setDifficulte(1);
		grille.setGrille(grille.getDifficulte());
		Case[][] maGrille = grille.getGrille();

		// Creation de la liste de mots
		try {
			listeMots = daoMot.creerListeMots();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Creation de la liste de cases avec les mots/couleurs
		cases = maCase.creerListeCases(listeMots);
		// Creation de la grille avec les cases remplies
		maGrille = grille.creerGrille(cases);
		// Affichage de la grille
		grille.afficherGrille(maGrille);

		return grille;
	}
}
