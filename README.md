 -- Projet Rogue-like IN404 2018 --

Participants: DE-JESUS Mathieu, LEDEME Florian, LAPEGUE Thibaut

***PRESENTATION DU JEU***
Le personnage part � la recherche d'un tr�sor. Il d�couvre une grotte et d�cide alors de s'y aventurer mais celui-ci heurte une pierre. Il se r�veille dans un salle souterraine.
Il devra alors traverser plusieurs salles parsemm�es des monstres qui seront � sa poursuite. Il devra alors s'�quiper et essayer de chercher diff�rents indices aupr�s de quelques villageois.
S'il arrive � atteindre la salle finale, il devra se mesurer � un ennemi de taille...

***DESCRIPTIF DU JEU PAR RAPPORT AU SUJET***
- L'univers du jeu est repr�sent� par des caract�res ASCII sur le terminal, il y a plusieurs salles � franchir.
- Les salles sont g�n�r�es al�atoirement (sauf la premi�re et la finale).
- Notre personnage �volue au fil des salles. Les salles contiennent des PNJ de plusieurs types: les villageois, inoffensifs avec qui
le personnage peut discuter et parfois acheter des objets, et des monstres qui poursuivent le personnage.
- Le personnage poss�de des points de vie, ainsi que des niveaux. Il a un inventaire qui lui permet de stocker des objets (�p�es, pioche etc..) mais
aussi une bourse. Cette bourse contient des rubis (monnaie du jeu).
- Les PNJ poss�dent aussi des points de vie et niveaux.
- La sauvegarde de la partie est possible.

***EXECUTION/COMPILATION DU JEU***
- L'ex�cution du jeu se fait comme indiqu�e sur le sujet de projet.
-> Compilation: ./gradlew build (linux) ou gradlew.bat build (windows).
-> Ex�cution: Dans projet-in404-2018/build/distributions, on d�compresse l'archive. On utilise les commandes suivantes pour lancer le jeu:
   projet-in404-2018/bin/projet-in404-2018 (linux)
   projet-in404-2018/bin/projet-in404-2018.bat (windows)
   
***MANUEL UTILISATEUR***
Le joueur est dans un grotte compos�e de plusieurs salles, ces salles contiennent des PNJ et divers objets pouvant �tre rammass�s.
Le joueur peut effectuer diff�rentes actions, comme se d�placer, ramasser un objet, discuter, consulter son inventaire etc. Un descriptif des commandes
essentielles pour jouer est disponible en jeu � tout moment. Il suffit de taper la commande "help" pour obtenir toutes les informations n�cessaires.

Repr�sentation des �l�ments:
- Le joueur est repr�sent� par une fl�che qui est dans la direction de la vision du personnage (<,v,>,^).
- Les PNJ villageois sont repr�sent�s par un 'N'.
- Les PNJ marchands par un 'M'
- Les PNJ monstres sont repr�sent�s par un 'G' ou par un '@' s'ils peuvent casser des murs.
- Les rubis (argent du jeu) sont r�pr�sent�s par une �toile '*'.
- Les armes sont r�pr�sent�es par un '!'.
- Objet flash (�claircir la pi�ce) par un 'O'.
- Les cl�s par un 'y'.
- Les piles par un '-'.
- Les pioches par un 'T'.
- Les potions par un '6'.
- Le t�l�porteur (objet mystere) par un '%'.
- Un mur est repr�sent� par '#'.
- Une porte par 'P'.
- Le sol par '.'.

Le joueur peut casser des murs avec une pioche, tuer des monstres avec une arme, acheter des objets chez un marchand, discuter avec les villageois ou encore utiliser
des objets (potions, t�l�porteur etc...). Chaque objet a ses caract�ristiques: exemple: une �p�e peut avoir des d�gats plus �l�v�s qu'une autre.
Le joueur peut utiliser une potion pour se soigner.

***MANUEL TECHNIQUE***
Contenu du projet:

Le projet est constitu� de diff�rentes classes et packages:
- Classe "Application", classe principale du projet qui permet de d�marrer une partie ou charger une sauvegarde.
- Package "objets", permettant d'impl�menter les objets du jeu, comprenant une super classe abstraite "Objet" et des sous-classes impl�mentant diff�rents types d'objets ("Arme", "Pile", "Key").
- Package "maps", permettant l'impl�mentation du terrain du jeu, comprenant la classe Terrain qui connait la liste des PNJ, PJ, objets pr�sents, affiche le terrain, g�re les actions des joueurs (d�placements, ramasser objet..) et
les commandes entr�es dans le terminal. Le package contient la classe "Porte" qui permet de passer d'une salle � une autre et une classe "Generation" qui permet de g�n�rer le terrain al�atoirement (placement des murs etc).
- Package "maths" comprenant une classe "Point2D" permettant de g�rer la position des personnages, et une classe "Fraction" permettant de g�rer les points de vie des personnages.
- Package "sauvegarde" comprenant une classe "Charger" pour le chargement d'une sauvegarde et "Sauvegarder" pour sauvegarder une partie.
- Package "personnage" comprenant une super classe abstraite "Personnage" permettant de cr�er un personnage et des sous-classes "Marchand", "Monstre", "Pj", "Pnj" qui permettent d'impl�menter un type de personnage.
Ce package contient une interface "Discussion" qui g�re la discussion entre Pj et villageois.
- Package "intro" qui contient la classe "Cinematic". Cette classe g�re la cin�matique de d�but de partie et l'affichage de fin de partie (si le pj meurt).


Descriptif:

- Les personnages

//Personnage.java
Ils sont repr�sent�s par diff�rents caract�res (voir ci-dessus).
Les personnages ont une vision (Nord, Ouest, Est, Sud), une position (x,y) sur le terrain, des points de vie (on utilise une fraction (num�rateur: points de vie actuels, d�nominateur: points de vie max)). Ils connaissent la position devant eux (permettra de savoir s'ils doivent avancer ou non (ex: mur devant)).
Diff�rents m�thodes nous permettent de g�rer les personnages. Par exemple pour avancer, on regarde la vision du personnage et sa position et on le d�place d'une case (en x ou y) dans la bonne direction selon la direction o� il regarde (la direction peut �tre aussi modifi�e pour pouvoir tourner!). Autres m�thodes,
servant � la gestion des points de vie: Exemple de la m�thode "regenLife()" qui regarde la fraction repr�sentant les points de vie du personnage et ajoute x points de vie au num�rateur. On v�rifie que le num�rateur ne d�passe pas le d�nominateur !.
De la m�me mani�re on peut retirer des points de vie gr�ce � la m�thode "setDamage()" par exemple qui pourra servir lorsqu'un monstre nous attaque ou inversement.

//Les types de personnages (sous-classes de Personnage.java)
Chaque type de personnage est cr�� gr�ce � notre super classe Personnage.java. Il existe alors diff�rentes sous-classes permettant de rajouter des sp�cificit�s � chaque type. Par
exemple, un monstre a un "radar" qui permet de d�tecter la position du Pj dans un rayon d�fini et de le suivre. Il pourra attaquer tandis qu'un villageois ne pourra pas. En revanche, un villageois peut discuter
avec le Pj. Un marchand pourra ajouter des objets � sa liste d'objets en vente. Chaque sous-classe contient des m�thodes et instances permettant de caract�riser chaque type de personnage comme d�crit ci-dessus.
 
- Les objets

//Objet.java
Ils sont repr�sent�s par diff�rents caract�res (voir ci-dessus).
De la m�me mani�re que pour les personnages, ils ont des caract�ristiques en commun: un nom, une position sur le terrain et une repr�sentation.

//Les types d'objets (sous-classes de Objet.java)
Chaque type d'objet est cr�� gr�ce � la super classe Objet.java. Les sous-classes nous permettent de caract�riser ces objets. Par exemple, une arme, une �p�e aura un nombre de d�gats d�finis ainsi qu'une durabilit�, 
tandis qu'une potion aura un taux de reg�n�ration de sant�.

- Le terrain
	Le terrain repr�sente une salle, on ne voit qu'une salle par une salle. Une salle est repr�sent�e par un tableau de caract�res (les caract�res possibles sont ceux affich�s plus haut).
	Un terrain poss�de donc un tableau, un num�ro (On peut changer les discussions � partir d'un certain num�ro de terrain par exemple pour amener plus d'indices au cours du jeu), une liste de personnages, objets, une porte d'entr�e et de sortie.
	//A COMPLETER

- La sauvegarde
	//A COMPLETER
 
***PROBLEMES RENCONTRES***
- Les monstres ne sont plus capable de casser les murs apr�s avoir charg� une partie.

 