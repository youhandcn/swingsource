package simpleclock;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;

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
		this.setSize(400, 300);
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
			panelTemp.add(getBox1());
			jContentPane.add(panelTemp, BorderLayout.CENTER);
			JPanel panel = new JPanel(new BorderLayout());
			printButton = new JButton("print");

			panel.add(printButton, BorderLayout.CENTER);
			jContentPane.add(panel, BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	private BorderPanel box1 = null;
	private AnalogClock analogClock = null;
	private JButton stopwatchBtn2 = null;
	private JButton alarmBtn2 = null;
	private JButton msgBtn2 = null;

	private BorderPanel getBox1() {
		if (box1 == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints12.gridy = 2;
			gridBagConstraints12.gridx = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 0;
			gridBagConstraints15.gridwidth = 2;
			gridBagConstraints15.anchor = java.awt.GridBagConstraints.NORTH;
			gridBagConstraints15.weighty = 1.0;
			gridBagConstraints15.gridy = 0;
			box1 = new BorderPanel();
			box1.setLayout(new GridBagLayout());
			box1.setTitle("Classical");
			box1.add(getAnalogClock(), gridBagConstraints15);
			box1.add(getStopwatchBtn2(), gridBagConstraints3);
			box1.add(getAlarmBtn2(), gridBagConstraints11);
			box1.add(getMsgBtn2(), gridBagConstraints12);
		}
		return box1;
	}

	/**
	 * This method shows how to create a analog-type clock.
	 * 
	 * @see com.jungleford.smartcalendar.AnalogClock
	 */
	private AnalogClock getAnalogClock() {
		if (analogClock == null) {
			analogClock = new AnalogClock();
		}
		return analogClock;
	}

	/**
	 * Creates a button to trigger stopwatch for analog-type clock.
	 * 
	 * @see com.jungleford.smartcalendar.AnalogClock#setParts(com.jungleford.smartcalendar.clock.analog.Parts)
	 */
	private JButton getStopwatchBtn2() {
		if (stopwatchBtn2 == null) {
			stopwatchBtn2 = new JButton();
			stopwatchBtn2.setText("Stopwatch ON!");
			stopwatchBtn2
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (analogClock.isStopwatch()) {
								analogClock.setParts(new SimpleParts());
								stopwatchBtn2.setText("Stopwatch ON!");
							} else {
								analogClock.setParts(new Chromoneter());
								stopwatchBtn2.setText("Stopwatch OFF!");
							}
						}
					});
		}
		return stopwatchBtn2;
	}

	private transient DateFormat df = new SimpleDateFormat("HH:mm:ss");

	/**
	 * Creates a button to trigger alarm for analog-type clock.
	 * 
	 * @see study.Clock#setAlarm(java.util.Date, int)
	 */
	private JButton getAlarmBtn2() {
		if (alarmBtn2 == null) {
			alarmBtn2 = new JButton();
			alarmBtn2.setText("Set Alarm...");
			alarmBtn2.setToolTipText("No special things will happen...");
			ToolTipManager.sharedInstance().setInitialDelay(0);// Show tool tips
			// immediately.
			alarmBtn2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (analogClock.getAlarm() == null) {
						String choice = JOptionPane.showInputDialog(
								ClockTest.this, "Format: HH:mm:ss",
								"Set your alarm point",
								JOptionPane.INFORMATION_MESSAGE);
						if (choice != null) {
							try {
								analogClock.setAlarm(df.parse(choice), 60);
								alarmBtn2.setText("Stop Alarm!");
							} catch (Exception ex) {
								JOptionPane.showMessageDialog(ClockTest.this,
										"Invalid time format!", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						analogClock.setAlarm(null, 0);
						alarmBtn2.setText("Set Alarm...");
					}
				}
			});
		}
		return alarmBtn2;
	}

	/**
	 * Creates a button to trigger additional message for analog-type clock.
	 * 
	 * @see study.Clock#setMessage(java.lang.Object)
	 */
	private JButton getMsgBtn2() {
		if (msgBtn2 == null) {
			msgBtn2 = new JButton();
			msgBtn2.setText("Set Message...");
			msgBtn2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String choice = JOptionPane.showInputDialog(ClockTest.this,
							"", "Enter your message",
							JOptionPane.INFORMATION_MESSAGE);
					if (choice != null) {
						analogClock.setMessage(choice);
					}
				}
			});
		}
		return msgBtn2;
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
