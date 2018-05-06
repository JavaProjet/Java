package fr.uvsq.poo.monprojet.objets;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.point.Point2D;

public class Key extends Objet{
	
	public Key() {
		super("Clé menant à la salle finale", 'y');
	}
	
	public void use(Terrain t) {
		if(t.getNumero() == 20) {
			if(t.t[t.joueur.devantLui.getX()][t.joueur.devantLui.getY()] == Terrain.PORTE) {
				if(t.joueur.devantLui.equals(t.sortie.position)) {
					t.sortie.setAutorisation(true);
					t.joueur.inventory.remove(this);
				}
				else {
					System.out.println(t + "Cette porte est déjà ouverte");
				}
			}
			else {
				System.out.println(t + "Il n'y a pas de porte à ouvrir devant vous");
			}
		}
		else {
			System.out.println(t + "cette permet d'ouvrir la salle finale");
		}
	}
	
	public static Key spawn(Terrain t, Point2D position) {
		Key p = new Key();
		p.position.setPosition(position);
		t.t[position.getX()][position.getY()] = p.getRepresentation();
		t.objets.add(p);
		return p;
	}
	
	public String toString() {
		String s = super.toString();	
		return s;
	}
}
