package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;

import fr.uvsq.poo.monprojet.personnage.Pj;

public class Generation {
	private static final int nombreCarte = 20;
	public ArrayList <Terrain> carte;
	public Pj joueur;
	
	public Generation() {
		carte = new ArrayList <Terrain> ();
		joueur = new Pj();
		for(int i = 0; i < nombreCarte; i++)
			carte.add(this.generation1());
		
		this.connection();
		
		joueur.initEntree(carte.get(0));
		joueur.pointDeVie.setFraction(20, 20);
	}
	
	private void connection() {
		int i;
		carte.get(0).entree.autorisation = false;
		carte.get(nombreCarte - 1).sortie.autorisation = false;
		for(i = 1; i < nombreCarte - 1; i++) {
			carte.get(i - 1).sortie.t = carte.get(i);
			carte.get(i).entree.t = carte.get(i - 1);
			carte.get(i).sortie.t = carte.get(i + 1);
			carte.get(i + 1).entree.t = carte.get(i);
		}
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
		t.entree = new Porte(t,0,h);
		t.entree.t = null;
		t.sortie = new Porte(t,x-1,h);
		t.sortie.t = null;
		return t;
	}
}
