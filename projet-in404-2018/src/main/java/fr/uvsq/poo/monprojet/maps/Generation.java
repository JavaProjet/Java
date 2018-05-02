package fr.uvsq.poo.monprojet.maps;

import java.util.ArrayList;
import java.util.Random;

import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.objets.Arme;
import fr.uvsq.poo.monprojet.objets.Pioche;
import fr.uvsq.poo.monprojet.objets.Teleporteur;
import fr.uvsq.poo.monprojet.personnage.Marchand;
import fr.uvsq.poo.monprojet.personnage.Monstre;
import fr.uvsq.poo.monprojet.personnage.Pj;
import fr.uvsq.poo.monprojet.personnage.Pnj;

public class Generation {
	private static final int nombreCarte = 20;
	public ArrayList <Terrain> carte;
	public Pj joueur;
	private int i;
	
	public Generation() {
		carte = new ArrayList <Terrain> ();
		joueur = new Pj();
		Random r = new Random();
		carte.add(this.generationD());
		int last = 0;
		for(i = 0; i < nombreCarte - 1; i++) {
			if(last == 0 || last == 4 || last == 2) {
				last = r.nextInt(3);
				if		(last == 0)	carte.add(this.generation1H());
				else if	(last == 1)	carte.add(this.generationL());
				else 				carte.add(this.generation3());
			}
			else { 
				last = r.nextInt(2) + 3;
				if(last == 3)	carte.add(this.generation1V());
				else 			carte.add(this.generationL2());
			}	
		}
		if(last == 3 || last == 1) carte.add(this.generationL2());
		else {
			last = r.nextInt(2);
			if(last == 0)	carte.add(this.generation1H());
			else 			carte.add(this.generation3());
		}
		carte.add(this.generationF());
		this.connection();
		joueur.pointDeVie.setFraction(20, 20);
		Teleporteur.spawn(this.carte.get(8));
		carte.get(nombreCarte).monstres.add(Monstre.spawn(carte.get(nombreCarte), 10));
		carte.get(nombreCarte).monstres.get(carte.get(nombreCarte).monstres.size() - 1).setRepresentation('&');
	}
	
	private void connection() {
		int i;
		carte.get(0).entree.autorisation = false;
		carte.get(0).sortie.autorisation = false;
		carte.get(nombreCarte + 1).sortie.autorisation = false;
		carte.get(nombreCarte).sortie.autorisation = false;
		carte.get(nombreCarte + 1).numero = nombreCarte + 1;
		carte.get(0).numero = 0;
		for(i = 1; i < nombreCarte; i++) {
			carte.get(i).numero = i;
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
			this.contourCase(p, t, posMur); // chaque coté de ce mur est enregistré si c'est un sol
			j2 = r.nextInt(3);
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
		int alea = r.nextInt(4) + 1;
		for(int i = 0; i < alea; i++) {
			Pnj.spawn(t);
		}
	}
	
	public void addRandomMonstre(Terrain t, int niveau,int surface) {
		surface = 1 * surface / 100;
		for(int i = 0; i < surface; i++) {
			Monstre.spawn(t,niveau);
		}
	}
	
	private void addMarchand(Terrain t) {
		if(i%2 == 1)t.vendeur = Marchand.spawn(t);
	}
	
	private Terrain generation1V() {
		Terrain t;
		Random r1 = new Random();
		int x = r1.nextInt(10) + 10;
		int y = r1.nextInt(15) + 10;
		t = new Terrain(x,y, joueur);
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(x - 2) + 1;
		int h2 = r1.nextInt(x - 2) + 1;
		t.t[h][0] = Terrain.PORTE;
		t.t[h2][y - 1] = Terrain.PORTE;
		t.entree = new Porte(null,h,0);
		t.sortie = new Porte(null,h2,y - 1);
		
		//ajout aléatoire des pnj/objets
		addMur(t,(t.getHauteur() * t.getLargeur()));
		addRandomPnj(t); addMarchand(t);
		addRandomMonstre(t,i/4,(t.getHauteur() * t.getLargeur()));
		//##//
		return t;
	}
	
	private Terrain generation1H() {
		Terrain t;
		Random r = new Random();
		int x = r.nextInt(30) + 15;
		int y = r.nextInt(21) + 7; //min = 7 max = 27
		t = new Terrain(x,y, joueur);
		// ajout des portes d'entrées et sorties
		int h = r.nextInt(y - 2) + 1;
		int h2 = r.nextInt(y - 2) + 1;
		t.t[0][h] = Terrain.PORTE;
		t.t[x-1][h2] = Terrain.PORTE;
		t.entree = new Porte(null,0,h);
		t.sortie = new Porte(null,x-1,h2);
		
		//ajout aléatoire des pnj/objets
		addMur(t,(t.getHauteur() * t.getLargeur()));
		addRandomPnj(t); addMarchand(t);
		addRandomMonstre(t,i/4,(t.getHauteur() * t.getLargeur()));
		//##//
		return t;
	}
	
	private Terrain generationL() {
		Terrain t;
		Random r1 = new Random();
		int x = r1.nextInt(20) + 10;
		int y = r1.nextInt(20) + 10;
		t = new Terrain(x,y, joueur);
		int i,j;
		int xt = r1.nextInt(x * 60 / 100) + (x * 20 / 100);
		int yt = r1.nextInt(y * 60 / 100) + (y * 20 / 100);
		int surface = y * x - xt * yt;
		
		for(i = 0; i < xt; i++) {
			for(j = y - yt - 1; j < y; j++) {
				t.t[i][j] = Terrain.VIDE;
			}
		}
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(x - 1 - xt) + xt;
		int h2 = r1.nextInt(y - 1 - yt);
		t.t[0][h2] = Terrain.PORTE;
		t.t[h][y - 1] = Terrain.PORTE;
		t.entree = new Porte(null,0,h2);
		t.sortie = new Porte(null,h,y - 1);
		
		//ajout aléatoire des pnj/objets
		addMur(t,surface);
		addRandomPnj(t);
		addMarchand(t);
		addRandomMonstre(t,this.i/4,surface);
		//##//
		return t;
	}
	
	private Terrain generationL2() {
		Terrain t;
		Random r1 = new Random();
		int x = r1.nextInt(20) + 10;
		int y = r1.nextInt(20) + 10;
		t = new Terrain(x,y, joueur);
		int i,j;
		int xt = r1.nextInt(x * 60 / 100) + (x * 20 / 100);
		int yt = r1.nextInt(y * 60 / 100) + (y * 20 / 100);
		int surface = y * x - xt * yt;
		
		for(i = x - xt - 1; i < x; i++) {
			for(j = 0; j < yt; j++) {
				t.t[i][j] = Terrain.VIDE;
			}
		}
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(x - 1 - xt);
		int h2 = r1.nextInt(y - 1 - yt) + yt;
		t.t[x - 1][h2] = Terrain.PORTE;
		t.t[h][0] = Terrain.PORTE;
		t.entree = new Porte(null,h,0);
		t.sortie = new Porte(null,x - 1, h2);
		
		//ajout aléatoire des pnj/objets
		addMur(t,surface);
		addRandomPnj(t); if(i%2 == 0)addMarchand(t);
		addRandomMonstre(t,this.i/4,surface);
		//##//
		return t;
	}
	private Terrain generationD() {
		Terrain t;	
		int x = 19,y = 15;
		t = new Terrain(x,y, joueur);
		char [][] tab = 
			{	{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','.','.','.','.','.','.'},
				{'#','#','#','#','#','#','.','.','.','#','#','#','#','#','#'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'#','#','#','#','#','#','.','.','.','#','#','#','#','#','#'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','#','.','.','.','.','.'},
				{'#','#','#','#','#','#','.','.','.','#','#','#','#','#','#'},
				{'#','#','#','#','#','#','.','P','.','#','#','#','#','#','#'}};
		t.t = tab;
		t.entree = new Porte(null,x-1,7);
		t.sortie = new Porte(null,x-1,7);
		Pnj.spawn(t);
		t.t[t.personnage.get(0).position.getX()][t.personnage.get(0).position.getY()] = Terrain.SOL;
		t.personnage.get(0).position.setPosition(15, 14);
		t.t[t.personnage.get(0).position.getX()][t.personnage.get(0).position.getY()] = 'V';
		t.personnage.get(0).setRepresentation('V');
		Pioche.spawn(t,new Point2D(13,14));
        Arme.spawn(t, "Epée en bois", 1,new Point2D(7,14));
        joueur.position.setPosition(0, 14); 
        t.t[0][14] = joueur.getRepresentation();
		return t;
	}
	
	private Terrain generationF() {
		Terrain t;	
		int x = 19,y = 15;
		t = new Terrain(x,y, joueur);
		char [][] tab = {
				{'.','.','.','.','.','#','.','P','.','#','.','.','.','.','.'},
				{'.','.','.','.','.','#','.','.','.','#','.','.','.','.','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','#','#','#','.','.','.','.','.','.','.','#','#','#','.'},
				{'.','.','.','.','.','.','.','.','.','.','.','.','.','.','.'},
				{'.','.','.','.','#','#','.','.','.','#','#','.','.','.','.'},
				{'.','.','.','.','#','.','.','.','.','.','#','.','.','.','.'},
				{'.','.','.','.','#','.','#','.','#','.','#','.','.','.','.'}};
		t.t = tab;
		t.entree = new Porte(null,0,7);
		t.sortie = new Porte(null,0,7);
		
		return t;
	}
	
	
	private Terrain generation3() {
		Terrain t;
		Random r1 = new Random();
		int x = 40;
		int i,j,nombrevidedebut,rx,ry,Y;
		int y1 = r1.nextInt(8) + 7; //moitier haute de la carte
		int y2 = r1.nextInt(8) + 7; //moitier basse de la carte
		
		
		Y = y1 + y2 + 3;
		t = new Terrain(x,Y, joueur);//init avec le sol 
		int surface = t.getHauteur() * t.getLargeur();
		nombrevidedebut = r1.nextInt(4)+2;
		i = 0;
		while (i<nombrevidedebut) {//initialise les vides du terrain haut
			rx = r1.nextInt(40);
			ry = r1.nextInt(y1);
			if (rx < 10 || rx > 30) {
				t.t[rx][ry + y2 + 3] = Terrain.VIDE ;
				i++;
				surface--;
			}
			else if (ry >= (y1 / 2)) {
				t.t[rx][ry + y2 + 3] = Terrain.VIDE ;
				i++;
				surface--;
			}
			
		}
		i = 0;
		while (i<nombrevidedebut) {//initialise les vides du terrain bas
			rx = r1.nextInt(40);
			ry = r1.nextInt(y2);
			if (rx < 10|| rx > 30) {
				t.t[rx][ry ] = Terrain.VIDE ;
				i++;surface--;
			}
			else if (ry <= (y2 / 2)) {
				t.t[rx][ry] = Terrain.VIDE ;
				i++;surface--;
			}
			
		}
		
		//t.t[i][j] = Terrain.VIDE;
		for (i = 0; i < y1; i++) {
			for(j = 0; j < x; j++) {
				
					if (t.t[j][i - 1 + y2 + 3] == Terrain.VIDE  ) {//verifie la case du bas
						t.t[j][i + y2 + 3] = Terrain.VIDE;
						surface--;
					}	
					if(t.correctPosition(j-1, i - 1 + y2 + 3)) {//celle du bas a gauche
						if (t.t[j-1][i - 1 + y2 + 3] == Terrain.VIDE  ) {
							t.t[j][i + y2 + 3] = Terrain.VIDE;
							
						}
					}
					if(t.correctPosition(j+1, i - 1 + y2 + 3)) {//celle de bas a droite
						if (t.t[j+1][i - 1 + y2 + 3] == Terrain.VIDE  ) {
							t.t[j][i + y2 + 3] = Terrain.VIDE;
													}
					}
				
			}
		}
		int k;
		for (k=0;k<y2;k++) {	
			for (i = 0; i < y2; i++) {//boucle du bas 
				for(j = 0; j < x;j ++) {
					
					if (t.t[j][i+1] == Terrain.VIDE  ) {
						t.t[j][i] = Terrain.VIDE;
						surface--;
					}
					if(t.correctPosition(j-1, i + 1 )) {
						if (t.t[j-1][i+1] == Terrain.VIDE  ) {
							t.t[j][i] = Terrain.VIDE;
							
						}
					}
					if(t.correctPosition(j+1, i + 1)) {
						if (t.t[j+1][i+1] == Terrain.VIDE  ) {
							t.t[j][i] = Terrain.VIDE;
							
						}
					}
				}
			}
		}
		// ajout des portes d'entrées et sorties
		int h = r1.nextInt(3) + y2;
		int h2 = r1.nextInt(3) + y2;
		t.t[0][h] = Terrain.PORTE;
		t.t[x-1][h2] = Terrain.PORTE;
		t.entree = new Porte(null,0,h);
		t.sortie = new Porte(null,x-1,h2);
		
		//ajout aléatoire des pnj/objets
		addMur(t,(surface));
		addRandomPnj(t);
		addMarchand(t);
		addRandomMonstre(t,this.i/4,(t.getHauteur() * t.getLargeur()));
		//##//
		return t;
	}
	
	
}
