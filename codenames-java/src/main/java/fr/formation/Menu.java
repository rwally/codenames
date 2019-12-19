package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.formation.model.Partie;
import fr.formation.model.Utilisateur;
import fr.formation.DAO.DAOUtilisateurSQL;
import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;

public class Menu {
	public static IDAOUtilisateur daoUtilisateur = new DAOUtilisateurSQL();

//		public void menuPrincipal() {
//			System.out.println("-----------------");
//			System.out.println("Bienvenue dans Code Names");
//			System.out.println("-----------------");
//			System.out.println("Page de connexion");
//			 
//			Scanner sc = new Scanner(System.in);
//			 
//			System.out.println("Entrez Votre Nom d'Utilisateur :");
//			String nomUtilisateur = sc.nextLine();
//			 
//			System.out.println("Entrez Votre mot de passe :");
//			String motPasse = sc.nextLine();
//			
//			DAOUtilisateurSQL.findByNom(nomUtilisateur, motPasse);
//			
//			
//			 
//			 
//			DAOUtilisateur user = new DAOUtilisateurSQL();
//			 
//			if(user.findAll() == null) {
//				System.out.println("Inscrivez-vous !");
//				 
//				System.out.println("Entrez Votre Nom :");
//				String nouveauNom = sc.nextLine();
//				 
//				System.out.println("Entrez Votre Prénom :");
//				String nouveauPrenom = sc.nextLine();
//				 
//				System.out.println("Entrez un Nom d'Utilisateur :");
//				String nouveauUtilisateur = sc.nextLine();
//				 
//				System.out.println("Entrez un mot de passe :");
//				String nouveauPasse = sc.nextLine();
//				 
//				Utilisateur newUser = new Utilisateur();
//				 
//				newUser.setNom(nouveauNom);
//				newUser.setPrenom(nouveauPrenom);
//				newUser.setUsername(nouveauUtilisateur);
//				newUser.setPassword(nouveauPasse);
//				 
//				user.save(newUser);
//			}
//			
//			System.out.println("-----------------");
//			System.out.println("MENU PRINCIPAL");
//			System.out.println("-----------------");
//			
//			System.out.println("Saisir 1 pour commencer une nouvelle partie");
//			
//			
//			if( sc.nextInt() == 1) {
//				
//				System.out.println("Saisir 1 pour choisir  l'Equipe Rouge");
//				System.out.println("Saisir 2 pour choisir l'Equipe Bleue)");
//				System.out.println("Saisir 3 pour être spectateur");
//				
//				int a  = sc.nextInt();
//				
//				switch (a) {
//				case 1 :
//					System.out.println("Vous êtes dans l'Equipe Bleue !");
//					Equipe.add(monJoueur);
//					monJoueur.setEquipe("Bleue");
//				case 2 :
//					System.out.println("Vous êtes dans l'Equipe Rouge !");
//					Equipe.add(monJoueur);
//					monJoueur.setEquipe("Rouge");
//				}
//				
//				System.out.println("");
//				System.out.println("-----------------");
//				System.out.println("Nouvelle Partie");
//				System.out.println("-----------------");
//				
//				Partie maPartie = new Partie();
//			}
//			
//			
//			
//			
//				
//			
//				
//			
//			
//			
//		}
	
	
	
	public void connection() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();

		Boolean ok = false;

		while (!ok) {
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
						ok = true;
					}
				}

				if (!ok) {
					System.out.println("Votre login ou votre mot de passe est incorrect.");
				}

			} else if (choix == 2) {
				ok = true;
				Application.sc.nextLine();
				inscription();
			}
		}
	}

	public void inscription() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();
		boolean ok = false;

		System.out.println("Veuillez entrer votre nom");
		String nom = Application.sc.nextLine();
		System.out.println("Entrez votre prénom");
		String prenom = Application.sc.nextLine();
		String username = null;

		while (!ok) {
			ok = true;
			System.out.println("Entrez votre login");
			username = Application.sc.nextLine();
			for (Utilisateur u : listeUtilisateurs) {
				if (u.getUsername().equals(username)) {
					ok = false;
					System.out.println("Ce login existe déjà");
				}
			}
		}

		System.out.println("Entrez votre mot de passe");
		String password = Application.sc.nextLine();

		Utilisateur utilisateur = new Joueur(0, nom, prenom, username, password);
		daoUtilisateur.save(utilisateur);
	}
}
