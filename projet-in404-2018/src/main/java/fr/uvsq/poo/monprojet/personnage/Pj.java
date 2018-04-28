package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Pj extends Personnage {
	public ArrayList <Objet> inventory;
	private int monnaie;
	private int rapidUse = 0;
	
	public Pj(int x, int y, int PointDeVie) {
		super(x,y,PointDeVie);
		inventory = new ArrayList <Objet> ();
		representation = '>';
		monnaie = 0;
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
	
	public void discuss(Terrain t) {
		Discussion.discussion(t);
	}
	
	public void addMonnaie(int valeur) {
		monnaie += valeur;
	}
	
	public void payer(int valeur) {
		monnaie -= valeur;
		if(monnaie < 0 ) {
			monnaie = 0;
		}
	}

	public int getMonnaie() {
		return monnaie;
	}
	
	public void setRapidUse(int rapidUse) {
		this.rapidUse = rapidUse;
	}

	public int getRapidUse() {
		return rapidUse;
	}
	
}
