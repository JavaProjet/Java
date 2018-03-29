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
		t[entree.p.getX()][entree.p.getY()] = PORTE;
		t[sortie.p.getX()][sortie.p.getY()] = PORTE;
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
		int alea = r.nextInt(10) + 2;
		for(int i = 0; i < alea; i++) {
			personnage.add(Pnj.spawn(this));
		}
		return true;
	}
	
	public boolean correctPosition(int x,int y) { //si la position x,y est dans le terrain
		
		if(y > -1 && y < hauteur) { // si la position x,y est incluse dans le terrain en Y
			if(x > -1 && x < largeur) { // idem en X
				if(t[x][y] == SOL) 	return true;
			}
		}
		return false;
	}
	
	public void play() {
		System.out.println(this);
		Scanner entree = new Scanner(System.in);
		String s = "";
		while(s.equals("stop") == false) {
			s = "";
			s = entree.nextLine();
			if(s.equals("z")) {
				joueur.setVision('N');
				if(this.correctPosition(joueur.position.getX(), joueur.position.getY() + 1)) {
					if(t[joueur.position.getX()][joueur.position.getY() + 1] == SOL) {
						joueur.avance(); //on monte, la case du dessous redevient sol
						this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
						this.t[joueur.position.getX()][joueur.position.getY() - 1] = SOL;
					}
				}
				System.out.print(this);
			}
			
			else if(s.equals("q")) {
				joueur.setVision('O');
				if(this.correctPosition(joueur.position.getX() - 1, joueur.position.getY())) {
					if(t[joueur.position.getX() - 1][joueur.position.getY()] == SOL) {
						joueur.avance(); //on monte, la case du dessous redevient sol
						this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
						this.t[joueur.position.getX() + 1][joueur.position.getY()] = SOL;
					}
				}
				System.out.print(this);
			}
			
			else if(s.equals("s")) {
				joueur.setVision('S');
				if(this.correctPosition(joueur.position.getX(), joueur.position.getY() -1)) {
					if(t[joueur.position.getX()][joueur.position.getY() - 1] == SOL) {
						joueur.avance(); //on monte, la case du dessous redevient sol
						this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
						this.t[joueur.position.getX()][joueur.position.getY() + 1] = SOL;
					}
				}
				System.out.print(this);
			}
			
			else if(s.equals("d")) {
				joueur.setVision('E');
				if(this.correctPosition(joueur.position.getX() + 1, joueur.position.getY())) {
					if(t[joueur.position.getX() + 1][joueur.position.getY()] == SOL) {
						joueur.avance(); //on monte, la case du dessous redevient sol
						this.t[joueur.position.getX()][joueur.position.getY()] = joueur.getRepresentation();
						this.t[joueur.position.getX() - 1][joueur.position.getY()] = SOL;
					}
				}
				System.out.print(this);
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
			}
			
			else if(s.equals("i")) {
				if(joueur.inventory.isEmpty()) {
					System.out.println("inventaire vide");
				}
				else {
					for(int i = 0; i < joueur.inventory.size(); i++) {
						System.out.println(i + "." + joueur.inventory.get(i).getNom());
					}
					System.out.println("quel objet utiliser ?");
					s = "";
					s = entree.nextLine();
					if(s.charAt(0) >= '0' && s.charAt(0) <= '9' && s.charAt(1) == '\0') {
						joueur.inventory.get(s.charAt(0) - 48);
					}
				}
			}
			
			else if(s.equals("r")) {
				
			}
			
			else if(s.length() == 42) {
				joueur.regenLife(joueur.pointDeVie.getDenominateur());
			}
			else {
				if(s.equals("stop") == false)
					System.out.println("> help pour obtenir la liste des commandes\n elle a la reponse à tout ;)");
			}
			
		}
		entree.close();
	}
}
