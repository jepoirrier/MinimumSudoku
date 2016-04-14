/*
 * Main.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 31 mai 2007
 *
 */

package minimumsudoku;

import javax.swing.*; // pour JFrame

/**
 * Classe de depart de MinimumSudoku
 * En charge de l'initialisation et du lancement des objets
 * @author Jean-Etienne
 */
public class Main {
    
    /**
     * Cree une nouvelle instance de Main
     * (ne fait rien)
     */
    public Main() {
    }
    
    /**
     * Fonction de lancement de l'application
     * @param   args    les arguments de la ligne de commande (n'en tient pas compte ici)
     */
    public static void main(String[] args) {
        // Prevoit un job pour le thread de repartition des evenements :
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                creeEtMontreLeGUI(); 
            }
        });
    }
    
    /**
     * Fonction de creation d'une instance de GUIPlanDeJeux et son affichage
     * (pourrait etre dans la fonction main() mais ca surcharge un peu :-))
     */
    private static void creeEtMontreLeGUI() {
        // Cree et definit la fenetre
        JFrame frame = new JFrame("Minimum Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Cree et definit le panneau de contenu
        GUIPlanDeJeux gpdj = new GUIPlanDeJeux();
        gpdj.setOpaque(true);
        frame.setContentPane(gpdj);

        //Display the window.
        frame.pack();
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
    
}
