package jtable.tableheader;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableRowSorter;

/**
 * An implementation of <code>RowSorter</code> that provides sorting and
 * filtering using a <code>TableModel</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyTableSorter.java <br>
 *         2008 2008/03/28 11:15:17 <br>
 */
public class MyTableSorter extends TableRowSorter<MyTableModel> {
	/** the sort table. */
	private JTable jtable = null;
	/** the sort model. */
	MyTableModel tableModel = null;

	/**
	 * An implementation of <code>RowSorter</code> that provides sorting and
	 * filtering using a <code>TableModel</code>.
	 * 
	 * @param tableModel
	 *            model the underlying model to use, or <code>null</code>
	 * @param table
	 */
	public MyTableSorter(MyTableModel tableModel, JTable table) {
		super(tableModel);
		jtable = table;
		this.tableModel = tableModel;
		setMaxSortKeys(1);
		// setAllUnSortable();
		// removeListener();
	}

	/**
	 * Invoked when rows have been inserted into the underlying model in the
	 * specified range (inclusive).
	 * 
	 * @param firstRow
	 *            the first row
	 * @param endRow
	 *            the last row
	 * @throws IndexOutOfBoundsException
	 *             if either argument is invalid, or <code>firstRow</code> &gt;
	 *             <code>endRow</code>
	 */
	public void rowsInserted(int firstRow, int endRow) {
		int newModelRowCount = getModelWrapper().getRowCount();
		if (endRow >= newModelRowCount || firstRow >= newModelRowCount) {
			return;
		} else {
			super.rowsInserted(firstRow, endRow);
		}
	}

	/**
	 * Invoked when rows have been deleted from the underlying model in the
	 * specified range (inclusive).
	 * 
	 * @param firstRow
	 *            the first row
	 * @param endRow
	 *            the last row
	 * @throws IndexOutOfBoundsException
	 *             if either argument is outside the range of the model before
	 *             the change, or <code>firstRow</code> &gt; <code>endRow</code>
	 */
	public void rowsDeleted(int firstRow, int endRow) {
		int newModelRowCount = getModelWrapper().getRowCount();
		if (endRow >= newModelRowCount) {
			return;
		} else {
			super.rowsDeleted(firstRow, endRow);
		}
	}

	/**
	 * remove all header listener.
	 */
	public void removeListener() {
		int i = 0;
		MouseListener[] listener = jtable.getTableHeader().getMouseListeners();
		if (listener != null && listener.length != 0) {
			for (; i < listener.length; i++) {
				jtable.getTableHeader().removeMouseListener(listener[i]);
			}
		}
	}

	/**
	 * Returns the location of <code>index</code> in terms of the underlying
	 * model. That is, for the row <code>index</code> in the coordinates of the
	 * view this returns the row index in terms of the underlying model.
	 * 
	 * @param index
	 *            the row index in terms of the underlying view
	 * @return row index in terms of the view
	 * @throws IndexOutOfBoundsException
	 *             if <code>index</code> is outside the range of the view
	 */
	public int convertRowIndexToModel(int index) {
		try {
			return super.convertRowIndexToModel(index);
		} catch (Exception e) {
			return index;
		}
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
		return tableModel.getValueAt(convertRowIndexToModel(row), column);
	}

	/**
	 * Enumeration value indicating the items are sorted in increasing
	 * 
	 * @param colIdx
	 *            index
	 */
	protected void setASCSort(int colIdx) {
		ArrayList<SortKey> key = new ArrayList<SortKey>();
		key.add(new RowSorter.SortKey(colIdx, SortOrder.ASCENDING));
		setSortKeys(key);
	}

	/**
	 * Enumeration value indicating the items are sorted in decreasing order.
	 * 
	 * @param colIdx
	 *            index
	 */
	protected void setDESCSort(int colIdx) {
		ArrayList<SortKey> key = new ArrayList<SortKey>();
		key.add(new RowSorter.SortKey(colIdx, SortOrder.DESCENDING));
		setSortKeys(key);
	}

	/**
	 * Enumeration value indicating the items are unordered.
	 * 
	 * @param colIdx
	 *            index
	 */
	protected void setUNSort(int colIdx) {
		ArrayList<SortKey> key = new ArrayList<SortKey>();
		key.add(new RowSorter.SortKey(colIdx, SortOrder.UNSORTED));
		setSortKeys(key);
	}

	/**
	 * is not sort.
	 * 
	 * @return boolean
	 */
	protected boolean isUnsorted() {
		List<? extends SortKey> keys = this.getSortKeys();
		int keySize = keys.size();
		return (keySize == 0 || keys.get(0).getSortOrder() == SortOrder.UNSORTED);
	}

	/**
	 * set all header is no sort.
	 */
	protected void setAllUnSortable() {
		int column;
		for (column = 0; column < jtable.getColumnCount(); column++) {
			ArrayList<SortKey> key = new ArrayList<SortKey>();
			key.add(new RowSorter.SortKey(column, SortOrder.UNSORTED));
			setSortKeys(key);
			setSortable(column, false);
		}
	}

	/**
	 * set header sort.
	 * 
	 * @param column
	 *            sort column.
	 * @param CtrlDown
	 *            is control down.
	 */
	protected void setSorterStatus(int column, boolean CtrlDown) {
		List<SortKey> SortStatus = new ArrayList<SortKey>(getSortKeys());
		SortKey sortKey = null;
		int sortIndex = -1;
		for (sortIndex = SortStatus.size() - 1; sortIndex >= 0; sortIndex--) {
			if (SortStatus.get(sortIndex).getColumn() == column) {
				break;
			}
		}
		if (sortIndex == -1) {
			// Key doesn't exist
			if (CtrlDown) {
				sortKey = new SortKey(column, SortOrder.DESCENDING);
			} else {
				sortKey = new SortKey(column, SortOrder.ASCENDING);
			}
			SortStatus.add(0, sortKey);
		} else if (sortIndex == 0) {
			// It's the primary sorting key, toggle it
			SortStatus.set(0, toggle(SortStatus.get(0), CtrlDown));
		} else {
			// It's not the first, but was sorted on, remove old
			// entry, insert as first with ascending.
			SortStatus.remove(sortIndex);
			if (CtrlDown) {
				sortKey = new SortKey(column, SortOrder.DESCENDING);
			} else {
				sortKey = new SortKey(column, SortOrder.ASCENDING);
			}
			SortStatus.add(0, sortKey);
		}
		if (SortStatus.size() > getMaxSortKeys()) {
			SortStatus = SortStatus.subList(0, getMaxSortKeys());
		}
		setSortable(column, true);
		setSortKeys(SortStatus);
		setSortable(column, false);
	}

	/**
	 * next status get.
	 * 
	 * @param key
	 *            sort key
	 * @param CtrlDown
	 *            is control down.
	 * @return sort key.
	 */
	private SortKey toggle(SortKey key, boolean CtrlDown) {

		if (key.getSortOrder() == SortOrder.ASCENDING) {
			return new SortKey(key.getColumn(), SortOrder.DESCENDING);
		} else if (key.getSortOrder() == SortOrder.DESCENDING) {
			return new SortKey(key.getColumn(), SortOrder.UNSORTED);
		} else {
			return new SortKey(key.getColumn(), SortOrder.ASCENDING);
		}
	}
}
