package clock;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Timer;

/**
 * To implement a analog-type clock.
 */
public class AnalogClock extends Clock implements ActionListener {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ye old fudge factor to give a little space around the text.
	 */
	protected static final int MARGIN = 6;

	/**
	 * Parts to construct this clock.
	 */
	private Parts parts = null;

	/**
	 * A timer to run in a independent thread.
	 */
	private Timer timer = null;

	/**
	 * Constructor:<br>
	 * Creates an analog-type clock by using given parts.
	 * 
	 * @param parts
	 */
	public AnalogClock(Parts parts) {
		super();
		timer = new Timer(50, this);
		this.parts = parts;
		initialize();
	}

	/**
   * 
   */
	private void initialize() {
		actionPerformed(null);
	}

	/**
	 * @see java.awt.Component#addNotify()
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		timer.start();
	}

	/**
	 * @see java.awt.Component#removeNotify()
	 */
	@Override
	public void removeNotify() {
		timer.stop();
		super.removeNotify();
	}

	/**
	 * Do transformation based on current precise time when display.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Date current = new Date();
		calendar.setTime(current);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);
		parts.doTransform(hour, minute, second, millisecond);

		repaint();
		// Resize this clock in time
		setSize(getPreferredSize());
		current = null;
		
	}

	/**
	 * Draws this clock.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		parts.paintComponent(g);
	}

	/**
	 */
	@Override
	public Dimension getPreferredSize() {
		Dimension size = getSize();

		size.width = parts.getSize().width;
		size.height = parts.getSize().height + MARGIN;
		return size;
	}
}
