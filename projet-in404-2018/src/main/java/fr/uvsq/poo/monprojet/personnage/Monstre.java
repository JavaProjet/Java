package fr.uvsq.poo.monprojet.personnage;

import java.util.Random;
import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.objets.Argent;
import fr.uvsq.poo.monprojet.objets.Arme;
import fr.uvsq.poo.monprojet.objets.Pile;
import fr.uvsq.poo.monprojet.objets.Potion;

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
		if(t.joueur.position.equals(devantLui)) { //le monstre attaque le joueur s'il est devant lui
			t.joueur.setDamage(damage);
			return damage;
		}
		else { // sinon il regarde s'il est autour de lui pour l'attaquer au tour prochain
			char vision = this.vision;
			this.setVision('N');
			if(t.joueur.position.equals(devantLui)) return 1;
			this.setVision('S');
			if(t.joueur.position.equals(devantLui)) return 1;
			this.setVision('O');
			if(t.joueur.position.equals(devantLui)) return 1;
			this.setVision('E');
			if(t.joueur.position.equals(devantLui)) return 1;
			this.vision = vision;
			return 0;
		}
	}
	
	public char radar() {
		if(attaque() > 0) return 'X'; //sinon il regarde si le joueur est dans son champs de vision et se deplace vers lui
		int i, i2, j, j2;
		i = this.position.getX() - distance; if(this.vision == 'E') i += distance - 1;
		i2 = this.position.getX() + distance;if(this.vision == 'O') i2 -= distance + 1;
		j = this.position.getY() - distance; if(this.vision == 'N') j += distance - 1;
		j2 = this.position.getY() + distance;if(this.vision == 'S') j2 -= distance + 1;
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
					if(vision == '#') return 'X';
					if((vision = mouvementY(y)) == 'X') return Personnage.probaDeplacement(4);
					else if(vision == '#') return 'X';
					else return vision;
				}
				else return vision;
			}
			else {
				if((vision = mouvementY(y)) == 'X') {
					if(vision == '#') return 'X';
					if((vision = mouvementX(x)) == 'X') return Personnage.probaDeplacement(4);
					else if(vision == '#') return 'X';
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
			if(t.correctPosition(devantLui.getX(), devantLui.getY())) {
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'O';
				else if(CasseMur == true && t.t[devantLui.getX()][devantLui.getY()] == Terrain.MUR) {
					t.t[devantLui.getX()][devantLui.getY()] = Terrain.SOL;
					return '#';
				}
			}
		}
		if(!x) {
			setVision('E');
			if(t.correctPosition(devantLui.getX(), devantLui.getY())) {
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'E';
				else if(CasseMur == true && t.t[devantLui.getX()][devantLui.getY()] == Terrain.MUR) {
					t.t[devantLui.getX()][devantLui.getY()] = Terrain.SOL;
					return '#';
				}
			}
		}
		return 'X';
	}
	
	private char mouvementY(boolean x) {
		if(x) {
			setVision('S');
			if(t.correctPosition(devantLui.getX(), devantLui.getY())) {
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'S';
				else if(CasseMur == true && t.t[devantLui.getX()][devantLui.getY()] == Terrain.MUR) {
					t.t[devantLui.getX()][devantLui.getY()] = Terrain.SOL;
					return '#';
				}
			}
		}
		if(!x) {
			setVision('N');
			if(t.correctPosition(devantLui.getX(), devantLui.getY())) {
				if(t.t[devantLui.getX()][devantLui.getY()] == Terrain.SOL) return 'N';
				else if(CasseMur == true && t.t[devantLui.getX()][devantLui.getY()] == Terrain.MUR) {
					t.t[devantLui.getX()][devantLui.getY()] = Terrain.SOL;
					return '#';
				}
			}
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
		int val = ( r.nextInt(12 * 100) + 1 ) % 14;
		if(val > -1 && val < 4)
			Argent.spawn(t, this.position, this.pointDeVie.getDenominateur());
		else if(val == 4)
			Arme.spawn(t,"Epée de monstre", ((CasseMur == true)? distance - 3 : distance - 2) + 1,this.position);
		else if(val > 4 && val < 7)
			Potion.spawn(t,this.pointDeVie.getDenominateur()/2,this.position);
		else if(val == 7)
			Pile.spawn(t,this.position);
		else ;
		t.joueur.addXP(((CasseMur == true)? distance - 3 : distance - 2) * 20);
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
