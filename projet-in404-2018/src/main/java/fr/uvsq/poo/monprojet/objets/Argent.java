package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Argent extends Objet{
	private int valeur;
	
	public Argent(int valeur) {
		super("Rubis d'une valeur de " + valeur,'*');
		this.valeur = valeur;
	}
	
	public char getRepresentation() {
		return '*';
	}
	
	public static void spawn(Terrain t, Point2D position, int valeur) {
		Argent a = new Argent(valeur);
		a.position.setPosition(position);
		t.objets.add(a);
		t.t[position.getX()][position.getY()] = a.getRepresentation();
	}
	
	public void use(Terrain t) {
		super.use(t);
		t.joueur.addMonnaie(valeur);
		t.joueur.inventory.remove(this);
		System.out.println("ils ont été importés dans votre porte monnaie");
	}
	
	public int getMonnaie() {
		return valeur;
	}
	
	public Argent clone() {
		return new Argent(valeur);
	}
	
	public String toString() {
		String s = super.toString();
		s += ";" + valeur;
		return s;
	}
}
