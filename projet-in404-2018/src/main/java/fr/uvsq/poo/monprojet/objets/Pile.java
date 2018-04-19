package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Pile extends Objet{
	
	public Pile() {
		super("Pile", '-');
	}
	
	public void use(Terrain t) {
		super.use(t);
		for(int i = 0; i < t.joueur.inventory.size(); i++) {
			if(t.joueur.inventory.get(i).getClass() == Teleporteur.class)
				((Teleporteur) t.joueur.inventory.get(i)).recharge();
		}
	}
	
	public static void spawn(Terrain t, Point2D position) {
		Pile a = new Pile();
		a.position.setPosition(position);
		t.objets.add(a);
		t.t[position.getX()][position.getY()] = a.getRepresentation();
	}
}
