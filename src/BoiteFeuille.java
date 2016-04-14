/*
 * BoiteFeuille.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 7 juin 2007
 */

package minimumsudoku;

/**
 * Classe BoiteFeuille = case contenant un nombre (2 instance de Nombre) dans MinimumSudoku
 * @author Jean-Etienne
 */
public class BoiteFeuille implements BoiteInterface {
    
    private Nombre nombreOriginal; // nombre original dans la boite feuille = la solution (en passant)
    private Nombre nombreJoueur;   // nombre entre par le joueur dans la case
    
    /**
     * Cree une nouvelle instance de BoiteFeuille
     * @param   no  Nombre original dans la boite feuille
     * @param   display true si le nombre doit etre affiche (case deja remplie) ou false si attend le joueur
     */
    public BoiteFeuille(Nombre no, boolean display) {
        nombreOriginal = no;
        if(display)
            nombreJoueur = no;
    }
    
    /**
     * Retourne le nombre entre par le joueur dans la case
     * @return      le Nombre du joueur
     */
    public Nombre getNombreJoueur() {
        return nombreJoueur;
    }
    
    /**
     * Specifie le nombre entre par le joueur dans la case
     * @param   nj  Nombre du joueur
     */
    public void setNombreJoueur(Nombre nj) {
        nombreJoueur = nj;
    }
    
    /**
     * Donne la solution, cad. que les 2 Nombres ont la meme valeur
     * Attention : efface les nombreJoueurs deja attribues !
     */
    public void donneSolution() {
        setNombreJoueur(nombreOriginal);
    }
    
    /**
     * Retourne une chaine representant le contenu = ses 2 nombres sous forme d'entiers + infos supplementaires
     * (surcharge de methode de java.lang.Object)
     * @return  String  une chaine representant le contenu d'un objet BoiteFeuille
     */
    public String toString() {
        String s = "Nombre Original = " + nombreOriginal.toString() + " ; nombre Joueur = ";
        if(nombreJoueur != null)
            s = s + nombreJoueur.toString();
        else
            s = s + "(null)";
        if(isEditable())
            s = s + " ; editable";
        else
            s = s + " ; non editable";
        return s;
    }

    /**
     * Determine si l'objet est editable (accepterait un NombreJoueur) ou pas
     * (implementation de methode d'interface BoiteInterface)
     * @return  boolean true si l'instance est editable, false si non editable
     */
    public boolean isEditable() {
        if(nombreJoueur != null)
            return false;
        else
            return true;
    }

    /**
     * Determine si l'objet est valide (le NombreOriginal a la meme valeur que le NombreJoueur) ou pas
     * (implementation de methode d'interface BoiteInterface)
     * @return  boolean true si l'instance est valide, false si non valide
     */
    public boolean isValid() {
        if(nombreOriginal.isEqual(nombreJoueur))
            return true;
        else
            return false;
    }
}
