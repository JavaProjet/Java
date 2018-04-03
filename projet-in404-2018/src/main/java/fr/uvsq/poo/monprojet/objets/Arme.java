package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;

public class Arme extends Objet{
	
	public int damage;
	public int durability;
	
	public Arme(String description, char representation, int damage, int niveau) {
		super(description,representation);
		this.damage = damage;
		super.nomObjet += " (" + "damages:" + damage + ", durabilité:" + (durability = 50 * niveau) + ")";
	}
	
	public void use(Terrain t) {
		
		
		
		durability--;
		if(durability == 0) {
			t.joueur.inventory.remove(this);
		}
	}
	
	
}
