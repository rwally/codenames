package fr.formation.model;

import javax.validation.constraints.NotEmpty;

public class UtilisateurForm {
	
	protected int id;

	protected String nom;

	protected String prenom;

	protected String username;

	protected String password;

	protected String password2;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	public Utilisateur returnUtilisateur() {
		Utilisateur utilisateur = new Utilisateur();

		utilisateur.setNom(this.nom);
		utilisateur.setPrenom(this.prenom);
		utilisateur.setUsername(this.username);
		utilisateur.setPassword(this.password);
		
		return utilisateur;
	}

}
