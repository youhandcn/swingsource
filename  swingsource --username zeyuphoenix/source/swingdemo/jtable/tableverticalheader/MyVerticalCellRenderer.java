package jtable.tableverticalheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableverticalheader MyVerticalCellRenderer.java <br>
 *         2008 2008/03/31 17:25:05 <br>
 */
public class MyVerticalCellRenderer extends JLabel implements TableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** make the cell no border. */
	private static Border noFocusBorder;

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 */
	public MyVerticalCellRenderer() {
		noFocusBorder = new EmptyBorder(1, 2, 1, 2);
		setOpaque(true);
		setBorder(noFocusBorder);
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
		Color foreground = null;
		Color background = null;
		Font font = null;
		TableModel model = table.getModel();
		if (model instanceof MyVerticalHeaderModel) {
			MyCellAttribute cellAtt = ((MyVerticalHeaderModel) model)
					.getCellAttribute();
			if (cellAtt instanceof MyCellColor) {
				foreground = ((MyCellColor) cellAtt).getForeground(row, column);
				background = ((MyCellColor) cellAtt).getBackground(row, column);
			}
			if (cellAtt instanceof MyCellFont) {
				font = ((MyCellFont) cellAtt).getFont(row, column);
			}
		}
		if (isSelected) {
			setForeground((foreground != null) ? foreground : table
					.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground((foreground != null) ? foreground : table
					.getForeground());
			setBackground((background != null) ? background : table
					.getBackground());
		}
		setFont((font != null) ? font : table.getFont());

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			if (table.isCellEditable(row, column)) {
				setForeground((foreground != null) ? foreground : UIManager
						.getColor("Table.focusCellForeground"));
				setBackground(UIManager.getColor("Table.focusCellBackground"));
			}
		} else {
			setBorder(noFocusBorder);
		}
		setValue(value);
		return this;
	}

	/**
	 * set the cell value.
	 * 
	 * @param value
	 *            cell value.
	 */
	protected void setValue(Object value) {
		setText((value == null) ? "" : value.toString());
	}
}
