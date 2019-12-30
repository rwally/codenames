package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.Hibernate.DAOHibernate;
import fr.formation.model.Equipe;
import fr.formation.model.Grille;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;

public class Application {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		// ************* Pour les tests ***************************
		List<Participation> lesParticipants = new ArrayList<Participation>();
		
		Joueur adeline = new Joueur(0, "Sinapayen", "Adeline", "Adeline", "09041993");
		Participation adel = new Participation(Role.agent, adeline);
		adeline.setSaParticipation(adel);
		lesParticipants.add(adel);
		
		Joueur reaz = new Joueur(0, "Wally", "Reaz", "Reaz", "123456");
		Participation pReaz = new Participation(Role.agent, reaz);
		reaz.setSaParticipation(pReaz);
		lesParticipants.add(pReaz);
		
		Joueur camille = new Joueur(0, "Julio", "Camille", "Camille", "123456");
		Participation pCamille = new Participation(Role.agent, camille);
		camille.setSaParticipation(pCamille);
		lesParticipants.add(pCamille);
		
		Joueur adrian = new Joueur(0, "Oliva", "Adrian", "Adrain", "123456");
		Participation pAdrian = new Participation(Role.agent, adrian);
		adrian.setSaParticipation(pAdrian);
		lesParticipants.add(pAdrian);
		
		Equipe equipeBleue = new Equipe();
		equipeBleue.AjouterMembre(adrian);
		equipeBleue.AjouterMembre(camille);
		Equipe equipeRouge = new Equipe();
		equipeRouge.AjouterMembre(reaz);
		equipeRouge.AjouterMembre(adeline);
		
//		****************************************************************************************

//		Menu monMenu = new Menu();
//		List<Participation> lesParticipants = monMenu.menuPrincipal();
		
//		Equipe equipeBleue = monMenu.getEquipeBleue();
//		Equipe equipeRouge = monMenu.getEquipeRouge();
		
		Partie unePartie = new Partie();
		unePartie.setJoueurs(lesParticipants);
		
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
