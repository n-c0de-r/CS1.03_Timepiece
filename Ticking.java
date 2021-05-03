
/**
 * Thread to make the clock tick automatically.
 * according to https://docs.oracle.com/en/java/javase/
 *              15/docs/api/java.base/java/lang/Thread.html
 *
 * @authors: Maximilian, Nermin
 * @version 1.0
 */
public class Ticking extends Thread
{
    private NumberDisplay seconds;
     /**
     * Constructor for objects of class Ticking
     */
    public Ticking(NumberDisplay secondsDisplay)
    {
        seconds = secondsDisplay;
    }

    /**
     * runs the thread
     */
    public void run(){
        seconds.increment();
    }
    /**
     * pauses the thread for 1 second
     */
    public void waitOneSecond(){
        /** After a lot of trial and error, just tried looking up "catch"
         * in the API, so step by step read through and added try.
         * https://docs.oracle.com/en/java/javase/15/docs/
         * api/jdk.compiler/com/sun/source/tree/TryTree.html
         */
        try {Thread.sleep(1000);} //time in milliseconds
        /**according to https://docs.oracle.com/en/java/javase/15/docs/
        *api/jdk.compiler/com/sun/source/tree/CatchTree.html
        *this is needed. Still don't get how to use right.*/
        catch (InterruptedException error){};
    }
}
