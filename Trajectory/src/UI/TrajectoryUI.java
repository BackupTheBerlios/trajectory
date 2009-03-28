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
 * TODO:    BE: R We will reference priority R as roadmap features.
 * TODO:    BE: R printing.
 * TODO:    BE: R saving user altered settings.
 * TODO:    BE: R compute the hitting point more precisely via change in sgn().
 * TODO:    BE: R extend measure mode to allow user to keep references for
 *                user-chosen parameters at any given point on the displayed
 *                screen.
 * TODO:    BE: R Density map - user drawable.
 * TODO:    CT/BE: R add the feature of defining acting forces at runtime.
 *          BE:    Possible via interpreter.
 * TODO:    BE: A Use the `infinity alert' cases in order to provide better
 *              user-experience for novices.
 * TODO:    BE: C,R (Auto-) Feedback facility; logging.
 * 
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

  private static int currentXscreenCoord = -10;
  private static int currentYscreenCoord = -10;

  private static int xScreen = -10; // used for keeping the coords the mouse
  private static int yScreen = -10; // is pointing at.
  private static int labelPositionX = -10; // used for determining the position
  private static int labelPositionY = -10; // of the x,y - coords user info.

  // private static boolean chooseMeasureMode = false;

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

//      if (i >= xs.size()) {     // TODO: Discuss: Best behavior? Keep it or
//        try {                   //       change it to let the user start again.
//          Thread.sleep(3000);   //
//        } catch (InterruptedException ex) {
//          System.out.println("caught: " + ex);
//          th.interrupt();
//          isAnimationRunning = false;
//        }
//        i = 0; // i >= number of movingBodies to draw, start from the beginning.
//      } // end if


      // use currently chosen slider value as waiting time for the animation
      animationSpeed = Analysis.getAnimationSpeedValue();

      // TODO: BE: B  draw a nice coordinate system.
      ScreenUtilities.drawCoordinateSystem(
              g, xs, ys,
              guitest.Main.xMin,
              guitest.Main.yMin,
              jPnlDrawingPlane.getWidth(),
              jPnlDrawingPlane.getHeight());

      // TODO: BE: C  improve transformation.
      
      // as we need the current coords twice:
      currentXscreenCoord = (int) (xs.get(i).floatValue() *
              ScreenUtilities.scalingFactor -
              guitest.Main.xMin * ScreenUtilities.scalingFactor +
              ScreenUtilities.ORIGIN_OFFSET);
      currentYscreenCoord=(int) (ScreenUtilities.SCREEN_HEIGHT -
              (ys.get(i).floatValue() - guitest.Main.yMin) *
              ScreenUtilities.scalingFactor) + ScreenUtilities.ORIGIN_OFFSET;

      // draw the trajectory
      // size 2 is choosen because a  single pixel can make the novice
      // see nothing. imho the chances that he can figure out that his
      // dt/settings  in  general  are  flawed  do  vastly increase by
      // drawing the trajectory bigger than 1, maybe even 3 is better.
      g.drawOval(
              currentXscreenCoord,
              currentYscreenCoord,
              2, 2
      );

      // as drawing has been directed to the buffered image `bi' before,
      // now display the results on the screen (a.k.a. `jPnlDrawingPlane')
      Graphics2D tmpG2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
      tmpG2d.drawImage(bi, 0, 0, this);

      // draw the body (representing the actual moving body itself).
      tmpG2d.setColor(Color.BLUE); // TODO: D BE implement a color-chooser.
      tmpG2d.drawOval( 
              currentXscreenCoord,
              currentYscreenCoord,
              5, 5
      );

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
    double dt = tempSetting.getDt();

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
       hTemp, tmp.getLocation(tempSetting).getRho(), tmp.getBeta(), angleTemp,
       UI.Analysis.computeThrowingTime(i, dt));

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
    jMenuItemStopAnimation = new javax.swing.JMenuItem();
    jMenuEdit = new javax.swing.JMenu();
    jMenuItemMeasureMode = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("--- Project Trajectory 2009 ---");

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

    jMenuBar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jMenuBar.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentHidden(java.awt.event.ComponentEvent evt) {
        jMenuBarComponentHidden(evt);
      }
    });

    jMenuFile.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuFile.setText("File");
    jMenuFile.setDelay(100);
    jMenuFile.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N

    jMenuItemOpenFile.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemOpenFile.setText("Open file ...");
    jMenuItemOpenFile.setToolTipText("Open a file to read data.");
    jMenuItemOpenFile.setActionCommand("OpenFile");
    jMenuItemOpenFile.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemOpenFileActionPerformed(evt);
      }
    });
    jMenuFile.add(jMenuItemOpenFile);

    jMenuItemClose.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jMenuItemClose.setLabel("Exit");
    jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemCloseActionPerformed(evt);
      }
    });
    jMenuFile.add(jMenuItemClose);

    jMenuBar.add(jMenuFile);

    jMenuSet.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuSet.setText("Set");
    jMenuSet.setFont(new java.awt.Font("Tahoma", 1, 11));

    jMenuItemSetNewParameters.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemSetNewParameters.setText("New parameters ...");
    jMenuItemSetNewParameters.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemSetNewParametersActionPerformed(evt);
      }
    });
    jMenuSet.add(jMenuItemSetNewParameters);

    jMenuItemLoadPresetFile.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemLoadPresetFile.setText("Load preset file ...");
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
    jMenuComputation.setFont(new java.awt.Font("Tahoma", 1, 11));

    jMenuItemCompute.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemCompute.setText("Compute");
    jMenuItemCompute.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemComputeActionPerformed(evt);
      }
    });
    jMenuComputation.add(jMenuItemCompute);

    jMenuItemReset.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemReset.setText("Reset");
    jMenuItemReset.setToolTipText("Stops the animation and clears the screen.");
    jMenuItemReset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemResetActionPerformed(evt);
      }
    });
    jMenuComputation.add(jMenuItemReset);

    jMenuBar.add(jMenuComputation);

    jMenuAnimation.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuAnimation.setText("Animation");
    jMenuAnimation.setFont(new java.awt.Font("Tahoma", 1, 11));

    jMenuItemStartAnimation.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemStartAnimation.setText("Start animation");
    jMenuItemStartAnimation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemStartAnimationActionPerformed(evt);
      }
    });
    jMenuAnimation.add(jMenuItemStartAnimation);

    jMenuItemStopAnimation.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemStopAnimation.setText("Stop animation");
    jMenuItemStopAnimation.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jMenuItemStopAnimationActionPerformed(evt);
      }
    });
    jMenuAnimation.add(jMenuItemStopAnimation);

    jMenuBar.add(jMenuAnimation);

    jMenuEdit.getPopupMenu().setLightWeightPopupEnabled(false);
    jMenuEdit.setText("Edit");
    jMenuEdit.setFont(new java.awt.Font("Tahoma", 1, 11));

    jMenuItemMeasureMode.setFont(new java.awt.Font("Tahoma", 1, 11));
    jMenuItemMeasureMode.setText("Measure mode");
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

  MeasureMode.setIsDisplayXYcoordsEnabled(false); // turn measure mode off.

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
  AnimationStop animationStopDialog;

  if ((th != null) && (th.isAlive())) { // if animation is still running
    // ask the user if he wants to stop it and start taking measurements.
    animationStopDialog = new UI.AnimationStop(this, true);
    animationStopDialog.setVisible(true);
  }
  else { // animation is already stopped -> turn measure mode on.

      if ( (th != null)&&(!th.isAlive()) ){ // if thread is stopped already

        // decide whether to turn `MeasureMode' on or off:
        if (modi.MeasureMode.getIsDisplayXYCoordsEnabled()){
          modi.MeasureMode.setIsDisplayXYcoordsEnabled(false); // turn off
          Graphics g2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
          g2d.drawImage(bi, 0,0,this);
        }
        else { //measure mode is off -> turn on.
          modi.MeasureMode.setIsDisplayXYcoordsEnabled(true);
        }
      }   //
  } // end if
  
//  TODO: BE B check if the reported flickering still does exist.
//  Remark: rolled back to the version before, as measuring during animation
//          needs some non-trivial changes; considered the time remaining, this
//          is postponed. btw further alphatester feedback would be valuable.
//  TODO: entry above...

//  if (chooseMeasureMode == false){
//    modi.MeasureMode.setIsDisplayXYcoordsEnabled(true);
//    chooseMeasureMode = true;
//  }
//  else{
//    modi.MeasureMode.setIsDisplayXYcoordsEnabled(false);
//    chooseMeasureMode = false;
//  }
  
}//GEN-LAST:event_jMenuItemMeasureModeActionPerformed
// end of `jMenuItemMeasureModeActionPerformed()'

// use: event handler for the movement of the mouse. this is needed to
//      provide the data for user-taken measurements.
private void jPnlDrawingPlaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPnlDrawingPlaneMouseMoved
  if (MeasureMode.getIsDisplayXYCoordsEnabled()) {
    Graphics2D g2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
    g2d.drawImage(bi, null, 0, 0);
    System.out.println("X: " + evt.getX() + " Y: " + evt.getY());
    xScreen = evt.getX();
    yScreen = evt.getY();
    
    if (ScreenUtilities.scalingFactor != 0) {
      System.out.println("X: " +
              MeasureMode.screenCoordsToReality(0, evt.getX()) +
              "Y: " + MeasureMode.screenCoordsToReality(1, evt.getY()));

      // find the quadrant of the drawing plane in which the mouse cursor
      // currently resides.

      if (xScreen <= 509 && yScreen <= 354){
         labelPositionX = 0;
         labelPositionY = 25;
      }
      if (xScreen <= 509 && yScreen > 354){
         labelPositionX = 0;
         labelPositionY = -30;
      }
      if (xScreen > 509 && yScreen <= 354){
         labelPositionX = -150;
         labelPositionY = 25;
      }
      if (xScreen > 509 && yScreen > 354){
         labelPositionX = -150;
         labelPositionY = -30;
      }

      // display the position (real physical values) where the mousecursor
      // points to on the screen.
      drawMeasureModeData(g2d, xScreen, yScreen,
                          labelPositionX, labelPositionY);
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
  modi.MeasureMode.setIsDisplayXYcoordsEnabled(false);
  EulerIntegration.positions.clear();
  ScreenUtilities.xs.clear();
  ScreenUtilities.ys.clear();
  TrajectoryUI.setCounterToZero();
  try {
    for(int j=0; j<3; j++){
        System.gc(); // free memory
        Thread.sleep(200);
    }
  } catch (Exception ignore){
    System.out.println("jMenuItemResetActionPerformed: " + ignore);
  }
    System.gc();
}//GEN-LAST:event_jMenuItemResetActionPerformed

private void jMenuItemStopAnimationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemStopAnimationActionPerformed

  TrajectoryUI.th.interrupt();
  TrajectoryUI.isAnimationRunning = false;
  System.gc();
  
}//GEN-LAST:event_jMenuItemStopAnimationActionPerformed

private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseActionPerformed
  System.exit(0); // kill programm nastily.
}//GEN-LAST:event_jMenuItemCloseActionPerformed


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
  private javax.swing.JMenuItem jMenuItemStopAnimation;
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

            if (i >= ScreenUtilities.xs.size()){
                 TrajectoryUI.th.interrupt();
                 TrajectoryUI.isAnimationRunning = false;
                 System.gc();
                 i = 0;
            }

            Thread.sleep(TrajectoryUI.animationSpeed);

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


    private void drawMeasureModeData(Graphics g, int xScreen, int yScreen,
            int labelPositionX, int labelPositionY){
        g.drawString(" X: " + 
                String.valueOf(UI.Analysis.computeValueFormat(MeasureMode.screenCoordsToReality(0, xScreen)).format(MeasureMode.screenCoordsToReality(0, xScreen)).replace(',','.'))
                       + " m", xScreen + labelPositionX, yScreen + labelPositionY);
        g.drawString(" Y: " + String.valueOf(UI.Analysis.computeValueFormat(MeasureMode.screenCoordsToReality(1, yScreen)).format(MeasureMode.screenCoordsToReality(1, yScreen)).replace(',','.'))
                       + " m", xScreen + labelPositionX, yScreen + labelPositionY + 15);
    }


    /* BE: it  isn´t  as easy as i thought in the beginning. if we ever want to
     *     take measures  during  an animation we have to reconstruct the whole
     *     image  after  every  drawn  buffered image. if enough users vote for
     *     such a feature we can implement it now.
     *     but as the  measure mode has been thought to become quite complex in
     *     in the future i vote for:
     *        (1) coordinatesystem
     *        (2) complex measuremode.
     *
     *     we can build upon [1] below if we ever intend to realize the feature.
     *
     * TODO: BE D bug:  leading  body disappears when being in measure mode (not
     *            new, but now discovered).
     * /

    /* [1]
           if (th != null){
      g.setColor(Color.BLUE);
      drawMeasureModeData(g, TrajectoryUI.xScreen, TrajectoryUI.yScreen,
                          TrajectoryUI.labelPositionX, TrajectoryUI.labelPositionY);

      }

      Graphics2D tmpG2d = (Graphics2D) jPnlDrawingPlane.getGraphics();
      // draw the body (representing the actual moving body itself).
      tmpG2d.setColor(Color.orange);
      tmpG2d.drawOval(
              currentXscreenCoord,
              currentYscreenCoord,
              5, 5
      );

     *  [/1]
     */

} // end of class `TrajectoryUI'
