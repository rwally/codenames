package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Tour;

public interface IDAOTour extends JpaRepository<Tour, Integer>{

}
