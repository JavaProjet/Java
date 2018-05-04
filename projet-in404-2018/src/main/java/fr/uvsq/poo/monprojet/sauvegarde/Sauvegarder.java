package fr.uvsq.poo.monprojet.sauvegarde;

import java.io.*;
import java.util.Scanner;

import fr.uvsq.poo.monprojet.maps.Generation;
import fr.uvsq.poo.monprojet.maps.Terrain;
import fr.uvsq.poo.monprojet.objets.Objet;
import fr.uvsq.poo.monprojet.personnage.Monstre;
import fr.uvsq.poo.monprojet.personnage.Pj;
import fr.uvsq.poo.monprojet.personnage.Pnj;

public interface Sauvegarder {
	
	static boolean openFile(String nomFichier) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(nomFichier)));
			bw.close();
			return true;
		}
		catch (IOException e){
			System.out.println("l'ouverture du fichier a échoué");
			return false;
		}
	}
	
	public static void sauvegarderPartie(Generation g) {
		File f = new File("Saves");
		int i = 0;
		if(f.exists() == false) {
			f.mkdirs();
		}
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String name = new String();
		FileWriter fw;
		System.out.print("entrez un nom pour votre sauvegarde : \n => ");
		name = s.nextLine();
		f = new File("Saves/" + name); 
		while(f.exists() == true) {
			System.out.print("une sauvegarde existe déjà à ce nom, choisissez en un autre : \n => ");
			name = s.nextLine();
			f = new File("Saves/" + name);
		}
		f.mkdirs();
		for(i = 0; i < g.carte.size(); i++) {
			openFile("Saves/" + name + "/Terrain" + i + ".txt"); // pour créer les fichiers
			try {
				fw = new FileWriter("Saves/" + name + "/Terrain" + i + ".txt");
				ecrireTerrain(fw,g.carte.get(i));
				fw.write("\n###fin d'ecriture###\n");
				fw.close();
			} catch (IOException e) {}	
		}
		openFile("Saves/" + name + "/joueur.txt"); // pour créer les fichiers
		try {
			fw = new FileWriter("Saves/" + name + "/joueur.txt");
			ecrireJoueur(fw,g.joueur);
			fw.close();
		} catch (IOException e) {}
		
	}
	static FileWriter ecrireTerrain(FileWriter fw, Terrain t) throws IOException {
		fw.write(t.getLargeur() + " " + t.getHauteur() + "\n" + t.toString1());
		fw.write(t.entree.autorisation + " " + t.sortie.autorisation + " " + t.isSombre() + " " + "\n\n#monstres " + t.monstres.size() + "\n");
		int i;
		for(i = 0; i < t.monstres.size();i++) {
			ecrireMonstre(fw,t.monstres.get(i));
		}
		fw.write("\n\n#Pnj " + t.personnage.size() +"\n");
		for(i = 0; i < t.personnage.size(); i++) {
			ecrirePnj(fw,t.personnage.get(i));
		}
		fw.write("\n\n#Objets " + t.objets.size() +"\n");
		for(i = 0; i < t.objets.size(); i++) {
			ecrireObjet(fw,t.objets.get(i));
		}
		fw.write("\n\n#Marchand ");
		try {
			fw.write("1\n" + t.vendeur.position.toString() + "\n");
		}
		catch(NullPointerException e) {
			fw.write("0\n");
		}
		return fw;
	}
	
	static void ecrireMonstre(FileWriter fw,Monstre m) throws IOException {
		fw.write(m.getVision() + ";" + m.position + ";" + m.pointDeVie + ";" + m.getRepresentation()); //données d'un personnage
		fw.write(";" + m.getNiveau());
		fw.write("\n");
	}
	
	static void ecrirePnj(FileWriter fw,Pnj p) throws IOException {
		fw.write(p.getVision() + ";" + p.position + ";" + p.pointDeVie); //données d'un personnage
		fw.write("\n");
	}
	
	static void ecrireObjet(FileWriter fw, Objet o) throws IOException {
		fw.write(o.toString());
		fw.write("\n");
	}
	
	static void ecrireJoueur(FileWriter fw, Pj joueur) throws IOException {
		fw.write(joueur.getVision() + ";" + joueur.position + ";" + joueur.pointDeVie + ";" + joueur.getRepresentation() + ";");
		fw.write(joueur.getMonnaie() + ";" + joueur.getRapidUse() + ";" + joueur.experience + ";" + joueur.getLevel() + ";" + joueur.getNumero());
		fw.write("\n\n#Objet " + joueur.inventory.size() + "\n");
		for(int i = 0; i < joueur.inventory.size(); i++) {
			ecrireObjet(fw,joueur.inventory.get(i));
		}
		fw.write("\n");
	}
}
