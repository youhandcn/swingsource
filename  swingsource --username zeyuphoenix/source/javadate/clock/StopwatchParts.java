package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * To define all modules which a analog-type stopwatch consists of.
 */
public abstract class StopwatchParts extends RotateParts {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Millisecond hand.
	 */
	protected Shape millisecondHand;

	/**
	 * Millisecond hand behavior controller.
	 */
	protected AffineTransform millisecondTransform;

	/**
	 * Moves all parts, to leave some margin.
	 */
	protected transient AffineTransform trans;

	/**
	 * Default constructor.
	 */
	protected StopwatchParts() {
	}

	/**
	 * Constructor: Joins every parts in a entire analog-type clock.
	 * 
	 * @param dial
	 *            the clock face.
	 * @param millisecondHand
	 *            the millisecond hand.
	 */
	protected StopwatchParts(Shape dial, Shape millisecondHand)
			throws Exception {
		if (dial == null || millisecondHand == null) {
			throw new Exception(
					"Illegal parts: Every parts MUST be initialized.");
		}

		this.dial = dial;
		this.millisecondHand = millisecondHand;
	}

	/**
	 * Initializes hand transformation.
	 */
	protected void initTransform() {
		millisecondTransform = getTransform();
	}

	/**
	 * Default algorithm for hands's action trace.
	 */
	@Override
	public void doTransform(int hour, int minute, int second, int millisecond) {
		if (millisecondTransform != null) {
			setToRotation(millisecondTransform, millisecond / 100.0, 10);
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (trans != null && millisecondHand != null
				&& millisecondTransform != null) {
			if (dial != null) {
				g2.setPaint(Color.black);
				g2.draw(trans.createTransformedShape(dial));
			}

			// Draw millisecond hand
			g2.setPaint(Color.red);
			g2.fill(trans.createTransformedShape(millisecondTransform
					.createTransformedShape(millisecondHand)));
		}
	}

	/**
	 * Changes the shape of millisecond hand.
	 */
	public void setMillisecondHand(Shape millisecondHand) throws Exception {
		if (millisecondHand == null) {
			throw new Exception("Millisecond hand MUST not be null.");
		}
		this.millisecondHand = millisecondHand;
	}

	/**
	 * Changes the rotation algorithm of millisecond hand.
	 */
	public void setMillisecondTransform(AffineTransform millisecondTransform)
			throws Exception {
		if (millisecondTransform == null) {
			throw new Exception("Millisecond hand MUST not be null.");
		}
		this.millisecondTransform = millisecondTransform;
	}
}
