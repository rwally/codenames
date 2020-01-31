package fr.formation.controller; 
 
import javax.servlet.http.HttpSession; 
 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
 
import fr.formation.dao.IDAOGrille; 
import fr.formation.dao.IDAOParticipation; 
import fr.formation.dao.IDAOPartie; 
import fr.formation.dao.IDAOTour; 
import fr.formation.model.Participation; 
import fr.formation.model.Partie; 
import fr.formation.model.Tour; 
 
@Controller() 
@RequestMapping("/PageVictoire") 
public class VictoireController { 
	 
	@Autowired 
	private IDAOPartie daoPartie; 
	 
	@Autowired 
	IDAOTour daoTour; 
	 
	@Autowired 
	private IDAOGrille daoGrille; 
	 
	@Autowired 
	private IDAOParticipation daoParticipation; 
 
	 
 
	@GetMapping("") 
	public String victoire (Model model, @RequestBody Tour tour, HttpSession session){ 
		tour = daoTour.findById((Integer) session.getAttribute("tour_id")).get(); 
		Partie partie = daoPartie.findById((Integer) session.getAttribute("partie_id")).get();

		model.addAttribute("tour",tour); 
		model.addAttribute("partie",partie); 
		return "redirect:/pageVictoire"; 	 
	} 
	 
	@PostMapping("") 
	public String supprimerParticipations(HttpSession session) { 
		Partie partieTermine = daoPartie.findById((Integer) session.getAttribute("partie_id")).get(); 
		for (Participation p : partieTermine.getJoueurs()) { 
			daoParticipation.delete(p); 
		} 
		daoPartie.delete(partieTermine); 
		return "redirect:/connexion"; 
	} 
} 