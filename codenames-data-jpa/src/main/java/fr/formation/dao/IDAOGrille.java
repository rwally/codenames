package fr.formation.dao;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Grille;


public interface IDAOGrille extends JpaRepository<Grille, Integer>{


}
