package jtable.tableheadertest;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.MyHeaderButtonRenderer;
import jtable.tableheader.MyTableModel;
import jtable.tableheader.MyTableSorter;

public class TestMyTableSorter extends JPanel {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	public MyTableSorter tt = null;

	public TestMyTableSorter() {
		setLayout(new BorderLayout());
		String[] headerStr = { "Name", "Date", "Size", "Dir" };
		int[] columnWidth = { 100, 150, 100, 50 };

		MyTableModel dm = new MyTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<?> getColumnClass(int col) {
				switch (col) {
				case 0:
					return String.class;
				case 1:
					return Date.class;
				case 2:
					return String.class;
				case 3:
					return Boolean.class;
				default:
					return Object.class;
				}
			}

			public boolean isCellEditable(int row, int col) {
				switch (col) {
				case 1:
					return false;
				default:
					return true;
				}
			}

			public void setValueAt(Object obj, int row, int col) {
				switch (col) {
				case 2:
//					super.setValueAt(new Integer(obj.toString()), row, col);
					super.setValueAt(obj, row, col);
					return;
				default:
					super.setValueAt(obj, row, col);
					return;
				}
			}
		};
//		dm
//				.setDataVector(
//						new Object[][] {
//								{ "b", getDate("98/12/02"), new Integer(14),
//										new Boolean(false) },
//								{ "a", getDate("99/01/01"), new Integer(67),
//										new Boolean(false) },
//								{ "d", getDate("99/02/11"), new Integer(2),
//										new Boolean(false) },
//								{ "c", getDate("99/02/27"), new Integer(7),
//										new Boolean(false) },
//								{ "foo", new Date(), new Integer(5),
//										new Boolean(true) },
//								{ "bar", new Date(), new Integer(10),
//										new Boolean(true) } }, headerStr);
		dm
		.setDataVector(
				new Object[][] {
						{ "b", getDate("98/12/02"), "14:12:00",
								new Boolean(false) },
						{ "a", getDate("99/01/01"), "0721:00:00",
								new Boolean(false) },
						{ "d", getDate("99/02/11"), "",
								new Boolean(false) },
						{ "c", getDate("99/02/27"), "",
								new Boolean(false) },
						{ "foo", new Date(), "25:00:11",
								new Boolean(true) },
						{ "bar", new Date(), "25:00:11",
								new Boolean(true) } }, headerStr);

		JTable table = new JTable(dm);

		MyHeaderButtonRenderer renderer = new MyHeaderButtonRenderer();
		TableColumnModel model = table.getColumnModel();
		int n = headerStr.length;
		for (int i = 0; i < n; i++) {
			model.getColumn(i).setHeaderRenderer(renderer);
			model.getColumn(i).setPreferredWidth(columnWidth[i]);
		}
		tt = new MyTableSorter(dm, table);
////////////////////////////////////////////////////////////////////////////////
///add for sorter///////////////////////////////////////////////////////////////
		tt.setComparator(2, new MyComparator<String>());
////////////////////////////////////////////////////////////////////////////////
		table.setRowSorter(tt);

		JTableHeader header = table.getTableHeader();
		header
				.addMouseListener(new MyHeaderButtonRenderer(header, renderer,
						tt));
		JScrollPane pane = new JScrollPane(table);
		add(pane, BorderLayout.CENTER);
	}
////////////////////////////////////////////////////////////////////////////////
///add for sorter///////////////////////////////////////////////////////////////
	private class MyComparator<T> implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {

			if (o1 == o2) {
				return 0;
			} else {
				if (isFormatOK(o1) && isFormatOK(o2)) {
					List<Integer> list1 = changeFormat(o1);
					List<Integer> list2 = changeFormat(o2);
					if (list1.get(0) == list2.get(0)) {
						if (list1.get(1) == list2.get(1)) {
							if (list1.get(2) == list2.get(2)) {
								return 0;
							} else {
								return list1.get(2) - list2.get(2);
							}
						} else {
							return list1.get(1) - list2.get(1);
						}

					} else {
						return list1.get(0) - list2.get(0);
					}

				} else if (isFormatOK(o1)) {
					return 1;
				} else if (isFormatOK(o2)) {
					return -1;
				}
				return 0;
			}
		}

		private boolean isNumeric(String str) {
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(str);
			if (!isNum.matches()) {
				return false;
			}
			return true;
		}

		private boolean isFormatOK(String str) {

			if (str == null || str.isEmpty()) {
				return false;
			}
			if (str.split(":").length > 2) {

				for (String info : str.split(":")) {
					if (!isNumeric(info)) {
						return false;
					}
				}

				return true;
			}

			return false;
		}

		private List<Integer> changeFormat(String str) {
			List<Integer> list = new ArrayList<Integer>();

			list.add(Integer.valueOf(str.split(":")[0]));
			list.add(Integer.valueOf(str.split(":")[1]));
			list.add(Integer.valueOf(str.split(":")[2]));

			return list;
		}
	}
////////////////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		JFrame f = new JFrame("SortableTable Example");
		f.getContentPane().add(new TestMyTableSorter(), BorderLayout.CENTER);
		f.setSize(400, 160);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private static DateFormat dateFormat = DateFormat.getDateInstance(
			DateFormat.SHORT, Locale.JAPAN);

	private static Date getDate(String dateString) {
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
		} catch (ParseException ex) {
			date = new Date();
		}
		return date;
	}
}
