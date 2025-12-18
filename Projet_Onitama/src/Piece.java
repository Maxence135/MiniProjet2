/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maxen
 */
public class Piece {
    public enum Type {Roi,Pion};
    public enum Couleur {Rouge,Bleu};
    
    private Type type;
    private Couleur couleur;
    private int x,y;

    public Piece(Type type, Couleur couleur, int x, int y) {
        this.type = type;
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }
    
    public int getX() {return x;}
    public int getY() {return y;}
    
    public void deplacer(int nx, int ny){
        this.x = nx;
        this.y = ny;
    }
    
    public Type getType() {return type;}
    public Couleur getCouleur(){return couleur;}
}
