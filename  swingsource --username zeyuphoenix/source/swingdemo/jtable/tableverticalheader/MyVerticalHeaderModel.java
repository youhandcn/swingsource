package jtable.tableverticalheader;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class MyVerticalHeaderModel extends DefaultTableModel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** cell attribute. */
	private MyCellAttribute cellAtt;

	/**
	 * Constructs a <code>DefaultTableModel</code> with <code>rowCount</code>
	 * and <code>columnCount</code> of <code>null</code> object values.
	 */
	public MyVerticalHeaderModel() {
		this(0, 0);
	}

	/**
	 * Constructs a <code>DefaultTableModel</code> with <code>rowCount</code>
	 * and <code>columnCount</code> of <code>null</code> object values.
	 * 
	 * @param rowCount
	 *            the number of rows the table holds
	 * @param columnCount
	 *            the number of columns the table holds
	 */
	public MyVerticalHeaderModel(int numRows, int numColumns) {
		Vector<?> names = new Vector<Object>(numColumns);
		names.setSize(numColumns);
		setColumnIdentifiers(names);
		dataVector = new Vector<Vector<?>>();
		setNumRows(numRows);
		cellAtt = new MyVerticalHeaderCell(numRows, numColumns);
	}

	/**
	 * Constructs a <code>DefaultTableModel</code> with as many columns as
	 * there are elements in <code>columnNames</code> and
	 * <code>rowCount</code> of <code>null</code> object values.
	 * 
	 * @param columnNames
	 *            <code>vector</code> containing the names of the new columns;
	 *            if this is <code>null</code> then the model has no columns
	 * @param rowCount
	 *            the number of rows the table holds
	 */
	public MyVerticalHeaderModel(Vector<?> columnNames, int numRows) {
		setColumnIdentifiers(columnNames);
		dataVector = new Vector<Vector<?>>();
		setNumRows(numRows);
		cellAtt = new MyVerticalHeaderCell(numRows, columnNames.size());
	}

	/**
	 * Constructs a <code>DefaultTableModel</code> with as many columns as
	 * there are elements in <code>columnNames</code> and
	 * <code>rowCount</code> of <code>null</code> object values. Each
	 * column's name will be taken from the <code>columnNames</code> array.
	 * 
	 * @param columnNames
	 *            <code>array</code> containing the names of the new columns;
	 *            if this is <code>null</code> then the model has no columns
	 * @param rowCount
	 *            the number of rows the table holds
	 */
	public MyVerticalHeaderModel(Object[] columnNames, int numRows) {
		this(convertToVector(columnNames), numRows);
	}

	/**
	 * Constructs a <code>DefaultTableModel</code> and initializes the table
	 * by passing <code>data</code> and <code>columnNames</code> to the
	 * <code>setDataVector</code> method.
	 * 
	 * @param data
	 *            the data of the table, a <code>Vector</code> of
	 *            <code>Vector</code>s of <code>Object</code> values
	 * @param columnNames
	 *            <code>vector</code> containing the names of the new columns
	 */
	public MyVerticalHeaderModel(Vector<Vector<?>> data, Vector<?> columnNames) {
		addDataVector(data, columnNames);
	}

	/**
	 * Constructs a <code>DefaultTableModel</code> and initializes the table
	 * by passing <code>data</code> and <code>columnNames</code> to the
	 * <code>setDataVector</code>
	 * 
	 * @param data
	 *            the data of the table
	 * @param columnNames
	 *            the names of the columns
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MyVerticalHeaderModel(Object[][] data, Object[] columnNames) {
		this(convertToVector(data), convertToVector(columnNames));
	}

	/**
	 * Replaces the current <code>dataVector</code> instance variable with the
	 * new <code>Vector</code> of rows, <code>dataVector</code>. Each row
	 * is represented in <code>dataVector</code> as a <code>Vector</code> of
	 * <code>Object</code> values. <code>columnIdentifiers</code> are the
	 * names of the new columns.
	 * 
	 * @param dataVector
	 *            the new data vector
	 * @param columnIdentifiers
	 *            the names of the columns
	 * @see #getDataVector
	 */
	public void addDataVector(Vector<Vector<?>> newData, Vector<?> columnNames) {
		if (newData == null) {
			throw new IllegalArgumentException(
					"setDataVector() - Null parameter");
		}
		dataVector = new Vector<Vector<?>>(0);

		setColumnIdentifiers(columnNames);
		dataVector = newData;

		cellAtt = new MyVerticalHeaderCell(dataVector.size(), columnIdentifiers
				.size());

		newRowsAdded(new TableModelEvent(this, 0, getRowCount() - 1,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	/**
	 * Adds a column to the model. The new column will have the identifier
	 * <code>columnName</code>, which may be null. This method will send a
	 * <code>tableChanged</code> notification message to all the listeners.
	 * This method is a cover for <code>addColumn(Object, Vector)</code> which
	 * uses <code>null</code> as the data vector.
	 * 
	 * @param columnName
	 *            the identifier of the column being added
	 */
	@SuppressWarnings("unchecked")
	public void addColumn(Object columnName, Vector columnData) {
		if (columnName == null)
			throw new IllegalArgumentException("addColumn() - null parameter");
		columnIdentifiers.addElement(columnName);
		int index = 0;
		Enumeration<Vector<?>> enumeration = dataVector.elements();
		while (enumeration.hasMoreElements()) {
			Object value;
			if ((columnData != null) && (index < columnData.size()))
				value = columnData.elementAt(index);
			else
				value = null;
			((Vector<Object>) enumeration.nextElement()).addElement(value);
			index++;
		}

		//
		cellAtt.addColumn();

		fireTableStructureChanged();
	}

	/**
	 * Adds a row to the end of the model. The new row will contain
	 * <code>null</code> values unless <code>rowData</code> is specified.
	 * Notification of the row being added will be generated.
	 * 
	 * @param rowData
	 *            optional data of the row being added
	 */
	@SuppressWarnings("unchecked")
	public void addRow(Vector rowData) {
		Vector newData = null;
		if (rowData == null) {
			newData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}
		dataVector.addElement(newData);

		//
		cellAtt.addRow();

		newRowsAdded(new TableModelEvent(this, getRowCount() - 1,
				getRowCount() - 1, TableModelEvent.ALL_COLUMNS,
				TableModelEvent.INSERT));
	}

	/**
	 * Inserts a row at <code>row</code> in the model. The new row will
	 * contain <code>null</code> values unless <code>rowData</code> is
	 * specified. Notification of the row being added will be generated.
	 * 
	 * @param row
	 *            the row index of the row to be inserted
	 * @param rowData
	 *            optional data of the row being added
	 * @exception ArrayIndexOutOfBoundsException
	 *                if the row was invalid
	 */
	@SuppressWarnings("unchecked")
	public void insertRow(int row, Vector rowData) {
		if (rowData == null) {
			rowData = new Vector(getColumnCount());
		} else {
			rowData.setSize(getColumnCount());
		}

		dataVector.insertElementAt(rowData, row);
		cellAtt.insertRow(row);

		newRowsAdded(new TableModelEvent(this, row, row,
				TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
	}

	/**
	 * get cell attribute.
	 * 
	 * @return cell attribute.
	 */
	public MyCellAttribute getCellAttribute() {
		return cellAtt;
	}

	/**
	 * set cell attribute.
	 * 
	 * @param newCellAtt
	 *            cell attribute.
	 */
	public void setCellAttribute(MyCellAttribute newCellAtt) {
		int numColumns = getColumnCount();
		int numRows = getRowCount();
		if ((newCellAtt.getSize().width != numColumns)
				|| (newCellAtt.getSize().height != numRows)) {
			newCellAtt.setSize(new Dimension(numRows, numColumns));
		}
		cellAtt = newCellAtt;
		fireTableDataChanged();
	}
}
