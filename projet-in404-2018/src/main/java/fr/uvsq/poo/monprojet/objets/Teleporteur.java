package fr.uvsq.poo.monprojet.objets;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;

public class Teleporteur extends Objet{
	
	private int durability;
	
	public Teleporteur() {
		super("Teleporteur", '%');
		setDurability(10);
	}
	
	public static void spawn(Terrain t) {
		Random r1 = new Random();
		Teleporteur o = new Teleporteur();
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		o.position.setPosition(l,h);
		t.addObjet(o);
	}
	
	public void use(Terrain t) {
		super.use(t);
		String s = new String();
		if(getDurability() == 0) {
			System.out.println(t + "utilisez une pile pour le recharger");
		}
		else {
			@SuppressWarnings("resource")
			Scanner entry = new Scanner(System.in);
			int val[] = new int[2];
			for(int i = 0; i < 2; i++) {
				if(i == 0)	System.out.print("deplacement en x : ");
				else 		System.out.print("deplacement en y : ");
				
				try {
					val[i] = entry.nextInt();
				}
				catch(InputMismatchException e) {
					System.out.println("invalid entry, number set to 0");
					val[i] = 0;
				}
			}
			val[0] += t.joueur.position.getX();
			val[1] += t.joueur.position.getY();
			s = "";
			if(t.correctPosition(val[0], val[1])) {
				if(t.t[val[0]][val[1]] == Terrain.SOL) {
					s += "\n*téléportation*";
					setDurability(getDurability() - 1);
					t.t[t.joueur.position.getX()][t.joueur.position.getY()] = Terrain.SOL;
					t.joueur.position.setPosition(val[0],val[1]);
					t.t[t.joueur.position.getX()][t.joueur.position.getY()] = t.joueur.getRepresentation();
				}
			}
			t.t[t.entree.position.getX()][t.entree.position.getY()] = Terrain.PORTE;
			t.t[t.sortie.position.getX()][t.sortie.position.getY()] = Terrain.PORTE;
			System.out.println(t + s);
		}
	}
	
	public void recharge() {
		setDurability(getDurability() + 10);
		if(getDurability() > 10) setDurability(10);
	}
	
	public Teleporteur clone() {
		Teleporteur t = new Teleporteur();
		t.setDurability(durability);
		return t;
	}
	
	public String toString() {
		String s = super.toString();
		s += ";" + getDurability();
		return s;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}
}
