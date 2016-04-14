/*
 * JeuProprietes.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 7 juin 2007
 *
 */

package minimumsudoku;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Properties;

/**
 * Utilise pour aller chercher/fournir les proprietes du jeu
 * TODO utiliser un fichier properties (mais pas dans cahier des charges, meme si c'est + elegant)
 * @author Jean-Etienne
 */
public class JeuProprietes {
    
    private static JeuProprietes ref; // la reference statique pour le motif singleton
    
    private String fichierpaires;
    //private String fichierproprietes = "minimumsudoku.properties";
    private int maximumjeux;
    private boolean PermetPlusieursEdits;
    
    /**
     * Cree une nouvelle instance de JeuProprietes
     * Attention : motif de conception Singleton sinon appelle trop souvent par GUIBoiteFeuille lors de la Solution (et tous ces acces au fichier de 6Mo, c'est beaucoup !)
     */
    private JeuProprietes() {
//        TODO ouvre le fichier properties
//        Properties props = new Properties();
//        FileInputStream propsfile = null;
//        try {
//            propsfile = new FileInputStream(fichierproprietes); // where is the properties file
//            props.load(propsfile); // now build the properties object from the file
//            propsfile.close(); // and do not forget to close the properties file
//        } catch (Exception ex) { ex.printStackTrace(); }
        
//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream(fichierproprietes));
//        }
//        catch (IOException e) { e.printStackTrace(); }
//
//        setFichierPaires(properties.getProperty("fichierpaires"));
//        setMaximumJeux(Integer.getInteger(properties.getProperty("maximumjeux")));
//        setMultipleEditAllowance(Boolean.getBoolean(properties.getProperty(permetplusieursedits)))
        setFichierPaires("minimumsudoku.txt");
        setMaximumJeux();
        setPermetPlusieursEdits(true);
    }
    
    /**
     * Seul point d'entree dans l'objet instance (singleton) de JeuProprietes ; voir http://www.beginner-java-tutorial.com/singleton.html 
     * (la methode est synchronisee pour prevenir les problemes de threads, bien que je n'en utilise pas)
     * @return  JeuProprietes   une instance de JeuProprietes (la seule dans tous le jeu)
     */ 
    public static synchronized JeuProprietes getJeuProprietes() {
        if(ref == null)
            ref = new JeuProprietes();
        return ref;
    }
    
    /**
     * Definit le nom du fichier de paires pour jeux
     * @param   f   la chaine contenant le nom du fichier
     */
    private void setFichierPaires(String f) {
        fichierpaires = f;
    }
    
    /**
     * Donne le nom du fichier de paires de parties
     * @return  String  chaine contenant le nom du fichier de paires de parties
     */
    public String getFichierPaires() {
        return fichierpaires;
    }
    
    /**
     * Definit le nombre maximum de jeux
     * @param   m   un entier definissant le nombre maximum de jeux
     */
    private void setMaximumJeux() {
        String tmpLine;
        int m = 0;
        
        try {
            LineNumberReader lnr = new LineNumberReader(new FileReader(fichierpaires)); // TODO changer pour charger dans le jar ?
            while((tmpLine = lnr.readLine()) != null) {
                m++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        maximumjeux = (int)Math.floor(m / 2); // en cas de nombre impair de lignes, retourne le plus grand entier inferieur
    }
    
    /**
     * Donne le nombre maximal de jeux dans le fichier
     * @return  int entier comprenant le nombre maximal de jeux dans le fichier
     */
    public int getMaximumJeux() {
        return maximumjeux;
    }
    
    /**
     * Definit si le jeu permet plusieurs editions d'une meme case
     * Dans les regles officielles, c'est interdit !
     * @param   a   booleen true si permet plusieurs editions ; false si respecte les regles
     */
    private void setPermetPlusieursEdits(boolean a) {
        PermetPlusieursEdits = a;
    }
    
    /**
     * Repond si le jeu permet plusieurs editions d'une meme case
     * Dans les regles officielles, c'est interdit
     * @return  boolean true si permet plusieurs editions ; false si respecte les regles
     */
    public boolean isPermetPlusieursEdits() {
        return PermetPlusieursEdits;
    }
    
    /**
     * Retourne une chaine representant toutes les proprietes
     * (surcharge de methode de java.lang.Object)
     * @return  String  une chaine representant toutes les proprietes d'un objet JeuProprietes
     */
    public String toString() {
        return("Fichier de paires = " + fichierpaires + " ; nombre maximum de jeux = " + maximumjeux);
    }
    
    /**
     * Empeche toute velleite de cloner l'objet singleton (au cas ou ...) ; voir http://www.javacoffeebreak.com/articles/designpatterns/index.html 
     * (surcharge de methode de java.lang.Object)
     * @throws  CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
