package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Participation;

public interface IDAOParticipation extends JpaRepository<Participation, Integer>{

}
