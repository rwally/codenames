package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.IDAOCase;
import fr.formation.DAO.IDAOGrille;
import fr.formation.DAO.IDAOMot;
import fr.formation.DAO.Hibernate.DAOCaseHibernate;
import fr.formation.DAO.Hibernate.DAOGrilleHibernate;
import fr.formation.DAO.Hibernate.DAOHibernate;
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
		 * Menu monMenu = new Menu();
		 * 
		 * monMenu.menuPrincipal();
		 */
		/*
		 * Menu menu = new Menu(); menu.connection();
		 */

		System.out.println("test");

		IDAOMot daoMot = new DAOMotHibernate();
		try {
			for (Mot m : daoMot.findAll()) {
				System.out.println(m.getLibelle());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TEST CREATION GRILLE
	
		int k=0;
		Grille grilleTest = creationGrille();
		
		for(int i=0;i<grilleTest.getDifficulte().getValeur();i++) {
			System.out.println();
			for(int j=0;j<grilleTest.getDifficulte().getValeur();j++) {
				System.out.println(grilleTest.getCases().get(k).getMot().getLibelle()+"\t");
				k++;
			}
		}

	}
	
	
	public static Grille creationGrille() {
		
		//Init de la liste de mots
		IDAOMot daoMot = new DAOMotHibernate();
		List<Mot> listeMots = new ArrayList<Mot>();
		
		//Init de la liste de cases
		IDAOCase daoCase = new DAOCaseHibernate();
		Case maCase=new Case();
		List<Case> cases = new ArrayList<Case>();
		
		//Init de la grille
		IDAOGrille daoGrille = new DAOGrilleHibernate(); 
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.facile);
		
		//Creation de la liste de mots
		try {
			listeMots=daoMot.creerListeMots(grille);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Creation de la liste de cases avec les mots/couleurs
		cases=daoCase.creerListeCase(listeMots,grille);
		
		//Creation de la grille avec les cases remplies
		grille=daoGrille.creerGrille(cases);

		return grille;
		
	}
	
	public static void afficherGrille(Grille maGrille) {
		
	}
	
	
}
