/*
 * BoiteInterface.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 30 mai 2007
 */

package minimumsudoku;

/**
 * Interface pour les differentes boites dans ce MinimumSudoku
 * @author Jean-Etienne
 */
public interface BoiteInterface {
    
    /**
     * Verifie si la boite est editable (le joueur peut entrer un nombre) ou pas
     * @return      true si la boite est editable, false si elle ne l'est pas
     */
    public boolean isEditable();
    
    /**
     * Verifie si la boite est valide (solution du joueur = solution officielle) ou pas
     * @return     true si la boite est valide, false si elle ne l'est pas
     */
    public boolean isValid();
}
