package jtable.tableheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * A small fixed size picture, typically used to decorate components.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyBlankIcon.java <br>
 *         2008 2008/03/27 15:58:16 <br>
 */
public class MyBlankIcon implements Icon {
	/** fill color. */
	private Color fillColor = null;
	/** icon size. */
	private int size = 0;

	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public MyBlankIcon() {
		this(null, 11);
	}

	/**
	 * A small fixed size picture, typically used to decorate components.
	 * 
	 * @param color
	 *            color in icon.
	 * @param size
	 *            icon size.
	 */
	public MyBlankIcon(Color color, int size) {
		fillColor = color;
		this.size = size;
	}

	/**
	 * Draw the icon at the specified location. Icon implementations may use the
	 * Component argument to get properties useful for painting, e.g. the
	 * foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (fillColor != null) {
			g.setColor(fillColor);
			g.drawRect(x, y, size - 1, size - 1);
		}
	}

	/**
	 * Returns the icon's width.
	 * 
	 * @return an int specifying the fixed width of the icon.
	 */
	public int getIconWidth() {
		return size;
	}

	/**
	 * Returns the icon's height.
	 * 
	 * @return an int specifying the fixed height of the icon.
	 */
	public int getIconHeight() {
		return size;
	}
}
