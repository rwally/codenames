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
import javax.persistence.Table;

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
	
	@OneToMany(mappedBy = "saParticipation")
	private List<Joueur> joueurs;
	
	@ManyToOne
	@JoinColumn(name = "PARTIE_ID")
	private Partie maPartie;
	
	@OneToMany(mappedBy = "participation")
	private List<Chat> chats;
	
	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	public void setJoueurs(List<Joueur> joueurs) {
		this.joueurs = joueurs;
	}
	
	
	public Participation(Role role, List<Joueur> joueurs) {
		this.role = role;
		this.joueurs = joueurs;
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
	
}
