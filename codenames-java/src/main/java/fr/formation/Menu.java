package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.DAO.Hibernate.DAOUtilisateurHibernate;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.model.Utilisateur;

public class Menu {
	public static IDAOUtilisateur daoUtilisateur = new DAOUtilisateurHibernate();

	

	public Optional<Joueur> connection() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();
		Optional<Joueur> joueur = null;

		Boolean mdpOk = false;

		while (!mdpOk) {
			System.out.println("1. Connection 2. Inscription");

			int choix = Application.sc.nextInt();

			if (choix == 1) {
				Application.sc.nextLine();
				System.out.println("Veuillez entrer votre login");
				String nom = Application.sc.nextLine();
				System.out.println("Veuillez entrer votre mot de passe");
				String mdp = Application.sc.nextLine();

				for (Utilisateur u : listeUtilisateurs) {
					if (u.getUsername().equals(nom) && u.getPassword().contentEquals(mdp)) {
						// CONNEXION
						mdpOk = true;
						joueur = daoUtilisateur.findByUsername(nom);
						
					}
				}

				if (!mdpOk) {
					System.out.println("Votre login ou votre mot de passe est incorrect.");
				}

			} else if (choix == 2) {
				mdpOk = true;
				Application.sc.nextLine();
				joueur = inscription();
			}
		}
		return joueur;
	}

	public Optional<Joueur> inscription() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();
		boolean usernameOk = false;
		String nom = null;
		String prenom = null;

		while (!usernameOk) {
			System.out.println("Veuillez entrer votre nom");
			nom = Application.sc.nextLine();
			if (!nom.equals("")) {
				usernameOk = true;
			}
		}
		
		usernameOk = false;
		while (!usernameOk) {
			System.out.println("Entrez votre prénom");
			prenom = Application.sc.nextLine();
			if (!prenom.equals("")) {
				usernameOk = true;
			}
		}
		
		usernameOk = false;
		String username = null;
		boolean loginOK = false;

		// Vérification de l'username unique
		while (!usernameOk) {
			usernameOk = true;
			while (!loginOK) {
				System.out.println("Entrez votre login");
				username = Application.sc.nextLine();
				if (!username.equals("")) {
					loginOK = true;
				}
				else {
					System.out.println("Votre login ne peut être nul");
				}
			}
			for (Utilisateur u : listeUtilisateurs) {
				if (u.getUsername().equals(username)) {
					usernameOk = false;
					System.out.println("Ce login existe déjà");
				}
			}
		}

		boolean mdpOK = false;
		String password = null;

		// Vérification de la bonne saise du mot de passe
		while (!mdpOK) {
			System.out.println("Entrez votre mot de passe");
			password = Application.sc.nextLine();
			System.out.println("Entrez à nouveau votre mot de passe");
			String password2 = Application.sc.nextLine();

			if (password.equals(password2)) {
				mdpOK = true;
			} else {
				System.out.println("Les deux mots de passe ne correspondent pas");
			}
		}

		Utilisateur utilisateur = new Joueur(0, nom, prenom, username, password);
		daoUtilisateur.save(utilisateur);
		
		Optional<Joueur> joueur = daoUtilisateur.findByUsername(username);
		return joueur;
	}
	
	
	public void menuPrincipal() {
		System.out.println("-----------------");
		System.out.println("Bienvenue dans Code Names");
		System.out.println("-----------------");

		
		System.out.println("-----------------");
		System.out.println("MENU PRINCIPAL");
		System.out.println("-----------------");
		
		System.out.println("Saisir 1 pour commencer une nouvelle partie");
		
		
		if( Application.sc.nextInt() == 1) {
			
			System.out.println("Saisir 1 pour choisir  l'Equipe Rouge");
			System.out.println("Saisir 2 pour choisir l'Equipe Bleue)");
//			System.out.println("Saisir 3 pour être spectateur");
			
			int a  = Application.sc.nextInt();
			Utilisateur monJoueur = new Joueur();
			monJoueur = daoUtilisateur.findById(0);
			
			switch (a) {
			case 1 :
				System.out.println("Vous êtes dans l'Equipe Bleue !");
				Equipe.AjouterJoueurBleu(monJoueur);
				monJoueur.setEquipe("Bleue");
				break;
			case 2 :
				System.out.println("Vous êtes dans l'Equipe Rouge !");
				Equipe.AjouterJoueurRouge(monJoueur);
				monJoueur.setEquipe("Rouge");
				
			break;
			}
			Participation maParticipation = new Participation(Role.agent, monJoueur);
			
			System.out.println();
			System.out.println("-----------------");
			System.out.println("Nouvelle Partie");
			System.out.println("-----------------");
			
			
		}
		
		
	}
}
