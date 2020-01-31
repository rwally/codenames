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
		Partie partie = daoPartie.findById((Integer) session.getAttribute("partie_id")).get();
		int dernierElement = partie.getTours().size() - 1;
		Tour tour = partie.getTours().get(dernierElement);
		
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
	
		String equipeJouant = tour.getEquipe().getNom().toLowerCase();
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
		
		
		for(int i=0;i<cases.size();i++) {
			
			/*
			 * REVELE LA CASE ET UPDATE LE NOMBRE MASTER
			 * 
			 */
			
			
			if(cases.get(i).getMot().getLibelle().equals(libelle)) {
				if(cases.get(i).getCouleur().toString().equals(equipeJouant)) {
					if(equipeJouant.equals("bleu")) {
						cases.get(i).setImage(trouverBleu);
						cases.get(i).setImageMaster(trouverBleu);
					}else {
						cases.get(i).setImage(trouverRouge);
						cases.get(i).setImageMaster(trouverRouge);
					}
					tour.setNombreMaster(--nombreMaster);
				}else if(cases.get(i).getCouleur()==Couleur.noir) {
					cases.get(i).setImage(trouverNoir);
					cases.get(i).setImageMaster(trouverNoir);
					tour.setNombreMaster(0);
				}else if(cases.get(i).getCouleur()==Couleur.blanc) {
					cases.get(i).setImage(trouverBlanc);
					cases.get(i).setImageMaster(trouverBlanc);
					tour.setNombreMaster(0);
				}else {
					if(equipeJouant.equals("bleu")) {
						cases.get(i).setImage(trouverRouge);
						cases.get(i).setImageMaster(trouverRouge);
					}else {
						cases.get(i).setImage(trouverBleu);
						cases.get(i).setImageMaster(trouverBleu);
					}
					tour.setNombreMaster(0);
				}

				cases.get(i).setTrouver(true);
				daoCase.save(cases.get(i));
			}
	
			if(cases.get(i).isTrouver() && cases.get(i).getCouleur()==Couleur.bleu) {
				caseTrouverBleu++;
			} else if(cases.get(i).isTrouver() && cases.get(i).getCouleur()==Couleur.rouge) {
				caseTrouverRouge++;
			} else if(cases.get(i).isTrouver() && cases.get(i).getCouleur()==Couleur.noir) {
				caseTrouverNoir++;
			}
		}
		
		tour.getPartie().getGrille().setCases(cases);
		daoTour.save(tour);
		
		/*
		 * VERIF DE FIN DE TOUR
		 * 
		 */
	
		
		if(tour.getNombreMaster()==0) {
			if(equipeJouant.equals("bleu")) {
					Tour nouveauTour = new Tour();
					nouveauTour.setPartie(tour.getPartie());
					nouveauTour.setEquipe((daoEquipe.findFirstByNom("Rouge")));
					nouveauTour.setNombreMaster(99);
					daoTour.save(nouveauTour);
					session.setAttribute("tour_id", nouveauTour.getId());
			}else {
				Tour nouveauTour = new Tour();
				nouveauTour.setPartie(tour.getPartie());
				nouveauTour.setEquipe((daoEquipe.findFirstByNom("Bleu")));
				nouveauTour.setNombreMaster(99);
				daoTour.save(nouveauTour);
				session.setAttribute("tour_id", nouveauTour.getId());
			}
		}
		
		
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
		
		
		for(SseEmitter emitter : this.emitters) {
			try {
				emitter.send("refresh");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				emitter.completeWithError(e);
			}
		}
	

		return "redirect:/plateau";
	}
	
	
	@GetMapping("/{libelle}/sse")
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
}

