 -- Projet Rogue-like IN404 2018 --

Participants: DE-JESUS Mathieu, LEDEME Florian, LAPEGUE Thibaut

***PRESENTATION DU JEU***
Le personnage part à la recherche d'un trésor. Il découvre une grotte et décide alors de s'y aventurer mais celui-ci heurte une pierre. Il se réveille dans un salle souterraine.
Il devra alors traverser plusieurs salles parsemmées des monstres qui seront à sa poursuite. Il devra alors s'équiper et essayer de chercher différents indices auprès de quelques villageois.
S'il arrive à atteindre la salle finale, il devra se mesurer à un ennemi de taille...

***DESCRIPTIF DU JEU PAR RAPPORT AU SUJET***
- L'univers du jeu est représenté par des caractères ASCII sur le terminal, il y a plusieurs salles à franchir.
- Les salles sont générées aléatoirement (sauf la première et la finale).
- Notre personnage évolue au fil des salles. Les salles contiennent des PNJ de plusieurs types: les villageois, inoffensifs avec qui
le personnage peut discuter et parfois acheter des objets, et des monstres qui poursuivent le personnage.
- Le personnage possède des points de vie, ainsi que des niveaux. Il a un inventaire qui lui permet de stocker des objets (épées, pioche etc..) mais
aussi une bourse. Cette bourse contient des rubis (monnaie du jeu).
- Les PNJ possèdent aussi des points de vie et niveaux.
- La sauvegarde de la partie est possible.

***EXECUTION/COMPILATION DU JEU***
- L'exécution du jeu se fait comme indiquée sur le sujet de projet.
-> Compilation: ./gradlew build (linux) ou gradlew.bat build (windows).
-> Exécution: Dans projet-in404-2018/build/distributions, on décompresse l'archive. On utilise les commandes suivantes pour lancer le jeu:
   projet-in404-2018/bin/projet-in404-2018 (linux)
   projet-in404-2018/bin/projet-in404-2018.bat (windows)
   
***MANUEL UTILISATEUR***
Le joueur est dans un grotte composée de plusieurs salles, ces salles contiennent des PNJ et divers objets pouvant être rammassés.
Le joueur peut effectuer différentes actions, comme se déplacer, ramasser un objet, discuter, consulter son inventaire etc. Un descriptif des commandes
essentielles pour jouer est disponible en jeu à tout moment. Il suffit de taper la commande "help" pour obtenir toutes les informations nécessaires.

Représentation des éléments:
- Le joueur est représenté par une flèche qui est dans la direction de la vision du personnage (<,v,>,^).
- Les PNJ villageois sont représentés par un 'N'.
- Les PNJ marchands par un 'M'
- Les PNJ monstres sont représentés par un 'G' ou par un '@' s'ils peuvent casser des murs.
- Les rubis (argent du jeu) sont réprésentés par une étoile '*'.
- Les armes sont réprésentées par un '!'.
- Objet flash (éclaircir la pièce) par un 'O'.
- Les clés par un 'y'.
- Les piles par un '-'.
- Les pioches par un 'T'.
- Les potions par un '6'.
- Le téléporteur (objet mystere) par un '%'.
- Un mur est représenté par '#'.
- Une porte par 'P'.
- Le sol par '.'.

Le joueur peut casser des murs avec une pioche, tuer des monstres avec une arme, acheter des objets chez un marchand, discuter avec les villageois ou encore utiliser
des objets (potions, téléporteur etc...). Chaque objet a ses caractéristiques: exemple: une épée peut avoir des dégats plus élévés qu'une autre.
Le joueur peut utiliser une potion pour se soigner.

***MANUEL TECHNIQUE***
Contenu du projet:

Le projet est constitué de différentes classes et packages:
- Classe "Application", classe principale du projet qui permet de démarrer une partie ou charger une sauvegarde.
- Package "objets", permettant d'implémenter les objets du jeu, comprenant une super classe abstraite "Objet" et des sous-classes implémentant différents types d'objets ("Arme", "Pile", "Key").
- Package "maps", permettant l'implémentation du terrain du jeu, comprenant la classe Terrain qui connait la liste des PNJ, PJ, objets présents, affiche le terrain, gère les actions des joueurs (déplacements, ramasser objet..) et
les commandes entrées dans le terminal. Le package contient la classe "Porte" qui permet de passer d'une salle à une autre et une classe "Generation" qui permet de générer le terrain aléatoirement (placement des murs etc).
- Package "maths" comprenant une classe "Point2D" permettant de gérer la position des personnages, et une classe "Fraction" permettant de gérer les points de vie des personnages.
- Package "sauvegarde" comprenant une classe "Charger" pour le chargement d'une sauvegarde et "Sauvegarder" pour sauvegarder une partie.
- Package "personnage" comprenant une super classe abstraite "Personnage" permettant de créer un personnage et des sous-classes "Marchand", "Monstre", "Pj", "Pnj" qui permettent d'implémenter un type de personnage.
Ce package contient une interface "Discussion" qui gère la discussion entre Pj et villageois.
- Package "intro" qui contient la classe "Cinematic". Cette classe gère la cinématique de début de partie et l'affichage de fin de partie (si le pj meurt).


Descriptif:

- Les personnages

//Personnage.java
Ils sont représentés par différents caractères (voir ci-dessus).
Les personnages ont une vision (Nord, Ouest, Est, Sud), une position (x,y) sur le terrain, des points de vie (on utilise une fraction (numérateur: points de vie actuels, dénominateur: points de vie max)). Ils connaissent la position devant eux (permettra de savoir s'ils doivent avancer ou non (ex: mur devant)).
Différents méthodes nous permettent de gérer les personnages. Par exemple pour avancer, on regarde la vision du personnage et sa position et on le déplace d'une case (en x ou y) dans la bonne direction selon la direction où il regarde (la direction peut être aussi modifiée pour pouvoir tourner!). Autres méthodes,
servant à la gestion des points de vie: Exemple de la méthode "regenLife()" qui regarde la fraction représentant les points de vie du personnage et ajoute x points de vie au numérateur. On vérifie que le numérateur ne dépasse pas le dénominateur !.
De la même manière on peut retirer des points de vie grâce à la méthode "setDamage()" par exemple qui pourra servir lorsqu'un monstre nous attaque ou inversement.

//Les types de personnages (sous-classes de Personnage.java)
Chaque type de personnage est créé grâce à notre super classe Personnage.java. Il existe alors différentes sous-classes permettant de rajouter des spécificités à chaque type. Par
exemple, un monstre a un "radar" qui permet de détecter la position du Pj dans un rayon défini et de le suivre. Il pourra attaquer tandis qu'un villageois ne pourra pas. En revanche, un villageois peut discuter
avec le Pj. Un marchand pourra ajouter des objets à sa liste d'objets en vente. Chaque sous-classe contient des méthodes et instances permettant de caractériser chaque type de personnage comme décrit ci-dessus.
 
- Les objets

//Objet.java
Ils sont représentés par différents caractères (voir ci-dessus).
De la même manière que pour les personnages, ils ont des caractéristiques en commun: un nom, une position sur le terrain et une représentation.

//Les types d'objets (sous-classes de Objet.java)
Chaque type d'objet est créé grâce à la super classe Objet.java. Les sous-classes nous permettent de caractériser ces objets. Par exemple, une arme, une épée aura un nombre de dégats définis ainsi qu'une durabilité, 
tandis qu'une potion aura un taux de regénération de santé.

- Le terrain
	Le terrain représente une salle, on ne voit qu'une salle par une salle. Une salle est représentée par un tableau de caractères (les caractères possibles sont ceux affichés plus haut).
	Un terrain possède donc un tableau, un numéro (On peut changer les discussions à partir d'un certain numéro de terrain par exemple pour amener plus d'indices au cours du jeu), une liste de personnages, objets, une porte d'entrée et de sortie.
	//A COMPLETER

- La sauvegarde
	//A COMPLETER
 
***PROBLEMES RENCONTRES***
- Les monstres ne sont plus capable de casser les murs après avoir chargé une partie.

 