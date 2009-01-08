/*****************************************************************************
 * Use:     This class `TrajectoryUI' is the place of the main frame.  It   is
 *          the application´s main frame providing class and home of the
 *          animation thread (so far)
 *          , which holds the code for drawing the by then computed
 *          trajectories.
 * Author:  BE
 * Date:    2008-09-30 , and the days b4
 *          2008-12-28
 *          2009-01-05
 * 
 * TODO:    BE: A smooth the code
 * TODO:    BE: A prevent thread spamming!
 * TODO:    BE: A catch drawing without file
 * TODO:    BE: B check overall structure
 * TODO:    BE: C animation speed user-adjustable, possibly during
 *              animation.
 * TODO:    BE: o is it a good idea to implement user control for `increment'?
 * TODO:    BE: A REDUCTION OF PTS to draw!!! DONE - BE Rem: use it as error
 *              indicator...
 * TODO:    BE: C Communicate the jPnlDrawingPlane issue with the developers of
 *          netbeans IDE.
 * TODO:    BE: C Write a method that dumps all data into one "dump"-object for
 *          later sending bug reports automatically.
 * TODO:    BE: A KILLERBUG: paint() redraw nastily interwoven with paint()
 *          animation! animation starts by moving the screen ;-( subtle.
 *          DONE BE.
 * TODO:    BE: B Rethink: Trade-off: Code-reuse against speed and memory.
 *          Drawing via `xs' `ys' after reading them from the vector of moving
 *          bodies.
 */
package UI;

import java.io.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Vector;
import java.lang.Thread;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

import UI.UserInputNewParameters;
import utilities.*;
import static utilities.EulerIntegration.*;
import physics.MovingBody;

/**
 *
 * @author  BE
 */
public class TrajectoryUI extends javax.swing.JFrame {

  // member variables
  private static int i = 0;
  static boolean isAnimationRunning = false;
  static Thread th;
  BufferedImage bi;
  Graphics2D big; // graphic context for the buffered image above

  // setters
  public static void setCounterToZero() {
    TrajectoryUI.i = 0;
  }

  /////////////
  // methods //
  /////////////    
  // use:  draws  the   points  <x_i, y_i>  given  in  the  argument vectors
  // pre:  for all data to be drawn the `xs', `ys' datastructures have to be
  //       filled properly. ScreenUtilities.scalingFactor is set accordingly
  // post: as long as the thread calling this method runs, the points in the
  //       above  mentioned  datastructures  are drawn to `jPnlDrawingPlane'
  // TODO: solve: all drawn stuff vanishes when covered by another window.
  //
  public void myPaint(Graphics2D g,
          Vector<Double> xs,
          Vector<Double> ys) {
    g.setColor(Color.RED); // drawing color

    try { // catching a NullPointerException in this try-block

      if (i >= xs.size()) {     // TODO: on debugging animation runs again
        try {                   //       after a pause of 3 seconds
          Thread.sleep(3000);   //
        } catch (InterruptedException ex) {
          System.out.println("caught: " + ex);
          th.interrupt();
          isAnimationRunning = false;
        }
        i = 0;
      }

      // TODO: BE: A  draw a nice coordinate system.
      ScreenUtilities.drawCoordinateSystem(
              g, xs, ys, 
              guitest.Main.xMin,
              guitest.Main.yMin,
              jPnlDrawingPlane.getWidth(),
              jPnlDrawingPlane.getHeight());
      
      // TODO: BE: A  improve transformation.
      g.drawOval((int) (xs.get(i).floatValue() * ScreenUtilities.scalingFactor - guitest.Main.xMin * ScreenUtilities.scalingFactor + ScreenUtilities.ORIGIN_OFFSET),
              (int) (ScreenUtilities.SCREEN_HEIGHT -
              (ys.get(i).floatValue() - guitest.Main.yMin) *
              ScreenUtilities.scalingFactor) + ScreenUtilities.ORIGIN_OFFSET,
              5, 5);
      /////System.out.println(TrajectoryUI.jPnlDrawingPlane.getWidth() + " " + TrajectoryUI.jPnlDrawingPlane.getHeight());

      // as drawing has been directed to the buffered image `bi' before,
      // now display the results on the screen (a.k.a. `jPnlDrawingPlane')
      Graphics2D tmpG2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
      tmpG2d.drawImage(bi, 0, 0, this);

      if(positions.size()!=0){
      MovingBody tmp = positions.get(i);
      guitest.Main.analysisUI.displayValues(
                tmp.getAngleVvectorToRvector(),
                tmp.getA(),
                tmp.getCw(),
                tmp.getM(),
                tmp.getR(),
                tmp.getV(),
                tmp.getVol(),
                tmp.getVx(),
                tmp.getVy()
                );
      }
      i += ScreenUtilities.increment; // reduction of pts to draw happens here

    } catch (NullPointerException ex) {
      // TODO: BE: C encapsulate a ~th.myStop() method...
      ex.getCause().printStackTrace(System.out);
      System.out.println("method myPaint: " + ex);
      System.out.println("stack-trace: ");
      System.out.println("stopping runner (thread)...");
      th.interrupt();
      isAnimationRunning = false;
      System.out.println("runner stopped.");
    } finally { // lots of info
      System.out.println("i: " + i);
      System.out.println("xs(0): " + xs.get(0));
      System.out.println("ys(0): " + ys.get(0));
      System.out.println("size:" + EulerIntegration.positions.size());
      System.out.println("xs-size: " + xs.size());
      System.out.println("xs-size: " + xs.size());
      System.out.println("sf: " + ScreenUtilities.scalingFactor);
      System.out.println("inc: " + ScreenUtilities.increment);
      System.out.println("ymax: " + guitest.Main.yMax);
      System.out.println("ymin: " + guitest.Main.yMin);
      System.out.println("xmax: " + guitest.Main.xMax);
      System.out.println("xmin: " + guitest.Main.xMin);
    }
  }

  /** Creates new form TrajectoryUI */
  public TrajectoryUI() {
    initComponents();
    // double buffering items
    bi = (BufferedImage) jPnlDrawingPlane.createImage(
            1018,//jPnlDrawingPlane.getWidth(), // seems like an odd IDE-issue, that the method-call returns the initial, not the current values.
            708);//jPnlDrawingPlane.getHeight()); // TODO: investigate the issue further :-(
    System.out.println("bi: " + bi.getWidth() + " " + bi.getHeight());
    System.out.println(
            "jpnl: " + jPnlDrawingPlane.getWidth() + " " + jPnlDrawingPlane.getHeight());
    big = (Graphics2D) bi.getGraphics();
  }

  private class DrawingPanel extends JPanel {

    public DrawingPanel() {
      super(true); // double-buffered enabled JPanel
    }
    
    @Override
    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(bi, null, 0, 0);
    }
    
  } // end `DrawingPanel'

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPnlDrawingPlane = new DrawingPanel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();
    jMenu3 = new javax.swing.JMenu();
    jMenu2 = new javax.swing.JMenu();
    jMenuBar2 = new javax.swing.JMenuBar();
    jMenu4 = new javax.swing.JMenu();
    jMenuItem2 = new javax.swing.JMenuItem();
    jMenu5 = new javax.swing.JMenu();
    jMenu6 = new javax.swing.JMenu();
    jMenuBar = new javax.swing.JMenuBar();
    jMenuFile = new javax.swing.JMenu();
    jMenuItemOpenFile = new javax.swing.JMenuItem();
    jMenuItemClose = new javax.swing.JMenuItem();
    jMenuSet = new javax.swing.JMenu();
    jMenuItemSetNewParameters = new javax.swing.JMenuItem();
    jMenuItemLoadPresetFile = new javax.swing.JMenuItem();
    jMenuComputation = new javax.swing.JMenu();
    jMenuItemCompute = new javax.swing.JMenuItem();
    jMenuAnimation = new javax.swing.JMenu();
    jMenuItemStartAnimation = new javax.swing.JMenuItem();
    jMenuEdit = new javax.swing.JMenu();
    jMenuItem3 = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jPnlDrawingPlane.setBackground(new java.awt.Color(255, 204, 0));
    jPnlDrawingPlane.setName("jPnlDrawingPlane"); // NOI18N

    javax.swing.GroupLayout jPnlDrawingPlaneLayout = new javax.swing.GroupLayout(jPnlDrawingPlane);
    jPnlDrawingPlane.setLayout(jPnlDrawingPlaneLayout);
    jPnlDrawingPlaneLayout.setHorizontalGroup(
      jPnlDrawingPlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 400, Short.MAX_VALUE)
    );
    jPnlDrawingPlaneLayout.setVerticalGroup(
      jPnlDrawingPlaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 273, Short.MAX_VALUE)
    );

    jMenu1.setText("File");

    jMenuItem1.setText("Close");
    jMenu1.add(jMenuItem1);

    jMenuBar1.add(jMenu1);

    jMenu3.setText("Menu");
    jMenuBar1.add(jMenu3);

    jMenu2.setText("Edit");
    jMenuBar1.add(jMenu2);

    jMenu4.setText("File");

    jMenuItem2.setText("Close");
    jMenu4.add(jMenuItem2);

    jMenuBar2.add(jMenu4);

    jMenu5.setText("Menu");
    jMenuBar2.add(jMenu5);

    jMenu6.setText("Edit");
    jMenuBar2.add(jMenu6);

    jMenuBar.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentHidden(java.awt.event.ComponentEvent evt) {
        jMenuBarComponentHidden(evt);
      }
    });

    jMenuFile.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuFile.setText("File");
    jMenuFile.setDelay(100);

    jMenuItemOpenFile.setToolTipText("Open a file to read data.");
    jMenuItemOpenFile.setActionCommand("OpenFile");
    jMenuItemOpenFile.setLabel("Open File...");
    jMenuItemOpenFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemOpenFileActionPerformed(evt);
      }
    });
    jMenuFile.add(jMenuItemOpenFile);

    jMenuItemClose.setLabel("Exit");
    jMenuFile.add(jMenuItemClose);

    jMenuBar.add(jMenuFile);

    jMenuSet.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuSet.setText("Set");

    jMenuItemSetNewParameters.setText("New Parameters...");
    jMenuItemSetNewParameters.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemSetNewParametersActionPerformed(evt);
      }
    });
    jMenuSet.add(jMenuItemSetNewParameters);

    jMenuItemLoadPresetFile.setText("Load Preset File...");
    jMenuItemLoadPresetFile.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentHidden(java.awt.event.ComponentEvent evt) {
        jMenuItemLoadPresetFileComponentHidden(evt);
      }
    });
    jMenuItemLoadPresetFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemLoadPresetFileActionPerformed(evt);
      }
    });
    jMenuSet.add(jMenuItemLoadPresetFile);

    jMenuBar.add(jMenuSet);

    jMenuComputation.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuComputation.setText("Computation");

    jMenuItemCompute.setText("compute");
    jMenuItemCompute.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemComputeActionPerformed(evt);
      }
    });
    jMenuComputation.add(jMenuItemCompute);

    jMenuBar.add(jMenuComputation);

    jMenuAnimation.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuAnimation.setText("Animation");

    jMenuItemStartAnimation.setText("Start Animation(File)");
    jMenuItemStartAnimation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemStartAnimationActionPerformed(evt);
      }
    });
    jMenuAnimation.add(jMenuItemStartAnimation);

    jMenuBar.add(jMenuAnimation);

    jMenuEdit.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuEdit.setText("Edit");

    jMenuItem3.setText("not implemented yet");
    jMenuEdit.add(jMenuItem3);

    jMenuBar.add(jMenuEdit);

    setJMenuBar(jMenuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(jPnlDrawingPlane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jPnlDrawingPlane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

private void jMenuItemSetNewParametersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSetNewParametersActionPerformed
  UserInputNewParameters dialog = new UserInputNewParameters();
  //dialog.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
  dialog.setAlwaysOnTop(true);
  dialog.setVisible(true);
  dialog.setModal(true);
}//GEN-LAST:event_jMenuItemSetNewParametersActionPerformed

private void jMenuItemStartAnimationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStartAnimationActionPerformed
  //UI.TrajectoryUI.setCounterToZero();
  if (!isAnimationRunning) {
    startAnimation();
    isAnimationRunning = true;
  } else {
    System.out.println("already running...");
    th.interrupt();
    isAnimationRunning = false;
  }
}//GEN-LAST:event_jMenuItemStartAnimationActionPerformed

//use:  display an open file dialog for the user.
//pre:  -
//post: if approved the selected file is read into the datastructures in order
//      to be displayed.
//TODO: secure method
  @SuppressWarnings("static-access")
private void jMenuItemOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemOpenFileActionPerformed

    JFileChooser fc = new JFileChooser(); // bring up a new filechooser

    System.out.println("ATTENTION: this is a workaround!!");
    //TODO: Either find a way to eliminate the WORKAROUND, or copy the
    //preset data into the distribution folder!!! ->batch file
    File pathToDeliveredProgram = new File("");
    fc.setCurrentDirectory(new File(pathToDeliveredProgram.getAbsolutePath().toString() + System.getProperty("file.separator") + "testcases"));

    // TODO: END WORKAROUND.

    int retval = fc.showOpenDialog(this);


    if (retval == fc.APPROVE_OPTION) { // check whether file selected or not
      ScreenUtilities.setOpenedFile(fc.getSelectedFile());
      try {
        System.out.println("Choosen file: " +
                ScreenUtilities.getOpenedFile().getCanonicalPath());

        // TODO provisional, clean up code
        ScreenUtilities.readFileToStructure(ScreenUtilities.getOpenedFile(),
                ScreenUtilities.xs, ScreenUtilities.ys);

      } catch (IOException ex) {
        System.out.println(ex);
      }
    }
}//GEN-LAST:event_jMenuItemOpenFileActionPerformed

// use:  start the computation of a trajectory.
// pre:  everything is propery set via menu `Set' ...
// post: the datastructures `xs', `ys' in class ScreenUtilities are filled
//       with the results of the computation.
private void jMenuItemComputeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemComputeActionPerformed
          EulerIntegration.eulerIntegrate();
//    for (int k=0; k<ScreenUtilities.xs.size(); k++){
//        System.out.println("from TrajectoryUI: x: " + ScreenUtilities.xs.get(k) + 
//        "y: " + ScreenUtilities.ys.get(k));
//    }
          System.out.println("xs-size: " + ScreenUtilities.xs.size());
          System.out.println("ys-size: " + ScreenUtilities.ys.size());
          System.out.println("positions-size: " + EulerIntegration.positions.size());

}//GEN-LAST:event_jMenuItemComputeActionPerformed

private void jMenuItemLoadPresetFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLoadPresetFileActionPerformed
// TODO: B pack some more preset files.
  File file;
  JFileChooser fc = new JFileChooser(); // bring up a new filechooser

  //TODO: Either find a way to eliminate the WORKAROUND, or copy the
  //preset data into the distribution folder!!! ->batch file
  File pathToDeliveredProgram = new File("");
  fc.setCurrentDirectory(new File(
          pathToDeliveredProgram.getAbsolutePath().toString() + System.getProperty("file.separator") + "presets"));

  // TODO: END WORKAROUND.


  int retval = fc.showOpenDialog(this); // show open file dialog

  if (retval == fc.APPROVE_OPTION) { // check whether file selected or aborted
    try {
      file = fc.getSelectedFile();
      System.out.println("Choosen file: " + file.getCanonicalPath()); // TODO: clean up

      // load the one and only `currentSetting' with the values from the file.
      UI.UserInputNewParameters.currentSetting.loadValuesFromFile(file);
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

}//GEN-LAST:event_jMenuItemLoadPresetFileActionPerformed

private void jMenuBarComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jMenuBarComponentHidden
// TODO add your handling code here:
}//GEN-LAST:event_jMenuBarComponentHidden

private void jMenuItemLoadPresetFileComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jMenuItemLoadPresetFileComponentHidden
// TODO add your handling code here:
}//GEN-LAST:event_jMenuItemLoadPresetFileComponentHidden
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrajectoryUI().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenu jMenu3;
  private javax.swing.JMenu jMenu4;
  private javax.swing.JMenu jMenu5;
  private javax.swing.JMenu jMenu6;
  private javax.swing.JMenu jMenuAnimation;
  private javax.swing.JMenuBar jMenuBar;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuBar jMenuBar2;
  private javax.swing.JMenu jMenuComputation;
  private javax.swing.JMenu jMenuEdit;
  private javax.swing.JMenu jMenuFile;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JMenuItem jMenuItem2;
  private javax.swing.JMenuItem jMenuItem3;
  private javax.swing.JMenuItem jMenuItemClose;
  private javax.swing.JMenuItem jMenuItemCompute;
  private javax.swing.JMenuItem jMenuItemLoadPresetFile;
  private javax.swing.JMenuItem jMenuItemOpenFile;
  private javax.swing.JMenuItem jMenuItemSetNewParameters;
  private javax.swing.JMenuItem jMenuItemStartAnimation;
  private javax.swing.JMenu jMenuSet;
  private static javax.swing.JPanel jPnlDrawingPlane;
  // End of variables declaration//GEN-END:variables
  
    public void startAnimation() {
        th = new RunnerThread(); // TODO: possible spamming prevention
        th.start();
    }

    
    private class RunnerThread extends Thread {

        @Override
      public void run() {
        while (!isInterrupted()) {
          myPaint(big,
                  ScreenUtilities.xs, ScreenUtilities.ys);
          //jPnlDrawingPlane.repaint();
          try {
            Thread.sleep(25); // TODO: C BE: variable speeds possible.
          } catch (InterruptedException ex) {
            System.out.println(ex);
            return; // good style?
          }
        }
      }
    } // end `RunnerThread'
} // end of class `TrajectoryUI'