/*
 * GUIOpenScreen.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 31 mai 2007
 */

package minimumsudoku;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Classe d'interface utilisateur pour l'affichage de la boite de selection du numero de partie
 * @author Jean-Etienne
 */
public class GUIOpenScreen extends JPanel implements ActionListener {
    
    private boolean ok;
    private int selection = 0;
    private int maximum = 1; // nombre maximum de jeux disponibles (valeur par defaut en attendant le constructeur)
    private FileInputStream fin;
    private Properties props;
    private JButton btnSelectionner, btnHasard, btnAnnuler;
    private JDialog dialog;
    private JLabel lblName;
    private JSlider sliderChoix;
    private JPanel panel4btn;
    
    /**
     * Cree une nouvelle instance de GUIOpenScreen
     * @param   jp  une instance de JeuProprietes (pour acceder aux proprietes du jeu !)
     */
    public GUIOpenScreen(JeuProprietes jp) {
        
        maximum = jp.getMaximumJeux();
        
        // un peu de layout d'abord
        GridBagLayout gb = new GridBagLayout();
        setLayout(gb);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 100;
        gbc.weighty = 100;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // un label contenant le nom du machin a ajouter
        lblName = new JLabel("Glisser pour choisir la partie :");
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.LINE_START;
        gb.setConstraints(lblName, gbc);
        add(lblName);
        
        // et un slider
        sliderChoix = new JSlider(0, maximum, 1);
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gb.setConstraints(sliderChoix, gbc);
        sliderChoix.addChangeListener(new ChangeListener() { // petit listener pour afficher la valeur en cours
            public void stateChanged(ChangeEvent evt) { sliderChoixChanger(evt); } });
        add(sliderChoix);
        
        // un panel pour en dessous
        panel4btn = new JPanel();
        
        btnSelectionner = new JButton("S\u00E9lectionner");
        btnSelectionner.setVerticalAlignment(AbstractButton.CENTER);
        btnSelectionner.setHorizontalTextPosition(AbstractButton.CENTER);
        btnSelectionner.setMnemonic(KeyEvent.VK_A);
        btnSelectionner.setActionCommand("selectionner");
        btnSelectionner.setToolTipText("S\u00E9lectionner la partie");
        btnSelectionner.setEnabled(true);
        btnSelectionner.addActionListener(this); // Ajout d'un action listener a ce bouton
        panel4btn.add(btnSelectionner);
        
        btnHasard = new JButton("Au hasard");
        btnHasard.setVerticalTextPosition(AbstractButton.CENTER);
        btnHasard.setHorizontalTextPosition(AbstractButton.CENTER);
        btnHasard.setMnemonic(KeyEvent.VK_H);
        btnHasard.setActionCommand("hasard");
        btnHasard.setToolTipText("Ouvre une partie au hasard");
        btnHasard.setEnabled(true);
        btnHasard.addActionListener(this); // Ajout d'un action listener a ce bouton
        panel4btn.add(btnHasard);
        
        btnAnnuler = new JButton("Annuler");
        btnAnnuler.setVerticalAlignment(AbstractButton.CENTER);
        btnAnnuler.setHorizontalTextPosition(AbstractButton.CENTER);
        btnAnnuler.setMnemonic(KeyEvent.VK_U);
        btnAnnuler.setActionCommand("annuler");
        btnAnnuler.setToolTipText("Annuler la s\u00E9lection");
        btnAnnuler.setEnabled(true);
        btnAnnuler.addActionListener(this); // Ajout d'un action listener a ce bouton
        panel4btn.add(btnAnnuler);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.LINE_END;
        gb.setConstraints(panel4btn, gbc);
        add(panel4btn);
    }
    
    /**
     * Retourne la selection choisie dans l'interface (ou 0 si pas de selection)
     * @return  int le numero de la selection choisie (ou 0 si pas de selection)
     */
    public int getSelection() {
        return selection;
    }
    
    /**
     * Change le label et la valeur selectionnee pour tout changement de slider
     * @param   ChangeEvent un evenement de changement du slider
     */
    private void sliderChoixChanger(ChangeEvent evt) {
        selection = sliderChoix.getValue();
        if(selection > 0)
            lblName.setText("Partie choisie : " + selection);
        else
            lblName.setText("(Aucune partie choisie)");
    }
    
    /**
     * Affiche le panneau de selection de la partie dans une boite de dialogue
     * @param   parent  un composant dans le cadre proprietaire ou null
     * @param   title   le titre de la boite de dialogue
     */
    public int showDialog(Component parent, String title) {
        ok = false;
        
        // Localise le cadre proprietaire
        Frame proprio = null;
        if(parent instanceof Frame)
            proprio = (Frame) parent;
        else
            proprio = (Frame)SwingUtilities.getAncestorOfClass(Frame.class, parent);
        
        // Si 1ere fois ou si proprio a change, cree nouvelle boite de dialogue
        if(dialog == null || dialog.getOwner() != proprio) {
            dialog = new JDialog(proprio, true);
            dialog.add(this);
            dialog.getRootPane().setDefaultButton(btnSelectionner);
            dialog.pack();
            dialog.setLocation(150, 150);
        }
        
        // definir le titre et afficher la boite de dialogue
        dialog.setTitle(title);
        dialog.setVisible(true);
        return selection;
    }

    public void actionPerformed(ActionEvent e) {
        if ("selectionner".equals(e.getActionCommand())) {
            // ferme juste, puisque la selection devrait deja etre faite
            (SwingUtilities.getWindowAncestor(this)).setVisible(false); // Maniere simple de fermer la fenetre de dialogue ! Elegant !
        }
        if ("hasard".equals(e.getActionCommand())) {
            Random generateur = new Random();
            selection = generateur.nextInt(maximum) + 1;
            sliderChoix.setValue(selection);
        }
        if ("annuler".equals(e.getActionCommand())) {
            selection = 0; // annule la modification eventuelle de la selection et ferme :
            (SwingUtilities.getWindowAncestor(this)).setVisible(false);
        }
    }
}
