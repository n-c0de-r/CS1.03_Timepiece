/**
 * The USOutside class implements a digital clock display for a
 * US-style 12 hour clock. Internally, the clock has the range
 * range of the clock is 00:00 (midnight) to 23:59 (one minute before 
 * midnight) - European-style. Just the display method differs!
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
public class USOutside
{
    private Number hrs;
    private Number mins;
    private String display;   // simulates the actual display
    
    /**
     * Constructor for USOutside objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public USOutside()
    {
        /*hrs = new Number(24);
        mins = new Number(60);
        update();*/
        this(0,0, true);
    }

    /**
     * Constructor for USOutside objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param isAM   Set to true, if it's in the morning, false for afternoon
     */
    public USOutside(int h, int m, boolean isAM)
    {
        hrs = new Number(24);
        mins = new Number(60);
        setTime(h, m, isAM);
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
    public void setTime(int h, int m, boolean isAM)
    {
        //Setup a "guard" to prevent invalid number inputs
        if (h >= 24 || h < 0 || m >= 60 || m < 0) {
            //invalid time, 24h not allowed, 60min not allowed, skip!
            return;
        }
        
        if (isAM){
            hrs.set(h); //If it's am, keep the time
        } else {
            hrs.set(h+12); //if it's afternoon, increase the time
        }
        mins.set(m);
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
        
        if (hrs.value() < 12){
            am_pm = "am";
        } else {
            am_pm = "pm";
        }
        // String to temporarily store the altered hour value
        String hoursDisplay = hrs.getDisplayValue();
        if (hrs.value() == 0){ //US clocks show 0 hour as 12!
            hoursDisplay = "12";
        }
        if (hrs.value() > 12) { //everything above 12 is 1-12 o'clock
            hoursDisplay = "" + (hrs.value()-12);
        }
        display = hoursDisplay + ":" +  //Put all strings together
                  mins.getDisplayValue() + am_pm;
    }
}

//edited by n-c0de-r. Same nick on GitHub.