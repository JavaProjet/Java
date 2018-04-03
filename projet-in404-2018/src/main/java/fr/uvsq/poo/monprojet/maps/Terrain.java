package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.personnage.Pnj;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Terrain {
	public char[][] t;
	private int largeur,hauteur;
	public ArrayList <Pnj> personnage;
	public ArrayList <Objet> objets;
	public Pj joueur;
	public Porte entree;
	public Porte sortie;
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
	
	public String toString() {
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
	private int x,y; //position en face du joueur (dépend de sa vision)
	private boolean changerTerrain; //si l'on va dans une porte, ce booleen s'en souvient pour changer de terrain
	//###//
	
	public void play() {
		System.out.println("\n\n");
		System.out.println(this);
		changerTerrain = false;
		x = -1; y = -1;
		Scanner entree = new Scanner(System.in);
		String s = "";
		while(s.equals("stop") == false && changerTerrain == false && joueur.pointDeVie.getNumerateur() != 0) {
			s = "";
			s = entree.nextLine();
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
		if(s.equals("z")) {
			this.deplacementHaut();
		}
		else if(s.equals("q")) {
			this.deplacementGauche();
		}
		else if(s.equals("s")) {
			this.deplacementBas();
		}
		else if(s.equals("d")) {
			this.deplacementDroite();
		}
		else if(s.equals("help")) {
			System.out.println("\"commande\".\"informations de la commande\"");
			System.out.println("(z,q,s,d).avancer respectivement en haut, à gauche, en bas et à droite");
			System.out.println("info.obtenir des informations sur votre personnage");
			System.out.println("i.utiliser un objet de l'inventaire");
			System.out.println("r.ramasser un objet");
		}
		else if(s.equals("info")) {
			System.out.println("points de vies : " + joueur.pointDeVie);
			System.out.println("position : " + (joueur.position.getX() + 1) + "," + (joueur.position.getY() + 1));
			System.out.println("vous regardez vers le " + joueur.getVision());
		}
		else if(s.equals("i")) {
			this.inventaire(entree);
		}
		else if(s.equals("r")) {
			this.ramasser();
		}
		else if(s.equals("damage")) {
			joueur.setDamage(10);
		}
		else if(s.length() == 42) {
			joueur.regenLife(joueur.pointDeVie.getDenominateur());
		}
		else {
			if(s.equals("stop") == false)
				System.out.println("> help pour obtenir la liste des commandes\n elle a la reponse à tout ;)");
		}
	}
	
	private void deplacementGauche() {
		x = joueur.position.getX() - 1;
		y = joueur.position.getY();
		joueur.setVision('O');
		if(this.correctPosition(x, y)) {
			if(t[x][y] == SOL) {
				joueur.avance();
				this.t[x][y] = joueur.getRepresentation();
				this.t[x+1][y] = SOL;
				t[this.entree.p.getX()][this.entree.p.getY()] = PORTE;
				t[sortie.p.getX()][sortie.p.getY()] = PORTE;
				x = joueur.position.getX() - 1;
				y = joueur.position.getY();
			}
			else if(t[x][y] == PORTE) {
				changerTerrain = true;
				this.t[x+1][y] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementDroite() {
		x = joueur.position.getX() + 1;
		y = joueur.position.getY();
		joueur.setVision('E');
		if(this.correctPosition(x, y)) {
			if(t[x][y] == SOL) {
				joueur.avance();
				this.t[x][y] = joueur.getRepresentation();
				this.t[x-1][y] = SOL;
				t[this.entree.p.getX()][this.entree.p.getY()] = PORTE;
				t[sortie.p.getX()][sortie.p.getY()] = PORTE;
				x = joueur.position.getX() + 1;
				y = joueur.position.getY();
			}
			else if(t[x][y] == PORTE) {
				changerTerrain = true;
				this.t[x-1][y] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementBas() {
		x = joueur.position.getX();
		y = joueur.position.getY() -1;
		joueur.setVision('S');
		if(this.correctPosition(x, y)) {
			if(t[x][y] == SOL) {
				joueur.avance();
				this.t[x][y] = joueur.getRepresentation();
				this.t[x][y+1] = SOL;
				t[this.entree.p.getX()][this.entree.p.getY()] = PORTE;
				t[sortie.p.getX()][sortie.p.getY()] = PORTE;
				x = joueur.position.getX();
				y = joueur.position.getY() -1;
			}
			else if(t[x][y] == PORTE) {
				changerTerrain = true;
				this.t[x][y+1] = SOL;
			}
		}
		System.out.print(this);
	}
	
	private void deplacementHaut() {
		x = joueur.position.getX();
		y = joueur.position.getY() + 1;
		joueur.setVision('N');
		if(this.correctPosition(x, y)) {
			if(t[x][y] == SOL) {
				joueur.avance();
				this.t[x][y] = joueur.getRepresentation();
				this.t[x][y-1] = SOL;
				t[this.entree.p.getX()][this.entree.p.getY()] = PORTE;
				t[sortie.p.getX()][sortie.p.getY()] = PORTE;
				x = joueur.position.getX();
				y = joueur.position.getY() + 1;
			}
			else if(t[x][y] == PORTE) {
				changerTerrain = true;
				this.t[x][y-1] = SOL;
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
			s = entree.nextLine();
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
			if(objets.get(i).position.getX() == x && objets.get(i).position.getY() == y) {
				joueur.inventory.add(objets.get(i));
				this.t[x][y] = Terrain.SOL;
				System.out.print(this);
				System.out.println("vous avez ramassé : " + objets.get(i).getNom());
				objets.remove(i);
				System.out.println(objets.size());
				return true;
			}
		}
		System.out.println("rien n'a pu être ramassé");
		return false;
	}
	
	private void playNew() {
		if(this.entree.p.getX() == x && this.entree.p.getY() == y) {
			if (this.entree.autorisation == true) {
				joueur.initSortie(this.entree.t);
				this.entree.t.play();
			}
			else {
				this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
				this.play();
			}
		}
		else if(this.sortie.p.getX() == x && this.sortie.p.getY() == y) {
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
}
