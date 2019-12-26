package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

public class Equipe {

	private List<Joueur> joueurs;
	private String nom;
	private int toursGagnes;
	
	private static List<Joueur> EquipeBleue = new ArrayList<Joueur>();
	private static List<Joueur> EquipeRouge = new ArrayList<Joueur>();
	
	public static List<Joueur> getEquipeBleue() {
		return EquipeBleue;
	}
	
	public void AjouterJoueurBleu(Joueur j) {
		EquipeBleue.add(j);
	}
	
	public void AjouterJoueurRouge(Joueur j) {
		EquipeRouge.add(j);
	}
	
	public void setEquipeBleue(List<Joueur> equipeBleue) {
		EquipeBleue = equipeBleue;
	}
	public static List<Joueur> getEquipeRouge() {
		return EquipeRouge;
	}
	public void setEquipeRouge(List<Joueur> equipeRouge) {
		EquipeRouge = equipeRouge;
	}
	
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
