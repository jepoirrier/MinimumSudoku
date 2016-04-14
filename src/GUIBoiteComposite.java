/*
 * GUIBoiteComposite.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 4 juin 2007
 *
 */

package minimumsudoku;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Classe d'interface utilisateur pour l'affichage d'un objet BoiteComposite
 * @author Jean-Etienne
 */
public class GUIBoiteComposite extends JPanel {
    
    private BoiteComposite bc;
    private ArrayList<BoiteInterface> lesBoites;
    
    /** Creates a new instance of GUIBoiteComposite */
    public GUIBoiteComposite(BoiteComposite b) {
        bc = b;
        lesBoites = bc.getBoites();
        
        Border bordure = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(bordure);
        
        setLayout(new GridLayout(3, 3));
        
        Iterator it = lesBoites.iterator();
        while(it.hasNext()) {
            BoiteInterface bc = (BoiteInterface)it.next();
            add(new GUIBoiteFeuille((BoiteFeuille)bc));
        }
    }

    /**
     * Demande a toutes les cellules d'afficher leur solution
     * (en descendant au niveau des feuilles ou en demandant aux autres composites eventuels)
     */
    public void donneSolution() {
        // ennuyeux mais on doit reparcourir tous les elements, trouver les instance de GUIBoite* et donner les solutions
        Component comp[] = this.getComponents();
        for(int i = 0; i < comp.length; i++) {
            if(comp[i] instanceof GUIBoiteFeuille)
                ((GUIBoiteFeuille)comp[i]).donneSolution();
            if(comp[i] instanceof GUIBoiteComposite)
                ((GUIBoiteComposite)comp[i]).donneSolution();
        }
    }

    /**
     * Demande a UNE cellule d'afficher sa solution
     * (ici ne descend pas s'il y a d'autres composite (TODO?)
     */
    public void donne1Solution() {
        // ennuyeux mais on doit reparcourir tous les elements, trouver les instance de GUIBoiteFeuille et donner une solution
        Component comp[] = this.getComponents();
        ArrayList<GUIBoiteFeuille> gbf = new ArrayList<GUIBoiteFeuille>();
        boolean solutionDonnee = false;
        int visitedGUIBoiteFeuille = 0;
        
        for(int i = 0; i < comp.length; i++) {
            if(comp[i] instanceof GUIBoiteFeuille)
                gbf.add((GUIBoiteFeuille)comp[i]);
        }
        
        if(!gbf.isEmpty()) { // ne fait rien si y'a pas de GUIBoiteFeuille ici !
            while((!solutionDonnee) || visitedGUIBoiteFeuille >= 9) { // ne sert a rien de depasser 9 boites deja jouees
                int auHasard = (int)(Math.random() * gbf.size());
                if(gbf.get(auHasard).getNombreJoueur() == null) { // ne donne pas de hint pour une case deja jouee !
                    gbf.get(auHasard).donneSolution();
                    solutionDonnee = true;
                }
                visitedGUIBoiteFeuille++;
            }
        }
    }
    
    /**
     * Determine si les instances de BoiteFeuille contenues sont editables
     * @return  boolean true si au moins une instance de BoiteFeuille est editable, false si toutes non editables
     */
    public boolean isEditable() {
        // ennuyeux mais on doit reparcourir tous les elements, trouver les instance de GUIBoite* et voir si isEditable()
        Component comp[] = this.getComponents();
        ArrayList<GUIBoiteFeuille> gbf = new ArrayList<GUIBoiteFeuille>();
        boolean drapeauEditable = false;
        
        for(int i = 0; i < comp.length; i++) {
            if(comp[i] instanceof GUIBoiteFeuille) {
                if(((GUIBoiteFeuille)comp[i]).isEditable())
                    drapeauEditable = true;
            }
            if(comp[i] instanceof GUIBoiteComposite) {
                if(((GUIBoiteComposite)comp[i]).isEditable())
                    drapeauEditable = true;
            }
        }
        return drapeauEditable;
    }
    
}
