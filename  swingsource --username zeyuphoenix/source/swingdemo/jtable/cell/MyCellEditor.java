package jtable.cell;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Hashtable;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * This interface defines the method any object that would like to be an editor
 * of values for components such as <code>JListBox</code>,
 * <code>JComboBox</code>, <code>JTree</code>, or <code>JTable</code>
 * needs to implement.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.cell MyCellEditor.java <br>
 *         2008 2008/04/01 13:08:50 <br>
 */
public class MyCellEditor implements TableCellEditor {
	/** save all editor to it. */
	private Hashtable<Integer, TableCellEditor> editors = null;
	/** each cell editor. */
	private TableCellEditor editor = null;
	/** the cell editor table. */
	private JTable table = null;

	/**
	 * Constructs a EachRowEditor. create default editor
	 * 
	 * @see TableCellEditor
	 * @see DefaultCellEditor
	 */
	public MyCellEditor(JTable table) {
		this.table = table;
		editors = new Hashtable<Integer, TableCellEditor>();
	}

	/**
	 * add cell editor to it.
	 * 
	 * @param row
	 *            table row
	 * @param editor
	 *            table cell editor
	 */
	public void setEditorAt(int row, TableCellEditor editor) {
		editors.put(new Integer(row), editor);
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
		editor = (TableCellEditor) editors.get(new Integer(row));
		if (editor == null) {
			editor = new DefaultCellEditor(new JTextField());
		}
		return editor.getTableCellEditorComponent(table, value, isSelected,
				row, column);
	}

	/**
	 * Returns the value contained in the editor.
	 * 
	 * @return the value contained in the editor
	 */
	public Object getCellEditorValue() {
		return editor.getCellEditorValue();
	}

	/**
	 * Tells the editor to stop editing and accept any partially edited value as
	 * the value of the editor.
	 * 
	 * @return true if editing was stopped; false otherwise
	 */
	public boolean stopCellEditing() {
		return editor.stopCellEditing();
	}

	/**
	 * Tells the editor to cancel editing and not accept any partially edited
	 * value.
	 */
	public void cancelCellEditing() {
		editor.cancelCellEditing();
	}

	/**
	 * Asks the editor if it can start editing using <code>anEvent</code>.
	 * <code>anEvent</code> is in the invoking component coordinate system.
	 * 
	 * @param anEvent
	 *            the event the editor should use to consider whether to begin
	 *            editing or not
	 * @return true if editing can be started
	 * @see #shouldSelectCell
	 */
	public boolean isCellEditable(EventObject anEvent) {
		selectEditor((MouseEvent) anEvent);
		return editor.isCellEditable(anEvent);
	}

	/**
	 * Adds a listener to the list that's notified when the editor stops, or
	 * cancels editing.
	 * 
	 * @param l
	 *            the CellEditorListener
	 */
	public void addCellEditorListener(CellEditorListener l) {
		editor.addCellEditorListener(l);
	}

	/**
	 * Removes a listener from the list that's notified
	 * 
	 * @param l
	 *            the CellEditorListener
	 */
	public void removeCellEditorListener(CellEditorListener l) {
		editor.removeCellEditorListener(l);
	}

	/**
	 * Returns true if the editing cell should be selected, false otherwise.
	 * 
	 * @param anEvent
	 *            the event the editor should use to start editing
	 * @return true if the editor would like the editing cell to be selected;
	 *         otherwise returns false
	 * @see #isCellEditable
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		selectEditor((MouseEvent) anEvent);
		return editor.shouldSelectCell(anEvent);
	}

	/**
	 * select editor.
	 * 
	 * @param e
	 *            MouseEvent
	 */
	private void selectEditor(MouseEvent e) {
		int row = 0;
		if (e == null) {
			row = table.getSelectionModel().getAnchorSelectionIndex();
		} else {
			row = table.rowAtPoint(e.getPoint());
		}
		editor = (TableCellEditor) editors.get(new Integer(row));
		if (editor == null) {
			editor = new DefaultCellEditor(new JTextField());
		}
	}
}
