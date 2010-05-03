package jtable.tableheadertest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.ColumnGroup;
import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyHeaderButtonRenderer;
import jtable.tableheader.MyTableHeader;

public class TestGroupHeaderTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestGroupHeaderTable() {
		super("Groupable Header Example");
		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] {
				{ "119", "foo", "bar", "ja", "ko", "zh" },
				{ "911", "bar", "foo", "en", "fr", "pt" } }, new Object[] {
				"SNo.", "1", "2", "Native", "2", "3" });
		JTable table = new JTable(dm) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected JTableHeader createDefaultTableHeader() {
				return new MyTableHeader(columnModel);
			}
		};
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Name");
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		ColumnGroup g_lang = new ColumnGroup("Language");
		g_lang.add(cm.getColumn(3));
		ColumnGroup g_other = new ColumnGroup("Others");
		g_other.add(cm.getColumn(4));
		g_other.add(cm.getColumn(5));
		g_lang.add(g_other);
		MyTableHeader header = (MyTableHeader) table
				.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);
		
		TableCellRenderer renderer = new MyHeaderButtonRenderer();
		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			model.getColumn(i).setHeaderRenderer(renderer);
		}
		table.getTableHeader().setUI(new MyGroupTableHeaderUI());
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(600, 300);
	}

	public static void main(String[] args) {
		TestGroupHeaderTable frame = new TestGroupHeaderTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
