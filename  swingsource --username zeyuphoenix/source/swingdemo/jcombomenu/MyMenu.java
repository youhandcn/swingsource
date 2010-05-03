package jcombomenu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;

public class MyMenu extends JMenu {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * create menu icon.
	 */
	ArrowIcon iconRenderer;

	/**
	 * create menu.
	 */
	public MyMenu() {
		this("");
	}

	/**
	 * create menu.
	 * 
	 * @param text
	 *            menu text.
	 */
	public MyMenu(String text) {
		super(text);
		iconRenderer = new ArrowIcon(SwingConstants.SOUTH, true);
		setBorder(new EtchedBorder());
		setIcon(new BlankIcon(null, 11));
		setHorizontalTextPosition(JButton.LEFT);
		setFocusPainted(true);
	}

	/**
	 * paint the menu.
	 * 
	 * @param g
	 *            Graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Dimension d = this.getPreferredSize();
		int x = Math.max(0, d.width - iconRenderer.getIconWidth() - 3);
		int y = Math.max(0, (d.height - iconRenderer.getIconHeight()) / 2 - 2);
		iconRenderer.paintIcon(this, g, x, y);
	}
}
