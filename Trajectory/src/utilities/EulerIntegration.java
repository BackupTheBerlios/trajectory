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

  // variables and constants
  final static int MSEC_PER_SEC = 1000;
  final int SIZE = 3000000; // TODO: AA adjust that to hardware, via autoset.
  // BE: i am on it.
  public static Vector<MovingBody> positions = new Vector<MovingBody>();
  static Long currentMillis = new Long(0);
  private static double rho = 0.0;
  private static double dt = 0.0;
  private static long tStart = 0;
  private static double allowedTime = 0.0;
  private static int i = 0;
  private static int k = 0;
  private Setting setting = null;
  private static int taskID = 0;  // debugging purposes

  /////////////
  // Methods //
  /////////////

  // Use:   Proved valuable during debugging. Helped finding unneccessary thread.
  //        The bug that cost me 3 work days of my life.
  //
  public int getTaskID() {
    return taskID;
  }

  public int incTaskID() {
    taskID++;
    return taskID;
  }

  // Use  : To determine wether there is still computing time left or not.
  private static boolean gotStillTime(long tStart, double allowedTime) {
    currentMillis = new Long(System.currentTimeMillis());
    return (boolean) ( (currentMillis - tStart) < allowedTime);
  }


  // inner class

  // Use:  This class is used to provide the background working thread
  //       facilities for the time-consuming `eulerIntegrate()' method.
  //       As you can see the code in `doInBackground()' (below) is the
  //       same as in `eulerIntegrate()'. Effectively, this lets the method
  //       now run without blocking the GUI.
  //       Thus pre-, and postcondition remain the same.
  public class EulerIntegrationTask extends SwingWorker<Void, Void>
          implements PropertyChangeListener {

    private MovingBody tmpMB;

    // constructor
    public EulerIntegrationTask() {
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
    public void eulerIntegrate(double rho, double dt, MovingBody tmpMB) {

      double x = 0.0;
      double y = 0.0;
      double vx = 0.0;
      double vy = 0.0;

      // report to progressbar (change the Progress property of this class,
      // which extends the `SwingWorker' class, and thus owns such a field.
      // the listener implemented below listens on this action!
      setProgress((int) Math.min(100 * (currentMillis - tStart) / allowedTime, 100));

      // compute new x,y coordinates and speed of the moving body in this loop
      tmpMB = positions.get(i - 1);

      // Berechnung der Dichte, wenn gewünscht
      if (Options.getComputeDensity() == true) {
        rho = Options.computeDensity(tmpMB, setting);

        tmpMB.getLocation(setting).setRho(rho);
      // echter Startwert bzw. an die momentane Höhe angepasster Dichtewert
      // wird an tmpMB weitergegeben
      }

      informWaitingUser(tmpMB); // graphically output

      x = tmpMB.getLocation(setting).getX() + tmpMB.getVx() * dt + Forces.computeDxByForces(dt, tmpMB, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
      y = tmpMB.getLocation(setting).getY() + tmpMB.getVy() * dt + Forces.computeDyByForces(dt, tmpMB, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

      // new speeds
      vx = tmpMB.getVx() + Forces.computeDvxByForces(dt, tmpMB, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
      vy = tmpMB.getVy() + Forces.computeDvyByForces(dt, tmpMB, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

      positions.add(new MovingBody(vx, vy, new Location(x, y, rho, setting), setting));
    } // end `eulerIntegrate()'


    // use:  the main computational workload occurs in this thread. the under-
    //       lying datastructure is filled with values according to decisions
    //       made by the user.
    @Override
    protected Void doInBackground() {
      // informational purposes only (during development)
      // @CT: feel free to comment out where neccessary, if you encounter the
      // routine again. You can delete this comment.
      System.out.println("Mem comparison: ");
      utilities.SystemData.testCleanUp();

      setUpInitialValues();

      // Four cases are handled so far, according to user decisions. Only
      // one is to be computed according to the following selection:

      // Abfrage, ob vorwärts oder rückwärts gerechnet wird und ob einfache Gravitation aktiv:
      if (Options.getComputeBackwards() == false && Forces.isActingGravity() == true) {
        eulerIntegrateFwdWithGravityOn();
      }

      // Simulation der Bewegung rückwärts:
      if (Options.getComputeBackwards() == true && Forces.isActingGravity() == true) {
        eulerIntegrateBwdWithGravityOn();
      }

      // einfache Gravitation:
      if (Options.getComputeBackwards() == false && Forces.isActingGravity() == false) {
        eulerIntegrateFwdWithGravityOff();
      }

      //einfache Gravitation und rückwärts Rechnen:
      if (Options.getComputeBackwards() == true && Forces.isActingGravity() == false) {
        eulerIntegrateBwdWithGravityOff();
      }

      UI.Analysis.computeFinalValues(utilities.EulerIntegration.positions, UI.UserInputNewParameters.currentSetting);
      UI.Analysis.displayFinalValues(UI.Analysis.getHMax(), UI.Analysis.getHMin(), UI.Analysis.getThrowingRange(), UI.Analysis.getVEnd(), UI.Analysis.getBetaEnd(), UI.Analysis.getHEnd(), UI.Analysis.getThrowingTime());
      // as animation already works via the xs, ys stuff.
      ScreenUtilities.positionsToXsYsStructures(ScreenUtilities.xs, ScreenUtilities.ys);
      // utilities.SystemData.testCleanUp();
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
      if ("progress".equals(evt.getPropertyName())) {
        int progress = (Integer) evt.getNewValue();
        DialogComputationInProgress.jProgressBarComputation.setValue(progress);
        DialogComputationInProgress.jProgressBarComputation.setStringPainted(true);
      }
    } // end `propertyChange()'

    // use: appends a feedback-providing value to the gui element seen by the
    //      waiting user.
    private void informWaitingUser(MovingBody tmpMB) {
      // TODO: A BE improve feedback for user, possibly automate or leave out.
      if (i == (6000 * k + 1)) {
        k++;

        if (Forces.isActingGravity() == false) {
          TrajectoryUI.dialog.jTextAreaComputationReport.append(
                  tmpMB.getLocation(setting).getY() + " m" +
                  System.getProperty("line.separator"));
        } else {
          TrajectoryUI.dialog.jTextAreaComputationReport.append(
                  tmpMB.getLocation(setting).getH() + " m" +
                  System.getProperty("line.separator"));
        }
      } // end if
    } // end `informWaitingUser()

    ////////////////////////////////////////////////////////////////////////////
    // "shell"-methods for calling the original eulerIntegrate() method with  //
    // the correct termination criteria.                                      //
    ////////////////////////////////////////////////////////////////////////////

    // use: adapt the termination criteria accordingly to the case choosen by
    //      the user.
    private void eulerIntegrateFwdWithGravityOn() {
      while ( // time left AND ground not hit AND space left for results
              (gotStillTime(tStart, allowedTime)) &&
              (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
              (i < SIZE)) {

        eulerIntegrate(rho, dt, tmpMB);
        i++;
      } // end while
    } // end `eulerIntegrateFwdWithGravityOn()'

    // use: as above
    private void eulerIntegrateBwdWithGravityOn() {
      while ( // Rechenzeit übrig und gewünschte Wurfweite nicht erreicht und Platz im Vektor vorhanden und Höhe größer Null
              (gotStillTime(tStart, allowedTime)) &&
              (positions.get(i - 1).getLocation(setting).getX() <= Options.getThrowingRange()) && (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
              (i < SIZE)) {

        eulerIntegrate(rho, dt, tmpMB);
        i++;
      } // end while
    } // end `eulerIntegrateBwdWithGravityOn()'


    // use: a.a.
    private void eulerIntegrateFwdWithGravityOff() {
      while ((gotStillTime(tStart, allowedTime)) &&
              (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
              (i < SIZE)) { // Anmerkung: bei simpleGravity wird als Abbruchkriterium statt der Höhe der y-Wert verwendet

        eulerIntegrate(rho, dt, tmpMB);
        i++;
      } // end while
    } // end  eulerIntegrateFwdWithGravityOff()'

    // use: a.a.
    private void eulerIntegrateBwdWithGravityOff() {
      while ((gotStillTime(tStart, allowedTime)) &&
              (positions.get(i - 1).getLocation(setting).getX() <= Options.getThrowingRange()) && (positions.get(i - 1).getLocation(setting).getY() >= 0.0) &&
              (i < SIZE)) {

        eulerIntegrate(rho, dt, tmpMB);
        i++;
      } // end while
    } // end  eulerIntegrateFwdWithGravityOff()'

    // use:  simple initialization of class-wide variables
    private void setUpInitialValues() {
      allowedTime = UI.UserInputNewParameters.currentSetting.getT() *
              MSEC_PER_SEC; // allowedTime in msec
      setting = UI.UserInputNewParameters.currentSetting;
      tStart = System.currentTimeMillis();                   // starting time
      dt = UI.UserInputNewParameters.currentSetting.getDt(); // integration time

      i = 1; // counter for the `positions' vector
      k = 0; // counter for user-feedback

      // clean up
      positions.removeAllElements();

      // set the 0th element in positions
      positions.add(new MovingBody(setting));

      // Startwert der Dichte (Höhe Null!),sollte bei einer Starthöhe>0 begonnen
      // werden, wird die Dichte im ersten Schritt an die tatsächliche Höhe angepasst
      rho = positions.get(0).getLocation(setting).getRho();

    } // end `setUpInitialValues()'
  } // `EulerIntegrationTask'
}
