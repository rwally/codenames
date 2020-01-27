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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.dao.IDAOParticipation;
import fr.formation.dao.IDAOPartie;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;

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
	
	
	//Création partie
	@GetMapping("/plateau")
	public String creationPlateau(Model model){
	
		Partie partie = new Partie();
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.facile);//TEST
		partie.setGrille(grille);
		
		/*
		 * CREER LISTE PARTICIPANTS
		 */
		
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
		daoPartie.save(partie);

		
		//On a besoin que de partie (ou tour)
		model.addAttribute("cases", cases); //TEST
		
		
		
		Participation participant = new Participation();//TEST
		participant.setRole(Role.master);//TEST
		model.addAttribute("participant", participant);//TEST
		
		model.addAttribute("partie", partie);
		
		return "plateau";//ON DOIT REDIRIGER VERS LA LISTE DE JOUEURS
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
	


}
