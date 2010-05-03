package jtable.cellattribute;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import jtable.tableverticalheader.MyCellAttribute;
import jtable.tableverticalheader.MyCellColor;
import jtable.tableverticalheader.MyCellFont;
import jtable.tableverticalheader.MyCellSpan;
import jtable.tableverticalheader.MyVerticalCellRenderer;
import jtable.tableverticalheader.MyVerticalHeaderModel;
import jtable.tableverticalheader.MyVerticalHeaderTabel;

/**
 * set cell attribute. font,fore color, back color and split.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cellattribute MixedExample.java <br>
 *         2008 2008/03/31 17:45:10 <br>
 */
public class TestTableCellAttribute extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * set cell attribute. font,fore color, back color and split.
	 */
	public TestTableCellAttribute() {
		super("Mixed Example");

		MyVerticalHeaderModel ml = new MyVerticalHeaderModel(20, 5) {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public Object getValueAt(int row, int col) {
				return "" + row + "," + col;
			}
		};
		MyCellAttribute cellAtt = ml.getCellAttribute();
		MyVerticalHeaderTabel table = new MyVerticalHeaderTabel(ml);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		table.setDefaultRenderer(Object.class, new MyVerticalCellRenderer());
		JScrollPane scroll = new JScrollPane(table);

		// color pane
		ColorPanel colorPanel = new ColorPanel(table, (MyCellColor) cellAtt);
		// font pane
		FontPanel fontPanel = new FontPanel(table, (MyCellFont) cellAtt);
		// split pane.
		SpanPanel spanPanel = new SpanPanel(table, (MyCellSpan) cellAtt);

		Box boxAtt = new Box(BoxLayout.Y_AXIS);
		boxAtt.add(colorPanel);
		boxAtt.add(fontPanel);
		boxAtt.add(spanPanel);

		Box box = new Box(BoxLayout.X_AXIS);
		box.add(scroll);
		box.add(new JSeparator(SwingConstants.HORIZONTAL));
		box.add(boxAtt);
		getContentPane().add(box);
		setSize(800, 600);
		setVisible(true);
	}

	/**
	 * set cell fore and back color.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtable.cellattribute MixedExample.java <br>
	 *         2008 2008/03/31 17:51:32 <br>
	 */
	class ColorPanel extends JPanel {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		private JTable table;
		private MyCellColor cellAtt;

		/**
		 * set cell fore and back color.
		 * 
		 * @param table
		 *            the table
		 * @param cellAtt
		 *            cell interface.
		 */
		ColorPanel(final JTable table, final MyCellColor cellAtt) {
			this.table = table;
			this.cellAtt = cellAtt;
			setLayout(new GridLayout(2, 1));
			setBorder(BorderFactory.createTitledBorder("Color"));
			JButton b_fore = new JButton("Foreground");
			b_fore.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeColor(true);
				}
			});
			JButton b_back = new JButton("Background");
			b_back.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					changeColor(false);
				}
			});
			add(b_fore);
			add(b_back);
		}

		/**
		 * change the cell color.
		 * 
		 * @param isForeground
		 *            is the fore selected.
		 */
		private final void changeColor(boolean isForeground) {
			int[] columns = table.getSelectedColumns();
			int[] rows = table.getSelectedRows();
			if ((rows == null) || (columns == null))
				return;
			if ((rows.length < 1) || (columns.length < 1))
				return;
			Color target = cellAtt.getForeground(rows[0], columns[0]);
			Color reference = cellAtt.getBackground(rows[0], columns[0]);
			for (int i = 0; i < rows.length; i++) {
				int row = rows[i];
				for (int j = 0; j < columns.length; j++) {
					int column = columns[j];
					target = (target != cellAtt.getForeground(row, column)) ? null
							: target;
					reference = (reference != cellAtt
							.getBackground(row, column)) ? null : reference;
				}
			}
			String title;
			if (isForeground) {
				target = (target != null) ? target : table.getForeground();
				reference = (reference != null) ? reference : table
						.getBackground();
				title = "Foreground Color";
			} else {
				target = (reference != null) ? reference : table
						.getBackground();
				reference = (target != null) ? target : table.getForeground();
				title = "Foreground Color";
			}
			TextColorChooser chooser = new TextColorChooser(target, reference,
					isForeground);
			Color color = chooser.showDialog(TestTableCellAttribute.this, title);
			if (color != null) {
				if (isForeground) {
					cellAtt.setForeground(color, rows, columns);
				} else {
					cellAtt.setBackground(color, rows, columns);
				}
				table.clearSelection();
				table.revalidate();
				table.repaint();
			}
		}
	}

	/**
	 * set table cell font.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtable.cellattribute MixedExample.java <br>
	 *         2008 2008/03/31 17:49:58 <br>
	 */
	class FontPanel extends JPanel {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		private String[] str_size = { "10", "12", "14", "16", "20" };
		private String[] str_style = { "PLAIN", "BOLD", "ITALIC" };
		private JComboBox name, style, size;

		/**
		 * set table cell font.
		 * 
		 * @param table
		 * @param cellAtt
		 */
		@SuppressWarnings("deprecation")
		FontPanel(final JTable table, final MyCellFont cellAtt) {
			setLayout(new BorderLayout());
			setBorder(BorderFactory.createTitledBorder("Font"));
			Box box = new Box(BoxLayout.X_AXIS);
			JPanel p2 = new JPanel(new GridLayout(3, 1));
			JPanel p3 = new JPanel(new GridLayout(3, 1));
			JPanel p4 = new JPanel(new BorderLayout());
			p2.add(new JLabel("Name:"));
			p2.add(new JLabel("Style:"));
			p2.add(new JLabel("Size:"));
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			name = new JComboBox(toolkit.getFontList());
			style = new JComboBox(str_style);
			size = new JComboBox(str_size);
			size.setEditable(true);
			JButton b_apply = new JButton("Apply");
			b_apply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int[] columns = table.getSelectedColumns();
					int[] rows = table.getSelectedRows();
					if ((rows == null) || (columns == null))
						return;
					if ((rows.length < 1) || (columns.length < 1))
						return;
					Font font = new Font((String) name.getSelectedItem(), style
							.getSelectedIndex(), Integer.parseInt((String) size
							.getSelectedItem()));
					cellAtt.setFont(font, rows, columns);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			p3.add(name);
			p3.add(style);
			p3.add(size);
			p4.add(BorderLayout.CENTER, b_apply);
			box.add(p2);
			box.add(p3);
			add(BorderLayout.CENTER, box);
			add(BorderLayout.SOUTH, p4);
		}
	}

	/**
	 * split and combine the table cell.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtable.cellattribute MixedExample.java <br>
	 *         2008 2008/03/31 17:48:42 <br>
	 */
	class SpanPanel extends JPanel {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * split and combine the table cell.
		 * 
		 * @param table
		 *            the table
		 * @param cellAtt
		 *            the cell interface.
		 */
		SpanPanel(final JTable table, final MyCellSpan cellAtt) {

			setLayout(new GridLayout(2, 1));
			setBorder(BorderFactory.createTitledBorder("Span"));
			JButton b_one = new JButton("Combine");
			b_one.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int[] columns = table.getSelectedColumns();
					int[] rows = table.getSelectedRows();
					cellAtt.combine(rows, columns);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			JButton b_split = new JButton("Split");
			b_split.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int column = table.getSelectedColumn();
					int row = table.getSelectedRow();
					cellAtt.split(row, column);
					table.clearSelection();
					table.revalidate();
					table.repaint();
				}
			});
			add(b_one);
			add(b_split);
		}
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestTableCellAttribute frame = new TestTableCellAttribute();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
