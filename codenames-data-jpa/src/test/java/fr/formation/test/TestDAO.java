package fr.formation.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.formation.config.JpaConfig;
import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOMot;
import fr.formation.dao.IDAOUtilisateur;
import fr.formation.model.Joueur;
import fr.formation.model.Utilisateur;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ JpaConfig.class })
public class TestDAO {

	
	@Autowired
	private IDAOMot daoMot;
	
	@Autowired
	private IDAOUtilisateur daoUtilisateur;
	
	@Autowired
	private IDAOCase daoCase;

	
	@BeforeClass
	public static void BeforeClass() {
		System.out.println("Démarrage TESTS DAO CodeNames");
	}
	
	
	@Test
	public void TestDAOMotExists() {
		assertNotNull(daoMot);
	}
	
	
	@Test
	public void TestDAOUtilisateurExists() {
		assertNotNull(daoUtilisateur);
	}
	
	@Test
	public void TestDAOCaseExists() {
		assertNotNull(daoCase);
	}
	
	@Test
	public void testFindById() {
		try {
			Optional<Utilisateur> testJoueur = daoUtilisateur.findById(1);
			assertNotNull(testJoueur);
			assertTrue(testJoueur.isPresent());
			assertEquals(1, testJoueur.get().getId());
		}
		catch(Exception e){
			fail(e.getMessage());
		}
	}
	
	
	@Test
	@Transactional
	@Rollback
	public void TestAjoutUtilisateur() {
		
		try {
			Utilisateur monJoueur = new Joueur();
			monJoueur.setNom("Nom_Test");
			monJoueur.setPrenom("Prenom_Test");
			monJoueur.setUsername("Username_Test");
			monJoueur.setPassword("Password_Test");
			
			assertEquals(0, monJoueur.getId());
			
			daoUtilisateur.save(monJoueur);
			assertNotEquals(0, monJoueur.getId());
			assertTrue(daoUtilisateur.findById(monJoueur.getId()).isPresent());
		}
		catch(Exception e) {
			fail(e.getMessage());
			System.out.println("Err de Test Ajout");
		}
	}
	
	
	@Test
	@Transactional
	@Rollback
	public void TestSupressionProduit() {
		
		try {
			daoUtilisateur.deleteById(1);
			Optional<Utilisateur> monProduit = daoUtilisateur.findById(1);
			assertNotNull(daoUtilisateur);
			//assertFalse(daoUtilisateur.isPresent());
			
		}
		catch(Exception e) {
			fail(e.getMessage());
			System.out.println("Err de Test Supression");
		}
	}
	
}
