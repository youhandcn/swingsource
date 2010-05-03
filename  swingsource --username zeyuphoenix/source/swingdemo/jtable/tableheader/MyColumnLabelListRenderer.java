package jtable.tableheader;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;

/**
 * a component that has been configured to display the specified value.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyColumnLabelListRenderer.java <br>
 *         2008 2008/03/27 13:14:47 <br>
 */
public class MyColumnLabelListRenderer extends JLabel implements
		ListCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * a component that has been configured to display the specified value.
	 * 
	 * @param table
	 *            list table.
	 * @param list
	 *            the list .
	 * @param cellWidth
	 *            cell width.
	 */
	MyColumnLabelListRenderer(JTable table, JList list, int cellWidth) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());

		list.setFixedCellWidth(cellWidth);
		list.setFont(table.getTableHeader().getFont());
		list.setForeground(table.getTableHeader().getForeground());
		list.setBackground(table.getTableHeader().getBackground());
		list
				.setFixedCellHeight(table.getRowHeight() + table.getRowMargin()
						- 1);

	}

	/**
	 * a component that has been configured to display the specified value.
	 * 
	 * @param table
	 *            list table.
	 * @param list
	 *            the list .
	 */
	MyColumnLabelListRenderer(JTable table, JList list) {
		this(table, list, 100);
	}

	/**
	 * Return a component that has been configured to display the specified
	 * value.
	 * 
	 * @param list
	 *            The JList we're painting.
	 * @param value
	 *            The value returned by list.getModel().getElementAt(index).
	 * @param index
	 *            The cells index.
	 * @param isSelected
	 *            True if the specified cell was selected.
	 * @param cellHasFocus
	 *            True if the specified cell has the focus.
	 * @return A component whose paint() method will render the specified value.
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());
		return this;
	}

}
