package fr.formation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

@Entity
@Table (name = "participation")
public class Participation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="PARTICIP_ID",nullable = false)
	private int id;

	@Column(name ="ROLE_JOUEUR",nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;
	
	
	@OneToOne
	@JoinColumn(name = "JOUEUR_ID")
	private Joueur joueur;
	
	@ManyToOne
	@JoinColumn(name = "PARTIE_ID")
	private Partie maPartie;
	
	@OneToMany(mappedBy = "participation")
	private List<Chat> chats;
	
	public Joueur getJoueurs() {
		return joueur;
	}

	public void setJoueurs(Joueur joueur) {
		this.joueur = joueur;
	}
	
	
	public Participation(Role role, Joueur joueur) {
		this.role = role;
		this.joueur = joueur;
	}
	
	
	public Participation(Role role) {
		this.role = role;
	}

	public Participation() {
		
	}
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public void setJoueur(Joueur joueur) {
		this.joueur = joueur;
	}

	public Partie getMaPartie() {
		return maPartie;
	}

	public void setMaPartie(Partie maPartie) {
		this.maPartie = maPartie;
	}

	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}
	
	
}
