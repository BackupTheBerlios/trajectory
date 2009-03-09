/*****************************************************************************
 * File:    Analysis.java
 * Author:  BE
 * Date:    5. Januar 2009, 13:32
 * Use:     Displaying all the relevant data to the user.
 * 
 * TODO:    BE: Again hard-wired width/height.
 * TODO:    BE: Some more tooltips?
 */

package UI;
import java.util.Vector;
import physics.*;
import java.text.DecimalFormat;
/**
 *
 * @author  BE
 */
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

   // to find the global extrema
   private static double hMinGlobal = 0.0;
   private static double hMaxGlobal = 0.0;

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
              format = new DecimalFormat("0.000000000000000");
           }
           if (dimension == 2){
              format = new DecimalFormat("0.00000000000000");
           }
           if (dimension == 3){
              format = new DecimalFormat("0.0000000000000");
           }
           if (dimension == 4){
              format = new DecimalFormat("0.000000000000");
           }
           if (dimension == 5){
              format = new DecimalFormat("0.00000000000");
           }
           if (dimension == 6){
              format = new DecimalFormat("0.0000000000");
           }
           if (dimension == 7){
              format = new DecimalFormat("0.000000000");
           }
           if (dimension == 8){
              format = new DecimalFormat("0.00000000");
           }
           if (dimension == 9){
              format = new DecimalFormat("0.0000000");
           }
           if (dimension == 10){
              format = new DecimalFormat("0.000000");
           }
           if (dimension == 11){
              format = new DecimalFormat("0.00000");
           }
           if (dimension == 12){
              format = new DecimalFormat("0.0000");
           }
           if (dimension == 13){
              format = new DecimalFormat("0.000");
           }
           if (dimension == 14){
              format = new DecimalFormat("0.00");
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
            format = new DecimalFormat("0.000000000000000");
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
            //this.jTfAngleStart.setText(String.valueOf(360*setting.getBeta()/(2*Math.PI)));
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
          double rho, double beta, double angleGround
            )
    {
      jTfV.setText(String.valueOf(computeValueFormat(v).format(v)));
      jTfVx.setText(String.valueOf(computeValueFormat(vx).format(vx)));
      jTfVy.setText(String.valueOf(computeValueFormat(vy).format(vy)));
      jTfX.setText(String.valueOf(computeValueFormat(x).format(x)));
      jTfY.setText(String.valueOf(computeValueFormat(y).format(y)));
      jTfHeight.setText(String.valueOf(computeValueFormat(h).format(h)));
      jTfDensity.setText(String.valueOf(computeValueFormat(rho).format(rho)));
      jTfAngleXAxis.setText(String.valueOf(computeValueFormat(beta).format(beta)));
      jTfAngleGround.setText(String.valueOf(computeValueFormat(angleGround).format(angleGround)));
    } // end `displayValues()'



    public static double computeThrowingTime(int i, double dt){
        double throwingTimeTemp = 0.0;
        throwingTimeTemp = (double)(i)*dt;
        return throwingTimeTemp;
    }


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

                        
 public static void displayFinalValues(double hMax, double hMin, double throwingRange, double vEnd, double betaEnd, double hEnd, double throwingTime) {
        jTextFieldhMax.setText(String.valueOf(computeValueFormat(hMax).format(hMax)));
        jTextFieldhMin.setText(String.valueOf(computeValueFormat(hMin).format(hMin)));
        jTextFieldthrowingRange.setText(String.valueOf(computeValueFormat(throwingRange).format(throwingRange)));
        jTextFieldvEnd.setText(String.valueOf(computeValueFormat(vEnd).format(vEnd)));
        jTextFieldbetaEnd.setText(String.valueOf(computeValueFormat(betaEnd).format(betaEnd)));
        jTextFieldhEnd.setText(String.valueOf(computeValueFormat(hEnd).format(hEnd)));
        jTextFieldthrowingTime.setText(String.valueOf(computeValueFormat(throwingTime).format(throwingTime)));
 }


 public static void computeFinalValues(Vector<MovingBody> positions, Setting setting){
        hMax =  UI.Analysis.computeHMax(positions, setting);
        hMin =UI.Analysis.computeHMin(positions, setting);
        throwingRange = UI.Analysis.computeThrowingRange(positions, setting);
        vEnd = UI.Analysis.computeVEnd(positions);
        betaEnd = UI.Analysis.computeBetaEnd(positions, setting);
        hEnd =  UI.Analysis.computeHEnd(positions, setting);
        throwingTime = UI.Analysis.computeThrowingTime((positions.size()-1), setting.getDt());
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Analysis");

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1024, 150));
        jTabbedPane1.setRequestFocusEnabled(false);

        jTfHeightStart.setEditable(false);

        jTfSpeedStart.setEditable(false);

        jTfAngleStart.setEditable(false);

        jLabelSpeed.setText("Speed:");

        jLabelAngle.setText("Angle:");

        jLabelHeight.setText("Height:");

        jLabelMass.setText("Mass:");

        jLabelVolume.setText("Volume:");

        jTfVolumeStart.setEditable(false);

        jLabelCw.setText("Cw:");

        jTfCwStart.setEditable(false);

        jLblAreaFlowBody.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLblAreaFlowBody.setText("Area affected by flow:");

        jTfAreaAffectedStart.setEditable(false);

        jLblRadiusBody.setText("Radius:");

        jTfRadiusBodyStart.setEditable(false);

        jLabelDimensionArea.setText("m²");

        jLabelDimensionRadius.setText("m");

        jTfMassBodyStart.setEditable(false);

        jLabelDimensionHeight.setText("m");

        jLabelDimensionSpeed.setText("m/s");

        jLabelDimensionBeta.setText("°");

        jLabelDimensionMass.setText("kg");

        jLabelDimensionVolume.setText("m³");

        jLblRhoFluid.setText("Density:");

        jLblEtaFluid.setText("Viscosity");

        jTfRhoFluidStart.setEditable(false);

        jTfEtaFluidStart.setEditable(false);

        jLabelDimensionDensity.setText("kg/m³");

        jLabelDimensionViscosity.setText("Ns/m²");

        jLblRadiusPlanet.setText("Radius, Planet:");

        jLblMassPlanet.setText("Mass, Planet:");

        jLblDt.setText("Integration interval:");

        jLblProposedComputiingTime.setText("Proposed computing time:");

        jTfProposedComputingTimeStart.setEditable(false);

        jTfDtStart.setEditable(false);

        jTfMassPlanetStart.setEditable(false);

        jTfRadiusPlanetStart.setEditable(false);

        jLabelDimensionRadiusPlanet.setText("m");

        jLabelDimensionMassPlanet.setText("kg");

        jLabelDimensionInterval.setText("s");

        jLabelDimensionTime.setText("s");

        jLabelThrowingRange.setText("Throwing range:");

        jLabelDimensionRange.setText("m");

        jTfThrowingRangeStart.setEditable(false);

        jCckBxBuoyancyStart.setText("Buoyancy");
        jCckBxBuoyancyStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxBuoyancyStartActionPerformed(evt);
            }
        });

        jCckBxFlowResistanceStart.setText("Flow Resistance");
        jCckBxFlowResistanceStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxFlowResistanceStartActionPerformed(evt);
            }
        });

        jCckBxGravityStart.setText("Gravity");
        jCckBxGravityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxGravityStartActionPerformed(evt);
            }
        });

        jCckBxViscosityStart.setText("Viscosity");
        jCckBxViscosityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxViscosityStartActionPerformed(evt);
            }
        });

        jCckBxSimpleGravityStart.setText("Simple Gravity");
        jCckBxSimpleGravityStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCckBxSimpleGravityStartActionPerformed(evt);
            }
        });

        jCckBxComputeDensityStart.setText("Compute density");

        jCckBxComputeBackwardsStart.setText("Compute backwards");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMass)
                            .addComponent(jLabelVolume)
                            .addComponent(jLabelCw))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTfCwStart, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(jTfVolumeStart, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                            .addComponent(jTfMassBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelSpeed)
                                    .addComponent(jLabelAngle))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabelHeight)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTfHeightStart, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTfSpeedStart, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTfAngleStart, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelDimensionVolume)
                        .addGap(63, 63, 63)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblMassPlanet)
                            .addComponent(jLblRadiusPlanet))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTfMassPlanetStart, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDimensionMassPlanet))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTfRadiusPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabelDimensionRadiusPlanet)))
                        .addGap(150, 150, 150))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDimensionMass)
                            .addComponent(jLabelDimensionBeta, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(94, 94, 94)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblEtaFluid)
                            .addComponent(jLblRhoFluid))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTfEtaFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDimensionViscosity))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jTfRhoFluidStart, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDimensionDensity)
                                .addGap(82, 82, 82)))
                        .addGap(42, 42, 42))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDimensionHeight)
                            .addComponent(jLabelDimensionSpeed))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLblRadiusBody)
                            .addComponent(jLblAreaFlowBody))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jLabelDimensionRadius))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTfAreaAffectedStart)
                                    .addComponent(jTfRadiusBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelDimensionArea)))
                        .addGap(73, 73, 73)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLblDt)
                                    .addComponent(jLblProposedComputiingTime))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTfProposedComputingTimeStart)
                                    .addComponent(jTfDtStart, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelDimensionInterval)
                                    .addComponent(jLabelDimensionTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabelThrowingRange)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTfThrowingRangeStart, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabelDimensionRange)))
                        .addGap(59, 59, 59))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCckBxComputeDensityStart)
                            .addComponent(jCckBxComputeBackwardsStart))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCckBxGravityStart)
                    .addComponent(jCckBxFlowResistanceStart)
                    .addComponent(jCckBxBuoyancyStart)
                    .addComponent(jCckBxViscosityStart)
                    .addComponent(jCckBxSimpleGravityStart))
                .addGap(92, 92, 92))
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
                                    .addComponent(jTfHeightStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelHeight))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfSpeedStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelSpeed)
                                    .addComponent(jLabelDimensionSpeed))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfAngleStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelAngle)
                                    .addComponent(jLabelDimensionBeta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(52, 52, 52)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelCw)
                                            .addComponent(jTfCwStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelMass)
                                            .addComponent(jTfMassBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionMass))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelVolume)
                                            .addComponent(jTfVolumeStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionVolume)))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfAreaAffectedStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelDimensionArea)
                                    .addComponent(jLblAreaFlowBody, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfRadiusBodyStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblRadiusBody)
                                    .addComponent(jLabelDimensionRadius))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTfRhoFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLblRhoFluid)
                                        .addComponent(jLabelDimensionDensity))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLblEtaFluid)
                                            .addComponent(jTfEtaFluidStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabelDimensionViscosity))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfRadiusPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblRadiusPlanet)
                                    .addComponent(jLabelDimensionRadiusPlanet))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTfMassPlanetStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLblMassPlanet)
                                    .addComponent(jLabelDimensionMassPlanet)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabelDimensionHeight))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLblDt)
                            .addComponent(jTfDtStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDimensionInterval))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTfProposedComputingTimeStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLblProposedComputiingTime)
                            .addComponent(jLabelDimensionTime))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabelDimensionRange))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelThrowingRange)
                                .addComponent(jTfThrowingRangeStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jCckBxComputeDensityStart)
                                .addGap(39, 39, 39))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCckBxComputeBackwardsStart))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jCckBxBuoyancyStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCckBxFlowResistanceStart)
                        .addGap(1, 1, 1)
                        .addComponent(jCckBxGravityStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCckBxViscosityStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCckBxSimpleGravityStart)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Start Data", jPanel2);

        jLabel1.setText("v:");

        jTfV.setEditable(false);

        jLabel2.setText("vx:");

        jTfVx.setEditable(false);

        jLabel3.setText("vy:");

        jTfVy.setEditable(false);

        jLabel4.setText("x:");

        jTfX.setEditable(false);

        jLabel5.setText("y:");

        jTfY.setEditable(false);

        jLabel6.setText("Height:");

        jTfHeight.setEditable(false);

        jLabel8.setText("Density:");

        jTfDensity.setEditable(false);
        jTfDensity.setToolTipText("relevant area for flow involving friction");

        jLabel9.setText("Angle(ground):");

        jTfAngleGround.setEditable(false);

        jLabel10.setText("Angle(x-axis):");

        jTfAngleXAxis.setEditable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTfVy, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTfVx, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTfV, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTfHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfX, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfY, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTfAngleXAxis, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfAngleGround, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTfDensity, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(568, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTfV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTfVx, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jTfHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTfVy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTfX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTfY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTfAngleXAxis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTfAngleGround, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTfDensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(139, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Present Data ", jPanel1);

        jTextFieldhMax.setEditable(false);

        jTextFieldhMin.setEditable(false);

        jTextFieldthrowingRange.setEditable(false);

        jTextFieldhEnd.setEditable(false);

        jLabel7.setText("Height maximum:");

        jLabel11.setText("Height minimum:");

        jLabel12.setText("Throwing range:");

        jTextFieldvEnd.setEditable(false);

        jTextFieldbetaEnd.setEditable(false);

        jLabel13.setText("Height end:");

        jLabel14.setText("Speed end:");

        jLabel15.setText("Angle end:");

        jTextFieldthrowingTime.setEditable(false);

        jLabel16.setText("Throwing time:");

        jButtonSetFinalValuesToStartParameters.setText("Set");
        jButtonSetFinalValuesToStartParameters.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSetFinalValuesToStartParametersActionPerformed(evt);
            }
        });

        jLabel17.setText("Set final values as new start parameters:");

        jLabel18.setText("s");

        jLabel19.setText("m");

        jLabel20.setText("m/s");

        jLabel21.setText("°");

        jLabel22.setText("m");

        jLabel23.setText("m");

        jLabel24.setText("m");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(509, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(439, 439, 439))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jLabel12)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldthrowingRange, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldhMin, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jTextFieldhMax, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldvEnd)
                    .addComponent(jTextFieldbetaEnd)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextFieldhEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jTextFieldthrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel18))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(62, 62, 62)
                                .addComponent(jLabel17))
                            .addComponent(jLabel20)
                            .addComponent(jLabel19))))
                .addContainerGap(291, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(453, 453, 453)
                .addComponent(jButtonSetFinalValuesToStartParameters)
                .addContainerGap(517, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldhEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel19)
                            .addComponent(jLabel22)
                            .addComponent(jTextFieldhMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldhMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldvEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20)
                            .addComponent(jLabel23))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldthrowingRange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jTextFieldbetaEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel21)
                            .addComponent(jLabel24)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldthrowingTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSetFinalValuesToStartParameters)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Final Values", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
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
        //UserInputNewParameters.currentSetting.setBeta(-Double.valueOf(jTextFieldbetaEnd.getText())*2*Math.PI/360);
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JTabbedPane jTabbedPane1;
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
    private javax.swing.JTextField jTfV;
    private javax.swing.JTextField jTfVolumeStart;
    private javax.swing.JTextField jTfVx;
    private javax.swing.JTextField jTfVy;
    private javax.swing.JTextField jTfX;
    private javax.swing.JTextField jTfY;
    // End of variables declaration//GEN-END:variables

}
