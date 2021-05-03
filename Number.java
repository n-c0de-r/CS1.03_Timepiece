
/**
 * The Number class represents a digital number display that can hold
 * values from zero to a given limit. The limit can be specified when creating
 * the display. The values range from zero (inclusive) to limit-1. If used,
 * for example, for the seconds on a digital clock, the limit would be 60, 
 * resulting in display values from 0 to 59. When incremented, the display 
 * automatically rolls over to zero when reaching the limit.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 * 
 * Edited the original project.
 * @author n-c0de-r
 * @version 21.05.03
 */
public class Number
{
    private int limit;
    private int value;

    /**
     * Constructor for objects of class Number.
     * Set the limit at which the display rolls over.
     * @param rollOverLimit   The number at which the display resets to 0.
     */
    public Number(int rollOverLimit)
    {
        limit = rollOverLimit;
        value = 0;
    }

    /**
     * Return the current value.
     * @return "value" - The value number of this display as an integer.
     */
    public int value()
    {
        return value;
    }

    /**
     * Return the display value (that is, the current value as a two-digit
     * String. If the value is less than ten, it will be padded with a leading
     * zero).
     * @return "value" - The value number of this display as a String.
     */
    public String getDisplayValue()
    {
        if(value < 10) {
            return "0" + value;
        }
        else {
            return "" + value;
        }
    }

    /**
     * Set the value of the display to the new specified value. If the new
     * value is less than zero or over the limit, do nothing.
     * @param newNr   Integer number to which the display will be set.
     */
    public void set(int newNr)
    {
        if((newNr >= 0) && (newNr < limit)) {
            value = newNr;
        }
    }

    /**
     * Increment the display value by one, rolling over to zero if the
     * limit is reached.
     */
    public void increment()
    {
        value = (value + 1) % limit;
    }
}

//edited by n-c0de-r. Same nick on GitHub.
