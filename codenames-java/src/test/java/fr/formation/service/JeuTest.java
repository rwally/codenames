package fr.formation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.formation.Application;
import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Grille;
import fr.formation.model.Mot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ Application.class })
public class JeuTest {
	
	@Autowired
	IDAOMot daoMot;

	@Autowired
	IDAOCase daoCase;

	@Autowired
	IDAOGrille daoGrille;
	
	@Autowired
	Jeu jeu;
	
	@BeforeClass
	public static void avantPremierTest(){
		System.out.println("Démarrage des tests");
	}
	
	@Test
	@Transactional
	@Rollback
	public void testCreationListeMots() {

		Grille maGrille = new Grille();
		maGrille.setDifficulte(Difficulte.moyen);
		List<Mot> mots = new ArrayList<Mot>();
		int taille = maGrille.getDifficulte().getValeur() * maGrille.getDifficulte().getValeur();
		//Vérifie si bonne taille de grille
		assertEquals(25, taille);
		
		
		boolean motsOK = false;

		while (!motsOK) {
			motsOK = true;

			List<Mot> motsNotUsed = daoMot.findByUsed(false);
			//Vérifie si il a bien récupéré une liste de mots
			assertFalse(motsNotUsed.isEmpty());
			

			if (motsNotUsed.size() > taille) {
				List<Integer> nombresRandom = new Random()
						.ints(1, motsNotUsed.size())
						.distinct()
						.limit(taille)
						.boxed()
						.collect(Collectors.toList());
				//Vérifie si il a bien pris 25 nombres random
				assertEquals(25,nombresRandom.size());

				for (int i = 0; i < taille; i++) {
					Mot mot = motsNotUsed.get((nombresRandom.get(i)));
					//Vérifie si il a pris que des mots non used
					assertEquals(false,mot.isUsed());
					mots.add(mot);
					mot.setUsed(true);
					daoMot.save(mot);
					//Vérifie si il a bien save le mot et changé le used
					assertTrue(daoMot.findById(mot.getId()).isPresent());
					assertEquals(true,daoMot.findById(mot.getId()).get().isUsed());
					
					
				}
			} else {
				motsOK = false;
				for (Mot m : daoMot.findAll()) {
					m.setUsed(false);
					daoMot.save(m);
					//Vérifie si il a bien save le mot et changé le used
					assertTrue(daoMot.findById(m.getId()).isPresent());
					assertEquals(false,daoMot.findById(m.getId()).get().isUsed());
				}
			}
		}
		
		for(Mot m : mots) {
			assertNotNull(m);
			assertEquals(true,m.isUsed());
			
		}
		
		
	}
	
	@Test
	@Transactional
	@Rollback
	public void testCreationListeCases() {
		
		Grille maGrille = new Grille();
		maGrille.setDifficulte(Difficulte.moyen);
		
		List<Mot> listeMots = jeu.creerListeMots(maGrille);
		//Verif si on a bien recup la liste de mots
		for(Mot m : listeMots) {
			assertNotNull(m);
		}
		
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
					//Vérifie si bonne taille de grille
					assertEquals(25, taille);
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
				
				//Verif si bonne insertion
				assertNotNull(maCase.getMot());
				
				
				if(i<r) {
					maCase.setCouleur(Couleur.rouge);
					assertEquals(Couleur.rouge,maCase.getCouleur());
				}else if(i<b) {
					maCase.setCouleur(Couleur.bleu);
					assertEquals(Couleur.bleu,maCase.getCouleur());
				}else if(i<w) {
					maCase.setCouleur(Couleur.blanc);
					assertEquals(Couleur.blanc,maCase.getCouleur());
				}else {
					maCase.setCouleur(Couleur.noir);
					assertEquals(Couleur.noir,maCase.getCouleur());
				}			
				
				daoCase.save(maCase);
				//Verif save case
				assertTrue(daoCase.findById(maCase.getId()).isPresent());

				cases.add(maCase);
				//Verif si on a bien recup la liste de cases
				for(Case c : cases) {
					assertNotNull(c);
				}
		}
		
		Collections.shuffle(cases);
		
	}
	
	@Test
	@Transactional
	@Rollback
	public void testCreationGrille() {
		
		// Init de la liste de mots
		List<Mot> listeMots = new ArrayList<Mot>();

		// Init de la liste de cases
		List<Case> cases = new ArrayList<Case>();

		// Init de la grille
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.moyen);

		// Creation de la liste de mots
		listeMots = jeu.creerListeMots(grille);
		
		//Verif de la liste de mots
		for(Mot m : listeMots) {
			assertNotNull(m);
		}
		

		// Creation de la liste de cases avec les mots/couleurs
		cases =jeu.creerListeCase(listeMots, grille);
		//Verif de la liste de cases
		for(Case c : cases) {
			assertNotNull(c);
			assertNotNull(c.getMot());
			assertNotNull(c.getCouleur());
			assertEquals(true,c.getMot().isUsed());
		}

		// affection de la liste de cases a la grille et inverse
		grille.setCases(cases);
		//Verifie l'attribution des cases à la grille
		for(Case c : grille.getCases()) {
			assertNotNull(c);
		}
		
		daoGrille.save(grille);
		//Verif si la grille est bien save
		assertTrue(daoGrille.findById(grille.getId()).isPresent());
		
		for (Case c : cases) {
			c.setGrille(grille);
			daoCase.save(c);
			//Verif si la grille est bien save DANS LA CASE
			assertEquals(daoGrille.findById(grille.getId()).get().getId(),
					daoCase.findById(c.getId()).get().getGrille().getId());
		}
	}
	
	
	

}
