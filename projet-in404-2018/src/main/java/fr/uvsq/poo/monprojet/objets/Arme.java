package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Arme extends Objet{
	
	private int damage;
	private int durability;
	private String nomArme;
	
	public Arme(String description, int niveau) {
		super(description,'!');
		this.setDamage(4 * niveau);
		setDurability(20 * niveau);
		setNomObjet(getNomArme());
		
	}
	
	public void use(Terrain t) {
		boolean frappe = false;
		for(int i = 0; i < t.monstres.size(); i++) {
			if(t.monstres.get(i).position.equals(t.joueur.devantLui)) {
				boolean ret = t.monstres.get(i).setDamage(damage);
				if(ret == false) {
					if(t.t[t.monstres.get(i).position.getX()][t.monstres.get(i).position.getY()] == t.monstres.get(i).getRepresentation()) {
						t.t[t.monstres.get(i).position.getX()][t.monstres.get(i).position.getY()] = Terrain.SOL;
					}
					t.monstres.remove(t.monstres.get(i));
					System.out.println(t + "Monstre : *meurt*");
				}
				else 
					System.out.println(t + "points de vie du monstres : " + t.monstres.get(i).pointDeVie.toString());
				frappe = true;
			}
		}
		setDurability(getDurability() - 1);
		if(frappe == false)System.out.println("il n'y a pas de monstre sur qui taper devant vous, mais vous avez quand-même abimé votre épée");
		if(getDurability() == 0) {
			System.out.println(t + "votre arme s'est cassé");
			t.joueur.inventory.remove(this);
		}
		nomObjet = new String(getNomArme() + " " + getDamage() + " damages, (" + getDurability() + "use)");
	}
	
	public static void spawn(Terrain t,String description, int niveau) {
		Random r1 = new Random();
		Arme a = new Arme(description, niveau);
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		t.addObjet(a);
	}
	
	public static void spawn(Terrain t,String description, int niveau, Point2D position) {
		Arme a = new Arme(description,niveau);
		a.position.setPosition(position);
		t.addObjet(a);
	}
	
	public Arme clone() {
		Arme a = new Arme(nomObjet, getDamage() / 4);
		a.setDurability(durability);
		return a;
	}
	
	public String toString() {
		String s = super.toString();
		s += ";" + getDamage() + ";" + getDurability() + ";" + getNomArme();
		return s;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public void setNomObjet(String nomObjet) {
		setNomArme(nomObjet);
		super.setNomObjet(getNomArme() + " " + getDamage() + " damages (" + getDurability() + "use)");
	}

	public String getNomArme() {
		return nomArme;
	}

	public void setNomArme(String nomArme) {
		this.nomArme = nomArme;
	}
}
