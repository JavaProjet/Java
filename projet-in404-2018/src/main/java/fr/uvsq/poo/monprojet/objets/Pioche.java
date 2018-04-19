package fr.uvsq.poo.monprojet.objets;

import java.util.Random;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Pioche extends Objet{
	
	private int durability = 100;
	
	public Pioche() {
		super("Pioche",'T');
		
	}
	
	public static Pioche spawn(Terrain t) {
		Random r1 = new Random();
		Pioche p = new Pioche();
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
	
	public static Pioche spawn(Terrain t,Point2D position) {
		Pioche p = new Pioche();
		p.position.setPosition(position);
		t.t[position.getX()][position.getY()] = p.getRepresentation();
		t.objets.add(p);
		return p;
	}
	
	public void use(Terrain t) {
		super.use(t);
		if(t.correctPosition(t.joueur.devantLui.getX(), t.joueur.devantLui.getY()))
			if(t.t[t.joueur.devantLui.getX()][t.joueur.devantLui.getY()] == Terrain.MUR) {
				t.t[t.joueur.devantLui.getX()][t.joueur.devantLui.getY()] = Terrain.SOL;
				Argent.spawn(t, t.joueur.devantLui, 1);
				durability--;
				System.out.println(t);
			}
			else {
				System.out.println(t + "Il n'y a pas de mur à casser devant vous");
			}
		if(durability == 0) {
			System.out.println(t + "votre pioche s'est cassé");
			t.joueur.inventory.remove(this);
		}
	}
}
