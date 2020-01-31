package fr.formation.controller; 
 
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOChat;
import fr.formation.dao.IDAOGrille; 
import fr.formation.dao.IDAOParticipation; 
import fr.formation.dao.IDAOPartie; 
import fr.formation.dao.IDAOTour;
import fr.formation.model.Case;
import fr.formation.model.Grille;
import fr.formation.model.Participation; 
import fr.formation.model.Partie; 
import fr.formation.model.Tour; 
 
@Controller
public class VictoireController { 
	 
	@Autowired 
	private IDAOPartie daoPartie; 
	 
	@Autowired 
	IDAOTour daoTour; 
	 
	@Autowired 
	private IDAOGrille daoGrille; 
	 
	@Autowired 
	private IDAOParticipation daoParticipation; 
	
	@Autowired
	private IDAOCase daoCase;
	
	@Autowired
	private IDAOChat daoChat;
 
	 
 
	@GetMapping("/pageVictoire") 
	public String victoire (Model model, HttpSession session){ 
		Tour tour = daoTour.findById((Integer) session.getAttribute("tour_id")).get(); 
		Partie partie = daoPartie.findById((Integer) session.getAttribute("partie_id")).get();

		model.addAttribute("tour",tour); 
		model.addAttribute("partie",partie); 
		return "pageVictoire"; 	 
	} 
	 
	@PostMapping("/pageVictoire") 
	@Transactional
	public String supprimerParticipations(HttpSession session) { 
		Partie partieTermine = daoPartie.findById((Integer) session.getAttribute("partie_id")).get();
		Hibernate.initialize(partieTermine.getTours());
		for (Tour t : partieTermine.getTours()) {
			daoTour.delete(t);
		}
		for (Participation p : partieTermine.getJoueurs()) { 
			daoParticipation.delete(p); 
		} 
		
		daoChat.deleteAll();
		daoCase.deleteAll();		
		
		Grille grille = partieTermine.getGrille();
		
		daoPartie.delete(partieTermine); 
		daoGrille.delete(grille);
		return "redirect:/connexion"; 
	} 
} 