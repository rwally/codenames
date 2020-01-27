package fr.formation.validateur;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

@Component
public class connexionValidateur implements Validator {
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return Joueur.class.equals(clazz);
	}
	
	

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		List<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();
		listeUtilisateur = daoUtilisateur.findAll();
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username", "username.empty", "Le nom d'utilisateur doit être saisi!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"password", "password.empty", "Le mot de passe doit être saisi!");
        
        Joueur joueur = (Joueur) target;
        
//        try {
        	
        	
        	for (Utilisateur u : listeUtilisateur) {
				if (u.getUsername().equals(joueur.getUsername()) ) {
					// CONNEXION
					
					Joueur joueurBase = (Joueur) daoUtilisateur.findByUsername(joueur.getUsername());
		        	
		    		if(!joueur.getPassword().contentEquals(joueurBase.getPassword())) {
		        		
		        		errors.rejectValue("password", "password.invalid", "Mot de passe Erroné");
		        	}
					
					

				}
			}
        	
//        	
//        }catch(NullPointerException e) {
//        	errors.rejectValue("username","user.notExists", "Ce username n'existe pas !");
        	
        	
//        }

	}
}


