/****************************************************************************
 * Author:  BE
 * Date:    2008-09-27
 * Use:     This is the place where GUI related experiments happen.
 *          This is the entry point (m a i n) of the program.
 * Remark:  TODO written in uppercase is recognized by netbeansIDE and
 *          written into the tasklist.
 * History: 
 *              2008-09-27  Start exploring the use for project `Trajectory'
 *                          BE
 *              2008-09-28  Threaded animation works very slow.
 *              2008-09-29  Tried overloading update() method of Graphics w/o
 *                          success.
 *              2008-09-30  Found out that a Thread.sleep(X) is necessary in
 *                          order to make the animation run at all. This solves
 *                          the problem of 09-28 above.
 *              2008-10-11  Smoothed the animation.
 *              2009-01-01  Happy New Year! Changed scaling and killed the bug
 *                          responsible for cutting only part of negative y-val
 *                          ues.
 *                          Successful tests included, but were not restricted
 *                          to, following files:
 *                           - erdumrundungG.txt
 *                           - erdumrundungGvz.txt
 *                           - erdumrundungK.txt
 *                           - erdumrundungNeg.txt
 *                           - testB.txt
 *                           - test_100m.txt
 *                           - test_glycerin.txt
 *                           - test[t]^{1,2,3}.txt
 *                             ---------> REDUCTION OF POINTS!!! for small dt
 *                                        is urgent.
 * 
 * TODO:    A! The tested cases with `test[t]^N.txt' showed that it is 
 *             mandatory to reduce the points being drawed, as visualization is
 *             slowed down otherwise. DONE BE
 * 
 * DONE:    B! Known bug: content of the panel gets erased by moving it out of
 *             visible range of by covering it with other windows. DONE BE
 *             
 * just 4me:
 * jPnlDrawingPlanes earlier "working size": 1018,649
 * 
 */
package guitest;

import java.io.*;

import utilities.EulerIntegration;
import utilities.ObjectSize;
import UI.TrajectoryUI;
import physics.MovingBody;
import physics.Setting;
import UI.Analysis;

/**
 *
 * @author BE
 */
public class Main {
  // TODO: A! put code where it belongs. Too much static variables crept in over
  //          the time.

  // these are set via calling ScreenUtilities.computeScalingFactor!
  public static Double xMax,  xMin,  yMax,  yMin;
  public static Analysis analysisUI;
  public static TrajectoryUI ui;
  //public static MeasureMode mmUI;
  
  // Use  : This is the absolute main method of the project. Everything starts
  //        here.
  public static void main(String[] args)
          throws FileNotFoundException, InterruptedException {
   
    ////////////////////
    // INITIALIZATION //
    ////////////////////

    // generate a unique instance keeping track of the user-provided physical
    // parametervalues as defined in class `Setting'.
    UI.UserInputNewParameters.currentSetting = new Setting();

    // bring up the GUI where all drawing, user-manipulation happens
    ui = new TrajectoryUI();
    ui.setSize(1024, 768);  // hard-wired screen resolution...
    ui.setResizable(false); // maybe via config file, to keep it small and
    ui.setVisible(true);    // simple?

    // bring up the GUI for showing `movingBody' data
    analysisUI = new Analysis();
    analysisUI.setVisible(true);

    // automatically set the maximum number of movingBody objects for the data-
    // structures
    System.gc();
    ObjectSize.gc();
    System.out.println("free mem: " + Runtime.getRuntime().freeMemory());
    ObjectSize.gc();
    long sizeOfAMovingBody = ObjectSize.sizeOfMovingBody(500);
    System.out.println("Size of a mb: " + sizeOfAMovingBody);
    System.out.println("MAX MEMORY: " + Runtime.getRuntime().maxMemory());

    // 2009-03-11--BE:
    // underestimate the amount of movingBodies the RAM can hold.
    // assumptions:
    //   (1) JVM is started with a value close to the size of the RAM.
    //   (2) the user doesn´t use more than 50% of its RAM while executing
    //       project trajectory.
    // post: the SIZE "constant" is set to this value. hope nobody plays with
    //       the "constant" now :-(
    // rem : this cannot be done accurately, as stated in the sources of
    //       `ObjectSize'.
    // TODO: learn to design programs.
    ObjectSize.gc();
    double estimatedMaxOfMovingBodies =
            0.32 * Runtime.getRuntime().maxMemory() /
            sizeOfAMovingBody;
    System.out.println(
            "estMaxMovingBodies: " +
            Math.round(estimatedMaxOfMovingBodies)
    );
    EulerIntegration.setSIZE((int)Math.ceil(estimatedMaxOfMovingBodies));
    System.out.println("SIZE: " + EulerIntegration.getSIZE());
  } // end `main()'
} // end `Main'