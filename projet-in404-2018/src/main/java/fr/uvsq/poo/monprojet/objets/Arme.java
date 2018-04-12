package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;

public class Arme extends Objet{
	
	public int damage;
	public int durability;
	
	public Arme(String description, char representation, int niveau) {
		super(description,representation);
		this.damage = 4 * niveau;
		super.nomObjet +=" damages:" + damage;
		durability = 10 * niveau;
	}
	
	public void use(Terrain t) {
		for(int i = 0; i < t.monstres.size(); i++) {
			if(t.monstres.get(i).position.equals(t.joueur.devantLui)) {
				t.monstres.get(i).setDamage(damage);
				durability--;
			}
		}
		if(durability == 0) {
			System.out.println("votre arme s'est cassé");
			t.joueur.inventory.remove(this);
		}
	}
	
	public static Arme spawn(Terrain t,String description, char representation, int niveau) {
		Random r1 = new Random();
		Arme a = new Arme(description,representation,niveau);
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		a.position.setPosition(l,h);
		t.t[l][h] = a.getRepresentation();
		t.objets.add(a);
		return a;
	}
}
