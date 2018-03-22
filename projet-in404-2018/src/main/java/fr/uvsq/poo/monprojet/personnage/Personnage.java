package fr.uvsq.poo.monprojet.personnage;

import fr.uvsq.poo.monprojet.Terrain;
import fr.uvsq.poo.monprojet.maths.fraction.Fraction;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public abstract class Personnage {
	protected char vision; //direction vers laquelle avancer
	public Point2D position; //position sur le terrain
	public Fraction pointDeVie; //points de vie et points de vies max du personnage
	protected char representation; //caractere representant le personnage sur le terrain
	
	public Personnage(int x, int y, int PointDeVie, char vision) {
		this.setVision(vision);
		this.pointDeVie = new Fraction(PointDeVie,PointDeVie);
		position = new Point2D(x,y);
		
	}
	
	public Personnage() {
		this.setVision(vision);
		this.pointDeVie = new Fraction(1,1);
		position = new Point2D(-1,-1);
		
	}
	
	public boolean tournerGauche() {
		if	   (vision == 'N') vision = 'O';
		else if(vision == 'S') vision = 'E';
		else if(vision == 'O') vision = 'S';
		else if(vision == 'E') vision = 'N';
		else {
			return false;
		}
		return true;
	}
	
	public boolean tournerDroite() {
		if	   (vision == 'N') vision = 'E';
		else if(vision == 'S') vision = 'O';
		else if(vision == 'O') vision = 'N';
		else if(vision == 'E') vision = 'S';
		else {
			return false;
		}
		return true;
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
	}
	
	public void recule() {
		if(vision == 'O') position.deplacementGauche(-1);
		if(vision == 'N') position.deplacementHaut(-1);
		if(vision == 'E') position.deplacementDroite(-1);
		if(vision == 'S') position.deplacementBas(-1);
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
	
	public char getRepresentation() {
		return representation;
	}
	
	public boolean correctPosition(Terrain t,int x, int y) { //si x=-1 et y=-1 la derniere condition est ignoré
		
		if(position.getY() > -1 && position.getY() < t.getHauteur()) { // si la position du personnage est incluse dans le terrain en Y
			if(position.getX() > -1 && position.getX() < t.getLargeur()) { // idem en X
				if(t.t[position.getX()][position.getY()] == Terrain.SOL ) { //si la position du personnage correspond a un sol dans le terrain
					if(x == -1 && y == -1) { // si on ignore la condition ci-dessous
						return true;
					}
					else if(position.getX() == x && position.getY() == y) { //si la position du personnage est egale a la position x,y
						return true;
					}
				}
			}
		}
		return false;
	}
}
