package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.views.Views;

@Entity
@Table(name = "[case]")
public class Case {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "couleur")
	@Enumerated(EnumType.STRING)
	@JsonView(Views.Case.class)
	private Couleur couleur;
	
	@Column(name = "trouver")
	@JsonView(Views.Case.class)
	private boolean trouver;
	
	@OneToOne
	@JoinColumn(name = "mot")
	@JsonView(Views.Common.class)
	private Mot mot;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "grille")
	private Grille grille;
	
	@Column(name="image")
	@JsonView(Views.Case.class)
	private String image;
	
	@Column(name="imageMaster")
	@JsonView(Views.Case.class)
	private String imageMaster;
	
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
	
	
	
	public String getImageMaster() {
		return imageMaster;
	}

	public void setImageMaster(String imageMaster) {
		this.imageMaster = imageMaster;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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


