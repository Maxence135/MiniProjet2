/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maxen
 */
public class CarteDeplacement {

    private String nom;
    private int[][] mouvements; // vecteurs (dx, dy)

    public CarteDeplacement(String nom, int[][] mouvements) {
        this.nom = nom;
        this.mouvements = mouvements;
    }

    public String getNom() {
        return nom;
    }

    public int[][] getMouvements() {
        return mouvements;
    }

    @Override
    public String toString() {
        return nom;
    }
}
