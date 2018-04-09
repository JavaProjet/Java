package fr.uvsq.poo.monprojet.personnage;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Monstre extends Personnage{
	private int damage;
	private boolean CasseMur;
	private int distance;
	
	
	public Monstre(boolean CasseMur, int niveau) {
		this.CasseMur = CasseMur;
		if(this.CasseMur) {
			this.pointDeVie.setFraction(12 * niveau, 12 * niveau);
			damage = 6 * niveau; 
			representation = '@';
			distance = 4;
		}
		else {
			this.pointDeVie.setFraction(10 * niveau, 10 * niveau);
			damage = 4 * niveau;
			representation = 'G';
			distance = 3;
		}
		distance += niveau;
		
	}
	
	public int attaque(Terrain t) {
		if(t.correctPosition(this.devantLui.getX(), this.devantLui.getY())) {
			if(t.joueur.position.equals(devantLui)) {
				t.joueur.setDamage(damage);
				return damage;
			}
		}
		return 0;
	}
	
	public char radar(Terrain t) {
		if(attaque(t) > 0) return 'X';
		int i, i2, j, j2;
		i = this.position.getX() - distance;
		i2 = this.position.getX() + distance;
		j = this.position.getY() - 3;
		j2 = this.position.getY() + distance;
		boolean x = false, y = false;
		Point2D p = new Point2D(0,0);
		while(j < j2 && (p.getX() == 0 || p.getY() == 0)) {
			while(i < i2 && (p.getX() == 0 || p.getY() == 0)) {
				if(t.correctPosition(i, j)) {
					if(t.t[i][j] == t.joueur.getRepresentation()) {
						p.setX(i - this.position.getX()); if(p.getX() < 0) {x = true; p.setX(p.getX()*-1);}
						p.setY(j - this.position.getY()); if(p.getY() < 0) {y = true; p.setY(p.getY()*-1);}
					}
				}i++;	
			}j++;
		}
		char vision;
		if(p.getX() != 0 || p.getY() != 0) {
			System.out.println("detecté");
			if(p.getX() > p.getY()) {
				if((vision = mouvementX(t,x)) == 'X') {
					if((vision = mouvementY(t,y)) == 'X')
						return Personnage.probaDeplacement(4);
					return vision;
				}
				else return vision;
			}
			else {
				if((vision = mouvementY(t,y)) == 'X') {
					if((vision = mouvementX(t,x)) == 'X')
						return Personnage.probaDeplacement(4);
					else return vision;
				}
				else return vision;
			}
		}
		else
			return Personnage.probaDeplacement(4);
	}
	
	private char mouvementX(Terrain t, boolean x) {
		if(x) {
			setVision('O');
			if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'O';
		}
		if(!x) {
			setVision('E');
			if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'E';
		}
		return 'X';
	}
	
	private char mouvementY(Terrain t, boolean x) {
		if(x) {
			setVision('S');
			if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'S';
		}
		if(!x) {
			setVision('N');
			if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'N';
		}
		return 'X';
	}
	
	public static Monstre spawn(Terrain t, int niveau) {
		Random r = new Random();
		boolean casseMur = (r.nextInt(100)%2 == 0);
		niveau -= r.nextInt(3);
		if(niveau < 1) niveau = 1;
		Monstre m = new Monstre(casseMur,niveau);
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
}
