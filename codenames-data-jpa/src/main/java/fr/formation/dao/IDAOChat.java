package fr.formation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.formation.model.Chat;

public interface IDAOChat extends JpaRepository<Chat, Integer>{
	
}
