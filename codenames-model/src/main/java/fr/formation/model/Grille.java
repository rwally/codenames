package fr.formation.model;

import java.util.ArrayList;
import java.util.Random;

public class Grille {
	
	int difficulte;
	private Case grille[][];
	
	Random random = new Random();
	int nombreRandom; 
	
	public Grille(int difficulte,Case[][] grille) {
		this.difficulte = difficulte;
		this.grille = grille;
	}

	public Grille() {	
	}
		
	public int getDifficulte() {
		return difficulte;
	}

	public void setDifficulte(int difficulte) {
		this.difficulte = difficulte;
	}

	public Case[][] getGrille() {
		return grille;
	}

	public void setGrille(int difficulte) {
		Case grille[][] = new Case[difficulte*5][difficulte*5];
		this.grille = grille;
	}

	public Case[][] creerGrille(ArrayList<Case> cases){
		
		for(int i=0;i<5;i++) {
			for(int j=0;j<5;j++) {
					nombreRandom = random.nextInt(cases.size());
					grille[i][j] = cases.get(nombreRandom);
					cases.remove(nombreRandom);
			}
		}
		
		return grille;
	}
	
	public void afficherGrille(Case[][] grille) {
		for(int i=0;i<5;i++) {		
			for(int j=0;j<5;j++) {
				System.out.print(grille[i][j].getMot().getLibelle()+" "+grille[i][j].getCouleur()+"\t");
			}
			System.out.println();
		}
	}
}
