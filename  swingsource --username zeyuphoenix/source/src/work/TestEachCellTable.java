package work;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * test about cell with some component.
 * 
 */
public class TestEachCellTable extends JFrame {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * * test about cell with some component.
	 */
	public TestEachCellTable() {
		super("MultiComponent Table");

		DefaultTableModel dm = new DefaultTableModel() {
			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				if (column == 0) {
					return true;
				}
				return false;
			}
		};
		dm.setDataVector(new Object[][] {
				{ new Boolean(false), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "Boolean", "JCheckBox", "JCheckBox" },
				{ new Boolean(false), "Boolean", "JCheckBox", "JCheckBox" },
				{ new Boolean(true), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "String", "JLabel", "JComboBox" },
				{ new Boolean(false), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "String", "JLabel", "JComboBox" },
				{ new Boolean(false), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "String", "JLabel", "JComboBox" },
				{ new Boolean(false), "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "String", "JLabel", "JTextField" },
				{ new Boolean(false), "String", "JLabel", "JTextField" } },
				new Object[] { "Component", "Data", "Renderer", "Editor" });

		MyCheckBoxRenderer checkBoxRenderer = new MyCheckBoxRenderer();

		JCheckBox checkBox = new JCheckBox("");
		checkBox.setHorizontalAlignment(JLabel.CENTER);

		DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);

		JTable table = new JTable(dm);

		table.getColumn("Component").setCellRenderer(checkBoxRenderer);
		table.getColumn("Component").setCellEditor(checkBoxEditor);
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionAdapter(table));
		table.addMouseListener(new ListSelectionAdapter(table));
		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(600, 400);
		setVisible(true);

	}

	private class ListSelectionAdapter implements ListSelectionListener,
			MouseListener {

		private JTable table = null;

		public ListSelectionAdapter(JTable table) {
			this.table = table;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				int[] rows = table.getSelectedRows();
				for (int i = 0; i < rows.length; i++) {
					boolean state = (Boolean) table.getValueAt(rows[i], 0);
					int leadRow = ((DefaultListSelectionModel) e.getSource())
							.getLeadSelectionIndex();
					if (leadRow != rows[i]) {
						if (state) {
							table.setValueAt(new Boolean(false), rows[i], 0);
						}
					} else {
						table.setValueAt(new Boolean(true), rows[i], 0);
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// 获取鼠标点击的行的位置（及行数）
			Point mousepoint = e.getPoint();
			int row = table.rowAtPoint(mousepoint);
			int column = table.columnAtPoint(mousepoint);
			if (row > -1 && column > 0) {
				boolean state = (Boolean) table.getValueAt(row, 0);

				if (state) {
					table.setValueAt(new Boolean(false), row, 0);
				} else {
					table.setValueAt(new Boolean(true), row, 0);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}
	}

	/**
	 * test.
	 * 
	 * @param args
	 *            string array.
	 */
	public static void main(String[] args) {
		TestEachCellTable frame = new TestEachCellTable();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
