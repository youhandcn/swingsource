package jtable.tableverticalheadertest;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.ColumnGroup;
import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyHeaderButtonRenderer;
import jtable.tableheader.MyTableHeader;
import jtable.tableverticalheader.MyCellSpan;
import jtable.tableverticalheader.MyVerticalHeaderModel;
import jtable.tableverticalheader.MyVerticalHeaderTabel;

/**
 * create a table with group model and vertical model.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableverticalheadertest MultipleRowHeaderExample.java <br>
 *         2008 2008/03/31 10:34:08 <br>
 */
public class TestMyVerticalTable extends JFrame {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** table data */
	private Object[][] data = null;
	/** table title */
	private Object[] column = null;
	/** group table. */
	private JTable table = null;
	/** vertical model. */
	private MyVerticalHeaderTabel fixedTable = null;

	/**
	 * create a table with group model and vertical model.
	 */
	/**
	 * 
	 */
	public TestMyVerticalTable() {
		super("Multiple Row Header Example");
		setSize(1000, 600);

		data = new Object[][] { { "Class", "" }, { "Name", "1", "zeyu" },
				{ "", "2" }, { "sort", "1" }, { "", "2", "english" },
				{ "", "3" }, { "Start", "one" }, { "", "two", "1" },
				{ "", "", "2" }, { "", "three" }, { "trace", "" },
				{ "end", "all", "1" }, { "", "", "2" }, { "", "", "3" },
				{ "", "", "4" }, { "finally", "one" }, { "", "two" },
				{ "", "three", "1" }, { "", "", "2" }, { "game over", "" } };
		column = new Object[] { "1", "2", "3" };

		MyVerticalHeaderModel fixedModel = new MyVerticalHeaderModel(data,
				column) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		MyCellSpan cellAtt = (MyCellSpan) fixedModel.getCellAttribute();
		cellAtt.combine(new int[] { 0 }, new int[] { 0, 1, 2 });
		cellAtt.combine(new int[] { 1, 2 }, new int[] { 0 });
		cellAtt.combine(new int[] { 3, 4, 5 }, new int[] { 0 });
		cellAtt.combine(new int[] { 6, 7, 8, 9 }, new int[] { 0 });
		cellAtt.combine(new int[] { 15, 16, 17, 18 }, new int[] { 0 });
		cellAtt.combine(new int[] { 10 }, new int[] { 0, 1, 2 });
		cellAtt.combine(new int[] { 19 }, new int[] { 0, 1, 2 });
		cellAtt.combine(new int[] { 11, 12, 13, 14 }, new int[] { 0, 1 });
		cellAtt.combine(new int[] { 2 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 3 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 5 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 6 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 9 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 15 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 16 }, new int[] { 1, 2 });
		cellAtt.combine(new int[] { 7, 8 }, new int[] { 1 });
		cellAtt.combine(new int[] { 17, 18 }, new int[] { 1 });

		DefaultTableModel dm = new DefaultTableModel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		dm.setDataVector(new Object[][] { { "1", "sd", "sd", "ww", "ww", "e" },
				{ "2", "", "r", "ja", "", "r" }, { "3", "", "", "", "", "" },
				{ "4", "", "", "", "ko", "" },
				{ "5", "", "e", "ja", "", "zh" },
				{ "6", "33", "rwe", "en", "r", "pt" },
				{ "7", "", "", "g", "", "" }, { "14", "", "", "", "", "g" },
				{ "8", "", "", "", "", "g" }, { "15", "", "g", "", "", "" },
				{ "9", "", "g", "", "", "" }, { "16", "", "", "", "g", "" },
				{ "10", "", "", "", "g", "" }, { "17", "", "", "g", "", "" },
				{ "11", "", "g", "", "", "" }, { "18", "", "", "", "", "" },
				{ "12", "g", "", "", "", "" }, { "19", "", "g", "", "", "" },
				{ "13", "", "g", "", "", "" }, { "20", "", "", "", "g", "" } },
				new Object[] { "Number", "one", "two", "Jap", "two", "three" });
		table = new JTable(dm) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			protected JTableHeader createDefaultTableHeader() {
				return new MyTableHeader(columnModel);
			}
		};

		// create vertical model.
		fixedTable = new MyVerticalHeaderTabel(fixedModel) {

			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			protected JTableHeader createDefaultTableHeader() {
				return new MyTableHeader(columnModel);
			}
		};
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setGridColor(table.getTableHeader().getBackground());

		TableCellRenderer renderer = new MyHeaderButtonRenderer();
		fixedTable.setDefaultRenderer(Object.class, renderer);
		fixedTable.setRowHeight(table.getRowHeight() + table.getRowMargin()
				- HEIGHT);

		// create group model.
		TableColumnModel cm = table.getColumnModel();
		ColumnGroup g_name = new ColumnGroup("Love");
		g_name.add(cm.getColumn(1));
		g_name.add(cm.getColumn(2));
		ColumnGroup g_lang = new ColumnGroup("UnLove");
		g_lang.add(cm.getColumn(3));
		ColumnGroup g_other = new ColumnGroup("VeryUnLove");
		g_other.add(cm.getColumn(4));
		g_other.add(cm.getColumn(5));
		g_lang.add(g_other);
		MyTableHeader header = (MyTableHeader) table.getTableHeader();
		header.addColumnGroup(g_name);
		header.addColumnGroup(g_lang);
		header.setReorderingAllowed(false);
		header.setHeaderEditable(false);
		TableCellRenderer renderer2 = new MyHeaderButtonRenderer();
		TableColumnModel model2 = table.getColumnModel();
		for (int i = 0; i < model2.getColumnCount(); i++) {
			model2.getColumn(i).setHeaderRenderer(renderer2);
		}
		table.getTableHeader().setUI(new MyGroupTableHeaderUI());

		// add them toghter.
		JScrollPane scroll = new JScrollPane(table);
		JViewport viewport = new JViewport();
		viewport.setView(fixedTable);
		viewport.setPreferredSize(fixedTable.getPreferredSize());
		scroll.setRowHeaderView(viewport);

		MyButton button = new MyButton();
	

		scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, button);

		getContentPane().add(scroll, BorderLayout.CENTER);

	}

	private class MyButton extends JButton {

		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public MyButton() {

		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2d = (Graphics2D) g;

			g2d.draw(new Line2D.Double(this.getX(), this.getX(), this.getX()
					+ this.getWidth(), this.getX() + this.getHeight()));

		}

	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestMyVerticalTable frame = new TestMyVerticalTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setVisible(true);
	}
}
