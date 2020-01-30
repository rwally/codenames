package fr.formation.model;

import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.views.Views;

@Entity
@Table(name = "partie")
public class Partie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "grille")
	@JsonView(Views.Partie.class)
	private Grille grille;

	@OneToMany(mappedBy = "maPartie")
	private List<Participation> joueurs;
	
	@OneToMany(mappedBy ="partie")
	private List<Tour> tours;

	public Partie(Grille grille, List<Participation> joueurs) {
		this.grille = grille;
		this.joueurs = joueurs;
	}

	public Partie() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Grille getGrille() {
		return grille;
	}

	public void setGrille(Grille grille) {
		this.grille = grille;
	}

	public List<Participation> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Participation> joueurs) {
		this.joueurs = joueurs;
	}
	
	
	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}

	public void setMaster(Equipe equipe) {
		for (Joueur j : equipe.getJoueurs()) {
			j.getSaParticipation().setRole(Role.agent);
		}
		
		Joueur j = getRandomElement(equipe.getJoueurs());
		j.getSaParticipation().setRole(Role.master);
	}

	public Joueur getRandomElement(List<Joueur> list) {
		
		Random randomGenerator = new Random();
		int randomIndex = randomGenerator.nextInt(list.size());
		return list.get(randomIndex);
	}

	public void AfficheJoueurs(List<Participation> participations) {

		for (Participation p : participations) {
			System.out.println(p.getJoueurs().getUsername() + " " + p.getJoueurs().getEquipe().getNom() + " "
					+ p.getRole());
		}
	}
}
