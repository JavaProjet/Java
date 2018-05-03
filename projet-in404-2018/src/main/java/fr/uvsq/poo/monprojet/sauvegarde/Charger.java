package fr.uvsq.poo.monprojet.sauvegarde;

import fr.uvsq.poo.monprojet.maps.Generation;

public interface Charger {
	
	public static Generation nouvellePartie() {
		Generation g = new Generation();
		g.nouvelleCarte();
		return g;
	}
	
	public static Generation chargerPartie() {
		Generation g = new Generation();
		
		return g;
	}
}
