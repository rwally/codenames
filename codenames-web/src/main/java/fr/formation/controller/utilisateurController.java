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
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Joueur;

@Controller
public class utilisateurController {
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	
	@GetMapping("/listeJoueurs")
	public String findAll(Model model) {
		model.addAttribute("utilisateurs", daoUtilisateur.findAll());
		return "listeJoueurs";
	}
	
	
	@GetMapping("/inscription")
	public String ajouterUtilisateur(Model model) {
		model.addAttribute("utilisateur",  new Joueur());
		
			return "inscription";
	}
	
	@PostMapping("/inscription")
	public String ajouterUtilisateur(
			@Valid
			@ModelAttribute Joueur monJoueur,
			BindingResult result, Model model) {
		
		if(result.hasErrors ()) {
			return "redirect:/inscription";
		}


		daoUtilisateur.save(monJoueur);
		return "redirect:/listeJoueurs";
	}
	
	@GetMapping("/supprimerUtilisateur")
	public String supprimerUtilisateur(
			@RequestParam int id) {
		
		daoUtilisateur.deleteById(id);
		return "redirect:/listJoueurs";
	}
	
	
	@GetMapping("/editerUtilisateur")
	public String edit(Model model, @RequestParam int id) {
		Joueur j = (Joueur) daoUtilisateur.findById(id).orElse(null);
		model.addAttribute("joueur",j);
		return "/listeJoueurs";
	}
	
	@PostMapping("/editerUtilisateur")
	public String edit(@Valid
			@ModelAttribute Joueur monJoueur, BindingResult result, Model model) {
		
		if(result.hasErrors ()) {
			return "listeJoueurs";
		}
		
		daoUtilisateur.save(monJoueur);
		return "redirect:/listeJoueurs";
	}
	

	
	
	
	
	
	
}
