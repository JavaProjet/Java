package fr.uvsq.poo.monprojet.personnage;

import java.util.Random;
import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.objets.Argent;

public class Monstre extends Personnage{
	private int damage;
	private boolean CasseMur;
	private int distance;
	private Terrain t;
	
	
	public Monstre(boolean CasseMur,Terrain t, int niveau) {
		this.CasseMur = CasseMur;
		this.t = t;
		if(this.CasseMur) {
			this.pointDeVie.setFraction(12 * niveau, 12 * niveau);
			damage = 3 * niveau; 
			representation = '@';
			distance = 3;
		}
		else {
			this.pointDeVie.setFraction(10 * niveau, 10 * niveau);
			damage = 2 * niveau;
			representation = 'G';
			distance = 2;
		}
		distance += niveau;
		
	}
	
	public int attaque() {
		if(t.correctPosition(this.devantLui.getX(), this.devantLui.getY())) {
			if(t.joueur.position.equals(devantLui)) {
				t.joueur.setDamage(damage);
				return damage;
			}
		}
		return 0;
	}
	
	public char radar() {
		if(attaque() > 0) return 'X';
		int i, i2, j, j2;
		i = this.position.getX() - distance;
		i2 = this.position.getX() + distance;
		j = this.position.getY() - distance;
		j2 = this.position.getY() + distance;
		int k = i,l = j;
		boolean x = false, y = false;
		Point2D p = new Point2D(0,0);
		while(l < j2 && (p.getX() == 0 || p.getY() == 0)) {
			while(k < i2 && (p.getX() == 0 || p.getY() == 0)) {
				if(t.correctPosition(k, l)) {
					if(k == t.joueur.position.getX() && l == t.joueur.position.getY()) {
						p.setX(k - this.position.getX()); if(p.getX() < 0) {x = true; p.setX(p.getX()*-1);}
						p.setY(l - this.position.getY()); if(p.getY() < 0) {y = true; p.setY(p.getY()*-1);}
					}
				}k++;
			}l++; k = i;
		}
		char vision;
		if(p.getX() != 0 || p.getY() != 0) {
			if(p.getX() > p.getY()) {
				if((vision = mouvementX(x)) == 'X') {
					if((vision = mouvementY(y)) == 'X') return Personnage.probaDeplacement(4);
					else return vision;
				}
				else return vision;
			}
			else {
				if((vision = mouvementY(y)) == 'X') {
					if((vision = mouvementX(x)) == 'X') return Personnage.probaDeplacement(4);
					else return vision;
				}
				else return vision;
			}
		}
		else return Personnage.probaDeplacement(4);	
	}
	
	private char mouvementX(boolean x) {
		if(x) {
			setVision('O');
			if(t.correctPosition(devantLui.getX(), devantLui.getY()))
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'O';
		}
		if(!x) {
			setVision('E');
			if(t.correctPosition(devantLui.getX(), devantLui.getY()))
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'E';
		}
		return 'X';
	}
	
	private char mouvementY(boolean x) {
		if(x) {
			setVision('S');
			if(t.correctPosition(devantLui.getX(), devantLui.getY()))
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'S';
		}
		if(!x) {
			setVision('N');
			if(t.correctPosition(devantLui.getX(), devantLui.getY()))
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'N';
		}
		return 'X';
	}
	
	public static Monstre spawn(Terrain t, int niveau) {
		Random r = new Random();
		boolean casseMur = (r.nextInt(100)%2 == 0);
		niveau -= r.nextInt(3);
		if(niveau < 1) niveau = 1;
		Monstre m = new Monstre(casseMur,t,niveau);
		int l,h;
		do{
			l = r.nextInt(t.getLargeur());
			h = r.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		m.position.setPosition(l,h);
		m.setDevant();
		m.pointDeVie.setFraction(10, 10);
		t.t[l][h] = m.getRepresentation();
		t.monstres.add(m);
		
		return m;
	}
	
	private void drop() {
		Random r = new Random();
		if((r.nextInt(100)+1)%2 == 0) {
			Argent.Spawn(t, this.position.getX(), this.position.getY(), this.pointDeVie.getDenominateur()/2);
		}
	}
	
	public boolean setDamage(int damage) { //si le monstre meurt un drop de rubis peut apparaitre
		boolean ret = super.setDamage(damage);
		
		if(ret == false) {// drop possible
			t.t[this.position.getX()][this.position.getY()] = Terrain.SOL;
			t.monstres.remove(this);
			drop();
		}
		
		return ret;
	}
}
