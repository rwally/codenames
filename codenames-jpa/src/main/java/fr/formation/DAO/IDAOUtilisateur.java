package fr.formation.DAO;

import java.util.Optional;

import fr.formation.model.Utilisateur;

public interface IDAOUtilisateur extends IDAO<Utilisateur, Integer> {
	public Utilisateur findByUsername(String username);
	public Optional<Utilisateur> findByNom(String nom);
}
