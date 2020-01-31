package fr.formation.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOParticipation;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.validator.connexionValidateur;

@Controller
public class ConnexionController {
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOParticipation daoParticipation;
	
	@Autowired
	private connexionValidateur conec;
	
	@GetMapping("/connexion")
	public String connexionUtilisateur(Model model) {
		model.addAttribute("joueur",  new Joueur());
		return "connexion";
	}
	
	@PostMapping("/connexion")
	public String connexionUtilisateur(
			@Valid	
			@ModelAttribute Joueur joueur,
			BindingResult result,
			HttpSession session) {
		
		conec.validate(joueur, result);
		
		if(result.hasErrors ()) {
			return "connexion";
		}
		
		Participation participation = new Participation();
		participation.setJoueur(daoJoueur.findByUsername(joueur.getUsername()));
		participation.setRole(Role.agent);
		
		daoParticipation.save(participation);
		
		session.setAttribute("participation_id", participation.getId());
		
		return "redirect:/choixPartie";
	}
	
	@ModelAttribute("utilisateurs")
	public List<Joueur> getJoueurs(){
		return daoJoueur.findAll();
	}

}
