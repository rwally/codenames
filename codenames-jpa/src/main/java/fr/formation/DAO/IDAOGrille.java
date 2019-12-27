package fr.formation.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.formation.model.Case;
import fr.formation.model.Grille;


public interface IDAOGrille extends IDAO<Grille, Integer>{
	public Optional<Grille> findByNom(String nom) throws SQLException;
	public Grille creerGrille(ArrayList<Case> cases);
	

}
