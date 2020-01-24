package fr.formation.model;

public enum Difficulte {
	facile(24), moyen(22), difficile(20);

	private int valeur;

	public int getValeur() {
		return valeur;
	}

	Difficulte(int valeur) {
		this.valeur = valeur;
	}
	
	

}