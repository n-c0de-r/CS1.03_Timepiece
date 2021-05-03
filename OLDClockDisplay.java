
/**
 * The ClockDisplay class implements a digital clock display for a
 * European-style 24 hour clock. The clock shows hours and minutes. The 
 * range of the clock is 00:00 (midnight) to 23:59 (one minute before 
 * midnight).
 * 
 * The clock display receives "ticks" (via the timeTick method) every minute
 * and reacts by incrementing the display. This is done in the usual clock
 * fashion: the hour increments when the minutes roll over to zero.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class ClockDisplay
{
    private NumberDisplay hours;
    private NumberDisplay minutes;
    private NumberDisplay seconds;
    private Separator separator;
    private AlarmClock alarm;
    private Ticking ticking;
    private String displayString; // simulates the actual display
    private boolean am;
    private boolean running;
    private int displayTypeNr;
    /**
     * Constructor for ClockDisplay objects. This constructor 
     * creates a new 24h clock set at 00:00.
     */
    public ClockDisplay(){
        this(0,0,0);
    }    
    /**
     * Depending on the input create an internally different clock. 
     * Nr 1 uses the 24h-format internally, but displays the American
     * 12h-standard. Nr 2 works with the 12h-format right away.
     * Any other entry uses the 24h-format inside and outside.
     */
    public ClockDisplay(int choiceNr){
        displayTypeNr = choiceNr;
        if (displayTypeNr == 1){
            hours = new NumberDisplay(24);
        }
        else if (displayTypeNr == 2){
            hours = new NumberDisplay(14); 
            /**must be higher than 12, to allow use of 12 as an hour,
            must be higher than 13, so 13 gets converted to 1*/
        }
        else {
            hours = new NumberDisplay(24);
        }
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);
        separator = new Separator();
        ticking = new Ticking(seconds);
        alarm = new AlarmClock(0,0);
        updateDisplay();
    }
    /**
     * Constructor for ClockDisplay objects. This constructor
     * creates a new clock set at the time specified by the 
     * parameters. Uses the international 24h-standard by default.
     */
    public ClockDisplay(int hour, int minute, int second){
        hours = new NumberDisplay(24);
        minutes = new NumberDisplay(60);
        seconds = new NumberDisplay(60);
        separator = new Separator();
        ticking = new Ticking(seconds);
        alarm = new AlarmClock(0,0);
        setTime(hour, minute);
    }
    /**
     * This method should get called at least once to start the clock.
     */
    public void startClock(){
        running = true;
        while(running){
        ticking.run();
        if(seconds.getValue() == 0) {  // it just rolled over!
            minutes.increment();
            if(minutes.getValue() == 0) {  // it just rolled over!
                hours.increment();
            }
        }
        ticking.waitOneSecond();
        separator.changeVisibility();
        updateDisplay();}
    }
    /**
     * Checks if the clock has passed to 12am or 12pm.
     * Only needed in the "true" only 12h-version.
     */
    private void amCheck(){
       if(hours.getValue() == 12 && minutes.getValue() == 0 && seconds.getValue() == 0){ 
            am = !am;
       }
    }
    /**
     * This is needed as there is no hour 0
     * and after 12 it needs to be set to 1
     */
    private void specialRollover(){
        if (hours.getValue() == 13){
            hours.setValue(1);
        }
    }  
    /**
     * Set the time of the display to
     * the specified hour and minute, resets seconds.
     */
    public void setTime(int hour, int minute){
        hours.setValue(hour);
        minutes.setValue(minute);
        seconds.setValue(0);
        updateDisplay();
    }
    /**
     * Set the time of the alarm to
     * the specified hour and minute.
     */
    public void setAlarm(int hour, int minute){
        alarm.setTime(hour, minute);
        updateDisplay();
    }
    /**
     * Turn the alarm on or off.
     */
    public void changeAlarmStatus(){
        alarm.toggleAlarm();
        updateDisplay();
    }
    /**
     * Return the current time of this display in the format HH:MM.
     */
    public String getTime(){
        return displayString;
    }
    /**
     * Get the time of the set alarm.
     */
    public String getAlarmTime(){
        return alarm.getTime();
    }
        /**
     * Get if the alarm is on or off.
     */
    public String getAlarmStatus(){
        return alarm.getStatus();
    }
    /**
     * Update the internal string that represents the display.
     */
    private void updateDisplay(){
        if (displayTypeNr == 1){AmericanDisplay1();}
        else if (displayTypeNr == 2){AmericanDisplay2();}
        
        else{
            displayString = hours.getDisplayValue() + separator.getSymbol() + 
                            minutes.getDisplayValue();
        }
        //if the alarm is reached, it will "ring" for a minute
        System.out.println(displayString);
        alarm.AlarmRinging(hours.getValue(), minutes.getValue(), seconds.getValue());
    }
    /** 
     * This only changes the way the time is shown on the display
     * the values are stored in 24h-format.
     */
    private void AmericanDisplay1(){
        if (hours.getValue() == 0){
            displayString = "12" + ":" + minutes.getDisplayValue() +"am";
        }
        else if((hours.getValue() > 0) && (hours.getValue() < 12)) {
            displayString = hours.getDisplayValue() + separator.getSymbol() + 
                        minutes.getDisplayValue() + "am";
        }else if(hours.getValue() == 12) {
            displayString = hours.getDisplayValue() + separator.getSymbol() + 
                        minutes.getDisplayValue() + "pm";
        }else {
            displayString = (hours.getValue() - 12) + separator.getSymbol() + 
                        minutes.getDisplayValue() + "pm";
        }
    }
    /**
     * changes the "normal" time to the "American" time (am, pm, ...)
     *  including a special rollover for shift from 12 to 1
     *  2x12h a day instead of 24h a day
     */
    private void AmericanDisplay2(){
        if (hours.getValue() == 0){
            hours.setValue(12);
            /**clock is set to 0 in the beginning,
            this needs a one-time correction here*/
        }
        amCheck();
        specialRollover();
        if (am == true){
            displayString = hours.getDisplayValue() + separator.getSymbol() + 
                            minutes.getDisplayValue() + "am";
        }
        else {
            displayString = hours.getDisplayValue() + separator.getSymbol() + 
                            minutes.getDisplayValue() + "pm";
        }
    }
}
