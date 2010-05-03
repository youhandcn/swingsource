package jtabletest;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;

public class TestColumn extends JFrame {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	Object[][] data;
	Object[] column;
	JTable fixedTable, table;

	public TestColumn() {
		super("Fixed Column Example");
		setSize(400, 200);

		data = new Object[][] { { "1", "11", "A", "", "", "", "", "" },
				{ "2", "22", "", "B", "", "", "", "" },
				{ "3", "33", "", "", "C", "", "", "" },
				{ "4", "44", "", "", "", "D", "", "" },
				{ "5", "55", "", "", "", "", "E", "" },
				{ "6", "66", "", "", "", "", "", "F" } };
		column = new Object[] { "fixed 1", "fixed 2", "a", "b", "c", "d", "e",
				"f" };
		// create a simple table that has simple layout.
		AbstractTableModel fixedModel = new AbstractTableModel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return 2;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return (String) column[col];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col];
			}
		};
		// create a simple table that has two column are short.
		AbstractTableModel model = new AbstractTableModel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public int getColumnCount() {
				return column.length - 2;
			}

			public int getRowCount() {
				return data.length;
			}

			public String getColumnName(int col) {
				return (String) column[col + 2];
			}

			public Object getValueAt(int row, int col) {
				return data[row][col + 2];
			}

			public void setValueAt(Object obj, int row, int col) {
				data[row][col + 2] = obj;
			}
		};

		fixedTable = new JTable(fixedModel) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * value change action.
			 */
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				checkSelection(true);
			}
		};
		table = new JTable(model) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * value change action.
			 */
			public void valueChanged(ListSelectionEvent e) {
				super.valueChanged(e);
				checkSelection(false);
			}
		};
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scroll = new JScrollPane(table);
		JViewport viewport = new JViewport();
		viewport.setView(fixedTable);
		viewport.setPreferredSize(fixedTable.getPreferredSize());
		scroll.setRowHeaderView(viewport);
		scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable
				.getTableHeader());

		getContentPane().add(scroll, BorderLayout.CENTER);
	}

	/**
	 * when is fix table, the select is change,the table UI change.
	 * 
	 * @param isFixedTable
	 *            is FixedTable
	 */
	private void checkSelection(boolean isFixedTable) {
		int fixedSelectedIndex = fixedTable.getSelectedRow();
		int selectedIndex = table.getSelectedRow();
		if (fixedSelectedIndex != selectedIndex) {
			if (isFixedTable) {
				table.setRowSelectionInterval(fixedSelectedIndex,
						fixedSelectedIndex);
			} else {
				fixedTable
						.setRowSelectionInterval(selectedIndex, selectedIndex);
			}
		}
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestColumn frame = new TestColumn();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
