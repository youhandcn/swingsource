package jtable.tableheadertest;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalIconFactory;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import jlist.MyListBoxRenderer;
import jlist.MyListItem;

class RowHeaderRenderer extends JButton implements ListCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	RowHeaderRenderer(JTable table, JList list) {
		JTableHeader header = table.getTableHeader();
		setOpaque(true);
		setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		setHorizontalAlignment(CENTER);
		setForeground(header.getForeground());
		setBackground(header.getBackground());
		setFont(header.getFont());

		list.setFixedCellWidth(100);
		list.setFont(table.getTableHeader().getFont());
		list.setForeground(table.getTableHeader().getForeground());
		list.setBackground(table.getTableHeader().getBackground());
		list
				.setFixedCellHeight(table.getRowHeight() + table.getRowMargin()
						- 1);
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		setText((value == null) ? "" : value.toString());
		return this;
	}
}

public class TestListTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TestListTable() {
		super("Row Header Example");
		setSize(300, 200);

		ListModel lm = new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Icon icon = MetalIconFactory.getFileChooserHomeFolderIcon();
			Object[] headers = { new MyListItem("Astart"),
					new MyListItem("B", true, icon),
					new MyListItem("1sssssss", false),
					new MyListItem("2", false), new MyListItem("abc", true),
					new MyListItem("2", false, icon), new MyListItem("2sssww"),
					new MyListItem("def", false, null) };

			// String headers[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i"};
			public int getSize() {
				return headers.length;
			}

			public Object getElementAt(int index) {
				return headers[index];
			}
		};

		ListModel lm1 = new AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 5162130645056381614L;
			String headers[] = { "a", "b", "c", "d", "e", "f", "g", "h" };

			public int getSize() {
				return headers.length;
			}

			public Object getElementAt(int index) {
				return headers[index];
			}
		};

		DefaultTableModel dm = new DefaultTableModel(lm.getSize(), 10);
		JTable table = new JTable(dm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JList rowHeader = new JList(lm);
		rowHeader.setFixedCellWidth(100);

		rowHeader.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		rowHeader.setForeground(table.getTableHeader().getForeground());
		rowHeader.setBackground(table.getTableHeader().getBackground());
		rowHeader.setFont(table.getTableHeader().getFont());

		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin() - WIDTH);
		rowHeader.setCellRenderer(new MyListBoxRenderer(rowHeader));
		rowHeader.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rowHeader.addMouseListener(new MyListBoxRenderer(rowHeader));

		JList rowHeader1 = new JList(lm1);
		rowHeader1.setCellRenderer(new RowHeaderRenderer(table, rowHeader1));

		JScrollPane scroll = new JScrollPane(table);
		// scroll.setRowHeaderView(rowHeader);
		scroll.setRowHeaderView(rowHeader1);
		getContentPane().add(scroll, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		TestListTable frame = new TestListTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
