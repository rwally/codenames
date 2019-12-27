package fr.formation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "grille")
public class Grille {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "difficulte")
	@Enumerated(EnumType.STRING)
	private Difficulte difficulte;
	
	@OneToMany(mappedBy = "grille")
	private List<Case> cases;
	
	
	
	public Grille(Difficulte difficulte, List<Case> cases) {
		this.difficulte = difficulte;
		this.cases = cases;
	}



	public Grille() {
		
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Difficulte getDifficulte() {
		return difficulte;
	}



	public void setDifficulte(Difficulte difficulte) {
		this.difficulte = difficulte;
	}






	public List<Case> getCases() {
		return cases;
	}



	public void setCases(List<Case> cases) {
		this.cases = cases;
	}


	

}
