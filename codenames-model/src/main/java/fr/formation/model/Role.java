package fr.formation.model;

import java.util.List;
import java.util.Random;

public enum Role {
	master, agent;
	
private Random randomGenerator;
	
	public void setRoleJoueur(Joueur j) {
		
		if (j.getEquipe().equals("EquipeRouge") ) {
			if(Equipe.getEquipeRouge().contains(j.getRole() == master)) {
				j.setRole(agent);
			}
		}
		
		if (j.getEquipe().equals("EquipeBleue") ) {
			if(Equipe.getEquipeBleue().contains(j.getRole() == master)) {
				j.setRole(agent);
			}
		}
			
	}
	
	public void setMaster() {
		for( Joueur j : Equipe.getEquipeRouge()) {
			
			getRandomElement(Equipe.getEquipeRouge());
			j.setRole(master);
		}
		
		for( Joueur j : Equipe.getEquipeBleue()) {
			getRandomElement(Equipe.getEquipeBleue());
			j.setRole(master);	
		}
	}
	
	
	public Joueur getRandomElement(List<Joueur> list) 
    { 
        int randomIndex = randomGenerator.nextInt(list.size());
        return list.get(randomIndex);
    } 
}
