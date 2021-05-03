
/**
 * Creates a separator that is displayed
 * between the two NumberDisplays.
 *
 * @authors Maximilian, Nermin
 * @version 1.0
 */
public class Separator
{
    // Create a new separator. Default is ":"
    private boolean visible;
    private String separator;

    /**
     * Constructor for objects of class Separator
     */
    public Separator()
    {
        // initialise instance variables
        visible = true;
        separator = ":";
    }

    /**
     * return the current separator symbol as a String.
     */
    public String getSymbol()
    {
        return separator;
    }
    /**
     * changes the current separator symbol to
     * be visible or invisible.
     */
    public String changeVisibility()
    {
        if (visible){ //if the separator is curretly visible
            visible = !visible;
            return separator = " "; //make it invisible
        } else { //if the separator is curretly invisible
            visible = !visible;
            return separator = ":";//make it visible
        }
    }
}
