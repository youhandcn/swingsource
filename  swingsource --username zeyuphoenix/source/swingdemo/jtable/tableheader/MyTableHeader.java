package jtable.tableheader;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * This is the object which manages the header of the <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyTableHeader.java <br>
 *         2008 2008/03/27 17:49:41 <br>
 */
public class MyTableHeader extends JTableHeader implements CellEditorListener {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** table header that groups. */
	protected Vector<ColumnGroup> columnGroups = null;
	/** edit component number. */
	transient protected int editingColumn = 0;
	/** cell editor */
	transient protected TableCellEditor cellEditor = null;
	/** can edit component. */
	transient protected Component editorComp = null;

	/**
	 * Constructs a <code>JTableHeader</code> which is initialized with
	 * <code>model</code> as the column model.
	 */
	public MyTableHeader() {
		this(null);
	}

	/**
	 * Constructs a <code>JTableHeader</code> which is initialized with
	 * <code>model</code> as the column model.
	 * 
	 * @param model
	 *            the column model for the table
	 */
	public MyTableHeader(TableColumnModel model) {
		super(model);
		setUI(new MyGroupTableHeaderUI());
		setReorderingAllowed(false);
		cellEditor = null;
		recreateTableColumn(columnModel);
	}

	/**
	 * Sets whether the user can drag column headers to reorder columns.
	 * 
	 * @param reorderingAllowed
	 *            true if the table view should allow reordering; otherwise
	 *            false
	 */
	public void setReorderingAllowed(boolean b) {
		reorderingAllowed = b;
	}

	/**
	 * add the column group to vector.
	 * 
	 * @param g
	 *            ColumnGroup
	 */
	public void addColumnGroup(ColumnGroup g) {
		if (columnGroups == null) {
			columnGroups = new Vector<ColumnGroup>();
		}
		columnGroups.addElement(g);
	}

	/**
	 * get Column Groups.
	 * 
	 * @param col
	 *            column index.
	 * @return Column Groups.
	 */
	public Enumeration<?> getColumnGroups(TableColumn col) {
		if (columnGroups == null)
			return null;
		Enumeration<ColumnGroup> enumTemp = columnGroups.elements();
		while (enumTemp.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) enumTemp.nextElement();
			Vector<?> v_ret = (Vector<?>) cGroup.getColumnGroups(col,
					new Vector<ColumnGroup>());
			if (v_ret != null) {
				return v_ret.elements();
			}
		}
		return null;
	}

	/**
	 * set column margin,the margin is about a cell.
	 */
	public void setColumnMargin() {
		if (columnGroups == null) {
			return;
		}
		int columnMargin = getColumnModel().getColumnMargin();
		Enumeration<ColumnGroup> enumTemp = columnGroups.elements();
		while (enumTemp.hasMoreElements()) {
			ColumnGroup cGroup = (ColumnGroup) enumTemp.nextElement();
			cGroup.setColumnMargin(columnMargin);
		}
	}

	/**
	 * update the header UI.
	 */
	public void updateUI() {
		setUI(new MyGroupTableHeaderUI());
		resizeAndRepaint();
		invalidate();
	}

	/**
	 * recreate Table Column.
	 * 
	 * @param columnModel
	 *            Table Column Model
	 */
	protected void recreateTableColumn(TableColumnModel columnModel) {
		int n = columnModel.getColumnCount();
		MyTableColumn[] newCols = new MyTableColumn[n];
		TableColumn[] oldCols = new TableColumn[n];
		for (int i = 0; i < n; i++) {
			oldCols[i] = columnModel.getColumn(i);
			newCols[i] = new MyTableColumn();
			newCols[i].copyValues(oldCols[i]);
		}
		for (int i = 0; i < n; i++) {
			columnModel.removeColumn(oldCols[i]);
		}
		for (int i = 0; i < n; i++) {
			columnModel.addColumn(newCols[i]);
		}
	}

	/**
	 * set cell is editing.
	 * 
	 * @param index
	 *            edit index.
	 * @return is editing
	 */
	public boolean editCellAt(int index) {
		return editCellAt(index);
	}

	/**
	 * *set cell is editing.
	 * 
	 * @param index
	 *            edit index.
	 * @param e
	 *            EventObject
	 * @return is editing
	 */
	public boolean editCellAt(int index, EventObject e) {
		if (cellEditor != null && !cellEditor.stopCellEditing()) {
			return false;
		}
		if (!isCellEditable(index)) {
			return false;
		}
		TableCellEditor editor = getCellEditor(index);

		if (editor != null && editor.isCellEditable(e)) {
			editorComp = prepareEditor(editor, index);
			editorComp.setBounds(getHeaderRect(index));
			add(editorComp);
			editorComp.validate();
			setCellEditor(editor);
			setEditingColumn(index);
			editor.addCellEditorListener(this);

			return true;
		}
		return false;
	}

	/**
	 * get cell is edit able.
	 * 
	 * @param index
	 *            cell index.
	 * @return is edit able.
	 */
	public boolean isCellEditable(int index) {
		if (getReorderingAllowed()) {
			return false;
		}
		int columnIndex = columnModel.getColumn(index).getModelIndex();
		MyTableColumn col = (MyTableColumn) columnModel.getColumn(columnIndex);
		return col.isHeaderEditable();
	}

	/**
	 * get Cell Editor.
	 * 
	 * @param index
	 *            new editor index .
	 * @return Cell Editor
	 */
	public TableCellEditor getCellEditor(int index) {
		int columnIndex = columnModel.getColumn(index).getModelIndex();
		MyTableColumn col = (MyTableColumn) columnModel.getColumn(columnIndex);
		return col.getHeaderEditor();
	}

	/**
	 * set Cell Editor.
	 * 
	 * @param newEditor
	 *            new editor.
	 */
	public void setCellEditor(TableCellEditor newEditor) {
		TableCellEditor oldEditor = cellEditor;
		cellEditor = newEditor;

		// firePropertyChange
		if (oldEditor != null && oldEditor instanceof TableCellEditor) {
			((TableCellEditor) oldEditor)
					.removeCellEditorListener((CellEditorListener) this);
		}
		if (newEditor != null && newEditor instanceof TableCellEditor) {
			((TableCellEditor) newEditor)
					.addCellEditorListener((CellEditorListener) this);
		}
	}

	/**
	 * prepare editor.
	 * 
	 * @param editor
	 *            cell editor.
	 * @param index
	 *            editor column number.
	 * @return editor column.
	 */
	@SuppressWarnings("deprecation")
	public Component prepareEditor(TableCellEditor editor, int index) {
		Object value = columnModel.getColumn(index).getHeaderValue();
		boolean isSelected = true;
		int row = -10;
		JTable table = getTable();
		Component comp = editor.getTableCellEditorComponent(table, value,
				isSelected, row, index);
		if (comp instanceof JComponent) {
			((JComponent) comp).setNextFocusableComponent(this);
		}
		return comp;
	}

	/**
	 * set editor column.
	 * 
	 * @param aColumn
	 *            editor column.
	 */
	public TableCellEditor getCellEditor() {
		return cellEditor;
	}

	/**
	 * get editor column.
	 * 
	 * @return editor column.
	 */
	public Component getEditorComponent() {
		return editorComp;
	}

	/**
	 * set editor column number.
	 * 
	 * @param aColumn
	 *            editor column number.
	 */
	public void setEditingColumn(int aColumn) {
		editingColumn = aColumn;
	}

	/**
	 * get editor column number.
	 * 
	 * @return editor column number.
	 */
	public int getEditingColumn() {
		return editingColumn;
	}

	/**
	 * set header edit.
	 * 
	 * @param able
	 *            edit able.
	 */
	public void setHeaderEditable(boolean able) {
		for (int i = 0; i < this.getColumnModel().getColumnCount(); i++) {
			((MyTableColumn) this.getColumnModel().getColumn(i))
					.setHeaderEditable(able);
		}
	}

	/**
	 * remove all editor.
	 */
	public void removeEditor() {
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			editor.removeCellEditorListener(this);

			requestFocus();
			remove(editorComp);

			int index = getEditingColumn();
			Rectangle cellRect = getHeaderRect(index);

			setCellEditor(null);
			setEditingColumn(-1);
			editorComp = null;

			repaint(cellRect);
		}
	}

	/** is the header editing. */
	public boolean isEditing() {
		return (cellEditor == null) ? false : true;
	}

	/** This tells the listeners the editor has ended editing */
	public void editingStopped(ChangeEvent e) {
		TableCellEditor editor = getCellEditor();
		if (editor != null) {
			Object value = editor.getCellEditorValue();
			int index = getEditingColumn();
			columnModel.getColumn(index).setHeaderValue(value);
			removeEditor();
		}
	}

	/** This tells the listeners the editor has canceled editing */
	public void editingCanceled(ChangeEvent e) {
		removeEditor();
	}
}
