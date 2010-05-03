package work;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 */
public class MyCheckBoxRenderer extends JCheckBox implements TableCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This interface defines the method required by any object that would like
	 * to be a renderer for cells in a <code>JTable</code>.
	 */
	public MyCheckBoxRenderer() {
		setHorizontalAlignment(JLabel.CENTER);
	}

	/**
	 * Returns the component used for drawing the cell. This method is used to
	 * configure the renderer appropriately before drawing.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		setSelected((value != null && ((Boolean) value).booleanValue()));

		return this;
	}
}
