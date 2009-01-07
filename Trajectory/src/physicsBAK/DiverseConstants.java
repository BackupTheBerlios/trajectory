/*****************************************************************************
 * Author: 	BE
 * Date:	2008-09-23
 * Purpose:     This is the class where constants (not only for physics, but also
 * 		for other purposes) belong in order to find them quickly. 
 * ToDo:	Adding various constants e.g. for ETA(X), where X is the fluid the
 * 			body is moving in.
 * 
 * History:     2008-10-04  Refactor-copied this file from the `Trajectory'
 *                          branch into the netbeans-branch. G added.
 * 
 */
package physics;

public class DiverseConstants {
    // for the time of development these values are constants... //
    private final static double ONE_MILLION = 1000000;
    public final static double MAX_WIDTH_SIMUL = ONE_MILLION;
    public final static double MAX_HEIGHT_SIMUL = ONE_MILLION;
    public final static int SCREEN_WIDTH = 1024;
    public final static int SCREEN_HEIGHT = 768;
    public final static int X_DIRECTION = 0;
    public final static int Y_DIRECTION = 1;    
    
    // p l a n e t  e a r t h //
    public final static double DENSITY_AT_SEALEVEL = 1.2; // kg/(m^3)	
    public final static double EQUATORIAL_SURFACE_GRAVITY_EARTH = 9.780327; // m/(sec^2)
    public final static double ESCAPE_VELOCITY_EARTH = 11186; // m/sec
    public final static double RADIUS_EARTH = 6378100; // earth´s mean radius in m
    
    // p h y s i c a l  c o n s t a n t s //
    public final static double GRAVITATIONAL_CONSTANT = 6.67428e-11;
    //+- 0.00067e-11
}
