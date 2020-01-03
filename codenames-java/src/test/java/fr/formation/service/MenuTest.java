package fr.formation.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import fr.formation.model.Equipe;
import fr.formation.model.Joueur;
import fr.formation.model.Participation;
import fr.formation.model.Role;
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
		System.out.println("D�but du test Menu");
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

			// Le joueur est dans la base de donn�es
			assertNotEquals(0, joueur.getId());
			assertTrue(daoJoueur.findById(joueur.getId()).isPresent());
			
			// Le joueur enregistr� n'est pas un utilisateur
			Optional<Utilisateur> utilisateur;
			utilisateur = daoUtilisateur.findById(joueur.getId());
			assertNotEquals(utilisateur, joueur);
			
			// V�rification des donn�es
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
			
			// Le joueur est dans la base de donn�es
			assertNotEquals(0, joueur.getId());
			assertTrue(daoJoueur.findById(joueur.getId()).isPresent());
			
			// L'utilisateur est dans la base de donn�es
			assertNotEquals(0, utilisateur.get().getId());
			
			// Le joueur enregistr� n'est pas un utilisateur
			assertNotEquals(utilisateur, joueur);
			
			// V�rification des donn�es
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
	
	
	
	@Test
	public void TestMenuPrincipal() {

			try {
				
				Equipe equipeBleueTest = new Equipe();
				Equipe equipeRougeTest = new Equipe();

				equipeRougeTest.setNom("RougeTest");
				equipeBleueTest.setNom("BleueTest");
				
				Joueur monJoueur = new Joueur();
				
//				Menu menuTest = new Menu();
//				menuTest.menuPrincipal();
//				
//				String input = "1\n";
//				InputStream stringStream = new java.io.ByteArrayInputStream(input.getBytes());
//		
//				OutputStream outputStream = new java.io.ByteArrayOutputStream();
//				PrintStream printStream = new PrintStream(outputStream);
//				
//				String result = outputStream.toString();
//				
//				// V�rification du passage � la m�thode connection
//				assertEquals(result, "Bonjour, veuillez saisir 1 pour vous connecter ou 2 pour vous inscrire.");
//				assertEquals(System.out, "Bonjour, veuillez saisir 1 pour vous connecter ou 2 pour vous inscrire.");
				
				equipeBleueTest.AjouterMembre(monJoueur);
			
				monJoueur.setEquipe(equipeBleueTest);
				
				// V�rification des donn�es de l'�quipe bleue
				assertEquals(1,equipeBleueTest.getJoueurs().size());
				assertEquals("BleueTest",monJoueur.getEquipe().getNom());
				
				
			
				Joueur monJoueur2 = new Joueur();
				equipeRougeTest.AjouterMembre(monJoueur2);
				monJoueur2.setEquipe(equipeRougeTest);
				
				// V�rification des donn�es de l'�quipe bleue
				assertEquals(1,equipeRougeTest.getJoueurs().size());
				assertEquals("RougeTest",monJoueur2.getEquipe().getNom());
				
				Participation maParticipation = new Participation(Role.agent, monJoueur);
				Participation maParticipation2 = new Participation(Role.agent, monJoueur2);
				
				monJoueur.setSaParticipation(maParticipation);
				monJoueur2.setSaParticipation(maParticipation2);
				
				
				// V�rification des Participations
				assertEquals(maParticipation, monJoueur.getSaParticipation());
				assertEquals(maParticipation2, monJoueur2.getSaParticipation());
				
				// V�rification de leur role
				assertEquals(Role.agent, monJoueur.getSaParticipation().getRole());
				assertEquals(Role.agent, monJoueur2.getSaParticipation().getRole());
				
			} catch (Exception e) {
				fail(e.getMessage());
			}
		
	}
	
	
	
	
	
	
	
}
