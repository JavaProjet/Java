package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.personnage.Pj;

public class Potion extends Objet {
	
	int nombreRegen;
	
	public Potion() {
		super();
	}
	
	public Potion(String NomObjet,char CaractereDeRepresentation,int x, int y, int nombreRegen) {
		super(NomObjet,CaractereDeRepresentation,x,y);
	}
	
	public void use(Pj p) {
		super.use(p);
		p.regenLife(nombreRegen);
		for(int i = 0; i < p.inventory.size(); i++) {
			if(p.inventory.get(i) == this) {
				p.inventory.remove(i);
			}
		}
	}
}
