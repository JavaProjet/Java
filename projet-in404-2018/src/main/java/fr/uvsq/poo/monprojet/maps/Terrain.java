package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.personnage.Pnj;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.personnage.Personnage;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Terrain {
	public char[][] t;
	private int largeur,hauteur;
	public ArrayList <Pnj> personnage;
	public ArrayList <Objet> objets;
	public Pj joueur;
	public Porte entree;
	public Porte sortie;
	private boolean sombre = false;
	public static final char MUR = '#';
	public static final char VIDE = ' ';
	public static final char SOL = '.';
	public static final char PORTE = 'P';
	
	
	public Terrain(int largeur, int hauteur, Pj joueur) {
		personnage = new ArrayList <Pnj> ();
		objets = new ArrayList <Objet> ();
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
		Terrain.clearScreen();
		if(sombre) return this.toString2();
		else return this.toString1();
	}
	
	public String toString1() {
		String s = "";
		int i,j;
		for(j = hauteur - 1; j >= 0; j--) {
			for(i = 0; i < largeur; i++) {
				s += t[i][j];
				s += " ";
			}
			s += "\n";
		}
		return s;
	}
	
	public String toString2() {
		int distance = 3;
		String s = "";
		int i,j,i2,j2;
		int k,l;
		
		i = joueur.position.getX() - distance; i2 = joueur.position.getX() + distance + 1;
		j = joueur.position.getY() - distance; j2 = joueur.position.getY() + distance + 1;
		
		if(i < 0) i = 0; if(j < 0) j = 0;
		if(i2 > largeur) i2 = largeur; if(j2 > hauteur) j2 = hauteur;
		
		
		for(l = j2 - 1; l >= j; l--) {
			for(k = i; k < i2; k++) {
				s += t[k][l];
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
	
	public boolean addRandomPnj() {
		Random r = new Random();
		int alea = r.nextInt(3) + 1;
		for(int i = 0; i < alea; i++) {
			Pnj.spawn(this);
		}
		return true;
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
	
	public void play() {
		System.out.println(this);
		changerTerrain = false;
		Scanner entree = new Scanner(System.in);
		String s = new String();
		while(s.equals("stop") == false && changerTerrain == false && joueur.pointDeVie.getNumerateur() != 0) {
			s = "";
			s += entree.nextLine();
			this.action(s,entree);
		}
		
		if(joueur.pointDeVie.getNumerateur() == 0) {
			System.out.println("Game Over");
			//System.exit(0);
		}
		else if(changerTerrain) {
			this.playNew();
		}
		entree.close();
	}
	
	private void action(String s, Scanner entree) {
		boolean vivant = true;
		switch(s) {
			case "z"	: 	tour(); this.deplacementHaut(joueur,true); 		break;
			case "q" 	: 	tour(); this.deplacementGauche(joueur,true); 	break;
			case "s"	: 	tour(); this.deplacementBas(joueur,true);		break;
			case "d" 	: 	tour(); this.deplacementDroite(joueur,true);	break;
			case "a"	:	for(int i = 0; i < personnage.size(); i++) {
								if(personnage.get(i).position.equals(joueur.devantLui)) {
									vivant = personnage.get(i).setDamage(1,this);
									System.out.println(this);
									if(vivant)System.out.println("le personnage possède " + personnage.get(i).pointDeVie + " PV");
									else System.out.println("le personnage est mort ");
								}
							}
																			break;
			case "help" : 	System.out.println("\"commande\".\"informations de la commande\"");
							System.out.println("(z,q,s,d).avancer respectivement en haut, à gauche, en bas et à droite");
							System.out.println("info.obtenir des informations sur votre personnage");
							System.out.println("i.utiliser un objet de l'inventaire");
							System.out.println("r.ramasser un objet");
							System.out.println("a.donner un coup de poing");
																			break;
			case "info" : 	System.out.println("points de vies : " + joueur.pointDeVie);
							System.out.println("position : " + (joueur.position.getX() + 1) + "," + (joueur.position.getY() + 1));
							System.out.println("vous regardez vers le " + joueur.getVision());
																			break;
			case "i" 	:	tour(); this.inventaire(entree);				break;
			case "r" 	: 	this.ramasser();								break;
			case "-d"	: 	joueur.setDamage(10); 							break;
			case "-s"	:	sombre = !sombre; System.out.print(this);		break;
			default 	: 	if(s.equals("stop") == false)
							System.out.println("> help pour obtenir la liste des commandes\n elle a la reponse à tout ;)");
																			break;	
		}
		
		if(s.length() == 42) {
			joueur.regenLife(joueur.pointDeVie.getDenominateur());
		}
	}
	
	private void deplacementGauche(Personnage j, boolean accesPorte) {
		j.setVision('O');
		this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
		if(this.correctPosition(j.devantLui.getX(), j.devantLui.getY())) {
			if(t[j.devantLui.getX()][j.devantLui.getY()] == SOL) {
				j.avance();
				this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
				this.t[j.position.getX()+1][j.position.getY()] = SOL;
				t[entree.position.getX()][entree.position.getY()] = PORTE;
				t[sortie.position.getX()][sortie.position.getY()] = PORTE;
			}
			else if(t[j.devantLui.getX()][j.devantLui.getY()] == PORTE && accesPorte) {
				changerTerrain = true;
				this.t[j.position.getX()][j.position.getY()] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementDroite(Personnage j, boolean accesPorte) {
		j.setVision('E');
		this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
		if(this.correctPosition(j.devantLui.getX(), j.devantLui.getY())) {
			if(t[j.devantLui.getX()][j.devantLui.getY()] == SOL) {
				j.avance();
				this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
				this.t[j.position.getX()-1][j.position.getY()] = SOL;
				t[entree.position.getX()][entree.position.getY()] = PORTE;
				t[sortie.position.getX()][sortie.position.getY()] = PORTE;
			}
			else if(t[j.devantLui.getX()][j.devantLui.getY()] == PORTE && accesPorte) {
				changerTerrain = true;
				this.t[j.position.getX()][j.position.getY()] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementBas(Personnage j, boolean accesPorte) {
		j.setVision('S');
		this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
		if(this.correctPosition(j.devantLui.getX(), j.devantLui.getY())) {
			if(t[j.devantLui.getX()][j.devantLui.getY()] == SOL) {
				j.avance();
				this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
				this.t[j.position.getX()][j.position.getY()+1] = SOL;
				t[entree.position.getX()][entree.position.getY()] = PORTE;
				t[sortie.position.getX()][sortie.position.getY()] = PORTE;
				
			}
			else if(t[j.devantLui.getX()][j.devantLui.getY()] == PORTE && accesPorte) {
				changerTerrain = true;
				this.t[j.position.getX()][j.position.getY()] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementHaut(Personnage j, boolean accesPorte) {
		j.setVision('N');
		this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
		if(this.correctPosition(j.devantLui.getX(), j.devantLui.getY())) {
			if(t[j.devantLui.getX()][j.devantLui.getY()] == SOL) {
				j.avance();
				this.t[j.position.getX()][j.position.getY()] = j.getRepresentation();
				this.t[j.position.getX()][j.position.getY()-1] = SOL;
				t[entree.position.getX()][entree.position.getY()] = PORTE;
				t[sortie.position.getX()][sortie.position.getY()] = PORTE;
				
			}
			else if(t[j.devantLui.getX()][j.devantLui.getY()] == PORTE && accesPorte) {
				changerTerrain = true;
				this.t[j.position.getX()][j.position.getY()] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void inventaire(Scanner entree) {
		String s = new String();
		if(joueur.inventory.isEmpty()) {
			System.out.println("inventaire vide");
		}
		else {
			boolean estEntier = true;
			joueur.afficherInventaire();
			System.out.println("quel objet utiliser ? (entrer und numéro pour utiliser, ou autre chose pour ne rien utiliser)");
			s = "";
			s += entree.nextLine();
			int val;
			for(val = 0; val < s.length(); val++) {
				if(s.charAt(val) < '0' || s.charAt(val) > '9') estEntier = false;
			}
			val = 0;
			if(estEntier) val = Integer.valueOf(s).intValue();
			if(val > 0 && val <= joueur.inventory.size()) {
				joueur.inventory.get(val - 1).use(this);
			}
		}
	}
	
	private boolean ramasser() {
		int i;
		for(i = 0; i < objets.size(); i++) {
			if(objets.get(i).position.equals(joueur.devantLui)) {
				joueur.inventory.add(objets.get(i));
				this.t[joueur.devantLui.getX()][joueur.devantLui.getY()] = Terrain.SOL;
				System.out.print(this);
				System.out.println("vous avez ramassé : " + objets.get(i).getNom());
				objets.remove(i);
				return true;
			}
		}
		System.out.println("rien n'a pu être ramassé");
		return false;
	}
	
	private void playNew() {
		if(this.entree.position.equals(joueur.devantLui)) {
			if (this.entree.autorisation == true) {
				joueur.initSortie(this.entree.t);
				this.entree.t.play();
			}
			else {
				this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
				this.play();
			}
		}
		else if(this.sortie.position.equals(joueur.devantLui)) {
			if (this.sortie.autorisation == true) {
				joueur.initEntree(this.sortie.t);
				this.sortie.t.play();
			}
			else {
				this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
				this.play();
			}
		}
	}
	
	public void tour() {
		int i;
		for(i = 0; i < personnage.size(); i++) {
			if(Pnj.probaDeplacement() == 'N') this.deplacementHaut(personnage.get(i),false);
			if(Pnj.probaDeplacement() == 'S') this.deplacementBas(personnage.get(i),false);
			if(Pnj.probaDeplacement() == 'E') this.deplacementGauche(personnage.get(i),false);
			if(Pnj.probaDeplacement() == 'O') this.deplacementDroite(personnage.get(i),false);
		}
	}
	
}
