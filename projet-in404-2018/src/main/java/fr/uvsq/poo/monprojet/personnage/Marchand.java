package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;
import java.util.Random;
import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Marchand extends Personnage{
	ArrayList <Objet> vente;
	ArrayList <Boolean> infini;
	
	public Marchand() {
		super();
		representation = 'M';
	}
	
	public static Marchand spawn(Terrain t) {
		Marchand p = new Marchand();
		Random r1 = new Random();
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		p.position.setPosition(l,h);
		p.setDevant();
		p.pointDeVie.setFraction(10, 10);
		t.t[l][h] = p.getRepresentation();
		t.vendeur = p;
		return p;
	}
	
	
}
