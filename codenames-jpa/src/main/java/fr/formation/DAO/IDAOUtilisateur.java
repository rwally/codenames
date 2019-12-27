package fr.formation.DAO;

import java.util.Optional;

import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

public interface IDAOUtilisateur extends IDAO<Utilisateur, Integer> {
	public Optional<Joueur> findByUsername(String username);
}
