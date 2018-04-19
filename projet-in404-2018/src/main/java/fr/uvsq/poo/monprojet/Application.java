package fr.uvsq.poo.monprojet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.uvsq.poo.monprojet.intro.Cinematic;
import fr.uvsq.poo.monprojet.maps.Generation;
import fr.uvsq.poo.monprojet.objets.Arme;
import fr.uvsq.poo.monprojet.objets.Pioche;
import fr.uvsq.poo.monprojet.objets.Potion;
import fr.uvsq.poo.monprojet.objets.Teleporteur;

/**
 * Cette classe est le programme principal du projet.
 *
 * Elle est une implémentation du <em>design pattern</em>
 * <a href="https://fr.wikipedia.org/wiki/Singleton_(patron_de_conception)">Singleton</a>.
 *
 * @author Stéphane Lopes
 * @version fév. 2018
 */
public enum Application {
    APPLICATION;
    private static final Logger logger = LogManager.getLogger(Application.class);

    public void run(String[] args) {
        logger.trace("Début du programme");
        Fenetre F = new Fenetre();
        boolean go = false;

	do{
		go = F.getGo();
	}
	while (go==false);
        
        Generation g = new Generation();
        
        //ajout d'une potion aléatoire sur le terrain 0 pour la tester
        
        Potion.spawn(g.carte.get(1), 12);
        Teleporteur.spawn(g.carte.get(1));
        Pioche.spawn(g.carte.get(1));
        Arme.spawn(g.carte.get(1), "Excalibur", '!', 10);
        //
        
        //Cinematic.introduction();
        g.carte.get(0).play();
        
       
        
        logger.trace("Fin du programme");
    }

    /**
     * La méthode de classe <em>main</em> se contente de déléguer le lancement du programme à la méthode <em>run</em>.
     *
     * @param args les paramètres de la ligne de commande du shell
     */
    public static void main(String[] args) {
        APPLICATION.run(args);
    }
}
