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
	
	private static List<Joueur> EquipeBleue = new ArrayList<Joueur>();
	private static List<Joueur> EquipeRouge = new ArrayList<Joueur>();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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
