package fr.formation.model;

import java.util.ArrayList;
import java.util.List;

import fr.formation.Application;

public class Tour {

//	public Case voteGlobal(List<Case> votes) {
//		Case maxCase = null;
//		int maxCount = 0;
//				
//		for (Case c : votes) {
//			maxCase = c;
//		}
//		return maxCase;
//	}
	

	public Case voteJoueur(Grille grille) {
		Case maCase = new Case();
		maCase = null;
		boolean saisieOK = false;

		while (!saisieOK) {
			System.out.println("Quel mot choisissez vous ?");
			String mot = Application.sc.nextLine();

			for (Case[] c : grille.getGrille()) {
				for (Case ca : c) {
					if (ca.getMot().getLibelle().equals(mot)) {
						maCase = ca;
						saisieOK = true;
					}
				}
			}
			if (!saisieOK) {
				System.out.println("Veuillez écrire un mot de la liste. Respectez la casse");
			}
		}
		return maCase;
	}
}
