package jtable.tableheader;

import javax.swing.table.DefaultTableModel;

/**
 * This is an implementation of <code>TableModel</code> that uses a
 * <code>Vector</code> of <code>Vectors</code> to store the cell value
 * objects.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyTableModel.java <br>
 *         2008 2008/03/28 10:55:51 <br>
 */
public class MyTableModel extends DefaultTableModel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** save index. */
	private int[] indexes = null;

	/**
	 * This is an implementation of <code>TableModel</code> that uses a
	 * <code>Vector</code> of <code>Vectors</code> to store the cell value
	 * objects.
	 */
	public MyTableModel() {
		super();
	}

	/**
	 * Returns true regardless of parameter values.
	 * 
	 * @param row
	 *            the row whose value is to be queried
	 * @param column
	 *            the column whose value is to be queried
	 * @return false
	 * @see #setValueAt
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * Returns an attribute value for the cell at <code>row</code> and
	 * <code>column</code>.
	 * 
	 * @param row
	 *            the row whose value is to be queried
	 * @param column
	 *            the column whose value is to be queried
	 * @return the value Object at the specified cell
	 * @exception ArrayIndexOutOfBoundsException
	 *                if an invalid row or column was given
	 */
	public Object getValueAt(int row, int column) {
		int rowIndex = row;
		if (indexes != null) {
			rowIndex = indexes[row];
		}
		return super.getValueAt(rowIndex, column);
	}

	/**
	 * Sets the object value for the cell at <code>column</code> and
	 * <code>row</code>. <code>aValue</code> is the new value. This method
	 * will generate a <code>tableChanged</code> notification.
	 * 
	 * @param aValue
	 *            the new value; this can be null
	 * @param row
	 *            the row whose value is to be changed
	 * @param column
	 *            the column whose value is to be changed
	 * @exception ArrayIndexOutOfBoundsException
	 *                if an invalid row or column was given
	 */
	public void setValueAt(Object aValue, int row, int column) {
		int rowIndex = row;
		if (indexes != null) {
			rowIndex = indexes[row];
		}
		super.setValueAt(aValue, rowIndex, column);
	}

	/**
	 * get the model index.
	 * 
	 * @return index.
	 */
	public int[] getIndexes() {
		int n = getRowCount();
		if (indexes != null) {
			if (indexes.length == n) {
				return indexes;
			}
		}
		indexes = new int[n];
		for (int i = 0; i < n; i++) {
			indexes[i] = i;
		}
		return indexes;
	}
}
