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
		p.setDevant();
		p.pointDeVie.setFraction(2, 2); //30
		t.t[l][h] = p.getRepresentation();
		t.personnage.add(p);
		return p;
	}
	
	public static char probaDeplacement() {
		Random r1 = new Random();
		int p = r1.nextInt(10);
		if(p == 0) return 'N';
		if(p == 1) return 'S';
		if(p == 2) return 'E';
		if(p == 3) return 'O';
		else return 'X';
	}
	
	public boolean setDamage(int damage, Terrain t) { //return false si le personnage est mort parce qu'il n'a plus de points de vie, true sinon
		boolean vivant = (super.setDamage(damage));
		
		if(vivant == false) {
			t.t[position.getX()][position.getY()] = Terrain.SOL;
			t.personnage.remove(this);
		}
		
		return vivant;
	}
	
}
