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
import java.util.Vector;
import physics.*;

public class EulerIntegration {
    final static int MSEC_PER_SEC = 1000;
    public static Vector<MovingBody> positions = new Vector<MovingBody>();
    
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
        int     i = 1;
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
        
        while ( // time left AND ground not hit AND space left for results
                (gotStillTime(tStart,allowedTime)) &&
                (positions.get(i - 1).getLocation(setting).getH() >= 0) &&
                (i < SIZE)
              ) {
 
            ///////////////

            // compute new x,y coordinates and speed in this loop
                tempMovingBody = positions.get(i - 1);

                if (i == (6000 * k + 1)) {
                    k++;
                  System.out.println(tempMovingBody.getLocation(setting).getY());
                } // Ausgabe von 50 Zwischenwerten
                
                x = tempMovingBody.getLocation(setting).getX() + tempMovingBody.getVx() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                y = tempMovingBody.getLocation(setting).getY() + tempMovingBody.getVy() * dt + Forces.computeDxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;

                // new speeds
                vx = tempMovingBody.getVx() + Forces.computeDvxByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;
                vy = tempMovingBody.getVy() + Forces.computeDvyByForces(dt, tempMovingBody, setting, Forces.isActingBuoyancy(), Forces.isActingFlowResistance(), Forces.isActingGravity(), Forces.isActingViscosity()) ;

                //System.out.println(i);
                positions.add(new MovingBody(vx, vy, new Location(x, y, setting), setting));

                i++;
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

////// no code beyond this line !!!

                // correct: first add all forces, than divide by 2m, than multiply dt^2.
                // -------- later.
                //sumFx = 0.0;
                //sumFy = DiverseConstants.EQUATORIAL_SURFACE_GRAVITY_EARTH;

                // new coordinates

// final int SIZE = 300000; 
// PrintWriter pw = new PrintWriter("testt.txt"); 							// Datenstruktur
// Setting setting = new Setting(1,1254,1.48,Constants.RADIUS_EARTH,Constants.Mass_Earth,0.00001,10,Math.PI/4,10,4.2e-3,0,0,0.1,0);  // benutzerdefiniertes Setting
// Eingabe: (h, rho, eta, R, M, dt, v, beta, mass, vol, cw, a, radius, T)
// Start-MovingBody

// initialize the element at position 0 in the vector structure
//positions.add(new MovingBody(9.81, new Location(1,10,10,30), 15, 30, 100, 0.3));
// later the user provided values are listed here:
