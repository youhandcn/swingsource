package jtree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;
import javax.swing.Icon;

public class MyNodeIcon implements Icon {
	/** icon. */
	private Icon icon = null;
	/** image. */
	private Image image = null;

	/**
	 * A small fixed size picture, typically used to decorate components.
	 * 
	 * @param icon
	 *            icon.
	 */
	public MyNodeIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Draw the icon at the specified location. Icon implementations may use the
	 * Component argument to get properties useful for painting, e.g. the
	 * foreground or background color.
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if (image == null) {
			Image orgImage = c.createImage(getIconWidth(), getIconHeight());
			Graphics imageG = orgImage.getGraphics();
			Color background = c.getBackground();
			imageG.setColor(background);
			imageG.fillRect(0, 0, getIconWidth(), getIconHeight());

			icon.paintIcon(c, imageG, x, y);

			ImageFilter colorfilter = new MyIconFilter();
			image = c.createImage(new FilteredImageSource(orgImage.getSource(),
					colorfilter));

		}
		g.drawImage(image, x, y, null);
	}

	/**
	 * Returns the icon's width.
	 * 
	 * @return specifying the fixed width of the icon.
	 */
	public int getIconWidth() {
		return icon.getIconWidth();
	}

	/**
	 * Returns the icon's height.
	 * 
	 * @return an int specifying the fixed height of the icon.
	 */
	public int getIconHeight() {
		return icon.getIconHeight();
	}

	/**
	 * This class provides an easy way to create an ImageFilter which modifies
	 * the pixels of an image in the default RGB ColorModel.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtreetest MyNodeIcon.java <br>
	 *         2008 2008/03/26 13:07:55 <br>
	 */
	private class MyIconFilter extends RGBImageFilter {
		/**
		 * This class provides an easy way to create an ImageFilter which
		 * modifies the pixels of an image in the default RGB ColorModel.
		 */
		public MyIconFilter() {
		}

		public int filterRGB(int x, int y, int rgb) {
			int r = (rgb & 0xff0000) >> 16;
			int g = (rgb & 0x00ff00) >> 8;
			int b = (rgb & 0x0000ff);
			int iy = (int) (r + g + b) / 3;
			iy = Math.min(255, iy);
			return ((rgb & 0xff000000) | (iy << 16) | (iy << 8) | iy);
		}
	}
}
