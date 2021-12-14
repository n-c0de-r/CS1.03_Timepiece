import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
 * @version 2021.07.22
 */
public class GUI {
	
	private Clock clock;
	private ClockThread clockRunning;
	
	private JLabel hours, dots, minutes;
	private JPanel clockPanel, alarmPanel;
	private JTextField alarmHours, alarmMinutes;
	private JToggleButton alarm;
	
	private String time, hour, minute, alarmTime, alarmHour, alarmMinute;
	
	private boolean running = false;
	
	public GUI () {
		clock = new Clock(LocalDateTime.now().getHour()%12, LocalDateTime.now().getMinute());
		
		// Get the numbers from the clock
		time = clock.getTime();
		hour = time.substring(0, 2);
		minute = time.substring(time.length()-2, time.length());
		
		// Get the alarm values
		alarmTime = clock.alarmTime();
		alarmHour = alarmTime.substring(18, 20);
		alarmMinute = alarmTime.substring(21, 23);
		
		// Make a new frame
		makeFrame();
		
		// Make the clock run
		running = true;
		clockRunning = new ClockThread();
		clockRunning.start();
	}
	
	/**
	 * A class to make the clock run automatically.
	 * Time checks could be refactored to clock class better.
	 * 
	 * @author n-c0de-r
	 * @version 2021.07.22
	 */
	class ClockThread extends Thread {
		
		private int seconds = 0;
		private AudioInputStream aio;
		private Clip clip;
		private DataLine.Info info;
		
		public void run() {
			
			File audio = new File("./alarm.wav");
			
			try {
				// Set up the audio stream
				aio = AudioSystem.getAudioInputStream(audio);
				clip = AudioSystem.getClip();
				AudioFormat format = aio.getFormat();
				 
				info = new DataLine.Info(Clip.class, format);
				
				clip = (Clip) AudioSystem.getLine(info);
				
			} catch (UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        	try {
        		while (running) {
        			// Wait one second
        			Thread.sleep(1000);
        			
        			// Close the audio
        			clip.stop();
        			clip.close();
        			
        			// Set dots in-/visible each second
        			dots.setVisible(!dots.isVisible());
        			
        			seconds++;
        			seconds = seconds % 60;
        			
        			// When seconds roll over
        			if (seconds == 0) {
        				clock.tick();
        				time = clock.getTime();
        				hour = time.substring(0, 2);
        				minute = time.substring(time.length()-2, time.length());
        				hours.setText(hour);
        				minutes.setText(minute);
        			}
        			
        			// Get the alarm time from input fields
        			alarmHour = alarmHours.getText();
        			alarmMinute = alarmMinutes.getText();
        			
        			// Check if the alarm is on
        			if (alarmHour.equals(hour) && alarmMinute.equals(minute)
        				&& seconds == 0 && alarm.isSelected()) {
        				clip.open(aio);
        	            clip.start();
        	        }
        			
        		}
        	} catch (InterruptedException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
		alarm = new JToggleButton("on/\noff");
		alarm.addActionListener(e -> clock.toggleAlarm());
		
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
