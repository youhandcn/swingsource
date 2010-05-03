package jtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;

/**
 * A class that implements an empty border with no size. This provides a
 * convenient base class from which other border classes can be easily derived.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable LinesBorder.java <br>
 *         2008 2008/03/31 16:58:11 <br>
 */
public class LinesBorder extends AbstractBorder implements SwingConstants {
	/**
	 * ÇtÇhÇcÅD
	 */
	private static final long serialVersionUID = 1L;
	private int northThickness = 0;
	private int southThickness = 0;
	private int eastThickness = 0;
	private int westThickness = 0;
	private Color northColor = null;
	private Color southColor = null;
	private Color eastColor = null;
	private Color westColor = null;

	/**
	 * A class that implements an empty border with no size. This provides a
	 * convenient base class from which other border classes can be easily
	 * derived.
	 * 
	 * @param color
	 *            line color.
	 */
	public LinesBorder(Color color) {
		this(color, 1);
	}

	public LinesBorder(Color color, int thickness) {
		setColor(color);
		setThickness(thickness);
	}

	public LinesBorder(Color color, Insets insets) {
		setColor(color);
		setThickness(insets);
	}

	/**
	 * This default implementation does no painting.
	 * 
	 * @param c
	 *            the component for which this border is being painted
	 * @param g
	 *            the paint graphics
	 * @param x
	 *            the x position of the painted border
	 * @param y
	 *            the y position of the painted border
	 * @param width
	 *            the width of the painted border
	 * @param height
	 *            the height of the painted border
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Color oldColor = g.getColor();

		g.setColor(northColor);
		for (int i = 0; i < northThickness; i++) {
			g.drawLine(x, y + i, x + width - 1, y + i);
		}
		g.setColor(southColor);
		for (int i = 0; i < southThickness; i++) {
			g
					.drawLine(x, y + height - i - 1, x + width - 1, y + height
							- i - 1);
		}
		g.setColor(eastColor);
		for (int i = 0; i < westThickness; i++) {
			g.drawLine(x + i, y, x + i, y + height - 1);
		}
		g.setColor(westColor);
		for (int i = 0; i < eastThickness; i++) {
			g.drawLine(x + width - i - 1, y, x + width - i - 1, y + height - 1);
		}

		g.setColor(oldColor);
	}

	/**
	 * This default implementation returns a new <code>Insets</code> instance
	 * where the <code>top</code>, <code>left</code>, <code>bottom</code>,
	 * and <code>right</code> fields are set to <code>0</code>.
	 * 
	 * @param c
	 *            the component for which this border insets value applies
	 * @return the new <code>Insets</code> object initialized to 0
	 */
	public Insets getBorderInsets(Component c) {
		return new Insets(northThickness, westThickness, southThickness,
				eastThickness);
	}

	/**
	 * Reinitializes the insets parameter with this Border's current Insets.
	 * 
	 * @param c
	 *            the component for which this border insets value applies
	 * @param insets
	 *            the object to be reinitialized
	 * @return the <code>insets</code> object
	 */
	public Insets getBorderInsets(Component c, Insets insets) {
		return new Insets(northThickness, westThickness, southThickness,
				eastThickness);
	}

	/**
	 * This default implementation returns false.
	 * 
	 * @return false
	 */
	public boolean isBorderOpaque() {
		return true;
	}

	/**
	 * set all line color.
	 * 
	 * @param c
	 *            line color.
	 */
	public void setColor(Color c) {
		northColor = c;
		southColor = c;
		eastColor = c;
		westColor = c;
	}

	/**
	 * * set line color.
	 * 
	 * @param c
	 *            line color.
	 * @param direction
	 *            direction.
	 */
	public void setColor(Color c, int direction) {
		switch (direction) {
		case NORTH:
			northColor = c;
			break;
		case SOUTH:
			southColor = c;
			break;
		case EAST:
			eastColor = c;
			break;
		case WEST:
			westColor = c;
			break;
		default:
		}
	}

	/**
	 * set Thickness Thickness
	 * 
	 * @param n
	 */
	public void setThickness(int n) {
		northThickness = n;
		southThickness = n;
		eastThickness = n;
		westThickness = n;
	}

	/**
	 * * set Thickness
	 * 
	 * @param insets
	 *            Thickness
	 */
	public void setThickness(Insets insets) {
		northThickness = insets.top;
		southThickness = insets.bottom;
		eastThickness = insets.right;
		westThickness = insets.left;
	}

	/**
	 * get Thickness
	 * 
	 * @param n
	 *            inset.
	 * @param direction
	 *            direction
	 */
	public void setThickness(int n, int direction) {
		switch (direction) {
		case NORTH:
			northThickness = n;
			break;
		case SOUTH:
			southThickness = n;
			break;
		case EAST:
			eastThickness = n;
			break;
		case WEST:
			westThickness = n;
			break;
		default:
		}
	}
}
