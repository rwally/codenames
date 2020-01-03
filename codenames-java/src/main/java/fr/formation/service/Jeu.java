package fr.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Role;
import fr.formation.service.Jeu;

@Service
public class Jeu {

	@Autowired
	IDAOMot daoMot;

	@Autowired
	IDAOCase daoCase;

	@Autowired
	IDAOGrille daoGrille;

	public Grille creationGrille() {
		// Init de la liste de mots
		List<Mot> listeMots = new ArrayList<Mot>();

		// Init de la liste de cases
		List<Case> cases = new ArrayList<Case>();

		// Init de la grille
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.moyen);

		// Creation de la liste de mots
		listeMots = creerListeMots(grille);

		// Creation de la liste de cases avec les mots/couleurs
		cases = creerListeCase(listeMots, grille);

		// affection de la liste de cases a la grille et inverse
		grille.setCases(cases);
		daoGrille.save(grille);
		for (Case c : cases) {
			c.setGrille(grille);
			daoCase.save(c);
		}

		return grille;

	}

	public void afficherGrille(Grille grille, Participation participant) {

		int k = 0;
		for (int i = 0; i < grille.getDifficulte().getValeur(); i++) {
			System.out.println();
			for (int j = 0; j < grille.getDifficulte().getValeur(); j++) {
				System.out.print(grille.getCases().get(k).getMot().getLibelle());
				if (grille.getCases().get(k).isTrouver() || participant.getRole() == Role.master) {
					System.out.print(" " + grille.getCases().get(k).getCouleur() + "\t");
				} else {
					System.out.print("\t");
				}
				k++;
			}
		}
	}

	public List<Mot> creerListeMots(Grille maGrille) {

		List<Mot> mots = new ArrayList<Mot>();
		int taille = maGrille.getDifficulte().getValeur() * maGrille.getDifficulte().getValeur();
		boolean motsOK = false;

		while (!motsOK) {
			motsOK = true;

			List<Mot> motsNotUsed = daoMot.findByUsed(false);
			

			if (motsNotUsed.size() > taille) {
				List<Integer> nombresRandom = new Random().ints(1, motsNotUsed.size()).distinct().limit(taille).boxed()
						.collect(Collectors.toList());

				for (int i = 0; i < taille; i++) {
					Mot mot = motsNotUsed.get((nombresRandom.get(i)));
					mots.add(mot);
					mot.setUsed(true);
					daoMot.save(mot);
				}
			} else {
				motsOK = false;
				for (Mot m : daoMot.findAll()) {
					m.setUsed(false);
					daoMot.save(m);
				}
			}
		}

		return mots;
	}
	
	public List<Case> creerListeCase(List<Mot> listeMots, Grille maGrille) {
		// TODO Auto-generated method stub
		ArrayList<Case> cases = new ArrayList<Case>();
		int taille = 0, r = 0,b = 0,w = 0; //rouge bleu w=blanc
		
		switch(maGrille.getDifficulte().getValeur()) {
			case 4: taille=16;
					r=6;
					b=11;
					w=16;
					break;
					
			case 5: taille=25;
					r=9;
					b=17;
					w=24;
					break;
					
			case 6 :taille=36;
					r=12;
					b=23;
					w=33;
					break;
		} 
		
		
		for(int i = 0; i < taille; i++) {
				Case maCase = new Case();
				maCase.setMot(listeMots.get(0));
				listeMots.remove(0);
				if(i<r) {
					maCase.setCouleur(Couleur.rouge);
				}else if(i<b) {
					maCase.setCouleur(Couleur.bleu);
				}else if(i<w) {
					maCase.setCouleur(Couleur.blanc);
				}else {
					maCase.setCouleur(Couleur.noir);
				}			
				
				daoCase.save(maCase);
				cases.add(maCase);
		}
		
		Collections.shuffle(cases);
		
		return cases;
		
	}

}