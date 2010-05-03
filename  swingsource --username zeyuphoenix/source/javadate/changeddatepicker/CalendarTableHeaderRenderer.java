package changeddatepicker;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * The standard class for rendering (displaying) individual cells in a
 * <code>JTable</code>.
 * 
 * @author shuai.zhang
 */
public class CalendarTableHeaderRenderer extends DefaultTableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Returns the table cell renderer.
	 * 
	 * @param table
	 *            the <code>JTable</code>
	 * @param value
	 *            the value to assign to the cell at <code>[row, column]</code>
	 * @param isSelected
	 *            true if cell is selected
	 * @param hasFocus
	 *            true if cell has focus
	 * @param row
	 *            the row of the cell to render
	 * @param column
	 *            the column of the cell to render
	 * @return the default table cell renderer
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JLabel label = (JLabel) super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		label.setHorizontalAlignment(JLabel.CENTER);

		// set table header.
		if (row == -1) {

			label.setForeground(Color.black);
			label.setBackground(Color.orange);
			label.setHorizontalAlignment(JLabel.CENTER);
			return label;
		}
		return null;
	}
}
