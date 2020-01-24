package fr.formation.validator;

import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.formation.model.Joueur;


public class UtilisateurValidator implements Validator{

	@Override
	public boolean supports(Class<?> cls) {
		return Joueur.class.equals(cls);
	}

	@Override
	public void validate(Object target, Errors e) {
		ValidationUtils.rejectIfEmptyOrWhitespace(e, "password2", "password2.empty", "Veuillez confirmer votre mot de passe");
		
	}


}
