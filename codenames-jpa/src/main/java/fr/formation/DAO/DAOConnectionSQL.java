package fr.formation.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.formation.DAO.EntityManager;

public abstract class DAOConnectionSQL {
	
	protected static Connection db = null;
	protected static EntityManager em = null;

	public DAOConnectionSQL() {
		
		try {
			if(db==null) {
				db = DriverManager.getConnection("jdbc:mysql://localhost:3306/codenames?serverTimezone=UTC","root","marseille");
			}
			
			if(em==null) {
				em= new EntityManager();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	
}
