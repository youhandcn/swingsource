package clock;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.JComponent;

/**
 * To represent all modules which a analog-type clock consists of.
 */
public abstract class Parts extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Coloring scheme for the parts.
	 */
	protected BasicColor colors;

	/**
	 * Size of this parts.
	 */
	protected Dimension size;

	/**
	 * Clock face.
	 */
	protected Shape dial;

	/**
	 * Default constructor.
	 */
	protected Parts() {
		size = new Dimension();
	}

	/**
	 * No special character currently, but may be overridden in the future.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	/**
	 * Changes positions of hour hand, minute hand, second hand and decisecond
	 * hand based on current time.
	 * 
	 * @param hour
	 *            current hour (in 24-hour format)
	 * @param minute
	 *            current minute
	 * @param second
	 *            current second
	 * @param millisecond
	 *            current millisecond
	 */
	public abstract void doTransform(int hour, int minute, int second,
			int millisecond);

	/**
	 * Gets the clock face.
	 * 
	 * @return the clock face.
	 */
	public Shape getDial() {
		return dial;
	}

	/**
	 * Sets the coloring scheme.
	 * 
	 * @param colors
	 */
	public void setColors(BasicColor colors) {
		this.colors = colors;
	}

	/**
	 * @return size of this parts.
	 */
	public Dimension getSize() {
		return size;
	}
}
