import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

/**
 * A GUI for the Clock.
 * 
 * @author n-c0de-r
 * @version 21.07.22
 */
public class GUI {
	
	Clock clock;
	JLabel hours, dots, minutes;
	JPanel clockPanel, alarmPanel;
	JTextField alarmHours, alarmMinutes;
	JToggleButton alarm;
	String time, hour, minute, alarmTime, alarmHour, alarmMinute;
	
	public GUI () {
		clock = new Clock();
		
		// Get the numbers from the clock
		time = clock.getTime();
		hour = time.substring(0, 2);
		minute = time.substring(time.length()-2, time.length());
		
		// Get the alarm values
		alarmTime = clock.alarmTime();
		alarmHour = alarmTime.substring(18, 20);
		alarmMinute = alarmTime.substring(21, 23);
		
		makeFrame();
	}
	
	/**
	 * Setup a new JFrame with all buttons and displays.
	 */
	private void makeFrame() {
		// Get the current screen's size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Main window frame
		JFrame frame = new JFrame("24h Style Clock");
		
		// Set the size of the frame, to quarter of the screen
		frame.setSize(screenSize.width/4, screenSize.height/4);
		
		// Set the initial position to the center of the screen, with formula:
		// half the screen minus half the frame, that way centers overlap.
		frame.setLocation(screenSize.width/2 - frame.getWidth()/2, screenSize.height/2 - frame.getHeight()/2);
		
		// Make it so that the process terminates the process on close properly.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set Style to a grid
		frame.setLayout(new GridLayout(3, 1));
		
		// Create a clock panel
		clockPanel = new JPanel(new GridLayout(1, 3));
		
		Font Arial = new Font("Arial", Font.PLAIN, frame.getHeight()/3);
		
		// Set labels with initial values.
		hours = new JLabel(hour);
		hours.setHorizontalAlignment(SwingConstants.CENTER);
		hours.setFont(Arial);
		
		dots = new JLabel(":");
		dots.setHorizontalAlignment(SwingConstants.CENTER);
		dots.setFont(Arial);
		
		minutes = new JLabel(minute);
		minutes.setHorizontalAlignment(SwingConstants.CENTER);
		minutes.setFont(Arial);
		
		// Add labels to the clock panel
		clockPanel.add(hours);
		clockPanel.add(dots);
		clockPanel.add(minutes);
		
		// Make new panel for the alarm
		alarmPanel = new JPanel(new GridLayout(1, 5));
		
		// Create and initialize new Textfields for the alarm
		alarmHours = new JTextField(alarmHour);
		alarmMinutes = new JTextField(alarmMinute);
		alarm = new JToggleButton("OFF");
		
		// Add all labels to the alarm panel
		alarmPanel.add(new JLabel("Alarm "));
		alarmPanel.add(new JLabel("Time: "));
		alarmPanel.add(alarmHours);
		alarmPanel.add(alarmMinutes);
		alarmPanel.add(alarm);
		
		// Add both panels to the frame
		frame.add(clockPanel);
		frame.add(new JPanel());
		frame.add(alarmPanel);
		
        // Show the final frame
        frame.setVisible(true);
	}
}
