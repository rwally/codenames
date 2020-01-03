package fr.formation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.formation.dao.IDAOCase;
import fr.formation.dao.IDAOPartie;
import fr.formation.dao.IDAOTour;
import fr.formation.model.Case;
import fr.formation.model.Couleur;
import fr.formation.model.Grille;
import fr.formation.model.Partie;
import fr.formation.model.Tour;

@Service
public class TourService {
	
	@Autowired
	Tour tour;
	
	@Autowired
	IDAOCase daoCase;
	
	@Autowired
	IDAOPartie daoPartie;
	
	@Autowired
	IDAOTour daoTour;
	
	/*
	 * 	Méthode pour savoir quelle est l'équipe qui joue (L'équipe rouge commence tout le temps et le NombreTours commence à 1
	 */
	public String equipeJoueuse() {
		tour.getNombreTours();
		if(tour.getNombreTours()%2==1) {
			return "rouge";
		} else if(tour.getNombreTours()%2==2) {
			return "bleu";
		}
		return null;
	}
	

	/*
	 * 	INITIALISATION DU TOUR
	 * 
	 */
	public String initTour(Partie partie) {
		
		//On assigne la partie au tour
		tour.setPartie(partie);
		
		
		//Le numero de ce tour est le numero du précédent tour +1
		if(partie.getTours().isEmpty()) {
			tour.setNombreTours(1);
		} else {
			tour.setNombreTours(partie
					.getTours()
					.get(partie.getTours().size()-1)
					.getNombreTours()+1);
		}
		
		
		//on rajoute le tour à la liste de tour de partie
		partie.getTours().add(tour);
		
		//on save la partie
		daoPartie.save(partie);
		
		//on save le tour
		daoTour.save(tour);
		
		//On renvoie l'equipe qui doit jouer le tour
		return equipeJoueuse();
	}
	
	
	/*
	 * METHODE DU MASTER QUI DONNE LE CODENAME ET LE NOMBRE DE MOTS A DEVINER
	 * 
	 */
	
	
	
	
	/*
	 * METHODE DU VOTE JOUEURS QUI RENVOIE LA CASE A CHECK
	 * 
	 */
	
	
	
	/*
	 * METHODE QUI PARCOURT LA GRILLE AVEC LA CASE A CHECK
	 * 
	 */
	
	public int parcoursGrille(Case caseACheck, int nombreDeMotsADeviner) {
			for(Case c : tour.getPartie().getGrille().getCases()) {
				//TANT QU'IL RESTE DES MOTS A DEVINER 
					if(c.getMot()==caseACheck.getMot()) {							
						c.setTrouver(true);
						daoCase.save(c);
							if(c.getCouleur().toString()==equipeJoueuse()) {
								nombreDeMotsADeviner=-1;
							} else if(c.getCouleur().toString()=="blanc") {
								nombreDeMotsADeviner=0;
								return nombreDeMotsADeviner;
							} else if(c.getCouleur().toString()=="noir") {
								nombreDeMotsADeviner=-1;
								return nombreDeMotsADeviner;
							} else {
								nombreDeMotsADeviner=0;
								return nombreDeMotsADeviner;
							}
						}
				}
			return nombreDeMotsADeviner;
	}
	
	
	/*
	 * METHODE QUI CHECK TOUTES LES CONDITIONS DE VICTOIRES
	 * 
	 */
	
	public boolean isWinner(String equipeJoueuse) {
		
		int motsTrouver=0;
		for(Case c : tour.getPartie().getGrille().getCases()) {
			if(c.isTrouver() && c.getCouleur().toString()==equipeJoueuse) {
				motsTrouver++;
			}  
		}
		
		if(motsTrouver==8) {
			return true;
		}
		
		return false;
	}
	
	//Pour la case noire
	public boolean isLooser(String equipeJoueuse) {
		
		for(Case c : tour.getPartie().getGrille().getCases()) {
			if(c.isTrouver() && c.getCouleur()==Couleur.noir) {
				return true;
			}  
		}
	
		return false;
	}
		
		
		

	
	
	

}
