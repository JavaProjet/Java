package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Pj extends Personnage{
	public ArrayList <Objet> inventory;
	
	public Pj(int x, int y, int PointDeVie) {
		super(x,y,PointDeVie);
		inventory = new ArrayList <Objet> ();
		representation = '>';
	}
	
	public Pj() {
		super();
		inventory = new ArrayList <Objet> ();
		representation = '>';	
	}
	
	public void initEntree(Terrain t) {
		t.t[t.entree.position.getX()][t.entree.position.getY()] = this.representation;
		t.joueur.position.setPosition(t.entree.position.getX(), t.entree.position.getY());
		t.joueur.setDevant();
	}
	
	public void initSortie(Terrain t) {
		t.t[t.sortie.position.getX()][t.sortie.position.getY()] = this.representation;
		t.joueur.position.setPosition(t.sortie.position.getX(), t.sortie.position.getY());
		t.joueur.setDevant();
	}
	
	public void addObjet(Objet o) {
		inventory.add(o);
	}
	
	public void afficherInventaire() {
		for(int i = 1; i < inventory.size() + 1; i++) {
			System.out.println(i + "." + inventory.get(i - 1).getNom());
		}
	}
	
	public void setVision(char direction) {
		super.setVision(direction);
		if(direction == 'N') this.representation = '^';
		if(direction == 'S') this.representation = 'v';
		if(direction == 'O') this.representation = '<';
		if(direction == 'E') this.representation = '>';
	}
	
}
