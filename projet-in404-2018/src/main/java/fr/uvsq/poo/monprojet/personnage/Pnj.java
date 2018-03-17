package fr.uvsq.poo.monprojet.personnage;

public class Pnj extends Personnage{
	public static final char PNJ = 'N';
	
	public char representation() {
		return PNJ;
	}
	
	public Pnj(int x, int y, int PointDeVie, char vision) {
		super(x,y,PointDeVie,vision);
	}
	
}
