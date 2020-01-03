package fr.formation.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.formation.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class PartieTest {


	@BeforeClass
	public static void debut() {
		System.out.println("Début du test partie");
	}

	@AfterClass
	public static void fin() {
		System.out.println("Fin du test partie");
	}

	@Test
	public void setMasterTest() {
		try {
			Partie partie = new Partie();
			
			Equipe equipe = new Equipe();
			List<Joueur> joueurs = new ArrayList<Joueur>();
			
			for (int i = 0; i < 10; i++) {
				Joueur joueur = new Joueur();
				Participation participation = new Participation(Role.agent);
				participation.setJoueurs(joueur);
				joueur.setSaParticipation(participation);
				joueurs.add(joueur);
			}
			
			equipe.setJoueurs(joueurs);
			
			partie.setMaster(equipe);
			
			int nbMaster = 0;
			int nbAgent = 0;
			
			for (Joueur j : equipe.getJoueurs()) {
				if (j.getSaParticipation().getRole().equals(Role.master)) {
					nbMaster ++;
				}
				if (j.getSaParticipation().getRole().equals(Role.agent)) {
					nbAgent ++;
				}
			}
			
			assertEquals(1, nbMaster);
			assertNotEquals(0, nbMaster);
			
			assertEquals(9, nbAgent);
			
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
