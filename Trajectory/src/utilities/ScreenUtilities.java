/****************************************************************************
 * Author:  BE
 * Date:    2008-10-27
 * Purpose: All   methods     related     to   DRAWING  on the screen belong
 *          in this class (`ScreenUtilities')
 * History: 
 *          2009-01-01  Slight modifications to scaling procedure.
 *                      Major structural changes.
 *          2009-01-02..methods for fileIO added.  
 * 
 * TODO:    A BE: Sometimes starting, stopping the thread seems not to work...
 *                - DONE BE
 * TODO:    C BE: Better naming/splitting of methods.
 * 
 */
package utilities;

import java.util.*;
import static java.lang.Math.*;
import java.io.*;
import java.awt.Graphics2D;

import java.util.logging.Level;
import java.util.logging.Logger;
import physics.Setting;

public class ScreenUtilities {
    // constants
    public final static int ORIGIN_OFFSET = 20; // in x,y direction
    public final static int SCREEN_WIDTH = 1018 - 2 * ORIGIN_OFFSET;
    public final static int SCREEN_HEIGHT = 649 - 2 * ORIGIN_OFFSET;
    
    //////////////////////
    // member variables //
    //////////////////////
    
    // datastructures
    public static Vector<Double> xs = new Vector<Double>();
    public static Vector<Double> ys = new Vector<Double>();    // scaling related variables
    public static float xScaleFactor = 0;
    public static float yScaleFactor = 0;
    public static double scalingFactor;  // to fit to `jPnlDrawingPlane'
    public static double increment;      // to draw a sufficient number of pts
    
    // selected file to read values from
    private static File openedFile = null;
    
    // getters
    public static File getOpenedFile() {
        return openedFile; //! user could do whatever he has in mind.
    }
    
    // setters
    public static void setOpenedFile(File file) {
        ScreenUtilities.openedFile = file;
    }
    
    
    /////////////
    // methods //
    /////////////
    
    //Use:  Computation of an appropriate scaling  factor to map the 
    //      `real-world coordinates' to the `screen-size' (currently
    //      hard-wired; bad coding practise).
    //Pre:  The  vector arguments keep the already computed positions of a
    //      trajectory.
    //Post: The   return  value is  the  best `scaling-factor' which keeps
    //      the proportions of the `real-world coordinates'. `xMin, yMax'
    //      contain the associated extrema.
    public static double computeScalingFactor(Vector<Double> xs,
            Vector<Double> ys,
            int screenWidth,
            int screenHeight) {

        double heightScalingFactor = 0;
        double widthScalingFactor = 0;

        int xMinPos = 0;  // these variables keep the positions of the extrema
        int xMaxPos = 0;
        int yMinPos = 0;
        int yMaxPos = 0;

        //////////////////////////////
        // find the extremal values //
        //////////////////////////////        

        // the following block SEARCHES the datastructures for THE EXTREMA
        for (int i = 1; i < xs.size(); i++) {
            if (xs.get(i) < xs.get(xMinPos)) {
                xMinPos = i;
            }
            if (xs.get(i) > xs.get(xMaxPos)) {
                xMaxPos = i;
            }
            if (ys.get(i) < ys.get(yMinPos)) {
                yMinPos = i;
            }
            if (ys.get(i) > ys.get(yMaxPos)) {
                yMaxPos = i;
            }
        }
      
        // I have no idea why a pass by reference is impossible here!, but
        // direct access is. enlightenment is very welcome.

        // values later needed to transform the origin.
        // TODO: BE: stupid spaghetti code; clean up
        guitest.Main.xMax = xs.get(xMaxPos);
        guitest.Main.yMax = ys.get(yMaxPos);
        guitest.Main.xMin = xs.get(xMinPos);
        guitest.Main.yMin = ys.get(yMinPos);
        
        
        // now we have obtained the positions of the extrema, compute the
        // scaling  factor  to  map  the `real-world  coordinates' to the 
        // `screen-size'
                
        // neat bug not yet killed: DIV 0 -> NaN -> scalingFactor -> infinity.
        // three troublesome cases to catch:
        
        heightScalingFactor = ((double) screenHeight) /
                              (ys.get(yMaxPos) - ys.get(yMinPos)) ;

        widthScalingFactor = ((double) screenWidth) /
                             (xs.get(xMaxPos) - xs.get(xMinPos));
        
        // (1)   body doesn´t change its height sufficiently:
        //            yMaxPos - yMinPos =~ 0
        //            ==> scale only for x
        if (Double.isInfinite(heightScalingFactor) && 
           !Double.isInfinite(widthScalingFactor))    {
          heightScalingFactor = 0; // TODO: watch the implications on
                                   // `TrajectoryUI.myPaint()'
          return widthScalingFactor;
        }
        
        // (2)   body doesn´t change its displacement sufficiently:
        //            xMaxPos - xMinPos =~ 0
        //            ==> scale only for y
        if (!Double.isInfinite(heightScalingFactor) && 
             Double.isInfinite(widthScalingFactor))    {        
          widthScalingFactor = 0;  // TODO: watch the implications on
                                   // `TrajectoryUI.myPaint()'
          return heightScalingFactor;
        }
        
        // (3)   (1) AND (2), the body remains at rest
        //            This case is handled by handling 1,2 above.
        //            For the time being this is a rather expensive
        //            computation.
        if ( Double.isInfinite(heightScalingFactor) && 
             Double.isInfinite(widthScalingFactor))    {        
          widthScalingFactor = 0;  // TODO: watch the implications on
                                   // `TrajectoryUI.myPaint()'
          System.out.println("INFINITY ALERT.");
          heightScalingFactor = 0;
          return 0;
        }


        return min(heightScalingFactor, widthScalingFactor);
    } // end of `computeScalingFactor()'

    // use:  filling the `xs', `ys' datastructures
    // pre:  `xs', `ys' are declared and non-null; well-formed data-file given
    // post: `xs', `ys' are filled with the contents of the datafile.
    // rem:  take care of the global vars.
    // TODO: secure format of datafile. proper exception handling.
    //
    public static void readFileToStructure(File fileName,
            Vector<Double> xs,
            Vector<Double> ys)
            throws FileNotFoundException {

        // clear datastructures of old values and provide everything for a
        // "fresh start"
        xs.removeAllElements();
        ys.removeAllElements();
        UI.TrajectoryUI.setCounterToZero();

        Scanner sc = new Scanner(ScreenUtilities.getOpenedFile());

        // add the file´s values to the datastructure
        while (sc.hasNextDouble()) {
            xs.add(sc.nextDouble());
            ys.add(sc.nextDouble());
        }

        System.out.println("xs: " + xs.size()); // TODO: debug msg
        System.out.println("ys: " + ys.size()); //

        ScreenUtilities.scalingFactor = computeScalingFactor(xs, ys,
                SCREEN_WIDTH, SCREEN_HEIGHT);
        ScreenUtilities.increment = computeIncrement();
        // TODO: debug msg
        System.out.println("SCALING FACTOR: " + ScreenUtilities.scalingFactor);

    } //end of `readFileToStructure()'

    // use:  read the data of the `positions' vector into datastructures
    //       for   which   the   animation  - framework  already  exists .
    // pre:  call to `EulerIntegration.eulerIntegrate()'; proper args.
    // post: the args keep the positional values provided by the global
    //       variable `EulerIntegration.positions', `scalingFactor' and
    //       `increment' are set.
    public static void positionsToXsYsStructures(
            Vector<Double> xs,
            Vector<Double> ys) {

        int SIZE = EulerIntegration.positions.size();
        Setting setting = UI.UserInputNewParameters.currentSetting;

        // clean up first
        xs.removeAllElements();
        ys.removeAllElements();

        //
        for (int i = 0; i < SIZE; i++) {
            xs.add(EulerIntegration.positions.get(i).getLocation(setting).getX());
            ys.add(EulerIntegration.positions.get(i).getLocation(setting).getY());
        }

        ScreenUtilities.scalingFactor = computeScalingFactor(xs, ys,
                SCREEN_WIDTH, SCREEN_HEIGHT);
        ScreenUtilities.increment = computeIncrement();
    }
    
    
    // use:  to compute a factor needed to reduce the amount of drawn pts
    //       on `jPnlDrawingPlane'.
    // pre:  `ScreenUtilities.scalingFactor' is called before this method.
    //       datastructures `xs', `ys' are properly set up.
    // post: the return value is the searched scaling factor.
    // TODO: correct naming!
    static private double computeIncrement() {
        double factor;
        double deltaScaledX;
        double deltaScaledY;
        double threshold = Math.max(xs.size()/1024, 1024);

        deltaScaledX = Math.abs(guitest.Main.xMax - guitest.Main.xMin) *
                ScreenUtilities.scalingFactor;
        deltaScaledY = Math.abs(guitest.Main.yMax - guitest.Main.yMin) *
                ScreenUtilities.scalingFactor;

        // xs.size() == ys.size() holds;
        // TODO: A! BE: During some computations a VERY large factor is
        //          is computed - DONE BE.
        factor = xs.size() / Math.max(deltaScaledX, deltaScaledY);
        if (factor < 1.0) {
            return 1.0;
        } else {
            if (factor >= threshold) // TODO: experimentally refine?
              return threshold;      // TODO: is infinity recognized as being
            else                     //       greater than threshold?
              return factor;
        } // end if
        
    } // end `computeIncrement()'
    
    // use:  to draw the coordinate system
    // pre: 
    // post:
    public static void drawCoordinateSystem(
            Graphics2D g,
            Vector<Double> xs,
            Vector<Double>ys,
            Double xMin,
            Double yMin,
            int width, int height)
    {
      // x-axis   // smoother is to use `g.translate()'
// TODO: the following commented out code worked with serious errors.
//      g.drawLine(
//              0,
//              (int) (ScreenUtilities.SCREEN_HEIGHT - 
//                    (yMin + ys.get(0)) *
//                     ScreenUtilities.scalingFactor 
//                     + ScreenUtilities.ORIGIN_OFFSET),
//              width,
//              (int) (ScreenUtilities.SCREEN_HEIGHT - 
//                    (yMin + ys.get(0)) * 
//                    ScreenUtilities.scalingFactor 
//                    + ScreenUtilities.ORIGIN_OFFSET));
            g.drawLine(
              0,//TODO: CONTINUE!!! , abs() finally...
              (int) (ScreenUtilities.SCREEN_HEIGHT - 
                    (Math.abs(yMin) + ys.get(0)) *
                     ScreenUtilities.scalingFactor 
                     + ScreenUtilities.ORIGIN_OFFSET),
              width,
              (int) (ScreenUtilities.SCREEN_HEIGHT - 
                    (Math.abs(yMin) + ys.get(0)) * 
                    ScreenUtilities.scalingFactor 
                    + ScreenUtilities.ORIGIN_OFFSET));

//            System.out.println("### x-axis drawn ### ");
//            System.out.println("y: " + (int) (ScreenUtilities.SCREEN_HEIGHT - 
//                    (yMin + ys.get(0)) * 
//                    ScreenUtilities.scalingFactor 
//                    + ScreenUtilities.ORIGIN_OFFSET));
//            System.out.println("");
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException ex) {
//      Logger.getLogger(ScreenUtilities.class.getName()).log(Level.SEVERE, null, ex);
//    }
      // y-axis
      g.drawLine(
              (int) (-xMin * ScreenUtilities.scalingFactor) +
                      ScreenUtilities.ORIGIN_OFFSET,
               0,
              (int) (-xMin * ScreenUtilities.scalingFactor) +
                      ScreenUtilities.ORIGIN_OFFSET,
              height);

      // Rem: it is absolutely necessary to do floating point arithmetic
      // here in order to antialize the trajectory.
    }
    
    
} // end of `ScreenUtilities'.

    

