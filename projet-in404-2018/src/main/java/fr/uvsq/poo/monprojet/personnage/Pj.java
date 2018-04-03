package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Pj extends Personnage{
	public ArrayList <Objet> inventory;
	
	public Pj(int x, int y, int PointDeVie) {
		super(x,y,PointDeVie);
		inventory = new ArrayList <Objet> ();
		representation = 'i';
	}
	
	public Pj() {
		super();
		inventory = new ArrayList <Objet> ();
		representation = 'i';	
	}
	
	public void initEntree(Terrain t) {
		t.t[t.entree.p.getX()][t.entree.p.getY()] = this.representation;
		t.joueur.position.setPosition(t.entree.p.getX(), t.entree.p.getY());
		t.joueur.setDevant();
	}
	
	public void initSortie(Terrain t) {
		t.t[t.sortie.p.getX()][t.sortie.p.getY()] = this.representation;
		t.joueur.position.setPosition(t.sortie.p.getX(), t.sortie.p.getY());
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
	
}
