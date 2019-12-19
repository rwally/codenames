package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import fr.formation.DAO.DAOMotSQL;
import fr.formation.model.Case;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;
import fr.formation.model.Utilisateur;

public class Application {
	
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
	
		/*
	 Menu monMenu = new Menu();
	 
	 monMenu.menuPrincipal();
	 */
		/*
		Menu menu = new Menu();
		menu.connection();*/
	 
		//MODIFIER
		
		//CREATION DE LA GRILLE

		//Init de la liste de mots
		DAOMotSQL daoMot = new DAOMotSQL();
		ArrayList<Mot> listeMots = new ArrayList<Mot>();
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Creation de la liste de cases avec les mots/couleurs
		cases=maCase.creerListeCases(listeMots,grille);
		
		//Affectation de l'attribut grille dans les instances de classe Case
		for(Case uneCase : cases) {
			uneCase.setGrille(grille); //Inutile pour l'instant mais peut servir plus tard
		}
		
	
		//Creation de la grille avec les cases remplies
		maGrille=grille.creerGrille(cases);
		
		

	
		
		//Creation d'un participant
		Participation participant = new Participation(Role.master);
		//Init de la liste de participants
		ArrayList<Participation> mesParticipants = new ArrayList<Participation>();
		//Creation de la liste des participants
		mesParticipants.add(participant);
		//Creation de la partie
		Partie partie = new Partie(grille, mesParticipants);
		//Affichage de la grille avec en parametre le numero du participant (index de l'arraylist mesParticipants)
		partie.afficherGrille(participant);
		
		
	 
	}

}
