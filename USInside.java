/**
 * The USInside class implements a digital clock display for a
 * US-style 12 hour clock. The clock shows hourrs and minutess. The 
 * range of the clock is 01:00 (midnight) to 12:59 (one minute before 
 * midnight) and keeps track if it's am or pm
 * 
 * The clock receives "ticks" (via the tick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the mins roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * The following is just an adapted copy of the original project.
 * @author n-c0de-r
 * @version 21.05.03
 */
public class USInside
{
    private Number hrs;
    private Number mins;
    private boolean am;
    private String display;    // simulates the actual display
    
    /**
     * Constructor for USInside objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public USInside()
    {
        /*hrs = new Number(24);
        mins = new Number(60);
        update();*/
        this(0,0, true);
    }

    /**
     * Constructor for USInside objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param isAM   Set to true, if it's in the morning, false for afternoon
     */
    public USInside(int h, int minute, boolean isAM)
    {
        hrs = new Number(12);
        mins = new Number(60);
        setTime(h, minute, isAM);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void tick()
    {
        mins.increment();
        if(mins.value() == 0) {  // it just rolled over!
            hrs.increment(); // if hrs rolled to 0, then switch am/pm
            if (hrs.value () == 0){
                am = !am;
            }
        }
        update();
    }

    /**
     * Set the time of the display to the specified hour and minute.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param isAM   Set to true, if it's in the morning, false for afternoon
     */
    public void setTime(int h, int m, boolean isAM)
    {
        //Setup a "guard" to prevent invalid number inputs
        if (h > 12 || h < 0 || m >= 60 || m < 0) {
            //invalid time, 24h not allowed, 60min not allowed, skip!
            return;
        }
        hrs.set(h);
        mins.set(m);
        am = isAM;
        update();
    }

    /**
     * Return the current time of this display in the format HH:MM am/pm.
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
        // am and pm string, switches after 12 o'clock
        String am_pm = "";
        
        if (am){ //am is true
            am_pm = "am";
        } else { //am is false
            am_pm = "pm";
        }
        // String to temporarily store the altered hour value
        String hoursDisplay = hrs.getDisplayValue();
        if (hrs.value() == 0){ //US clocks show 0 hour as 12!
            hoursDisplay = "12";
        }
        display = hoursDisplay + ":" + 
                  mins.getDisplayValue() + am_pm;
    }
}

//edited by n-c0de-r. Same nick on GitHub.