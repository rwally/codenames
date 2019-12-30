package fr.formation;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import fr.formation.DAO.Hibernate.DAOHibernate;
import fr.formation.model.Grille;
import fr.formation.model.Participation;
import fr.formation.model.Partie;

public class Application {

	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		boolean rougeHasMaster = false, bleuHasMaster = false;
		Menu monMenu = new Menu();
		List<Participation> lesParticipants = monMenu.menuPrincipal();
		
		Partie unePartie = new Partie();
		unePartie.setJoueurs(lesParticipants);
		
		for(Participation p : lesParticipants) {
			if(p.getJoueurs().getEquipe().getNom()=="Rouge") {
				if(!rougeHasMaster) {
					unePartie.setMaster(p.getJoueurs().getEquipe());
					rougeHasMaster=true;
				}
			} else if(p.getJoueurs().getEquipe().getNom()=="Bleu") {
				if(!bleuHasMaster) {
					unePartie.setMaster(p.getJoueurs().getEquipe());
					bleuHasMaster=true;
				}
			}
		}
		
		unePartie.AfficheJoueurs(lesParticipants);
		
		Grille nouvelleGrille = new Grille();
		nouvelleGrille = Jeu.creationGrille();
		unePartie.setGrille(nouvelleGrille);
		Jeu.afficherGrille(nouvelleGrille, lesParticipants.get(0));

		DAOHibernate.close();

	}


}
