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
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		p.position.setPosition(l,h);
		p.pointDeVie.setFraction(30, 30);
		t.t[l][h] = p.getRepresentation();
		t.personnage.add(p);
		return p;
	}
}
