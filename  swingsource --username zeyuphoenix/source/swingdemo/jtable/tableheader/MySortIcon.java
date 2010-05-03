package jtable.tableheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

/**
 * When table sort,the icon is showing in header.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MySortIcon.java <br>
 *         2008 2008/03/27 15:43:56 <br>
 */
public class MySortIcon implements Icon {
	/**
	 * default size.
	 */
	private static final int DEFAULT_SIZE = 11;
	/** color. */
	private Color edge1;
	/** color. */
	private Color edge2;
	/** the color that fill in icon. */
	private Color fill;
	/** icon size. */
	private int size = 0;
	/** icon direction */
	private int direction = 0;

	/**
	 * When table sort,the icon is showing in header.
	 * 
	 * @param direction
	 *            direction
	 * @param isRaisedView
	 *            Raised View
	 * @param isPressedView
	 *            Pressed View
	 */
	public MySortIcon(int direction, boolean isRaisedView, boolean isPressedView) {
		if (isRaisedView) {
			if (isPressedView) {
				init(UIManager.getColor("controlLtHighlight"), UIManager
						.getColor("controlDkShadow"), UIManager
						.getColor("controlShadow"), DEFAULT_SIZE, direction);
			} else {
				init(UIManager.getColor("controlHighlight"), UIManager
						.getColor("controlShadow"), UIManager
						.getColor("control"), DEFAULT_SIZE, direction);
			}
		} else {
			if (isPressedView) {
				init(UIManager.getColor("controlDkShadow"), UIManager
						.getColor("controlLtHighlight"), UIManager
						.getColor("controlShadow"), DEFAULT_SIZE, direction);
			} else {
				init(UIManager.getColor("controlShadow"), UIManager
						.getColor("controlHighlight"), UIManager
						.getColor("control"), DEFAULT_SIZE, direction);
			}
		}
	}

	/**
	 * When table sort,the icon is showing in header.
	 * 
	 * @param edge1
	 *            color.
	 * @param edge2
	 *            color.
	 * @param fill
	 *            the color that fill in icon.
	 * @param size
	 *            icon size.
	 * @param direction
	 *            icon direction
	 */
	public MySortIcon(Color edge1, Color edge2, Color fill, int size,
			int direction) {
		init(edge1, edge2, fill, size, direction);
	}

	/**
	 * Draw the icon at the specified location. Icon implementations may use the
	 * Component argument to get properties useful for painting, e.g. the
	 * foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		switch (direction) {
		case MyHeaderButtonRenderer.DOWN:
			drawDownArrow(g, x, y);
			break;
		case MyHeaderButtonRenderer.UP:
			drawUpArrow(g, x, y);
			break;
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

	private void init(Color edge1, Color edge2, Color fill, int size,
			int direction) {
		this.edge1 = edge1;
		this.edge2 = edge2;
		this.fill = fill;
		this.size = size;
		this.direction = direction;
	}

	/**
	 * draw down icon.
	 * 
	 * @param g
	 *            Graphics
	 * @param xo
	 *            x
	 * @param yo
	 *            y
	 */
	private void drawDownArrow(Graphics g, int xo, int yo) {
		g.setColor(edge1);
		g.drawLine(xo, yo, xo + size - 1, yo);
		g.drawLine(xo, yo + 1, xo + size - 3, yo + 1);
		g.setColor(edge2);
		g.drawLine(xo + size - 2, yo + 1, xo + size - 1, yo + 1);
		int x = xo + 1;
		int y = yo + 2;
		int dx = size - 6;
		while (y + 1 < yo + size) {
			g.setColor(edge1);
			g.drawLine(x, y, x + 1, y);
			g.drawLine(x, y + 1, x + 1, y + 1);
			if (0 < dx) {
				g.setColor(fill);
				g.drawLine(x + 2, y, x + 1 + dx, y);
				g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
			}
			g.setColor(edge2);
			g.drawLine(x + dx + 2, y, x + dx + 3, y);
			g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
			x += 1;
			y += 2;
			dx -= 2;
		}
		g.setColor(edge1);
		g.drawLine(xo + (size / 2), yo + size - 1, xo + (size / 2), yo + size
				- 1);
	}

	/**
	 * draw up icon.
	 * 
	 * @param g
	 *            Graphics
	 * @param xo
	 *            x
	 * @param yo
	 *            y
	 */
	private void drawUpArrow(Graphics g, int xo, int yo) {
		g.setColor(edge1);
		int x = xo + (size / 2);
		g.drawLine(x, yo, x, yo);
		x--;
		int y = yo + 1;
		int dx = 0;
		while (y + 3 < yo + size) {
			g.setColor(edge1);
			g.drawLine(x, y, x + 1, y);
			g.drawLine(x, y + 1, x + 1, y + 1);
			if (0 < dx) {
				g.setColor(fill);
				g.drawLine(x + 2, y, x + 1 + dx, y);
				g.drawLine(x + 2, y + 1, x + 1 + dx, y + 1);
			}
			g.setColor(edge2);
			g.drawLine(x + dx + 2, y, x + dx + 3, y);
			g.drawLine(x + dx + 2, y + 1, x + dx + 3, y + 1);
			x -= 1;
			y += 2;
			dx += 2;
		}
		g.setColor(edge1);
		g.drawLine(xo, yo + size - 3, xo + 1, yo + size - 3);
		g.setColor(edge2);
		g.drawLine(xo + 2, yo + size - 2, xo + size - 1, yo + size - 2);
		g.drawLine(xo, yo + size - 1, xo + size, yo + size - 1);
	}
}
