/*****************************************************************************
 * Author: 	BE/CT
 * Date:	2009-09-23 ...
 * Use:		Class `Forces' keeps all predefined  formulae for  the  forces
 *              acting   upon   the  instance  of  class  `MovingBody' . Users
 *              implementing  their  own  functions are encouraged to put them 
 *              into this class.

 * 
 * TODO:
 * 	B!  BE: for   each   pair   of   components   (dx,dy)   we   have  one
 *          redundant computation,  as  the  terms for these differ   only  in
 *          `rvectorx', `rvectory'.  We  can  optimize  manually , if coerced.
 *          The  same  holds  for  each  pair  (dvx,dvy) .  Trade -off (here):
 *          less readable code -> better performance,
 *          lower performance  -> better readable code.
 *          (addendum:  the  longer  I  look  at the methods the clumsier they
 *                      become, but still B-C priority).
 *
 *      o   CT: Ueberpruefen: Eventuell  kann   bei  getLocation(setting)  auf
 *          setting verzichtet werden.
 * 		  -> BE: maybe one  can  make  the  member  location  in class
 *      	`    MovingBody' public .  The  members  in  `location' remain
 *                   private  ,   but   than   one   can   access   it   like:
 *                   movingBody.location.getXXX()'	
 * 			
 *      B!  BE: Putting `0.5*Math.pow(dt,2)'  in  the  main loop  should  save
 *                computing  time ,  this  way  it  is  more  readable for the
 *                uninvolved reader (imho).
 * 
 *      A!  BE: Clarify: We  leave  out the Reynolds-number  decision (laminar
 * 		  to turbulent) ?
 * 
 *      B!  BE: By  returning  the values immediatly from the methods  without
 *                creating a variable, one could save further time, if not al-
 *                ready done by the bytecode-compiler.
 *      B!  BE: 
 */
package physics;

public class Forces {

    // switches
    private static boolean isActingBuoyancy       = false;
    private static boolean isActingFlowResistance = false;
    private static boolean isActingGravity        = false;
    private static boolean isActingViscosity      = false;

    // getters
    public static boolean isActingBuoyancy() {
        return isActingBuoyancy;
    }

    public static boolean isActingFlowResistance() {
        return isActingFlowResistance;
    }

    public static boolean isActingGravity() {
        return isActingGravity;
    }

    public static boolean isActingViscosity() {
        return isActingViscosity;
    }

    // setters
    public static void setIsActingBuoyancy(boolean isActingBuoyancy) {
        Forces.isActingBuoyancy = isActingBuoyancy;
    }

    public static void setIsActingFlowResistance(
            boolean isActingFlowResistance) {
        Forces.isActingFlowResistance = isActingFlowResistance;
    }

    public static void setIsActingGravity(boolean isActingGravity) {
        Forces.isActingGravity = isActingGravity;
    }

    public static void setIsActingViscosity(boolean isActingViscosity) {
        Forces.isActingViscosity = isActingViscosity;
    }

    public static void setActingForces(
            boolean isActingBuoyancy,
            boolean isActingFlowResistance,
            boolean isActingGravity,
            boolean isActingViscosity) {
        Forces.isActingBuoyancy = isActingBuoyancy;
        Forces.isActingFlowResistance = isActingFlowResistance;
        Forces.isActingGravity = isActingGravity;
        Forces.isActingViscosity = isActingViscosity;
    }
    
    // use: print selected forces to command line.
    public static void currentForces(){
        System.out.println("currentForces:");
        String tmp = "";
        //String CrLf = System.getProperty("line.separator");
        if (isActingBuoyancy()){
            tmp = tmp + "B; ";
        }
        if (isActingFlowResistance()){
            tmp = tmp + "F; ";
        }
        if (isActingGravity()){
            tmp = tmp + "G; ";
        }
        if (isActingViscosity()){
            tmp = tmp + "V.";
        }
      System.out.println(tmp);
    }
    
    /////////////
    // methods //
    /////////////
    
    // Ortsaenderungen der Form: ds = 0.5*dt^2*F/m
    // Gravitation:
    public static double gravityForceX(double dt, MovingBody movingBody, Setting setting) {
        double dx;
        dx = 0.5 * Math.pow(dt, 2) * (((Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * movingBody.getLocation(setting).getRvectorx());
        return dx;
    }

    public static double gravityForceY(double dt, MovingBody movingBody, Setting setting) {
        double dy;
        dy = 0.5 * Math.pow(dt, 2) * (((Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * movingBody.getLocation(setting).getRvectory());
        return dy;
    }

    // Auftrieb:
    public static double buoyantForceX(double dt, MovingBody movingBody, Setting setting) {
        double dx;
        dx = 0.5 * Math.pow(dt, 2) * ((((movingBody.getLocation(setting).getRho() * movingBody.getVol() * Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * (-1) * movingBody.getLocation(setting).getRvectorx()) / movingBody.getM());
        return dx;
    }

    public static double buoyantForceY(double dt, MovingBody movingBody, Setting setting) {
        double dy;
        dy = 0.5 * Math.pow(dt, 2) * ((((movingBody.getLocation(setting).getRho() * movingBody.getVol() * Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * (-1) * movingBody.getLocation(setting).getRvectory()) / movingBody.getM());
        return dy;
    }

    // Stroemungswiderstand:
    public static double flowResistanceX(double dt, MovingBody movingBody, Setting setting) { // *
        double dx;
        dx = 0.5 * Math.pow(dt, 2) * (((0.5 * movingBody.getCw() * movingBody.getLocation(setting).getRho() * movingBody.getA() * Math.pow(movingBody.getV(), 2)) * (-1) * movingBody.getVvectorx()) / movingBody.getM());
        return dx;
    }

    public static double flowResistanceY(double dt, MovingBody movingBody, Setting setting) { //*
        double dy;
        dy = 0.5 * Math.pow(dt, 2) * (((0.5 * movingBody.getCw() * movingBody.getLocation(setting).getRho() * movingBody.getA() * Math.pow(movingBody.getV(), 2)) * (-1) * movingBody.getVvectory()) / movingBody.getM());
        return dy;
    }

    // innere Reibung:
    public static double viscosityForceX(double dt, MovingBody movingBody, Setting setting) { //*
        double dx;
        dx = 0.5 * Math.pow(dt, 2) * (((6 * Math.PI * movingBody.getR() * movingBody.getLocation(setting).getEta() * movingBody.getV()) * (-1) * movingBody.getVvectorx()) / movingBody.getM());
        return dx;
    } // gilt nur fuer kugelfoermige Koerper

    public static double viscosityForceY(double dt, MovingBody movingBody, Setting setting) { //*
        double dy;
        dy = 0.5 * Math.pow(dt, 2) * (((6 * Math.PI * movingBody.getR() * movingBody.getLocation(setting).getEta() * movingBody.getV()) * (-1) * movingBody.getVvectory()) / movingBody.getM());
        return dy;
    }	// gilt nur fuer kugelfoermige Koerper

    // Geschwindigkeits�nderungen der Form: dv = dt*F/m
    // Gravitation:
    public static double gravityForceVx(double dt, MovingBody movingBody, Setting setting) {
        double dvx;
        dvx = dt * (((Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * movingBody.getLocation(setting).getRvectorx());
        return dvx;
    }

    public static double gravityForceVy(double dt, MovingBody movingBody, Setting setting) {
        double dvy;
        dvy = dt * (((Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * movingBody.getLocation(setting).getRvectory());
        return dvy;
    }

    // Auftrieb:
    public static double buoyantForceVx(double dt, MovingBody movingBody, Setting setting) {
        double dvx;
        dvx = dt * ((((movingBody.getLocation(setting).getRho() * movingBody.getVol() * Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * (-1) * movingBody.getLocation(setting).getRvectorx()) / movingBody.getM());
        return dvx;
    }

    public static double buoyantForceVy(double dt, MovingBody movingBody, Setting setting) {
        double dvy;
        dvy = dt * ((((movingBody.getLocation(setting).getRho() * movingBody.getVol() * Constants.G * setting.getM()) / Math.pow(movingBody.getLocation(setting).getR(), 2)) * (-1) * movingBody.getLocation(setting).getRvectory()) / movingBody.getM());
        return dvy;
    }

    // Stroemungswiderstand:
    // TODO: C: inconsistant with the naming scheme of the others
    public static double flowResistanceVx(double dt, MovingBody movingBody, Setting setting) { // *
        double dvx;
        dvx = dt * (((0.5 * movingBody.getCw() * movingBody.getLocation(setting).getRho() * movingBody.getA() * Math.pow(movingBody.getV(), 2)) * (-1) * movingBody.getVvectorx()) / movingBody.getM());
        return dvx;
    }

    public static double flowResistanceVy(double dt, MovingBody movingBody, Setting setting) { //*
        double dvy;
        dvy = dt * (((0.5 * movingBody.getCw() * movingBody.getLocation(setting).getRho() * movingBody.getA() * Math.pow(movingBody.getV(), 2)) * (-1) * movingBody.getVvectory()) / movingBody.getM());
        return dvy;
    }

    // innere Reibung (gilt nur fuer kugelfoermige Koerper):
    public static double viscosityForceVx(double dt, MovingBody movingBody, Setting setting) { //*
        double dvx;
        dvx = dt * (((6 * Math.PI * movingBody.getR() * movingBody.getLocation(setting).getEta() * movingBody.getV()) * (-1) * movingBody.getVvectorx()) / movingBody.getM());
        return dvx;
    }
    // gilt nur fuer kugelfoermige Koerper:
    public static double viscosityForceVy(double dt, MovingBody movingBody, Setting setting) { //*
        double dvy;
        dvy = dt * (((6 * Math.PI * movingBody.getR() * movingBody.getLocation(setting).getEta() * movingBody.getV()) * (-1) * movingBody.getVvectory()) / movingBody.getM());
        return dvy;
    }
    
    
    /*---------------------- shortcut methods ------------------------------*/
    
    // TODO: B: see discussion on optimization, as this is truely inefficient.
    //       there is _lot_ of potential for doing better.
    // TODO: B: methods -> private 
    //
    // use:  encapsulating all the methods for the acting forces, as their
    //       contribution to the result is user-choosen.
    // pre:  selection already took place via setting the flags accordingly.
    // post: the return value is the sum of the displacement in x-direction
    //       caused by the forces acting over the time interval `dt' on the
    //       `movingBody' instance (taking the environment into account via
    //       `setting')
    public static double computeDxByForces(
            double dt, MovingBody movingBody, Setting setting,
            boolean isActingBuoyancy, boolean isActingFlowResistance,
            boolean isActingGravity, boolean isActingViscosity){
     double sum = 0.0;
        
     if (isActingBuoyancy) sum += buoyantForceX(dt,movingBody,setting);
     if (isActingFlowResistance) sum += flowResistanceX(dt,movingBody,setting);
     if (isActingGravity) sum+= gravityForceX(dt,movingBody,setting);
     if (isActingViscosity) sum += viscosityForceX(dt,movingBody,setting);
            
     return sum;
    } // end of computeDxByForces()
    
    
    // use:  analogous to `computeDxByForces(), this time for dy.
    // pre:  a.a.
    // post: a.a.
     public static double computeDyByForces(
            double dt, MovingBody movingBody, Setting setting,
            boolean isActingBuoyancy, boolean isActingFlowResistance,
            boolean isActingGravity, boolean isActingViscosity){
      double sum = 0.0;
        
      if (isActingBuoyancy) sum += buoyantForceY(dt,movingBody,setting);
      if (isActingFlowResistance) sum += flowResistanceY(dt,movingBody,setting);
      if (isActingGravity) sum+= gravityForceY(dt,movingBody,setting);
      if (isActingViscosity) sum += viscosityForceY(dt,movingBody,setting);
            
     return sum;
    } // end of computeDyByForces()
    
   // use:  compute dvx as a result by all contributing forces.
   // pre:  a.a.
   // post: a.a.
    public static double computeDvxByForces(
            double dt, MovingBody movingBody, Setting setting,
            boolean isActingBuoyancy, boolean isActingFlowResistance,
            boolean isActingGravity, boolean isActingViscosity){
      double sum = 0.0;
        
      if (isActingBuoyancy) sum += buoyantForceVx(dt,movingBody,setting);
      if (isActingFlowResistance) sum += flowResistanceVx(dt,movingBody,setting);
      if (isActingGravity) sum+= gravityForceVx(dt,movingBody,setting);
      if (isActingViscosity) sum += viscosityForceVx(dt,movingBody,setting);
            
     return sum;
    } // end of computeDvxByForces() 
    
    
   // use:  compute dvy as a result by all contributing forces.
   // pre:  a.a.
   // post: a.a.
    public static double computeDvyByForces(
            double dt, MovingBody movingBody, Setting setting,
            boolean isActingBuoyancy, boolean isActingFlowResistance,
            boolean isActingGravity, boolean isActingViscosity){
      double sum = 0.0;
        
      if (isActingBuoyancy) sum += buoyantForceVy(dt,movingBody,setting);
      if (isActingFlowResistance) sum += flowResistanceVy(dt,movingBody,setting);
      if (isActingGravity) sum+= gravityForceVy(dt,movingBody,setting);
      if (isActingViscosity) sum += viscosityForceVy(dt,movingBody,setting);
            
     return sum;
    } // end of computeDvyByForces() 
    
    
} // end of class `Forces'


