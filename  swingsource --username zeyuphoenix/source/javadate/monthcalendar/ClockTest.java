package monthcalendar;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ClockTest extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;

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
		this.setSize(800, 400);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
			panelTemp.add(getMonthPanel());
			jContentPane.add(panelTemp, BorderLayout.CENTER);
		}
		return jContentPane;
	}

	// //////////////////////
	// Month calendar box //
	// //////////////////////
	private JPanel monthPanel = null;
	private MonthCalendar.DateChooser monthCalendar = null;

	private JPanel getMonthPanel() {
		if (monthPanel == null) {

			monthPanel = new JPanel();
			monthPanel.setLayout(new BorderLayout());
			monthPanel.add(getMonthCalendar(), BorderLayout.CENTER);
		}
		return monthPanel;
	}

	/**
	 * This method shows how to create a month calendar.
	 */
	private MonthCalendar.DateChooser getMonthCalendar() {
		if (monthCalendar == null) {
			monthCalendar = new MonthCalendar.DateChooser(
					new MonthCalendarImpl(), true);
		}
		return monthCalendar;
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
