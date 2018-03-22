package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.Objet;

public class Pj extends Personnage{
	public ArrayList <Objet> inventory;
	
	public Pj(int x, int y, int PointDeVie, char vision) {
		super(x,y,PointDeVie,vision);
		inventory = new ArrayList <Objet> ();
		representation = 'J';
	}
	
	public Pj() {
		super();
		inventory = new ArrayList <Objet> ();
		representation = 'J';
		
	}
	
}
