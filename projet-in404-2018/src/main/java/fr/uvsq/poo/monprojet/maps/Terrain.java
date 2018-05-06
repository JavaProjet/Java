package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.personnage.Pnj;
import fr.uvsq.poo.monprojet.intro.Cinematic;
import fr.uvsq.poo.monprojet.objets.Argent;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.personnage.Marchand;
import fr.uvsq.poo.monprojet.personnage.Monstre;
import fr.uvsq.poo.monprojet.personnage.Personnage;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Terrain {
	public char[][] t;
	private int numero;
	private int largeur,hauteur;
	public ArrayList <Pnj> personnage;
	public ArrayList <Monstre> monstres;
	public ArrayList <Objet> objets;
	public Marchand vendeur;
	public Pj joueur;
	public Porte entree;
	public Porte sortie;
	private boolean sombre = true;
	public static final char MUR = '#';
	public static final char VIDE = ' ';
	public static final char SOL = '.';
	public static final char PORTE = 'P';
	
	
	public Terrain(int largeur, int hauteur, Pj joueur) {
		personnage = new ArrayList <Pnj> ();
		objets = new ArrayList <Objet> ();
		monstres = new ArrayList <Monstre> ();
		t = new char[largeur][hauteur];
		this.joueur = joueur;
		this.largeur = largeur;
		this.hauteur = hauteur;
		int i,j;
		for(i = 0; i < largeur; i++) {
			for(j = 0; j < hauteur; j++) {
				t[i][j] = SOL;
			}
		}
	}
	
	private static void clearScreen() {   //pour le terminal linux
	    System.out.print("\033[H\033[2J");  
	    System.out.flush();  
	}
	
	public String toString() {
		String info = new String();
		info = "PV : " + joueur.pointDeVie + "\t" + "Rubis : " + joueur.getMonnaie() + "\t" + "XP : " + joueur.experience + "\n\n";
		System.out.println("\n\n");
		Terrain.clearScreen();
		if(isSombre()) return this.toString2() + info;
		else return this.toString1() + info;
	}
	
	public String toString1() {
		String s = "";
		int i,j;
		for(j = hauteur - 1; j >= 0; j--) {
			for(i = 0; i < largeur; i++) {
				s += t[i][j];
				if(i < largeur - 1)s += " ";
			}
			s += "\n";
		}
		return s;
	}
	
	
	
	public String toString2() {
		int distance = 4;
		String s = "";
		int i,j,i2,j2;
		
		i = joueur.position.getX() - distance; i2 = joueur.position.getX() + distance + 1;
		j = joueur.position.getY() - distance; j2 = joueur.position.getY() + distance + 1;
		
		if(i < 0) i = 0; if(j < 0) j = 0;
		if(i2 > largeur) i2 = largeur; if(j2 > hauteur) j2 = hauteur;
		
		int a,b;
		for(b = hauteur - 1; b >= 0; b--) {
			for(a = 0; a < largeur; a++) {
				if(a >= i && a < i2 && b >= j && b < j2) s += t[a][b];
				else s += " ";
				s += " ";
			}
			s += "\n";
		}
		
		return s;
	}
	
	public int getLargeur() {
		return largeur;
	}
	
	public int getHauteur() {
		return hauteur;
	}
	

	public int getSurface() {
		int surface = 0,i,j;
		for(i = 0; i < largeur; i++) {
			for(j = 0; j < hauteur; j++) {
				if(t[i][j] == SOL) surface ++;
			}
		}
		return surface;
	}
	
	void respawnMonstre(int surface) {
		while(monstres.size() < surface / 100) {
			Monstre.spawn(this, getNumero() / 4);
		}
	}
	
	public boolean correctPosition(int x,int y) { //si la position x,y est dans le terrain
		
		if(y > -1 && y < hauteur) { // si la position x,y est incluse dans le terrain en Y
			if(x > -1 && x < largeur) { // idem en X
				return true;
			}
		}
		return false;
	}
	
	//variables utilisées pour la méthode play() et les autres methodes qu'elle appelle.
	private boolean changerTerrain; //si l'on va dans une porte, ce booleen s'en souvient pour changer de terrain
	//###//
	
	public int play() {
		if(getNumero() != 0)respawnMonstre(getSurface());
		joueur.setNumero(getNumero());
		System.out.println(this);
		changerTerrain = false;
		Scanner entree = new Scanner(System.in);
		String s = new String();
		while(s.equals("stop") == false && s.equals("save") == false && changerTerrain == false && joueur.pointDeVie.getNumerateur() != 0) {
			s = "";
			s += entree.nextLine();
			this.action(s,entree);
		}
		
		if(joueur.pointDeVie.getNumerateur() == 0) {
			t[joueur.position.getX()][joueur.position.getY()] = '~';
			System.out.println(this);
			Cinematic.gameOver();
			return 1;
		}
		else if(changerTerrain) {
			return this.playNew();
		}
		else if(s.equals("save")) {
			return 2;
		}
		else return 0;
	}
	
	private void action(String s, Scanner entree) {
		switch(s) {
			case "z"	: 	this.deplacement(joueur,true,'N'); tour();		
							System.out.print(this);
																			break;
			case "q" 	: 	this.deplacement(joueur,true,'O'); tour();
							System.out.print(this);
																			break;
			case "s"	: 	this.deplacement(joueur,true,'S'); tour();	
							System.out.print(this);
																			break;
			case "d" 	: 	this.deplacement(joueur,true,'E'); tour();	
							System.out.print(this);
																			break;
			case "a"	:	joueur.discuss(this);
																			break;
			case "help" : 	System.out.println("\"commande\".\"informations de la commande\"");
							System.out.println("(z,q,s,d).avancer respectivement en haut, à gauche, en bas et à droite");
							System.out.println("i.afficher/utiliser un objet de l'inventaire");
							System.out.println("r.ramasser un objet");
							System.out.println("save.sauvegarder la partie");
							System.out.println("j.jeter un objet devant vous");
							System.out.println("a.discuter avec un pnj ou un marchand");
							System.out.println("u.assigner un raccourci vers un Nième objet de l'inventaire, N -> votre choix   (1 au départ)");
							System.out.println("e.utiliser l'objet à l'emplacement N de votre inventaire");
							System.out.println("P : Porte\tN : Pnj\t&/@/G : Monstres\tM : Marchand\t"
											 + "*/!/D/0/y/-/T/6/% : objets à découvrir\n");
																			break;
			case "i" 	:	this.inventaire(entree,s); tour();				break;
			case "u" 	:	this.inventaire(entree,s);						break;
			case "e"	:	if(joueur.getRapidUse() < joueur.inventory.size() && joueur.inventory.isEmpty() == false)
								joueur.inventory.get(joueur.getRapidUse()).use(this);
							tour();
																			break;
			case "r" 	: 	this.ramasser();								break;
			case "j" 	: 	this.jeter(entree, s);								break;
			//case "-s"	:	setSombre(!isSombre()); System.out.print(this);	break;
			case "save" :	;break;
			case "hidden test" :	joueur.addMonnaie(9999); joueur.addXP(100000);
									System.out.print(this);
																			break;
			default 	: 	if(s.equals("stop") == false && s.length() != 42)
								System.out.println(this + "entrez help pour obtenir des informations et les commandes\n elle a la réponse à tout ;)");
																			break;	
		}
		
		if(s.length() == 42) {
			joueur.regenLife(joueur.pointDeVie.getDenominateur());
		}
	}
	
	private void deplacement(Personnage j, boolean accesPorte,char vision) {
		j.setVision(vision);
		if(correctPosition(j.devantLui.getX(), j.devantLui.getY())) {
			if(t[j.devantLui.getX()][j.devantLui.getY()] == SOL) {
				t[j.position.getX()][j.position.getY()] = SOL;
				j.avance();
			}
			else if(t[j.devantLui.getX()][j.devantLui.getY()] == PORTE && accesPorte) {
				changerTerrain = true;
				t[j.position.getX()][j.position.getY()] = SOL;
			}
		}
		t[entree.position.getX()][entree.position.getY()] = PORTE;
		t[sortie.position.getX()][sortie.position.getY()] = PORTE;
		if(changerTerrain == false) {
			t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
		}
		if(j.getClass() != Pj.class)t[j.position.getX()][j.position.getY()] = j.getRepresentation();
	}
	
	private void inventaire(Scanner entree, String s) {
		if(joueur.inventory.isEmpty()) {
			System.out.println(this + "inventaire vide");
		}
		else {
			System.out.println(this + "entrez le numéro de l'objet choisit, ou une commande du jeu pour continuer");
			joueur.afficherInventaire();
			int val;
			try {
				val = entree.nextInt(); val--;
				if(val > -1 && val < joueur.inventory.size()) {
					if(s.equals("i"))joueur.inventory.get(val).use(this);
					if(s.equals("u"))joueur.setRapidUse(val);
				}
				else System.out.println(this + "valeur incorrect");
				entree.nextLine();
			} 
			catch (InputMismatchException e) {}
		}
	}
	
	private void jeter(Scanner entree, String s) {
		if(joueur.inventory.isEmpty()) {
			System.out.println(this + "inventaire vide");
		}
		else if(t[joueur.devantLui.getX()][joueur.devantLui.getY()] != SOL) {
			System.out.println(this + "vous ne pouvez pas jeter d'objet devant vous");
		}
		else {
			System.out.println(this + "entrez le numéro de l'objet à jeter, ou une commande du jeu pour continuer");
			joueur.afficherInventaire();
			int val;
			try {
				val = entree.nextInt(); val--;
				if(val > -1 && val < joueur.inventory.size()) {
					joueur.inventory.get(val).position.setPosition(joueur.devantLui);
					t[joueur.devantLui.getX()][joueur.devantLui.getY()] = joueur.inventory.get(val).getRepresentation();
					objets.add(joueur.inventory.get(val));
					joueur.inventory.remove(val);
					System.out.print(this);
				}
				else System.out.println(this + "valeur incorrect");
				entree.nextLine();
			} 
			catch (InputMismatchException e) {}
		}
	}
	
	private boolean ramasser() {
		int i;
		for(i = 0; i < objets.size(); i++) {
			if(objets.get(i).position.equals(joueur.devantLui)) {
				joueur.inventory.add(objets.get(i));
				this.t[joueur.devantLui.getX()][joueur.devantLui.getY()] = Terrain.SOL;
				System.out.print(this);
				System.out.println("vous avez ramassé : " + objets.get(i).getNomObjet());
				if(objets.get(i).getClass() == Argent.class) this.joueur.inventory.get(this.joueur.inventory.size() - 1).use(this);
				objets.remove(i);
				return true;
			}
		}
		System.out.println("rien n'a pu être ramassé");
		return false;
	}
	
	private int playNew() {
		if(this.sortie.position.equals(joueur.devantLui)) {
			if (this.sortie.getAutorisation() == true) {
				joueur.initEntree(this.sortie.t);
				return this.sortie.t.play();
			}
			else {
				this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
				return this.play();
			}
		}
		else if(this.entree.position.equals(joueur.devantLui)) {
			if (this.entree.getAutorisation() == true) {
				joueur.initSortie(this.entree.t);
				return this.entree.t.play();
			}
			else {
				this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
				return this.play();
			}
		}
		else return -1;
	}
	
	public void tour() {
		int i;
		if(this.getNumero() != 0) {
			for(i = 0; i < personnage.size(); i++) {
				char vision = Personnage.probaDeplacement(10);
				if(vision == 'N' || vision == 'S' || vision == 'E' || vision == 'O')
					this.deplacement(personnage.get(i),false,vision);
			}
		}
		for(i = 0; i < monstres.size(); i++) {
			char vision = monstres.get(i).radar();
			if(vision == 'N' || vision == 'S' || vision == 'E' || vision == 'O')
				this.deplacement(monstres.get(i),false,vision);
		}
	}

	public boolean isSombre() {
		return sombre;
	}

	public void setSombre(boolean sombre) {
		this.sombre = sombre;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
