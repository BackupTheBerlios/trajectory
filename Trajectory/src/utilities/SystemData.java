/*****************************************************************************
 * Use   :  This class keeps all the methods gathering information about the
 *          target system. It could be a good place for debug routines, too,
 *          if these are time-proven.
 * Author:  BE
 * Date  :  2009-03-08
 *
 */

package utilities;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;

import physics.MovingBody;


public class SystemData {

  private static LinkedList<MovingBody> mbList;

    public static void gatherIntelligence(){
        final long BYTES_PER_MB = 1024*1024;
        Runtime rt = Runtime.getRuntime();
        Properties props = new Properties();

        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("+++ Free  JVM mem ~ (MB): " + (rt.freeMemory()/BYTES_PER_MB));
        System.out.println("+++ Total JVM mem ~ (MB): " + (rt.totalMemory()/BYTES_PER_MB));
        System.out.println("+++ Max   JVM mem ~ (MB): " + (rt.maxMemory()/BYTES_PER_MB));
        props = System.getProperties();
        System.out.println(props.toString());
        System.out.println("... testing available mem ...");

    }
    public static void cleanUp(){
        System.runFinalization();
        System.gc();
    }

    public static void testCleanUp(){
        for (int i=0; i<2; i++){
            gatherIntelligence();
            cleanUp();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
