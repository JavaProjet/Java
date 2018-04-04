package fr.uvsq.poo.monprojet.maps;

import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Porte {
	public Point2D position;
	public Terrain t;
	public boolean autorisation; 
	
	public Porte(Terrain t, int x, int y) {
		this.t = t;
		position = new Point2D(x,y);
		autorisation = true;
	}
	
	
}
