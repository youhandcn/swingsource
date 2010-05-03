package jtabletest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTable;

import jtable.MyScrollPane;

/**
 * test hide column.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable HideColumnTableExample.java <br>
 *         2008 2008/03/31 17:05:04 <br>
 */
public class TestHideColumnTable extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * test hide column.
	 */
	public TestHideColumnTable() {
		super("HideColumnTable Example");
		setSize(400, 200);
		JTable table = new JTable(5, 7);
		MyScrollPane pane = new MyScrollPane(table);
		getContentPane().add(pane);
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestHideColumnTable frame = new TestHideColumnTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(400, 100);
		frame.setVisible(true);
	}
}
