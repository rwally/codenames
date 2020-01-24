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

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOMot;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Partie;

@Controller
public class PlateauController {
	
	@Autowired
	IDAOMot daoMot;
	
	@Autowired
	IDAOCase daoCase;
	
	

	@GetMapping("/plateau")
	public String creationPlateau(Model model){
	
		//A SUPPRIMER
		Partie partie = new Partie();
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.facile);
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
		for(int i = 0; i < mots.size(); i++) {
			Case maCase = new Case();
			maCase.setMot(mots.get(0));
			mots.remove(0);
			
			if(i<9) {
				maCase.setCouleur(Couleur.bleu);
			}else if(i<17) {
				maCase.setCouleur(Couleur.rouge);
			}else if(i<partie.getGrille().getDifficulte().getValeur()) {
				maCase.setCouleur(Couleur.blanc);
			}else {
				maCase.setCouleur(Couleur.noir);
			}			
			
			daoCase.save(maCase);
			cases.add(maCase);
		}
		
		Collections.shuffle(cases);
		
		//Le plateau est crée
		partie.getGrille().setCases(cases);
		
		model.addAttribute("cases", cases);
		model.addAttribute("mots", mots);
		model.addAttribute("partie", partie);
		
		return "plateau";
	}

}
