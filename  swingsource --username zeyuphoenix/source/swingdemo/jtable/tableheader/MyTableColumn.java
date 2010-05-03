package jtable.tableheader;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

/**
 * A <code>TableColumn</code> represents all the attributes of a column in a
 * <code>JTable</code>, such as width, resizibility, minimum and maximum
 * width. In addition, the <code>TableColumn</code> provides slots for a
 * renderer and an editor that can be used to display and edit the values in
 * this column.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyTableColumn.java <br>
 *         2008 2008/03/28 13:25:25 <br>
 */
public class MyTableColumn extends TableColumn {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** table header editor. */
	private TableCellEditor headerEditor;
	/** is header edit able. */
	private boolean isHeaderEditable;

	/**
	 * A <code>TableColumn</code> represents all the attributes of a column in
	 * a <code>JTable</code>, such as width, resizibility, minimum and
	 * maximum width. In addition, the <code>TableColumn</code> provides slots
	 * for a renderer and an editor that can be used to display and edit the
	 * values in this column.
	 */
	public MyTableColumn() {
		this(true);
	}

	/**
	 * A <code>TableColumn</code> represents all the attributes of a column in
	 * a <code>JTable</code>, such as width, resizibility, minimum and
	 * maximum width. In addition, the <code>TableColumn</code> provides slots
	 * for a renderer and an editor that can be used to display and edit the
	 * values in this column.
	 * 
	 * @param isHeaderEditable
	 *            is edit able.
	 */
	public MyTableColumn(boolean isHeaderEditable) {
		setHeaderEditor(createDefaultHeaderEditor());
		this.isHeaderEditable = isHeaderEditable;
	}

	/**
	 * set table header editor.
	 * 
	 * @param headerEditor
	 *            header editor.
	 */
	public void setHeaderEditor(TableCellEditor headerEditor) {
		this.headerEditor = headerEditor;
	}

	/**
	 * get table header editor.
	 * 
	 * @return header editor.
	 */
	public TableCellEditor getHeaderEditor() {
		return headerEditor;
	}

	/**
	 * set header is edit able.
	 * 
	 * @param isEditable
	 *            header is edit able.
	 */
	public void setHeaderEditable(boolean isEditable) {
		this.isHeaderEditable = isEditable;
	}

	/**
	 * get header is edit able.
	 * 
	 * @return header is edit able.
	 */
	public boolean isHeaderEditable() {
		return isHeaderEditable;
	}

	/**
	 * copy the old header value to the one.
	 * 
	 * @param base
	 *            base column.
	 */
	public void copyValues(TableColumn base) {
		modelIndex = base.getModelIndex();
		identifier = base.getIdentifier();
		width = base.getWidth();
		minWidth = base.getMinWidth();
		setPreferredWidth(base.getPreferredWidth());
		maxWidth = base.getMaxWidth();
		headerRenderer = base.getHeaderRenderer();
		headerValue = base.getHeaderValue();
		cellRenderer = base.getCellRenderer();
		cellEditor = base.getCellEditor();
		isResizable = base.getResizable();
	}

	protected TableCellEditor createDefaultHeaderEditor() {
		return new DefaultCellEditor(new JTextField());
	}

}
