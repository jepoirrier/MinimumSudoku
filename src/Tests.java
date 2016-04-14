/*
 * Tests.java
 *
 * Creation : 30 mai 2007
 * Dernieres modifications : 5 juin 2007
 *
 */

package minimumsudoku;

/**
 * S'occupe d'un certain nombre de tests, surtout sur la partie "moteur" (pas GUI)
 * (pas le courage d'ecrire des JUnits completes)
 * @author Jean-Etienne
 */
public class Tests {
    
    /**
     * Cree une nouvelle instance de Tests
     */
    public Tests() {
    }
    
    /**
     * Test pour le pattern Flyweight (creation de Nombres)
     */
    public void testePatternFlyweight() {
        System.out.println("*** Test du pattern Flyweight ...");
        Nombre un = NombreFactory.getNombre(1);
        Nombre trois = NombreFactory.getNombre(3);
        System.out.println(un.toString());
        System.out.println(trois.toString());
        Nombre unbis = NombreFactory.getNombre(1);
        System.out.println(unbis.toString());
    }
    
    /**
     * Test pour le pattern Composite (creation des Boite et BoiteComposite)
     */
    public void testePatternComposite() {
        System.out.println("*** Test du pattern Composite ...");
        BoiteComposite pdj = new BoiteComposite();
        
        BoiteComposite topGauche = new BoiteComposite();
        BoiteComposite topMilieu = new BoiteComposite();
        
        BoiteFeuille bf1 = new BoiteFeuille(NombreFactory.getNombre(1), false);
        BoiteFeuille bf2 = new BoiteFeuille(NombreFactory.getNombre(2), false);
        BoiteFeuille bf3 = new BoiteFeuille(NombreFactory.getNombre(1), false);
        BoiteFeuille bf4 = new BoiteFeuille(NombreFactory.getNombre(2), false);
        
        bf1.setNombreJoueur(NombreFactory.getNombre(1));
        bf2.setNombreJoueur(NombreFactory.getNombre(2));
        bf3.setNombreJoueur(NombreFactory.getNombre(1));
//        bf4.setNombreJoueur(NombreFactory.getNombre(2));
        
        topGauche.addBoiteFeuille(bf1);
        topGauche.addBoiteFeuille(bf2);
        topMilieu.addBoiteFeuille(bf3);
        topMilieu.addBoiteFeuille(bf4);
        
        pdj.addBoiteComposite(topGauche);
        pdj.addBoiteComposite(topMilieu);
        
        if(pdj.isEditable())
            System.out.println("Plan de jeux editable");
        else
            System.out.println("Plan de jeux non editable");
        
        if(pdj.isValid())
            System.out.println("Plan de jeux valide");
        else
            System.out.println("Plan de jeux non valide");
    }
    
    /**
     * Test pour la lecture des proprietes
     */
    public void testeLectureProprietes() {
        JeuProprietes jp = JeuProprietes.getJeuProprietes();
        System.out.println(jp.toString());
    }
}
