package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Joueur;

public interface IDAOJoueur extends JpaRepository<Joueur, Integer>{

	public Joueur findByUsername(String nom);
}
