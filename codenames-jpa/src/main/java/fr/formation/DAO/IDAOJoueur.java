package fr.formation.DAO;

import fr.formation.model.Joueur;

public interface IDAOJoueur extends IDAO<Joueur, Integer>{
	public Joueur findByUsername(String username);
}
