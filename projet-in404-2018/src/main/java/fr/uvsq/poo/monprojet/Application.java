package fr.uvsq.poo.monprojet;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.uvsq.poo.monprojet.intro.Cinematic;
import fr.uvsq.poo.monprojet.maps.Generation;

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
		while (go == false);
        
        
		Scanner entree = new Scanner(System.in);
        int s;
        System.out.println("passer l'introduction ?  (taper \"1\" pour passer)");
        s = entree.nextInt();
        if(s != 1)Cinematic.introduction();
        Generation g = new Generation();
        g.carte.get(0).play();
        entree.close();
       
        
        logger.trace("Fin du programme");
        System.exit(0);
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
