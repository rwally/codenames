package fr.formation.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.formation.Application;
import fr.formation.service.Jeu;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class TourTest {
	
	@Autowired
	Jeu jeu;

	@BeforeClass
	public static void debut() {
		System.out.println("Début de test tour");
	}

	@AfterClass
	public static void fin() {
		System.out.println("Fin du test tour");
	}
	
	@Test
	@Transactional
	@Rollback
	public void voteJoueurTest() {
		System.out.println("****************************");
		System.out.println("TEST VOTE JOUEUR");
		try {
			Tour tour = new Tour();
			Participation participation = new Participation();
			participation.setRole(Role.agent);
			
			Grille grille = new Grille();
			grille = jeu.creationGrille();

			jeu.afficherGrille(grille, participation);
			
			System.out.println("Choisissez le premier mot de la liste");

			Case laCase = tour.voteJoueur(grille);
			
			assertNotNull(laCase);
			assertEquals(grille.getCases().get(0), laCase);
			assertNotEquals(grille.getCases().get(1), laCase);
	
			
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	@Transactional
	@Rollback
	public void voteGlobalTest() {
		System.out.println("*******************************");
		System.out.println("TEST VOTE GOBAL");
		try {
			Tour tour = new Tour();
			
			Grille grille = new Grille();
			grille = jeu.creationGrille();
			
			List<Case> votes = new ArrayList<Case>();
			votes.add(grille.getCases().get(1));
			votes.add(grille.getCases().get(0));
			votes.add(grille.getCases().get(1));
			votes.add(grille.getCases().get(2));
			
			Case laCase = tour.voteGlobal(votes);
			
			assertNotNull(laCase);
			assertEquals(grille.getCases().get(1), laCase);
			assertNotEquals(grille.getCases().get(0), laCase);
			
			// test si une égalité
			votes.add(grille.getCases().get(0));
			laCase = tour.voteGlobal(votes);
			
			assertNull(laCase);
			assertNotEquals(grille.getCases().get(0),laCase);
			assertNotEquals(grille.getCases().get(1), laCase);
			
			// test si plusieurs égalités
			votes.add(grille.getCases().get(2));
			laCase = tour.voteGlobal(votes);
			
			assertNull(laCase);
			assertNotEquals(grille.getCases().get(0),laCase);
			assertNotEquals(grille.getCases().get(1), laCase);
			
			votes.add(grille.getCases().get(2));
			laCase = tour.voteGlobal(votes);
			
			assertNotNull(laCase);
			assertEquals(grille.getCases().get(2), laCase);
			
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
