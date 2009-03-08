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

  // use:  compute all the positions of the moving body in order to obtain its
  //       trajectory.
  // pre:  `currentSetting' has to be properly set up.
  //       TODO: assure the above
  // post: `positions'  is  filled;  `xs', `ys' are only filled with the x, y
  //        values.
  //
  public static void eulerIntegrate() {
    Setting setting = UI.UserInputNewParameters.currentSetting;
    MovingBody tempMovingBody = null;
    
    long tStart = System.currentTimeMillis(); // starting time
    double x = 0.0;
    
    double y = 0.0;
    double vx = 0.0;
    double vy = 0.0;
    
    int    i  = 1;
    double k  = 0;
    double dt = UI.UserInputNewParameters.currentSetting.getDt();
    
    // allowedTime in msec
    double allowedTime = UI.UserInputNewParameters.currentSetting.getT() * MSEC_PER_SEC;
    final int SIZE = 3000000; // TODO: AA adjust that to hardware, maybe
    //       automatically ;-)

    // clean up
    positions.removeAllElements();
    // set the 0th element in positions
    positions.add(new MovingBody(setting));

    while ( // time left AND ground not hit AND space left for results
            (gotStillTime(tStart, allowedTime)) &&
            (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
            (i < SIZE)) {

      // compute new x,y coordinates and speed in this loop
      tempMovingBody = positions.get(i - 1);

      if (i == (6000 * k + 1)) {
        k++;
        System.out.println(tempMovingBody.getLocation(setting).getY());
      } // Ausgabe von 50 Zwischenwerten

      x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
      y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

      // new speeds
      vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
      vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

      positions.add(new MovingBody(vx, vy, new Location(x, y, setting), setting));

      i++;
    }
    // as animation already works via the xs, ys stuff.
    ScreenUtilities.positionsToXsYsStructures(ScreenUtilities.xs, ScreenUtilities.ys);
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

      
    @Override
    //
    protected Void doInBackground() {
      Setting setting = UI.UserInputNewParameters.currentSetting;
      MovingBody tempMovingBody = null;
      long tStart = System.currentTimeMillis(); // starting time
      double x = 0.0;
      
      double y = 0.0;
      double vx = 0.0;
      double vy = 0.0;
      
      int i = 1;
      double k = 0;
      double dt = UI.UserInputNewParameters.currentSetting.getDt();
      // allowedTime in msec
      double allowedTime = UI.UserInputNewParameters.currentSetting.getT() * MSEC_PER_SEC;
      final int SIZE = 3000000; // TODO: AA adjust that to hardware, maybe
      //       automatically ;-)

      // clean up
      positions.removeAllElements();
      // set the 0th element in positions
      positions.add(new MovingBody(setting));

      while ( // time left AND ground not hit AND space left for results
              (gotStillTime(tStart, allowedTime)) &&
              (positions.get(i - 1).getLocation(setting).getH() >= 0.0) &&
              (i < SIZE)) {

        // report to progressbar (change the Progress property of this class,
        // which extends the `SwingWorker' class, and thus owns such a field.
        // the listener implemented below listens on this action!
        setProgress( (int) Math.min(100 * (currentMillis - tStart)/allowedTime, 100));
        
        // compute new x,y coordinates and speed in this loop
        tempMovingBody = positions.get(i - 1);

        // TODO: A BE improve feedback for user, possibly automate or leave out.
        if (i == (6000 * k + 1)) {
          k++;

          TrajectoryUI.dialog.jTextAreaComputationReport.append(
                  tempMovingBody.getLocation(setting).getY() +
                  System.getProperty("line.separator")
                  );
        }

        x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
        y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

        // new speeds
        vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity());
        vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity(), Forces.isActingSimpleGravity());

        positions.add(new MovingBody(vx, vy, new Location(x, y, setting), setting));

        i++;
      } // end while
        
      // as animation already works via the xs, ys stuff.
      ScreenUtilities.positionsToXsYsStructures(ScreenUtilities.xs, ScreenUtilities.ys);
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
