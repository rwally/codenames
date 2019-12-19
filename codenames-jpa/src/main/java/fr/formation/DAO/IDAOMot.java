package fr.formation.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import fr.formation.model.Mot;


public interface IDAOMot extends IDAO<Mot, Integer>{
	public ArrayList<Mot> findByNom(String nom) throws SQLException;
}
