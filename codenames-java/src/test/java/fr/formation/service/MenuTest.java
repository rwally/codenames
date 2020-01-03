package fr.formation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.Optional;

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
import fr.formation.dao.IDAOJoueur;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
@Transactional
@Rollback(true)
public class MenuTest {

	@Autowired
	private Menu menu;
	
	@Autowired
	private IDAOJoueur daoJoueur;
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	@BeforeClass
	public static void debut() {
		System.out.println("Début du test Menu");
	}
	
	@AfterClass
	public static void fin() {
		System.out.println("Fin du tes Menu");
	}

	@Test
	public void connectionTest() {

		try {
			System.out.println("************************************");
			System.out.println("TEST CONNECTION");
			Joueur joueur = new Joueur();

			joueur = menu.connection();

			// Le joueur est dans la base de données
			assertNotEquals(0, joueur.getId());
			assertTrue(daoJoueur.findById(joueur.getId()).isPresent());
			
			// Le joueur enregistré n'est pas un utilisateur
			Optional<Utilisateur> utilisateur;
			utilisateur = daoUtilisateur.findById(joueur.getId());
			assertNotEquals(utilisateur, joueur);
			
			// Vérification des données
			assertEquals("Sinapayen", joueur.getNom());
			assertEquals("Adeline", joueur.getPrenom());
			assertEquals("09041993", joueur.getPassword());
			assertEquals("Adeline", joueur.getUsername());
			
			// Le joueur n'a pas uniquement le username et le mot de passe
			Joueur adeline = new Joueur();
			adeline.setUsername("Adeline");
			adeline.setPassword("09041993");
			assertNotEquals(adeline, joueur);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void inscriptionTest() {
		try {
			System.out.println("**********************************************");
			System.out.println("TEST INSCRIPTION");
			Joueur joueur = new Joueur();
			Optional<Utilisateur> utilisateur;
			
			joueur = menu.inscription();
			
			utilisateur = daoUtilisateur.findById(joueur.getId());
			
			// Le joueur est dans la base de données
			assertNotEquals(0, joueur.getId());
			assertTrue(daoJoueur.findById(joueur.getId()).isPresent());
			
			// L'utilisateur est dans la base de données
			assertNotEquals(0, utilisateur.get().getId());
			
			// Le joueur enregistré n'est pas un utilisateur
			assertNotEquals(utilisateur, joueur);
			
			// Vérification des données
			assertEquals("Simon", joueur.getNom());
			assertEquals("Marcellin", joueur.getPrenom());
			assertEquals("123456", joueur.getPassword());
			assertEquals("Marcellin", joueur.getUsername());
			
			// Le joueur n'a pas uniquement le username et le mot de passe
			Joueur nouveau = new Joueur(0, "Simon", "Marcellin", "Marcellin", "123456");
			assertNotEquals(nouveau, joueur);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}

	}
}
