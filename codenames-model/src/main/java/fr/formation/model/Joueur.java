package fr.formation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class Joueur extends Utilisateur {
	
	@ManyToOne
	@JoinColumn(name = "equipe")
	private Equipe equipe;
	
	@Column (name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@ManyToOne
	@JoinColumn(name = "participation")
	private Participation saParticipation;
	
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	public Joueur(int id, String nom, String prenom, String username, String password) {
		super(id, nom, prenom, username, password);
	}
	
	public Joueur() {
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

}