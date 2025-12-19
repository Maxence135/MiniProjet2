
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author maxen
 */
public class FenetreJeu extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FenetreJeu.class.getName());
    private Jeu jeu;
    private Piece pieceSelectionnee = null;
    private CarteDeplacement carteSelectionnee = null;
    private final String ImagesCartesChemin
            = "C:/Users/maxen/Desktop/MiniProjet2JEU/MiniProjet2/Projet_Onitama/src/Cartes/";
    private final String ImagesPiecesChemin
            = "C:/Users/maxen/Desktop/MiniProjet2JEU/MiniProjet2/Projet_Onitama/src/Pieces/";
    private IA ia = null;
    private boolean modeIA;

    /**
     * Creates new form FenetreJeu
     */
    private ImageIcon chargerImage(String chemin, int w, int h) {
        java.io.File f = new java.io.File(chemin);

        if (!f.exists()) {
            System.out.println("Image introuvable : " + chemin);
            return null;
        }

        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public FenetreJeu(boolean modeIA) {
        setContentPane(new BackgroundPanel("/Fonds/fond.png"));
        this.modeIA = modeIA;
        initComponents();
        fixerTailleBoutonsCartes();
        jeu = new Jeu();
        if (modeIA) {
            ia = new IA(jeu.getJoueurBleu(), jeu.getPlateau(), jeu);
        }
        initialiserPlateauGraphique();
        SwingUtilities.invokeLater(() -> {
            mettreAJourAffichage();
        });
        btnRejouer.setVisible(false);
    }

    private void fixerTailleBoutonsCartes() {
        Dimension dim = new Dimension(250, 105);

        btnCarte1.setPreferredSize(dim);
        btnCarte1.setMinimumSize(dim);
        btnCarte1.setMaximumSize(dim);

        btnCarte2.setPreferredSize(dim);
        btnCarte2.setMinimumSize(dim);
        btnCarte2.setMaximumSize(dim);

        btnCarteCentre.setPreferredSize(dim);
        btnCarteCentre.setMinimumSize(dim);
        btnCarteCentre.setMaximumSize(dim);
    }

    private void caseCliquee(int x, int y) {
        Plateau p = jeu.getPlateau();
        Piece piece = p.getCase(x, y);

        // 1) Aucune pièce sélectionnée mais une carte a été choisie -> sélectionner une pièce
        if (pieceSelectionnee == null && carteSelectionnee != null) {

            if (piece != null && piece.getCouleur() == jeu.getJoueurActuel().getCouleur()) {
                pieceSelectionnee = piece;
                resetCouleurs();
                boutons[x][y].setBackground(Color.CYAN);

                afficherDeplacementsPossibles(pieceSelectionnee, carteSelectionnee);
                return;
            }

            System.out.println("Pas une pièce jouable.");
            return;
        }

        // 2) Une pièce est déjà sélectionnée + une carte est choisie
        if (pieceSelectionnee != null && carteSelectionnee != null) {

            // 👉 CAS 1 : On clique sur une autre pièce de SON joueur → changer la sélection
            if (piece != null && piece.getCouleur() == jeu.getJoueurActuel().getCouleur()) {
                pieceSelectionnee = piece;
                resetCouleurs();
                boutons[x][y].setBackground(Color.CYAN);

                afficherDeplacementsPossibles(pieceSelectionnee, carteSelectionnee);
                return;
            }

            // 👉 CAS 2 : On clique ailleurs → tenter un déplacement
            tenterDeplacement(x, y);
            return;
        }
    }

    private JButton[][] boutons = new JButton[5][5];

    private void resetCouleurs() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boutons[i][j].setBackground(null);
            }
        }
    }

    private void initialiserPlateauGraphique() {

        panelPlateau.setLayout(new java.awt.GridLayout(5, 5));

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {

                JButton btn = new JButton();
                btn.setFocusable(false);
                btn.setPreferredSize(new java.awt.Dimension(80, 80));
                btn.setMaximumSize(new java.awt.Dimension(80, 80));
                btn.setMinimumSize(new java.awt.Dimension(80, 80));

                boutons[x][y] = btn;

                final int fx = x;
                final int fy = y;

                btn.addActionListener(e -> caseCliquee(fx, fy));

                panelPlateau.add(btn);
            }
        }
    }

    private void mettreAJourAffichage() {
        if (jeu == null) {
            return;
        }

        if (jeu.getJoueurActuel().getCouleur() == Piece.Couleur.Rouge) {
            lblTour.setText("Tour du Joueur Rouge");
        } else {
            lblTour.setText("Tour du Joueur Bleu");
        }

        Plateau p = jeu.getPlateau();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {

                Piece piece = p.getCase(x, y);

                if (piece == null) {
                    boutons[x][y].setIcon(null);
                    boutons[x][y].setText("");
                } else {

                    String fichier = "";

                    if (piece.getType() == Piece.Type.Roi) {
                        fichier += "R"; //Roi
                    } else {
                        fichier += "P"; //Pion
                    }

                    if (piece.getCouleur() == Piece.Couleur.Rouge) {
                        fichier += "R"; //Rouge
                    } else {
                        fichier += "B"; //Bleu
                    }
                    String chemin = ImagesPiecesChemin + fichier + ".png";

                    int w2 = boutons[x][y].getWidth();
                    int h2 = boutons[x][y].getHeight();

                    ImageIcon icon = new ImageIcon(
                            new ImageIcon(chemin).getImage().getScaledInstance(w2, h2, Image.SCALE_SMOOTH)
                    );

                    boutons[x][y].setIcon(icon);
                    boutons[x][y].setText("");
                }
            }
            Joueur j = jeu.getJoueurActuel();
            CarteDeplacement c1 = j.getCarte1();
            CarteDeplacement c2 = j.getCarte2();
            CarteDeplacement cc = jeu.getCarteCentre();

            int w = btnCarte1.getWidth();
            int h = btnCarte1.getHeight();

            btnCarte1.setIcon(chargerImage(
                    ImagesCartesChemin + c1.getNom().toLowerCase() + ".jpg",
                    w, h
            ));
            btnCarte1.setText("");

            btnCarte2.setIcon(chargerImage(
                    ImagesCartesChemin + c2.getNom().toLowerCase() + ".jpg",
                    w, h
            ));
            btnCarte2.setText("");

            btnCarteCentre.setIcon(chargerImage(
                    ImagesCartesChemin + cc.getNom().toLowerCase() + ".jpg",
                    w, h
            ));
            btnCarteCentre.setText("");

        }
    }

    private void carteCliquee(CarteDeplacement carte) {
        carteSelectionnee = carte;
        System.out.println("Carte cliquee : " + carte.getNom());
    }

    private void afficherDeplacementsPossibles(Piece piece, CarteDeplacement carte) {

        // Efface d'abord toute ancienne surbrillance
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boutons[i][j].setBackground(null);
            }
        }

        // Si aucune pièce ou carte → rien à afficher
        if (piece == null || carte == null) {
            return;
        }

        int x = piece.getX();
        int y = piece.getY();

        boolean joueurBleu = (piece.getCouleur() == Piece.Couleur.Bleu);

        for (int[] d : carte.getMouvements()) {

            int dx = d[0];
            int dy = d[1];

            //On doit inversé pour les bleus
            if (joueurBleu) {
                dx = -dx;
                dy = -dy;
            }

            int nx = x + dx;
            int ny = y + dy;

            // On vérifie que la case est dans le plateau
            if (jeu.getPlateau().estDansPlateau(nx, ny)) {
                boutons[nx][ny].setBackground(Color.YELLOW);
            }
        }
    }

    private void tenterDeplacement(int x, int y) {

        if (pieceSelectionnee == null || carteSelectionnee == null) {
            return;
        }

        boolean ok = jeu.deplacer(pieceSelectionnee, carteSelectionnee, x, y);

        if (ok) {
            System.out.println("Deplacement reussi !");
            pieceSelectionnee = null;
            carteSelectionnee = null;

            mettreAJourAffichage();
            resetCouleurs();

            //Si mode IA et que c'est le tour du bleu
            if (modeIA && jeu.getJoueurActuel().getCouleur() == Piece.Couleur.Bleu) {

                //la le but c'est de laisser du temps sinon bug graphique
                new Thread(() -> {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                    }

                    //Lancer l'IA
                    ia.jouerCoup();

                    //mettre à jour l’ecran apres le coup de l'IA
                    SwingUtilities.invokeLater(() -> {
                        mettreAJourAffichage();
                    });
                }).start();

            }

            if (jeu.estTermine()) {
                String gagnant = jeu.getVainqueur();

                JOptionPane.showMessageDialog(this,
                        "Victoire du joueur " + gagnant + " !");

                // Montrer le bouton rejouer
                btnRejouer.setVisible(true);

                return;

            } else {
                System.out.println("Deplacement impossible !");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPlateau = new javax.swing.JPanel();
        btnCarte1 = new javax.swing.JButton();
        btnCarte2 = new javax.swing.JButton();
        btnCarteCentre = new javax.swing.JButton();
        lblTour = new javax.swing.JLabel();
        btnRejouer = new javax.swing.JButton();
        btnRetour = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPlateau.setPreferredSize(new java.awt.Dimension(400, 400));
        panelPlateau.setLayout(new java.awt.GridLayout(5, 5));

        btnCarte1.setText("jButton1");
        btnCarte1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarte1ActionPerformed(evt);
            }
        });

        btnCarte2.setText("jButton2");
        btnCarte2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarte2ActionPerformed(evt);
            }
        });

        btnCarteCentre.setText("jButton3");
        btnCarteCentre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarteCentreActionPerformed(evt);
            }
        });

        lblTour.setText("jLabel1");

        btnRejouer.setText("Recommencer");
        btnRejouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejouerActionPerformed(evt);
            }
        });

        btnRetour.setText("Retour au menu");
        btnRetour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTour, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(btnRejouer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelPlateau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCarte1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCarte2, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCarteCentre)
                        .addGap(31, 31, 31))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRetour)
                        .addGap(126, 126, 126))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnCarte1, btnCarte2, btnCarteCentre});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTour)
                            .addComponent(btnRejouer))
                        .addGap(32, 32, 32)
                        .addComponent(panelPlateau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(btnCarteCentre)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCarte1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCarte2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRetour)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnCarte1, btnCarte2, btnCarteCentre});

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnCarte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarte1ActionPerformed
        // TODO add your handling code here:
        carteSelectionnee = jeu.getJoueurActuel().getCarte1();

        System.out.println("Carte selectionnee : " + carteSelectionnee.getNom());

        resetCouleurs();

        // Recolorier la pièce sélectionnée, si il y en a une
        if (pieceSelectionnee != null) {
            boutons[pieceSelectionnee.getX()][pieceSelectionnee.getY()].setBackground(Color.BLUE);
            afficherDeplacementsPossibles(pieceSelectionnee, carteSelectionnee);
        }

        // Mettre la carte en surbrillance
        btnCarte1.setBackground(Color.YELLOW);
        btnCarte2.setBackground(null);
    }//GEN-LAST:event_btnCarte1ActionPerformed

    private void btnCarte2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarte2ActionPerformed
        // TODO add your handling code here:
        carteSelectionnee = jeu.getJoueurActuel().getCarte2();

        System.out.println("Carte selectionnee : " + carteSelectionnee.getNom());

        resetCouleurs();

        if (pieceSelectionnee != null) {
            boutons[pieceSelectionnee.getX()][pieceSelectionnee.getY()].setBackground(Color.BLUE);
            afficherDeplacementsPossibles(pieceSelectionnee, carteSelectionnee);
        }

        btnCarte2.setBackground(Color.YELLOW);
        btnCarte1.setBackground(null);
    }//GEN-LAST:event_btnCarte2ActionPerformed

    private void btnCarteCentreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarteCentreActionPerformed
        // TODO add your handling code here:
        carteCliquee(jeu.getCarteCentre());
    }//GEN-LAST:event_btnCarteCentreActionPerformed

    private void btnRejouerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRejouerActionPerformed
        // TODO add your handling code here:
        jeu = new Jeu();
        pieceSelectionnee = null;
        carteSelectionnee = null;
        resetCouleurs();
        mettreAJourAffichage();
        btnRejouer.setVisible(false);
    }//GEN-LAST:event_btnRejouerActionPerformed

    private void btnRetourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetourActionPerformed
        // TODO add your handling code here:
        new FenetreAccueil().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRetourActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FenetreJeu(false).setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarte1;
    private javax.swing.JButton btnCarte2;
    private javax.swing.JButton btnCarteCentre;
    private javax.swing.JButton btnRejouer;
    private javax.swing.JButton btnRetour;
    private javax.swing.JLabel lblTour;
    private javax.swing.JPanel panelPlateau;
    // End of variables declaration//GEN-END:variables
}
