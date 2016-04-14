/*
 * Nombre.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 30 mai 2007
 */

package minimumsudoku;

/**
 * Classe de stockage d'une valeur entiere
 * @author Jean-Etienne
 */
public class Nombre {
    
    private int valeur;
    
    /**
     * Cree une nouvelle instance de Nombre
     * @param   v   un entier a stocker dans le Nombre
     */
    public Nombre(int v) {
        setValeur(v);
    }
    
    /**
     * Definit la valeur de case Sudoku
     * @param   v   un entier a stocker dans le Nombre
     */
    public void setValeur(int v) {
        valeur = v;
    }
    
    /**
     * Recupere la valeur de l'entier
     * @return  int un entier stocke dans le Nombre
     */
    public int getValeur() {
        return valeur;
    }
    
    /**
     * Verifie si le Nombre est egal a un autre nombre "invite" (ie. leurs entiers sont egaux)
     * @param   ni  Nombre invite = nombre avec lequel la comparaison est faite
     * @return  boolean true si les 2 nombres sont egaux, false si les 2 nombres ne sont pas les memes
     */
    public boolean isEqual(Nombre ni) {
        if(this == null || ni == null) // cas ou le nombre du joueur pas encore entre (+ securite sur this)
            return false;
        else {
            if(this.getValeur() == ni.getValeur())
                return true;
            else
                return false;
        }
    }

    /**
     * Retourne la valeur du nombre stocke sous forme de chaine
     * (surcharge de methode de java.lang.Object)
     * @return  String  une chaine contenant la valeur entiere stockee
     */
    public String toString() {
        return Integer.toString(valeur);
    }
}
