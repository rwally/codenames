package fr.formation.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import fr.formation.Views.Views;


@Entity
@Table (name = "chat")
public class Chat {
	
	@Id
	@Column(name ="CHAT_ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Common.class)
	private int id;
	
	@ManyToOne
	@JoinColumn(name ="CHAT_JOUEUR")
	private Participation joueur;
	
	@Column(name ="CHAT_DATE")
	@JsonView(Views.Chat.class)
	private Date date;
	
	@Column(name ="CHAT_MESSAGE")
	@JsonView(Views.Chat.class)
	private String message;

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@ManyToOne
	@JoinColumn(name = "CHAT_PARTICIPATION")
	private Participation participation;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Participation getJoueur() {
		return joueur;
	}

	public void setJoueur(Participation joueur) {
		this.joueur = joueur;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
