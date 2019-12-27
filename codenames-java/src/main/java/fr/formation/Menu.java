package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.DAO.Hibernate.DAOUtilisateurHibernate;
import fr.formation.DAO.SQL.DAOUtilisateurSQL;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

public class Menu {
	public static IDAOUtilisateur daoUtilisateur = new DAOUtilisateurHibernate();

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
					}
				}

				if (!mdpOk) {
					System.out.println("Votre login ou votre mot de passe est incorrect.");
				}

			} else if (choix == 2) {
				mdpOk = true;
				Application.sc.nextLine();
				inscription();
			}
		}
	}

	public void inscription() throws SQLException {
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
	}
}
