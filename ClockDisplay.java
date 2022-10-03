
/**
 * The ClockDisplay class implements a digital clock display for a...
 * 1) European-style 24 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 00:00 (midnight) to 23:59 (one minute before 
 * midnight).
 * or
 * 2) US-style 12 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 01:00 (midnight) to 12:59 (one minute before 
 * midnight) and keeps track if it's am or pm
 * or
 * 3) A "fake" US-style showing 12 hour clock but is a European clock inside.
 * 
 * The clock display receives "ticks" (via the timeTick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the minutes roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * The following is just an adapted copy of the original project.
 * @author n-c0de-r
 * @version 21.05.03
 * @version 22.10.03
 */
public class ClockDisplay
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private String displayString;    // simulates the actual display
    // New fields
    private String clockType; // European, Fake_US, True_US
    // Enum could be good choice here too
    private boolean isAM; // Assigment 3
    // fields (variables) for the alarm
    private int alarmHour;
    private int alarmMinute;
    private boolean isAlarmAM;
    private boolean alarmOn;
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new clock set at 00:00.
     */
    public ClockDisplay()
    {
        /*hours = new NumberDisplay(24);
        minutes = new NumberDisplay(60);
        updateDisplay();*/
        this(0, 0, "European", true);
    }

    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters.
     * @param hour      Hour to be set, integer value
     * @param minute    Minute to be set, integer value
     * @param type      Sets the clock's type
     */
    public ClockDisplay(int hour, int minute, String type, boolean am)
    {
        clockType = type;
        hours = new NumberDisplay(24);
        if (isTrueUS()) { // Assignment 4
            hours = new NumberDisplay(12);
        }
        
        minutes = new NumberDisplay(60);
        // Reset the time as given
        setTime(hour, minute, am);
    }

    /**
     * This method should get called once every minute - it makes
     * the clock display go one minute forward.
     */
    public void timeTick()
    {
        minutes.increment();
        if(minutes.getValue() == 0) {  // it just rolled over!
            hours.increment();
            // Assignment 3 & 4
            if (hours.getValue () == 0 || hours.getValue () == 12){
                isAM = !isAM;
            }
        }
        updateDisplay();
    }

    /**
     * Set the time of the display to the specified hour and
     * minute.
     * @param hour      Hour to be set, integer value
     * @param minute    Minute to be set, integer value
     * @param setAM     Sets am or pm
     */
    public void setTime(int hour, int minute, boolean setAM)
    {
        int maxHours = 24;
        if (isTrueUS()) { // Assignment 4
            maxHours = 12; // US Inside
        }
        
        // Limit numbers to positive and maximum
        hour = Math.abs(hour) % maxHours;
        minute = Math.abs(minute) % 60;
        
        hours.setValue(hour);
        minutes.setValue(minute);
        isAM = setAM;
        updateDisplay();
    }

    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime()
    {
        return displayString;
    }

    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay()
    {
        // String set to the european hour value by default
        String hoursDisplay = hours.getDisplayValue();
        String am_pm = ""; // European has no am or pm
        if (!isEuropean()) {
            // Assignment 3 & 4
            if (isAM){ //am is true
                am_pm = "am";
            } else { //am is false
                am_pm = "pm";
            }
            // Assignment 4
            if (hours.getValue() == 0){
                hoursDisplay = "12"; // US clocks don't show 0h
            }
            // Assignment 3
            if (hours.getValue() > 12) { //all above is 1-12h
                hoursDisplay = "0" + (hours.getValue()-12);
                hoursDisplay = hoursDisplay.substring(1);
            }
        }
        
        displayString = hoursDisplay + ":"
                      + minutes.getDisplayValue() + am_pm;
                      
        checkAlarm();
    }
    // Assignment 6
    //Methods for an alarm, copies of clock's methods
    /**
     * Set the alarm hour, minute and state.
     * @param h      Hour to be set, integer value
     * @param m      Minute to be set, integer value
     * @param on     Set to true, if the alarm should ring.
     */
    public void setAlarm(int h, int m, boolean on, boolean am)
    {
        int maxHours = 24;
        if (isTrueUS()) { // Assignment 4
            maxHours = 12; // US Inside
        }
        
        // Limit numbers to positive and maximum
        h = Math.abs(h) % maxHours;
        m = Math.abs(m) % 60;
        
        String hoursDisplay = hours.getDisplayValue();
        String am_pm = ""; // European has no am or pm
        if (!isEuropean()) {
            if (h == 0){
                h = 12; // US clocks don't show 0h
            }
            
            if (hours.getValue() > 12) { //all above is 1-12h
                h = h-12;
            }
        }
        
        alarmHour = h;
        alarmMinute = m;
        isAlarmAM = am;
        alarmOn = on;
        checkAlarm();
    }
    
    /**
     * Toggle the alarm on or off respectively.
     * @return    State of the alarm: on or off.
     */
    public boolean toggleAlarm()
    {
        alarmOn = !alarmOn;
        return alarmOn;
    }

    /**
     * Return the current time of the alarm in the format HH:MM.
     * @return display   String of text with set alarm time.
     */
    public String alarmTime()
    {
        String h = ""; // Temporary storage String for hours.
        String m = ""; // Temporary storage String for minutes.
        String a = ""; // Temporary storage String for state.
        String o = ""; // Temporary storage String for status.
        
        h = "" + alarmHour;
        m = "" + alarmMinute;
        o = "on.";
        
        if (!isEuropean()) {
            if (isAlarmAM){ //am is true
                a = "am";
            } else { //am is false
                a = "pm";
            }
        }
        if (alarmHour < 10) { //Add leading 0 if time is not 2 digits
            h = "0" + alarmHour;
        }
        if (alarmMinute < 10) {
            m = "0" + alarmMinute;
        }
        //If it's true, it's set to be on, otherwise off.
        if (!alarmOn) { 
            o = "off.";
        }
        return "Alarm time: " + h + ":" + m + "and is " + o;
    }
    // Private helper methods
    /**
     * Checks the alarm time and rings.
     */
    private void checkAlarm()
    {
        if (alarmHour == hours.getValue() &&
            alarmMinute == minutes.getValue() && alarmOn) {
            System.out.println("Riiiiing!!!");
        }
    }
    
    private boolean isEuropean() {
        return clockType.equals("European");
    }
    
    private boolean isTrueUS() {
        return clockType.equals("True_US");
    }
}
