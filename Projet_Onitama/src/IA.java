/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maxen
 */
public class IA {
    private Joueur joueurIA;
    private Plateau plateau;
    private Jeu jeu;
    
    public IA(Joueur joueurIA, Plateau plateau, Jeu jeu) {
        this.joueurIA = joueurIA;
        this.plateau = plateau;
        this.jeu = jeu;
    }
    
    //IA facile : joue le premier coup légal possible:
    public void jouerTourFacile() {
        //Pour chaque pièce du bot
        for (Piece piece : joueurIA.getPieces()) {

            int x = piece.getX();
            int y = piece.getY();
            
            //Pour chaque carte que le bot possède
            CarteDeplacement[] cartes = { joueurIA.getCarte1(), joueurIA.getCarte2() };

            for (CarteDeplacement carte : cartes) {

                for (int[] m : carte.getMouvements()) {

                    int nx = x + m[0];
                    int ny = y + m[1];
                    
                    //Vérifier limite du plateau
                    if (!plateau.estDansPlateau(nx, ny)) continue;

                    //Vérifier si le déplacement est possible
                    if (jeu.deplacer(piece, carte, nx, ny)) {
                        System.out.println("IA joue : " + carte.getNom() + " avec " + piece.getType());
                        return;
                    }
                }
            }
        }

        System.out.println("IA n'a trouva aucun coup jouable.");
    }
}
