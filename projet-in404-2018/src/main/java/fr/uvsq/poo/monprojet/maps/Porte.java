package fr.uvsq.poo.monprojet.maps;

import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Porte {
	public Point2D position;
	public Terrain t;
	private boolean autorisation; 
	
	public Porte(int x, int y) {
		position = new Point2D(x,y);
		setAutorisation(true);
	}

	public boolean getAutorisation() {
		return autorisation;
	}

	public void setAutorisation(boolean autorisation) {
		this.autorisation = autorisation;
	}
	
	
}
