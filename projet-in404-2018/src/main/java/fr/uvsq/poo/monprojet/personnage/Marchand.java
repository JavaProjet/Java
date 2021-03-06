package fr.uvsq.poo.monprojet.personnage;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Arme;
import fr.uvsq.poo.monprojet.objets.Bouclier;
import fr.uvsq.poo.monprojet.objets.Flash;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.objets.Pile;
import fr.uvsq.poo.monprojet.objets.Pioche;
import fr.uvsq.poo.monprojet.objets.Potion;

public class Marchand extends Personnage{
	private ArrayList <Objet> vente;
	private ArrayList <Integer> prix;
	
	public Marchand() {
		super();
		vente = new ArrayList <Objet> ();
		prix = new ArrayList <Integer> ();
		representation = 'M';
	}
	
	public void addObjetVente() {
		addVente(new Pile(),25);
		addVente(new Flash(),15);
		addVente(new Potion(10),15);
		addVente(new Pioche(),60);
		Random r = new Random();
		int valeurAleatoire = 0;
		valeurAleatoire = r.nextInt(2) + 1;
		addVente(new Arme("épée en bois", valeurAleatoire), 20);
		valeurAleatoire = r.nextInt(2) + 3;
		addVente(new Arme("épée en Fer", valeurAleatoire), 50);
		valeurAleatoire = r.nextInt(2) + 5;
		addVente(new Arme("épée en Or", valeurAleatoire), 80);
		valeurAleatoire = r.nextInt(2) + 7;
		addVente(new Arme("épée en Rubis", valeurAleatoire), 120);
		valeurAleatoire = r.nextInt(2) + 10;
		addVente(new Arme("épée en Diamand", valeurAleatoire), 200);
		addVente(new Bouclier("bouclier en bois",2),40);
		addVente(new Bouclier("bouclier en fer",5),80);
		addVente(new Bouclier("bouclier en or",10),200);
		addVente(new Bouclier("bouclier en diamand",20),350);
	}
	
	private void addVente(Objet o, int prix) {
		vente.add(o);
		this.prix.add(prix);
	}
	
	public String affiche_vente() {
		String s = "Bonjour !\nVoici ce que j'ai à vendre, que souhaite - tu m'acheter ?\n";
		for(int i = 0; i < vente.size(); i++) {
			s += (i + 1) + ". " + vente.get(i).getNomObjet() + "   prix : " + prix.get(i) + "\n"; 
		}
		return s;
	}
	
	public void transaction(Terrain t) {
		@SuppressWarnings("resource")
		Scanner entree = new Scanner(System.in);
		int val = 0;
		try {
			val = entree.nextInt(); val--;
			if(val > -1 && val < vente.size()) {
				if(t.joueur.getMonnaie() >= prix.get(val)) {
					t.joueur.inventory.add(vente.get(val).clone());
					t.joueur.payer(prix.get(val));
					System.out.println(t + "Voici ton objet : " + vente.get(val).getNomObjet());
					System.out.println("\nRubis restant : " + t.joueur.getMonnaie());
				}
				else System.out.println("vous n'avez pas assez d'argent, revenez plus tard avec au moins " + prix.get(val) + " rubis.");
			}
			else System.out.println("valeur incorrect");
			entree.nextLine();
		} 
		catch (InputMismatchException e) {
			System.out.println(t + "Au revoir !");
		}
	}

	public static void spawn(Terrain t) {
		Marchand p = new Marchand();
		Random r1 = new Random();
		int l,h;
		do{
			l = r1.nextInt(t.getLargeur());
			h = r1.nextInt(t.getHauteur());
		}while(t.t[l][h] != Terrain.SOL);
		p.position.setPosition(l,h);
		p.setDevant();
		t.t[l][h] = p.getRepresentation();
		p.addObjetVente();
		t.vendeur = p;
	}
}
