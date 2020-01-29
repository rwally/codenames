package fr.formation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.dao.IDAOParticipation;
import fr.formation.dao.IDAOPartie;
import fr.formation.dao.IDAOTour;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Tour;

@Controller
public class PlateauController {
	
	
	@Autowired
	IDAOMot daoMot;
	
	@Autowired
	IDAOCase daoCase;
	
	@Autowired
	IDAOParticipation daoParticipation;
	
	@Autowired
	IDAOPartie daoPartie;
	
	@Autowired
	IDAOGrille daoGrille;
	
	@Autowired
	IDAOTour daoTour;
	
	
	//Création partie à mapper sur le bouton Création partie (après Connexion/Inscription) de la page Création partie/Rejoindre partie
	//Ce bouton envoie ensuite sur liste joueurs
	//@GetMapping("/listeJoueurs")
	public String creationPartie(Model model){
		Partie partie = new Partie();
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
			}else if(i<17) {
				maCase.setCouleur(Couleur.rouge);
				maCase.setImageMaster("https://i.imgur.com/SAcppjf.png");
			}else if(i<partie.getGrille().getDifficulte().getValeur()) {
				maCase.setCouleur(Couleur.blanc);
				maCase.setImageMaster("https://i.imgur.com/LRk4Jee.png");
			}else {
				maCase.setCouleur(Couleur.noir);
				maCase.setImageMaster("https://i.imgur.com/AfRrEMz.png");
			}			
			
			
			daoCase.save(maCase);
			cases.add(maCase);
		}
		
		Collections.shuffle(cases);
		
		//Le plateau est crée
		grille.setCases(cases);
		daoGrille.save(grille);
		partie.setGrille(grille);
		
		/*
		 * AJOUTER LA PARTIE AU PARTICIPANT ET SAVE PARTICIPANT
		 * AJOUTER LE PARTICIPANT
		 */
		
		daoPartie.save(partie);
	
		
		return "listeJoueurs";//ON DOIT REDIRIGER VERS LA LISTE DE JOUEURS AVEC L'IDPARTIE ET L'IDPARTICIPATION
	} 
	
	
	
	/*
	 Master bleu	https://i.imgur.com/LDcUXHC.png
	 Master rouge	https://i.imgur.com/SAcppjf.png
	 Master noir	https://i.imgur.com/AfRrEMz.png
	 Master blanc	https://i.imgur.com/LRk4Jee.png
	 
	 Agent			https://i.imgur.com/LRk4Jee.png
	 
	 Trouver bleu	https://i.imgur.com/miuPPRe.png
	 Trouver rouge	https://i.imgur.com/hTeiagx.png
	 Trouver noir 	https://i.imgur.com/QWHCuAR.png
	 Trouver blanc	https://i.imgur.com/7MeDbpE.png

	 */
	
	//A mapper sur le bouton de Liste Joueurs qui va lancer la partie
	public String lancerJeu(Model model, @RequestParam int idPartie, @RequestParam int idParticipant) {
		Partie partie = daoPartie.findById(idPartie).get();
		Tour tour = new Tour();
		tour.setPartie(partie);
		//On assigne l'equipe au tour (l'equipe bleue commence toujours)
		for(Participation p : partie.getJoueurs()) {
			if(p.getJoueur().getEquipe().getNom()=="bleu") {
				tour.setEquipe(p.getJoueur().getEquipe());
			}
		}
		
		daoTour.save(tour);
		daoPartie.save(partie);
		
		//On lance le plateau jeu avec l'idTour et l'idParticipant en param ou path variable
		return "redirect:/plateau";
		
	}
	
	//Partie en cours
	@GetMapping("/plateau")
	public String plateauJeu(Model model, @RequestParam int idTour, @RequestParam int idParticipant) {
		model.addAttribute("tour", daoTour.findById(idTour).get());
		model.addAttribute("participant", daoParticipation.findById(idParticipant).get());
		return "plateau";
	}
	
	
	/*
	 * RECUPERE LE NOMBRE DE MOTS A DEVINER
	 * 
	 */
	@PostMapping("/plateau")
	public String plateauJeu(@ModelAttribute Tour tour) {
		daoTour.save(tour);
		//On relance le plateau jeu avec l'idTour et l'idParticipant en param ou path variable
		return "redirect:/plateau";
	}
	
	
	/*
	 * BOUCLE DE JEU
	 * 
	 */
	
	

}
