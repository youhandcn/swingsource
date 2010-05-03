package jtable.tableheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyHeaderComboxRenderer.java <br>
 *         2008 2008/03/28 15:03:22 <br>
 */
public class MyHeaderComboxRenderer extends JComboBox implements
		TableCellRenderer, MouseListener {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/** table header height. */
	private int headerHeight = 18;

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 * 
	 * @param items
	 *            combox item.
	 */
	public MyHeaderComboxRenderer(String[] items) {
		for (int i = 0; i < items.length; i++) {
			addItem(items[i]);
		}
	}

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 * 
	 * @param items
	 *            combox item.
	 */
	public MyHeaderComboxRenderer(Vector<String> items) {
		for (int i = 0; i < items.size(); i++) {
			addItem(items.get(i));
		}
	}

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 * 
	 * @param items
	 *            combox item.
	 */
	public MyHeaderComboxRenderer(ArrayList<String> items) {
		for (int i = 0; i < items.size(); i++) {
			addItem(items.get(i));
		}
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

		JComboBox combox = this;
		// set some color font about header.
		JTableHeader header = table.getTableHeader();
		if (header != null) {
			Color fgColor = null;
			Color bgColor = null;
			if (hasFocus) {
				fgColor = UIManager.getColor("TableHeader.focusCellForeground");
				bgColor = UIManager.getColor("TableHeader.focusCellBackground");
			}
			if (fgColor == null) {
				fgColor = header.getForeground();
			}
			if (bgColor == null) {
				bgColor = header.getBackground();
			}
			setForeground(fgColor);
			setBackground(bgColor);

			setFont(header.getFont());
		}
		// set height.
		if (headerHeight != 0) {
			setPreferredSize(new Dimension(getWidth(), headerHeight));
		} else {
			setPreferredSize(new Dimension(getWidth(), getHeight()));
		}

		Border border = null;
		if (hasFocus) {
			border = UIManager.getBorder("TableHeader.focusCellBorder");
		}
		if (border == null) {
			border = UIManager.getBorder("TableHeader.cellBorder");
		}
		setBorder(border);

		combox.setSelectedItem((value == null) ? "" : value.toString());

		return combox;
	}

	/**
	 * set table header height.
	 * 
	 * @param headerheight
	 *            table header height.
	 */
	public void setHeight(int headerheight) {
		headerHeight = headerheight;
	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 */
	public void mouseClicked(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 */
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 */
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 */
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 */
	public void mouseReleased(MouseEvent e) {
	}

}
