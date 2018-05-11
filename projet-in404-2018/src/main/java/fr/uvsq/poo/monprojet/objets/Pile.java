package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Pile extends Objet{
	
	public Pile() {
		super("Pile", '-');
	}
	
	public void use(Terrain t) {
		super.use(t);
		boolean used = false;
		for(int i = 0; i < t.joueur.inventory.size() && used == false; i++) {
			if(t.joueur.inventory.get(i).getClass() == Teleporteur.class) {
				((Teleporteur) t.joueur.inventory.get(i)).recharge(); used = true;
			}
		}
		if(used == false)System.out.println("vous ne possédez pas de téléporteur.");
	}
	
	public static void spawn(Terrain t, Point2D position) {
		Pile a = new Pile();
		a.position.setPosition(position);
		t.addObjet(a);
	}
	
	public Pile clone() {
		return new Pile();
	}
	
	public String toString() {
		String s = super.toString();	
		return s;
	}
}
