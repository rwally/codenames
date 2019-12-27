package fr.formation.DAO.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.formation.DAO.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

public class DAOUtilisateurSQL extends DAOConnectionSQL implements IDAOUtilisateur{

	@Override
	public List<Utilisateur> findAll() {
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();

		try {
			if (db != null) {
				Statement myStatement = db.createStatement();
				ResultSet myResult = myStatement.executeQuery(
						"SELECT * FROM utilisateur");

				while (myResult.next()) {
						listeUtilisateurs.add(em.getUtilisateur(myResult));
				}
			}
		} catch (SQLException e) {
			System.out.println("ERR : Création de la liste d'utilisateurs échouée");
			e.printStackTrace();
		}

		return listeUtilisateurs;
	}
	
	@Override
	public Utilisateur findById(Integer id) throws SQLException {
		Utilisateur entity = new Joueur();

		try {
			if (db != null) {
				Statement myStatement = db.createStatement();
				ResultSet myResult = myStatement.executeQuery(
						"SELECT * FROM utilisateur WHERE id = " + id);

				while (myResult.next()) {
					entity = em.getUtilisateur(myResult);	
				}
			}
		} catch (SQLException e) {
			System.out.println("ERR : Création de l'utilisateur échouée");
			e.printStackTrace();
		}
		return entity;
	}
	
	@Override
	public Utilisateur save(Utilisateur entity) {
		try {
			PreparedStatement myStatement = null;
			if (entity.getId() != 0) { // UPDATE
				myStatement = db.prepareStatement("UPDATE utilisateur"
						+ " SET nom = ?, prenom = ?, username = ?, password = ?" + " WHERE id = ?");
				myStatement.setInt(5, entity.getId());
			} else { // INSERT
				myStatement = db.prepareStatement(
						"INSERT INTO utilisateur (nom, prenom, username, password) VALUES (?, ?, ?, ?)");
			}
			
			myStatement.setString(1, entity.getNom());
			myStatement.setString(2, entity.getPrenom());
			myStatement.setString(3, entity.getUsername());
			myStatement.setString(4, entity.getPassword());
			
			myStatement.execute();
		}

		catch (SQLException e) {
			System.out.println("ERR : Impossible de créer ou de modifier l'utilisateur.");
			e.printStackTrace();
		}
		return entity;
	}

	@Override
	public void delete(Utilisateur entity) throws SQLException {
		deleteById(entity.getId());	
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		try {
			PreparedStatement myStatement = db.prepareStatement("DELETE FROM utilisateur" + " WHERE id = ?");
			myStatement.setInt(1, id);
			myStatement.execute();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERR : Utilisateur non supprimé.");
		}
		
	}

	@Override
	public Utilisateur findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findByNom(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

}
