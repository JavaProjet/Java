package fr.uvsq.poo.monprojet;

import java.util.ArrayList;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.personnage.Pnj;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Terrain {
	public char[][] t;
	private int largeur,hauteur;
	public ArrayList <Pnj> personnage;
	public ArrayList <Objet> objets;
	public Pj joueur;
	public static final char MUR = '#';
	public static final char VIDE = ' ';
	public static final char SOL = '.';
	
	
	public Terrain(int largeur, int hauteur) {
		personnage = new ArrayList <Pnj> ();
		objets = new ArrayList <Objet> ();
		t = new char[largeur][hauteur];
		joueur = new Pj(0,0,20,'N');
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
				
				if(joueur.correctPosition(this,i,j)) {
					s += joueur.getRepresentation();
				}
				else {
					s += t[i][j];
				}
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
	
	public boolean addPnj(Pnj p) {
		if(p.position.getX() == -1 || p.position.getY() == -1) return false;
		personnage.add(p);
		return true;
	}
	
	public void play() {
		Scanner entree = new Scanner(System.in);
		String s = "";
		while(s.equals("stop") == false) {
			s = "";
			s = entree.nextLine();
			if(s.equals("1")) {
				joueur.avance();
				if(joueur.correctPosition(this,-1,-1) == false)
					joueur.recule();
				System.out.print(this);
			}
			else if(s.equals("2")) {
				joueur.tournerGauche();
				System.out.print(this);
			}
			else if(s.equals("3")) {
				joueur.tournerDroite();
				System.out.print(this);
			}
			else if(s.equals("help")) {
				System.out.println("\"commande\".\"informations de la commande\"");
				System.out.println("1.avancer");
				System.out.println("2.tourner à gauche");
				System.out.println("3.tourner à droite");
				System.out.println("info.obtenir des informations sur votre personnage");
			}
			else if(s.equals("info")) {
				System.out.println("points de vies : " + joueur.pointDeVie);
				System.out.println("vous regardez dans cette direction : " + joueur.getVision());
				System.out.println("position : " + (joueur.position.getX() + 1) + "," + (joueur.position.getY() + 1));
			}
			else System.out.println("entrez help pour obtenir la liste des commandes");
		}
		entree.close();
	}
}
