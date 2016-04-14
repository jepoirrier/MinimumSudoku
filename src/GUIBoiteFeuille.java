/*
 * GUIBoiteFeuille.java
 *
 * Creation : 30 juin 2007
 * Dernieres modifications : 5 juin 2007
 *
 */

package minimumsudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe d'interface utilisateur pour l'affichage d'un objet BoiteFeuille
 * @author Jean-Etienne
 */
public class GUIBoiteFeuille extends JPanel implements FocusListener {
    
    private Nombre nj;
    private String message = "";
    private BoiteFeuille bf;
    
    private JFormattedTextField jftf;
    
    /**
     * Cree une nouvelle instance de GUIBoiteFeuille
     * @param   b  un objet de type BoiteFeuille dont l'affichage doit etre gere
     */
    public GUIBoiteFeuille(BoiteFeuille b) {
        bf = b;
        nj = (Nombre)bf.getNombreJoueur();
        
        this.setFocusable(true);
        
        // Construction du JFormattedTextField
        jftf = new JFormattedTextField(new DecimalFormat("#"));
        jftf.setPreferredSize(new Dimension(20, 20));
        jftf.setHorizontalAlignment(JTextField.CENTER);
        jftf.setFocusable(true);
        jftf.addFocusListener(this);
        if(nj != null) {
            jftf.setValue(nj.getValeur());
            jftf.setEditable(false);
            jftf.setForeground(Color.DARK_GRAY);
        } else
            jftf.setValue(null);
        // definit une classe interne de validation, de cette maniere, c'est effectue au niveau GUI, immediatement
        jftf.setInputVerifier(new InputVerifier() {
            public boolean verify(JComponent comp) {
                JFormattedTextField j = (JFormattedTextField)comp;
                boolean bienUnEntier = false;
                try {
                    int n = Integer.parseInt(j.getText());
                    bienUnEntier = (n >= 1 && n <= 9);
                } catch(NumberFormatException e) {
                    // si entre autre chose que nombre, simplement reviens a null
                    j.setValue(null);
                }
                // actions en cas de pas un entier : beep, selectionne l'entree et change pour rouge (sinon, reste blanc
                if(!bienUnEntier) {
                    comp.getToolkit().beep();
                    j.selectAll();
                    j.setBackground(Color.RED);
                } else {
                    j.setBackground(Color.WHITE); // TODO? prendre en fait la valeur du systeme d'exploitation (e.g. si l'utilisateur a des fonds de boite roses par defaut)
                }
                return bienUnEntier;
            }
        });
        add(jftf);
    }
    
    /**
     * Retourne l'instance de Nombre du joueur stocke dans cette GUIBoiteFeuille (donc, en fait, celui stocke dans la BoiteFeuille)
     * @return  Nombre  une instance de Nombre du joueur (peut etre null !)
     */
    public Nombre getNombreJoueur() {
        return(bf.getNombreJoueur());
    }
    
    /**
     * Permet au GUI de BoiteFeuille de definir le nombre entre par le joueur
     * Note : ce nombre est deja valide par l'InputVierifier de GUIBoiteFeuille
     * @param   n   nombre entier entre par le joueur
     */
    private void setNombreJoueur(int n) {
        bf.setNombreJoueur(NombreFactory.getNombre(n));
    }

    /**
     * Definit si le JFormattedTextField donne en parametre est editable ou pas en fonction des proprietes
     * Attention : c'est une fonction pour la GUI ; rien a voir ("informatiquement") avec la fonction isEditable()
     * @param   JFormattedTextField le JFormattedTextField qui devrait etre editable ou non
     */
    private void definitEditable(JFormattedTextField jftf) {
        JeuProprietes props = JeuProprietes.getJeuProprietes();
        if(!props.isPermetPlusieursEdits())
            jftf.setEditable(false);
    }
    
    /**
     * Determine si l'instance de BoiteFeuille contenue est editable
     * @return  boolean true si l'instance de BoiteFeuille est editable, false si non editable
     */
    public boolean isEditable() {
       return(bf.isEditable());
    }
      
    /**
     * Demande a la cellule d'afficher la solution
     * (doit donc definir le nombre normalement donne par le joueur)
     */
    public void donneSolution() {
        bf.donneSolution(); // donne la solution
        jftf.setEditable(true); // l'affiche
        jftf.setValue(bf.getNombreJoueur().getValeur());
        definitEditable(jftf);
    }
    
    /**
     * Implementation de la fonction focusGained obligatoire
     * (ne fait rien)
     * @param   e   un FocusEvent qui contient notamment le composant qui gagne le focus (a caster)
     */
    public void focusGained(FocusEvent e) {
    }

    /**
     * Implementation de la fonction focusLost obligatoire
     * Gere le nombre entre dans le JFormattedTextField de la GUIBoiteFeuille avant de passer le focus
     * @param   e   un FocusEvent qui contient notamment le composant qui perd le focus (a caster)
     */
    public void focusLost(FocusEvent e) {
        JFormattedTextField jt = (JFormattedTextField)e.getComponent();
        try {
            int val = 0;
            val = Integer.parseInt(jt.getText());
            if(val > 0 || val <= 9) {
                setNombreJoueur(val);
                definitEditable(jt);
            }
        } catch (NumberFormatException nfe) {}
    }
    
}