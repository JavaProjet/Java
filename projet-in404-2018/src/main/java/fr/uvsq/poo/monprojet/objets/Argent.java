package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Argent extends Objet{
	private int valeur;
	
	public Argent(int valeur) {
		super("Rubis d'une valeur de " + valeur + ". utilisez le pour mettre dans votre porte monnaie",'*');
		this.valeur = valeur;
	}
	
	public char getRepresentation() {
		return '*';
	}
	
	public static void spawn(Terrain t, Point2D position, int valeur) {
		Random r = new Random();
		if(r.nextInt(100) < 40) {
			Argent a = new Argent(valeur);
			a.position.setPosition(position);
			t.objets.add(a);
			t.t[position.getX()][position.getY()] = a.getRepresentation();
		}
	}
	
	public void use(Terrain t) {
		super.use(t);
		t.joueur.addMonnaie(valeur);
		t.joueur.inventory.remove(this);
		System.out.println("vos rubis ont été importés dans votre porte monnaie");
	}
}
