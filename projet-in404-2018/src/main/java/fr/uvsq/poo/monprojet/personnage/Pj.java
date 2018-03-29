package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;
import java.util.Random;

import fr.uvsq.poo.monprojet.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

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
	
	public void initDepart(Terrain t) {
		boolean encore = true;
		int l = 0,h = 0;
		Random r1 = new Random();
		while(encore) {
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
			if(t.t[l][h] == Terrain.SOL) {
				this.position.setPosition(l,h);
				encore = false;
			}
		}
		t.t[l][h] = this.representation;
	}
	
}
