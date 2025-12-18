# MiniProjet2 – Jeu de plateau Onitama en Java

Ce projet est une implémentation du jeu de plateau Onitama en Java, réalisée dans le cadre du MiniProjet2.  
Le programme propose une interface graphique et une gestion du plateau, des cartes et des déplacements.

---

## Fonctionnalités

- Plateau 5×5 entièrement interactif  
- Affichage graphique des pièces (Roi / Pion pour chaque couleur)  
- Chargement automatique des cartes de déplacement  
- Sélection d’une pièce et affichage des déplacements possibles  
- Gestion des règles du jeu :
  - Déplacements basés sur la carte sélectionnée  
  - Capture des pièces adverses  
  - Victoire par capture du roi  
  - Victoire par prise du temple  
  - Échange des cartes après chaque tour  
- Jeu au tour par tour (Rouge puis Bleu)  
- Bouton permettant de relancer une partie après une victoire

---

## Structure du projet

/src
├── FenetreJeu.java # Interface graphique
├── Jeu.java # Logique générale du jeu
├── Plateau.java # Gestion du plateau 5×5
├── Piece.java # Définition des pièces
├── Joueur.java # Gestion des cartes et des pièces d’un joueur
├── CarteDeplacement.java # Représentation d’une carte
└── ListeCartes.java # Liste des cartes disponibles

├── Cartes/ # Images des cartes
└── Pieces/ # Images des pièces (RR, RB, PR, PB)

---

## Lancer le programme

Exécuter la classe suivante :

FenetreJeu.java

La fenêtre de jeu s’ouvre et une nouvelle partie est automatiquement générée.

---

## Règles implémentées

- Chaque joueur possède deux cartes de déplacement.
- Une carte centrale détermine l’ordre du tour et est échangée après chaque coup.
- Les pièces ne peuvent se déplacer que selon les directions indiquées par la carte.
- Une pièce ne peut pas capturer une pièce de la même couleur.
- Deux conditions de victoire :
  - Capturer le roi adverse.
  - Atteindre le temple opposé avec son propre roi.

---

## Ressources graphiques

Nommage des pièces utilisées dans l’affichage :

- `RR.png` : Roi rouge  
- `RB.png` : Roi bleu  
- `PR.png` : Pion rouge  
- `PB.png` : Pion bleu  

Les cartes sont nommées selon leur nom en minuscules, par exemple :

boar.jpg
cobra.jpg
dragon.jpg
...

---

## Auteur

Projet réalisé dans le cadre du MiniProjet 2.  
Développeur : Maxence.
