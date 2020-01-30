package fr.formation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import fr.formation.dao.IDAOPartie;
import fr.formation.dao.IDAOTour;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;
import fr.formation.model.Tour;
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
	
	@Autowired
	private IDAOPartie daoPartie;
	
	@Autowired
	private IDAOTour daoTour;
	
	@Autowired
	private IDAOEquipe daoEquipe;
	
	
	@GetMapping("/listeJoueurs")
	public String findAll(Model model, HttpSession session) {
		int partieId = (int) session.getAttribute("partie_id");
		List<Participation> participations = daoParticipation.findByMaPartie(daoPartie.findById(partieId).get());
		model.addAttribute("participations", participations);
		model.addAttribute("partie_id", partieId);
		session.setAttribute("participations", participations);
		return "listeJoueurs";
	}
	
	@Transactional
	@PostMapping("/listeJoueurs")
	public String lancerPartie(HttpSession session) {
		
		int idPartie = (int) session.getAttribute("partie_id");
		Partie partie = daoPartie.findById(idPartie).get();
		
		Tour tour = null;
		
		// Attribution du tour à la partie
		
		if (partie.getTours().size() == 0) {
			tour = new Tour();
			tour.setPartie(partie);
			
			daoTour.save(tour);
		}
		else {
			int dernierElement = partie.getTours().size() - 1;
			tour = partie.getTours().get(dernierElement);
		}
		
		session.setAttribute("tour_id", tour.getId());
		
		//On assigne l'equipe au tour (l'equipe bleue commence toujours)

		tour.setEquipe(daoEquipe.findFirstByNom("Bleu"));
		
		
		
		daoTour.save(tour);
		daoPartie.save(partie);
		
		// Distribution des rôles
		List<Equipe> equipes = new ArrayList<Equipe>();
		equipes.add(daoEquipe.findById(3).get());
		equipes.add(daoEquipe.findById(4).get());
		
		List<Participation> participations = (List<Participation>) session.getAttribute("participations");
		
		for (Equipe e : equipes) {
			Hibernate.initialize(partie.getJoueurs());
			
			List<Joueur> joueurs = new ArrayList<Joueur>();
			
			for (int i = 0; i < participations.size(); i++) {
				Joueur joueur = daoJoueur.findBySaParticipationId(participations.get(i).getId());
				
				if (joueur.getEquipe() == e) {
					joueurs.add(joueur);
					participations.get(i).setRole(Role.agent);
					daoParticipation.save(participations.get(i));
				}
			}

			e.setJoueurs(joueurs);
			
			Joueur j = partie.getRandomElement(joueurs);
			
			Participation p = daoParticipation.findByJoueur(j);
			p.setRole(Role.master);
			daoParticipation.save(p);

			
		}
		
		return "redirect:/api/plateau";
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
	
//	@ModelAttribute("participations")
//	public List<Participation> getParticipations() {
//		return daoParticipation.findAll();
//	}
	
	
}
