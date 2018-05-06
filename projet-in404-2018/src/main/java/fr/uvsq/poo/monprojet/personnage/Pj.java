package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.fraction.Fraction;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Pj extends Personnage {
	public ArrayList <Objet> inventory;
	private int monnaie = 0;
	private int rapidUse = 0;
	public Fraction experience;
	private int level = 1;
	private int numero;
	
	public Pj() {
		super();
		inventory = new ArrayList <Objet> ();
		representation = '>';	
		experience = new Fraction(0,100);
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
			System.out.println(i + "." + inventory.get(i - 1).getNomObjet());
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
	
	public void addXP(int valeur) {
		experience.setNumerateur(experience.getNumerateur() + valeur);
		int ret = experience.getDenominateur() - experience.getNumerateur();
		if(ret <= 0) {
			levelUp(-ret);
		}
	}
	
	private void levelUp(int enTrop) {
		level = level + 1;
		experience.setNumerateur(enTrop);
		experience.setDenominateur(level * 50);
		pointDeVie.setDenominateur(pointDeVie.getDenominateur() + 10);
		pointDeVie.setNumerateur(pointDeVie.getDenominateur());
		int ret = experience.getDenominateur() - experience.getNumerateur();
		if(ret <= 0) {
			levelUp(-ret);
		}
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level - 1;
		levelUp(0);
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
}
