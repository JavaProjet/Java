package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public abstract class Objet {
	public Point2D position; //position sur le terrain
	protected String nomObjet; //nom de l'objet
	protected char representation; //caractere representant l'objet sur le terrain
	
	public Objet(String NomObjet,char CaractereDeRepresentation) {
		position = new Point2D(-1,-1);
		nomObjet = NomObjet;
		representation = CaractereDeRepresentation;
	}
	
	public Objet() {
		position = new Point2D(-1,-1);
		nomObjet = "sans nom";
		representation = '?';
	}
	
	public char getRepresentation() {
		return representation;
	}
	
	public String getNom() {
		return nomObjet;
	}
	
	public void use(Terrain t) {
		//pour faire du polymorphisme
	}
	
	public Objet clone() {
		return null;
		//pour faire du polymorphisme
	}
	
	public String toString() {
		return position + ";" + nomObjet + ";" + representation;
	}
}
