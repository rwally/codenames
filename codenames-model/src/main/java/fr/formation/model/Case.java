package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "case")
public class Case {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "couleur")
	@Enumerated(EnumType.STRING)
	private Couleur couleur;
	
	@OneToOne
	@JoinColumn(name = "mot_id")
	private Mot mot;
	
	@ManyToOne
	@JoinColumn(name = "grille_id")
	private Grille grille;
	
	@Column(name = "trouver")
	private boolean trouver=false;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Case() {		
	}

	public Case(Couleur couleur, Mot mot, Grille grille) {
		this.couleur = couleur;
		this.mot = mot;
		this.grille = grille;
	}
	
	public boolean isTrouver() {
		return trouver;
	}
	
	public void setTrouver(boolean trouver) {
		this.trouver = trouver;
	}



	public Grille getGrille() {
		return grille;
	}



	public void setGrille(Grille grille) {
		this.grille = grille;
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
	
	
	
}


