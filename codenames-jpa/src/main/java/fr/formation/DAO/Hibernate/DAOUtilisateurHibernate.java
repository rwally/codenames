package fr.formation.DAO.Hibernate;

import java.sql.SQLException;
import java.util.List;

import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.model.Utilisateur;

public class DAOUtilisateurHibernate extends DAOHibernate implements IDAOUtilisateur {

	@Override
	public List<Utilisateur> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Utilisateur save(Utilisateur entity) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Utilisateur entity) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	
}
