package fr.uvsq.poo.monprojet.objets;

import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;

public class Teleporteur extends Objet{
	
	public Teleporteur() {
		super("Teleporteur", '~');
	}
	
	public static Teleporteur spawn(Terrain t) {
		Random r1 = new Random();
		Teleporteur o = new Teleporteur();
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		o.position.setPosition(l,h);
		t.t[l][h] = o.getRepresentation();
		t.objets.add(o);
		return o;
	}
	
	public void use(Terrain t) {
		super.use(t);
		boolean estEntier;
		String s = new String();
		@SuppressWarnings("resource")
		Scanner entry = new Scanner(System.in);
		int val[] = new int[2];
		for(int i = 0; i < 2; i++) {
			estEntier = true;
			if(i == 0)	System.out.print("deplacement en x : ");
			else 		System.out.print("deplacement en y : ");
			s = "";
			s += entry.nextLine();
			if(s.length() > 0) {
				if(s.charAt(0) == '-') {
					if(s.length() > 1)
						for(int j = 1; j < s.length(); j++) {
							if(s.charAt(j) < '0' || s.charAt(j) > '9') estEntier = false;
						}
					else estEntier = false;
				}
				else {
					for(int j = 0; j < s.length(); j++) {
						if(s.charAt(j) < '0' || s.charAt(j) > '9') estEntier = false;
					}
				}
			}
			else estEntier = false;
			val[i] = 0;
			if(estEntier) val[i] = Integer.valueOf(s).intValue();
		}
		
		val[0] += t.joueur.position.getX();
		val[1] += t.joueur.position.getY();
		s = "";
		if(t.correctPosition(val[0], val[1])) {
			if(t.t[val[0]][val[1]] == Terrain.SOL) {
				s += "\n*téléportation*";
				t.t[t.joueur.position.getX()][t.joueur.position.getY()] = Terrain.SOL;
				t.joueur.position.setPosition(val[0],val[1]);
				t.t[t.joueur.position.getX()][t.joueur.position.getY()] = t.joueur.getRepresentation();
			}
		}
		t.t[t.entree.position.getX()][t.entree.position.getY()] = Terrain.PORTE;
		t.t[t.sortie.position.getX()][t.sortie.position.getY()] = Terrain.PORTE;
		System.out.println(t + s);
		
		//entry.close();
	}
}
