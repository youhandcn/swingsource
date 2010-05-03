package jtable.tableheadertest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyHeaderButtonRenderer;
import jtable.tableheader.MyTableHeader;

public class TestEditButtonHeaderTable extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	public TestEditButtonHeaderTable() {
		super("EditableHeader Example");
		setSize(300, 200);
		JTable table = new JTable(7, 5);
		TableColumnModel columnModel = table.getColumnModel();
		table.setTableHeader(new MyTableHeader(columnModel));

		TableCellRenderer renderer = new MyHeaderButtonRenderer();
		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			model.getColumn(i).setHeaderRenderer(renderer);
		}
		table.getTableHeader().setUI(new MyGroupTableHeaderUI());

		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
	}

	public static void main(String[] args) {
		TestEditButtonHeaderTable frame = new TestEditButtonHeaderTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
}
