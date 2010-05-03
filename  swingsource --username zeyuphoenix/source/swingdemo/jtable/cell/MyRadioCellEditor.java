package jtable.cell;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTable;

/**
 * create cell editor that radio in it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyRadioCellEditor.java <br>
 *         2008 2008/04/01 10:19:48 <br>
 */
public class MyRadioCellEditor extends DefaultCellEditor implements
		ItemListener {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** radio pane. */
	private MyRadioPanel panel = null;

	/**
	 * create cell editor that radio in it.
	 * 
	 * @param panel
	 *            radio pane.
	 */
	public MyRadioCellEditor(MyRadioPanel panel) {
		super(new JCheckBox());
		this.panel = panel;
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton[] buttons = panel.getButtons();
		for (int i = 0; i < buttons.length; i++) {
			buttonGroup.add(buttons[i]);
			buttons[i].addItemListener(this);
		}
	}

	/**
	 * Sets an initial <code>value</code> for the editor.
	 * 
	 * @param table
	 *            the <code>JTable</code> that is asking the editor to edit;
	 *            can be <code>null</code>
	 * @param value
	 *            the value of the cell to be edited;
	 * @param isSelected
	 *            true if the cell is to be rendered with highlighting
	 * @param row
	 *            the row of the cell being edited
	 * @param column
	 *            the column of the cell being edited
	 * @return the component for editing
	 */
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value instanceof Integer) {
			panel.setSelectedIndex(((Integer) value).intValue());
		}
		return panel;
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#getCellEditorValue
	 */
	public Object getCellEditorValue() {
		return new Integer(panel.getSelectedIndex());
	}

	/**
	 * * Notifies all listeners that have registered interest for notification
	 * on this event type. The event instance is created lazily.
	 * 
	 * @param e
	 *            ItemEvent.
	 */
	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}
