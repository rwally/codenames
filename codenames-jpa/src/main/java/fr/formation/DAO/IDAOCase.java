package fr.formation.DAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import fr.formation.model.Case;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

public interface IDAOCase extends IDAO<Case, Integer>{
	public Optional<Case> findByNom(String nom);
	public List<Case> creerListeCase(List<Mot> listeMots, Grille maGrille);
}
