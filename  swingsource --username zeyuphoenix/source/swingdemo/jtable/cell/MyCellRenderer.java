package jtable.cell;

import java.awt.Component;
import java.util.Hashtable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyCellRenderer.java <br>
 *         2008 2008/04/01 13:05:16 <br>
 */
public class MyCellRenderer implements TableCellRenderer {
	/** save all render in it. */
	private Hashtable<Integer, TableCellRenderer> renderers;

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 */
	public MyCellRenderer() {
		renderers = new Hashtable<Integer, TableCellRenderer>();
	}

	/**
	 * add row render to it.
	 * 
	 * @param row
	 *            row index.
	 * @param renderer
	 *            each row renderer.
	 */
	public void add(int row, TableCellRenderer renderer) {
		renderers.put(new Integer(row), renderer);
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
	 * 
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		TableCellRenderer renderer = (TableCellRenderer) renderers
				.get(new Integer(row));
		if (renderer == null) {
			renderer = new DefaultTableCellRenderer();
		}
		return renderer.getTableCellRendererComponent(table, value, isSelected,
				hasFocus, row, column);
	}
}
