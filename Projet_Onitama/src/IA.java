
import java.util.ArrayList;
import java.util.Random;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maxen
 */
public class IA {

    private Joueur ia;
    private Plateau plateau;
    private Jeu jeu;

    public IA(Joueur ia, Plateau plateau, Jeu jeu) {
        this.ia = ia;
        this.plateau = plateau;
        this.jeu = jeu;
    }

    public void jouerCoup() {

        // Liste de tous les coups possibles
        ArrayList<CoupPossible> coups = new ArrayList<>();

        for (Piece p : ia.getPieces()) {

            // Mort ou capturée
            if (p == null || p.getX() == -1) {
                continue;
            }

            // Tester les 2 cartes
            for (CarteDeplacement carte : ia.getCartes()) {

                int[][] moves = carte.getMouvements();

                for (int[] d : moves) {

                    int dx = d[0];
                    int dy = d[1];

                    // Inversion pour les bleus
                    if (ia.getCouleur() == Piece.Couleur.Bleu) {
                        dx = -dx;
                        dy = -dy;
                    }

                    int nx = p.getX() + dx;
                    int ny = p.getY() + dy;

                    // Valide ?
                    if (plateau.estDansPlateau(nx, ny)) {
                        Piece cible = plateau.getCase(nx, ny);

                        // On ne peut pas prendre une pièce alliée
                        if (cible != null && cible.getCouleur() == ia.getCouleur()) {
                            continue;
                        }

                        // Ajouter ce coup
                        coups.add(new CoupPossible(p, carte, nx, ny));
                    }
                }
            }
        }

        // Aucun coup ?
        if (coups.isEmpty()) {
            System.out.println("IA n’a aucun coup !");
            return;
        }

        // Choisir un coup au hasard
        CoupPossible choix = coups.get(new Random().nextInt(coups.size()));

        // Exécuter le coup
        jeu.deplacer(choix.piece, choix.carte, choix.x, choix.y);
    }

    // Petite structure interne pour stocker un coup
    private static class CoupPossible {

        Piece piece;
        CarteDeplacement carte;
        int x, y;

        CoupPossible(Piece p, CarteDeplacement c, int x, int y) {
            this.piece = p;
            this.carte = c;
            this.x = x;
            this.y = y;
        }
    }
}
