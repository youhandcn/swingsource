package jtree;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * A small fixed size picture, typically used to decorate components.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyFolderIcon.java <br>
 *         2008 2008/03/26 10:29:00 <br>
 */
public class MyFolderIcon implements Icon {
	private int width = 16;
	private int height = 16;
	private int additionalHeight = 2;

	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public MyFolderIcon() {
		this(16, 16);
	}

	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public MyFolderIcon(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Draw the icon at the specified location. Icon implementations may use the
	 * Component argument to get properties useful for painting, e.g. the
	 * foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		int right = width - 1;
		int bottom = height - 2;

		g.setColor(c.getBackground());
		g.fillRect(0, 0, width, getIconHeight());

		// Draw tab
		g.setColor(MetalLookAndFeel.getControlDisabled());
		g.drawLine(right - 5, 2, right, 2);
		g.drawLine(right - 6, 3, right - 6, 4);
		g.drawLine(right, 3, right, 4);

		// Draw outline
		g.setColor(MetalLookAndFeel.getControlDisabled());
		g.drawLine(0, 5, 0, bottom); // left side
		g.drawLine(1, 4, right - 7, 4); // first part of top
		g.drawLine(right - 6, 5, right - 1, 5); // second part of top
		g.drawLine(right, 4, right, bottom); // right side
		g.drawLine(0, bottom, right, bottom); // bottom
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
		return height + additionalHeight;
	}
}
