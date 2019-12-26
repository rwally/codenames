package fr.formation.model;

import java.util.ArrayList;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grille")
public class Grille {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "difficulte")
	@Enumerated(EnumType.STRING)
	private Difficulte difficulte;
	
	@OneToMany(mappedBy = "grille")
	private Case grille[][];
	
	private Random random = new Random();
	int nombreRandom; 
	
	
	public Grille(Difficulte difficulte, Case[][] grille) {
		this.difficulte = difficulte;
		this.grille = grille;
	}



	public Grille() {
		
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Difficulte getDifficulte() {
		return difficulte;
	}



	public void setDifficulte(Difficulte difficulte) {
		this.difficulte = difficulte;
	}



	public Case[][] getGrille() {
		return grille;
	}


	public void setGrille(int difficulte) {
		Case grille[][] = new Case[difficulte][difficulte];
		this.grille = grille;
	}


	public Case[][] creerGrille(ArrayList<Case> cases){
		int taille = difficulte.getValeur();
		for(int i=0;i<taille;i++) {
			for(int j=0;j<taille;j++) {
					nombreRandom = random.nextInt(cases.size());
					grille[i][j] = cases.get(nombreRandom);
					cases.remove(nombreRandom);
			}
		}
		
		return grille;
	}
	

	

}
