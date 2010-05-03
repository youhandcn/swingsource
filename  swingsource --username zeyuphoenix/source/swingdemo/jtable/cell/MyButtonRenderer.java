package jtable.cell;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>. <br>
 * in there, I put button in it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyButtonRenderer.java <br>
 *         2008 2008/03/31 18:14:40 <br>
 */
public class MyButtonRenderer extends JButton implements TableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a JButton using simple.
	 */
	public MyButtonRenderer() {
		setOpaque(true);
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
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(UIManager.getColor("Button.background"));
		}
		setText((value == null) ? "" : value.toString());
		return this;
	}
}
