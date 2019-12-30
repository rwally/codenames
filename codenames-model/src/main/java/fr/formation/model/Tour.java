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

//	/!\ m�thode non test�e
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

//	/!\ m�thode non test�e
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
				System.out.println("Veuillez �crire un mot de la liste. Respectez la casse");
			}
		}
		return maCase;
	}

	public Map<String,List<Case>> CodeName(Joueur j, Grille grille) {
		String code = null;
		int nbMots = -1;

		Map<String,List<Case>> casesADeviner = new HashMap<String,List<Case>>();
		List<Case> mesCases = new ArrayList<Case>(); 

		Scanner sc = new Scanner(System.in);

		if (j.getSaParticipation().getRole() == Role.master) {

			System.out.println("Entrez le nom de code :");
			code = sc.nextLine();
			System.out.println("Entrez un nombre de mots � deviner :");
			nbMots = sc.nextInt();

			System.out.println("Choisissez les mots � faire deviner :");

			switch (nbMots) {
			
			case 0:
				System.out.println("La totalit� de vos mots sonts � deviner, sans rapport avec votre CODENAME :");
				
				for (Case c : grille.getCases()){
					if(j.getEquipe().getNom().contains(c.getCouleur().toString())){
						mesCases.add(c);
						
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;
			case 1:
				System.out.println("Entrez LE mot � deviner dans la grille :");
				String codeName = sc.nextLine();
				for (Case c : grille.getCases()){
					if(c.getMot().getLibelle().equals(codeName) && j.getEquipe().getNom().contains(c.getCouleur().toString())){
						mesCases.add(c);
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;

			case 2:
				System.out.println("Entrez les DEUX noms de code :");
				System.out.println("Code Name 1 :");
				String codeName1 = sc.nextLine();
				System.out.println("Code Name 2 :");
				String codeName2 = sc.nextLine();

				for (Case c : grille.getCases()){
					if((c.getMot().getLibelle().equals(codeName1)||c.getMot().getLibelle().equals(codeName2)) 
						&& j.getEquipe().getNom().contains(c.getCouleur().toString()))
						{
						mesCases.add(c);
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;
				
			case 3:
				System.out.println("Entrez les TROIS noms de code :");
				System.out.println("Code Name 1 :");
				String codeName3 = sc.nextLine();
				System.out.println("Code Name 2 :");
				String codeName4 = sc.nextLine();
				System.out.println("Code Name 3 :");
				String codeName5 = sc.nextLine();

				for (Case c : grille.getCases()){
					if((c.getMot().getLibelle().equals(codeName3)||c.getMot().getLibelle().equals(codeName4)||c.getMot().getLibelle().equals(codeName5)) 
						&& j.getEquipe().getNom().contains(c.getCouleur().toString()))
						{
						mesCases.add(c);
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;
				
			case 4:
				System.out.println("Entrez les TROIS noms de code :");
				System.out.println("Code Name 1 :");
				String codeName6 = sc.nextLine();
				System.out.println("Code Name 2 :");
				String codeName7 = sc.nextLine();
				System.out.println("Code Name 3 :");
				String codeName8 = sc.nextLine();
				System.out.println("Code Name 4 :");
				String codeName9 = sc.nextLine();

				for (Case c : grille.getCases()){
					if((c.getMot().getLibelle().equals(codeName6)||c.getMot().getLibelle().equals(codeName7)||c.getMot().getLibelle().equals(codeName8)||c.getMot().getLibelle().equals(codeName9)) 
						&& j.getEquipe().getNom().contains(c.getCouleur().toString()))
						{
						mesCases.add(c);
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;
			
			}
			

		} 
		return casesADeviner;
	}
}
