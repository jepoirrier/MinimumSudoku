/*
 * NombreFactory.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 30 mai 2007
 *
 */

package minimumsudoku;

import java.util.*; // pour Map et HashMap

/**
 * Construit les NombreFlyweight et repond a la demande
 * @author Jean-Etienne
 */
public class NombreFactory {
    
    private static Map lesNombres = new HashMap(); // une table de hash n'est pas un canon pour tuer une mouche dans ce cas-ci ?
    
    /**
     * Cree une nouvelle instance de NombreFactory
     * (ne fait rien)
     */
    public NombreFactory() {
    }
    
    /**
     * Retourne une instance de Nombre cree par la "factory"
     * @param   i   un entier a stocker dans cette instance de Nombre
     * @return  Nombre  une instance de Nombre contenant l'entier donne en parametre
     */
    public static Nombre getNombre(int i) {
        Nombre nbre = (Nombre)lesNombres.get(i);
        if(nbre == null) {
            nbre = new Nombre(i);
            lesNombres.put(i, nbre);
        }
        return nbre;
    }
    
}
