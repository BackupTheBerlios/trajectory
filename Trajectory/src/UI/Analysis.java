/*****************************************************************************
 * File:    Analysis.java
 * Author:  BE/CT
 * Date:    2009-01-05 Start
 * Use:     Displaying all the relevant data to the user.
 * 
 * TODO:    BE: Again hard-wired width/height.
 * TODO:    BE: Some more tooltips?
 */

package UI;
import java.util.Vector;
import physics.*;
import java.text.DecimalFormat;


public class Analysis extends javax.swing.JFrame {
    //final static int WIDTH = 1024;


   // trajactories final values
   private static double hMax = 0.0;
   private static double hMin = 0.0;
   private static double throwingRange = 0.0;
   private static double hEnd = 0.0;
   private static double vEnd = 0.0;
   private static double betaEnd = 0.0;
   private static double throwingTime = 0.0;
   private static double vMax = 0.0;

   // to find the global extrema
   private static double hMinGlobal = 0.0;
   private static double hMaxGlobal = 0.0;
   private static double vMaxGlobal = 0.0;

   // getters:
   public static double getHMax(){
       return hMax;
   }

      public static double getHMin(){
       return hMin;
   }

    public static double getThrowingRange(){
       return throwingRange;
   }

   public static double getHEnd(){
       return hEnd;
   }

   public static double getVEnd(){
       return vEnd;
   }

   public static double getBetaEnd(){
       return betaEnd;
   }

   public static double getThrowingTime(){
       return throwingTime;
   }

   public static double getVMax(){
       return vMax;
   }


   public static int getAnimationSpeedValue(){
       return jSliderAnimationSpeed.getValue();
   }

    /** Creates new form Analysis */
    public Analysis() {
        initComponents();
        setSize(1024,250);        
    }

    public static DecimalFormat computeValueFormat(double value){
       DecimalFormat format = new DecimalFormat("0");
       value = Math.abs(value);
       int dimension = 0;
         if (value > 1){
           for (int i = 1; i<17; i++){
             value = value / 10;
             if (value < 1){
                 dimension = i;
                 i = 17;
             }
           }
           if (dimension == 1){
              format = new DecimalFormat("0.0##############");
           }
           if (dimension == 2){
              format = new DecimalFormat("0.0#############");
           }
           if (dimension == 3){
              format = new DecimalFormat("0.0############");
           }
           if (dimension == 4){
              format = new DecimalFormat("0.0###########");
           }
           if (dimension == 5){
              format = new DecimalFormat("0.0##########");
           }
           if (dimension == 6){
              format = new DecimalFormat("0.0#########");
           }
           if (dimension == 7){
              format = new DecimalFormat("0.0########");
           }
           if (dimension == 8){
              format = new DecimalFormat("0.0#######");
           }
           if (dimension == 9){
              format = new DecimalFormat("0.0######");
           }
           if (dimension == 10){
              format = new DecimalFormat("0.0#####");
           }
           if (dimension == 11){
              format = new DecimalFormat("0.0####");
           }
           if (dimension == 12){
              format = new DecimalFormat("0.0###");
           }
           if (dimension == 13){
              format = new DecimalFormat("0.0##");
           }
           if (dimension == 14){
              format = new DecimalFormat("0.0#");
           }
           if (dimension == 15){
              format = new DecimalFormat("0.0");
           }
           if (dimension == 16){
              format = new DecimalFormat("0");
           }
         }
         else{
           if (value == 0){
            format = new DecimalFormat("0.0");
           }
           else{
            format = new DecimalFormat("0.0##############");
           }
         }
         return format;
    }

    // display start values on the form
    public void displayStartValues(physics.Setting setting){
            this.jTfHeightStart.setText(String.valueOf(setting.getH()));
            this.jTfRhoFluidStart.setText(String.valueOf(setting.getRho()));
            this.jTfEtaFluidStart.setText(String.valueOf(setting.getEta()));
            this.jTfRadiusPlanetStart.setText(String.valueOf(setting.getR()));
            this.jTfMassPlanetStart.setText(String.valueOf(setting.getM()));
            this.jTfDtStart.setText(String.valueOf(setting.getDt()));
            this.jTfSpeedStart.setText(String.valueOf(setting.getV()));
            this.jTfAngleStart.setText(String.valueOf(setting.getBeta()));
            this.jTfMassBodyStart.setText(String.valueOf(setting.getMass()));
            this.jTfVolumeStart.setText(String.valueOf(setting.getVol()));
            this.jTfCwStart.setText(String.valueOf(setting.getCw()));
            this.jTfAreaAffectedStart.setText(String.valueOf(setting.getA()));
            this.jTfRadiusBodyStart.setText(String.valueOf(setting.getRadius()));
            this.jTfProposedComputingTimeStart.setText(String.valueOf(setting.getT()));
            this.jTfThrowingRangeStart.setText(String.valueOf(utilities.Options.getThrowingRange()));
         if (physics.Forces.isActingGravity()){
            this.jCckBxGravityStart.setSelected(true);  
           }
         if (physics.Forces.isActingSimpleGravity()){
            this.jCckBxSimpleGravityStart.setSelected(true);  
           }
         if (physics.Forces.isActingFlowResistance()){
            this.jCckBxFlowResistanceStart.setSelected(true);  
           }
         if (physics.Forces.isActingViscosity()){
            this.jCckBxViscosityStart.setSelected(true);
           }            
         if (physics.Forces.isActingBuoyancy()){
            this.jCckBxBuoyancyStart.setSelected(true);
           }
         if (utilities.Options.getComputeBackwards()){
            this.jCckBxComputeBackwardsStart.setSelected(true);
           }
         if (utilities.Options.getComputeDensity()){
            this.jCckBxComputeDensityStart.setSelected(true);  
           }
    }


    // use: to display values on the form.
    public void displayValues(
          double v, double vx, double vy, double x, double y, double h,
          double rho, double beta, double angleGround, double throwingTime
            )
    {
      jTfV.setText(String.valueOf(computeValueFormat(v).format(v)).replace(',','.'));
      jTfVx.setText(String.valueOf(computeValueFormat(vx).format(vx)).replace(',','.'));
      jTfVy.setText(String.valueOf(computeValueFormat(vy).format(vy)).replace(',','.'));
      jTfX.setText(String.valueOf(computeValueFormat(x).format(x)).replace(',','.'));
      jTfY.setText(String.valueOf(computeValueFormat(y).format(y)).replace(',','.'));
      jTfHeight.setText(String.valueOf(computeValueFormat(h).format(h)).replace(',','.'));
      jTfDensity.setText(String.valueOf(computeValueFormat(rho).format(rho)).replace(',','.'));
      jTfAngleXAxis.setText(String.valueOf(computeValueFormat(beta).format(beta)).replace(',','.'));
      jTfAngleGround.setText(String.valueOf(computeValueFormat(angleGround).format(angleGround)).replace(',','.'));
      jTfThrowingTime.setText(String.valueOf(computeValueFormat(throwingTime).format(throwingTime)).replace(',','.'));
    } // end `displayValues()'



    public static double computeThrowingTime(int i, double dt){
        double throwingTimeTemp = 0.0;
        throwingTimeTemp = (double)(i)*dt;
        return throwingTimeTemp;
    }

    // TODO: BE C simplify method.
    public static double computeHMin(Vector<MovingBody> positions, Setting setting){
      double hMinTemp = 0.0;
      if (Forces.isActingGravity()){
        hMinTemp = positions.get(0).getLocation(setting).getH();
        hMinGlobal = positions.get(0).getLocation(setting).getH();
        for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).getLocation(setting).getH() < positions.get(i-1).getLocation(setting).getH() && positions.get(i).getLocation(setting).getH() < hMinGlobal) {
                hMinTemp = positions.get(i).getLocation(setting).getH();
                hMinGlobal = hMinTemp;
             }
          }
      }
      else{
        hMinTemp = positions.get(0).getLocation(setting).getY();
        hMinGlobal = positions.get(0).getLocation(setting).getY();
        for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).getLocation(setting).getY() < positions.get(i-1).getLocation(setting).getY() && positions.get(i).getLocation(setting).getY() < hMinGlobal) {
                hMinTemp = positions.get(i).getLocation(setting).getY();
                hMinGlobal = hMinTemp;
             }
          }
      }
        if(hMinTemp < 0){
        hMinTemp = 0.0;
        }
        return hMinTemp;
    }
   
    // TODO: BE C simplify method.
    public static double computeHMax(Vector<MovingBody> positions, Setting setting){
      double hMaxTemp = 0.0;
      if (Forces.isActingGravity()){
        hMaxTemp = positions.get(0).getLocation(setting).getH();
        hMaxGlobal = positions.get(0).getLocation(setting).getH();
        for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).getLocation(setting).getH() > positions.get(i-1).getLocation(setting).getH() && positions.get(i).getLocation(setting).getH() > hMaxGlobal) {
                hMaxTemp = positions.get(i).getLocation(setting).getH();
                hMaxGlobal = hMaxTemp;
             }
          }
      }
      else{
        hMaxTemp = positions.get(0).getLocation(setting).getY();
        hMaxGlobal = positions.get(0).getLocation(setting).getY();
        for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).getLocation(setting).getY() > positions.get(i-1).getLocation(setting).getY() && positions.get(i).getLocation(setting).getY() > hMaxGlobal) {
                hMaxTemp = positions.get(i).getLocation(setting).getY();
                hMaxGlobal = hMaxTemp;
             }
          }
      }
      return hMaxTemp;
    }

    // TODO: BE C simplify method.
    public static double computeVMax(Vector<MovingBody> positions){
        double vMaxTemp = positions.get(0).getV();
        vMaxGlobal = positions.get(0).getV();
          for (int i = 1; i < positions.size(); i++) {
            if (positions.get(i).getV() > positions.get(i-1).getV() && positions.get(i).getV() > vMaxGlobal) {
                vMaxTemp = positions.get(i).getV();
                vMaxGlobal = vMaxTemp;
             }
          }
        return vMaxTemp;
    }


    public static double computeThrowingRange(Vector<MovingBody> positions, Setting setting){
        double throwingRangeTemp = 0.0;
        double alpha = 0.0;
        if(Forces.isActingGravity()){
         alpha = Math.acos((-1)*positions.get(positions.size()-1).getLocation(setting).getRvectory());
          if(positions.get(positions.size()-1).getLocation(setting).getX()<0){
            alpha = 2*Math.PI - alpha;
          }
         throwingRangeTemp = alpha * setting.getR();
        }
        else{
        throwingRangeTemp = positions.get(positions.size()-1).getLocation(setting).getX();
        }
        return throwingRangeTemp;
    }


    public static double computeVEnd(Vector<MovingBody> positions){
        double vEndTemp = 0.0;
        vEndTemp = positions.get(positions.size()-1).getV();
        return vEndTemp;
    }


    public static double computeBetaEnd(Vector<MovingBody> positions, Setting setting){
        double betaEndTemp = 0.0;
        if (!Forces.isActingGravity()){
        betaEndTemp = positions.get(positions.size()-1).getBeta();
        }
        else{
        betaEndTemp = (positions.get(positions.size()-1).getAngleVvectorToRvector() - 90);
        }
        return betaEndTemp;
    }
          

    public static double computeHEnd(Vector<MovingBody> positions, Setting setting){
        double hEndTemp = 0.0;
      if (!Forces.isActingGravity()){
        hEndTemp = positions.get(positions.size()-1).getLocation(setting).getY();
      }
      else{
        hEndTemp = positions.get(positions.size()-1).getLocation(setting).getH();
      }
        if(hEndTemp < 0){
        hEndTemp = 0.0;
        }
        return hEndTemp;
    }

                        
 public static void displayFinalValues(double hMax, double hMin, double throwingRange, double vEnd, double betaEnd, double hEnd, double throwingTime, double vMax) {
        jTextFieldhMax.setText(String.valueOf(computeValueFormat(hMax).format(hMax)).replace(',','.'));
        jTextFieldhMin.setText(String.valueOf(computeValueFormat(hMin).format(hMin)).replace(',','.'));
        jTextFieldthrowingRange.setText(String.valueOf(computeValueFormat(throwingRange).format(throwingRange)).replace(',','.'));
        jTextFieldvEnd.setText(String.valueOf(computeValueFormat(vEnd).format(vEnd)).replace(',','.'));
        jTextFieldbetaEnd.setText(String.valueOf(computeValueFormat(betaEnd).format(betaEnd)).replace(',','.'));
        jTextFieldhEnd.setText(String.valueOf(computeValueFormat(hEnd).format(hEnd)).replace(',','.'));
        jTextFieldthrowingTime.setText(String.valueOf(computeValueFormat(throwingTime).format(throwingTime)).replace(',','.'));
        jTextFieldSpeedMaximum.setText(String.valueOf(computeValueFormat(vMax).format(vMax)).replace(',','.'));
 }


 public static void computeFinalValues(Vector<MovingBody> positions, Setting setting){
        hMax =  UI.Analysis.computeHMax(positions, setting);
        hMin =UI.Analysis.computeHMin(positions, setting);
        throwingRange = UI.Analysis.computeThrowingRange(positions, setting);
        vEnd = UI.Analysis.computeVEnd(positions);
        betaEnd = UI.Analysis.computeBetaEnd(positions, setting);
        hEnd =  UI.Analysis.computeHEnd(positions, setting);
        throwingTime = UI.Analysis.computeThrowingTime((positions.size()-1), setting.getDt());
        vMax = UI.Analysis.computeVMax(positions);
 }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        //jTabbedPane1.setSize(1024,100);
        jTabbedPane1.setBounds(0, 0, 1024, 100);
        jPanel2 = new javax.swing.JPanel();
        jTfHeightStart = new javax.swing.JTextField();
        jTfSpeedStart = new javax.swing.JTextField();
        jTfAngleStart = new javax.swing.JTextField();
        jLabelSpeed = new javax.swing.JLabel();
        jLabelAngle = new javax.swing.JLabel();
        jLabelHeight = new javax.swing.JLabel();
        jLabelMass = new javax.swing.JLabel();
        jLabelVolume = new javax.swing.JLabel();
        jTfVolumeStart = new javax.swing.JTextField();
        jLabelCw = new javax.swing.JLabel();
        jTfCwStart = new javax.swing.JTextField();
        jLblAreaFlowBody = new javax.swing.JLabel();
        jTfAreaAffectedStart = new javax.swing.JTextField();
        jLblRadiusBody = new javax.swing.JLabel();
        jTfRadiusBodyStart = new javax.swing.JTextField();
        jLabelDimensionArea = new javax.swing.JLabel();
        jLabelDimensionRadius = new javax.swing.JLabel();
        jTfMassBodyStart = new javax.swing.JTextField();
        jLabelDimensionHeight = new javax.swing.JLabel();
        jLabelDimensionSpeed = new javax.swing.JLabel();
        jLabelDimensionBeta = new javax.swing.JLabel();
        jLabelDimensionMass = new javax.swing.JLabel();
        jLabelDimensionVolume = new javax.swing.JLabel();
        jLblRhoFluid = new javax.swing.JLabel();
        jLblEtaFluid = new javax.swing.JLabel();
        jTfRhoFluidStart = new javax.swing.JTextField();
        jTfEtaFluidStart = new javax.swing.JTextField();
        jLabelDimensionDensity = new javax.swing.JLabel();
        jLabelDimensionViscosity = new javax.swing.JLabel();
        jLblRadiusPlanet = new javax.swing.JLabel();
        jLblMassPlanet = new javax.swing.JLabel();
        jLblDt = new javax.swing.JLabel();
        jLblProposedComputiingTime = new javax.swing.JLabel();
        jTfProposedComputingTimeStart = new javax.swing.JTextField();
        jTfDtStart = new javax.swing.JTextField();
        jTfMassPlanetStart = new javax.swing.JTextField();
        jTfRadiusPlanetStart = new javax.swing.JTextField();
        jLabelDimensionRadiusPlanet = new javax.swing.JLabel();
        jLabelDimensionMassPlanet = new javax.swing.JLabel();
        jLabelDimensionInterval = new javax.swing.JLabel();
        jLabelDimensionTime = new javax.swing.JLabel();
        jLabelThrowingRange = new javax.swing.JLabel();
        jLabelDimensionRange = new javax.swing.JLabel();
        jTfThrowingRangeStart = new javax.swing.JTextField();
        jCckBxBuoyancyStart = new javax.swing.JCheckBox();
        jCckBxFlowResistanceStart = new javax.swing.JCheckBox();
        jCckBxGravityStart = new javax.swing.JCheckBox();
        jCckBxViscosityStart = new javax.swing.JCheckBox();
        jCckBxSimpleGravityStart = new javax.swing.JCheckBox();
        jCckBxComputeDensityStart = new javax.swing.JCheckBox();
        jCckBxComputeBackwardsStart = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTfV = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTfVx = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTfVy = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTfX = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTfY = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTfHeight = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTfDensity = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTfAngleGround = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTfAngleXAxis = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jSliderAnimationSpeed = new javax.swing.JSlider();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTfThrowingTime = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldhMax = new javax.swing.JTextField();
        jTextFieldhMin = new javax.swing.JTextField();
        jTextFieldthrowingRange = new javax.swing.JTextField();
        jTextFieldhEnd = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldvEnd = new javax.swing.JTextField();
        jTextFieldbetaEnd = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldthrowingTime = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButtonSetFinalValuesToStartParameters = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldSpeedMaximum = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analysis");
        setResizable(false);

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1024, 150));
        jTabbedPane1.setRequestFocusEnabled(false);

        jTfHeightStart.setEditable(false);
        jTfHeightStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfHeightStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfHeightStart.setToolTipText("Start height of the body referring to the ground level");

        jTfSpeedStart.setEditable(false);
        jTfSpeedStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfSpeedStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfSpeedStart.setToolTipText("Start speed of the body");

        jTfAngleStart.setEditable(false);
        jTfAngleStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfAngleStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfAngleStart.setToolTipText("Start angle of the body concerning the x-axis");

        jLabelSpeed.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelSpeed.setText("Speed:");

        jLabelAngle.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelAngle.setText("Angle:");

        jLabelHeight.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelHeight.setText("Height:");

        jLabelMass.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelMass.setText("Mass:");

        jLabelVolume.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelVolume.setText("Volume:");

        jTfVolumeStart.setEditable(false);
        jTfVolumeStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfVolumeStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfVolumeStart.setToolTipText("Volume of the body");

        jLabelCw.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelCw.setText("Cw:");

        jTfCwStart.setEditable(false);
        jTfCwStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfCwStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfCwStart.setToolTipText("Cw of the body");

        jLblAreaFlowBody.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblAreaFlowBody.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLblAreaFlowBody.setText("Area:");

        jTfAreaAffectedStart.setEditable(false);
        jTfAreaAffectedStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfAreaAffectedStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfAreaAffectedStart.setToolTipText("Area affected by flow");

        jLblRadiusBody.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblRadiusBody.setText("Radius:");

        jTfRadiusBodyStart.setEditable(false);
        jTfRadiusBodyStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfRadiusBodyStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfRadiusBodyStart.setToolTipText("Radius of the body if sphere");

        jLabelDimensionArea.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionArea.setText("m²");

        jLabelDimensionRadius.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionRadius.setText("m");

        jTfMassBodyStart.setEditable(false);
        jTfMassBodyStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfMassBodyStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfMassBodyStart.setToolTipText("Mass of the body");

        jLabelDimensionHeight.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionHeight.setText("m");

        jLabelDimensionSpeed.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionSpeed.setText("m/s");

        jLabelDimensionBeta.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionBeta.setText("°");

        jLabelDimensionMass.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionMass.setText("kg");

        jLabelDimensionVolume.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionVolume.setText("m³");

        jLblRhoFluid.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblRhoFluid.setText("Density:");

        jLblEtaFluid.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblEtaFluid.setText("Viscosity");

        jTfRhoFluidStart.setEditable(false);
        jTfRhoFluidStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfRhoFluidStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfRhoFluidStart.setToolTipText("Density of the medium at ground level");

        jTfEtaFluidStart.setEditable(false);
        jTfEtaFluidStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfEtaFluidStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfEtaFluidStart.setToolTipText("Viscosity of the medium");

        jLabelDimensionDensity.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionDensity.setText("kg/m³");

        jLabelDimensionViscosity.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionViscosity.setText("Ns/m²");

        jLblRadiusPlanet.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblRadiusPlanet.setText("Radius (planet):");

        jLblMassPlanet.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblMassPlanet.setText("Mass (planet):");

        jLblDt.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblDt.setText("Integration interval:");

        jLblProposedComputiingTime.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLblProposedComputiingTime.setText("Computing time:");

        jTfProposedComputingTimeStart.setEditable(false);
        jTfProposedComputingTimeStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfProposedComputingTimeStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfProposedComputingTimeStart.setToolTipText("Maximum time the computing process should last");

        jTfDtStart.setEditable(false);
        jTfDtStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfDtStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfDtStart.setToolTipText("Intervall used to integrate during the computation");

        jTfMassPlanetStart.setEditable(false);
        jTfMassPlanetStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfMassPlanetStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfMassPlanetStart.setToolTipText("Mass of the planet");

        jTfRadiusPlanetStart.setEditable(false);
        jTfRadiusPlanetStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfRadiusPlanetStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfRadiusPlanetStart.setToolTipText("Radius of the planet");

        jLabelDimensionRadiusPlanet.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionRadiusPlanet.setText("m");

        jLabelDimensionMassPlanet.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionMassPlanet.setText("kg");

        jLabelDimensionInterval.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionInterval.setText("s");

        jLabelDimensionTime.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionTime.setText("s");

        jLabelThrowingRange.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabelThrowingRange.setText("Throwing range:");

        jLabelDimensionRange.setFont(new java.awt.Font("Arial", 1, 11));
        jLabelDimensionRange.setText("m");

        jTfThrowingRangeStart.setEditable(false);
        jTfThrowingRangeStart.setFont(new java.awt.Font("Arial", 0, 11));
        jTfThrowingRangeStart.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTfThrowingRangeStart.setToolTipText("The throwing range the user wants to reach");

        jCckBxBuoyancyStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxBuoyancyStart.setText("Buoyancy");
        jCckBxBuoyancyStart.setToolTipText("Buoyancy caused force");
        jCckBxBuoyancyStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxBuoyancyStartActionPerformed(evt);
            }
        });

        jCckBxFlowResistanceStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxFlowResistanceStart.setText("Flow resistance");
        jCckBxFlowResistanceStart.setToolTipText("Flow resistance caused force for all cases of turbulent flow");
        jCckBxFlowResistanceStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxFlowResistanceStartActionPerformed(evt);
            }
        });

        jCckBxGravityStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxGravityStart.setText("Gravity");
        jCckBxGravityStart.setToolTipText("Gravity caused by a central potential and drawing of the planets shape");
        jCckBxGravityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxGravityStartActionPerformed(evt);
            }
        });

        jCckBxViscosityStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxViscosityStart.setText("Viscosity");
        jCckBxViscosityStart.setToolTipText("Viscosity caused force for laminar flow in cases of a sphere shaped body");
        jCckBxViscosityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxViscosityStartActionPerformed(evt);
            }
        });

        jCckBxSimpleGravityStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxSimpleGravityStart.setText("Simple gravity");
        jCckBxSimpleGravityStart.setToolTipText("Gravity caused by Earth acceleration location independent");
        jCckBxSimpleGravityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxSimpleGravityStartActionPerformed(evt);
            }
        });

        jCckBxComputeDensityStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxComputeDensityStart.setText("Compute density");
        jCckBxComputeDensityStart.setToolTipText("Computing the height depending density of the atmosphere");

        jCckBxComputeBackwardsStart.setFont(new java.awt.Font("Tahoma", 1, 11));
        jCckBxComputeBackwardsStart.setText("Compute backwards");
        jCckBxComputeBackwardsStart.setToolTipText("Simulating the movement backwards to get the correlating start parameters");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel39.setText("Acting forces:");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel40.setText("Chosen options:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelHeight, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelSpeed, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelAngle, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelMass, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelVolume, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelCw, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTfHeightStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfSpeedStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfAngleStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfMassBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfVolumeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfCwStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDimensionHeight)
                    .addComponent(jLabelDimensionSpeed)
                    .addComponent(jLabelDimensionBeta, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDimensionMass)
                    .addComponent(jLabelDimensionVolume))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblAreaFlowBody, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblRadiusBody, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblRhoFluid, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblEtaFluid, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblRadiusPlanet, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblMassPlanet, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTfRadiusBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfAreaAffectedStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfRhoFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfEtaFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfRadiusPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfMassPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDimensionMassPlanet)
                    .addComponent(jLabelDimensionRadiusPlanet)
                    .addComponent(jLabelDimensionViscosity)
                    .addComponent(jLabelDimensionDensity)
                    .addComponent(jLabelDimensionRadius, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabelDimensionArea))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLblDt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLblProposedComputiingTime, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelThrowingRange, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCckBxComputeBackwardsStart)
                            .addComponent(jCckBxComputeDensityStart))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTfDtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTfProposedComputingTimeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelDimensionRange)
                                        .addComponent(jLabelDimensionTime, javax.swing.GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
                                        .addComponent(jLabelDimensionInterval)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTfThrowingRangeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(33, 33, 33))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel40)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCckBxBuoyancyStart)
                    .addComponent(jCckBxFlowResistanceStart)
                    .addComponent(jCckBxGravityStart)
                    .addComponent(jCckBxViscosityStart)
                    .addComponent(jCckBxSimpleGravityStart)
                    .addComponent(jLabel39))
                .addGap(334, 334, 334))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfHeightStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelHeight))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfSpeedStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelSpeed)
                                    .addComponent(jLabelDimensionSpeed)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfAreaAffectedStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDimensionArea)
                                    .addComponent(jLblAreaFlowBody, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfRadiusBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblRadiusBody)
                                    .addComponent(jLabelDimensionRadius))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfAngleStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelAngle)
                                    .addComponent(jLabelDimensionBeta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelCw)
                                            .addComponent(jTfCwStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelMass)
                                            .addComponent(jTfMassBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionMass))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelVolume)
                                            .addComponent(jTfVolumeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionVolume)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTfRhoFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLblRhoFluid)
                                        .addComponent(jLabelDimensionDensity))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLblEtaFluid)
                                            .addComponent(jTfEtaFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionViscosity))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfRadiusPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblRadiusPlanet)
                                    .addComponent(jLabelDimensionRadiusPlanet))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfMassPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblMassPlanet)
                                    .addComponent(jLabelDimensionMassPlanet)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelDimensionHeight)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel39)
                                        .addGap(18, 18, 18)))
                                .addComponent(jCckBxBuoyancyStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCckBxFlowResistanceStart)
                                .addGap(1, 1, 1)
                                .addComponent(jCckBxGravityStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCckBxViscosityStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCckBxSimpleGravityStart))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLblDt)
                                    .addComponent(jTfDtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDimensionInterval))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfProposedComputingTimeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblProposedComputiingTime)
                                    .addComponent(jLabelDimensionTime))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabelDimensionRange))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTfThrowingRangeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelThrowingRange)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCckBxComputeDensityStart)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCckBxComputeBackwardsStart)))))
                .addGap(195, 195, 195))
        );

        jTabbedPane1.addTab("Start data", jPanel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("V:");

        jTfV.setEditable(false);
        jTfV.setFont(new java.awt.Font("Arial", 0, 11));
        jTfV.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfV.setToolTipText("Current velocity");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Vx:");

        jTfVx.setEditable(false);
        jTfVx.setFont(new java.awt.Font("Arial", 0, 11));
        jTfVx.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfVx.setToolTipText("Current velocity's X-component");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Vy:");

        jTfVy.setEditable(false);
        jTfVy.setFont(new java.awt.Font("Arial", 0, 11));
        jTfVy.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfVy.setToolTipText("Current velocity's Y-component");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("X:");

        jTfX.setEditable(false);
        jTfX.setFont(new java.awt.Font("Arial", 0, 11));
        jTfX.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfX.setToolTipText("Current X-coordinate of the body");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Y:");

        jTfY.setEditable(false);
        jTfY.setFont(new java.awt.Font("Arial", 0, 11));
        jTfY.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfY.setToolTipText("Current Y-coordinate of the body");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Height:");

        jTfHeight.setEditable(false);
        jTfHeight.setFont(new java.awt.Font("Arial", 0, 11));
        jTfHeight.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfHeight.setToolTipText("Current height of the body referring to the ground");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Density:");

        jTfDensity.setEditable(false);
        jTfDensity.setFont(new java.awt.Font("Arial", 0, 11));
        jTfDensity.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfDensity.setToolTipText("Density of the medium at the current location");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Angle(ground):");

        jTfAngleGround.setEditable(false);
        jTfAngleGround.setFont(new java.awt.Font("Arial", 0, 11));
        jTfAngleGround.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfAngleGround.setToolTipText("Angle of the moving direction referring to the ground");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel10.setText("Angle(x-axis):");

        jTfAngleXAxis.setEditable(false);
        jTfAngleXAxis.setFont(new java.awt.Font("Arial", 0, 11));
        jTfAngleXAxis.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfAngleXAxis.setToolTipText("Angle of the moving direction referring to the X-axis");

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel25.setText("Animation speed:");

        jSliderAnimationSpeed.setMaximum(200);
        jSliderAnimationSpeed.setToolTipText("Choose the speed of the animation");
        jSliderAnimationSpeed.setValue(200);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel26.setText("slow");

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel27.setText("fast");

        jTfThrowingTime.setEditable(false);
        jTfThrowingTime.setFont(new java.awt.Font("Arial", 0, 11));
        jTfThrowingTime.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTfThrowingTime.setToolTipText("Current time the drawn movement lasts");

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel28.setText("Throwing time:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel29.setText("s");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel30.setText("m/s");

        jLabel31.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel31.setText("m/s");

        jLabel32.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel32.setText("m/s");

        jLabel33.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel33.setText("m");

        jLabel34.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel34.setText("m");

        jLabel35.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel35.setText("m");

        jLabel36.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel36.setText("kg/m³");

        jLabel37.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel37.setText("°");

        jLabel38.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel38.setText("°");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTfVx, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTfV, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(jTfVy, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30)
                                    .addComponent(jLabel31)
                                    .addComponent(jLabel32))
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTfHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTfX, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTfY, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel26)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel34)
                                            .addComponent(jLabel35))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel9)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                        .addComponent(jLabel8)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTfAngleGround, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTfDensity, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTfAngleXAxis, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel28)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTfThrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel36)
                            .addComponent(jLabel38)
                            .addComponent(jLabel37)
                            .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jSliderAnimationSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(491, 491, 491))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTfV, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTfVx, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTfVy, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)))
                    .addComponent(jLabel30)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTfHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTfX, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTfY, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel35))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTfDensity, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTfAngleGround, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel37))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTfAngleXAxis, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel38))
                            .addComponent(jLabel10))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfThrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel29)
                                    .addComponent(jLabel28)))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(jLabel26)
                                .addComponent(jLabel27))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jSliderAnimationSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(219, 219, 219))
        );

        jTabbedPane1.addTab("Present data ", jPanel1);

        jTextFieldhMax.setEditable(false);
        jTextFieldhMax.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldhMax.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldhMax.setToolTipText("Maximum height of the body referring to the ground level");

        jTextFieldhMin.setEditable(false);
        jTextFieldhMin.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldhMin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldhMin.setToolTipText("Minimum height of the body referring to the ground level");

        jTextFieldthrowingRange.setEditable(false);
        jTextFieldthrowingRange.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldthrowingRange.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldthrowingRange.setToolTipText("Reached throwing range");

        jTextFieldhEnd.setEditable(false);
        jTextFieldhEnd.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldhEnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldhEnd.setToolTipText("Final height of the body referring to the ground level");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Height maximum:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Height minimum:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel12.setText("Throwing range:");

        jTextFieldvEnd.setEditable(false);
        jTextFieldvEnd.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldvEnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldvEnd.setToolTipText("Final speed of the body");

        jTextFieldbetaEnd.setEditable(false);
        jTextFieldbetaEnd.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldbetaEnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldbetaEnd.setToolTipText("Final angle of the moving direction referring to the ground");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel13.setText("Height end:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel14.setText("Speed end:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel15.setText("Angle end:");

        jTextFieldthrowingTime.setEditable(false);
        jTextFieldthrowingTime.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldthrowingTime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldthrowingTime.setToolTipText("Overall time the computed movement lasts");
        jTextFieldthrowingTime.setMinimumSize(new java.awt.Dimension(110, 20));
        jTextFieldthrowingTime.setPreferredSize(new java.awt.Dimension(110, 20));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel16.setText("Throwing time:");

        jButtonSetFinalValuesToStartParameters.setFont(new java.awt.Font("Tahoma", 1, 11));
        jButtonSetFinalValuesToStartParameters.setText("Set");
        jButtonSetFinalValuesToStartParameters.setToolTipText("Gives the opportunity to check the computed start parameters in cases of backwards computation");
        jButtonSetFinalValuesToStartParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetFinalValuesToStartParametersActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel17.setText("Set final values as new start parameters:");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel18.setText("s");

        jLabel19.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel19.setText("m");

        jLabel20.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel20.setText("m/s");

        jLabel21.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel21.setText("°");

        jLabel22.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel22.setText("m");

        jLabel23.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel23.setText("m");

        jLabel24.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel24.setText("m");

        jTextFieldSpeedMaximum.setEditable(false);
        jTextFieldSpeedMaximum.setFont(new java.awt.Font("Arial", 0, 11));
        jTextFieldSpeedMaximum.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextFieldSpeedMaximum.setToolTipText("Maximum speed the body reached");

        jLabel41.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel41.setText("m/s");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel42.setText("Speed maximum:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldhMin, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldhMax, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel42)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSpeedMaximum, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel22)
                        .addComponent(jLabel23)))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldhEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldvEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldbetaEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jButtonSetFinalValuesToStartParameters))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldthrowingRange, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldthrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(409, 409, 409))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldvEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextFieldbetaEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel21))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addComponent(jLabel15))))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(37, 37, 37)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(jLabel14)
                                        .addComponent(jTextFieldhMin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel23)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel22)
                                    .addComponent(jTextFieldhMax, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13)
                                    .addComponent(jTextFieldhEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel19)))
                            .addGap(18, 18, 18)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldSpeedMaximum, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel41)
                                .addComponent(jLabel42))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldthrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextFieldthrowingRange, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel24))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSetFinalValuesToStartParameters)))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Final values", jPanel4);

        jTabbedPane1.setSelectedIndex(1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCckBxBuoyancyStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCckBxBuoyancyStartActionPerformed
        // reading the values via the OK-button event handler is less error-prone.
}//GEN-LAST:event_jCckBxBuoyancyStartActionPerformed

    private void jCckBxFlowResistanceStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCckBxFlowResistanceStartActionPerformed
        // reading the values via the OK-button event handler is less error-prone.
}//GEN-LAST:event_jCckBxFlowResistanceStartActionPerformed

    private void jCckBxGravityStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCckBxGravityStartActionPerformed
        // reading the values via the OK-button event handler is less error-prone.
}//GEN-LAST:event_jCckBxGravityStartActionPerformed

    private void jCckBxViscosityStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCckBxViscosityStartActionPerformed
        // reading the values via the OK-button event handler is less error-prone.
}//GEN-LAST:event_jCckBxViscosityStartActionPerformed

    private void jCckBxSimpleGravityStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCckBxSimpleGravityStartActionPerformed
        // reading the values via the OK-button event handler is less error-prone.
}//GEN-LAST:event_jCckBxSimpleGravityStartActionPerformed

    private void jButtonSetFinalValuesToStartParametersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSetFinalValuesToStartParametersActionPerformed

        UserInputNewParameters.currentSetting.setH(Double.valueOf(jTextFieldhEnd.getText()));
        UserInputNewParameters.currentSetting.setBeta(-Double.valueOf(jTextFieldbetaEnd.getText()));
        UserInputNewParameters.currentSetting.setV(Double.valueOf(jTextFieldvEnd.getText()));
        utilities.Options.setComputeBackwards(false);
        utilities.Options.setThrowingRange(0.0);

}//GEN-LAST:event_jButtonSetFinalValuesToStartParametersActionPerformed
   
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Analysis a = new Analysis();
                a.setVisible(true);
           
            System.out.println("frame: " + a.getWidth() + " " + a.getHeight());
            System.out.println("tabbedPane: " + a.jTabbedPane1.getWidth() +  " " +
                    a.jTabbedPane1.getHeight());
            a.jTabbedPane1.setBounds(0, 0, a.getWidth(), a.getHeight());
                        System.out.println("frame: " + a.getWidth() + " " + a.getHeight());
            System.out.println("tabbedPane: " + a.jTabbedPane1.getWidth() +  " " +
                    a.jTabbedPane1.getHeight());
            
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSetFinalValuesToStartParameters;
    private javax.swing.JCheckBox jCckBxBuoyancyStart;
    private javax.swing.JCheckBox jCckBxComputeBackwardsStart;
    private javax.swing.JCheckBox jCckBxComputeDensityStart;
    private javax.swing.JCheckBox jCckBxFlowResistanceStart;
    private javax.swing.JCheckBox jCckBxGravityStart;
    private javax.swing.JCheckBox jCckBxSimpleGravityStart;
    private javax.swing.JCheckBox jCckBxViscosityStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelAngle;
    private javax.swing.JLabel jLabelCw;
    private javax.swing.JLabel jLabelDimensionArea;
    private javax.swing.JLabel jLabelDimensionBeta;
    private javax.swing.JLabel jLabelDimensionDensity;
    private javax.swing.JLabel jLabelDimensionHeight;
    private javax.swing.JLabel jLabelDimensionInterval;
    private javax.swing.JLabel jLabelDimensionMass;
    private javax.swing.JLabel jLabelDimensionMassPlanet;
    private javax.swing.JLabel jLabelDimensionRadius;
    private javax.swing.JLabel jLabelDimensionRadiusPlanet;
    private javax.swing.JLabel jLabelDimensionRange;
    private javax.swing.JLabel jLabelDimensionSpeed;
    private javax.swing.JLabel jLabelDimensionTime;
    private javax.swing.JLabel jLabelDimensionViscosity;
    private javax.swing.JLabel jLabelDimensionVolume;
    private javax.swing.JLabel jLabelHeight;
    private javax.swing.JLabel jLabelMass;
    private javax.swing.JLabel jLabelSpeed;
    private javax.swing.JLabel jLabelThrowingRange;
    private javax.swing.JLabel jLabelVolume;
    private javax.swing.JLabel jLblAreaFlowBody;
    private javax.swing.JLabel jLblDt;
    private javax.swing.JLabel jLblEtaFluid;
    private javax.swing.JLabel jLblMassPlanet;
    private javax.swing.JLabel jLblProposedComputiingTime;
    private javax.swing.JLabel jLblRadiusBody;
    private javax.swing.JLabel jLblRadiusPlanet;
    private javax.swing.JLabel jLblRhoFluid;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private static javax.swing.JSlider jSliderAnimationSpeed;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static javax.swing.JTextField jTextFieldSpeedMaximum;
    private static javax.swing.JTextField jTextFieldbetaEnd;
    private static javax.swing.JTextField jTextFieldhEnd;
    private static javax.swing.JTextField jTextFieldhMax;
    private static javax.swing.JTextField jTextFieldhMin;
    private static javax.swing.JTextField jTextFieldthrowingRange;
    private static javax.swing.JTextField jTextFieldthrowingTime;
    private static javax.swing.JTextField jTextFieldvEnd;
    private javax.swing.JTextField jTfAngleGround;
    private javax.swing.JTextField jTfAngleStart;
    private javax.swing.JTextField jTfAngleXAxis;
    private javax.swing.JTextField jTfAreaAffectedStart;
    private javax.swing.JTextField jTfCwStart;
    private javax.swing.JTextField jTfDensity;
    private javax.swing.JTextField jTfDtStart;
    private javax.swing.JTextField jTfEtaFluidStart;
    private javax.swing.JTextField jTfHeight;
    private javax.swing.JTextField jTfHeightStart;
    private javax.swing.JTextField jTfMassBodyStart;
    private javax.swing.JTextField jTfMassPlanetStart;
    private javax.swing.JTextField jTfProposedComputingTimeStart;
    private javax.swing.JTextField jTfRadiusBodyStart;
    private javax.swing.JTextField jTfRadiusPlanetStart;
    private javax.swing.JTextField jTfRhoFluidStart;
    private javax.swing.JTextField jTfSpeedStart;
    private javax.swing.JTextField jTfThrowingRangeStart;
    private javax.swing.JTextField jTfThrowingTime;
    private javax.swing.JTextField jTfV;
    private javax.swing.JTextField jTfVolumeStart;
    private javax.swing.JTextField jTfVx;
    private javax.swing.JTextField jTfVy;
    private javax.swing.JTextField jTfX;
    private javax.swing.JTextField jTfY;
    // End of variables declaration//GEN-END:variables

}
