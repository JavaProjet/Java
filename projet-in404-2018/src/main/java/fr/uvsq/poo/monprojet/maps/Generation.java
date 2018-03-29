package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;

import fr.uvsq.poo.monprojet.personnage.Pj;

public class Generation {
	public ArrayList <Terrain> carte;
	public Pj joueur;
	
	public Generation() {
		carte = new ArrayList <Terrain> ();
		joueur = new Pj();
		carte.add(this.generation1());
		joueur.initEntree(carte.get(0));
		joueur.pointDeVie.setFraction(20, 20);
	}
	
	
	
	public Terrain generation1() {
		Terrain t;
		Random r1 = new Random();
		int y = r1.nextInt(21) + 7; //min = 7 max = 27
		int x = r1.nextInt(30) + 15;
		t = new Terrain(x,y, joueur);
		int h = r1.nextInt(y);
		t.t[x-1][h] = Terrain.PORTE;
		t.t[0][h] = Terrain.PORTE;
		t.entree = new Porte(t,0,h,true);
		t.entree.t = null;
		t.sortie = new Porte(t,x-1,h,false);
		t.sortie.t = null;
		return t;
	}
}
