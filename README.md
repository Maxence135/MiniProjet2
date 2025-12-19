# MiniProjet2 – Jeu de plateau Onitama

Ce projet est une implémentation complète du jeu de plateau **Onitama** en Java (Swing).  
Il inclut une interface graphique, un écran d’accueil, un système de cartes, des déplacements conformes aux règles officielles, ainsi qu’une intelligence artificielle très basique.

---

## Fonctionnalités

### 1. Interface graphique complète
- Écran d’accueil avec :
  - Joueur vs Joueur
  - Joueur vs IA (niveau facile)
  - Bouton Règles du jeu
  - Quitter
- Image de fond personnalisée.
- Plateau affiché graphiquement (5×5).
- Pièces affichées avec images personnalisées (roi rouge/bleu, pion rouge/bleu).
- Cartes affichées avec leurs images respectives.

### 2. Gestion des cartes
- Les 5 cartes de déplacement sont mélangées en début de partie.
- Chaque joueur reçoit 2 cartes, 1 est placée au centre.
- Après chaque coup, la carte utilisée est échangée avec la carte centrale.

### 3. Déplacements conformes au jeu Onitama
- Tous les mouvements possibles dépendent de la carte sélectionnée.
- Les déplacements du joueur bleu sont automatiquement inversés.
- Vérification complète :
  - Case dans le plateau
  - Mouvement valide selon la carte
  - Interdiction de capturer sa propre pièce
  - Capture autorisée d’une pièce adverse

### 4. Conditions de victoire
Deux conditions sont implémentées :
- Capture du roi adverse
- Atteinte du temple adverse avec son roi

Le jeu s’arrête immédiatement lorsqu’une condition est remplie.

### 5. Intelligence Artificielle (niveau facile)
Une première IA a été ajoutée.

- L’IA joue automatiquement après le tour du joueur lorsque le mode IA est activé.
- L’IA analyse toutes ses pièces et cartes, et choisit un coup valide.
- Sélection aléatoire parmi tous les coups possibles.
- Méthode utilisée :  
  ```java
  ia.jouerCoup();
  ```

Cette IA représente un niveau "facile". D’autres niveaux (moyen, avancé, minimax…) peuvent être ajoutés dans le futur.

### 6. Code propre et structuré
- Classes principales : `Jeu`, `Plateau`, `Piece`, `CarteDeplacement`, `Joueur`, `FenetreJeu`, `FenetreAccueil`, `IA`.
- Le code sépare bien la logique du jeu et l’interface graphique.
- Possibilité d’ajouter d’autres modes, IA plus avancée ou skins personnalisés.

---

## Lancer le projet

1. Ouvrir le projet dans NetBeans ou toute autre IDE compatible Swing.
2. Lancer la classe :
   ```
   Main.java
   ```
   qui ouvre l’écran d’accueil.
3. Choisir un mode de jeu.
4. Jouer normalement.

---

## Pistes d’amélioration futures

- IA moyenne (priorité aux captures, évite les cases dangereuses).
- IA avancée (minimax ou Monte-Carlo).
- Choix de plateaux alternatifs.
- Système de statistiques (victoires/défaites).
- Mode en ligne local (2 joueurs sur le même réseau).
- Animations de déplacement.
- Sons lors des actions.

---

## Crédits

Projet réalisé dans le cadre du MiniProjet2.  
Images de pièces et fonds réalisées avec génération assistée.

