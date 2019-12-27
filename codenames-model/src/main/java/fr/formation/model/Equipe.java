package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "equipe")
public class Equipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nom")
	private String nom;
	
	@OneToMany(mappedBy = "equipe")
	private List<Joueur> joueurs;
	
	@Column(name = "toursGagnes")
	private int toursGagnes;
	
	private static List<Joueur> Equipe = new ArrayList<Joueur>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void AjouterMembre(Joueur j) {
		Equipe.add(j);
	}
	
	public static List<Joueur> getEquipe() {
		return Equipe;
	}

	public static void setEquipe(List<Joueur> equipe) {
		Equipe = equipe;
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
