package fr.formation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tour")
public class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	// private List<Mot> CodeNames = new ArrayList<Mot>();
	
	@ManyToOne
	@JoinColumn(name="tour_partie")
	private Partie partie;
	
	@Column(name = "tour_nombre")
	private int nombreTours;
	


	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}

	public int getNombreTours() {
		return nombreTours;
	}

	public void setNombreTours(int nombreTours) {
		this.nombreTours = nombreTours;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

//	/!\ méthode non testée
	public Case voteGlobal(List<Case> votes) {
		Map<Case, Integer> map = new HashMap<Case, Integer>();
		int max = 0;
		Case laCase = null;

		for (int i = 0; i < votes.size(); i++) {
			int value = 1;
			Case key = votes.get(i);

			if (map.containsKey(key)) {
				value = map.get(key);
			}
			map.put(key, value);

			if (value > max) {
				max = value;
				laCase = key;
			}
		}
		return laCase;
	}

//	/!\ méthode non testée
	public Case voteJoueur(Grille grille) {
		Case maCase = new Case();
		maCase = null;
		boolean saisieOK = false;
		Scanner sc = new Scanner(System.in);

		while (!saisieOK) {
			System.out.println("Quel mot choisissez vous ?");
			String mot = sc.nextLine();

			for (Case c : grille.getCases()) {

				if (c.getMot().getLibelle().equals(mot)) {
					maCase = c;
					saisieOK = true;
				}

			}
			if (!saisieOK) {
				System.out.println("Veuillez écrire un mot de la liste. Respectez la casse");
			}
		}
		return maCase;
	}

	public boolean CodeName(Joueur j) {
		String code = null;
		int nbMots = -1;

		boolean valid = false;

		Scanner sc = new Scanner(System.in);

		if (j.getSaParticipation().getRole() == Role.master) {

			System.out.println("Entrez le nom de code :");
			code = sc.nextLine();
			System.out.println("Entrez un nombre de mots à deviner :");
			nbMots = sc.nextInt();

			System.out.println("Choisissez les mots à faire deviner :");

			switch (nbMots) {

			case 0:

			case 1:
				System.out.println("Choisissez le mot à deviner dans la grille :");
				String codeName = sc.nextLine();

			case 2:
				System.out.println("Entrez les deux noms de code :");
				String codeName1 = sc.nextLine();
				String codeName2 = sc.nextLine();

				if (codeName1.equals(code)) {
					valid = true;
				}
			}

		} else {
			System.out.println(code + " en " + nbMots + " mots.");

			switch (nbMots) {

			case 0:

			case 1:
				System.out.println("Entrez le nom de code :");
				String codeName = sc.nextLine();

				if (codeName.equals(code)) {
					valid = true;
				}

			case 2:
				System.out.println("Entrez les deux noms de code :");
				String codeName1 = sc.nextLine();
				String codeName2 = sc.nextLine();

				if (codeName1.equals(code)) {
					valid = true;
				}
			}
		}
		return valid;
	}
}
