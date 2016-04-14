/*
 * BoiteComposite.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 7 juin 2007
 */

package minimumsudoku;

import java.util.*;

/**
 * Composition d'instances de BoiteFeuille
 * @author Jean-Etienne
 */
public class BoiteComposite implements BoiteInterface {
    
    private ArrayList<BoiteInterface> lesBoites;
    
    /** Cree une nouvelle instance de BoiteComposite */
    public BoiteComposite() {
        lesBoites = new ArrayList();
    }
    
    /**
     * Ajoute une instance de BoiteFeuille
     * @param   bf  objet de type BoiteFeuille a ajouter dans la BoiteComposite
     */
    public void addBoiteFeuille(BoiteFeuille bf) {
        lesBoites.add(bf);
    }
    
    /**
     * Ajoute une instance de BoiteComposite
     * @param   bc  objet de type BoiteComposite a ajouter dans la BoiteComposite
     */
    public void addBoiteComposite(BoiteComposite bc) {
        lesBoites.add(bc);
    }
    
    /**
     * Retourne l'ensemble des boites composant la BoiteComposite
     * @return      une liste (ArrayList) d'instances de BoiteInterface
     */
    public ArrayList getBoites() {
        return lesBoites;
    }
    
    /**
     * Retourne une chaine representant le contenu = les nombres sous forme d'entiers
     * (surcharge de methode de java.lang.Object)
     * @return  String  une chaine representant le contenu d'un objet BoiteComposite
     */
    public String toString() {
        String s = "";
        Iterator it = lesBoites.iterator();
        while(it.hasNext()) {
            BoiteInterface bc = (BoiteInterface)it.next();
            s = s + bc.toString();
        }
        return s;
    }

    /**
     * Determine si l'objet est editable (accepterait encore un nombre supplementaire) ou pas
     * (implementation de methode d'interface BoiteInterface)
     * @return  boolean true si l'instance est editable (= au moins 1 BoiteFeuille editable), false si non editable
     */
    public boolean isEditable() {
        boolean drapeauEditable = false; // par defaut false ainsi le moindre true rend le tout encore editable
        Iterator it = lesBoites.iterator();
        while(it.hasNext()) {
            BoiteInterface bc = (BoiteInterface)it.next();
            if(bc.isEditable())
                drapeauEditable = true;
        }
        return drapeauEditable;
    }

    /**
     * Determine si l'objet est valide (le NombreOriginal a la meme valeur que le NombreJoueur) ou pas
     * (implementation de methode d'interface BoiteInterface)
     * @return  boolean true si l'instance est valide (= toutes les BoiteFeuille valides), false si non valide
     */
    public boolean isValid() {
        boolean drapeauValide = true; // par defaut valide ainsi le moindre false rend le tout NON valide !
        Iterator it = lesBoites.iterator();
        while(it.hasNext()) {
            BoiteInterface bc = (BoiteInterface)it.next();
            if(!bc.isValid())
                drapeauValide = false;
        }
        return drapeauValide;
    }
    
}
