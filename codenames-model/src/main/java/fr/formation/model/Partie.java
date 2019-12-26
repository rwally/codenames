package fr.formation.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class Partie {
	private Grille grille;
	private List<Participation> joueurs;
	

	public Partie(Grille grille, List<Participation> joueurs) {
		this.grille = grille;
		this.joueurs = joueurs;
	}
	
	public Partie() {
	}
	
	
}
