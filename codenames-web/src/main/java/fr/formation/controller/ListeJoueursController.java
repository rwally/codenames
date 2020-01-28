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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.dao.IDAOEquipe;
import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOParticipation;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.model.Utilisateur;
import fr.formation.model.UtilisateurForm;
import fr.formation.validator.UtilisateurValidator;

@Controller
public class ListeJoueursController {
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOParticipation daoParticipation;
	
	
	
	@GetMapping("/listeJoueurs")
	public String findAll(Model model) {
		model.addAttribute("participations", daoParticipation.findAll());
		return "listeJoueurs";
	}

	
	@GetMapping("/supprimerParticipation")
	public String supprimerParticipation(
			@RequestParam int id) {
		
		daoParticipation.deleteById(id);
		return "redirect:/listeJoueurs";
	}
	
	
	@GetMapping("/editerParticipation")
	public String edit(Model model, @RequestParam int id) {
		Participation p = (Participation) daoParticipation.findById(id).orElse(null);
		model.addAttribute("participation",p);
		return "choixEquipe";
	}
	
	@PostMapping("/editerParticipation")
	public String edit(@Valid
			@ModelAttribute Participation participation, BindingResult result, Model model) {
		
		if(result.hasErrors ()) {
			return "choixEquipe";
		}
		
		daoParticipation.save(participation);
		return "redirect:/listeJoueurs";
	}
	
	@ModelAttribute("participations")
	public List<Participation> getParticipations() {
		return daoParticipation.findAll();
	}
	
	
}
