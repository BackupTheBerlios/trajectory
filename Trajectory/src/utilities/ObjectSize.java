/*****************************************************************************
 * Use    : This class is based upon an implementation of Steve Wilson´s book
 *          on performance.
 *          http://java.sun.com/docs/books/performance/1st_edition/html/
 *                 JPPreface.fm.html
 *
 *          The main use is to obtain a good estimation of an objects size in
 *          memory.
 */

package utilities;

import physics.MovingBody;
import physics.Setting;

public class ObjectSize {


  public static void main(String[] args)
  throws InterruptedException {
    System.out.println(
            "============== measuring object size of a movingBody ======");
    for(int j=10; j<=10000; j*=10){
    gc();
    System.out.println("=== " + sizeOfMovingBody( j ));
    gc();
    }
  }


  // Use  :
  public static long sizeOfMovingBody(int numOfInstances){
    long usedMemoryAtStart = -1;
    long usedMemoryAtEnd   = -1;
    MovingBody[] movingBodies = new MovingBody[numOfInstances];

    try {
    System.out.println("#### amount: "+ numOfInstances);
      // one time creation of mbPrimer eliminates costs for caching (one time)
      MovingBody mbPrimer = new MovingBody(new Setting());

      gc();
      usedMemoryAtStart = getUsedHeapMemory();
      System.out.println("used mem at start: " + usedMemoryAtStart);

      // fill the memory; the higher size the more accurate the results
      for (int i=0; i<numOfInstances; i++){
        movingBodies[i] = new MovingBody(new Setting());
      }

      usedMemoryAtEnd = getUsedHeapMemory();

      System.out.println("used mem at end  : " + usedMemoryAtEnd);

    } catch (Exception e){
        System.out.println("WARNING: " + e);
        e.printStackTrace();
    }

    float approxSize = (usedMemoryAtEnd-usedMemoryAtStart)/numOfInstances;
    return Math.round(approxSize);
  }

  public static long getUsedHeapMemory(){
    long totalHeapMemory = -1;
    long freeHeapMemory  = -1;
    System.gc(); // clean up first to obtain a known state of the JVM heap.
    totalHeapMemory = Runtime.getRuntime().totalMemory();
    System.gc();
    freeHeapMemory = Runtime.getRuntime().freeMemory();
    return (totalHeapMemory - freeHeapMemory);
  }

  public static void gc()
  throws InterruptedException {
    System.gc();
    Thread.currentThread().sleep(100);
    System.runFinalization();
    System.gc();
    Thread.currentThread().sleep(100);
    System.runFinalization();
    System.gc();
    Thread.currentThread().sleep(100);
    System.runFinalization();
  }

}
