package fr.formation.model;

public enum Difficulte {
	facile(4), moyen(5), difficile(6);

	private int valeur;

	public int getValeur() {
		return valeur;
	}

	Difficulte(int valeur) {
		this.valeur = valeur;
	}
	
	

}