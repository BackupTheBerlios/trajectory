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
            this.location.getEta(),
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
    this.vvectorx = this.vx / v;
    this.vvectory = this.vy / v;
  }

  private void setVcomponentsVxVy(double vx, double vy) {
    this.v = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    this.beta = Math.acos(vx / v);						// acos = arccos!
    this.vvectorx = vx / this.v;
    this.vvectory = vy / this.v;
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
  
} // end `MovingBody'


// no code beyond this point!!!

// wie bei Location ist f�r die Programmstruktur (noch) kein vollst�ndiger Zugriff auf das Objekt n�tig

/*// constructor: without arguments, set for earth //
public MovingBody(){
this.setG(DiverseConstants.EQUATORIAL_SURFACE_GRAVITY_EARTH);
this.setLocation(new Location());
this.setVx(0.0);
this.setVy(0.0);
this.setMass(10.0);
}

public MovingBody(double g, Location location, double vx, double vy, double mass,
double volume){
this.g = g;
this.location = location;
this.vx = vx;
this.vy = vy;
this.mass = mass;
this.volume = volume;
}


// getters //
public double getG(){
return this.g;
}

// use:
public Location getLocation(){
Location tmp = new Location(this.location.getDensity(),
this.location.getR(),
this.location.getX(),
this.location.getY());
return tmp;
}

public double getVx(){
return vx;
}
public double getVy(){
return vy;
}
public double getMass(){
return mass;
}
public double getVolume(){
return volume;
}

// setters //
public void setG(double g){
this.g = g;
}
public void setLocation(Location aLocation){
this.location = aLocation;
}
public void setVx(double vx){
this.vx = vx;
}
public void setVy(double vy){
this.vy = vy;
}
public void setMass(double mass){
this.mass = mass;
}
public void setVolume(double volume){
this.volume = volume;
}

// methods //

// use: print all data of an instance of `MovingBody' on console
public void printMovingBody(){
System.out.println("-----------------------------------");
System.out.println("g: " + this.g);
System.out.println("location x: " + this.location.getX());
System.out.println("location y: " + this.location.getY());
System.out.println("vx: " + this.getVx());
System.out.println("vx: " + this.getVy());
System.out.println("mass: " + this.getMass());
System.out.println("volume: " + this.volume);
}

// use: move an instance of this class by deltaX, deltaY resp.
public void move(double deltaX, double deltaY){
this.location.setX(this.location.getX() + deltaX);
this.location.setY(this.location.getY() + deltaY);
}

// use: accelerate an instance of this class by deltaVx, deltaVy resp.
public void accelerate(double deltaVx, double deltaVy){
this.setVx(this.getVx()+ deltaVx);
this.setVy(this.getVy()+ deltaVy);
}*/
	