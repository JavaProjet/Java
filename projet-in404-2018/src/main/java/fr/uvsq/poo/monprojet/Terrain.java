package fr.uvsq.poo.monprojet;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.personnage.Pnj;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Terrain {
	public char[][] t;
	public int largeur,hauteur;
	public ArrayList <Pnj> personnage;
	public ArrayList <Objet> objets;
	public Pj joueur;
	public static final char MUR = '#';
	public static final char VIDE = ' ';
	public static final char SOL = 'O';
	
	
	public Terrain(int largeur, int hauteur) {
		personnage = new ArrayList <Pnj> ();
		objets = new ArrayList <Objet> ();
		t = new char[hauteur][largeur];
		this.largeur = largeur;
		this.hauteur = hauteur;
		int i,j;
		for(i = 0; i < hauteur; i++) {
			for(j = 0; j < largeur; j++) {
				t[i][j] = SOL;
			}
		}
	}
	
	public String toString() {
		String s = "";
		int i,j;
		for(i = 0; i < hauteur; i++) {
			for(j = 0; j < largeur; j++) {
				s += t[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	public boolean addPersonnage(Pnj p) {
		if(p.position.getX() == -1 || p.position.getY() == -1) return false;
		personnage.add(p);
		return true;
	}
}
