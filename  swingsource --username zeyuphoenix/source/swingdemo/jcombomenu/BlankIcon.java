package jcombomenu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.SwingConstants;

/**
 * the icon in the menu
 * 
 * @author zeyuphoenix <br>
 *         daily jcombomenu BlankIcon.java <br>
 *         2008 2008/03/19 19:02:50 <br>
 */
public class BlankIcon implements Icon {
	private Color fillColor;
	private int size;

	/**
	 * the icon in the menu
	 */
	public BlankIcon() {
		this(null, SwingConstants.TRAILING);
	}

	/**
	 * the icon in the menu
	 * 
	 * @param color
	 *            icon color.
	 * @param size
	 *            icon size
	 */
	public BlankIcon(Color color, int size) {
		fillColor = color;
		this.size = size;
	}

	/**
	 * paint the icon.
	 * 
	 * @param c
	 *            Component
	 * @param g
	 *            Graphics
	 * @param x
	 *            height
	 * @param y
	 *            width
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (fillColor != null) {
			g.setColor(fillColor);
			g.drawRect(x, y, size - 1, size - 1);
		}
	}

	/**
	 * get icon Width.
	 * 
	 * @return Width.
	 */
	public int getIconWidth() {
		return size;
	}

	/**
	 * get icon height.
	 * 
	 * @return height.
	 */
	public int getIconHeight() {
		return size;
	}
}
