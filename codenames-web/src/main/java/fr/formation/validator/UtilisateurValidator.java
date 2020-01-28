package fr.formation.validator;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;
import fr.formation.model.UtilisateurForm;

@Component
public class UtilisateurValidator implements Validator{

	@Autowired
	private IDAOUtilisateur daoUtilisateur;

	@Override
	public boolean supports(Class<?> cls) {
		return Joueur.class.equals(cls);
	}

	@Override
	public void validate(Object target, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "nom", "nom.empty", "Veuillez entrer votre nom");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "prenom", "prenom.empty", "Veuillez entrer votre prenom");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "username", "username.empty", "Veuillez entrer votre nom d'utilisateur");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password", "password.empty", "Veuillez entrer votre mot de passe");
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password2", "password2.empty", "Veuillez entrer à nouveau votre mot de passe");
		
		UtilisateurForm utilisateur = (UtilisateurForm) target;
		if (!utilisateur.getPassword().equals(utilisateur.getPassword2()) ) {
			e.rejectValue("password2", "password2.notSame","Les mots de passe sont differends.");
		}
		
		List<Utilisateur> utilisateurs = daoUtilisateur.findAll();
		
		for (Utilisateur u : utilisateurs) {
			if (u.getUsername().equals(utilisateur.getUsername())) {
				e.rejectValue("username", "username.unique", "Ce nom d'utilisateur existe déjà");
				break;
			}
		}
		
		
	}


}
