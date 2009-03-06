/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package physicsBAK;

/**
 *
 * @author otto
 */


import java.util.Vector;
import physics.*;
import utilities.*;

public class EulerIntegration {
    final static int MSEC_PER_SEC = 1000;
    public static Vector<MovingBody> positions = new Vector<MovingBody>();

    public static int     i = 0;

    // use:  compute all the positions of the moving body in order to obtain its
    //       trajectory.
    // pre:  `currentSetting' has to be properly set up.
    //       TODO: assure the above
    // post: `positions'  is  filled;  `xs', `ys' are only filled with the x, y
    //        values.
    //
    public static void eulerIntegrate(){
        Setting setting = UI.UserInputNewParameters.currentSetting;
        MovingBody tempMovingBody = null;
        long tStart = System.currentTimeMillis(); // starting time
        double x  = 0.0;
        double y  = 0.0;
        double vx = 0.0;
        double vy = 0.0;
        double rho = 0.0; //Variable zur Zwischenspeicherung und Neuberechnung der Dichte in Abhängigkeit von der Höhe
        i = 1;
        double  k = 0;
	    double dt = UI.UserInputNewParameters.currentSetting.getDt();
        // allowedTime in msec
        double allowedTime = UI.UserInputNewParameters.currentSetting.getT()
                             * MSEC_PER_SEC;
        final int SIZE = 3000000; // TODO: AA adjust that to hardware, maybe
                                  //       automatically ;-)

        // clean up
        positions.removeAllElements();
        // set the 0th element in positions
        positions.add(new MovingBody(setting));
       rho = positions.get(0).getLocation(setting).getRho();
        //Startwert der Dichte (Höhe Null!), sollte bei einer Starthöhe>0 begonnen werden,
        //wird die Dichte im ersten Schritt an die tatsächliche Höhe angepasst

       //Abfrage, ob vorwärts oder rückwärts gerechnet wird und ob einfache Gravitation aktiv:
      if (Options.computeBackwards == false && Forces.isActingSimpleGravity() == false){

            while ( // time left AND ground not hit AND space left for results
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
                (i < SIZE)
              ) {

            ///////////////

            // compute new x,y coordinates and speed in this loop
                tempMovingBody = positions.get(i - 1);

//                System.out.println("DEBUG_ON");
//                tempMovingBody.printMovingBody();
//                if (positions.get(i - 1).getLocation(setting).getH() < 0.0)
//                  System.out.println("### H<0 ###");
//                else System.out.println(positions.get(i - 1).getLocation(setting).getH());
//                if (i>SIZE) System.out.println("### size ###");
//                if (!(gotStillTime(tStart,allowedTime))) System.out.println("time out");
//                System.out.println("DEBUG_OFF");

                if(Options.computeDensity == true){
                    rho = Options.computeDensity(rho, tempMovingBody, setting);
                    //Berechnung der Dichte, wenn gewünscht
                    tempMovingBody.getLocation(setting).setRho(rho);
                    //echter Startwert bzw. an die momentane Höhe angepasster Dichtewert wird an tempMovingBody weitergegeben
                    }

                if (i == (6000 * k + 1)) {
                    k++;
                  //System.out.println(tempMovingBody.getLocation(setting).getY());
                   // System.out.println(ScreenUtilities.scalingFactor);
                    System.out.println(tempMovingBody.getLocation(setting).getRho());
                    //System.out.println(ScreenUtilities.compareTrajectories);
                } // Ausgabe von 50 Zwischenwerten


                x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;

                // new speeds
                vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;


                //System.out.println(i);
                positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));

                i++;
              }
            }


       if (Options.computeBackwards == true && Forces.isActingSimpleGravity() == false){   //Simulation der Bewegung rückwärts:

            while ( // Rechenzeit übrig und gewünschte Wurfweite nicht erreicht und Platz im Vektor vorhanden und Höhe größer Null
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getX() <= Options.throwingRange) && (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
                (i < SIZE)
              ) {

            ///////////////

            // compute new x,y coordinates and speed in this loop
                tempMovingBody = positions.get(i - 1);

//                System.out.println("DEBUG_ON");
//                tempMovingBody.printMovingBody();
//                if (positions.get(i - 1).getLocation(setting).getH() < 0.0)
//                  System.out.println("### H<0 ###");
//                else System.out.println(positions.get(i - 1).getLocation(setting).getH());
//                if (i>SIZE) System.out.println("### size ###");
//                if (!(gotStillTime(tStart,allowedTime))) System.out.println("time out");
//                System.out.println("DEBUG_OFF");

                 if(Options.computeDensity == true){
                    rho = Options.computeDensity(rho, tempMovingBody, setting);

                    tempMovingBody.getLocation(setting).setRho(rho);
                    //echter Startwert bzw. an die momentane Höhe angepasster Dichtewert wird an tempMovingBody weitergegeben
                    }

                if (i == (6000 * k + 1)) {
                    k++;
                  //System.out.println(tempMovingBody.getLocation(setting).getY());
                  //System.out.println(tempMovingBody.getLocation(setting).getX());
                   // System.out.println(ScreenUtilities.scalingFactor);
                } // Ausgabe von 50 Zwischenwerten


                x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;

                // new speeds
                vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;



                //System.out.println(i);
                positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));

                i++;
               }
            }


        if (Options.computeBackwards == false && Forces.isActingSimpleGravity() == true){

            while (
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
                (i < SIZE)
              ) { // Anmerkung: bei simpleGravity wird als Abbruchkriterium statt der Höhe der y-Wert verwendet

                 tempMovingBody = positions.get(i - 1);


                 if(Options.computeDensity == true){
                    rho = Options.computeDensity(rho, tempMovingBody, setting);

                    tempMovingBody.getLocation(setting).setRho(rho);
                    }

                if (i == (6000 * k + 1)) {
                    k++;
                  //System.out.println(tempMovingBody.getLocation(setting).getY());
                  //System.out.println(tempMovingBody.getLocation(setting).getX());
                   // System.out.println(ScreenUtilities.scalingFactor);
                }


                x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;


                vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;

                positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));

                i++;
               }
            }


       if (Options.computeBackwards == true && Forces.isActingSimpleGravity() == true){

            while (
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getX() <= Options.throwingRange) && (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
                (i < SIZE)
              ) {

                 tempMovingBody = positions.get(i - 1);


                 if(Options.computeDensity == true){
                    rho = Options.computeDensity(rho, tempMovingBody, setting);

                    tempMovingBody.getLocation(setting).setRho(rho);
                    }

                if (i == (6000 * k + 1)) {
                    k++;
                  //System.out.println(tempMovingBody.getLocation(setting).getY());
                  //System.out.println(tempMovingBody.getLocation(setting).getX());
                   // System.out.println(ScreenUtilities.scalingFactor);
                }


                x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;


                vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity()) ;

                positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));

                i++;
               }
            }


        // as animation already works via the xs, ys stuff.
        ScreenUtilities.positionsToXsYsStructures(ScreenUtilities.xs, ScreenUtilities.ys);
     }

    private static boolean gotStillTime(long tStart, double allowedTime){
        return (boolean)(
                (System.currentTimeMillis() - tStart)
                <
                allowedTime);
    }
}


