package fr.uvsq.poo.monprojet.personnage;

public class Pnj extends Personnage{
	
	public Pnj(int x, int y, int PointDeVie, char vision) {
		super(x,y,PointDeVie,vision);
		representation = 'N';
	}
	
	public Pnj() {
		super();
		representation = 'N';
	}
}
