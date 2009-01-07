import javax.swing.*;
import java.awt.*;

public class AnimTestThreaded
extends Frame
implements Runnable
{
  int cnt = 0;

  public static void main(String[] args)
  {
    AnimTestThreaded wnd = new AnimTestThreaded();
    wnd.setSize(250,150);
    wnd.setVisible(true);
    wnd.startAnimation();
  }

  public AnimTestThreaded()
  {
    super("Animations-Threads");
    setBackground(Color.lightGray);
    //addWindowListener(new WindowClosingAdapter(true));
  }

  public void startAnimation()
  {
    Thread th = new Thread(this);
    th.start();
  }

  public void run()
  {
    while (true) {
      repaint();
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        //nichts
      }
    }
  }

  public void paint(Graphics g)
  {
    ++cnt;
    g.drawString("Counter = "+cnt,10,50);
  }
}
