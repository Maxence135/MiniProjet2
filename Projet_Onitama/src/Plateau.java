/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maxen
 */
public class Plateau {

    private Piece[][] cases = new Piece[5][5];

    public Plateau() {
    }

    public Piece getCase(int x, int y) {
        return cases[x][y];
    }

    public boolean estDansPlateau(int x, int y) {
        return x >= 0 && x < 5 && y >= 0 && y < 5;
    }

    public void placerPiece(Piece p) {
        cases[p.getX()][p.getY()] = p;
    }

    public void deplacerPiece(Piece p, int nx, int ny) {
        cases[p.getX()][p.getY()] = null; // libere l ancien emplacement apres deplacement
        p.deplacer(nx, ny);
        cases[nx][ny] = p; // et la, pose dans le nouveau
    }
}
