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
		t = new char[hauteur][largeur];
		this.largeur = largeur;
		this.hauteur = hauteur;
		int i,j;
		for(i = 0; i < hauteur; i++) {
			for(j = 0; j < largeur; j++) {
				t[i][j] = MUR;
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
}
