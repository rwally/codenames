package fr.formation.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOEquipe;
import fr.formation.dao.IDAOGrille;
import fr.formation.dao.IDAOMot;
import fr.formation.dao.IDAOParticipation;
import fr.formation.dao.IDAOPartie;
import fr.formation.dao.IDAOTour;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Difficulte;
import fr.formation.model.Equipe;
import fr.formation.model.Grille;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;
import fr.formation.model.Tour;


@Controller
@RequestMapping("/plateau")
public class PlateauController {
	
	
	@Autowired
	IDAOMot daoMot;
	
	@Autowired
	IDAOCase daoCase;
	
	@Autowired
	IDAOParticipation daoParticipation;
	
	@Autowired
	IDAOPartie daoPartie;
	
	@Autowired
	IDAOGrille daoGrille;
	
	@Autowired
	IDAOTour daoTour;
	
	@Autowired
	IDAOEquipe daoEquipe;
	
	private List<SseEmitter> emitters = new ArrayList<SseEmitter>();	

	
	//Affichage principale
	@GetMapping
	@Transactional
	public String plateauJeu(Model model, HttpSession session) {
		Tour tour = daoTour.findById((Integer) session.getAttribute("tour_id")).get();
		Participation participant = daoParticipation.findById((Integer) session.getAttribute("participation_id")).get();
		
		model.addAttribute("participant", participant);
		model.addAttribute("tour", tour);
		
		Hibernate.initialize(tour.getPartie().getGrille().getCases());
		model.addAttribute("cases", tour.getPartie().getGrille().getCases());
		
		return "plateau";
		
	}
	
	
	//Recupere le nombre de mots à deviner
	@PostMapping
	public String nombreADeviner(@RequestParam int nombreMaster, HttpSession session) {
		Tour tour = daoTour.findById((Integer) session.getAttribute("tour_id")).get();
		tour.setNombreMaster(nombreMaster);
		daoTour.save(tour);
		return "redirect:/plateau";
	}	
	
	//Boucle de jeu, appelé après le clique sur une case
	@GetMapping("/{libelle}")
	@Transactional
	public String cliqueImage(@PathVariable String libelle, HttpSession session) {
			
		Tour tour = daoTour.findById((Integer) session.getAttribute("tour_id")).get();
	
		String equipeJouant = tour.getEquipe().getNom();
		List<Case> cases = tour.getPartie().getGrille().getCases();
		int nombreMaster=tour.getNombreMaster();
		
		String masterBleu ="https://i.imgur.com/LDcUXHC.png";
		String masterRouge ="https://i.imgur.com/SAcppjf.png";
		String masterNoir="https://i.imgur.com/AfRrEMz.png";
		String masterBlanc="https://i.imgur.com/LRk4Jee.png";
		String agent="https://i.imgur.com/LRk4Jee.png";
		String trouverBleu="https://i.imgur.com/miuPPRe.png";
		String trouverRouge="https://i.imgur.com/hTeiagx.png";
		String trouverNoir="https://i.imgur.com/QWHCuAR.png";
		String trouverBlanc="https://i.imgur.com/7MeDbpE.png";
		
		int caseTrouverBleu=0, caseTrouverRouge=0, caseTrouverNoir=0;
		
		
		for(Case c : cases) {
			
			/*
			 * REVELE LA CASE ET UPDATE LE NOMBRE MASTER
			 * 
			 */
			if(c.getMot().getLibelle()==libelle) {
				if(c.getCouleur().toString()==equipeJouant) {
					if(equipeJouant=="Bleu") {
						c.setImage(trouverBleu);
						c.setImageMaster(trouverBleu);
					}else {
						c.setImage(trouverRouge);
						c.setImageMaster(trouverRouge);
					}
					tour.setNombreMaster(nombreMaster--);
				}else if(c.getCouleur()==Couleur.noir) {
					c.setImage(trouverNoir);
					c.setImageMaster(trouverNoir);
					tour.setNombreMaster(0);
				}else if(c.getCouleur()==Couleur.blanc) {
					c.setImage(trouverBlanc);
					c.setImageMaster(trouverBlanc);
					tour.setNombreMaster(0);
				}else {
					if(equipeJouant=="Bleu") {
						c.setImage(trouverRouge);
						c.setImageMaster(trouverRouge);
					}else {
						c.setImage(trouverBleu);
						c.setImageMaster(trouverBleu);
					}
					tour.setNombreMaster(0);
				}

				c.setTrouver(true);
				daoCase.save(c);
			}
			
			/*
			 * VERIF DE FIN DE TOUR
			 * 
			 */
		
			
			if(tour.getNombreMaster()==0) {
				if(equipeJouant=="Bleu") {
					tour.setEquipe(daoEquipe.findFirstByNom("Rouge"));
				}else {
					tour.setEquipe(daoEquipe.findFirstByNom("Bleu"));
				}
			}
			
			if(c.isTrouver() && c.getCouleur()==Couleur.bleu) {
				caseTrouverBleu++;
			} else if(c.isTrouver() && c.getCouleur()==Couleur.rouge) {
				caseTrouverRouge++;
			} else if(c.isTrouver() && c.getCouleur()==Couleur.noir) {
				caseTrouverNoir++;
			}
		}
		
		tour.getPartie().getGrille().setCases(cases);
		
		/*
		 * VERIF FIN DE JEU
		 * 
		 */
		
		if(caseTrouverBleu==9) {
				tour.setEquipe(daoEquipe.findFirstByNom("Bleu"));
			//ECRAN BLEU A GAGNE
		} else if(caseTrouverRouge==8) {
				tour.setEquipe(daoEquipe.findFirstByNom("Rouge"));
			//ECRAN ROUGE A GAGNE
		} else if(caseTrouverNoir==1) {
			if(equipeJouant=="Bleu") {
				tour.setEquipe(daoEquipe.findFirstByNom("Rouge"));
				//ECRAN ROUGE A GAGNE
			}else {
				tour.setEquipe(daoEquipe.findFirstByNom("Bleu"));
				//ECRAN BLEU A GAGNE
			}
		}
		
		
		//Créer un nouveau tour
		tour.setId(0);
		daoTour.save(tour);
		session.setAttribute("tour_id", tour.getId());
		
		

		/*
		for(SseEmitter emitter : this.emitters) {
			try {
				emitter.send("refresh");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				emitter.completeWithError(e);
			}
		}
		*/
		return "redirect:/plateau";
	}
	
	/*
	@GetMapping("/sse")
	public SseEmitter testSSE() {
		SseEmitter emitter = new SseEmitter();
		
		emitter.onCompletion(() -> {
			synchronized (this.emitters) {
				this.emitters.remove(emitter);
			}
			
		});
		
		emitter.onTimeout(() -> {
			emitter.complete();
		});
		
		
		this.emitters.add(emitter);
		
		return emitter;
	}
	*/
}

