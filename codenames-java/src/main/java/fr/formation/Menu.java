package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.formation.DAO.IDAOJoueur;
import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.DAO.Hibernate.DAOJoueur;
import fr.formation.DAO.Hibernate.DAOUtilisateurHibernate;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.model.Utilisateur;

public class Menu {
	public static IDAOUtilisateur daoUtilisateur = new DAOUtilisateurHibernate();
	public static IDAOJoueur daoJoueur = new DAOJoueur();

	

	public Joueur connection() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();
		Joueur joueur = null;

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
						joueur = daoJoueur.findByUsername(nom);
						
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

	public Joueur inscription() throws SQLException {
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
			System.out.println("Entrez votre pr�nom");
			prenom = Application.sc.nextLine();
			if (!prenom.equals("")) {
				usernameOk = true;
			}
		}
		
		usernameOk = false;
		String username = null;
		boolean loginOK = false;

		// V�rification de l'username unique
		while (!usernameOk) {
			usernameOk = true;
			while (!loginOK) {
				System.out.println("Entrez votre login");
				username = Application.sc.nextLine();
				if (!username.equals("")) {
					loginOK = true;
				}
				else {
					System.out.println("Votre login ne peut �tre nul");
				}
			}
			for (Utilisateur u : listeUtilisateurs) {
				if (u.getUsername().equals(username)) {
					usernameOk = false;
					System.out.println("Ce login existe d�j�");
				}
			}
		}

		boolean mdpOK = false;
		String password = null;

		// V�rification de la bonne saise du mot de passe
		while (!mdpOK) {
			System.out.println("Entrez votre mot de passe");
			password = Application.sc.nextLine();
			System.out.println("Entrez � nouveau votre mot de passe");
			String password2 = Application.sc.nextLine();

			if (password.equals(password2)) {
				mdpOK = true;
			} else {
				System.out.println("Les deux mots de passe ne correspondent pas");
			}
		}

		Utilisateur utilisateur = new Joueur(0, nom, prenom, username, password);
		daoUtilisateur.save(utilisateur);
		Joueur joueur = daoJoueur.save((Joueur) utilisateur);

		return joueur;
	}
	
	
	public Participation menuPrincipal() {
		
		
		System.out.println("-----------------");
		System.out.println("Bienvenue dans Code Names");
		System.out.println("-----------------");

		
		System.out.println("-----------------");
		System.out.println("MENU PRINCIPAL");
		System.out.println("-----------------");
		
		
		Joueur monJoueur = new Joueur();
		try {
			monJoueur = connection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERR de recuperation du Joueur dans Menu Principal !!");
		}
		
		Equipe EquipeRouge = new Equipe();
		Equipe EquipeBleue = new Equipe();
		
		System.out.println("Saisir 1 pour commencer une nouvelle partie");
		
		
		if( Application.sc.nextInt() == 1) {
			
			System.out.println("Saisir 1 pour choisir  l'Equipe Rouge");
			System.out.println("Saisir 2 pour choisir l'Equipe Bleue)");
//			System.out.println("Saisir 3 pour �tre spectateur");
			
			int a  = Application.sc.nextInt();
			
			
			
			switch (a) {
			case 1 :
				System.out.println("Vous �tes dans l'Equipe Bleue !");
				EquipeBleue.AjouterMembre(monJoueur);
				monJoueur.setEquipe(EquipeBleue);
				break;
			case 2 :
				System.out.println("Vous �tes dans l'Equipe Rouge !");
				EquipeRouge.AjouterMembre(monJoueur);
				monJoueur.setEquipe(EquipeRouge);
				
			break;
			}
		}
		
		Participation maParticipation = new Participation(Role.agent, monJoueur);
		
		System.out.println();
		System.out.println("-----------------");
		System.out.println("Nouvelle Partie");
		System.out.println("-----------------");
		
		return maParticipation;
	}
}
