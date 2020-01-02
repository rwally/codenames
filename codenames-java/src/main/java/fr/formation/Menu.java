package fr.formation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.model.Utilisateur;

@Service
public class Menu {
	
	@Autowired
	IDAOUtilisateur daoUtilisateur;
	
	@Autowired
	IDAOJoueur daoJoueur;
	
	private Equipe equipeBleue = new Equipe();
	private Equipe equipeRouge = new Equipe();

	public Joueur connection() throws SQLException {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();
		listeUtilisateurs = daoUtilisateur.findAll();
		Joueur joueur = null;

		Boolean mdpOk = false;

		while (!mdpOk) {
			System.out.println("Bonjour, veuillez saisir 1 pour vous connecter ou 2 pour vous inscrire.");

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
			System.out.println("Entrez votre prï¿½nom");
			prenom = Application.sc.nextLine();
			if (!prenom.equals("")) {
				usernameOk = true;
			}
		}

		usernameOk = false;
		String username = null;
		boolean loginOK = false;

		// Vï¿½rification de l'username unique
		while (!usernameOk) {
			usernameOk = true;
			System.out.println("Entrez votre login");
			username = Application.sc.nextLine();

			for (Utilisateur u : listeUtilisateurs) {
				if (u.getUsername().equals(username)) {
					usernameOk = false;
					System.out.println("Ce login existe dï¿½jï¿½");
				}
			}
		}

		boolean mdpOK = false;
		String password = null;

		// Vï¿½rification de la bonne saise du mot de passe
		while (!mdpOK) {
			System.out.println("Entrez votre mot de passe");
			password = Application.sc.nextLine();
			System.out.println("Entrez ï¿½ nouveau votre mot de passe");
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

	public List<Participation> menuPrincipal() {

		List<Participation> lesParticipations = new ArrayList<Participation>();

		System.out.println("-----------------");
		System.out.println("Bienvenue dans Code Names");
		System.out.println("-----------------");

		System.out.println("-----------------");
		System.out.println("MENU PRINCIPAL");
		System.out.println("-----------------");

		boolean nouveauJoueur = true;
		int nbJoueurs = 0;
		int nbJoueursBleus = 0;
		int nbJoueursRouge = 0;

		equipeRouge.setNom("Rouge");
		equipeBleue.setNom("Bleu");

		while (nouveauJoueur) {
			Joueur monJoueur = new Joueur();
			nbJoueurs++;

			try {
				monJoueur = connection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("ERR de recuperation du Joueur dans Menu Principal !!");
			}

			System.out.println("Saisir 1 pour choisir  l'Equipe Bleue");
			System.out.println("Saisir 2 pour choisir l'Equipe Rouge");

			int a = Application.sc.nextInt();

			switch (a) {
			case 1:
				System.out.println("Vous êtes dans l'Equipe Bleue !");
				equipeBleue.AjouterMembre(monJoueur);
				monJoueur.setEquipe(equipeBleue);
				nbJoueursBleus++;
				break;
			case 2:
				System.out.println("Vous êtes dans l'Equipe Rouge !");
				equipeRouge.AjouterMembre(monJoueur);
				monJoueur.setEquipe(equipeRouge);
				nbJoueursRouge++;
				break;
			}

			Participation maParticipation = new Participation(Role.agent, monJoueur);
			lesParticipations.add(maParticipation);
			monJoueur.setSaParticipation(maParticipation);
			

			if (nbJoueurs < 4) {
				System.out.println("Enregistrez un autre joueur (nombre de joueurs minimum : 4)");
			} else if (nbJoueursBleus < 2 || nbJoueursRouge < 2) {
				System.out.println("Il faut au moins deux joueurs dans chaque équipe.");
				System.out.println("Nombre de joueurs dans l'équipe rouge : " + nbJoueursRouge);
				System.out.println("Nombre de joueurs dans l'équipe bleue : " + nbJoueursBleus);
				System.out.println("Enregistrez un autre joueur.");
			} else {

				System.out.println("Saisir 1 pour entrer un nouveau joueur ou 2 pour commencer la partie.");
				int choix = Application.sc.nextInt();

				switch (choix) {
				case 1:
					break;
				case 2:
					nouveauJoueur = false;
					break;
				}
			}
		}

		System.out.println();
		System.out.println("-----------------");
		System.out.println("Nouvelle Partie");
		System.out.println("-----------------");

		return lesParticipations;
	}

	public Equipe getEquipeBleue() {
		return equipeBleue;
	}

	public void setEquipeBleue(Equipe equipeBleue) {
		this.equipeBleue = equipeBleue;
	}

	public Equipe getEquipeRouge() {
		return equipeRouge;
	}

	public void setEquipeRouge(Equipe equipeRouge) {
		this.equipeRouge = equipeRouge;
	}
}
