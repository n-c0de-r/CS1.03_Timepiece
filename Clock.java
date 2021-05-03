/**
 * The Clock class implements a digital clock display for a
 * European-style 24 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 00:00 (midnight) to 23:59 (one minute before 
 * midnight).
 * 
 * The clock receives "ticks" (via the tick method) every minute
 * and reacts by incrementing the display. This is done  the usual clock
 * fashion: the hour increments when the mins roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Edited the original project.
 * @author n-c0de-r
 * @version 21.05.03
 */
public class Clock
{
    private Number hrs;
    private Number mins;
    private String display;    // simulates the actual display
    // fields (variables) for the alarm
    private int aHrs;
    private int aMins;
    private boolean alarmOn; // without value, it's off at start.
    
    /**
     * Constructor for Clock objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public Clock()
    {
        /*hrs = new Number(24);
        mins = new Number(60);
        update();*/
        this(0,0);
    }

    /**
     * Constructor for Clock objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     */
    public Clock(int h, int minute)
    {
        hrs = new Number(24);
        mins = new Number(60);
        setTime(h, minute);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void tick()
    {
        mins.increment();
        if(mins.value() == 0) {  // it just rolled over!
            hrs.increment();
        }
        update();
    }

    /**
     * Set the time of the display to the specified hour and minute.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param isAM   Set to true, if it's in the morning, false for afternoon
     */
    public void setTime(int h, int m)
    {
        //Setup a "guard" to prevent invalid number inputs
        if (h > 12 || h < 0 || m >= 60 || m < 0) {
            //invalid time, 24h not allowed, 60min not allowed, skip!
            return;
        }
        hrs.set(h);
        mins.set(m);
        update();
    }
    

    /**
     * Return the current time of this display in the format HH:MM.
     * @return display   String of text containing the current time
     */
    public String getTime()
    {
        return display;
    }
    
    /**
     * Update the internal string that represents the display.
     */
    private void update()
    {
        display = hrs.getDisplayValue() + ":" + 
                  mins.getDisplayValue();
        checkAlarm();
    }
    
    //Methods for an alarm, copies of clock's methods
    /**
     * Set the alarm of the clock to the specified hour and minute.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param on     Set to true, if the alarm should ring.
     */
    public void setAlarm(int h, int m, boolean on)
    {
        aHrs = h;
        aMins = m;
        alarmOn = on;
        update();
    }
    
    /**
     * Toggle the alarm on or off respectively.
     * @return    String of text saying if the alarm is now on or off.
     */
    public String toggleAlarm()
    {
        alarmOn = !alarmOn;
        if (alarmOn) {
            return "Alarm is now on.";
        } else {
            return "Alarm is now off.";
        }
    }

    /**
     * Return the current time of thee alarm in the format HH:MM.
     * @return display   String of text containing the set alarm time.
     */
    public String alarmTime()
    {
        String h = ""; // Temporary storage String for hours.
        String m = ""; // Temporary storage String for minutes.
        String o = ""; // Temporary storage String for status.
        if (aHrs < 10) { //Add a leading 0 if the time is below 2 digits
            h = "0" + aHrs;
        } else { // otherwise convert the number to a String.
            h = "" + aHrs;
        }
        if (aMins < 10) { //Add a leading 0 if the time is below 2 digits
            m = "0" + aMins;
        } else { // otherwise convert the number to a String.
            m = "" + aMins;
        }
        if (alarmOn) { // Add a String reflecting the alarm status.
            o = "on.";
        } else { //If it's true, it's set to be on, otherwise off.
            o = "off.";
        }
        return "Alarm is set for: " + h + ":" + m + "and is " + o;
    }
    
    /**
     * checks if the alarm time is the same as the current and rings.
     */
    private void checkAlarm()
    {
        if (aHrs == hrs.value() && aMins == mins.value() && alarmOn) {
            System.out.println("Riiiiing!!!");
        }
    }
}

//edited by n-c0de-r. Same nick on GitHub.