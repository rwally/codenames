package fr.formation.DAO;

	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
import java.util.List;

import fr.formation.model.Joueur;
import fr.formation.model.Mot;
import fr.formation.model.Utilisateur;



	public class EntityManager {
		
		private ArrayList<Mot> mots = new ArrayList<Mot>();
		
		public EntityManager() {
			
		}
		
		public Mot getMot(ResultSet result) throws SQLException {
			int id =result.getInt("id");
			for(Mot m : this.mots) {
				if(m.getId()==id) {
					return m;
				} 
			}
			Mot monMot = new Mot();
			monMot.setId(result.getInt("id"));
			monMot.setLibelle(result.getString("libelle"));

			return monMot;
		}
		

		private List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();

		public Utilisateur getUtilisateur(ResultSet result) throws SQLException {

			try {
				int id = result.getInt("id");

				for (Utilisateur u : this.utilisateurs) {
					if (u.getId() == id) {
						return u;
					}
				}

				Utilisateur utilisateur = new Joueur();
				utilisateur.setId(result.getInt("id"));
				utilisateur.setNom(result.getString("nom"));
				utilisateur.setPrenom(result.getString("prenom"));
				utilisateur.setUsername(result.getString("username"));
				utilisateur.setPassword(result.getString("password"));

				this.utilisateurs.add(utilisateur);
				return utilisateur;

			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}



