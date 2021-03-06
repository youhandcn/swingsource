package lunarmonthcalendar;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * A small fixed size picture, typically used to decorate components.
 */
public class NextIcon implements Icon {
	/** fill color. */
	private Color fillColor = null;
	/** icon width size */
	private int width = 0;
	/** icon height size */
	private int height = 0;

	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public NextIcon() {
		this(null, 10);
	}

	/**
	 * A small fixed size picture, typically used to decorate components.
	 * 
	 * @param color
	 *            color in icon.
	 * @param size
	 *            icon size.
	 */
	public NextIcon(Color color, int size) {

		this(color, size, size);
	}

	/**
	 * A small fixed size picture, typically used to decorate components.
	 * 
	 * @param color
	 *            color in icon.
	 * @param size
	 *            icon size.
	 */
	public NextIcon(Color color, int width, int height) {
		fillColor = color;
		this.width = width;
		this.height = height;
	}

	/**
	 * Draw the icon at the specified location. Icon implementations may use the
	 * Component argument to get properties useful for painting, e.g. the
	 * foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// set x array
		int[] xPoints = new int[3];
		// set y array.
		int[] yPoints = new int[3];

		// set fill color.
		g.setColor(fillColor);

		// first point
		xPoints[0] = x;
		yPoints[0] = y;

		// second point
		xPoints[1] = x;
		yPoints[1] = y + height;

		// three point.
		xPoints[2] = x + width;
		yPoints[2] = y + (height / 2);

		// Fills a closed polygon defined by Polygon.
		g.fillPolygon(xPoints, yPoints, 3);

	}

	/**
	 * Returns the icon's width.
	 * 
	 * @return an int specifying the fixed width of the icon.
	 */
	public int getIconWidth() {
		return width;
	}

	/**
	 * Returns the icon's height.
	 * 
	 * @return an int specifying the fixed height of the icon.
	 */
	public int getIconHeight() {
		return height;
	}

	/**
	 * Returns the icon's color.
	 * 
	 * @return color specifying the fixed of the icon.
	 */
	public Color getIconColor() {
		return fillColor;
	}
}
