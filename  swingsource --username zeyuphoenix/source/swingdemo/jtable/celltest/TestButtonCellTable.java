package jtable.celltest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jtable.cell.MyButtonCellEditor;
import jtable.cell.MyButtonRenderer;

/**
 * test button cell.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell TestButtonCellTable.java <br>
 *         2008 2008/04/01 10:42:26 <br>
 */
public class TestButtonCellTable extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * test button cell.
	 */
	public TestButtonCellTable() {
		super("JButtonTable Example");

		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "button 1", "foo" },
				{ "button 2", "bar" } }, new Object[] { "Button", "String" });

		JTable table = new JTable(dm);
		table.getColumn("Button").setCellRenderer(new MyButtonRenderer());
		table.getColumn("Button").setCellEditor(new MyButtonCellEditor());
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(400, 200);
		setVisible(true);
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestButtonCellTable frame = new TestButtonCellTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
