package fr.formation.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Case;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public interface IDAOCase extends JpaRepository<Case, Integer>{
	public Optional<Case> findByNom(String nom);
	public List<Case> creerListeCase(List<Mot> listeMots, Grille maGrille);
}
