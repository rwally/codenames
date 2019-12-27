package fr.formation.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.formation.model.Grille;
import fr.formation.model.Mot;


public interface IDAOMot extends IDAO<Mot, Integer>{
	public Optional<Mot> findByNom(String nom);
	public List<Mot> creerListeMots(Grille maGrille) throws SQLException;
}
