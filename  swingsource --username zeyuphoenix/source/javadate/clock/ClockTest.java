package clock;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import timespinner.DateSpinner;

public class ClockTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	/**
	 * @param owner
	 */
	public ClockTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 300);
		this.setLocation(500, 450);
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			JPanel panelTemp = new JPanel();
			panelTemp.add(getColorfulClock());
			jContentPane.add(panelTemp, BorderLayout.CENTER);

			DateSpinner time = new DateSpinner();
			time.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
			time.setPreferredSize(new Dimension(100, 30));
			jContentPane.add(time, BorderLayout.SOUTH);

		}
		return jContentPane;
	}

	private AnalogClock colorfulClock = null;

	/**
	 * This method shows how to create a user defined analog-type clock, using
	 * <code>MyParts</code>.
	 */
	private AnalogClock getColorfulClock() {
		if (colorfulClock == null) {
			try {
				colorfulClock = new AnalogClock(new MyPartsWithStopwatch());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return colorfulClock;
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array
	 */
	public static void main(final String[] args) {
		// show the frame.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				(new ClockTest()).setVisible(true);
			}
		});
	}
}
