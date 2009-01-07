/****************************************************************************
 * Author:  BE
 * Date:    2008-10-04
 * Use:     
 *          This class encapsulates the different preset-settings as well as
 *          the variable declarations for the most  recently  USER-ENTERED
 *          VALUES (via the `UserInputNewParametersUI'.
 * 
 */
package physics;

/**
 *
 * @author BE
 */
public class Settings {

    private static double height;
    private static double angle;
    private static double speed;
    private static double g;
    private static double mass;
    private static double volume;
    private static double deltaT;
    private static double computingTimeLimit;
    /////
    // internal values
    private static double vx;
    private static double vy;
    // end internal values
    /////
    
    // getters
    public static String getAllValuesFromSetting() {
        String CrLf = System.getProperty("line.separator");
        String tmp = "==========================================" + CrLf +
                "Angle   : " + getAngle() + CrLf +
                "G-factor: " + getG() + CrLf +
                "Height  : " + getHeight() + CrLf +
                "Speed   : " + getSpeed() + CrLf +
                "Mass    : " + getMass() + CrLf +
                "Volume  : " + getVolume() + CrLf +
                "Vx      : " + getVx() + CrLf +
                "Vy      : " + getVy() + CrLf;
        return tmp;
    }

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
    }
}
