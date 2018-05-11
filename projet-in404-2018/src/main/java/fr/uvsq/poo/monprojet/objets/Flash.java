package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Flash extends Objet{
	
	public Flash() {
		super("Bombe flash éléctrique (éclaircit la pièce)", '0');
	}
	
	public void use(Terrain t) {
		int i;
		boolean used = false;
		for(i = 0; i < t.joueur.inventory.size() && used == false; i++) {
			if(t.joueur.inventory.get(i).getClass() == Pile.class) {
				used = true;
				t.joueur.inventory.remove(i);
				t.joueur.inventory.remove(this);
				t.setSombre(false);
				System.out.println(t + "une pile a été consommé et la carte s'éclaircit");
			}
		}
		if(used == false) System.out.println("vous avez besoin d'une pile pour utiliser cet objet");
	}
	
	public static void spawn(Terrain t, Point2D position) {
		Flash a = new Flash();
		a.position.setPosition(position);
		t.addObjet(a);
	}
	
	public Flash clone() {
		return new Flash();
	}
	
	public String toString() {
		String s = super.toString();
		return s;
	}
}
