package jtable.tableheadertest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyHeaderComboxRenderer;
import jtable.tableheader.MyTableColumn;
import jtable.tableheader.MyTableHeader;

public class TestComboxHeaderTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestComboxHeaderTable() {
		super("EditableHeader Example");
		setSize(300, 200);
		JTable table = new JTable(7, 5);
		TableColumnModel columnModel = table.getColumnModel();
		table.setTableHeader(new MyTableHeader(columnModel));

		String[] items = { "Dog", "Cat" };
		JComboBox combo = new JComboBox();
		for (int i = 0; i < items.length; i++) {
			combo.addItem(items[i]);
		}
		TableCellRenderer renderer = new MyHeaderComboxRenderer(items);
		MyTableColumn col;
		// column 1
		col = (MyTableColumn) table.getColumnModel().getColumn(1);
		col.setHeaderValue(combo.getItemAt(0));
		col.setHeaderRenderer(renderer);
		col.setHeaderEditor(new DefaultCellEditor(combo));

		// column 3
		col = (MyTableColumn) table.getColumnModel().getColumn(3);
		col.setHeaderValue(combo.getItemAt(0));
		col.setHeaderRenderer(renderer);
		col.setHeaderEditor(new DefaultCellEditor(combo));

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			model.getColumn(i).setHeaderRenderer(renderer);
		}
		table.getTableHeader().setUI(new MyGroupTableHeaderUI());

		JScrollPane pane = new JScrollPane(table);
		getContentPane().add(pane);
	}

	public static void main(String[] args) {
		TestComboxHeaderTable frame = new TestComboxHeaderTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setSize(300, 100);
		frame.setVisible(true);
	}
}
