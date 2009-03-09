/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author otto
 */
public class Options {

    private static boolean computeBackwards = false;
    //Abfrage ob vorwärts oder rückwärts gerechnet werden soll, false = vorwärts

    private static double throwingRange = 0.0;
    //gewünschte Wurfweite beim Rückwärtsrechnen, geht nur, wenn die Erdkrümmung vernachlässigt wird (simple gravity!)

    private static boolean computeDensity = false;
    //bei Bedarf Abschalten der Dichtekorrektur, spart Rechenkapazität, Vergleich, ob vernachlässigbar


    //getters:

public static boolean getComputeBackwards(){
   return computeBackwards;
  }

public static double getThrowingRange(){
   return throwingRange;
  }

public static boolean getComputeDensity(){
   return computeDensity;
  }


  //setters:

public static void setComputeBackwards(boolean computeBackwards){
    Options.computeBackwards = computeBackwards;
  }

public static void setThrowingRange(double throwingRange){
   Options.throwingRange = throwingRange;
  }

public static void setComputeDensity(boolean computeDensity){
  Options.computeDensity = computeDensity;
  }


public static double computeDensity(physics.MovingBody tempMovingBody, physics.Setting setting){
    double rho;
    rho = setting.getRho()*Math.exp((-setting.getRho()/101300) * physics.Constants.g * tempMovingBody.getLocation(setting).getH());
                    //Anmerkung: gilt in der Form nur für Erdatmosphäre, Druck auf Höhe Null muss passend gewählt werden
                    //durch den Benutzer muss der Dichtewert auf Höhe Null eingegeben werden
   return rho;
}

}
