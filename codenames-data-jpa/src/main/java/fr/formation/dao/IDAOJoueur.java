package fr.formation.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;

public interface IDAOJoueur extends JpaRepository<Joueur, Integer>{

	public Joueur findByUsername(String nom);
	public List<Joueur> findByEquipe(Equipe equipe);

	public Joueur findBySaParticipation(Participation participation);
	public Joueur findBySaParticipationId(int id);

}
