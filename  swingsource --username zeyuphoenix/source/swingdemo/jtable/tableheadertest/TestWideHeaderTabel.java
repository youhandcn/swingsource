package jtable.tableheadertest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.ColumnGroup;
import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyTableHeader;

public class TestWideHeaderTabel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestWideHeaderTabel() {
		super("Multi-Width Header Example");

		DefaultTableModel dm = new DefaultTableModel();
		dm.setDataVector(new Object[][] { { "a", "b", "c", "d", "e", "f" },
				{ "A", "B", "C", "D", "E", "F" } }, new Object[] { "", "", "",
				"", "", "" });

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
		ColumnGroup g_1st = new ColumnGroup("1 st");
		g_1st.add(cm.getColumn(0));
		ColumnGroup g_2nd = new ColumnGroup("2 nd");
		g_2nd.add(cm.getColumn(1));
		g_2nd.add(cm.getColumn(2));
		ColumnGroup g_3rd = new ColumnGroup("3 rd");
		g_3rd.add(cm.getColumn(3));
		g_3rd.add(cm.getColumn(4));
		g_3rd.add(cm.getColumn(5));
		MyTableHeader header = (MyTableHeader) table.getTableHeader();
		header.addColumnGroup(g_1st);
		header.addColumnGroup(g_2nd);
		header.addColumnGroup(g_3rd);

		TableCellRenderer renderer = new DefaultTableCellRenderer();

		// TableCellRenderer renderer1 = new MyHeaderButtonRenderer();

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			model.getColumn(i).setHeaderRenderer(renderer);
		}
		table.getTableHeader().setUI(new MyGroupTableHeaderUI());

		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(400, 200);
		header.revalidate();
	}

	public static void main(String[] args) {
		TestWideHeaderTabel frame = new TestWideHeaderTabel();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
