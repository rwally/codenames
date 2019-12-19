package fr.formation.model;

import java.util.ArrayList;

public class Case {
	
	private Couleur couleur;
	private Mot mot;
	//private Grille grille;
	
	public Case() {		
	}

	public Case(Couleur couleur, Mot mot) {
		this.couleur = couleur;
		this.mot = mot;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public void setCouleur(Couleur couleur) {
		this.couleur = couleur;
	}

	public Mot getMot() {
		return mot;
	}

	public void setMot(Mot mot) {
		this.mot = mot;
	}
	
	public ArrayList<Case> creerListeCases(ArrayList<Mot> listeMots){
		
		ArrayList<Case> cases = new ArrayList<Case>();
		
		for(int i = 0; i < 25; i++) {
				Case maCase = new Case();
				maCase.setMot(listeMots.get(0));
				listeMots.remove(0);
				if(i<9) {
					maCase.setCouleur(Couleur.rouge);
				}else if(i<17) {
					maCase.setCouleur(Couleur.bleu);
				}else if(i<24) {
					maCase.setCouleur(Couleur.blanc);
				}else {
					maCase.setCouleur(Couleur.noir);
				}			
				cases.add(maCase);
		}
		
		return cases;
		
	}

	
	/*
	for(int i = 0; i < 9; i++) {
		Case maCase = new Case();
		maCase.setMonMot(myResult.getString("mot"));
		maCase.setMaCouleur(Couleur.rouge);
		cases.add(maCase);
	}
	for(int i = 0; i < 8; i++) {
		Case maCase = new Case();
		maCase.setMonMot(myResult.getString("mot"));
		maCase.setMaCouleur(Couleur.bleu);
		cases.add(maCase);
	}
	for(int i = 0; i < 7; i++) {
		Case maCase = new Case();
		maCase.setMonMot(myResult.getString("mot"));
		maCase.setMaCouleur(Couleur.blanc);
		cases.add(maCase);
	}
	Case maCase = new Case();
	maCase.setMonMot(myResult.getString("mot"));
	maCase.setMaCouleur(Couleur.noir);
	cases.add(maCase);
	*/
}


