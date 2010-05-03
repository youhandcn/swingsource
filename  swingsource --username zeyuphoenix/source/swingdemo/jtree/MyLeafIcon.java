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
public class MyLeafIcon implements Icon {
	int width = 16;
	int height = 16;
	int additionalHeight = 4;
	
	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public MyLeafIcon() {
		this(16, 16);
	}

	/**
	 * A small fixed size picture, typically used to decorate components.
	 */
	public MyLeafIcon(int width, int height) {
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
		int bottom = height + 1;

		g.setColor(c.getBackground());
		g.fillRect(0, 0, width, getIconHeight());

		// Draw frame
		g.setColor(MetalLookAndFeel.getControlDisabled());
		g.drawLine(2, 2, 2, bottom); // left
		g.drawLine(2, 2, right - 4, 2); // top
		g.drawLine(2, bottom, right - 1, bottom); // bottom
		g.drawLine(right - 1, 8, right - 1, bottom); // right
		g.drawLine(right - 6, 4, right - 2, 8); // slant 1
		g.drawLine(right - 5, 3, right - 4, 3); // part of slant 2
		g.drawLine(right - 3, 4, right - 3, 5); // part of slant 2
		g.drawLine(right - 2, 6, right - 2, 7); // part of slant 2
	}
	/**
	 * Returns the icon's width.
	 * 
	 * @return specifying the fixed width of the icon.
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
