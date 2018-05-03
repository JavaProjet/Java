package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Arme extends Objet{
	
	public int damage;
	public int durability;
	public String nomArme;
	public Arme(String description, int niveau) {
		super(description,'!');
		nomArme = new String(description);
		this.damage = 4 * niveau;
		durability = 20 * niveau;
		super.nomObjet +=" " + damage + " damages, (" + durability + "use)";
		
	}
	
	public void use(Terrain t) {
		boolean frappe = false;
		for(int i = 0; i < t.monstres.size(); i++) {
			if(t.monstres.get(i).position.equals(t.joueur.devantLui)) {
				t.monstres.get(i).setDamage(damage);
				System.out.println(t + "vous avez infligé " + damage + " damages au monstre");
				frappe = true;
			}
		}
		durability--;
		if(frappe == false)System.out.println("il n'y a pas de monstre sur qui taper devant vous, mais vous avez quand-même abimé votre épée");
		if(durability == 0) {
			System.out.println(t + "votre arme s'est cassé");
			t.joueur.inventory.remove(this);
		}
		nomObjet = new String(nomArme + " " + damage + " damages, (" + durability + "use)");
	}
	
	public static Arme spawn(Terrain t,String description, int niveau) {
		Random r1 = new Random();
		Arme a = new Arme(description, niveau);
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
	
	public static Arme spawn(Terrain t,String description, int niveau, Point2D position) {
		Arme a = new Arme(description,niveau);
		a.position.setPosition(position);
		t.t[position.getX()][position.getY()] = a.getRepresentation();
		t.objets.add(a);
		return a;
	}
	
	public Arme clone() {
		Arme a = new Arme(nomObjet, damage / 4);
		a.durability = durability;
		return a;
	}
	
	public String toString() {
		String s = this.getClass().getSimpleName();
		s += ";" + super.toString();
		s += ";" + damage + ";" + durability + ";" + nomArme;
		return s;
	}
}
