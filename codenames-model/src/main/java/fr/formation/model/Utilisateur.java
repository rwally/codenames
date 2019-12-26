package fr.formation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateur")
public abstract class Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	
	@Column(name = "nom", nullable = false)
	protected String nom;
	
	@Column(name = "prenom", nullable = false)
	protected String prenom;
	
	@Column(name = "username", unique = true, nullable = false)
	protected String username;
	
	@Column(name = "password", nullable = false)
	protected String password;


	public Utilisateur(int id, String nom, String prenom, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	public Utilisateur() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
}

