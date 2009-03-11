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
 * DONE:    BE: A REDUCTION OF PTS to draw!!! 
 * TODO:    BE: D Communicate the jPnlDrawingPlane issue with the developers of
 *          netbeans IDE.
 * TODO:    BE: C Write a method that dumps all data into one "dump"-object for
 *          later sending bug reports automatically.
 * DONE:    BE: A BUG: paint() redraw nastily interwoven with paint()
 *          animation! animation starts by moving the screen ;-( subtle.
 * TODO:    BE: B Rethink: Trade-off: Code-reuse against speed and memory.
 *          Drawing via `xs' `ys' after reading them from the vector of moving
 *          bodies.
 * TODO:    BE: B Increment is still double.
 * TODO:    BE: C More efficient is to prepare the image, not to draw CS again
 *                all the time.
 */
package UI;

import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.util.Vector;
import java.awt.Color;
import javax.swing.*;
import java.io.*;

import static utilities.EulerIntegration.*;
import physics.MovingBody;
import java.awt.Cursor;
import utilities.*;
import modi.*;


// Use  : This class provides the users "main frame".
public class TrajectoryUI extends javax.swing.JFrame {

  // member variables
  public static DialogComputationInProgress dialog;
  
  static boolean isAnimationRunning = false;
  BufferedImage bi;
  static Thread th;
  Graphics2D big; // graphic context for the buffered image above

  private static int i = 0;
  private static int animationSpeed = 0;


  // setters
  public static void setAnimationSpeed(int speed){
      animationSpeed = speed;
  }

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
  // DONE: BE all drawn stuff vanishes when covered by another window.
  //
  public void myPaint(Graphics2D g,
          Vector<Double> xs,
          Vector<Double> ys) {
    g.setColor(Color.red); // drawing color

    try { // catching a NullPointerException in this try-block

      if (i >= xs.size()) {     // TODO: Discuss: Best behavior? Keep it or
        try {                   //       change it to let the user start again.
          Thread.sleep(3000);   //
        } catch (InterruptedException ex) {
          System.out.println("caught: " + ex);
          th.interrupt();
          isAnimationRunning = false;
        }
        i = 0; // i >= number of movingBodies to draw, start from the beginning.
      } // end if

      // TODO : @CT in every call to `myPaint()' this will be evaluated...
      // moving the code to Analysis in the eventhandler of your combobox
      // is best. then work with get and set to retrieve the value here.
      // i was tempted to do it for you, but remembered, that you were working
      // on stuff there. in order to make life easier for you i stopped.
      switch(Analysis.getSelectedIndexJComboBoxAnimationSpeed()){
        case 0: animationSpeed = 100;
          break;
        case 1: animationSpeed = 25;
          break;
        case 2: animationSpeed = 0; // if  trouble occurrs set to 5.
          break;
        default:
          System.out.println("You should never be able to read this.");
      } // end `switch'

      // doesn´t work here: a thread a sleeps within thread b and catches the
      // message for thread b. by the way it is clumsy -> see run method
      // instead; otherwise fine.
      // TODO: @CT feel free to delete this comment.
//      try {
//        Thread.sleep(animationSpeed);
//      } catch (InterruptedException ex) {
//        System.out.println("myPaint(): " + ex);
//      }

      // TODO: BE: B  draw a nice coordinate system.
      ScreenUtilities.drawCoordinateSystem(
              g, xs, ys,
              guitest.Main.xMin,
              guitest.Main.yMin,
              jPnlDrawingPlane.getWidth(),
              jPnlDrawingPlane.getHeight());

      // TODO: BE: C  improve transformation.
      g.drawOval((int) (xs.get(i).floatValue() * ScreenUtilities.scalingFactor - 
              guitest.Main.xMin * ScreenUtilities.scalingFactor +
              ScreenUtilities.ORIGIN_OFFSET),
              (int) (ScreenUtilities.SCREEN_HEIGHT -
              (ys.get(i).floatValue() - guitest.Main.yMin) *
              ScreenUtilities.scalingFactor) + ScreenUtilities.ORIGIN_OFFSET,
              5, 5);

      // as drawing has been directed to the buffered image `bi' before,
      // now display the results on the screen (a.k.a. `jPnlDrawingPlane')
      Graphics2D tmpG2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
      tmpG2d.drawImage(bi, 0, 0, this);

      // TODO: B BE: data flow?
      if (positions.size() != 0) {   // if there is something to draw at all:
        displayValuesOnAnalysisUI(); // of the current Body being drawn
      }

      i += ScreenUtilities.increment; // reduction of pts to draw happens here

    } catch (NullPointerException ex) {
      // TODO: BE: C encapsulate a ~th.myStop() method...
      ex.getCause().printStackTrace(System.out);
      System.out.print("~~~ If you can read this s.th. went terribly wrong:");
      System.out.println("method myPaint: " + ex);
      System.out.println("stack-trace: ");
      System.out.println("stopping runner (thread)...");
      th.interrupt();
      isAnimationRunning = false;
      System.out.println("runner stopped.");
    } finally { 
      dumpInfoToStdOut(xs, ys); // lots of info
    }

  } // end `myPaint()'

  
  // constructor
  public TrajectoryUI() {
    initComponents();
    jPnlDrawingPlane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

    // double buffering items
    bi = (BufferedImage) jPnlDrawingPlane.createImage(
            1018,//jPnlDrawingPlane.getWidth(), // seems like an odd IDE-issue, that the method-call returns the initial, not the current values.
            708);//jPnlDrawingPlane.getHeight()); // TODO: investigate the issue further :-(

    System.out.println("bi: " + bi.getWidth() + " " + bi.getHeight());
    System.out.println("jpnl: " + jPnlDrawingPlane.getWidth() + " " +
            jPnlDrawingPlane.getHeight());
    big = (Graphics2D) bi.getGraphics();
  }


  private class DrawingPanel extends JPanel {
    public DrawingPanel() {
      super(true); // double-buffered enabled JPanel
    }

 // Use: DrawingPanels´ paint method
    @Override
    public void paint(Graphics g) {
      Graphics2D g2d = (Graphics2D) g;
      g2d.drawImage(bi, null, 0, 0);
    }
  } // end `DrawingPanel'


  // Use:  Computing height and angle according to the user´s choice,
  //       isActingGravity == {true, false}; Print computed values to AnalysisUI
  // Pre:  Just useful where it stands right now ;-(
  private void displayValuesOnAnalysisUI(){
    MovingBody tmp = positions.get(i);
    physics.Setting tempSetting = UserInputNewParameters.currentSetting;
    double hTemp = 0.0;
    double angleTemp = 0.0;

    // compute the potentially different height and angle
    if (!physics.Forces.isActingGravity()) {
      hTemp = tmp.getLocation(tempSetting).getY();
      angleTemp = tmp.getBeta();
    } else {
      hTemp = tmp.getLocation(tempSetting).getH();
      angleTemp = (tmp.getAngleVvectorToRvector() - 90);
    }
    guitest.Main.analysisUI.displayValues(
       tmp.getV(), tmp.getVx(), tmp.getVy(),
       tmp.getLocation(tempSetting).getX(), tmp.getLocation(tempSetting).getY(),
       hTemp, tmp.getLocation(tempSetting).getRho(), tmp.getBeta(), angleTemp);

  } // end `displayValuesOnAnalysisUI()'

  private void dumpInfoToStdOut(Vector<Double> xs, Vector<Double> ys){
    System.out.println("i: " + i);
    System.out.println("### inc: " + ScreenUtilities.increment);
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

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jPnlDrawingPlane = new DrawingPanel();
    jMenuBar = new javax.swing.JMenuBar();
    jMenuFile = new javax.swing.JMenu();
    jMenuItemOpenFile = new javax.swing.JMenuItem();
    jMenuItemClose = new javax.swing.JMenuItem();
    jMenuSet = new javax.swing.JMenu();
    jMenuItemSetNewParameters = new javax.swing.JMenuItem();
    jMenuItemLoadPresetFile = new javax.swing.JMenuItem();
    jMenuComputation = new javax.swing.JMenu();
    jMenuItemCompute = new javax.swing.JMenuItem();
    jMenuItemReset = new javax.swing.JMenuItem();
    jMenuAnimation = new javax.swing.JMenu();
    jMenuItemStartAnimation = new javax.swing.JMenuItem();
    jMenuEdit = new javax.swing.JMenu();
    jMenuItemMeasureMode = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jPnlDrawingPlane.setBackground(new java.awt.Color(255, 204, 0));
    jPnlDrawingPlane.setName("jPnlDrawingPlane"); // NOI18N
    jPnlDrawingPlane.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        jPnlDrawingPlaneMouseClicked(evt);
      }
    });
    jPnlDrawingPlane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
      public void mouseMoved(java.awt.event.MouseEvent evt) {
        jPnlDrawingPlaneMouseMoved(evt);
      }
    });

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

    jMenuItemCompute.setText("Compute");
    jMenuItemCompute.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemComputeActionPerformed(evt);
      }
    });
    jMenuComputation.add(jMenuItemCompute);

    jMenuItemReset.setText("Reset");
    jMenuItemReset.setToolTipText("Stops the animation and clears the screen.");
    jMenuItemReset.setActionCommand("Reset");
    jMenuItemReset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemResetActionPerformed(evt);
      }
    });
    jMenuComputation.add(jMenuItemReset);

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

    jMenuItemMeasureMode.setText("Measure Mode");
    jMenuItemMeasureMode.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemMeasureModeActionPerformed(evt);
      }
    });
    jMenuEdit.add(jMenuItemMeasureMode);

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
  UserInputNewParameters userInputDialog = new UserInputNewParameters();
  userInputDialog.setAlwaysOnTop(true);
  userInputDialog.setVisible(true);
  userInputDialog.setModal(true);
}//GEN-LAST:event_jMenuItemSetNewParametersActionPerformed

private void jMenuItemStartAnimationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStartAnimationActionPerformed

  if (!isAnimationRunning) {
    startAnimation();
    isAnimationRunning = true;
  } else { // don´t start another thread. // TODO: BE inform user
    System.out.println("already running...");
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
      dialog = new DialogComputationInProgress(guitest.Main.ui, false);
      dialog.setVisible(true);

      EulerIntegration.EulerIntegrationTask task = new EulerIntegration().new EulerIntegrationTask();
      task.addPropertyChangeListener(task);
      task.execute();
      Thread.yield();
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

  // BE: netbeansIDE signals a warning, but SUN says it is alright like this.
  //     I vote for leaving the warning, otherwise if something goes wrong, it
  //     would be shaded by the IDE imho.
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


private void jMenuItemMeasureModeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMeasureModeActionPerformed
  AnimationStop animationStopDialog;// = new UI.AnimationStop(this,true);
  if ((th != null) && (th.isInterrupted())) {
    modi.MeasureMode.setIsDisplayXYcoordsEnabled(true);
  } else {
    // ask user if he wants to stop the animation and start taking
    // measures
    animationStopDialog = new UI.AnimationStop(this, true);
    animationStopDialog.setVisible(true);
  }
}//GEN-LAST:event_jMenuItemMeasureModeActionPerformed


private void jPnlDrawingPlaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPnlDrawingPlaneMouseMoved
  if (MeasureMode.getIsDisplayXYCoordsEnabled()) {
    Graphics2D g2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
    g2d.drawImage(bi, null, 0, 0);
    System.out.println("X: " + evt.getX() + " Y: " + evt.getY());
    if (ScreenUtilities.scalingFactor != 0) {
      System.out.println("X: " +
              MeasureMode.screenCoordsToReality(0, evt.getX()) +
              "Y: " + MeasureMode.screenCoordsToReality(1, evt.getY()));
      g2d.drawString("X: " + evt.getX() + " Y: " + evt.getY(),
              evt.getX(), evt.getY());
    } // end if
  } // end if
}//GEN-LAST:event_jPnlDrawingPlaneMouseMoved


private void jPnlDrawingPlaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPnlDrawingPlaneMouseClicked
  if (evt.getButton() == MouseEvent.BUTTON3) {
    System.out.println("RIGHT");

  }
}//GEN-LAST:event_jPnlDrawingPlaneMouseClicked


private void jMenuItemResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemResetActionPerformed
  th.interrupt();
  System.out.println("+++++++++++++++++++++++++++ th ID:" + th);
  guitest.Main.ui.dispose();
  System.out.println("+++++++++++++++++++++++++++ th ID:" + th);
  guitest.Main.ui = new TrajectoryUI();
  guitest.Main.ui.setSize(1024, 768);  // hard-wired screen resolution...
  guitest.Main.ui.setResizable(false); // maybe via config file, to keep it small and
  guitest.Main.ui.setVisible(true);    // simple?
  ScreenUtilities.setCompareTrajectories(false);
  TrajectoryUI.isAnimationRunning = false;
  System.gc(); // free memory
}//GEN-LAST:event_jMenuItemResetActionPerformed


    // Use:  to run the form itself.
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrajectoryUI().setVisible(true);
            }
        });
    }

    
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenu jMenuAnimation;
  private javax.swing.JMenuBar jMenuBar;
  private javax.swing.JMenu jMenuComputation;
  private javax.swing.JMenu jMenuEdit;
  private javax.swing.JMenu jMenuFile;
  private javax.swing.JMenuItem jMenuItemClose;
  private javax.swing.JMenuItem jMenuItemCompute;
  private javax.swing.JMenuItem jMenuItemLoadPresetFile;
  private javax.swing.JMenuItem jMenuItemMeasureMode;
  private javax.swing.JMenuItem jMenuItemOpenFile;
  private javax.swing.JMenuItem jMenuItemReset;
  private javax.swing.JMenuItem jMenuItemSetNewParameters;
  private javax.swing.JMenuItem jMenuItemStartAnimation;
  private javax.swing.JMenu jMenuSet;
  private static javax.swing.JPanel jPnlDrawingPlane;
  // End of variables declaration//GEN-END:variables

  
    public void startAnimation() {
        th = new RunnerThread(); // TODO: possible spamming prevention
        th.start();
    }


    // Use:  Class providing the animation thread.
    private class RunnerThread extends Thread {
      private int runnerID = 0;

      // constructor
      public RunnerThread(){
        incID();
        System.out.println("~~~~~~~~~~~~~~~~~~~+I am alive: " + this.getID());
      }

      // methods
      public void incID(){
        runnerID++;
      }
      public void decID(){
        runnerID--;
      }
      
      public int getID(){
        return this.runnerID;
      }

      @Override
      public void run() {
        while (!isInterrupted()) { // donn´t clear the interrupt flag.
          try {

            myPaint(big, ScreenUtilities.xs, ScreenUtilities.ys);
            Thread.sleep(TrajectoryUI.animationSpeed); // TODO: C BE: variable speeds possible.

          } catch (InterruptedException ex) {
              System.out.println("################################ " + ex +
                                 " " + this.getID());
              break;
          }

        } // end while
        System.out.println("############################### thread: " + getID() +" : " + "DONE");
        decID();
        System.out.println("############################### threads left: " + getID());
      } // end run()
    } // end `RunnerThread'
} // end of class `TrajectoryUI'
