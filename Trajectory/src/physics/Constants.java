/*****************************************************************************
 * Author: 	BE/CT
 * Date:	2008-09-23
 * Purpose: This is the class where constants (not only for physics, but also
 * 			for other purposes) belong in order to find them quickly. 
 * ToDo:	Adding various constants e.g. for ETA(X), where X is the fluid the
 * 			body is moving in.
 * 			
 * 			C! BE: constants -> UPPERCASE
 * 
 */

package physics;

public class Constants { 
	
	public final static double G = 6.67428e-11;	// Gravitationskonstante
	
	// for the time of development these values are constants... //
	private final static double ONE_MILLION = 1000000;
	public  final static double MAX_WIDTH_SIMUL = ONE_MILLION;
	public  final static double MAX_HEIGHT_SIMUL = ONE_MILLION;
	public  final static int SCREEN_WIDTH = 1024;
	public  final static int SCREEN_HEIGHT = 768;
	public  final static int X_DIRECTION = 0;
	public  final static int Y_DIRECTION = 1;
	
	// p l a n e t  e a r t h //
	public final static double DENSITY_AT_SEALEVEL = 1.2; // kg/(m^3)	
	public final static double EQUATORIAL_SURFACE_GRAVITY_EARTH = 9.780327; // m/(sec^2)
	public final static double ESCAPE_VELOCITY_EARTH = 11186; // m/sec
	public final static double RADIUS_EARTH = 6378100; // earth´s mean radius in m
	public final static double Mass_Earth = 5.974e24; // Erdmasse
	public final static double First_Cosmical_Velocity = 7900; // erste kosmische Geschwindigkeit in m/s
	
}
