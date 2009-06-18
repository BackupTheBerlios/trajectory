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

    // global physical constants:

	public final static double G = 6.67428e-11;	// Gravitationskonstante
    public final static double c = 299792458;	// Lichtgeschwindigkeit
    public final static double g = 9.8013747;   // m/(sec^2) Erdbeschleunigung,
    //Äquator, Höhe Null, von Hand mit den verwendeten Werten für G und R und M berechnet

	
	// for the time of development these values are constants... //

	private final static double ONE_MILLION       = 1000000;
	public  final static double MAX_WIDTH_SIMUL   = ONE_MILLION;
	public  final static double MAX_HEIGHT_SIMUL  = ONE_MILLION;
	public  final static int SCREEN_WIDTH         = 1024;
	public  final static int SCREEN_HEIGHT        = 768;
	public  final static int X_DIRECTION          = 0;
	public  final static int Y_DIRECTION          = 1;
	
    
    //Medien - Dichte, Viskosität (kg/m³, Ns/m²) (20°C, Normaldruck):
	  
    public final static double DENSITY_AIR                  = 1.204;
	public final static double VISCOSITY_AIR                = 17.1e-6;

    public final static double DENSITY_WATER                = 998.2;
	public final static double VISCOSITY_WATER              = 1.0e-3;

    public final static double DENSITY_GLYCERIN             = 1260;
	public final static double VISCOSITY_GLYCERIN           = 1.48;

    public final static double DENSITY_PARAFFIN            = 740;
	public final static double VISCOSITY_PARAFFIN          = 0.65e-3;

    public final static double DENSITY_MERCURY              = 13546;
	public final static double VISCOSITY_MERCURY            = 1.55e-3;

    public final static double DENSITY_HONEY                = 1400;
	public final static double VISCOSITY_HONEY              = 10;

    public final static double DENSITY_HELIUM               = 0.178;
	public final static double VISCOSITY_HELIUM             = 18.6e-6;

    public final static double DENSITY_HYDROGEN             = 0.08988;
	public final static double VISCOSITY_HYDROGEN           = 8.4e-6;


    public final static double DENSITY_KOHLENMONOXID        = 1.250;    //Kohlenmonoxid
	public final static double DENSITY_STICKSTOFF           = 1.251;    //Stickstoff
	public final static double DENSITY_STICKSTOFFMONOXID    = 1.340;    //Stickstoffmonoxid
	public final static double DENSITY_ETHAN                = 1.356;    //Ethan
	public final static double DENSITY_SAUERSTOFF           = 1.429;    //Sauerstoff
	public final static double DENSITY_KOHLENSTOFFDIOXID	= 1.977;	//Kohlenstoffdioxid
	public final static double DENSITY_SCHWEFELDIOXID       = 2.926;    //Schwefeldioxid
	public final static double DENSITY_CHLOR                = 3.214;    //Chlor
	public final static double DENSITY_KRYPTON              = 3.479;    //Krypton
    public final static double DENSITY_XENON                = 5.897;    //Xenon
	public final static double DENSITY_RADON                = 9.73;     //Radon
   
    
    //Körper - Cw-Werte:

    public final static double Cw_SPHERE                    = 0.45;//
	public final static double Cw_HALBKUGELSCHALE_KONKAV    = 1.35;   
 	public final static double Cw_HALBKUGELSCHALE_KONVEX    = 0.34;
    public final static double Cw_HALBKUGEL                 = 0.42;
    public final static double Cw_DISC                      = 1.12;		
	public final static double Cw_PARACHUTE                 = 1.33;//  
	public final static double Cw_PENGUIN                   = 0.03;//
    public final static double Cw_HUMAN                     = 0.78;//
    public final static double Cw_KEGEL                     = 1.35;    //60°
    public final static double Cw_CUBE                      = 1.05;//  //ungünstigste Lage, bestenfalls 0.8
    public final static double Cw_KREISZYLINDER             = 0.82;    //bzw. 1.15
    public final static double Cw_STROMLINIENKÖRPER         = 0.04;
    public final static double Cw_STRÖMUNGSPROFIL           = 0.056;
	public final static double Cw_AIRFOIL                   = 0.08;//  //Tragflügel
    public final static double Cw_GLEITSCHIRM               = 0.6;


    //Planet - Masse, Radius, Dichte (kg, m, kg/m³):

    public final static double MASS_EARTH               = 5.974e24;
    public final static double RADIUS_EARTH             = 6378100;
    public final static double DENSITY_EARTH            = 1.204;
    public final static double VISCOSITY_EARTH          = 17.1e-6;

    public final static double MASS_MARS                = 6.419e23;
    public final static double RADIUS_MARS              = 3397000;

    public final static double MASS_MERCUR              = 3.302e23;
    public final static double RADIUS_MERCUR            = 2439500;

    public final static double MASS_VENUS               = 4.869e24;
    public final static double RADIUS_VENUS             = 6052000;
    public final static double DENSITY_VENUS            = 60.2;
    public final static double VISCOSITY_VENUS          = 855e-6;

  
    // other celestial bodies
    
    // Earth´s moon
    public final static double MASS_MOON                = 7.349e22;
    public final static double RADIUS_MOON              = 1738000;

    //preset Bodys, density:

    public final static double DENSITY_POLYSTYRENE      = 15;
    public final static double DENSITY_IRON             = 7800;
    public final static double DENSITY_WOOD             = 500;
    public final static double DENSITY_PLUMBUM          = 11300;
    public final static double DENSITY_KORK             = 150;
    public final static double DENSITY_GRANIT           = 2800;
    public final static double DENSITY_ALU              = 2700;
    public final static double DENSITY_ICE              = 920;


    //preset Bodys, mass, volume, radius, area:

    public final static double MASS_PENGUIN             = 10;
    public final static double VOLUME_PENGUIN           = 0.00236;
    public final static double AREA_PENGUIN             = 0.00785;

    public final static double MASS_HUMAN               = 70;
    public final static double VOLUME_HUMAN             = 0.1275;
    public final static double AREA_HUMAN               = 0.075;

    public final static double MASS_PARACHUTE           = 70;    //with human
    public final static double VOLUME_PARACHUTE         = 0.1275;
    public final static double AREA_PARACHUTE           = 40;

    public final static double MASS_AIRFOIL             = 1;
    public final static double VOLUME_AIRFOIL           = 1;
    public final static double AREA_AIRFOIL             = 1;

    public final static double MASS_SPHERE_POLYSTYRENE  = 0.212058;
    public final static double MASS_SPHERE_WOOD         = 7.06860;
    public final static double MASS_SPHERE_IRON         = 110.272;
    public final static double MASS_SPHERE_HELIUM       = 2.51642e-3;
    public final static double VOLUME_SPHERE            = 0.0141372;
    public final static double RADIUS_SPHERE            = 0.15;
    public final static double AREA_SPHERE              = 0.0706858;

}
