package jcombomenu;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * paint the menu item image.
 * 
 * @author zeyuphoenix <br>
 *         daily jcombomenu ArrowIcon.java <br>
 *         2008 2008/03/19 18:44:16 <br>
 */
public class ArrowIcon implements Icon, SwingConstants {
	/**
	 * size.
	 */
	private int size = 0;
	/**
	 * icon size.
	 */
	private int iconSize = 0;
	/**
	 * direction.
	 */
	private int direction = 0;
	/**
	 * enabled.
	 */
	private boolean isEnabled = false;
	/**
	 * drawn in the specified direction..
	 */
	private BasicArrowButton iconRenderer = null;

	/**
	 * * icon in the menu.
	 * 
	 * @param direction
	 *            direction
	 * @param isPressedView
	 *            enable
	 */
	public ArrowIcon(int direction, boolean isPressedView) {
		this(SwingConstants.TRAILING, direction, isPressedView);
	}

	/**
	 * icon in the menu.
	 * 
	 * @param iconSize
	 *            size
	 * @param direction
	 *            direction
	 * @param isEnabled
	 *            enable
	 */
	public ArrowIcon(int iconSize, int direction, boolean isEnabled) {
		this.size = iconSize / 2;
		this.iconSize = iconSize;
		this.direction = direction;
		this.isEnabled = isEnabled;
		iconRenderer = new BasicArrowButton(direction);
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
		iconRenderer.paintTriangle(g, x, y, size, direction, isEnabled);
	}

	/**
	 * get icon Width.
	 * 
	 * @return Width.
	 */
	public int getIconWidth() {
		switch (direction) {
		case NORTH:
		case SOUTH:
			return iconSize;
		case EAST:
		case WEST:
			return size;
		}
		return iconSize;
	}

	/**
	 * get icon height.
	 * 
	 * @return height.
	 */
	public int getIconHeight() {
		switch (direction) {
		case NORTH:
		case SOUTH:
			return size;
		case EAST:
		case WEST:
			return iconSize;
		}
		return size;
	}
}
