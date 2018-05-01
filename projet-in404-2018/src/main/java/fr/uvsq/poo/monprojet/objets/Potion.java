package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Potion extends Objet {
	
	int nombreRegen;
	
	public Potion() {
		super();
		nombreRegen = 0;
	}
	
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
	
	public static Potion spawn(Terrain t, int PV, Point2D position) {
		Potion p = new Potion(PV);
		p.position.setPosition(position);
		t.t[position.getX()][position.getY()] = p.getRepresentation();
		t.objets.add(p);
		return p;
	}
	
	public Potion clone() {
		return new Potion(nombreRegen);
	}
}
