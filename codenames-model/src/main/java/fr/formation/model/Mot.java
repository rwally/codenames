package fr.formation.model;

public class Mot {

	private int id;
	private String libelle;
	private boolean used;
	
	public Mot(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public Mot(String libelle) {
		this.libelle = libelle;
	}
	
	public Mot() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
	
}
