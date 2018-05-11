
package fr.uvsq.poo.monprojet.personnage;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;

public interface Discussion {
	
	public static void discussion(Terrain T) {
		if(T.getNumero() == 0) {
			if(T.joueur.devantLui.equals(T.personnage.get(0).position))
				discussVieux(T);
			else {
				int alea = 0;
				Random r = new Random(); 
				if(T.sortie.getAutorisation() == false)alea = r.nextInt(3);
				if      (alea == 0)System.out.println(T + "*bruit de quelqu'un qui tousse*");
				else if (alea == 1) {
					if(T.sortie.getAutorisation() == false)
						System.out.println(T + "Voix d'un vieux : il y a quelqu'un ?");
				}
				else {
					if(T.sortie.getAutorisation() == false)
						System.out.println(T + "Voix d'un vieux : hé oh ! Viens me viens me voir avant de prendre la porte");
				}
			}
		}
		else {
			for(int i = 0; i < T.personnage.size(); i++) {
				if(T.joueur.devantLui.equals(T.personnage.get(i).position)) {
					if(T.getNumero() > 0 && T.getNumero() <= 5) {// dans les 5 premieres salles
						discuss1(T);
					}
					else if(T.getNumero() > 5 && T.getNumero() <= 10) {// dans les 5 premieres salles
						discuss2(T);
					}
					else {//tamporaire
						System.out.println(T + "pas de discussion dans cette salle");
					}
				}
			}
			try {
				if(T.joueur.devantLui.equals(T.vendeur.position)) {
					discussVendeur(T);
				}
			} catch (java.lang.NullPointerException e) {}
		}
	}
	
	public static void discussVendeur(Terrain t) {
		System.out.println(t + t.vendeur.affiche_vente());
		t.vendeur.transaction(t);
	}

	public static void discussVieux(Terrain t) {
		if(t.sortie.getAutorisation() == false) {
			System.out.println("Que fais - tu ici ? Tu dois quitter cet endroit en prenant la porte mais attention, c'est dangereux !\n"
							 + "Ah ! Et si ce n'est pas déjà fais prends la pioche et l'épée.\n"
							 + "Il me reste quelques rubis je te les offre, tu peut aussi en trouver dans les murs\n Vous avez reçu 3 rubis");
			t.joueur.addMonnaie(3);
			t.sortie.setAutorisation(true); //la porte est désormais accessible
		}
		else {
			System.out.println("je ne peut rien faire pour toi et tu ne peut rien faire pour moi non plus je suis aveugle,\n"
							 + " alors prends donc cette porte et ne reste pas ici.");
		}
		
	}

	public static void discuss1(Terrain T) throws InputMismatchException{ //salles jusqu'à 5
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
	public static void discuss2(Terrain T) throws InputMismatchException { //salles 5à10
		@SuppressWarnings("resource")
		Scanner discute = new Scanner(System.in);
		Random r = new Random();
		int rand = -1; rand = r.nextInt(3);
		if(rand == 0){
			System.out.println("Bonjour aventurier ! Comment es-tu arrivé jusqu'ici ?");
			System.out.println("1- (Partir)");
			System.out.println("2- J'ai traversé de nombreuses salles et on m'a poursuivi, je suis arrivé ici par hasard..");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("La route est difficile tu sais, et il te reste à découvrir beaucoup de choses.");
			}
		}
		if(rand == 1){
			System.out.println("Hé! aventurier ! Que fais-tu par ici ?");
			System.out.println("1- (Partir)");
			System.out.println("2- Je suis à la recherche du téléporteur");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("Ah ! Puisque tu es arrivé jusque là je peux t'en dire plus..");
				System.out.println(".. les gens qui se trouvent à l'entrée de la grotte ne veulent jamais rien dire là dessus..");
				System.out.println(".. en même temps ils ne savent rien non plus !");
				System.out.println("Le téléporteur existe bien mais il est protégé par des gardiens assez... redoutables ?");
			}
		}
		if(rand == 2){
			System.out.println("Tu cherches le téléporteur ?");
			System.out.println("1- Ignorer ce personnage");
			System.out.println("2- Non, qu'est ce que c'est?");
			System.out.println("3- Oui! vous savez quelque chose?");
			int reponse = -1; reponse = discute.nextInt();
			if(reponse == 2){
				System.out.println("Ah! Le téléporteur te permet de te déplacer dans la grotte comme tu le souhaite..");
				System.out.println("Il a des pouvoirs assez fantastiques !");
			}
			if(reponse == 3){
				System.out.println("Le téléporteur existe bien mais il est protégé par des gardiens assez redoutables");
				System.out.println("Tu le trouvera sûrement à quelques pas d'ici");
			}
		}
	}
	
	public static void discuss3(Terrain T) { //Salles 10à15
		@SuppressWarnings("resource")
		Scanner discute = new Scanner(System.in);
		Random r = new Random();
		int rand = -1; rand = r.nextInt(4);
		if(rand == 0){
			System.out.println("Rares sont les aventuriers arriv고jusqu'ici.");
		}
		if(rand == 1) {
			System.out.println("La route pour percer le secret de cette grotte est encore longue.");
		}
		if(rand == 2) {
			System.out.println("Bonjour !");
		}
		if(rand == 3) {
			System.out.println("Bonjour aventurier ! As-tu trouv顬e tꭩporteur?");
			System.out.println("1- Oui !");
			System.out.println("2- Non, je suis ࡳa recherche..");
			int reponse = discute.nextInt();
			if(reponse == 1) {
				System.out.println("Cet outil est formidable");
			}
			else if(reponse == 2) {
				System.out.println("On dit qu'il faut avoir le tꭩporteur pour sortir de cette grotte... ");
			}
		}
		if(rand == 4) {
			System.out.println("Je pense qu'il y a peu de chances que tu trouves le tꭩporteur apr鳠cette salle"
					+ "mais ce n'est que mon avis.");
		}
		
	}
	
	public static void discuss4(Terrain T) { //Salles 15à20
		Random r = new Random();
		int rand = -1; rand = r.nextInt(3);
		if(rand == 0){
			System.out.println("Bien joué! Tu es presque arrivé à ton but");
		}
		if(rand == 1) {
			System.out.println("Tiens ! ça fait un moment que je n'ai pas vu autre chose que des monstres par ici!");
		}
		if(rand == 2) {
			System.out.println("La fin de la grotte n'est plus très loin d'ici.");
		}
	}
	
}
