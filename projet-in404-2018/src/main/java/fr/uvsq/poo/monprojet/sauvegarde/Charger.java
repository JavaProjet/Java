package fr.uvsq.poo.monprojet.sauvegarde;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Generation;
import fr.uvsq.poo.monprojet.maps.Porte;
import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.maths.fraction.Fraction;
import fr.uvsq.poo.monprojet.maths.point.Point2D;
import fr.uvsq.poo.monprojet.objets.Argent;
import fr.uvsq.poo.monprojet.objets.Arme;
import fr.uvsq.poo.monprojet.objets.Bouclier;
import fr.uvsq.poo.monprojet.objets.Flash;
import fr.uvsq.poo.monprojet.objets.Key;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.objets.Pile;
import fr.uvsq.poo.monprojet.objets.Pioche;
import fr.uvsq.poo.monprojet.objets.Potion;
import fr.uvsq.poo.monprojet.objets.Teleporteur;
import fr.uvsq.poo.monprojet.personnage.Marchand;
import fr.uvsq.poo.monprojet.personnage.Monstre;
import fr.uvsq.poo.monprojet.personnage.Pj;
import fr.uvsq.poo.monprojet.personnage.Pnj;

public interface Charger {
	static Generation g = new Generation();
	
	public static int nouvellePartie() {
		g.nouvelleCarte();
		return g.carte.get(0).play();
	}
	
	public static String nomSauvegarde() {
		File f,sf[];
		int i;
		f = new File("Saves");
		if(f.exists()) {
			sf = f.listFiles();
			if(sf.length == 0) {
				System.out.println("aucune sauvegarde n'existe");
				return null;
			}
			else {
				System.out.println("listes des sauvegardes : ");
				boolean ecrire;
				for(File name : sf) {
					ecrire = false;//pour afficher uniquement le nom de la sauvegarde et pas le dossier la contenant
					for(i = 0; i < name.toString().length(); i++) {
						if(ecrire == true) System.out.print(name.toString().charAt(i));
						if(name.toString().charAt(i) == '/') ecrire = true;
					}
					System.out.println("");
				}
			}
		}
		else {
			System.out.println("aucune sauvegarde n'existe");
			return null;
		}
		System.out.print("entrez le nom de la sauvegarde que vous souhaitez charger (\"quitter\" pour annuler) : \n => ");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String name = s.nextLine();
		f = new File("Saves/" + name); 
		while(f.exists() == false && name.equals("quitter") == false) {
			System.out.print("cette sauvegarde n'existe pas, veuillez en saisir une autre : \n => ");
			name = s.nextLine();
			f = new File("Saves/" + name);
		}
		if(name.equals("quitter")) return null;
		return name;
	}
	
	static void lectureSave(String name) {
		int i = 0; FileReader fr = null;
		try {
			fr = new FileReader("Saves/" + name + "/joueur.txt");
			lireJoueur(fr);
			fr.close();
			new File("Saves/" + name + "/joueur.txt").delete();
		} catch (IOException e) {}
		while(new File("Saves/" + name + "/Terrain" + i + ".txt").exists()) {
			try {
				fr = new FileReader("Saves/" + name + "/Terrain" + i + ".txt");
				g.carte.add(lireTerrain(fr));
				g.carte.get(i).setNumero(i);
				fr.close();
				new File("Saves/" + name + "/Terrain" + i + ".txt").delete();
				i++;
			} catch (IOException e) {}	
		}
		g.connection();
		(new File("Saves/" + name)).delete();
	}
	
	public static int chargerPartie() {
		String s = nomSauvegarde();
		if(s == null)return -1;
		else {
			lectureSave(s);
			return g.carte.get(g.joueur.getNumero()).play();
		}
	}
	
	static String lireString(FileReader fr) throws IOException {
		String s = "";
		char c = (char)fr.read();
		while(c != '\n' && c != ';') { //separateur dans le fichier de sauvegarde
			s += c;
			c = (char)fr.read();
		}
		return s;
	}
	static int lireEntier(FileReader fr) throws IOException {
		String s = lireString(fr);
		return Integer.parseInt(s);
	}
	static boolean lireBoolean(FileReader fr) throws IOException {
		String s = lireString(fr);
		if(s.equals("true")) return true;
		else if(s.equals("false")) return false;
		else {
			System.exit(123);
			return false;
		}
	}
	static Point2D lirePoint2D(FileReader fr) throws IOException {
		Point2D p = Point2D.parsePoint2D(lireString(fr));
		return p;
	}
	static Fraction lireFraction(FileReader fr) throws IOException {
		Fraction p = Fraction.parseFraction(lireString(fr));
		return p;
	}
	static Terrain lireTerrain(FileReader fr) throws IOException {
		int largeur = lireEntier(fr); int hauteur = lireEntier(fr);
		Terrain t = new Terrain(largeur,hauteur,g.joueur);
		int i,j; char c;
		for(j = hauteur - 1; j >= 0; j--) {
			for(i = 0; i < largeur; i++) {
				c = (char)fr.read();
				t.t[i][j] = c;
				c = (char)fr.read(); //pour lire l'espace ou le \n entre chaque caractere du terrain dans le fichier
			}
		}
		t.entree = new Porte(0,0); t.sortie = new Porte(0,0);
		t.entree.setAutorisation(lireBoolean(fr)); 
		t.entree.position = lirePoint2D(fr); 
		t.sortie.setAutorisation(lireBoolean(fr)); 
		t.sortie.position = lirePoint2D(fr);
		t.setSombre(lireBoolean(fr));
		
		fr.read(new char["\n#Monstres ".length()], 0, "\n#Monstres ".length()); j = lireEntier(fr);	
		for(i = 0; i < j; i++) t.monstres.add(lireMonstre(fr));
		
		fr.read(new char["\n\n#Pnj ".length()], 0, "\n\n#Pnj ".length()); j = lireEntier(fr);
		for(i = 0; i < j; i++) t.personnage.add(lirePnj(fr));
		
		fr.read(new char["\n\n#Objets ".length()], 0, "\n\n#Objets ".length()); j = lireEntier(fr);
		for(i = 0; i < j; i++) t.objets.add(lireObjet(fr));
		
		fr.read(new char["\n\n#Marchand ".length()], 0, "\n\n#Marchand ".length()); j = lireEntier(fr);
		if(j == 1) {
			t.vendeur = new Marchand();
			t.vendeur.addObjetVente();
			t.vendeur.position.setPosition(lirePoint2D(fr));
		}
		return t;
	}
	
	static Monstre lireMonstre(FileReader fr) throws IOException {
		char vision = (char)fr.read(); fr.read();
		Point2D position = lirePoint2D(fr); Fraction pointDeVie = lireFraction(fr);
		char representation = (char)fr.read(); fr.read();
		int niveau = lireEntier(fr);
		Monstre m;
		if(representation == '&') {
			m = new Monstre(true,g.carte.get(g.carte.size() - 1),niveau);
			m.setRepresentation('&');
		}
		else if (representation == '@') {
			m = new Monstre(true,g.carte.get(g.carte.size() - 1),niveau);
		}
		else {
			m = new Monstre(false,g.carte.get(g.carte.size() - 1),niveau);
		}
		m.pointDeVie = pointDeVie;
		m.position = position;
		m.setVision(vision);
		m.setDamages(lireEntier(fr));
		m.setDistance(lireEntier(fr));
		return m;
	}

	static Pnj lirePnj(FileReader fr) throws IOException {
		char vision = (char)fr.read(); fr.read();
		Point2D position = lirePoint2D(fr); Fraction pointDeVie = lireFraction(fr);
		Pnj n = new Pnj();
		n.position.setPosition(position);
		n.setVision(vision);
		n.pointDeVie.setFraction(pointDeVie);
		return n;
	}
	
	static Objet lireObjet(FileReader fr) throws IOException {
		Point2D position = lirePoint2D(fr);
		String nomObjet = lireString(fr);
		char representation = (char)fr.read(); fr.read();
		if(representation == '*') {
			Argent o = new Argent(lireEntier(fr));
			o.position.setPosition(position); o.setNomObjet(nomObjet);
			return o;
		}
		else if(representation == '!') {
			Arme o = new Arme(nomObjet, 1);
			o.position.setPosition(position);
			o.setDamage(lireEntier(fr)); o.setDurability(lireEntier(fr)); o.setNomObjet(lireString(fr));
			return o;
		}
		else if(representation == 'D') {
			Bouclier o = new Bouclier(nomObjet, 1);
			o.position.setPosition(position);
			o.setAbsorption(lireEntier(fr)); o.setDurability(lireEntier(fr)); o.setNomObjet(lireString(fr));
			return o;
		}
		else if(representation == '0') {
			Flash o = new Flash();
			o.position.setPosition(position);
			return o;
		}
		else if(representation == 'y') {
			Key o = new Key();
			o.position.setPosition(position);
			return o;
		}
		else if(representation == '-') {
			Pile o = new Pile();
			o.position.setPosition(position);
			return o;
		}
		else if(representation == 'T') {
			Pioche o = new Pioche();
			o.position.setPosition(position); o.setNomObjet(nomObjet);
			o.setDurability(lireEntier(fr));
			return o;
		}
		else if(representation == '6') {
			Potion o = new Potion(lireEntier(fr));
			o.position.setPosition(position);
			return o;
		}
		else if(representation == '%') {
			Teleporteur o = new Teleporteur();
			o.position.setPosition(position); o.setDurability(lireEntier(fr));
			return o;
		}
		else {
			return null;
		}
	}
	
	static void lireJoueur(FileReader fr) throws IOException {
		int j;
		g.joueur = new Pj();
		char vision = (char) fr.read(); fr.read();
		g.joueur.position = lirePoint2D(fr);
		g.joueur.setVision(vision); 
		g.joueur.pointDeVie = lireFraction(fr);
		g.joueur.addMonnaie(lireEntier(fr));
		g.joueur.setRapidUse(lireEntier(fr));
		g.joueur.setLevel(lireEntier(fr));
		g.joueur.experience = lireFraction(fr);
		g.joueur.setNumero(lireEntier(fr));
		fr.read(new char["\n#Objets ".length()], 0, "\n#Objets ".length()); j = lireEntier(fr);
		for(int i = 0; i < j; i++) {
			g.joueur.addObjet(lireObjet(fr));
		}
		fr.read(new char["\n\n#Bouclier ".length()], 0, "\n\n#Bouclier ".length()); j = lireEntier(fr);
		if(j != -1) g.joueur.protection = (Bouclier) g.joueur.inventory.get(j);
	}
}
