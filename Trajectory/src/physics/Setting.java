/****************************************************************************
 * Author:  BE/CT
 * Date:    2008-10-04
 * Use:     
 *          This class encapsulates the different preset-settings as well as
 *          the variable declarations for the most  recently  USER-ENTERED
 *          VALUES (via the `UserInputNewParametersUI'.
 * TODO:    BE: Slightly better structure is to put `Setting' into `utilities'
 *          package (imho).
 * TODO:    degrees (user-interface) ----> radians
 * 
 */
package physics;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Setting {

  // properties:
  private double h;	// Starthoehe ueber Nullniveau
  private double rho;	// Dichte des Mediums auf Höhe Null
  private double eta;	// Viskositaet des Mediums am Ort des Starts
  private double R;	// Radius des Planeten
  private double M;	// Masse des Planeten
  private double dt;	// Zeitintervall ueber das genaehert wird
  private double v;	// Startgeschwindigkeit
  private double beta;	// Startwinkel gegenueber der x-Achse (Bogenmaß).
  private double mass;  // Masse des Koerpers
  private double vol;	// Volumen des Koerpers
  private double cw;	// Cw-Wert des Koerpers
  private double a;	// stroemungswirksame Flaeche des Koerpers
  private double radius;// Radius des Koerpers (bei Kugel)
  private double T;	// Zeitlimit fuer die Rechenzeit

  // constructors:
  // Constructor zur Erzeugung eines (einzelnen) Settings, numerisch
  public Setting(double h, double rho, double eta, double R, double M, double dt, double v, double beta, double mass, double vol, double cw, double a, double radius, double T) {
    this.h = h;
    this.rho = rho;
    this.eta = eta;
    this.R = R;
    this.M = M;
    this.dt = dt;
    this.v = v;
    this.beta = beta;
    this.mass = mass;
    this.vol = vol;
    this.cw = cw;
    this.a = a;
    this.radius = radius;
    this.T = T;
  }

  
  // use: Constructor for string values
  public Setting(String h, String rho, String eta, String R, String M, String dt, String v, String beta, String mass, String vol, String cw, String a, String radius, String T) {
    this.h = Double.parseDouble(h);
    this.rho = Double.parseDouble(rho);
    this.eta = Double.parseDouble(eta);
    this.R = Double.parseDouble(R);
    this.M = Double.parseDouble(M);
    this.dt = Double.parseDouble(dt);
    this.v = Double.parseDouble(v);
    this.beta = Double.parseDouble(beta);
    this.mass = Double.parseDouble(mass);
    this.vol = Double.parseDouble(vol);
    this.cw = Double.parseDouble(cw);
    this.a = Double.parseDouble(a);
    this.radius = Double.parseDouble(radius);
    this.T = Double.parseDouble(T);
  }
  
  
  // use: standard constructor; default: all values to 0.
  public Setting(){
    zeroSetting();
  }
  
  
  //getters
  public double getH() {
    return this.h;
  }

  public double getRho() {
    return this.rho;
  }

  public double getEta() {
    return this.eta;
  }

  public double getR() {
    return this.R;
  }

  public double getM() {
    return this.M;
  }

  public double getDt() {
    return this.dt;
  }

  public double getV() {
    return this.v;
  }

  public double getBeta() {
    return this.beta;
  }

  public double getMass() {
    return this.mass;
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

  public double getRadius() {
    return this.radius;
  }

  public double getT() {
    return this.T;
  }

  
  // setters
  public void setM(double M) {
    this.M = M;
  }

  public void setR(double R) {
    this.R = R;
  }

  public void setT(double T) {
    this.T = T;
  }

  public void setA(double a) {
    this.a = a;
  }

  public void setBeta(double beta) {
    this.beta = beta;
  }

  public void setCw(double cw) {
    this.cw = cw;
  }

  public void setDt(double dt) {
    this.dt = dt;
  }

  public void setEta(double eta) {
    this.eta = eta;
  }

  public void setH(double h) {
    this.h = h;
  }

  public void setMass(double mass) {
    this.mass = mass;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public void setRho(double rho) {
    this.rho = rho;
  }

  public void setV(double v) {
    this.v = v;
  }

  public void setVol(double vol) {
    this.vol = vol;
  }
  /////////////
  // methods //
  /////////////
  // use:  set all values in a setting-object at once.
  public void setSetting(
          double h, double rho, double eta,
          double R, double M, double dt,
          double v, double beta, double mass,
          double vol, double cw, double a,
          double radius, double T) {
    this.setH(h);
    this.setRho(rho);
    this.setEta(eta);
    this.setR(R);
    this.setM(M);
    this.setDt(dt);
    this.setV(v);
    this.setBeta(beta);
    this.setMass(mass);
    this.setVol(vol);
    this.setCw(cw);
    this.setA(a);
    this.setRadius(radius);
    this.setT(T);
  }
  // use:  set all values in a given setting to zero.
  public void zeroSetting() {
    this.setSetting(
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0, 0.0, 0.0,
            0.0, 0.0);
  }
  // use:  debug, informational; print all entries of an instance of class
  //       `Setting'.
  // pre:  caller is an instance of class `Setting'.
  // post: all properties are printed to command line.
  public void printSetting() {
    String CrLf = System.getProperty("line.separator");
    String tmp = "==========================================" + CrLf +
            "H        : " + getH() + CrLf +
            "v        : " + getV() + CrLf +
            "Beta:    : " + getBeta() + CrLf +
            "Mass     : " + getMass() + CrLf +
            "Volume   : " + getVol() + CrLf +
            "Cw       : " + getCw() + CrLf +
            "Area     : " + getA() + CrLf +
            "Rho      : " + getRho() + CrLf +
            "Eta      : " + getEta() + CrLf +
            "RadiusP  : " + getR() + CrLf +
            "Mass  P  : " + getM() + CrLf +
            "dt       : " + getDt() + CrLf +
            "Radius B : " + getRadius() + CrLf +
            "CTL      : " + getT() + CrLf;
    System.out.println(tmp);
  }
  // use:  set   all   properties  of the calling setting - instance via file.
  // pre:  caller is instance of class `Setting'. the file is  well  formatted
  // post: all  properties  are set  to  the  values  provided  by  the  file.
  public void loadValuesFromFile(File presetSettingFile)
          throws IOException {
    HashMap<String, String> store = new HashMap<String, String>(20);

    // set all values not explicitly listed in the `presetSettingFile' to zero
    zeroSetting();

    // fill the hashmap with the preset file´s values
    store = presetFileToHashMap(presetSettingFile);

    // set the properties of the calling setting-instance accordingly.
    if (store.containsKey("height")) {
      setH(Double.valueOf(store.get("height")));
    }
    if (store.containsKey("rho")) {
      setRho(Double.valueOf(store.get("rho")));
    }
    if (store.containsKey("eta")) {
      setEta(Double.valueOf(store.get("eta")));
    }
    if (store.containsKey("radiusPlanet")) {
      setR(Double.valueOf(store.get("radiusPlanet")));
    }
    if (store.containsKey("massPlanet")) {
      setM(Double.valueOf(store.get("massPlanet")));
    }
    if (store.containsKey("radiusPlanet")) {
      setR(Double.valueOf(store.get("radiusPlanet")));
    }

    if (store.containsKey("dt")) {
      setDt(Double.valueOf(store.get("dt")));
    }
    if (store.containsKey("v")) {
      setV(Double.valueOf(store.get("v")));
    }
    if (store.containsKey("beta")) {
      setBeta(Double.valueOf(store.get("beta")));
    }
    if (store.containsKey("massBody")) {
      setMass(Double.valueOf(store.get("massBody")));
    }
    if (store.containsKey("volumeBody")) {
      setVol(Double.valueOf(store.get("volumeBody")));
    }
    if (store.containsKey("cwBody")) {
      setCw(Double.valueOf(store.get("cwBody")));
    }

    if (store.containsKey("areaOfBody")) {
      setA(Double.valueOf(store.get("areaOfBody")));
    }
    if (store.containsKey("radiusOfBody")) {
      setRadius(Double.valueOf(store.get("radiusOfBody")));
    }
    if (store.containsKey("upperBoundComputingTime")) {
      setT(Double.valueOf(store.get("upperBoundComputingTime")));
    } 
    
  }
  
  
  // use:  to  read all  values of a `presetSettingFile' (user-written) into
  //       a  hashmap ,  which  is later  used  to load an instance of class
  //       `Setting'. refer to the  comments in the file `presets.earth.dat'
  //       for formatting issues.
  // pre:  a file given to the rules in `earth.dat'
  // post: a hashmap with the <key,value> pairs as stated  in the `file' arg
  //       is returned to the caller.
  private static HashMap<String, String> presetFileToHashMap(
          File presetSettingFile)
          throws IOException {
    BufferedReader br = null;
    String tmp;
    String strings[]; // keys later at strings[0], values at strings[1]
    HashMap<String, String> store = new HashMap<String, String>(20);
    // compile the pattern for one or more whitespaces
    Pattern p = Pattern.compile("[\\s]+");

    try {
      br = new BufferedReader(new FileReader(presetSettingFile));

      // as long as there is data read the file line by line
      while ((tmp = br.readLine()) != null) {
        if (!isCommentOrEmptyLine(tmp)) {
          // then we have data and can read keys, values, optional comments
          // seperatly
          strings = tmp.split(p.pattern(), 3);
          System.out.println("0: " + strings[0] + " 1: " + strings[1]);
          store.put(strings[0], strings[1]);
        } // else: a useless line...
      } // end while
    } catch (FileNotFoundException ex) {
      System.out.println("From Setting.load(): " + ex.toString());
    } finally {
      if (br != null) {
        br.close();
      } else {
        System.out.println("info: buffered reader never opened.");
      }
    }

    return store;
  }
  // use:  to determine whether a string is the empty one or a comment.
  // pre:  s is string
  // post: the return value is true if s is empty or a comment (a line pre-
  //       ceded by one or more `/' otherwise the return value is false.
  // TODO: put in a `stringMethod' package.
  private static boolean isCommentOrEmptyLine(String s) {
    String tmp = s.trim();
    return (tmp.startsWith("/") || (tmp.length() == 0));
  }  // ------------------------ only old code beyond this line ----------- //
  //System.out.println("####" + isCommentOrEmptyLine(tmp) + " " + tmp.length());
  // getters:
   /*
  public static double getAngle() {
  return angle;
  }
  
  public static double getComputingTimeLimit() {
  return computingTimeLimit;
  }
  
  public static double getDeltaT() {
  return deltaT;
  }
  
  public static double getG() {
  return g;
  }
  
  public static double getHeight() {
  return height;
  }
  
  public static double getMass() {
  return mass;
  }
  
  public static double getSpeed() {
  return speed;
  }
  
  public static double getVolume() {
  return volume;
  }
  
  public static double getVx() {
  return vx;
  }
  
  public static double getVy() {
  return vy;
  }
  // EVERY setter-method is overloaded in order to be applied to
  // `double' and `String' argument.
  public static void setAngle(double angle) {
  Settings.angle = angle;
  }
  
  public static void setAngle(String angle) {
  Settings.angle = (Double.valueOf(angle)).doubleValue();
  }
  
  public static void setComputingTimeLimit(double computingTimeLimit) {
  Settings.computingTimeLimit = computingTimeLimit;
  }
  
  public static void setComputingTimeLimit(String limit) {
  Settings.computingTimeLimit = (Double.valueOf(limit)).doubleValue();
  }
  
  public static void setDeltaT(double deltaT) {
  Settings.deltaT = deltaT;
  }
  
  public static void setDeltaT(String deltaT) {
  Settings.deltaT = (Double.valueOf(deltaT)).doubleValue();
  }
  
  public static void setG(double g) {
  Settings.g = g;
  }
  
  public static void setG(String g) {
  Settings.g = (Double.valueOf(g)).doubleValue();
  }
  
  public static void setHeight(double height) {
  Settings.height = height;
  }
  
  public static void setHeight(String height) {
  Settings.height = (Double.valueOf(height)).doubleValue();
  }
  
  public static void setMass(double mass) {
  Settings.mass = mass;
  }
  
  public static void setMass(String mass) {
  Settings.mass = (Double.valueOf(mass)).doubleValue();
  }
  
  // USE:   Compute the components of the velocity vector immediatly and set
  //        them.
  // PRE:  `Settings.angle' must be already set.
  // POST: `Settings.vx', `Settings.vy' are computed and set.
  public static void setSpeed(double speed) {
  Settings.speed = speed;
  
  Settings.setVx(Math.cos(Math.toRadians(Settings.getAngle())) * speed);
  Settings.setVy(Math.sin(Math.toRadians(Settings.getAngle())) * speed);
  System.out.println("vx: " + Settings.getVx() + "vy: " + Settings.getVy());
  }
  
  public static void setSpeed(String speed) {
  double tmpSpeed = (Double.valueOf(speed)).doubleValue();
  Settings.setSpeed(tmpSpeed);
  }
  
  public static void setVolume(double volume) {
  Settings.volume = volume;
  }
  
  public static void setVolume(String volume) {
  Settings.volume = (Double.valueOf(volume)).doubleValue();
  }
  // For internal use only, as this is the SETTINGS class, and not the
  // MOVINGBODY class. In the latter, it is utterly important to update
  // vx, vy, but this is what you might have already guessed.
  private static void setVx(double vx) {
  Settings.vx = vx;
  }
  
  private static void setVy(double vy) {
  Settings.vy = vy;
  }*/
}
