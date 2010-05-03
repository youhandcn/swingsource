package yearcalendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import monthcalendar.MonthCalendarImpl;

/**
 * 
 */
public class ClockTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

	private JButton printButton = null;

	/**
	 * @param owner
	 */
	public ClockTest() {
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 780);
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
			panelTemp.add(getYearPanel());
			jContentPane.add(panelTemp, BorderLayout.CENTER);
			JPanel panel = new JPanel(new BorderLayout());
			printButton = new JButton("print");

			panel.add(printButton, BorderLayout.CENTER);
			jContentPane.add(panel, BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	// /////////////////////
	// Year calendar box //
	// /////////////////////
	private JPanel yearPanel = null;
	private YearTimer yearCalendar = null;

	private JPanel getYearPanel() {
		if (yearPanel == null) {
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 1;
			gridBagConstraints16.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints16.gridy = 1;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 0;
			gridBagConstraints13.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints13.gridy = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints9.gridy = 1;
			gridBagConstraints9.gridx = 2;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.insets = new java.awt.Insets(5, 5, 5, 5);
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.gridwidth = 3;
			gridBagConstraints8.gridx = 0;
			yearPanel = new JPanel();
			yearPanel.setLayout(new GridBagLayout());
			yearPanel.add(getYearCalendar(), gridBagConstraints8);
		}
		return yearPanel;
	}

	/**
	 * This method shows how to create a year calendar.
	 * 
	 * @see com.jungleford.smartcalendar.YearCalendar
	 */
	private YearTimer getYearCalendar() {
		if (yearCalendar == null) {
			// yearCalendar = new YearTimer();// Use MonthCalendarImpl type as
			// default month calendar style.
			yearCalendar = new YearTimer(new YearCalendar(
					MonthCalendarImpl.class));
			yearCalendar.setGridSize(200, 150);
		}
		return yearCalendar;
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
