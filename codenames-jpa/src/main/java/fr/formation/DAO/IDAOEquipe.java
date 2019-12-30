package fr.formation.DAO;

import fr.formation.model.Equipe;


public interface IDAOEquipe extends IDAO<Equipe, Integer>{
	public Equipe findByNom(String username);
}
