package fr.formation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOEquipe;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOMot;
import fr.formation.dao.IDAOParticipation;
import fr.formation.dao.IDAOPartie;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Equipe;
import fr.formation.model.Grille;
import fr.formation.model.Joueur;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
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
	
	@Autowired
	private IDAOPartie daoPartie;
	
	@Autowired
	private IDAOGrille daoGrille;
	
	@Autowired
	private IDAOMot daoMot;
	
	@Autowired
	private IDAOCase daoCase;
	
	@GetMapping("/presentation")
	public String afficherPresentation() {
		return "presentation";
	}

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
		return "redirect:/choixPartie";
	}
	
	@GetMapping("/choixPartie")
	public String choixPartie(Model model) {
		model.addAttribute("parties", daoPartie.findAll());
		return "choixPartie";
	}
	
	@PostMapping("/choixPartie")
	public String choixPartie(@RequestParam(required=false, defaultValue="0") int id, 
							HttpSession session) {
		Participation participation = daoParticipation.findById((Integer) session.getAttribute("participation_id")).get();
		
		Partie partie = null;
		
//		if (button.contentEquals("Créer une nouvelle partie")) {
//			System.out.println("passe ici");
//			id = 0;
//		}
		
		if (id == 0) {
			partie = new Partie();
			Grille grille = new Grille();
			grille.setDifficulte(Difficulte.facile);//TEST
			partie.setGrille(grille);
			
			
			
			//Création des 25 mots
			List<Mot> mots = new ArrayList<Mot>();
			List<Integer> nombresRandom = new Random().ints(1, 699).distinct().limit(25).boxed()
						.collect(Collectors.toList());
			
			for(int n : nombresRandom) {
				mots.add(daoMot.findById(n).get());
			}
			
			//Création des cases 
			List<Case> cases = new ArrayList<Case>();
			for(int i = 0; i < 25; i++) {
				Case maCase = new Case();
				maCase.setMot(mots.get(i));
				
				if(i<9) {
					maCase.setCouleur(Couleur.bleu);
					maCase.setImageMaster("https://i.imgur.com/LDcUXHC.png");
					maCase.setImage("https://i.imgur.com/LRk4Jee.png");
				}else if(i<17) {
					maCase.setCouleur(Couleur.rouge);
					maCase.setImageMaster("https://i.imgur.com/SAcppjf.png");
					maCase.setImage("https://i.imgur.com/LRk4Jee.png");
				}else if(i<partie.getGrille().getDifficulte().getValeur()) {
					maCase.setCouleur(Couleur.blanc);
					maCase.setImageMaster("https://i.imgur.com/LRk4Jee.png");
					maCase.setImage("https://i.imgur.com/LRk4Jee.png");
				}else {
					maCase.setCouleur(Couleur.noir);
					maCase.setImageMaster("https://i.imgur.com/AfRrEMz.png");
					maCase.setImage("https://i.imgur.com/LRk4Jee.png");
				}			
				
				
				daoCase.save(maCase);
				cases.add(maCase);
			}
			
			Collections.shuffle(cases);
			
			//Le plateau est crée
			grille.setCases(cases);
			daoGrille.save(grille);
			partie.setGrille(grille);

			participation.setMaPartie(partie);
			
			daoPartie.save(partie);
		}
		else {
			participation.setMaPartie(daoPartie.findById(id).get());
		}
		
		daoParticipation.save(participation);
		
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
		
		//participation.getJoueur().setEquipe(nouvelleEquipe);
		
		Joueur joueur = daoJoueur.findById(participation.getJoueur().getId()).get();
		joueur.setEquipe(nouvelleEquipe);
		
		daoEquipe.save(nouvelleEquipe);
		daoJoueur.save(joueur);
		
		int id = participation.getMaPartie().getId();
		session.setAttribute("partie_id", id);
		
		return "redirect:/listeJoueurs";
	}
	
	
}
