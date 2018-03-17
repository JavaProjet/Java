package fr.uvsq.poo.monprojet;

import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Objet {
	public Point2D p; //position sur le terrain
	public String nomObjet; //nom de l'objet
	public char representation; //caractere representant l'objet sur le terrain
	
	
	public Objet(String NomObjet,char CaractereDeRepresentation,int x, int y) {
		p = new Point2D(x,y);
		nomObjet = NomObjet;
		representation = CaractereDeRepresentation;
	}
	
	
	public char getCaractereRepresentation() {
		return representation;
	}
	
	public String getNom() {
		return nomObjet;
	}
}
