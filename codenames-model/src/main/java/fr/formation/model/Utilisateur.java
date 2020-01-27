package fr.formation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Table(name = "utilisateur")
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	protected int id;
	
	@Column(name = "nom", nullable = false)
//	@NotEmpty(message="Veuillez entrer votre nom")
	protected String nom;
	
	@Column(name = "prenom", nullable = false)
//	@NotEmpty(message="Veuillez entrer votre prénom")
	protected String prenom;
	
	@Column(name = "username", unique = true, nullable = false)
	//@UniqueElements(message="Cet identifiant existe déjà")
	protected String username;
	
	@Column(name = "password", nullable = false)
//	@NotEmpty(message="Veuillez entrer un mot de passe")
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

