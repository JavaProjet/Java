package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.personnage.Pj;

public class Bouclier extends Objet{
	private int durability;
	private int absorption;
	private String nomBouclier;
	
	public Bouclier(String name, int niveau) {
		super(name,'D');
		absorption = niveau * 2;
		durability = niveau * 10;
		setNomObjet(name);
	}
	
	public static void spawn(Terrain t,String name, int niveau, Point2D position) {
		Bouclier a = new Bouclier(name, niveau);
		a.position.setPosition(position);
		t.objets.add(a);
		t.t[position.getX()][position.getY()] = a.getRepresentation();
	}
	
	public void use(Terrain t) {
		t.joueur.protection = this;
		System.out.println(t + "votre bouclier est équipé, il vous protègera durant le combat");
	}
	
	public int getDurability() {
		return durability;
	}
	
	public void setDurability(int durability) {
		this.durability = durability;
	}
	
	public void reduceDurability(Pj joueur) {
		durability--;
		if(durability == 0) {
			joueur.protection = null;
			joueur.inventory.remove(this);
		}
	}
	
	public int getAbsoption() {
		return durability;
	}
	
	public void setAbsorption(int absorption) {
		this.absorption = absorption;
	}
	
	public int getAbsorption() {
		return absorption;
	}
	
	public String getNomBouclier() {
		return nomBouclier;
	}
	
	public void setNomBouclier(String nomBouclier) {
		this.nomBouclier = nomBouclier;
	}
	
	public void setNomObjet(String nomBouclier) {
		setNomBouclier(nomBouclier);
		super.setNomObjet(getNomBouclier() + " " + getAbsorption() + " absorption (" + getDurability() + "durability)  usage : s'en équiper");
	}
	
	public Bouclier clone() {
		Bouclier b = new Bouclier(" ",1);
		b.setNomObjet(nomBouclier);
		b.setAbsorption(absorption);
		b.setDurability(durability);
		return b;
	}
	
	public String toString() {
		String s = super.toString();
		s += ";" + durability + ";" + absorption + ";" + nomBouclier;
		return s;
	}
}