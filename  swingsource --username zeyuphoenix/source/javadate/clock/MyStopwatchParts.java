package clock;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import simpleclock.SimpleParts;

/**
 * To implement a concrete stopwatch.
 */
public class MyStopwatchParts extends StopwatchParts {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 10 ticks.
	 */
	private GeneralPath tick;

	/**
	 * Default constructor: Creates a regular classical circle face and hands.
	 */
	public MyStopwatchParts() {
		radius = 15;
		x = 50 + radius;
		y = 80 + radius;

		initialize();
	}

	/**
	 * Constructor: Draws a classical clock at specific position with specific
	 * value.
	 * 
	 * @param x
	 *            The x coordinate of the upper left corner of the boundary
	 *            square of this stopwatch.
	 * @param y
	 *            The y coordinate of the upper left corner of the boundary
	 *            square of this stopwatch.
	 * @param width
	 *            The width of the boundary square of this stopwatch.
	 */
	public MyStopwatchParts(float x, float y, float width) throws Exception {
		if (x < 0 || y < 0 || width < 0) {
			throw new Exception("Illegal coordinates or width: Minus value.");
		}
		radius = width / 2;
		this.x = x + radius;
		this.y = y + radius;

		initialize();
	}

	/**
	 * Creates stopwatch shape, millisecond-second hand and ticks.
	 */
	protected void initialize() {
		// Initialize clock face
		dial = new Ellipse2D.Float(x - radius, y - radius, radius * 2,
				radius * 2);

		// Initialize decisecond hand
		float dWidthOfRadius = .04f;// Width of the decisecond hand is 4% of the
		// dail radius
		float dLengthOfRadius = .85f;// Length of the decisecond hand is 85% of
		// the dail radius
		float dMarginOfRadius = .1f;// Margin length of the decisecond hand is
		// 10% of the dail radius
		millisecondHand = new GeneralPath();
		SimpleParts.createHand(millisecondHand, x, y, radius, dWidthOfRadius,
				dLengthOfRadius, dMarginOfRadius);

		// Initialize hand transformation object.
		initTransform();

		// Initialize the first tick
		float tWidthOfRadius = .04f;// Width of the decisecond hand is 4% of the
		// dail radius
		float tLengthOfRadius = .1f;// Length of the decisecond hand is 10% of
		// the dail radius
		tick = new GeneralPath();
		SimpleParts.createHand(tick, x, y - radius, radius, tWidthOfRadius,
				tLengthOfRadius / 2, tLengthOfRadius / 2);

		// initialize container size
		float sizeMarginOfRadius = .1f;
		size.width = (int) (x + radius * (1 + sizeMarginOfRadius));
		size.height = (int) (y + radius * (1 + sizeMarginOfRadius));
	}

	/**
	 */
	@Override
	protected void initTransform() {
		super.initTransform();

		// Move all parts, to leave some margin
		float margin = 0;
		trans = AffineTransform.getTranslateInstance(radius * margin, radius
				* margin);
	}

	/**
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw 10 ticks
		drawTicks(g, 10);
	}

	/**
	 * Draw ticks.
	 */
	protected void drawTicks(Graphics g, int tickNumber) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform at = AffineTransform.getRotateInstance(0, x, y);

		for (int p = 0; p < tickNumber; p++) {
			at = getRotateInstance(tickNumber, p);
			g2.setPaint(Color.red);
			g2.fill(trans.createTransformedShape(at
					.createTransformedShape(tick)));
		}
	}
}
