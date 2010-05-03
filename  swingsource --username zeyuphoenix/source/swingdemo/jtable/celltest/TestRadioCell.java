package jtable.celltest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jtable.cell.MyRadioCellEditor;
import jtable.cell.MyRadioCellRenderer;

/**
 * the class is for test radio cell table.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell TestRadioCell.java <br>
 *         2008 2008/04/01 10:32:18 <br>
 */
public class TestRadioCell extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the class is for test radio cell table.
	 */
	public TestRadioCell() {
		super("JRadioCellTable");
		setSize(400, 300);
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "1", new Integer(-1) },
				{ "2", new Integer(-1) }, { "3", new Integer(0) },
				{ "4", new Integer(1) }, { "5", new Integer(2) } },
				new Object[] { "Question", "Answer" });

		JTable table = new JTable(dm);
		String[] answer = { "A", "B", "C" };

		table.getColumn("Answer").setCellRenderer(
				new MyRadioCellRenderer(answer));
		table.getColumn("Answer").setCellEditor(
				new MyRadioCellEditor(new MyRadioCellRenderer(answer)));
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestRadioCell frame = new TestRadioCell();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(400, 300);
		frame.setVisible(true);
	}
}
