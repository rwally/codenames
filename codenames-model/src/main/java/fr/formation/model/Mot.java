package fr.formation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.Views.Views;

@Entity
@Table(name = "mot")
public class Mot {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "libelle", nullable = false)
	@JsonView(Views.Common.class)
	private String libelle;
	
	@Column(name = "used", nullable = false)
	private boolean used;
	
	@OneToOne(mappedBy = "mot")
	@JoinColumn(name = "[case]")
	private Case laCase;
	
	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public Case getLaCase() {
		return laCase;
	}

	public void setLaCase(Case laCase) {
		this.laCase = laCase;
	}

	public Mot(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}
	
	public Mot(String libelle) {
		this.libelle = libelle;
	}
	
	public Mot() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	
	
}
