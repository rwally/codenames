package fr.formation.model;

import java.util.ArrayList;
import java.util.Random;

public class Grille {

	Difficulte difficulte;
	private Case grille[][];
	
	Random random = new Random();
	int nombreRandom; 
	
	
	

	
	public Grille(Difficulte difficulte, Case[][] grille) {
		this.difficulte = difficulte;
		this.grille = grille;
	}



	public Grille() {
		
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
