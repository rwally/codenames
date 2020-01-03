package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Partie;

public interface IDAOPartie extends JpaRepository<Partie, Integer>{

}
