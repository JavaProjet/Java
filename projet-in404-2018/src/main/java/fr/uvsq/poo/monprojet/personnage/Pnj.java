package fr.uvsq.poo.monprojet.personnage;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;

public class Pnj extends Personnage{
	
	public Pnj(int x, int y, int PointDeVie) {
		super(x,y,PointDeVie);
		representation = 'N';
	}
	
	public Pnj() {
		super();
		representation = 'N';
	}
	
	public static Pnj spawn(Terrain t) {
		Pnj p = new Pnj();
		Random r1 = new Random();
		int l = r1.nextInt(t.getLargeur());
		int h = r1.nextInt(t.getHauteur());
		if(t.t[l][h] == Terrain.SOL) {
			p.position.setPosition(l,h);
			p.pointDeVie.setFraction(100, 100);
		}
		return p;
	}
}
