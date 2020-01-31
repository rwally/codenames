package fr.formation.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.Views.Views;



@Entity
@Table(name = "tour")
public class Tour {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	// private List<Mot> CodeNames = new ArrayList<Mot>();
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tour_partie")
	@JsonView(Views.Tour.class)
	private Partie partie;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="tour_equipe")
	@JsonView(Views.Tour.class)
	private Equipe equipe;
	
	@Column(name="nombreMaster")
	@JsonView(Views.Tour.class)
	private int nombreMaster;
	
<<<<<<< Updated upstream
	@Column
	@JsonView(Views.Tour.class)
	private String indice;
=======
	@Column(name="indice")
	@JsonView(Views.Tour.class)
	private String indice;
	
>>>>>>> Stashed changes
	
	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public String getIndice() {
		return indice;
	}

	public void setIndice(String indice) {
		this.indice = indice;
	}

	public Tour() {
		
	}

	public Partie getPartie() {
		return partie;
	}

	public void setPartie(Partie partie) {
		this.partie = partie;
	}


	public int getNombreMaster() {
		return nombreMaster;
	}

	public void setNombreMaster(int nombreMaster) {
		this.nombreMaster = nombreMaster;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Case vote(int nbJoueurs, Grille grille) {
		Case laCase = new Case();
		List<Case> votes = new ArrayList<Case>();
		boolean voteOK = false;
		
		while (!voteOK) {
		for (int i = 0; i < nbJoueurs; i++) {
			votes.add(voteJoueur(grille));
		}
		
		laCase = voteGlobal(votes);
		if (laCase != null) {
			voteOK = true;
		}
		else {
			System.out.println("Il y a une égalité pour les votes, revotez");
		}
		}
		
		return laCase;
	}

	public Case voteGlobal(List<Case> votes) {
		Map<Case, Integer> map = new HashMap<Case, Integer>();
		int max = 0;
		Case laCase = null;

		for (int i = 0; i < votes.size(); i++) {
			Case key = votes.get(i);
			int value = 1;

			if (map.containsKey(key)) {
				value = map.get(key) + 1;
			}
			map.put(key, value);

			if (value > max) {
				max = value;
				laCase = key;
			}
			else if (value == max) {
				laCase = null;
			}
		}
		return laCase;
	}

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

	public Map<String,List<Case>> CodeName(Joueur j, Grille grille) {
		String code = null;
		int nbMots = -1;

		Map<String,List<Case>> casesADeviner = new HashMap<String,List<Case>>();
		List<Case> mesCases = new ArrayList<Case>(); 

		Scanner sc = new Scanner(System.in);

		if (j.getSaParticipation().getRole() == Role.master) {

			System.out.println("Entrez le nom de code :");
			code = sc.nextLine();
			System.out.println("Entrez un nombre de mots à deviner :");
			nbMots = sc.nextInt();

			System.out.println("Choisissez les mots à faire deviner :");

			switch (nbMots) {
			
			case 0:
				System.out.println("La totalité de vos mots sonts à deviner, sans rapport avec votre CODENAME :");
				
				for (Case c : grille.getCases()){
					if(j.getEquipe().getNom().contains(c.getCouleur().toString())){
						mesCases.add(c);
						
					}
				}
				
				casesADeviner.put(code,mesCases);
				
				break;
			case 1:
				System.out.println("Entrez LE mot à deviner dans la grille :");
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
