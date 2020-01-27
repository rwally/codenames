package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Joueur;
import fr.formation.model.Participation;

public interface IDAOParticipation extends JpaRepository<Participation, Integer>{
	public Participation findByJoueur(Joueur joueur);
}
