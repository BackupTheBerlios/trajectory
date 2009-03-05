/*****************************************************************************
 * Author: CT/BE
 * Date:   Oct 2008
 * Use:	   Class `MovingBody' is the class keeping the properties of the body
 * 	   being  analyzed  concerning  its  motion in the context of a given
 * 	   `Setting'.
 * 
 * TODO:   C!among  the  dreams  of the future are e.g.: variable volume of a	
 *         body, variable density of the atmosphere, user-customed input of
 * 	   acting forces -> interpreter, (BeanShell or the like)
 * 	  
 * 
 * TODO: CT: eventuell kann bei getLocation() auf die Erzeugung eines neuen
 *           Objekts verzichtet werden!
 *       --> BE: The early design of class `Settings' and class `Location'
 *           aimed towards keeping them disjunct (see CVS version 1.1 for
 *           both). Trouble begins with interweaving both. Both blind.
 *       
 *       BE: one could tighten the constructors´code, but that´s just for the
 *           record :-) (TODO: Z )   
 * 
 * TODO: BE: peyaminator: restrict the length of entries in every TextField !!
 * 
 */

package physics;

public class MovingBody {

  //properties:
  private double vx;	// Geschwindigkeitskomponente x
  private double vy;	// Geschwindigkeitskomponente y
  private double v;	// Betrag der Geschwindigkeit
  private double vvectorx;// Einheitsvektorkomponente x der Geschwindigkeit  
  private double vvectory;// Einheitsvektorkomponente y der Geschwindigkeit 
  private double beta;	// Winkel der Bewegungsrichtung gegenueber x-Achse (rad)
  private double m;	// Masse des Koerpers
  private double vol;	// Volumen des Koerpers
  private double cw;	// Cw-Wert des Koerpers
  private double a;	// stroemungswirksame Flaeche des Koerpers
  private double r;	// Radius des Koerpers (bei Kugel)
  private Location location; // Eigenschaften des Orts des Koerpers
  private double angleVvectorToRvector; // 

  // Constructors
  // Constructor fuer Start-MovingBody
  public MovingBody(Setting setting) {
    this.v = setting.getV();
    this.beta = setting.getBeta();
    this.setVcomponentsVBeta(this.v, this.beta);
    this.m = setting.getMass();
    this.vol = setting.getVol();
    this.cw = setting.getCw();
    this.a = setting.getA();
    this.r = setting.getRadius();
    this.location = new Location(setting);
    this.angleVvectorToRvector = computeAngleVvectorToRvector(setting);
  }
  
  // Allgemeiner Constructor
  public MovingBody(double vx, double vy, Location location, Setting setting) {
    this.vx = vx;
    this.vy = vy;
    this.setVcomponentsVxVy(this.vx, this.vy);
    this.m = setting.getMass();
    this.vol = setting.getVol();
    this.cw = setting.getCw();
    this.a = setting.getA();
    this.r = setting.getRadius();
    this.location = location;
    this.angleVvectorToRvector = computeAngleVvectorToRvector(setting);
  }
  
  //getters
  public double getVx() {
    return this.vx;
  }

  public double getVy() {
    return this.vy;
  }

  public double getV() {
    return this.v;
  }

  public double getVvectorx() {
    return this.vvectorx;
  }

  public double getVvectory() {
    return this.vvectory;
  }

  public double getBeta() {
    return this.beta;
  }

  public double getM() {
    return this.m;
  }

  public double getVol() {
    return this.vol;
  }

  public double getCw() {
    return this.cw;
  }

  public double getA() {
    return this.a;
  }

  public double getR() {
    return this.r;
  }

  public Location getLocation(Setting setting) {
    Location tempLocation = new Location(
            this.location.getX(),
            this.location.getY(),
            this.location.getRho(),      
            setting);
    return tempLocation;
  }

  public double getAngleVvectorToRvector() {
    return angleVvectorToRvector;
  }
  
  //setters
  public void setA(double a) {
    this.a = a;
  }

  public void setBeta(double beta) {
    this.beta = beta;
  }

  public void setCw(double cw) {
    this.cw = cw;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public void setM(double m) {
    this.m = m;
  }

  public void setR(double r) {
    this.r = r;
  }

  public void setV(double v) {
    this.v = v;
  }

  public void setVol(double vol) {
    this.vol = vol;
  }

  public void setVvectorx(double vvectorx) {
    this.vvectorx = vvectorx;
  }

  public void setVvectory(double vvectory) {
    this.vvectory = vvectory;
  }

  public void setVx(double vx) {
    this.vx = vx;
  }

  public void setVy(double vy) {
    this.vy = vy;
  }

  public void setAngleVvectorToRvector(double angleVvectorToRvector) {
    this.angleVvectorToRvector = angleVvectorToRvector;
  }
  
 
/////////////
// methods //
/////////////
  
  private void setVcomponentsVBeta(double v, double beta) {
    this.vx = Math.cos(beta) * v;
    this.vy = Math.sin(beta) * v;
    if (v==0) {
      this.vvectorx = 0;
      this.vvectory = 0;
    } else {
        this.vvectorx = this.vx / v;
        this.vvectory = this.vy / v;
    }
  }
  
  private void setVcomponentsVxVy(double vx, double vy) {
    this.v = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    if (v != 0) {
      this.beta = Math.acos(vx / v);
      this.vvectorx = vx / this.v;
      this.vvectory = vy / this.v;
    } else {
      System.out.println("|v|=0, division through zero caught.");
    }
  }

  // use:  compute the angle enclosed by the velocity vector and the current
  //       position.
  // pre:  it is crucial, that a call to this method is made after having
  //       called each of the vector-related methods.
  // post: the return value is the angle between both vectors in degree.
  private double computeAngleVvectorToRvector(Setting setting) {
    // scalar product (v,R)
    // n.b.: if v==0 then DIV 0 ! if not catched.
    //       v==0 -> 0 deg.
    if (getV() == 0)
      return 0.0;
    else
      return (Math.acos( 
                (getVvectorx() * getLocation(setting).getRvectorx()+
                 getVvectory() * getLocation(setting).getRvectory()  )
                       ) * 360 ) / (Math.PI * 2);
  }
  
  // use: print all data of an instance of `MovingBody' on console
  public void printMovingBody() {
    System.out.println("-----------------------------------");
    System.out.println("vx:" + vx);
    System.out.println("vy:" + vy);
    System.out.println("v:" + v);
    System.out.println("vvectorx:" + vvectorx);
    System.out.println("vvectory:" + vvectory);
    System.out.println("beta:" + beta);
    System.out.println("m:" + m);
    System.out.println("vol:" + vol);
    System.out.println("cw:" + cw);
    System.out.println("a (area):" + a);
    System.out.println("r (radius):" + r);
    System.out.println("location:" + location);
    System.out.println("angleVvectorToRvector:" + angleVvectorToRvector); // 
    System.out.println("-----------------------------------");
  }
  
} // end `MovingBody'
