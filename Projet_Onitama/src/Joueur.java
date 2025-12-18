
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author maxen
 */
public class Joueur {

    private ArrayList<Piece> pieces;
    private CarteDeplacement carte1;
    private CarteDeplacement carte2;
    private Piece.Couleur couleur;

    public Joueur(ArrayList<Piece> pieces, CarteDeplacement carte1, CarteDeplacement carte2) {
        this.pieces = pieces;
        this.carte1 = carte1;
        this.carte2 = carte2;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public CarteDeplacement getCarte1() {
        return carte1;
    }

    public CarteDeplacement getCarte2() {
        return carte2;
    }

    public void remplacerCarte(CarteDeplacement ancienne, CarteDeplacement nouvelle) {
        if (carte1 == ancienne) {
            carte1 = nouvelle;
        } else if (carte2 == ancienne) {
            carte2 = nouvelle;
        }
    }
    
    public Piece.Couleur getCouleur(){
        return couleur;
    }
}
