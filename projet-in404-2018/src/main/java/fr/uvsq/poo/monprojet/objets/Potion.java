package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Potion extends Objet {
	
	int nombreRegen;
	
	public Potion(int nombreRegen) {
		super("Potion de soin (+" + nombreRegen + " PV)",'6');
		this.nombreRegen = nombreRegen;
	}
	
	public void use(Terrain t) {
		super.use(t);
		t.joueur.regenLife(nombreRegen);
		t.joueur.inventory.remove(this);
		System.out.println(t + "vos points de vies ont été régénérés");
	}
	
	public static void spawn(Terrain t, int PV, Point2D position) {
		Potion p = new Potion(PV);
		p.position.setPosition(position);
		t.addObjet(p);
	}
	
	public Potion clone() {
		return new Potion(nombreRegen);
	}
	
	public String toString() {
		String s = super.toString();
		s += ";" + nombreRegen;
		return s;
	}
}
