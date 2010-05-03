/*
 * ImageToolTip.java
 *
 * Created on June 27, 2007, 5:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tooltip;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JToolTip;

/**
 */
class ImageToolTip extends JToolTip {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Icon icon;

	/** Creates a new instance of ImageToolTip */
	public ImageToolTip(Icon icon) {
		this.icon = icon;
	}

	public void paint(Graphics g) {
		if (icon != null)
			icon.paintIcon(this, g, 0, 0);
	}

	public Dimension getPreferredSize() {
		if (icon == null)
			return super.getPreferredSize();
		else
			return new Dimension(icon.getIconWidth(), icon.getIconHeight());
	}
}
