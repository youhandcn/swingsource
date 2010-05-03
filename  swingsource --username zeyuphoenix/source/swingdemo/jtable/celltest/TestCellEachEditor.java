package jtable.celltest;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jcombobox.MyComboBox;
import jtable.cell.MyCellEditor;

/**
 * test cell editor each.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell TestCellEachEditor.java <br>
 *         2008 2008/04/01 13:35:18 <br>
 */
public class TestCellEachEditor extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	public TestCellEachEditor() {
		super("SmallCell Combo Example");
		setSize(500, 300);
		DefaultTableModel dm = new DefaultTableModel(4, 10) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public void setValueAt(Object obj, int row, int col) {
				if (obj != null) {
					String str = null;
					if (obj instanceof String) {
						if (((String) obj).length() > 2) {
							str = ((String) obj).substring(0, 2);
						} else {
							str = (String) obj;
						}
					} else {
						str = obj.toString();
					}
					super.setValueAt(str, row, col);
				}
			}
		};
		JTable table = new JTable(dm);

		String[] str = { "010 - To Time", "020 - Vacation", "030 - Feel Bad" };

		MyComboBox combo = new MyComboBox(str);
		Dimension d = combo.getPreferredSize();
		combo.setPopupWidth(d.width);
		MyCellEditor rowEditor = new MyCellEditor(table);
		for (int i = 0; i < table.getColumnCount(); i++) {
			for (int j = 0; j < table.getRowCount(); j++) {
				if (i == j) {
					rowEditor.setEditorAt(i, new DefaultCellEditor(combo));
					table.getColumnModel().getColumn(i)
							.setCellEditor(rowEditor);
				}
			}
		}
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestCellEachEditor frame = new TestCellEachEditor();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(300, 120);
		frame.setVisible(true);
	}
}
