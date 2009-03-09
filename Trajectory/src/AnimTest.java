 /* AnimTest.java */
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;

public class AnimTest
extends Frame
{
  int cnt = 0;


  public AnimTest()
  {
    super("Animierter Zähler");
    setBackground(Color.lightGray);
//    addWindowListener(new WindowClosingAdapter(true));
  }

  public void startAnimation()
  {
    while (true) {
      repaint();
    }
  }

  public void paint(Graphics g)
  {
    ++cnt;
    g.drawString("Counter = "+cnt,10,50);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
    }
  }
}
