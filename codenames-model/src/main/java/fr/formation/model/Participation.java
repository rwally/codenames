package fr.formation.model;

public class Participation {
	
	private Role role;
	private Joueur joueur;
	
	public Participation(Role role, Joueur joueur) {
		this.role = role;
		this.joueur = joueur;
	}
	
	public Participation(Role role) {
		this.role = role;
	}

	public Participation() {
		
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}
}
