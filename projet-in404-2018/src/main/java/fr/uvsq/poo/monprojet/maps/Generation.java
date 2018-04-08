package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;

import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.personnage.Pj;
import fr.uvsq.poo.monprojet.personnage.Pnj;

public class Generation {
	private static final int nombreCarte = 20;
	public ArrayList <Terrain> carte;
	public Pj joueur;
	private static final int nbGeneration = 2;
	
	public Generation() {
		carte = new ArrayList <Terrain> ();
		joueur = new Pj();
		Random r = new Random();
		for(int i = 0; i < nombreCarte; i++)
			switch(r.nextInt(nbGeneration)) {
				case 0 : carte.add(this.generation1());
				case 1 : carte.add(this.generationL());
			}
		
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
	
	
	//enregistre dans la liste chaque case qui est un sol la ou on pose un mur
	private void contourCase(Point2D p, Terrain t, ArrayList <Point2D> posMur) { 
		int x,y;
		
		x = p.getX() + 1;
		y = p.getY();
		if(t.correctPosition(x, y)) {
			if(t.t[x][y] == Terrain.SOL) posMur.add(new Point2D(x,y));
		}
		
		x = p.getX() - 1;
		y = p.getY();
		if(t.correctPosition(x, y)) {
			if(t.t[x][y] == Terrain.SOL) posMur.add(new Point2D(x,y));
		}
		
		x = p.getX();
		y = p.getY() + 1;
		if(t.correctPosition(x, y)) {
			if(t.t[x][y] == Terrain.SOL) posMur.add(new Point2D(x,y));
		}
		
		x = p.getX();
		y = p.getY() - 1;
		if(t.correctPosition(x, y)) {
			if(t.t[x][y] == Terrain.SOL) posMur.add(new Point2D(x,y));
		}
	}
	
	
	private void addMur(Terrain t,int surface) { // 20% de la carte qui est du sol devient du mur
		surface /= 5;
		Random r = new Random();
		ArrayList <Point2D> posMur = new ArrayList <Point2D> ();
		int i = 0,j = 0;
		int j2;
		Point2D p = new Point2D(0,0);
		while(i < surface) {
			do { // on pose un mur au hazard au sol
				p.setPosition(r.nextInt(t.getLargeur()), r.nextInt(t.getHauteur()));
			}while(t.t[p.getX()][p.getY()] != Terrain.SOL);
			
			t.t[p.getX()][p.getY()] = Terrain.MUR;
			i++;
			this.contourCase(p, t, posMur); // chaque cote de ce mur est enregistré
			j2 = r.nextInt(6);
			for(j = 0; j <= j2 && i < surface; j++) { 
				//parmis les positions de la liste une est choisit pour un mur et on actualise la liste
				p.setPosition(posMur.get(r.nextInt(posMur.size())));
				t.t[p.getX()][p.getY()] = Terrain.MUR;
				this.contourCase(p, t, posMur);
				i++;
			}
		}
	}
	
	public void addRandomPnj(Terrain t) {
		Random r = new Random();
		int alea = r.nextInt(3) + 1;
		for(int i = 0; i < alea; i++) {
			Pnj.spawn(t);
		}
	}
	
	private Terrain generation1() {
		Terrain t;
		Random r1 = new Random();
		int y = r1.nextInt(21) + 7; //min = 7 max = 27
		int x = r1.nextInt(30) + 15;
		t = new Terrain(x,y, joueur);
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(y - 2) + 1;
		int h2 = r1.nextInt(y - 2) + 1;
		t.t[0][h] = Terrain.PORTE;
		t.t[x-1][h2] = Terrain.PORTE;
		t.entree = new Porte(t,0,h);
		t.entree.t = null;
		t.sortie = new Porte(t,x-1,h2);
		t.sortie.t = null;
		
		//ajout aléatoire des pnj/objets
		addMur(t,(t.getHauteur() * t.getLargeur()));
		addRandomPnj(t);
		//##//
		return t;
	}
	
	private Terrain generationL() {
		Terrain t;
		Random r1 = new Random();
		int x = r1.nextInt(22) + 10;
		int y = r1.nextInt(22) + 10;
		t = new Terrain(x,y, joueur);
		int i,j;
		int xt = r1.nextInt(t.getLargeur() * 60 / 100) + (t.getLargeur() * 20 / 100);
		int yt = r1.nextInt(t.getHauteur() * 60 / 100) + (t.getHauteur() * 20 / 100);
		int surface = t.getHauteur() * t.getLargeur() - xt*yt;
		
		for(i = 0; i < xt; i++) {
			for(j = t.getHauteur() - yt - 1; j < t.getHauteur(); j++) {
				t.t[i][j] = Terrain.VIDE;
			}
		}
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(t.getLargeur() - xt) + xt;
		int h2 = r1.nextInt(t.getHauteur() - yt);
		t.t[0][h2] = Terrain.PORTE;
		t.t[h][t.getHauteur() - 1] = Terrain.PORTE;
		t.entree = new Porte(t,0,h2);
		t.entree.t = null;
		t.sortie = new Porte(t,h,t.getHauteur() - 1);
		t.sortie.t = null;
		
		//ajout aléatoire des pnj/objets
		addMur(t,surface);
		addRandomPnj(t);
		//##//
		return t;
	}
	
}
