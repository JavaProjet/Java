package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;

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
		System.out.println("vos points de vies ont été régénérés");
	}
	
	public static Potion spawn(Terrain t, int max) {
		Random r1 = new Random();
		Potion p = new Potion(r1.nextInt(max) + 1);
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		p.position.setPosition(l,h);
		t.t[l][h] = p.getRepresentation();
		t.objets.add(p);
		return p;
	}
}
