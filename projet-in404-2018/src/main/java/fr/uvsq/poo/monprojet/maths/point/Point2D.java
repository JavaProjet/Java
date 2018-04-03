package fr.uvsq.poo.monprojet.maths.point;

public class Point2D {
	private int x,y;
	
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public String toString() {
		return ("position : " + x + "," + y + "\n");
	}
	
	public void deplacementHaut(int nombre) {
		y += nombre;
	}
	
	public void deplacementBas(int nombre) {
		y -= nombre;
	}
	
	public void deplacementDroite(int nombre) {
		x += nombre;
	}
	
	public void deplacementGauche(int nombre) {
		x -= nombre;
	}

	public boolean equals(Point2D p) {
		if(p.getX() == this.x && p.getY() == this.y) {
			return true;
		}
		return false;
	}
}
