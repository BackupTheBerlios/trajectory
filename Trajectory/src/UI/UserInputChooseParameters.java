/*****************************************************************************
 * Filename:
 * Author:     
 * Date: 
 * History:   
 */

package UI;

import physics.Setting;
import physics.Forces;


public class UserInputChooseParameters extends javax.swing.JDialog {

    // TODO: A create a common place for all data in order to avoid scattering.
    public static Setting currentSetting;
    
    /** Creates new form UserInputNewParameters */
    public UserInputChooseParameters() {
        initComponents();
        this.setResizable(false);
        this.setModal(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupMedium = new javax.swing.ButtonGroup();
        buttonGroupPlanet = new javax.swing.ButtonGroup();
        buttonGroupBody = new javax.swing.ButtonGroup();
        jBtOK = new javax.swing.JButton();
        jBtReset = new javax.swing.JButton();
        jLabelMedium = new javax.swing.JLabel();
        jLabelPlanet = new javax.swing.JLabel();
        jRadioButtonEarth = new javax.swing.JRadioButton();
        jRadioButtonMoon = new javax.swing.JRadioButton();
        jRadioButtonMars = new javax.swing.JRadioButton();
        jRadioButtonMercur = new javax.swing.JRadioButton();
        jRadioButtonVenus = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jRadioButtonSphere = new javax.swing.JRadioButton();
        jRadioButtonCube = new javax.swing.JRadioButton();
        jRadioButtonPenguin = new javax.swing.JRadioButton();
        jRadioButtonParachute = new javax.swing.JRadioButton();
        jRadioButtonHuman = new javax.swing.JRadioButton();
        jRadioButtonAir = new javax.swing.JRadioButton();
        jRadioButtonWater = new javax.swing.JRadioButton();
        jRadioButtonGlycerin = new javax.swing.JRadioButton();
        jRadioButtonHoney = new javax.swing.JRadioButton();
        jRadioButtonHelium = new javax.swing.JRadioButton();
        jRadioButtonHydrogen = new javax.swing.JRadioButton();
        jRadioButtonPetroleum = new javax.swing.JRadioButton();
        jRadioButtonMercury = new javax.swing.JRadioButton();
        jRadioButtonAirFoil = new javax.swing.JRadioButton();
        jLabelPresetBody = new javax.swing.JLabel();
        jRadioButtonHeliumBalloon = new javax.swing.JRadioButton();
        jRadioButtonSphereOfWood = new javax.swing.JRadioButton();
        jRadioButtonSphereOfIron = new javax.swing.JRadioButton();
        jRadioButtonSphereOfPolystyrene = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jBtOK.setText("OK");
        jBtOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtOKActionPerformed(evt);
            }
        });

        jBtReset.setText("Reset");
        jBtReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtResetActionPerformed(evt);
            }
        });

        jLabelMedium.setText("Medium");

        jLabelPlanet.setText("Planet");

        buttonGroupPlanet.add(jRadioButtonEarth);
        jRadioButtonEarth.setText("Earth");

        buttonGroupPlanet.add(jRadioButtonMoon);
        jRadioButtonMoon.setText("Moon");

        buttonGroupPlanet.add(jRadioButtonMars);
        jRadioButtonMars.setText("Mars");

        buttonGroupPlanet.add(jRadioButtonMercur);
        jRadioButtonMercur.setText("Mercur");

        buttonGroupPlanet.add(jRadioButtonVenus);
        jRadioButtonVenus.setText("Venus");

        jLabel1.setText("Body");

        buttonGroupBody.add(jRadioButtonSphere);
        jRadioButtonSphere.setText("Sphere");

        buttonGroupBody.add(jRadioButtonCube);
        jRadioButtonCube.setText("Cube");

        buttonGroupBody.add(jRadioButtonPenguin);
        jRadioButtonPenguin.setText("Penguin");

        buttonGroupBody.add(jRadioButtonParachute);
        jRadioButtonParachute.setText("Parachute");

        buttonGroupBody.add(jRadioButtonHuman);
        jRadioButtonHuman.setText("Human");

        buttonGroupMedium.add(jRadioButtonAir);
        jRadioButtonAir.setText("Air");

        buttonGroupMedium.add(jRadioButtonWater);
        jRadioButtonWater.setText("Water");

        buttonGroupMedium.add(jRadioButtonGlycerin);
        jRadioButtonGlycerin.setText("Glycerin");

        buttonGroupMedium.add(jRadioButtonHoney);
        jRadioButtonHoney.setText("Honey");

        buttonGroupMedium.add(jRadioButtonHelium);
        jRadioButtonHelium.setText("Helium");

        buttonGroupMedium.add(jRadioButtonHydrogen);
        jRadioButtonHydrogen.setText("Hydrogen");

        buttonGroupMedium.add(jRadioButtonPetroleum);
        jRadioButtonPetroleum.setText("Petroleum");

        buttonGroupMedium.add(jRadioButtonMercury);
        jRadioButtonMercury.setText("Mercury");

        buttonGroupBody.add(jRadioButtonAirFoil);
        jRadioButtonAirFoil.setText("Airfoil");

        jLabelPresetBody.setText("Preset body");

        jRadioButtonHeliumBalloon.setText("Helium balloon");

        jRadioButtonSphereOfWood.setText("Sphere of wood");

        jRadioButtonSphereOfIron.setText("Spere of iron");

        jRadioButtonSphereOfPolystyrene.setText("Sphere of polystyrene");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPresetBody)
                            .addComponent(jRadioButtonHeliumBalloon)
                            .addComponent(jRadioButtonSphereOfWood)
                            .addComponent(jRadioButtonSphereOfIron)
                            .addComponent(jRadioButtonSphereOfPolystyrene))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jRadioButtonMars)
                                        .addComponent(jRadioButtonEarth)
                                        .addComponent(jRadioButtonMercur)
                                        .addComponent(jRadioButtonMoon))
                                    .addComponent(jRadioButtonVenus))
                                .addGap(69, 69, 69))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabelPlanet)
                                .addGap(80, 80, 80)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jRadioButtonSphere)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jRadioButtonAirFoil)
                                        .addComponent(jRadioButtonCube))))
                            .addComponent(jRadioButtonParachute)
                            .addComponent(jRadioButtonHuman)
                            .addComponent(jRadioButtonPenguin))
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabelMedium))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jRadioButtonAir)
                                .addComponent(jRadioButtonHydrogen)
                                .addComponent(jRadioButtonWater)
                                .addComponent(jRadioButtonGlycerin)
                                .addComponent(jRadioButtonHelium)
                                .addComponent(jRadioButtonPetroleum)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jRadioButtonHoney)
                                    .addGap(8, 8, 8))
                                .addComponent(jRadioButtonMercury)))
                        .addGap(77, 77, 77))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBtReset)
                        .addGap(27, 27, 27)
                        .addComponent(jBtOK)
                        .addContainerGap(413, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBtOK, jBtReset});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(jRadioButtonHeliumBalloon))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelPresetBody)))
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonSphereOfWood)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonSphereOfIron)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButtonSphereOfPolystyrene)
                        .addGap(114, 114, 114)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBtReset)
                            .addComponent(jBtOK)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelMedium)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonAir)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonHelium)
                                .addGap(15, 15, 15)
                                .addComponent(jRadioButtonHydrogen)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonWater)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButtonGlycerin)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonPetroleum)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonMercury)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonHoney))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(26, 26, 26)
                                        .addComponent(jRadioButtonSphere)
                                        .addGap(12, 12, 12)
                                        .addComponent(jRadioButtonCube)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonAirFoil)
                                        .addGap(12, 12, 12)
                                        .addComponent(jRadioButtonParachute)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonHuman)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonPenguin))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelPlanet)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jRadioButtonEarth)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonMoon)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonMars)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonMercur)
                                        .addGap(18, 18, 18)
                                        .addComponent(jRadioButtonVenus)))))))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

// use : At the time all values are resetted to 0 (in the `jTfXXX').
// TODO: check for non-numerical, nonsense input ->method taking a `setting'.
private void jBtResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtResetActionPerformed
  // show 0 values only on UI, changes to the real setting-object occur on
  // OK button´s action event.

  
}//GEN-LAST:event_jBtResetActionPerformed

private void jBtOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtOKActionPerformed

if (jRadioButtonEarth.isSelected()){
UserInputNewParameters.currentSetting.setR(physics.Constants.RADIUS_EARTH);
UserInputNewParameters.currentSetting.setM(physics.Constants.MASS_EARTH);
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_EARTH);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_EARTH);
}
if (jRadioButtonMoon.isSelected()){
UserInputNewParameters.currentSetting.setR(physics.Constants.RADIUS_MOON);
UserInputNewParameters.currentSetting.setM(physics.Constants.MASS_MOON);
}
if (jRadioButtonMars.isSelected()){
UserInputNewParameters.currentSetting.setR(physics.Constants.RADIUS_MARS);
UserInputNewParameters.currentSetting.setM(physics.Constants.MASS_MARS);
}
if (jRadioButtonMercur.isSelected()){
UserInputNewParameters.currentSetting.setR(physics.Constants.RADIUS_MERCUR);
UserInputNewParameters.currentSetting.setM(physics.Constants.MASS_MERCUR);
}
if (jRadioButtonVenus.isSelected()){
UserInputNewParameters.currentSetting.setR(physics.Constants.RADIUS_VENUS);
UserInputNewParameters.currentSetting.setM(physics.Constants.MASS_VENUS);
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_VENUS);    
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_VENUS);
}

if (jRadioButtonAir.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_AIR);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_AIR);
}
if (jRadioButtonHelium.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_HELIUM);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_HELIUM);
}
if (jRadioButtonHydrogen.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_HYDROGEN);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_HYDROGEN);
}
if (jRadioButtonWater.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_WATER);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_WATER);
}
if (jRadioButtonGlycerin.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_GLYCERIN);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_GLYCERIN);
}
if (jRadioButtonPetroleum.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_PARAFFIN);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_PARAFFIN);
}
if (jRadioButtonMercury.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_MERCURY);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_MERCURY);
}
if (jRadioButtonHoney.isSelected()){
UserInputNewParameters.currentSetting.setRho(physics.Constants.DENSITY_HONEY);
UserInputNewParameters.currentSetting.setEta(physics.Constants.VISCOSITY_HONEY);
}

if (jRadioButtonSphere.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_SPHERE);
}
if (jRadioButtonCube.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_CUBE);
}
if (jRadioButtonAirFoil.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_AIRFOIL);
}
if (jRadioButtonParachute.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_PARACHUTE);
}
if (jRadioButtonPenguin.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_PENGUIN);
}
if (jRadioButtonHuman.isSelected()){
UserInputNewParameters.currentSetting.setCw(physics.Constants.Cw_HUMAN);
}
  
    this.setVisible(false);
}//GEN-LAST:event_jBtOKActionPerformed

private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
            
           
}//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserInputChooseParameters().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupBody;
    private javax.swing.ButtonGroup buttonGroupMedium;
    private javax.swing.ButtonGroup buttonGroupPlanet;
    private javax.swing.JButton jBtOK;
    private javax.swing.JButton jBtReset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelMedium;
    private javax.swing.JLabel jLabelPlanet;
    private javax.swing.JLabel jLabelPresetBody;
    private javax.swing.JRadioButton jRadioButtonAir;
    private javax.swing.JRadioButton jRadioButtonAirFoil;
    private javax.swing.JRadioButton jRadioButtonCube;
    private javax.swing.JRadioButton jRadioButtonEarth;
    private javax.swing.JRadioButton jRadioButtonGlycerin;
    private javax.swing.JRadioButton jRadioButtonHelium;
    private javax.swing.JRadioButton jRadioButtonHeliumBalloon;
    private javax.swing.JRadioButton jRadioButtonHoney;
    private javax.swing.JRadioButton jRadioButtonHuman;
    private javax.swing.JRadioButton jRadioButtonHydrogen;
    private javax.swing.JRadioButton jRadioButtonMars;
    private javax.swing.JRadioButton jRadioButtonMercur;
    private javax.swing.JRadioButton jRadioButtonMercury;
    private javax.swing.JRadioButton jRadioButtonMoon;
    private javax.swing.JRadioButton jRadioButtonParachute;
    private javax.swing.JRadioButton jRadioButtonPenguin;
    private javax.swing.JRadioButton jRadioButtonPetroleum;
    private javax.swing.JRadioButton jRadioButtonSphere;
    private javax.swing.JRadioButton jRadioButtonSphereOfIron;
    private javax.swing.JRadioButton jRadioButtonSphereOfPolystyrene;
    private javax.swing.JRadioButton jRadioButtonSphereOfWood;
    private javax.swing.JRadioButton jRadioButtonVenus;
    private javax.swing.JRadioButton jRadioButtonWater;
    // End of variables declaration//GEN-END:variables

}
