/*
 * GUIPlanDeJeux.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 7 juin 2007
 *
 */

package minimumsudoku;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Panel principal de l'application
 * TODO ? Internationalisation de l'interface GUI ?
 * @author Jean-Etienne
 */
public class GUIPlanDeJeux extends JPanel implements ActionListener {

    private int partieSelectionnee = 0;
    private BoiteFeuille[] bf = new BoiteFeuille[81];
    private BoiteComposite[] bc = new BoiteComposite[9];
    
    private GridBagLayout gb;
    private GridBagConstraints ct;
    private JButton btnOuvrir, btnValider, btnAide, btnSolution, btnQuitter;
    private JPanel jeuPanel;
    private GUIOpenScreen dialog = null;
    
    /** Cree une nouvelle instance de GUIPlanDeJeux */
    public GUIPlanDeJeux() {
        // Definit le layout
        gb = new GridBagLayout();
        this.setLayout(gb);
        ct = new GridBagConstraints();
        // Ajout des differents boutons d'interface
        btnOuvrir = new JButton("Ouvrir");
        btnOuvrir.setVerticalTextPosition(AbstractButton.CENTER);
        btnOuvrir.setHorizontalTextPosition(AbstractButton.CENTER); //LEADING = LEFT, for left-to-right locales
        btnOuvrir.setMnemonic(KeyEvent.VK_O);
        btnOuvrir.setActionCommand("ouvrir");
        btnOuvrir.setToolTipText("Ouvre une partie sp\u00E9cifique");
        btnValider = new JButton("Valider");
        btnValider.setVerticalTextPosition(AbstractButton.CENTER);
        btnValider.setHorizontalTextPosition(AbstractButton.CENTER);
        btnValider.setMnemonic(KeyEvent.VK_V);
        btnValider.setActionCommand("valider");
        btnValider.setToolTipText("Valide la grille en cours");
        btnValider.setEnabled(false);
        btnAide = new JButton("Aide");
        btnAide.setVerticalTextPosition(AbstractButton.CENTER);
        btnAide.setHorizontalTextPosition(AbstractButton.CENTER);
        btnAide.setMnemonic(KeyEvent.VK_A);
        btnAide.setActionCommand("aide");
        btnAide.setToolTipText("Donne une aide : r\u00E9soud une case");
        btnAide.setEnabled(false);
        btnSolution = new JButton("Solution");
        btnSolution.setVerticalTextPosition(AbstractButton.CENTER);
        btnSolution.setHorizontalTextPosition(AbstractButton.CENTER);
        btnSolution.setMnemonic(KeyEvent.VK_S);
        btnSolution.setActionCommand("solution");
        btnSolution.setToolTipText("Donne la solution de la grille en cours");
        btnSolution.setEnabled(false);
        btnQuitter = new JButton("Quitter");
        btnQuitter.setVerticalTextPosition(AbstractButton.CENTER);
        btnQuitter.setHorizontalTextPosition(AbstractButton.CENTER);
        btnQuitter.setMnemonic(KeyEvent.VK_Q);
        btnQuitter.setActionCommand("quitter");
        btnQuitter.setToolTipText("Quitte la partie");
        jeuPanel = new JPanel();
        jeuPanel.setPreferredSize(new Dimension(400, 300));
        jeuPanel.setBackground(Color.white);
        jeuPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        jeuPanel.setLayout(new GridLayout(3, 3));
        // Ajoute des ActionListeners aux boutons
        btnOuvrir.addActionListener(this);
        btnValider.addActionListener(this);
        btnAide.addActionListener(this);
        btnSolution.addActionListener(this);
        btnQuitter.addActionListener(this);
        // Ajout des composants au container, en utilisant un GridBagLayout et des constraintes
        ct.weightx = 100;
        ct.weighty = 100;
        ct.gridx = 0;
        ct.gridy = 0;
        ct.gridwidth = 1;
        ct.gridheight = 1;
        ct.insets = new Insets(10, 10, 10, 10);
        ct.fill = GridBagConstraints.BOTH;
        gb.setConstraints(btnOuvrir, ct);
        add(btnOuvrir, ct);
        ct.gridy = 1;
        gb.setConstraints(btnValider, ct);
        add(btnValider, ct);
        ct.gridy = 2;
        gb.setConstraints(btnAide, ct);
        add(btnAide, ct);
        ct.gridy = 3;
        gb.setConstraints(btnSolution, ct);
        add(btnSolution, ct);
        ct.gridy = 4;
        gb.setConstraints(btnQuitter, ct);
        add(btnQuitter, ct);
        ct.gridx = 1;
        ct.gridy = 0;
        ct.gridwidth = 3;
        ct.gridheight = 5;
        gb.setConstraints(jeuPanel, ct);
        add(jeuPanel, ct);
    }

    /**
     * Ouvre le GUI de selection de partie et, si choix valide, ouvre la partie + prepare le plan de jeu
     */
    private void ouvrePartieEtPreparePlanDeJeu() {
        JeuProprietes jp = JeuProprietes.getJeuProprietes();
        String positionsUser = "";
        String positionsSolution = "";
        String tmpLine = "";
        int trouveNLignes = 0;
        
        // si premiere fois, construire la boite de dialogue
        if(dialog == null)
            dialog = new GUIOpenScreen(jp);
        // affiche la boite de dialogue et recupere la partie selectionnee (0 = annuler)
        partieSelectionnee = dialog.showDialog(GUIPlanDeJeux.this, "Choix de la partie");
        if(partieSelectionnee != 0) {
            int intUser, intSolution; // variables temporaires utilisees pour le choix de ligne et l'extraction d'entiers
            intSolution = 2 * partieSelectionnee;
            intUser = intSolution - 1;
            try {
                LineNumberReader lnr = new LineNumberReader(new FileReader(jp.getFichierPaires())); // TODO changer pour charger dans le jar ?
                while((tmpLine = lnr.readLine()) != null && trouveNLignes < 2) {
                    if(lnr.getLineNumber() == intUser) {
                        positionsUser = tmpLine;
                        trouveNLignes++;
                    }
                    if(lnr.getLineNumber() == intSolution) {
                        positionsSolution = tmpLine;
                        trouveNLignes++;
                    }
                } // fin du while pour trouver les 2 lignes de Sudoku
                // attribution des objets Nombre aux objets BoiteFeuille
                for(int i = 0; i < 81; i++) {
                    intUser = Integer.parseInt(positionsUser.substring(i, i+1));
                    intSolution = Integer.parseInt(positionsSolution.substring(i, i+1));
                    bf[i] = new BoiteFeuille(NombreFactory.getNombre(intSolution), (intUser == intSolution));
                }
                // attribution des objets BoiteFeuille aux objets BoiteComposite ; TODO y'a pas moyen de faire + elegant ? 
                ajouteFeuillesAComposite(1, 1);
                ajouteFeuillesAComposite(2, 4);
                ajouteFeuillesAComposite(3, 7);
                ajouteFeuillesAComposite(4, 28);
                ajouteFeuillesAComposite(5, 31);
                ajouteFeuillesAComposite(6, 34);
                ajouteFeuillesAComposite(7, 55);
                ajouteFeuillesAComposite(8, 58);
                ajouteFeuillesAComposite(9, 61);
                // attribution des objets BoiteComposite aux objets GUIBoiteComposite qui vont les dessiner pratiquement
                for(int i = 0; i < 9; i++) {
                    GUIBoiteComposite gbc = new GUIBoiteComposite(bc[i]);
                    jeuPanel.add(gbc);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            btnValider.setEnabled(true);
            btnAide.setEnabled(true);
            btnSolution.setEnabled(true);
            jeuPanel.revalidate(); // Yeee ! cette simple ligne permet d'afficher les GUIBoiteComposite apres creation (2 jours pour la trouver !)
        }
        else { // partieSelectionnee == 0
            btnValider.setEnabled(false);
            btnAide.setEnabled(false);
            btnSolution.setEnabled(false);
        }
    }
    
    /**
     * Ajoute 9 objets BoiteSeuille a un objet BoiteComposite
     * @param   int l'indice (entier) de la BoiteComposite (en notation normale, debutant par 1)
     * @param   int l'indice (entier) de la premiere BoiteFeuille a ajouter (en notation normale, debutant par 1)
     */
    private void ajouteFeuillesAComposite(int nComposite, int debut) {
        nComposite--;
        debut--;
        bc[nComposite] = new BoiteComposite();
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                bc[nComposite].addBoiteFeuille(bf[debut]);
                debut++;
            }
            debut += 6;
        }
    }
    
    /**
     * Implementation de actionPerformed requis par l'interface ActionListener implementee
     */
    public void actionPerformed(ActionEvent e) {
        if("ouvrir".equals(e.getActionCommand())) { // affiche l'objet de menu de selection de partie
            jeuPanel.removeAll(); // sert surtout si on ouvre une 2eme grille : efface la 1ere grille
            ouvrePartieEtPreparePlanDeJeu();
        }
        if("valider".equals(e.getActionCommand())) {
            boolean isValid = true; // 1 seul false et tout est faux
            int tmpCompteur = 0;
            while(isValid && tmpCompteur < 9) {
                isValid = bc[tmpCompteur].isValid();
                tmpCompteur++;
            }
            if(isValid)
                JOptionPane.showMessageDialog(null, "F\u00E9licitations ! Votre grille est valide !", "Grille valide !", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "A\u00EFe ! Votre grille n'est PAS valide !", "Non valide", JOptionPane.WARNING_MESSAGE);
        }
        if("aide".equals(e.getActionCommand())) {
            // ennuyeux mais on doit reparcourir tous les elements du jeuPanel, trouver 1 instance de GUIBoiteComposite et donner 1 solution
            // TODO trouver pourquoi le programme se gele a un moment donne, quand on demande trop d'aide (faut pas trop tricher ?)
            Component comp[] = jeuPanel.getComponents();            
            ArrayList<GUIBoiteComposite> gbc = new ArrayList<GUIBoiteComposite>();
            boolean solutionDonnee = false;
            
            for(int i = 0; i < comp.length; i++) {
                if(comp[i] instanceof GUIBoiteComposite)
                    gbc.add((GUIBoiteComposite)comp[i]);
            }
            
            if(!gbc.isEmpty()) { // ne fait rien si y'a pas de GUIBoiteComposite ici !
                int compteur = 0;
                while((!solutionDonnee) && compteur < gbc.size()) {
                    int auHasard = (int)(Math.random() * gbc.size());
                    if(gbc.get(auHasard).isEditable()) {
                        gbc.get(auHasard).donne1Solution();
                        solutionDonnee = true;
                    }
                    compteur++;
                }
                solutionDonnee = true;
            }
        }
        if("solution".equals(e.getActionCommand())) {
            // ennuyeux mais on doit reparcourir tous les elements du jeuPanel, trouver les instances de GUIBoiteComposite et donner les solutions
            Component comp[] = jeuPanel.getComponents();
            for(int i = 0; i < comp.length; i++) {
                    if(comp[i] instanceof GUIBoiteComposite)
                        ((GUIBoiteComposite)comp[i]).donneSolution();
                }
        }
        if("quitter".equals(e.getActionCommand()))
            System.exit(0); // quitte le programme
    }

}
