package fr.uvsq.poo.monprojet;

public class Terrain {
	public char[][] t;
	public int largeur,hauteur;
	public static final char MUR = '#';
	public static final char VIDE = ' ';
	public static final char PNJ = '0';
	public static final char PJ = 'H';
	public static final char SOL = '*';
	
	
	public Terrain(int largeur, int hauteur) {
		t = new char[largeur][hauteur];
		this.largeur = largeur;
		this.hauteur = hauteur;
		int i,j;
		for(i = 0; i < largeur; i++) {
			for(j = 0; j < hauteur; i++) {
				t[i][j] = SOL;
			}
		}
	}
	
	public String toString() {
		String s = "";
		int i,j;
		for(i = 0; i < largeur; i++) {
			for(j = 0; j < hauteur; i++) {
				s += t[i][j];
			}
			s += "\n";
		}
		return s;
	}
}
