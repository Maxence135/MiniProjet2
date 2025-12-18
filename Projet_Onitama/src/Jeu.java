/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.*;

/**
 *
 * @author maxen
 */
public class Jeu {

    private Plateau plateau;
    private Joueur joueurRouge;
    private Joueur joueurBleu;
    private CarteDeplacement carteCentre;
    public boolean tourRouge = true;
    private boolean victoireRouge = false;
    private boolean victoireBleu = false;

    public Jeu() {
        initialiserPartie();
    }

    private void initialiserPartie() {
        plateau = new Plateau();

        //LEs pieces rouges :
        ArrayList<Piece> rouges = new ArrayList<>();
        rouges.add(new Piece(Piece.Type.Roi, Piece.Couleur.Rouge, 4, 2));
        for (int c = 0; c < 5; c++) {
            if (c != 2) {
                rouges.add(new Piece(Piece.Type.Roi, Piece.Couleur.Rouge, 4, c));
            }
        }

        //Les pieces bleues :
        ArrayList<Piece> bleues = new ArrayList<>();
        bleues.add(new Piece(Piece.Type.Roi, Piece.Couleur.Bleu, 4, 2));
        for (int c = 0; c < 5; c++) {
            if (c != 2) {
                bleues.add(new Piece(Piece.Type.Roi, Piece.Couleur.Bleu, 4, c));
            }
        }

        //Pour placer les pieces :
        rouges.forEach(p -> plateau.placerPiece(p));
        bleues.forEach(p -> plateau.placerPiece(p));

        //Les cartes :
        CarteDeplacement[] cartes = ListeCartes.getToutesLesCartes();
        List<CarteDeplacement> liste = Arrays.asList(cartes);
        Collections.shuffle(liste);

        joueurRouge = new Joueur(rouges, liste.get(0), liste.get(1));
        joueurBleu = new Joueur(bleues, liste.get(2), liste.get(3));
        carteCentre = liste.get(4);
    }

    //Bouger les pieces :
    public boolean deplacer(Piece p, CarteDeplacement carte, int indexMouv) {
        int dx = carte.getMouvements()[indexMouv][0];
        int dy = carte.getMouvements()[indexMouv][1];

        //Inverse pour JBleu :
        if (p.getCouleur() == Piece.Couleur.Bleu) {
            dx = -dx;
        }
        int nx = p.getX() + dx;
        int ny = p.getY() + dy;

        if (!plateau.estDansPlateau(nx, ny)) {
            return false;
        }

        Piece cible = plateau.getCase(nx, ny);

        if (cible != null && cible.getCouleur() == p.getCouleur()) {
            return false;
        }

        plateau.deplacerPiece(p, nx, ny);

        //Victoire 1 : capture du roi
        if (cible != null && cible.getType() == Piece.Type.Roi) {
            if (p.getCouleur() == Piece.Couleur.Rouge) {
                victoireRouge = true;
            } else {
                victoireBleu = true;
            }
        }

        //Victoire 2 : temple
        if (p.getType() == Piece.Type.Roi) {
            if (p.getCouleur() == Piece.Couleur.Rouge && nx == 0 && ny == 2) {
                victoireRouge = true;
            }
            if (p.getCouleur() == Piece.Couleur.Bleu && nx == 4 && ny == 2) {
                victoireBleu = true;
            }
        }

        //Echange carte :
        echangerCarte(carte);

        //Changer joueur :
        tourRouge = !tourRouge;
        return true;
    }

    private void echangerCarte(CarteDeplacement carte) {
        CarteDeplacement temp = carteCentre;
        carteCentre = carte;

        if (tourRouge) {
            joueurRouge.remplacerCarte(carte, temp);
        } else {
            joueurBleu.remplacerCarte(carte, temp);
        }
    }

    public boolean estTermine() {
        return victoireRouge || victoireBleu;
    }

    public String getVainqueur() {
        if (victoireRouge) {
            return "Rouge";
        }
        if (victoireBleu) {
            return "Bleu";
        }
        return null;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueurActuel() {
        return tourRouge
                ? joueurRouge : joueurBleu;
    }
    
    public CarteDeplacement getCarteCentre(){
        return carteCentre;
    }
}
