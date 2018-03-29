package fr.uvsq.poo.monprojet.maps;

import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Porte {
	public Point2D p;
	public Terrain t;
	public boolean entree; 
	
	public Porte(Terrain t, int x, int y, boolean entree) {
		this.t = t;
		p = new Point2D(x,y);
		this.entree = entree;
	}
	
	
}
