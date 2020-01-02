package fr.formation.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Grille;
import fr.formation.model.Mot;


public interface IDAOMot extends JpaRepository<Mot, Integer>{
	public Optional<Mot> findByNom(String nom);
	public List<Mot> creerListeMots(Grille maGrille) throws SQLException;
}
