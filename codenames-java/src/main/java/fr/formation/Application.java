package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.IDAOMot;
import fr.formation.DAO.Hibernate.DAOMotHibernate;
import fr.formation.model.Case;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public class Application {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		/*
	 Menu monMenu = new Menu();
	 
	 monMenu.menuPrincipal();
	 */
		/*
		Menu menu = new Menu();
		menu.connection();*/
	 
		/* TEST CREATION GRILLE
		   
		Grille grilleTest = creationGrille();
		Case[][] caseTest = grilleTest.getGrille();
		for(int i=0;i<grilleTest.getDifficulte().getValeur();i++) {
			System.out.println();
			for(int j=0;j<grilleTest.getDifficulte().getValeur();j++) {
				System.out.println(caseTest[i][j].getMot().getLibelle()+"\t");
			}
		}
		*/
		

		
	 
	}
	
	public static Grille creationGrille() {
		
		//Init de la liste de mots
		IDAOMot daoMot = new DAOMotHibernate();
		List<Mot> listeMots = new ArrayList<Mot>();
		
		//Init de la liste de cases
		Case maCase=new Case();
		ArrayList<Case> cases = new ArrayList<Case>();
		
		//Init de la grille
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.facile);
		grille.setGrille(grille.getDifficulte().getValeur());
		Case[][] maGrille = grille.getGrille();
		
		//Creation de la liste de mots
		try {
			listeMots=daoMot.creerListeMots(grille);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Creation de la liste de cases avec les mots/couleurs
		cases=maCase.creerListeCases(listeMots,grille);
		
		//Affectation de l'attribut grille dans les instances de classe Case
		for(Case uneCase : cases) {
			uneCase.setGrille(grille); //Inutile pour l'instant mais peut servir plus tard on sait jamais t'a vu
		}
		
		//Creation de la grille avec les cases remplies
		maGrille=grille.creerGrille(cases);

		return grille;
		
	}

}
