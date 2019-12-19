package fr.formation.model;

public class Joueur extends Utilisateur {
	
	private Equipe equipe;
	
	public Joueur(int id, String nom, String prenom, String username, String password) {
		super(id, nom, prenom, username, password);
	}
	
	public Joueur() {
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

}