package fr.formation.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.formation.dao.IDAOJoueur;
import fr.formation.model.Joueur;
import fr.formation.validateur.connexionValidateur;

@Controller
public class ConnexionController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private connexionValidateur conec;
	
//	connexionValidateur valid = new connexionValidateur();
	
	@GetMapping("/connexion")
	public String connexionUtilisateur(
			Model model) {
		model.addAttribute("joueur",  new Joueur());
		
			return "connexion";
	}
	
	@PostMapping("/connexion")
	public String connexionUtilisateur(
			@Valid	
			@ModelAttribute Joueur joueur,
			BindingResult result) {
		
		conec.validate(joueur, result);
		
		if(result.hasErrors ()) {
			
			System.out.println("erreur");
			System.out.println("nb d'erreurs dans result: " +result.getErrorCount());
			System.out.println(result.getFieldError());
			return "connexion";
		}
		
		return "redirect:/choixEquipe";
	}
	
	@ModelAttribute("utilisateurs")
	public List<Joueur> getJoueurs(){
		return daoJoueur.findAll();
	}

}
