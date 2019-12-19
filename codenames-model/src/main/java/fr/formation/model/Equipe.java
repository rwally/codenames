package fr.formation.model;

import java.util.List;

public class Equipe {

	private List<Joueur> joueurs;
	private String nom;
	private int toursGagnes;
	
	public List<Joueur> getJoueurs() {
		return joueurs;
	}
	
	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getToursGagnes() {
		return toursGagnes;
	}
	
	public void setToursGagnes(int toursGagnes) {
		this.toursGagnes = toursGagnes;
	}

}
