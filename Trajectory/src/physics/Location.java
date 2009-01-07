/*****************************************************************************
 * Author: 	BE/CT
 * Date:	2009-09-23
 * Purpose: This is the class implementing the `Location' of a `MovingBody'.
 * 
 * todo:	
 * 			o CT: setAlpha() vervollst�ndigen!
 * 
 */
package physics;
//import physics.Constants;

public class Location {

	// properties:
	private double x;			// x-Position
	private double y;			// y-Position
	private double r;			// Abstand zum Planetenmittelpunkt
	private double rvectorx;	// Einheitsvektorkomponente x von location zu Mittelpunkt
	private double rvectory;	// Einheitsvektorkomponente y von location zu Mittelpunkt
	private double h;			// H�he �ber Nullniveau
	private double rho;			// Dichte des Mediums (in kg/m^3)
	private double eta;			// Viskosit�t des Mediums
	private double alpha;		// Winkel bez�glich der y-Achse (Bogenma�)
        
        
	// constructors:
	
	// Constructor f�r Start-Location
	public Location(Setting setting){
		this.x = 0;
		this.y = setting.getH();
		this.setRcomponents(0, setting.getH(), setting);
		this.rho = setting.getRho();
		this.eta = setting.getEta();
		this.alpha = 0;}									

	// allgemeiner Constructor
	public Location(double x, double y, double rho, double eta, Setting setting){
		this.x = x;
		this.y = y;
		this.setRcomponents(x, y, setting);
		this.rho = rho;
		this.eta = eta;
		this.setAlpha(x, y, setting);}							
	
	// Constructor f�r konstantes Rho und Eta
	public Location(double x, double y, Setting setting){
		this.x = x;
		this.y = y;
		this.setRcomponents(x, y, setting);
		this.rho = setting.getRho();
		this.eta = setting.getEta();
		this.setAlpha(x, y, setting);}						
	
	// getters: 
	public double getX(){
		return this.x;}	

	public double getY(){
		return this.y;}

	public double getR(){
		return this.r;}

	public double getRvectorx(){
		return this.rvectorx;}

	public double getRvectory(){
		return this.rvectory;}

	public double getH(){
		return this.h;}

	public double getRho(){
		return this.rho;}

	public double getEta(){
		return this.eta;}

	public double getAlpha(){
		return this.alpha;}
	
	// setters: 
	public void setLocation(double x, double y, double rho, double eta, Setting setting){
		this.x = x;
		this.y = y;
		this.setRcomponents(x, y, setting);
		this.rho = rho;
		this.eta = eta;
		this.setAlpha(x, y, setting);}							// allgemeiner Setter

	public void setRho(double rho) {
		this.rho = rho;}
	
	public void setEta(double eta) {
		this.eta = eta;}
	
	// private set-Methode f�r r, rvectorx, rvectory, h
	// Anmerkung: �ber die set-Methoden ist kein vollst�ndiger Zugriff auf das Objekt m�glich,
	// im Rahmen der Programmstruktur ist dies aber (noch) nicht n�tig
	private void setRcomponents(double x, double y, Setting setting){
		this.r = Math.sqrt(Math.pow(((-1)*x),2)+Math.pow(((-1)*setting.getR()-y),2)); 	// Betrag des Verbindungsvektors location zu Mittelpunkt
		this.rvectorx = ((-1)*x)/this.r; 												// normierte x-Komponente von Vektor r
		this.rvectory = ((-1)*setting.getR()-y)/this.r; 								// normierte y-Komponente von Vektor r
		this.h = this.r-setting.getR();}												// H�he �ber Nullniveau						
																
	private void setAlpha(double x, double y, Setting setting){
		this.alpha = 0;}										// private set-Methode f�r alpha
	
	// use:	print instance of class `Location' on command line.
	// rem:	every newly created property/debugInfo should be listed here.
	public void printLocation(){
		System.out.println("------- Location --------");
		System.out.println("x: " + this.x);
		System.out.println("y: " + this.y);
		System.out.println("r: " + this.r);
		System.out.println("rvectorx: " + this.rvectorx);
		System.out.println("rvectory: " + this.rvectory);
		System.out.println("h: " + this.h);
		System.out.println("rho: " + this.rho);
		System.out.println("eta: " + this.eta);
		System.out.println("alpha: " + this.alpha);}
	
}
