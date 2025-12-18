
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;

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

    /**
     * Creates new form FenetreJeu
     */
    public FenetreJeu() {
        initComponents();
        jeu = new Jeu();
        initialiserPlateauGraphique();
        mettreAJourAffichage();
        btnRejouer.setVisible(false);
    }

    private void caseCliquee(int x, int y) {
        Plateau p = jeu.getPlateau();
        Piece piece = p.getCase(x, y);

        // 1) Si aucune pièce n’est encore sélectionnée
        if (pieceSelectionnee == null && carteSelectionnee != null) {
            
            System.out.println(
        "DEBUG → pièce=" + piece +
        " | couleur pièce=" + (piece != null ? piece.getCouleur() : "null") +
        " | couleur joueur actuel=" + jeu.getJoueurActuel().getCouleur()
    );

            // Vérifier qu'il y a bien une pièce ET qu'elle appartient au joueur
            if (piece != null && piece.getCouleur() == jeu.getJoueurActuel().getCouleur()) {
                boutons[x][y].setBackground(Color.CYAN);

                pieceSelectionnee = piece; // on la sélectionne
                System.out.println("Pièce sélectionnée : " + piece.getType());

                // afficher les déplacements possibles
                afficherDeplacementsPossibles(pieceSelectionnee, carteSelectionnee);

            } else {
                System.out.println("Pas une pièce jouable.");
            }

            return;
        }
        // 2) Si une pièce est déjà sélectionnée → on tente un déplacement
        if (pieceSelectionnee != null && carteSelectionnee != null) {

            // Si la case cliquée n'est pas la pièce elle-même
            if (!(pieceSelectionnee.getX() == x && pieceSelectionnee.getY() == y)) {

                tenterDeplacement(x, y);
                return;
            }
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
            lblTour.setText("Tour du Joueur ROUGE");
        } else {
            lblTour.setText("Tour du Joueur BLEU");
        }

        Plateau p = jeu.getPlateau();

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {

                Piece piece = p.getCase(x, y);

                if (piece == null) {
                    boutons[x][y].setText("");
                } else {
                    String txt = "";

                    if (piece.getType() == Piece.Type.Roi) {
                        txt += "R";
                    } else {
                        txt += "P";
                    }

                    if (piece.getCouleur() == Piece.Couleur.Rouge) {
                        txt += "R";
                    } else {
                        txt += "B";
                    }

                    boutons[x][y].setText(txt);
                }
            }
            Joueur j = jeu.getJoueurActuel();
            btnCarte1.setText(j.getCarte1().getNom());
            btnCarte2.setText(j.getCarte2().getNom());
            btnCarteCentre.setText(jeu.getCarteCentre().getNom());

        }
    }

    private void carteCliquee(CarteDeplacement carte) {
        carteSelectionnee = carte;
        System.out.println("Carte cliquée : " + carte.getNom());
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

        // Pour chaque déplacement possible
        for (int[] d : carte.getMouvements()) {

            int nx = x + d[0];
            int ny = y + d[1];

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
            System.out.println("Déplacement réussi !");
            pieceSelectionnee = null;
            carteSelectionnee = null;

            mettreAJourAffichage();
            resetCouleurs();
        }

        if (jeu.estTermine()) {
            String gagnant = jeu.getVainqueur();

            JOptionPane.showMessageDialog(this,
                    "Victoire du joueur " + gagnant + " !");

            // Montrer le bouton rejouer
            btnRejouer.setVisible(true);

            return;

        } else {
            System.out.println("Déplacement impossible !");
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

        btnRejouer.setText("jButton1");
        btnRejouer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRejouerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(133, 133, 133)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCarte1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCarte2))
                    .addComponent(panelPlateau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addComponent(btnCarteCentre)
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnRejouer))
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTour, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(lblTour)
                        .addGap(38, 38, 38)
                        .addComponent(panelPlateau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(283, 283, 283)
                        .addComponent(btnCarteCentre)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCarte1)
                    .addComponent(btnCarte2))
                .addGap(18, 18, 18)
                .addComponent(btnRejouer))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCarte1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarte1ActionPerformed
        // TODO add your handling code here:
        carteSelectionnee = jeu.getJoueurActuel().getCarte1();

        System.out.println("Carte sélectionnée : " + carteSelectionnee.getNom());

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

        System.out.println("Carte sélectionnée : " + carteSelectionnee.getNom());

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
        java.awt.EventQueue.invokeLater(() -> new FenetreJeu().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarte1;
    private javax.swing.JButton btnCarte2;
    private javax.swing.JButton btnCarteCentre;
    private javax.swing.JButton btnRejouer;
    private javax.swing.JLabel lblTour;
    private javax.swing.JPanel panelPlateau;
    // End of variables declaration//GEN-END:variables
}
