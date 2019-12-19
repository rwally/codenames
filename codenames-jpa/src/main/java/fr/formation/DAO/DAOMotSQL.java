package fr.formation.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.formation.model.Grille;
import fr.formation.model.Mot;


public class DAOMotSQL extends DAOConnectionSQL implements IDAOMot {

	@Override
	public List<Mot> findAll() throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Mot> mots = new ArrayList<Mot>();
		Statement myStatement = db.createStatement();
		ResultSet myResult = myStatement.executeQuery("SELECT * FROM mot");
		
		while(myResult.next()) {
				mots.add(em.getMot(myResult));
		}
	
		return mots;
	}

	@Override
	public Mot findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		Mot monMot = new Mot();
		PreparedStatement myStatement = db.prepareStatement("SELECT * FROM mot WHERE id=?");
		myStatement.setInt(1,id);
		ResultSet myResult = myStatement.executeQuery();
		
		while(myResult.next()) {
			monMot = em.getMot(myResult);
		}
		return monMot;
	}
	
	@Override
	public ArrayList<Mot> findByNom(String nom) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Mot> mesMots = new ArrayList<Mot>();
		PreparedStatement myStatement = db.prepareStatement("SELECT * FROM mot WHERE libelle LIKE ?");
		myStatement.setString(1,nom);
		ResultSet myResult = myStatement.executeQuery();
		
		while(myResult.next()) {
			mesMots.add(em.getMot(myResult));
		}
		return mesMots;
	}

	@Override
	public Mot save(Mot entity) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStatement = db.prepareStatement("SELECT * FROM mot WHERE id=?");
		myStatement.setInt(1,entity.getId());
		ResultSet myResult = myStatement.executeQuery();
		
		if(myResult.next()) {
			myStatement= db.prepareStatement("UPDATE mot SET libelle=? WHERE id=?");
			myStatement.setString(1, entity.getLibelle());
			myStatement.setInt(2, entity.getId());
			myStatement.execute();
		
		}else {
			myStatement = db.prepareStatement("INSERT INTO mot (libelle) VALUES (?)");
			myStatement.setString(1,entity.getLibelle());
			myStatement.execute();	
			
			myStatement = db.prepareStatement("SELECT LAST_INSERT_ID()");
			myResult = myStatement.executeQuery();
			myResult.next();
			entity.setId(myResult.getInt(1));
		}
		
		return entity;
	}

	@Override
	public void delete(Mot entity) throws SQLException {
		// TODO Auto-generated method stub
		deleteById(entity.getId());
			
	}

	@Override
	public void deleteById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement myStatement = db.prepareStatement("DELETE FROM mot WHERE id=?");
		myStatement.setInt(1,id);	
		myStatement.execute();
		
	}
	
	//MODIFIER
	public ArrayList<Mot> creerListeMots(Grille maGrille) throws SQLException{
		ArrayList<Mot> mots = new ArrayList<Mot>();
		int taille = maGrille.getDifficulte().getValeur()*maGrille.getDifficulte().getValeur();
		PreparedStatement myStatement = db.prepareStatement("SELECT * FROM mot order by rand() limit ?");
		myStatement.setInt(1,taille);
		ResultSet myResult = myStatement.executeQuery();
		
		int compteur = 0;
		
		while(myResult.next()) {
			mots.add(em.getMot(myResult));
			myStatement = db.prepareStatement("UPDATE mot SET used = 1 where id = ?");
			myStatement.setInt(1,myResult.getInt("id"));
			compteur++;
		}
		
		if (compteur < taille) {
			mots.clear();
			myStatement = db.prepareStatement("UPDATE mot SET used = 0");
			myResult = myStatement.executeQuery("SELECT * FROM mot WHERE used = 0 order by rand() limit " + taille);
			while (myResult.next()) {
				mots.add(em.getMot(myResult));
				myStatement = db.prepareStatement("UPDATE mot SET used = 1 where id = ?");
				myStatement.setInt(1,myResult.getInt("id"));
			}
		}
		
		
		return mots;
	}

	
	

}
