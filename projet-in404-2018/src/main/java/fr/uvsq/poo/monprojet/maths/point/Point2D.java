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
		return x + "," + y;
	}
	
	public static Point2D parsePoint2D(String s) {
		int i,j = -1;
		String s1 = "", s2 = "";
		for(i = 0; i < s.length(); i++) {
			if(s.charAt(i) == ',') j = i;
		}
		if(j == -1) return null;
		else {
			for(i = 0; i < j; i++) {
				s1 += s.charAt(i);
			}
			for(i = j + 1; i < s.length(); i++) {
				s2 += s.charAt(i);
			}
			return new Point2D(Integer.parseInt(s1),Integer.parseInt(s2));
		}
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
	
	public void setPosition(Point2D p) {
		this.setX(p.getX());
		this.setY(p.getY());
	}
}
