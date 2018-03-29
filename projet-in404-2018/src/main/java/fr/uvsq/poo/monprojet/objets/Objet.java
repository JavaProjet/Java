package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.personnage.Pj;

public abstract class Objet {
	public Point2D p; //position sur le terrain
	protected String nomObjet; //nom de l'objet
	protected char representation; //caractere representant l'objet sur le terrain
	
	public Objet(String NomObjet,char CaractereDeRepresentation,int x, int y) {
		p = new Point2D(x,y);
		nomObjet = NomObjet;
		representation = CaractereDeRepresentation;
	}
	
	public Objet() {
		p = new Point2D(-1,-1);
		nomObjet = "sans nom";
		representation = '?';
	}
	
	public char getRepresentation() {
		return representation;
	}
	
	public String getNom() {
		return nomObjet;
	}
	
	public void use(Pj p) {
		
	}
}
