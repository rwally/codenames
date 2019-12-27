package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.IDAOMot;
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
		
		// A MODIFIER REAZ
	
//		Grille grilleTest = creationGrille();
//		Case[][] caseTest = grilleTest.getGrille();
//		for(int i=0;i<grilleTest.getDifficulte().getValeur();i++) {
//			System.out.println();
//			for(int j=0;j<grilleTest.getDifficulte().getValeur();j++) {
//				System.out.println(caseTest[i][j].getMot().getLibelle()+"\t");
//			}
//		}

	}

		return grille;
		
	}
	*/
	
}
