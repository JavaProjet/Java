package fr.uvsq.poo.monprojet.intro;

import java.util.Scanner;

public interface Cinematic {
	
	public static String changeChar(String s, int i, char newChar) {
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
	
	public static void clearScreen() {   //pour le terminal linux
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}
	
	public static void introduction() {
		@SuppressWarnings("resource")
		Scanner line = new Scanner(System.in);
		String s = "                                            \n"; //45
		int k = 40;
		clearScreen();
		System.out.println("Vous êtes à la recherche d'un tresor");
		sleep(1);
		System.out.println("\nun trésor d'une valeur inestimable, on raconte qu'il permet de se téléporter, mais...");
		sleep(2);
		System.out.println("\nMalheureusement, personne à ce jour ne l'a trouvé et on dit de cette objet que ce n'est qu'une légende et qu'il serait gardé par une tribut inconnu de tous");
		line.nextLine();
		clearScreen();
		sleep(1);
 		System.out.println("Mais un jour, vous tombez sur une grotte mysterieuse, son entrée avait une forme étrange.");
		sleep(3);
		System.out.println("\nalors vous vous decidez à l'explorer, et partez à l'aventure");
		sleep(3);
		System.out.println("\nc'est ainsi que vous entrez dans cette mystérieuse grotte...");
		line.nextLine();
		clearScreen();
		sleep(1);
		for(int i = 0; i < 10; i++) {
			s = changeChar(s,k--,'#'); s = changeChar(s,k+2,' ');
			System.out.println("#############################################\n"
							 + "#############################################\n"
							 + "#############################################\n"
							 + s
							 + "                     o                       \n"
							 + "                    ---                      \n"
							 + "                    / \\                      \n"
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
		System.out.println("Mais ce caillou heurte votre tête, et vous assomme...\n\n vous vous reveillez dans une salle.");
		line.nextLine();
	}
	
	public static void gameOver() {
		String s1 = " _____   ______   ____ ____   ______       _______              ______   _______   \n";
		String s2 = "|  ___| | [  ] | |  _ | _  | |  ____|     |  ___  | | |    | | |  ____| |  ___  |  \n";
		String s3 = "| |  _  |  __  | | | |_| | | | |__|       | |   | | \\ \\    / / | |__|   |  _____|  \n";
		String s4 = "| |_| | | |  | | | |     | | | |____      | |___| |  \\ \\__/ /  | |____  | |\\ \\   \n";
		String s5 = "|_____| |_|  |_| |_|     |_| |______|     |_______|   \\____/   |______| |_| \\_\\   \n";
		System.out.println( s1 + s2 + s3 + s4 + s5);
	}
	
}
