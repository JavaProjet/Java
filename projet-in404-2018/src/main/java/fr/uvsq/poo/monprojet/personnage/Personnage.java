package fr.uvsq.poo.monprojet.personnage;

import fr.uvsq.poo.monprojet.maths.fraction.Fraction;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public abstract class Personnage {
	protected char vision; //direction vers laquelle avancer
	public Point2D position; //position sur le terrain
	protected Fraction pointDeVie; //points de vie et points de vies max du personnage
	
	public Personnage(int x, int y, int PointDeVie, char vision) {
		this.setVision(vision);
		this.pointDeVie = new Fraction(PointDeVie,PointDeVie);
		position = new Point2D(x,y);
		
	}
	
	public boolean setVision(char direction) {
		if	   (direction == 'N');
		else if(direction == 'S');
		else if(direction == 'O');
		else if(direction == 'E');
		else {
			return false;
		}
		vision = direction;
		return true;
	}
	
	public void avance() {
		if(vision == 'O') position.deplacementGauche(1);;
		if(vision == 'N') position.deplacementHaut(1);
		if(vision == 'E') position.deplacementDroite(1);
		if(vision == 'S') position.deplacementBas(1);
	}
	
	public void setPointDeVie(int PointDeVie) {
		pointDeVie.setNumerateur(PointDeVie);
	}
	
	public int getPointDeVie() {
		return pointDeVie.getNumerateur();
	}
	
	public int getMaxPointDeVie() {
		return pointDeVie.getDenominateur();
	}
	
	public void setMaxPointDeVie(int newMax) {
		pointDeVie.setDenominateur(newMax);
	}
	
	public boolean setDamage(int damage) { //return false si le personnage est mort parce qu'il n'a plus de points de vie, true sinon
		int res = this.getPointDeVie() - damage;
		if(res <= 0 ) {
			this.setPointDeVie(0);
			return false;
		}
		else {
			this.setPointDeVie(res);
			return true;
		}
	}
	
	public void regenLife(int points) { //redonne des points de vie au personnage
		int res = this.getPointDeVie() + points;
		if(res >= this.getMaxPointDeVie() ) {
			this.setPointDeVie(this.getMaxPointDeVie());
		}
		else {
			this.setPointDeVie(res);
		}
	}
}
