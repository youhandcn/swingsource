package jtree;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Hashtable;

import javax.swing.Icon;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 * Factory object that vends <code>Icon</code>s for the Java<sup><font
 * size="-2">TM</font></sup> look and feel (Metal).
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTextIcon.java <br>
 *         2008 2008/03/26 13:24:49 <br>
 */
public class MyTextIcon extends MetalIconFactory.TreeLeafIcon {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** icon in label. */
	private String label = null;
	/** set the icon table. */
	private static Hashtable<String, String> labels = null;

	/**
	 * Factory object that vends <code>Icon</code>s for the Java<sup><font
	 * size="-2">TM</font></sup> look and feel (Metal).
	 */
	public MyTextIcon() {
	}

	/**
	 * paint the icon.
	 * 
	 * @param c
	 *            Component
	 * @param g
	 *            Graphics
	 * @param x
	 *            int x
	 * @param y
	 *            int y
	 */
	public void paintIcon(Component c, Graphics g, int x, int y) {
		super.paintIcon(c, g, x, y);
		if (label != null) {
			FontMetrics fm = g.getFontMetrics();

			int offsetX = (getIconWidth() - fm.stringWidth(label)) / 2;
			int offsetY = (getIconHeight() - fm.getHeight()) / 2 - 2;

			g.drawString(label, x + offsetX, y + offsetY + fm.getHeight());
		}
	}

	/**
	 * get the text icon.
	 * 
	 * @param str
	 *            text
	 * @return icon.
	 */
	public static Icon getIcon(String str) {
		if (labels == null) {
			labels = new Hashtable<String, String>();
			setDefaultSet();
		}
		MyTextIcon icon = new MyTextIcon();
		icon.label = (String) labels.get(str);
		return icon;
	}

	/**
	 * set label's icon.
	 * 
	 * @param ext
	 * @param label
	 */
	public static void setLabelSet(String ext, String label) {
		if (labels == null) {
			labels = new Hashtable<String, String>();
			setDefaultSet();
		}
		labels.put(ext, label);
	}

	/**
	 * set some default set icon.
	 */
	private static void setDefaultSet() {
		labels.put("c", "C");
		labels.put("java", "J");
		labels.put("html", "H");
		labels.put("htm", "H");
		labels.put("txt", "TXT");
		labels.put("TXT", "TXT");
		labels.put("cc", "C++");
		labels.put("C", "C++");
		labels.put("cpp", "C++");
		labels.put("exe", "BIN");
		labels.put("class", "BIN");
		labels.put("gif", "GIF");
		labels.put("GIF", "GIF");
		labels.put("", "");
	}
}
