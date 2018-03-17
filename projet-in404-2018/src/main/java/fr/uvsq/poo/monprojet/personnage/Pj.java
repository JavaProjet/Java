package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

public class Pj extends Personnage{
	public ArrayList <String> inventory;
	public static final char PJ = 'J';
	
	public Pj(int x, int y, int PointDeVie, char vision) {
		super(x,y,PointDeVie,vision);
		inventory = new ArrayList <String> ();
	}
	
	public char representation() {
		return PJ;
	}
}
