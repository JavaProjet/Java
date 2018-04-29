
package fr.uvsq.poo.monprojet.personnage;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;

public interface Discussion {
	
	//il faut rajouter une disussion pour le vieux (je m'en occupe)
	// cree une fonction qui fera appel a plusieurs autres fonctions qui elles permettent de discuter pour
	//reduire la taille de la fonction
	// comme ca on aura differentes discussions et elles varieront selon le terrain le numero varie entre 0 et 21,
	//plus on va loin et plus les pnj donneront des indices sur la fin
	// pour la fin soit on fais une teleportation en x = 42 et y = 42 dans la derniere salle pour finir le jeu
	//et on met plein d'indice dans les discussions parce que 42 reponse a tout ;)
	//sinon on fais un boss de fin et une salle de fin de jeu,
	// t'en pense quoi ?
	
	public static void discussion(Terrain T) {
		if(T.numero == 0) {
			discussVieux(T);
		}
		else {
			for(int i = 0; i < T.personnage.size(); i++) {
				if(T.joueur.devantLui.equals(T.personnage.get(i).position)) {
					if(T.numero > 0 && T.numero <= 5) {// dans les 5 premieres salles
						discuss1(T);
					}
				}
			}
			if(T.joueur.devantLui.equals(T.vendeur.position)) {
				discussVendeur(T);
			}
		}
	}
	
	public static void discussVendeur(Terrain t) {
		System.out.println("je n'ai rien à vendre");
	}

	public static void discussVieux(Terrain t) {
		if(t.sortie.autorisation == false) {
			System.out.println("Laisse moi tranquille, j'ai pas envie de parler, prends plutôt la porte et fais gaffe, c'est dangereux.");
			t.sortie.autorisation = true;
		}
		else {
			System.out.println("je ne peut rien faire pour toi et tu ne peut rien faire non plus pour moi je suis aveugle,\n"
							 + " alors prends la porte, et aussi, si ce n'est pas déjà fais prends la pioche et l'épée");
		}
		
	}

	public static void discuss1(Terrain T) throws InputMismatchException{ //met les accents, ils s'affichent sur le terminal ;)
		@SuppressWarnings("resource")
		Scanner discute = new Scanner(System.in);
		Random r = new Random();
		int rand = -1; rand = r.nextInt(4);
		if(rand == 0){
			System.out.println("Bonjour aventurier !");
			System.out.println("1- Bonjour (Partir)");
			System.out.println("2- Bonjour, avez-vous entendu parler d'un mysterieux objet supposé être dans ces grottes ?");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("On raconte que les gens de l'ombre garderaient un mystérieux téléporteur, personnellement je n'ai jamais rien vu de tel");
			}
		}
		if(rand == 1){
			System.out.println("Comment vas-tu aventurier ?");
			System.out.println("1- Ignorer ce personnage");
			System.out.println("2- Je me méfie des creatures, elles sont nombreuses par ici..");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("Tu as raison, reste sur tes gardes. Certains peuvent même casser la roche pour t'attaquer.");
				System.out.println("Si tu as besoin de te soigner, tu pourras trouver des fioles médicinales par ici");
			}
		}
		if(rand == 2){
			System.out.println("Que fais - tu par ici?");
			System.out.println("1- Ignorer ce personnage");
			System.out.println("2- J'ai trouvé cette grotte par hasard, je suis à la recherche d'un tresor");
			System.out.println("3- Je me suis reveillé à quelques pas d'ici");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("Ah! Le téléporteur c'est ça ? Tu es tombé au bon endroit...");
				System.out.println("Malheureusement, je crois bien que ce soit qu'une légende");
			}
			if(reponse == 3){
				System.out.println("Tu as croisé des monstres?");
				System.out.println("1- Oui je me suis échappé");
				System.out.println("2- Non, il y a des monstres par ici ?");
				reponse = -1; reponse = discute.nextInt();
				if(reponse == 1) System.out.println("Fais attention, tu peux trouver de quoi te défendre par endroits.");
				if(reponse == 2) System.out.println("Oui ça grouille de monstres, tu peux trouver de quoi te défendre par endroits.");
			}
		}
		if(rand == 3){
			System.out.println("Qui êtes-vous?");
			System.out.println("1- Ignorer ce personnage");
			System.out.println("2- Je me suis aventuré dans cette grotte..");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("Que cherches - tu ?");
				System.out.println("1- Je cherche un trésor");
				System.out.println("2- Rien... Je rentre chez moi");
				reponse = -1; reponse = discute.nextInt();
				if(reponse == 1) System.out.println("Ah, certains croient toujours à cette vieille légende...");
			}
		}
	}
}
