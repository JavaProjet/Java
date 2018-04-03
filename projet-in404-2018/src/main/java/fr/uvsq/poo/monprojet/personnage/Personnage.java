package fr.uvsq.poo.monprojet.personnage;

import fr.uvsq.poo.monprojet.maths.fraction.Fraction;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public abstract class Personnage {
	protected char vision; //direction vers laquelle avancer
	public Point2D position; //position sur le terrain
	public Point2D devantLui; //position devant lui, depend de sa vision
	public Fraction pointDeVie; //points de vie et points de vies max du personnage
	protected char representation; //caractere representant le personnage sur le terrain
	
	public Personnage(int x, int y, int PointDeVie) {
		this.vision = 'N';
		this.pointDeVie = new Fraction(PointDeVie,PointDeVie);
		position = new Point2D(x,y);
	}
	
	public Personnage() {
		vision = 'N';
		this.pointDeVie = new Fraction(1,1);
		position = new Point2D(-1,-1);
		devantLui = new Point2D(-1,-1);
		
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
		this.setDevant();
		return true;
	}
	
	protected void setDevant() {
		devantLui.setPosition(position.getX(), position.getY());
		if(vision == 'N') devantLui.deplacementHaut(1);
		if(vision == 'S') devantLui.deplacementBas(1);
		if(vision == 'O') devantLui.deplacementGauche(1);
		if(vision == 'E') devantLui.deplacementDroite(1);
	}
	
	public String getVision() {
		if(vision == 'N') return "Nord";
		if(vision == 'S') return "Sud";
		if(vision == 'O') return "Ouest";
		if(vision == 'E') return "Est";
		else return "erreur, vision inconnu : " + vision;
	}
	
	public void avance() {
		if(vision == 'O') position.deplacementGauche(1);
		if(vision == 'N') position.deplacementHaut(1);
		if(vision == 'E') position.deplacementDroite(1);
		if(vision == 'S') position.deplacementBas(1);
		this.setDevant();
	}
	
	public void setMaxPointDeVie(int newMax) {
		pointDeVie.setDenominateur(newMax);
	}
	
	public boolean setDamage(int damage) { //return false si le personnage est mort parce qu'il n'a plus de points de vie, true sinon
		int res = this.pointDeVie.getNumerateur() - damage;
		if(res <= 0 ) {
			this.pointDeVie.setNumerateur(0);
			return false;
		}
		else {
			this.pointDeVie.setNumerateur(res);
			return true;
		}
	}
	
	public void regenLife(int points) { //redonne des points de vie au personnage
		int res = this.pointDeVie.getNumerateur() + points;
		if(res >= this.pointDeVie.getDenominateur() ) {
			this.pointDeVie.setNumerateur(this.pointDeVie.getDenominateur());
		}
		else {
			this.pointDeVie.setNumerateur(res);
		}
	}
	
	public char getRepresentation() {
		return representation;
	}
	
}