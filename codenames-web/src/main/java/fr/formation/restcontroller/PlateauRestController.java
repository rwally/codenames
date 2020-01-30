package fr.formation.restcontroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import fr.formation.model.Joueur;
import fr.formation.model.Mot;
import fr.formation.model.Participation;
import fr.formation.model.Partie;
import fr.formation.model.Role;
import fr.formation.model.Tour;
import fr.formation.views.Views;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/plateau")
public class PlateauRestController {
	
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
	
	@GetMapping("/test")
	public Tour testPlateau(HttpSession session) {
		Partie partie = new Partie();
		
		Equipe equipe1= new Equipe();
		Equipe equipe2= new Equipe();
		
		
		equipe1.setNom("bleu");
		equipe2.setNom("rouge");
		
		
		Grille grille = new Grille();
		grille.setDifficulte(Difficulte.facile);//TEST
		partie.setGrille(grille);
		
		//Création des 25 mots
				List<Mot> mots = new ArrayList<Mot>();
				List<Integer> nombresRandom = new Random().ints(1, 699).distinct().limit(25).boxed()
							.collect(Collectors.toList());
				
				for(int n : nombresRandom) {
					mots.add(daoMot.findById(n).get());
				}
				
				//Création des cases 
				List<Case> cases = new ArrayList<Case>();
				for(int i = 0; i < 25; i++) {
					Case maCase = new Case();
					maCase.setMot(mots.get(i));
					
					if(i<9) {
						maCase.setCouleur(Couleur.bleu);
						maCase.setImageMaster("https://i.imgur.com/LDcUXHC.png");
					}else if(i<17) {
						maCase.setCouleur(Couleur.rouge);
						maCase.setImageMaster("https://i.imgur.com/SAcppjf.png");
					}else if(i<partie.getGrille().getDifficulte().getValeur()) {
						maCase.setCouleur(Couleur.blanc);
						maCase.setImageMaster("https://i.imgur.com/LRk4Jee.png");
					}else {
						maCase.setCouleur(Couleur.noir);
						maCase.setImageMaster("https://i.imgur.com/AfRrEMz.png");
					}			
					
					maCase.setGrille(grille);
//					daoCase.save(maCase);
					cases.add(maCase);
				}
				
				Collections.shuffle(cases);
				
				//Le plateau est crée
				grille.setCases(cases);
//				daoGrille.save(grille);
				partie.setGrille(grille);
				
				Participation participant1 = new Participation();		
				participant1.setId(789);
				participant1.setRole(Role.master);
				
				Tour tour = new Tour();
				tour.setPartie(partie);
				tour.setEquipe(equipe1);
				
			
				session.setAttribute("idParticipant", participant1.getId()); 
				session.setAttribute("idTour", daoTour.save(tour).getId()); 
				
				return tour;
	}
	
	//Affichage principale
	@GetMapping
	public Tour plateauJeu(Model model, HttpSession session) {
		Tour tour = daoTour.findById((Integer) session.getAttribute("idTour")).get();
		Participation participant = daoParticipation.findById((Integer) session.getAttribute("idParticipant")).get();
		model.addAttribute("participant", participant);
		model.addAttribute("cases", tour.getPartie().getGrille().getCases());
		return tour;
	}
	
	
	//Recupere le nombre de mots à deviner
	@PostMapping
	public void nombreADeviner(Model model, @RequestBody Tour tour, HttpSession session) {
		daoTour.save(tour);
		Participation participant = daoParticipation.findById((Integer) session.getAttribute("idParticipant")).get();
		model.addAttribute("participant", participant);
		model.addAttribute("cases", tour.getPartie().getGrille().getCases());
	}
	
	
	//Boucle de jeu, appelé après le clique sur une case
	@GetMapping("/{libelle}")
	public Tour cliqueImage(Model model, @PathVariable String libelle, HttpSession session) {
		
		Participation participant = daoParticipation.findById((Integer) session.getAttribute("idParticipant")).get();
		model.addAttribute("participant", participant);
		
		Tour tour = daoTour.findById((Integer) session.getAttribute("idTour")).get();
		model.addAttribute("cases", tour.getPartie().getGrille().getCases());
		
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
					if(equipeJouant=="bleu") {
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
					if(equipeJouant=="bleu") {
						c.setImage(trouverRouge);
						c.setImageMaster(trouverRouge);
					}else {
						c.setImage(trouverBleu);
						c.setImageMaster(trouverBleu);
					}
					tour.setNombreMaster(0);
				}

				c.setTrouver(true);
			}
			
			/*
			 * VERIF DE FIN DE TOUR
			 * 
			 */
			
			if(tour.getNombreMaster()==0) {
				if(equipeJouant=="bleu") {
					tour.setEquipe(daoEquipe.findByNom("rouge"));
				}else {
					tour.setEquipe(daoEquipe.findByNom("bleu"));
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
				tour.setEquipe(daoEquipe.findByNom("bleu"));
			//ECRAN BLEU A GAGNE
		} else if(caseTrouverRouge==8) {
				tour.setEquipe(daoEquipe.findByNom("rouge"));
			//ECRAN ROUGE A GAGNE
		} else if(caseTrouverNoir==1) {
			if(equipeJouant=="bleu") {
				tour.setEquipe(daoEquipe.findByNom("rouge"));
				//ECRAN ROUGE A GAGNE
			}else {
				tour.setEquipe(daoEquipe.findByNom("bleu"));
				//ECRAN BLEU A GAGNE
			}
		}
			
		
		daoTour.save(tour);

		
		for(SseEmitter emitter : this.emitters) {
			try {
				emitter.send("refresh");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				emitter.completeWithError(e);
			}
		}
		
		return tour;
	}
	
	
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
	

}
