/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maxen
 */
public class Plateau {

    private Piece[][] grille;

    public Plateau() {
        grille = new Piece[5][5];
        initialiserPlateau();
    }
    
    public void initialiserPlateau() {

    grille = new Piece[5][5]; // TRÈS IMPORTANT

    // --- Ligne du haut : BLEU (ligne 0) ---
    for (int c = 0; c < 5; c++) {
        if (c == 2)
            grille[0][c] = new Piece(Piece.Type.Roi, Piece.Couleur.Bleu, 0, c);
        else
            grille[0][c] = new Piece(Piece.Type.Pion, Piece.Couleur.Bleu, 0, c);
    }

    // --- Ligne du bas : ROUGE (ligne 4) ---
    for (int c = 0; c < 5; c++) {
        if (c == 2)
            grille[4][c] = new Piece(Piece.Type.Roi, Piece.Couleur.Rouge, 4, c);
        else
            grille[4][c] = new Piece(Piece.Type.Pion, Piece.Couleur.Rouge, 4, c);
    }

    // Lignes 1, 2, 3 vides
    for (int r = 1; r < 4; r++)
        for (int c = 0; c < 5; c++)
            grille[r][c] = null;
}


    public Piece getCase(int x, int y) {
        return grille[x][y];
    }

    public boolean estDansPlateau(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public void placerPiece(Piece p) {
        grille[p.getX()][p.getY()] = p;
    }

    public void deplacerPiece(Piece p, int nx, int ny) {
        grille[p.getX()][p.getY()] = null; // libere l ancien emplacement apres deplacement
        p.deplacer(nx, ny);
        grille[nx][ny] = p; // et la, pose dans le nouveau
    }
}
