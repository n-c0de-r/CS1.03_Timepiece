/**
 * The AlarmClock class stores the alarm time.
 * Based in NumberDisplay class.
 */
public class AlarmClock
{
    private int alarmHour;
    private int alarmMinute;
    private int alarmSecond;
    private boolean alarmStatus;

    /**
     * Constructor for objects of class AlarmClock.
     * Set the time when the alarm should ring.
     */
    public AlarmClock(int hours, int minutes)
    {
        alarmHour = hours;
        alarmMinute = minutes;
        alarmSecond = 0;
        alarmStatus = false;
    }

    /**
     * Turn the alarm on or off.
     */
    public void toggleAlarm(){
        alarmStatus = !alarmStatus;
    }
   
    /**
     * Set the alarm time, 2 numbers for alarmHour and alarmMinute
     */
    public void setTime(int hours, int minutes){
        alarmHour = hours;
        alarmMinute = minutes;
    }
    
    /**
     * Return the alarm clock hours.
     */
    public int getHours(){
        return alarmHour;
    }
        
    /**
     * Return the alarm clock hours.
     */
    public int getMinutes(){
        return alarmMinute;
    }
    
    /**
     * Returns the time when the alarm will ring
     */
    public String getTime(){
        String hrs = "";
        String min = "";
        if(alarmHour < 10) {
            hrs = hrs + "0" + alarmHour;
        }
        else {
            hrs = hrs + alarmHour;
        }
        if(alarmMinute < 10) {
            min = min + "0" + alarmMinute;
        }
        else {
            min = min + alarmMinute;
        }
        return hrs + ":" + min;
    }
        
    /**
     * Returns whether the alarm is on or off
     */
    public boolean getStatusValue(){
        return alarmStatus;
    }
    
    /**
     * Returns whether the alarm is on or off
     */
    public String getStatus(){
        if (alarmStatus){return "The alarm is on.";}
        else{return "The alarm is off.";}
    }
        /**
     * Alarm rings at the set time if Alarm in on
     */
    public void AlarmRinging(int clockHours, int clockMinutes, int clockSeconds){
        if ((clockHours == alarmHour) &&
        (clockMinutes == alarmMinute) &&
        (clockSeconds == alarmSecond) &&
        alarmStatus){
            System.out.println("RING RING, it's " + getTime());
        }
    }
}
