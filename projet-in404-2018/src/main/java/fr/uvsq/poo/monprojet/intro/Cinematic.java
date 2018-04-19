package fr.uvsq.poo.monprojet.intro;

public class Cinematic {
	private static String changeChar(String s, int i, char newChar) {
		String chaine = "";
		String chaine2 = "";
		int a;
		if(i < s.length()) {
			for(a = 0; a < i; a++)
				chaine += s.charAt(a);
			a++;
			for(; a < s.length(); a++)
				chaine2 += s.charAt(a);
		}
		return chaine + newChar + chaine2;
	}
	
	public static void sleep(double secondes) {
		try {
			Thread.sleep((long) (secondes * 1000.0));
		} catch (InterruptedException e) {
			System.out.println("Une erreur est survenue lors de la création d'un thread, fermeture du programme");
			System.exit(2);
		}
	}
	
	private static void clearScreen() {   //pour le terminal linux
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}
	
	public static void introduction() {
		String s = "                                            \n"; //45
		int k = 40;
		clearScreen();
		sleep(2);
		System.out.println("Vous etes à la recherche d'un tresor");
		sleep(3);
		System.out.println("\nun tresor d'une valeur inestimable, on raconte qu'il permet de se teleporter, mais...");
		sleep(3);
		System.out.println("\nMalheureusement, personne à ce jour ne l'a trouve et on dit de cette objet que ce n'est qu'une legende et qu'il serait garde par une tribut qui vit dans l'ombre");
		sleep(3);
		clearScreen();
		sleep(2);
		System.out.println("Mais un jour, vous tombez sur une grotte mysterieuse, son entree avait une forme etrange.");
		sleep(3);
		System.out.println("\nalors vous vous decidez à l'explorer, et partez à l'aventure");
		sleep(3);
		System.out.println("\nc'est ainsi que vous entrez dans cette mysterieuse grotte...");
		sleep(3);
		clearScreen();
		sleep(2);
		for(int i = 0; i < 10; i++) {
			s = changeChar(s,k--,'#'); s = changeChar(s,k+2,' ');
			System.out.println("#############################################\n"
							 + "#############################################\n"
							 + "#############################################\n"
							 + s
							 + "                     o                       \n"
							 + "                    ---                      \n"
							 + "                    / \\                     \n"
							 + "#############################################\n"
							 + "#############################################\n");
			sleep(0.5);
			clearScreen();
			s = changeChar(s,k--,'#'); s = changeChar(s,k+2,' ');
			System.out.println("#############################################\n"
							 + "#############################################\n"
							 + "#############################################\n"
							 + s
							 + "                     o                       \n"
							 + "                     -                       \n"
							 + "                     |                       \n"
							 + "#############################################\n"
							 + "#############################################\n");
			sleep(0.5);
			clearScreen();
		}
		sleep(1);
		clearScreen();
		sleep(2);	
		System.out.println("Mais ce caillou heurte votre tête vous assommant...\n\n vous vous reveillez dans une salle.");
		sleep(4);
	}
	
}
