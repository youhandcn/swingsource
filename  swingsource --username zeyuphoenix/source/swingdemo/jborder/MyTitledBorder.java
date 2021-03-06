package jborder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * the title border that override it.
 * 
 * @author zeyuphoenix <br>
 *         daily jborder MyTitledBorder.java <br>
 *         2008 2008/03/20 11:14:11 <br>
 */
public class MyTitledBorder extends TitledBorder {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the component in the border.
	 */
	protected JComponent component = null;

	/**
	 * Creates a TitledBorder instance.
	 * 
	 * @param component
	 *            the JComponent
	 */
	public MyTitledBorder(JComponent component) {
		this(null, component, LEFT, TOP);
	}

	/**
	 * Creates a TitledBorder instance.
	 * 
	 * @param border
	 *            the border
	 */
	public MyTitledBorder(Border border) {
		this(border, null, LEFT, TOP);
	}

	/**
	 * Creates a TitledBorder instance.
	 * 
	 * @param border
	 *            the border
	 * @param component
	 *            the JComponent
	 * @param titleJustification
	 *            the justification for the title
	 * @param titlePosition
	 *            the position for the title
	 */
	public MyTitledBorder(Border border, JComponent component) {
		this(border, component, LEFT, TOP);
	}

	/**
	 * Creates a TitledBorder instance.
	 * 
	 * @param border
	 *            the border
	 * @param component
	 *            the JComponent
	 * @param titleJustification
	 *            the justification for the title
	 * @param titlePosition
	 *            the position for the title
	 */
	public MyTitledBorder(Border border, JComponent component,
			int titleJustification, int titlePosition) {
		super(border, null, titleJustification, titlePosition, null, null);
		this.component = component;
		if (border == null) {
			this.border = super.getBorder();
		}
	}

	/**
	 * Paints the border for the specified component with the specified position
	 * and size.
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
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		Rectangle borderR = new Rectangle(x + EDGE_SPACING, y + EDGE_SPACING,
				width - (EDGE_SPACING * 2), height - (EDGE_SPACING * 2));
		Insets borderInsets;
		if (border != null) {
			borderInsets = border.getBorderInsets(c);
		} else {
			borderInsets = new Insets(0, 0, 0, 0);
		}

		Rectangle rect = new Rectangle(x, y, width, height);
		Insets insets = getBorderInsets(c);
		Rectangle compR = getComponentRect(rect, insets);
		int diff;
		switch (titlePosition) {
		case ABOVE_TOP:
			diff = compR.height + TEXT_SPACING;
			borderR.y += diff;
			borderR.height -= diff;
			break;
		case TOP:
		case DEFAULT_POSITION:
			diff = insets.top / 2 - borderInsets.top - EDGE_SPACING;
			borderR.y += diff;
			borderR.height -= diff;
			break;
		case BELOW_TOP:
		case ABOVE_BOTTOM:
			break;
		case BOTTOM:
			diff = insets.bottom / 2 - borderInsets.bottom - EDGE_SPACING;
			borderR.height -= diff;
			break;
		case BELOW_BOTTOM:
			diff = compR.height + TEXT_SPACING;
			borderR.height -= diff;
			break;
		}
		border.paintBorder(c, g, borderR.x, borderR.y, borderR.width,
				borderR.height);
		Color col = g.getColor();
		g.setColor(c.getBackground());
		g.fillRect(compR.x, compR.y, compR.width, compR.height);
		g.setColor(col);
		component.repaint();
	}

	/**
	 * Reinitialize the insets parameter with this Border's current Insets.
	 * 
	 * @param c
	 *            the component for which this border insets value applies
	 * @param insets
	 *            the object to be reinitialized
	 */
	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		Insets borderInsets;
		if (border != null) {
			borderInsets = border.getBorderInsets(c);
		} else {
			borderInsets = new Insets(0, 0, 0, 0);
		}
		insets.top = EDGE_SPACING + TEXT_SPACING + borderInsets.top;
		insets.right = EDGE_SPACING + TEXT_SPACING + borderInsets.right;
		insets.bottom = EDGE_SPACING + TEXT_SPACING + borderInsets.bottom;
		insets.left = EDGE_SPACING + TEXT_SPACING + borderInsets.left;

		if (c == null || component == null) {
			return insets;
		}

		int compHeight = 0;
		if (component != null) {
			compHeight = component.getPreferredSize().height;
		}

		switch (titlePosition) {
		case ABOVE_TOP:
			insets.top += compHeight + TEXT_SPACING;
			break;
		case TOP:
		case DEFAULT_POSITION:
			insets.top += Math.max(compHeight, borderInsets.top)
					- borderInsets.top;
			break;
		case BELOW_TOP:
			insets.top += compHeight + TEXT_SPACING;
			break;
		case ABOVE_BOTTOM:
			insets.bottom += compHeight + TEXT_SPACING;
			break;
		case BOTTOM:
			insets.bottom += Math.max(compHeight, borderInsets.bottom)
					- borderInsets.bottom;
			break;
		case BELOW_BOTTOM:
			insets.bottom += compHeight + TEXT_SPACING;
			break;
		}
		return insets;
	}

	/**
	 * get title component.
	 * 
	 * @return component
	 */
	public JComponent getTitleComponent() {
		return component;
	}

	/**
	 * set title component.
	 * 
	 * @param component
	 *            component
	 */
	public void setTitleComponent(JComponent component) {
		this.component = component;
	}

	/**
	 * get component Rectangle.
	 * 
	 * @param rect
	 *            Rectangle
	 * @param borderInsets
	 *            borderInsets
	 * @return Rectangle
	 */
	public Rectangle getComponentRect(Rectangle rect, Insets borderInsets) {
		Dimension compD = component.getPreferredSize();
		Rectangle compR = new Rectangle(0, 0, compD.width, compD.height);
		switch (titlePosition) {
		case ABOVE_TOP:
			compR.y = EDGE_SPACING;
			break;
		case TOP:
		case DEFAULT_POSITION:
			compR.y = EDGE_SPACING
					+ (borderInsets.top - EDGE_SPACING - TEXT_SPACING - compD.height)
					/ 2;
			break;
		case BELOW_TOP:
			compR.y = borderInsets.top - compD.height - TEXT_SPACING;
			break;
		case ABOVE_BOTTOM:
			compR.y = rect.height - borderInsets.bottom + TEXT_SPACING;
			break;
		case BOTTOM:
			compR.y = rect.height
					- borderInsets.bottom
					+ TEXT_SPACING
					+ (borderInsets.bottom - EDGE_SPACING - TEXT_SPACING - compD.height)
					/ 2;
			break;
		case BELOW_BOTTOM:
			compR.y = rect.height - compD.height - EDGE_SPACING;
			break;
		}
		switch (titleJustification) {
		case LEFT:
		case DEFAULT_JUSTIFICATION:
			compR.x = TEXT_INSET_H + borderInsets.left;
			break;
		case RIGHT:
			compR.x = rect.width - borderInsets.right - TEXT_INSET_H
					- compR.width;
			break;
		case CENTER:
			compR.x = (rect.width - compR.width) / 2;
			break;
		}
		return compR;
	}

}
