/*****************************************************************************
 * @author  BE
 * Date:    2009-01-02
 * Use:     This class keeps the main computational part of the program, which
 *          is     known     in     the     literature   as   `Euler´s Method'
 *          (numerical integration).
 *
 * TODO:    CT: A Check automatical opimization of dt.
 * TODO:    -->BE: seems that we can only give an upper bound   for  computing
 *                 time, as there is always the possibility of obtaining  non-
 *                 sensical   results  by shortening the integration interval.
 */
package utilities;

import UI.DialogComputationInProgress;
import UI.TrajectoryUI;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.util.Vector;
import physics.*;

public class EulerIntegration {

  final static int MSEC_PER_SEC = 1000;
  public static Vector<MovingBody> positions = new Vector<MovingBody>();
  static Long currentMillis = new Long(0);

  private static double rho = 0.0;
  private static double dt = 0.0;
  private MovingBody tempMovingBody = null;
  private Setting setting = null;
  private static long tStart = 0;
  private static double allowedTime = 0.0;
  private static int i = 0;
  private static int k = 0;


  // Use:   Proved valuable during debugging. Helped finding unneccessary thread.
  //        The bug that cost me 3 work days of my life.
  private static int taskID = 0;
  public int getTaskID(){
      return taskID;
  }

  public int incTaskID(){
      taskID++;
      return taskID;
  }

  // Use  : To determine wether there is still computing time left or not.
  private static boolean gotStillTime(long tStart, double allowedTime) {
    currentMillis = new Long(System.currentTimeMillis());
    return (boolean) (( currentMillis - tStart) <
            allowedTime);
  }


  // Use:  This class is used to provide the background working thread
  //       facilities for the time-consuming `eulerIntegrate()' method.
  //       As you can see the code in `doInBackground()' (below) is the
  //       same as in `eulerIntegrate()'. Effectively, this lets the method
  //       now run without blocking the GUI.
  //       Thus pre-, and postcondition remain the same.
  public class EulerIntegrationTask extends SwingWorker<Void, Void>
  implements PropertyChangeListener {

      // constructor
      public EulerIntegrationTask(){
          incTaskID();
          System.out.println("TASK_ID: " + getTaskID());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EulerIntegration.class.getName()).log(Level.SEVERE, null, ex);
            }
      }


  // use:  compute all the positions of the moving body in order to obtain its
  //       trajectory.
  // pre:  `currentSetting' has to be properly set up.
  //       TODO: assure the above
  // post: `positions'  is  filled;  `xs', `ys' are only filled with the x, y
  //        values.
  //
      public void eulerIntegrate(double rho, double dt, MovingBody tempMovingBody) {

        double x = 0.0;
        double y = 0.0;
        double vx = 0.0;
        double vy = 0.0;

        // report to progressbar (change the Progress property of this class,
        // which extends the `SwingWorker' class, and thus owns such a field.
        // the listener implemented below listens on this action!
        setProgress( (int) Math.min(100 * (currentMillis - tStart)/allowedTime, 100));

        // compute new x,y coordinates and speed in this loop
        tempMovingBody = positions.get(i - 1);

                    if(Options.getComputeDensity() == true){
                    rho = Options.computeDensity(tempMovingBody, setting);
                    //Berechnung der Dichte, wenn gewünscht
                    tempMovingBody.getLocation(setting).setRho(rho);
                    //echter Startwert bzw. an die momentane Höhe angepasster Dichtewert wird an tempMovingBody weitergegeben
                    }

        // TODO: A BE improve feedback for user, possibly automate or leave out.
        if (i == (6000 * k + 1)) {
          k++;

         if (Forces.isActingGravity() == false){
           TrajectoryUI.dialog.jTextAreaComputationReport.append(
               tempMovingBody.getLocation(setting).getY() + " m" +
               System.getProperty("line.separator"));
         }
         else{
           TrajectoryUI.dialog.jTextAreaComputationReport.append(
               tempMovingBody.getLocation(setting).getH() + " m" +
               System.getProperty("line.separator"));
         }
        }

        x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
        y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

        // new speeds
        vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
        vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

        positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));
    }



    @Override
    //
    protected Void doInBackground() {

      setting = UI.UserInputNewParameters.currentSetting;
      tStart = System.currentTimeMillis(); // starting time
      //Variable zur Zwischenspeicherung und Neuberechnung der Dichte in Abhängigkeit von der Höhe
      i = 1;
      k = 0;
      dt = UI.UserInputNewParameters.currentSetting.getDt();
      // allowedTime in msec
      allowedTime = UI.UserInputNewParameters.currentSetting.getT() * MSEC_PER_SEC;
      final int SIZE = 3000000; // TODO: AA adjust that to hardware, maybe
      // automatically ;-)
      // clean up
      positions.removeAllElements();
      // set the 0th element in positions
      positions.add(new MovingBody(setting));
      rho = positions.get(0).getLocation(setting).getRho();
      //Startwert der Dichte (Höhe Null!), sollte bei einer Starthöhe>0 begonnen werden,
      //wird die Dichte im ersten Schritt an die tatsächliche Höhe angepasst

      System.out.println("asdfL");
      utilities.SystemData.testCleanUp();

      //Abfrage, ob vorwärts oder rückwärts gerechnet wird und ob einfache Gravitation aktiv:
      if (Options.getComputeBackwards() == false && Forces.isActingGravity() == true){

            while ( // time left AND ground not hit AND space left for results
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
                (i < SIZE)) {

             eulerIntegrate(rho, dt, tempMovingBody);
             i++;
            } // end while
      }

      //Simulation der Bewegung rückwärts:
      if (Options.getComputeBackwards() == true && Forces.isActingGravity() == true){

            while ( // Rechenzeit übrig und gewünschte Wurfweite nicht erreicht und Platz im Vektor vorhanden und Höhe größer Null
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getX() <= Options.getThrowingRange()) && (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
                (i < SIZE)) {

             eulerIntegrate(rho, dt, tempMovingBody);
             i++;
            } // end while
      }

      //einfache Gravitation:
        if (Options.getComputeBackwards() == false && Forces.isActingGravity() == false){

            while (
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
                (i < SIZE)) { // Anmerkung: bei simpleGravity wird als Abbruchkriterium statt der Höhe der y-Wert verwendet

             eulerIntegrate(rho, dt, tempMovingBody);
             i++;
            } // end while
      }

      //einfache Gravitation und rückwärts Rechnen:
      if (Options.getComputeBackwards() == true && Forces.isActingGravity() == false){

            while (
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getX() <= Options.getThrowingRange()) && (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
                (i < SIZE)) {

             eulerIntegrate(rho, dt, tempMovingBody);
             i++;
            } // end while
      }


       UI.Analysis.computeFinalValues(utilities.EulerIntegration.positions, UI.UserInputNewParameters.currentSetting);
       UI.Analysis.displayFinalValues(UI.Analysis.getHMax(), UI.Analysis.getHMin(), UI.Analysis.getThrowingRange(), UI.Analysis.getVEnd(), UI.Analysis.getBetaEnd(), UI.Analysis.getHEnd(), UI.Analysis.getThrowingTime(), UI.Analysis.finalFormat);
    // as animation already works via the xs, ys stuff.
      ScreenUtilities.positionsToXsYsStructures(ScreenUtilities.xs, ScreenUtilities.ys);
      utilities.SystemData.testCleanUp();
      return null; // p.d.
    }


    // Use:  Beep when the task is done.
    @Override
    public void done() {
      Toolkit.getDefaultToolkit().beep();
      setProgress(0);
      TrajectoryUI.dialog.dispose();
    }


    public void propertyChange(PropertyChangeEvent evt) {
      if ("progress".equals(evt.getPropertyName())){
        int progress = (Integer) evt.getNewValue();
        DialogComputationInProgress.jProgressBarComputation.setValue(progress);
        DialogComputationInProgress.jProgressBarComputation.setStringPainted(true);
      }
    } // end `propertyChange()'
  } // `EulerIntegrationTask'
}
