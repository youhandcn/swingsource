package jtable.cell;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>. <br>
 * in there, I put progress bar in it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyProgressCellRenderer.java <br>
 *         2008 2008/03/31 18:04:08 <br>
 */
public class MyProgressCellRenderer extends JProgressBar implements
		TableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** the progress bar's color. */
	private Hashtable<?, ?> limitColors = null;
	/** cell with progress bar's value. */
	private int[] limitValues = null;

	/**
	 * Creates a progress bar using simple.
	 */
	public MyProgressCellRenderer() {
		super(JProgressBar.HORIZONTAL);
		setBorderPainted(false);
	}

	/**
	 * Creates a progress bar using the specified orientation, minimum, and
	 * maximum.
	 * 
	 * @param min
	 *            the minimum value of the progress bar
	 * @param max
	 *            the maximum value of the progress bar
	 */
	public MyProgressCellRenderer(int min, int max) {
		super(JProgressBar.HORIZONTAL, min, max);
		setBorderPainted(false);
	}

	/**
	 * Returns the component used for drawing the cell. This method is used to
	 * configure the renderer appropriately before drawing.
	 * 
	 * @param table
	 *            the <code>JTable</code> that is asking the renderer to draw;
	 *            can be <code>null</code>
	 * @param value
	 *            the value of the cell to be rendered.
	 * @param isSelected
	 *            true if the cell is to be rendered with the selection
	 *            highlighted; otherwise false
	 * @param hasFocus
	 *            if true, render cell appropriately.
	 * @param row
	 *            the row index of the cell being drawn.
	 * @param column
	 *            the column index of the cell being drawn
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		int n = 0;
		if (!(value instanceof Number)) {
			String str;
			if (value instanceof String) {
				str = (String) value;
			} else {
				str = value.toString();
			}
			try {
				n = Integer.valueOf(str).intValue();
			} catch (NumberFormatException ex) {
			}
		} else {
			n = ((Number) value).intValue();
		}
		Color color = getColor(n);
		if (color != null) {
			setForeground(color);
		}
		setValue(n);
		return this;
	}

	/**
	 * set the cell color and sort.
	 * 
	 * @param limitColors
	 *            value and color.
	 */
	public void setLimits(Hashtable<?, ?> limitColors) {
		this.limitColors = limitColors;
		int i = 0;
		int n = limitColors.size();
		limitValues = new int[n];
		Enumeration<?> enumeration = limitColors.keys();
		while (enumeration.hasMoreElements()) {
			limitValues[i++] = ((Integer) enumeration.nextElement()).intValue();
		}
		sort(limitValues);
	}

	/**
	 * get the progress bar's color.
	 * 
	 * @param value
	 *            progress bar value.
	 * @return the value's color.
	 */
	private Color getColor(int value) {
		Color color = null;
		if (limitValues != null) {
			int i = 0;
			for (i = 0; i < limitValues.length; i++) {
				if (limitValues[i] < value) {
					color = (Color) limitColors
							.get(new Integer(limitValues[i]));
				}
			}
		}
		return color;
	}

	/**
	 * sort the progress bar by it's value.
	 * 
	 * @param a
	 *            it's value.
	 */
	private void sort(int[] a) {
		int n = a.length;
		for (int i = 0; i < n - 1; i++) {
			int k = i;
			for (int j = i + 1; j < n; j++) {
				if (a[j] < a[k]) {
					k = j;
				}
			}
			int tmp = a[i];
			a[i] = a[k];
			a[k] = tmp;
		}
	}
}
