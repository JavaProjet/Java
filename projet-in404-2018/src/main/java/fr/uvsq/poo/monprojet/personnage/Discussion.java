package fr.uvsq.poo.monprojet.personnage;

import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;

public interface Discussion {
	
	//il faut rajouter une disussion pour le vieux
	// cree une fonction qui fera appel a plusieurs autres fonctions qui elles permettent de discuter pour
	//reduire la taille de la fonction, et donc pour le vieux tu fais appel à sa discussion si (le terrain) T.numero == 0
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
		else if(T.numero > 0 && T.numero <= 5) {// dans les 5 premieres salles
			discuss1(T);
		}
	}
	
	public static void discussVieux(Terrain t) {
		//on va faire une intro avec un dialogue etc.. et apres (le but de cette discussion)
		//faut que le vieux dise au joueur de partir ça sert a rien de rester ici le vieux n'as plus rien a lui dise etc...
		//car si il reste la on joue pas 
	}

	public static void discuss1(Terrain T){
		@SuppressWarnings("resource")
		Scanner discute = new Scanner(System.in);
		int x = T.joueur.position.getX();
		int y = T.joueur.position.getY();
		int test = 0;
		for(int j = 0; j < T.personnage.size(); j++){
			int px = T.personnage.get(j).position.getX();
			int py = T.personnage.get(j).position.getY();
			if(py == y+1 || py == y-1 || px == x+1 || px == x-1){
				Random r = new Random();
				int rand = r.nextInt(4);
				if(rand == 0){
					System.out.println("Bonjour aventurier !");
					System.out.println("1- Bonjour (Partir)");
					System.out.println("2- Bonjour, avez-vous entendu parler d'un mysterieux objet supposé être dans ces grottes?");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("On raconte que les gens de l'ombre garderaient un mysterieux teleporteur, personnellement je n'ai jamais rien vu de tel");
					}
				}
				if(rand == 1){
					System.out.println("Comment vas-tu aventurier?");
					System.out.println("1- Ignorer ce personnage");
					System.out.println("2- Je me mefie des creatures, elles sont nombreuses par ici..");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("Tu as raison, reste sur tes gardes. Certains peuvent même casser la roche pour t'attaquer.");
						System.out.println("Si tu as besoin de te soigner, tu pourras trouver des fioles medicinales par ici");
					}
				}
				if(rand == 2){
					System.out.println("Que fais - tu par ici?");
					System.out.println("1- Ignorer ce personnage");
					System.out.println("2- J'ai trouve cette grotte par hasard, je suis à la recherche d'un tresor");
					System.out.println("3- Je me suis reveille a quelques pas d'ici");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("Ah! Le teleporteur c'est ça? Tu es tombé au bon endroit...");
						System.out.println("Malheureusement, je crois bien que ce soit qu'une legende");
					}
					if(reponse == 3){
						System.out.println("Tu as croise des monstres?");
						System.out.println("1- Oui je me suis echappe");
						System.out.println("2- Non, il y a des monstres par ici?");
						reponse = discute.nextInt();
						if(reponse == 1) System.out.println("Fais attention, tu peux trouver de quoi te defendre par endroits.");
						if(reponse == 2) System.out.println("Oui ça grouille de monstres, tu peux trouver de quoi te defendre par endroits.");
					}
				}
				if(rand == 3){
					System.out.println("Qui etes-vous?");
					System.out.println("1- Ignorer ce personnage");
					System.out.println("2- Je me suis aventure dans cette grotte..");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("Que cherches -tu?");
						System.out.println("1- Je cherche un tresor");
						System.out.println("2- Rien... Je rentre chez moi");
						reponse = discute.nextInt();
						if(reponse == 1) System.out.println("Ah, certains croient toujours a cette vieille legende...");
					}
				}
				test = 1;
			}
		}
		if(test == 0) System.out.println("Vous ne pouvez discuter qu'a cote d'un personnage !");
	}
}
