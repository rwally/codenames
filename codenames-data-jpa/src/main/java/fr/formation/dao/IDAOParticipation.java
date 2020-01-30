package fr.formation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Partie;

public interface IDAOParticipation extends JpaRepository<Participation, Integer>{
	public Participation findByJoueur(Joueur joueur);
	public List<Participation> findByMaPartie(Partie partie);
}
