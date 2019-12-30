package fr.formation;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.Hibernate.DAOHibernate;
import fr.formation.model.Equipe;
import fr.formation.model.Grille;
import fr.formation.model.Participation;
import fr.formation.model.Partie;

public class Application {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		Menu monMenu = new Menu();
		List<Participation> lesParticipants = monMenu.menuPrincipal();
		
		Partie unePartie = new Partie();
		unePartie.setJoueurs(lesParticipants);
		
		Equipe equipeBleue = monMenu.getEquipeBleue();
		Equipe equipeRouge = monMenu.getEquipeRouge();
		
		// Définition des rôles
		unePartie.setMaster(equipeBleue);
		unePartie.setMaster(equipeRouge);
		
		// Affichage de la grille pour la partie
		Grille nouvelleGrille = new Grille();
		nouvelleGrille = Jeu.creationGrille();
		unePartie.setGrille(nouvelleGrille);
		Jeu.afficherGrille(nouvelleGrille, lesParticipants.get(0));

		DAOHibernate.close();

	}


}
