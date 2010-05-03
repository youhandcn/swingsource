package jtable.celltest;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import jtable.cell.MyCellEditor;
import jtable.cell.MyCellRenderer;
import jtable.cell.MyCheckBoxRenderer;

/**
 * test about cell with some component.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell MultiComponentTable2.java <br>
 *         2008 2008/04/01 13:45:50 <br>
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
				{ "true", "String", "JLabel", "JComboBox" },
				{ "false", "String", "JLabel", "JComboBox" },
				{ new Boolean(true), "Boolean", "JCheckBox", "JCheckBox" },
				{ new Boolean(false), "Boolean", "JCheckBox", "JCheckBox" },
				{ "true", "String", "JLabel", "JTextField" },
				{ "false", "String", "JLabel", "JTextField" } }, new Object[] {
				"Component", "Data", "Renderer", "Editor" });

		MyCheckBoxRenderer checkBoxRenderer = new MyCheckBoxRenderer();
		MyCellRenderer rowRenderer = new MyCellRenderer();
		rowRenderer.add(2, checkBoxRenderer);
		rowRenderer.add(3, checkBoxRenderer);

		JComboBox comboBox = new JComboBox();
		comboBox.addItem("true");
		comboBox.addItem("false");
		JCheckBox checkBox = new JCheckBox("ilove");
		checkBox.setHorizontalAlignment(JLabel.CENTER);

		DefaultCellEditor comboBoxEditor = new DefaultCellEditor(comboBox);
		DefaultCellEditor checkBoxEditor = new DefaultCellEditor(checkBox);

		JTable table = new JTable(dm);

		MyCellEditor rowEditor = new MyCellEditor(table);
		rowEditor.setEditorAt(0, comboBoxEditor);
		rowEditor.setEditorAt(1, comboBoxEditor);
		rowEditor.setEditorAt(2, checkBoxEditor);
		rowEditor.setEditorAt(3, checkBoxEditor);
		table.getColumn("Component").setCellRenderer(rowRenderer);
		table.getColumn("Component").setCellEditor(rowEditor);

		table.getSelectionModel().addListSelectionListener(new selectChange(table));

		JScrollPane scroll = new JScrollPane(table);
		getContentPane().add(scroll);
		setSize(400, 200);
		setVisible(true);
	}

	private class selectChange implements ListSelectionListener {
		public selectChange(JTable table) {
			this.table = table;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				for (int  row : table.getSelectedRows()) {
					System.out.println(row);
					
				}
			}
		
		}

		JTable table = null;
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
