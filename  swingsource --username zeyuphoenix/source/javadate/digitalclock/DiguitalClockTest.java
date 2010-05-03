package digitalclock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Some sample codes for usage of SmartCalender beans.
 */
public class DiguitalClockTest extends JFrame {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	// //////////////////////////
	// Digital-type clock box //
	// //////////////////////////
	private DigitalClock digitalClock = null;

	/**
	 * This method shows how to create a digital-type clock.
	 * 
	 * @see study.DigitalClock
	 */
	private DigitalClock getDigitalClock() {
		if (digitalClock == null) {
			// To create a digital-type clock with dark
			// red foreground and black background.
			digitalClock = new DigitalClock(":", "'", new Font("Comic Sans MS",
					Font.BOLD, 20), Color.GREEN.darker(), Color.BLACK
					.brighter(), Locale.ENGLISH);
		}
		return digitalClock;
	}

	private JPanel panel = null;

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.add(getDigitalClock(), BorderLayout.CENTER);
		}
		return panel;
	}

	/**
	 * Default constructor
	 */
	public DiguitalClockTest() {
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle(" Calendar Test Panel");
		this.setLocation(100, 200);
		this.pack();
		this.setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	public static void main(String[] args) throws Exception {
		// show the frame.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new DiguitalClockTest();
			}
		});
	}
}
