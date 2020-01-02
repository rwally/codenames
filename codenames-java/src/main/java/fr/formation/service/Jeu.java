package fr.formation.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.model.Case;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Role;

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
		cases = daoCase.creerListeCase(listeMots, grille);

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

			List<Mot> motsNotUsed = daoMot.findIfNotUsed();

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

}