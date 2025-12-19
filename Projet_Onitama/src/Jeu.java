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
    private Joueur joueurActuel;
    private CarteDeplacement carteCentre;
    public boolean tourRouge = true;
    private boolean victoireRouge = false;
    private boolean victoireBleu = false;

    public Jeu() {
        initialiserPartie();
        joueurActuel = joueurRouge;
    }

    private void initialiserPartie() {

        plateau = new Plateau();

        //Récupérer les pièces placées sur le plateau
        ArrayList<Piece> rouges = new ArrayList<>();
        ArrayList<Piece> bleues = new ArrayList<>();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                Piece p = plateau.getCase(x, y);
                if (p != null) {
                    if (p.getCouleur() == Piece.Couleur.Rouge) {
                        rouges.add(p);
                    } else {
                        bleues.add(p);
                    }
                }
            }
        }

        //Distribuer les cartes
        CarteDeplacement[] cartes = ListeCartes.getToutesLesCartes();
        List<CarteDeplacement> liste = Arrays.asList(cartes);
        Collections.shuffle(liste);

        joueurRouge = new Joueur(rouges, liste.get(0), liste.get(1), Piece.Couleur.Rouge);
        joueurBleu = new Joueur(bleues, liste.get(2), liste.get(3), Piece.Couleur.Bleu);
        carteCentre = liste.get(4);

        joueurActuel = joueurRouge;
    }

    //Bouger les pieces :
    public boolean deplacer(Piece p, CarteDeplacement carte, int destX, int destY) {

        int x = p.getX();
        int y = p.getY();

        int[][] dep = carte.getMouvements();

        boolean mouvementValide = false;

        //Ici aussi faut inversé pour bleu
        for (int[] d : dep) {

            int dx = d[0];
            int dy = d[1];

            if (p.getCouleur() == Piece.Couleur.Bleu) {
                dx = -dx;
                dy = -dy;
            }

            int nx = x + dx;
            int ny = y + dy;

            if (nx == destX && ny == destY) {
                mouvementValide = true;
                break;
            }
        }

        if (!mouvementValide) {
            return false;
        }

        //Vérifier que la case est dans le plateau
        if (!plateau.estDansPlateau(destX, destY)) {
            return false;
        }

        Piece cible = plateau.getCase(destX, destY);

        //Si une pièce adverse est là : capture
        if (cible != null) {

            //Interdit de capturer sa propre pièce
            if (cible.getCouleur() == p.getCouleur()) {
                return false;
            }

            //Si c'est le roi adverse : victoire
            if (cible.getType() == Piece.Type.Roi) {
                if (p.getCouleur() == Piece.Couleur.Rouge) {
                    victoireRouge = true;
                } else {
                    victoireBleu = true;
                }
            }
        }

        //Déplacer la pièce
        plateau.deplacerPiece(p, destX, destY);

        //Condition de victoire "Temple"
        if (p.getType() == Piece.Type.Roi) {
            //Roi rouge atteint le temple bleu (2,0)
            if (p.getCouleur() == Piece.Couleur.Rouge && destX == 2 && destY == 0) {
                victoireRouge = true;
            }

            //Roi bleu atteint le temple rouge (2,4)
            if (p.getCouleur() == Piece.Couleur.Bleu && destX == 2 && destY == 4) {
                victoireBleu = true;
            }
        }

        //Echange carte :
        echangerCarte(carte);

        //Changer joueur :
        changerDeJoueur();
        tourRouge = !tourRouge;
        return true;
    }

    private void echangerCarte(CarteDeplacement carte) {
        CarteDeplacement temp = carteCentre;
        carteCentre = carte;

        if (joueurActuel == joueurRouge) {
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

    public void changerDeJoueur() {
        if (joueurActuel == joueurRouge) {
            joueurActuel = joueurBleu;
        } else {
            joueurActuel = joueurRouge;
        }
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    public Joueur getJoueurRouge() {
        return joueurRouge;
    }

    public Joueur getJoueurBleu() {
        return joueurBleu;
    }

    public CarteDeplacement getCarteCentre() {
        return carteCentre;
    }
}
