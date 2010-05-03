package jtabpane;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * JButton object that draws a scaled Arrow in one of the cardinal directions.
 * 
 * @author zeyuphoenix <br>
 *         daily jtabpane StopArrowButton.java <br>
 *         2008 2008/03/25 14:26:41 <br>
 */
public class StopArrowButton extends BasicArrowButton {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a {@code BasicArrowButton} whose arrow is drawn in the specified
	 * direction and with the specified colors.
	 * 
	 * @param direction
	 *            the direction of the arrow.
	 */
	public StopArrowButton(int direction) {
		super(direction);
	}

	/**
	 * Paints a triangle.
	 * 
	 * @param g
	 *            the {@code Graphics} to draw to
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @param size
	 *            the size of the triangle to draw
	 * @param direction
	 *            the direction in which to draw the arrow; one of
	 *            {@code SwingConstants.NORTH}, {@code SwingConstants.SOUTH},
	 *            {@code SwingConstants.EAST} or {@code SwingConstants.WEST}
	 * @param isEnabled
	 *            whether or not the arrow is drawn enabled
	 */
	public void paintTriangle(Graphics g, int x, int y, int size,
			int direction, boolean isEnabled) {
		super.paintTriangle(g, x, y, size, direction, isEnabled);
		Color c = g.getColor();
		if (isEnabled) {
			g.setColor(UIManager.getColor("controlDkShadow"));
		} else {
			g.setColor(UIManager.getColor("controlShadow"));
		}
		g.translate(x, y);
		size = Math.max(size, 2);
		int mid = size / 2;
		int h = size - 1;
		if (direction == WEST) {
			g.drawLine(-1, mid - h, -1, mid + h);
			if (!isEnabled) {
				g.setColor(UIManager.getColor("controlLtHighlight"));
				g.drawLine(0, mid - h + 1, 0, mid - 1);
				g.drawLine(0, mid + 2, 0, mid + h + 1);
			}
		} else { // EAST
			g.drawLine(size, mid - h, size, mid + h);
			if (!isEnabled) {
				g.setColor(UIManager.getColor("controlLtHighlight"));
				g.drawLine(size + 1, mid - h + 1, size + 1, mid + h + 1);
			}
		}
		g.setColor(c);
	}
}
