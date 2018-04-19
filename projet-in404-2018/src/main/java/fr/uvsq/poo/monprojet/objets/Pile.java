package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;

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
}
