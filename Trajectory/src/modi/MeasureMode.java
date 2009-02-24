/*****************************************************************************
 * Author:  Be
 * Date:    2009-02-24
 * Use:     Experimental class providing/testing measure modi for end user.
 *
 */
package modi;

import java.awt.image.BufferedImage;

import utilities.*;

public class MeasureMode {

  private static boolean isDisplayXYcoordsEnabled = false;
  
  public static boolean getIsDisplayXYCoordsEnabled(){
    return MeasureMode.isDisplayXYcoordsEnabled;
  }

  public static void setIsDisplayXYcoordsEnabled(boolean isDisplayXYcoordsEnabled) {
    MeasureMode.isDisplayXYcoordsEnabled = isDisplayXYcoordsEnabled;
  }
  
  // Use :  not implemented yet.
  // Idea:  bringing up a suite for more complex measures
  //        distance(A,B), polar coordinates, measures between two or
  //        more points.
  public static void bringUpMeasureMode(BufferedImage bi) {
    // ... stub
  }

  // Use : Provides the inverse function for the implicitly given
  //       transformation in `TrajectoryUI' myPaint() method.
  // Pre : ScreenUtilities.scalingFactor has to be non-zero.
  // Post: The return value is a value related to the physical world.
  //       Obviously one can obtain only discrete values due to the 
  //       resolution of the underlying `jPnlDrawingPlane'. The error
  //       due to double-arithmetic - which occurs twice, as the call of 
  //       functions is:
  //        f(screenCoords->realityCoords) o f(realityCoords->screenCoords)
  //       hasn´t been examined yet.
  // TODO: BE A Examine the various sources of error.
  public static double screenCoordsToReality(int axis, int value) {
    if (axis == 0) { // compute physical reality´s x value
      return ((value - ScreenUtilities.ORIGIN_OFFSET) /
              ScreenUtilities.scalingFactor + guitest.Main.xMin);

    } else { // axis other than 0 indicates y value computation
      return ((-1) * (value - ScreenUtilities.ORIGIN_OFFSET -
              ScreenUtilities.SCREEN_HEIGHT) /
              ScreenUtilities.scalingFactor + guitest.Main.yMin);
    }
  } // end `screenCoordsToReality()'

  public static void toggleMeasureMode() {
    
  }
}