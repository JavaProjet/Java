package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;

public class Pj extends Personnage{
	public ArrayList <Objet> inventory;
	private int monnaie;
	
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
	
	public void discuss(Terrain T){
		Scanner discute = new Scanner(System.in);
		int x = T.joueur.position.getX();
		int y = T.joueur.position.getY();
		int test=0;
		for(int j=0; j<T.personnage.size(); j++){
			int px = T.personnage.get(j).position.getX();
			int py = T.personnage.get(j).position.getY();
			if(py == y+1 || py == y-1|| px == x+1 || px == x-1){
				Random r = new Random();
				int rand = r.nextInt(2);
				if(rand == 0){
					System.out.println("Bonjour aventurier !");
					System.out.println("1- Bonjour (Partir)");
					System.out.println("2- Bonjour, avez-vous entendu parler d'un mysterieux objet supposé être dans ces grottes?");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("On raconte que les gens de l'ombre garderaient un mysterieux teleporteur, personnellement je n'ai jamais rien vu de tel");
					}
				}
				if(rand == 1){
					System.out.println("Comment vas-tu aventurier?");
					System.out.println("1- Ignorer ce personnage");
					System.out.println("2- Je me mefie des creatures, elles sont nombreuses par ici..");
					int reponse = discute.nextInt();
					if(reponse == 2){
						System.out.println("Tu as raison, reste sur tes gardes. Certains peuvent même casser la roche pour t'attaquer.");
						System.out.println("Si tu as besoin de te soigner, tu pourras trouver des fioles medicinales par ici");
					}
				}
				test =1;
			}
		}
		if(test == 0) System.out.println("Vous ne pouvez discuter qu'a cote d'un personnage !");
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
	
}
