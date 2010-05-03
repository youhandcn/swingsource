package jtable.cell;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.EventListenerList;

/**
 * The default editor for table and tree cells.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyButtonCellEditor.java <br>
 *         2008 2008/03/31 18:27:20 <br>
 */
public class MyButtonCellEditor extends DefaultCellEditor {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	private JButton button = null;
	private String label = null;
	private boolean isPushed = false;

	/**
	 * The default editor for table and tree cells.
	 */
	public MyButtonCellEditor() {
		super(new JCheckBox());
		button = new JButton();
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	/**
	 * Sets an initial <code>value</code> for the editor.
	 * 
	 * @param table
	 *            the <code>JTable</code> that is asking the editor to edit;
	 *            can be <code>null</code>
	 * @param value
	 *            the value of the cell to be edited.
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
		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
			button.setBackground(table.getSelectionBackground());
		} else {
			button.setForeground(table.getForeground());
			button.setBackground(table.getBackground());
		}
		label = (value == null) ? "" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#getCellEditorValue
	 */
	public Object getCellEditorValue() {
		if (isPushed) {
		}
		isPushed = false;
		return new String(label);
	}

	/**
	 * Forwards the message from the <code>CellEditor</code> to the
	 * <code>delegate</code>.
	 * 
	 * @see EditorDelegate#stopCellEditing
	 */
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is created lazily.
	 * 
	 * @see EventListenerList
	 */
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

}
