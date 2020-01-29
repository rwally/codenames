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
public class UtilisateurController {
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOParticipation daoParticipation;
	
	@Autowired
	private IDAOEquipe daoEquipe;
	
	@Autowired
	private UtilisateurValidator utiValid;
	

	@GetMapping("/inscription")
	public String ajouterUtilisateur(Model model) {
		model.addAttribute("utilisateurForm",  new UtilisateurForm());
		return "inscription";
	}
	
	@PostMapping("/inscription")
	public String ajouterUtilisateur(
			@Valid
			@ModelAttribute UtilisateurForm utilisateurForm,
			BindingResult result, Model model,
			HttpSession session) {
		
		utiValid.validate(utilisateurForm, result);
		
		if(result.hasErrors ()) {
			return "inscription";
		}
		
		Utilisateur utilisateur = utilisateurForm.returnUtilisateur();
		
		Joueur joueur = new Joueur(0, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getUsername(), utilisateur.getPassword());
		daoJoueur.save(joueur);
		
		Participation participation = new Participation();
		participation.setJoueurs(joueur);
		participation.setRole(Role.agent);
		
		
		daoParticipation.save(participation);
		
		session.setAttribute("participation_id", participation.getId());
		return "redirect:/choixEquipe";
	}
	
	@GetMapping("/choixEquipe")
	public String choixEquipe(Model model) {
		return "choixEquipe";
	}
	
	@PostMapping("/choixEquipe")
	public String choixEquipe(@ModelAttribute Equipe equipe, HttpSession session) {
		List<Equipe> equipes = daoEquipe.findAll();
		
		Boolean existe = false;

		Participation participation = daoParticipation.findById((Integer) session.getAttribute("participation_id")).get();
		
		Equipe nouvelleEquipe = null;
		
		for (Equipe e : equipes) {
			if (e.getNom().equals(equipe.getNom())) {
				existe = true;
				nouvelleEquipe = e;
			}
		}
		
		if (!existe && equipe.getNom().equals("Bleu")) {
			nouvelleEquipe = new Equipe();
			nouvelleEquipe.setNom("Bleu");
		}
		else if (!existe && equipe.getNom().equals("Rouge")) {
			nouvelleEquipe = new Equipe();
			nouvelleEquipe.setNom("Rouge");
		}
		
		participation.getJoueur().setEquipe(nouvelleEquipe);
		daoEquipe.save(nouvelleEquipe);
		daoJoueur.save(participation.getJoueur());
		
		return "redirect:listeJoueurs";
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
