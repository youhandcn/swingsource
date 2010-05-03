package jlist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * the list cell's label.
 * 
 * @author zeyuphoenix <br>
 *         daily jlist MyListLabel.java <br>
 *         2008 2008/03/19 13:40:28 <br>
 */
public class MyListLabel extends JLabel {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * is the label select.
	 */
	boolean isSelected;
	/**
	 * is the label have focus.
	 */
	boolean hasFocus;

	/**
	 * the list cell's label.
	 */
	public MyListLabel() {
	}

	/**
	 * set the label back ground color.
	 * 
	 * @param color
	 *            back ground color
	 */
	public void setBackground(Color color) {
		if (color instanceof ColorUIResource) {
			color = null;
		}
		super.setBackground(color);
	}

	/**
	 * repaint the label.
	 * 
	 * @param g
	 *            Graphics
	 */
	public void paint(Graphics g) {
		String str;
		if ((str = getText()) != null) {
			if (0 < str.length()) {
				if (isSelected) {
					g.setColor(UIManager.getColor("Tree.selectionBackground"));
				} else {
					g.setColor(UIManager.getColor("Tree.textBackground"));
				}
				Dimension d = getPreferredSize();
				int imageOffset = 0;
				Icon currentI = getIcon();
				if (currentI != null) {
					imageOffset = currentI.getIconWidth()
							+ Math.max(0, getIconTextGap() - 1);
				}
				g.fillRect(imageOffset, 0, d.width - 1 - imageOffset, d.height);
				if (hasFocus) {
					g.setColor(UIManager.getColor("Tree.selectionBorderColor"));
					g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
							d.height - 1);
				}
			}
		}
		super.paint(g);
	}

	/**
	 * get it's size.
	 * 
	 * @return Dimension size.
	 */
	public Dimension getPreferredSize() {
		Dimension retDimension = super.getPreferredSize();
		if (retDimension != null) {
			retDimension = new Dimension(retDimension.width + 3,
					retDimension.height);
		}
		return retDimension;
	}

	/**
	 * set the label select.
	 * 
	 * @param isSelected
	 *            is select.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * set the label have focus.
	 * 
	 * @param hasFocus
	 *            have focus.
	 */
	public void setFocus(boolean hasFocus) {
		this.hasFocus = hasFocus;
	}
}
