package clock;

import java.awt.Graphics;

/**
 * To implement a analog-type clock with stopwatch.<br>
 */
public class MyPartsWithStopwatch extends MyParts {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The stopwatch parts.
	 */
	private MyStopwatchParts stopwatch;

	/**
	 * Default constructor: Creates a simple stopwatch under the center of the
	 * clock.
	 * 
	 * @throws Exception
	 */
	public MyPartsWithStopwatch() throws Exception {
		super();
		initialize(x, y + 40, 40);
	}

	/**
	 * To construct stopwatch shape.
	 * 
	 * @param sx
	 *            The x coordinate of the upper left corner of the boundary
	 *            square of stopwatch.
	 * @param sy
	 *            The y coordinate of the upper left corner of the boundary
	 *            square of stopwatch.
	 * @param sWidth
	 *            The width of the boundary square of stopwatch.
	 */
	private void initialize(float sx, float sy, float sWidth) throws Exception {
		stopwatch = new MyStopwatchParts(sx, sy, sWidth);
	}

	/**
	 */
	@Override
	public void doTransform(int hour, int minute, int second, int millisecond) {
		super.doTransform(hour, minute, second, millisecond);
		stopwatch.doTransform(hour, minute, second, millisecond);
	}

	/**
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		stopwatch.paintComponent(g);
	}
}
