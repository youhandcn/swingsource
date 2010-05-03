package jtable.tableheadertest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import jtable.tableheader.MyHeaderListRenderer;

public class TestHightHeaderTabel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestHightHeaderTabel() {
		super("Multi-Line Header Example");

		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(
				new Object[][] { { "a", "b", "c" }, { "A", "B", "C" } },
				new Object[] { "1st\nalpha", "2nd\nbeta", "3rd\ngamma" });

		JTable table = new JTable(dm);
		MyHeaderListRenderer renderer = new MyHeaderListRenderer();
		Enumeration<?> enumTemp = table.getColumnModel().getColumns();
		while (enumTemp.hasMoreElements()) {
			((TableColumn) enumTemp.nextElement()).setHeaderRenderer(renderer);
		}
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(400, 250);
		setVisible(true);
	}

	public static void main(String[] args) {
		TestHightHeaderTabel frame = new TestHightHeaderTabel();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}