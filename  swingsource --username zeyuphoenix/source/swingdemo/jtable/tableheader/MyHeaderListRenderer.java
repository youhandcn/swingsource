package jtable.tableheader;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyHeaderListRenderer.java <br>
 *         2008 2008/03/27 13:49:47 <br>
 */
public class MyHeaderListRenderer extends JList implements TableCellRenderer {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** split flag. */
	private String splitFlag = null;

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 */
	public MyHeaderListRenderer() {
		this(null);
	}

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 * 
	 * @param split
	 *            the list split flag.
	 */
	public MyHeaderListRenderer(String split) {
		splitFlag = split;
		setOpaque(true);
		setForeground(UIManager.getColor("TableHeader.foreground"));
		setBackground(UIManager.getColor("TableHeader.background"));
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		ListCellRenderer renderer = getCellRenderer();
		((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
		setCellRenderer(renderer);
	}

	/**
	 * Returns the component used for drawing the cell. This method is used to
	 * configure the renderer appropriately before drawing.
	 * 
	 * @param table
	 *            the <code>JTable</code> that is asking the renderer to draw;
	 *            can be <code>null</code>
	 * @param value
	 *            the value of the cell to be rendered. It is up to the specific
	 *            renderer to interpret and draw the value. For example, if
	 *            <code>value</code> is the string "true", it could be
	 *            rendered as a string or it could be rendered as a check box
	 *            that is checked. <code>null</code> is a valid value
	 * @param isSelected
	 *            true if the cell is to be rendered with the selection
	 *            highlighted; otherwise false
	 * @param hasFocus
	 *            if true, render cell appropriately. For example, put a special
	 *            border on the cell, if the cell can be edited, render in the
	 *            color used to indicate editing
	 * @param row
	 *            the row index of the cell being drawn. When drawing the
	 *            header, the value of <code>row</code> is -1
	 * @param column
	 *            the column index of the cell being drawn
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setFont(table.getFont());
		String str = (value == null) ? "" : value.toString();
		BufferedReader br = new BufferedReader(new StringReader(str));
		String line = null;
		Vector<String> v = null;
		try {
			if (splitFlag == null) {
				v = new Vector<String>();
				while ((line = br.readLine()) != null) {
					v.addElement(line);
				}
			} else {
				v = convertToVector(str.split(splitFlag));
			}

		} catch (IOException ex) {
		}
		setListData(v);
		return this;
	}

	/**
	 * Returns a vector that contains the same objects as the array.
	 * 
	 * @param anArray
	 *            the array to be converted
	 * @return the new vector; if <code>anArray</code> is <code>null</code>,
	 *         returns <code>null</code>
	 */
	private static Vector<String> convertToVector(String[] anArray) {
		if (anArray == null) {
			return null;
		}
		Vector<String> v = new Vector<String>(anArray.length);
		for (int i = 0; i < anArray.length; i++) {
			v.addElement(anArray[i]);
		}
		return v;
	}
}
