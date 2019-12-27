package fr.formation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class Tour {
	
	List<Mot> CodeNames = new ArrayList<Mot>();
	Scanner sc = new Scanner(System.in);

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
			String mot = sc.nextLine();

			for (Case c : grille.getCases()) {
				
					if (c.getMot().getLibelle().equals(mot)) {
						maCase = c;
						saisieOK = true;
					}
				
			}
			if (!saisieOK) {
				System.out.println("Veuillez écrire un mot de la liste. Respectez la casse");
			}
		}
		return maCase;
	}
	
	
	
	
	
	public boolean CodeName (Joueur j) {
		String code = null;
		int nbMots = -1;
		
		boolean valid = false;
		
		Scanner sc = new Scanner(System.in);
		
		if(j.getRole() == Role.master) {
			
			
			 System.out.println("Entrez le nom de code :");
			 code = sc.nextLine();
			 System.out.println("Entrez un nombre de mots à deviner :");
			 nbMots = sc.nextInt();
			 
			 System.out.println("Choisissez les mots à faire deviner :");
			 
			 switch (nbMots) {
				
				case 0:
					
				case 1:
					System.out.println("Choisissez le mot à deviner dans la grille :");
					String codeName = sc.nextLine();
					
				case 2:
					System.out.println("Entrez les deux noms de code :");
					String codeName1 = sc.nextLine();
					String codeName2 = sc.nextLine();
					
					if(codeName1.equals(code)) {
						valid = true;
					}
				}
			 
			 
		}
		else {
			System.out.println(code + " en " + nbMots + " mots.");
			
			switch (nbMots) {
			
			case 0:
				
			case 1:
				System.out.println("Entrez le nom de code :");
				String codeName = sc.nextLine();
				
				if(codeName.equals(code)) {
					valid = true;
				}
				
			case 2:
				System.out.println("Entrez les deux noms de code :");
				String codeName1 = sc.nextLine();
				String codeName2 = sc.nextLine();
				
				if(codeName1.equals(code)) {
					valid = true;
				}
			}
		}
		return valid;
	}
}
