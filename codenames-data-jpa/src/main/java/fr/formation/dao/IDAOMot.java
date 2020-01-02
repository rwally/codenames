package fr.formation.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Grille;
import fr.formation.model.Mot;


public interface IDAOMot extends JpaRepository<Mot, Integer>{
	public List<Mot> findByUsed(boolean isUsed);
}
