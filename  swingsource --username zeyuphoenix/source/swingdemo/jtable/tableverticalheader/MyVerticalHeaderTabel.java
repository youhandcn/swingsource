package jtable.tableverticalheader;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 * Constructs a <code>JTable</code> that is initialized with.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyVerticalHeaderTabel.java <br>
 *         2008 2008/03/28 18:45:38 <br>
 */
public class MyVerticalHeaderTabel extends JTable {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a default <code>JTable</code> that is initialized with a
	 * default data model, a default column model, and a default selection
	 * model.
	 */
	public MyVerticalHeaderTabel() {
		this(null, null, null);
	}

	/**
	 * Constructs a <code>JTable</code> that is initialized with
	 * <code>dm</code> as the data model, a default column model, and a
	 * default selection model.
	 * 
	 * @param dm
	 *            the data model for the table
	 * @see #createDefaultColumnModel
	 * @see #createDefaultSelectionModel
	 */
	public MyVerticalHeaderTabel(TableModel dm) {
		this(dm, null, null);
	}

	/**
	 * Constructs a <code>JTable</code> that is initialized with
	 * <code>dm</code> as the data model, <code>cm</code> as the column
	 * model, and a default selection model.
	 * 
	 * @param dm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 * @see #createDefaultSelectionModel
	 */
	public MyVerticalHeaderTabel(TableModel dm, TableColumnModel cm) {
		this(dm, cm, null);
	}

	/**
	 * Constructs a <code>JTable</code> that is initialized with
	 * <code>dm</code> as the data model, <code>cm</code> as the column
	 * model, and <code>sm</code> as the selection model.
	 * 
	 * @param dm
	 *            the data model for the table
	 * @param cm
	 *            the column model for the table
	 * @param sm
	 *            the row selection model for the table
	 */
	public MyVerticalHeaderTabel(TableModel dm, TableColumnModel cm,
			ListSelectionModel sm) {
		super(dm, cm, sm);
		init();

	}

	/**
	 * Constructs a <code>JTable</code> with <code>numRows</code> and
	 * <code>numColumns</code> of empty cells.
	 * 
	 * @param numRows
	 *            the number of rows the table holds
	 * @param numColumns
	 *            the number of columns the table holds
	 * 
	 */
	public MyVerticalHeaderTabel(int numRows, int numColumns) {
		super(numRows, numColumns);
		init();
	}

	/**
	 * Constructs a <code>JTable</code> to display the values in the
	 * <code>Vector</code> of <code>Vectors</code>.
	 * 
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public MyVerticalHeaderTabel(Vector<?> rowData, Vector<?> columnNames) {
		super(rowData, columnNames);
		init();
	}

	/**
	 * Constructs a <code>JTable</code> to display the values in the two
	 * dimensional array, <code>rowData</code>.
	 * 
	 * @param rowData
	 *            the data for the new table
	 * @param columnNames
	 *            names of each column
	 */
	public MyVerticalHeaderTabel(final Object[][] rowData,
			final Object[] columnNames) {
		super(rowData, columnNames);
		init();
	}

	/**
	 * init.
	 */
	private void init() {
		setUI(new MyVerticalHeaderCellUI());
		getTableHeader().setReorderingAllowed(false);
		setCellSelectionEnabled(true);
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	}

	/**
	 * Returns a rectangle for the cell that lies at the intersection of
	 * <code>row</code> and <code>column</code>.
	 * 
	 * @param row
	 *            the row index where the desired cell is located
	 * @param column
	 *            the column index where the desired cell is located in the
	 *            display;
	 * @param includeSpacing
	 *            if false, return the true cell bounds - computed by
	 *            subtracting the intercell spacing from the height and widths
	 *            of the column and row models
	 * 
	 * @return the rectangle containing the cell at location
	 */
	public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
		Rectangle sRect = super.getCellRect(row, column, includeSpacing);
		if ((row < 0) || (column < 0) || (getRowCount() <= row)
				|| (getColumnCount() <= column)) {
			return sRect;
		}
		MyCellSpan cellAtt = (MyCellSpan) ((MyVerticalHeaderModel) getModel())
				.getCellAttribute();
		if (!cellAtt.isVisible(row, column)) {
			int temp_row = row;
			int temp_column = column;
			row += cellAtt.getSpan(temp_row, temp_column)[MyCellSpan.ROW];
			column += cellAtt.getSpan(temp_row, temp_column)[MyCellSpan.COLUMN];
		}
		int[] n = cellAtt.getSpan(row, column);

		int index = 0;
		int columnMargin = getColumnModel().getColumnMargin();
		Rectangle cellFrame = new Rectangle();
		int aCellHeight = rowHeight + rowMargin;
		cellFrame.y = row * aCellHeight;
		cellFrame.height = n[MyCellSpan.ROW] * aCellHeight;

		Enumeration<?> enumeration = getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width = aColumn.getWidth() + columnMargin;
			if (index == column)
				break;
			cellFrame.x += cellFrame.width;
			index++;
		}
		for (int i = 0; i < n[MyCellSpan.COLUMN] - 1; i++) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			cellFrame.width += aColumn.getWidth() + columnMargin;
		}

		if (!includeSpacing) {
			Dimension spacing = getIntercellSpacing();
			cellFrame.setBounds(cellFrame.x + spacing.width / 2, cellFrame.y
					+ spacing.height / 2, cellFrame.width - spacing.width,
					cellFrame.height - spacing.height);
		}
		return cellFrame;
	}

	/**
	 * Returns the index of the row that <code>point</code> lies in, or -1 if
	 * the result is not in the range [0, <code>getRowCount()</code>-1].
	 * 
	 * @param point
	 *            the location of interest
	 * @return the index of the row that <code>point</code> lies in, or -1 if
	 *         the result is not in the range [0, <code>getRowCount()</code>-1]
	 */
	private int[] rowColumnAtPoint(Point point) {
		int[] retValue = { -1, -1 };
		int row = point.y / (rowHeight + rowMargin);
		if ((row < 0) || (getRowCount() <= row))
			return retValue;
		int column = getColumnModel().getColumnIndexAtX(point.x);

		MyCellSpan cellAtt = (MyCellSpan) ((MyVerticalHeaderModel) getModel())
				.getCellAttribute();

		if (cellAtt.isVisible(row, column)) {
			retValue[MyCellSpan.COLUMN] = column;
			retValue[MyCellSpan.ROW] = row;
			return retValue;
		}
		retValue[MyCellSpan.COLUMN] = column
				+ cellAtt.getSpan(row, column)[MyCellSpan.COLUMN];
		retValue[MyCellSpan.ROW] = row
				+ cellAtt.getSpan(row, column)[MyCellSpan.ROW];
		return retValue;
	}

	/**
	 * Returns the index of the row that <code>point</code> lies in, or -1 if
	 * the result is not in the range [0, <code>getRowCount()</code>-1].
	 * 
	 * @param point
	 *            the location of interest
	 * @return the index of the row that <code>point</code> lies in, or -1 if
	 *         the result is not in the range [0, <code>getRowCount()</code>-1]
	 * @see #columnAtPoint
	 */
	public int rowAtPoint(Point point) {
		return rowColumnAtPoint(point)[MyCellSpan.ROW];
	}

	/**
	 * Returns the index of the column that <code>point</code> lies in, or -1
	 * if the result is not in the range [0, <code>getColumnCount()</code>-1].
	 * 
	 * @param point
	 *            the location of interest
	 * @return the index of the column that <code>point</code> lies in, or -1
	 *         if the result is not in the range [0,
	 *         <code>getColumnCount()</code>-1]
	 * @see #rowAtPoint
	 */
	public int columnAtPoint(Point point) {
		return rowColumnAtPoint(point)[MyCellSpan.COLUMN];
	}

	/**
	 * Track that the selection model of the TableColumnModel changed.
	 * 
	 * @param e
	 *            the event received
	 */
	public void columnSelectionChanged(ListSelectionEvent e) {
		repaint();
	}

	/**
	 * Invoked when the row selection changes -- repaints to show the new
	 * selection.
	 * <p>
	 * Application code will not use these methods explicitly, they are used
	 * internally by JTable.
	 * 
	 * @param e
	 *            the event received
	 */
	public void valueChanged(ListSelectionEvent e) {
		int firstIndex = e.getFirstIndex();
		int lastIndex = e.getLastIndex();
		if (firstIndex == -1 && lastIndex == -1) { // Selection cleared.
			repaint();
		}
		Rectangle dirtyRegion = getCellRect(firstIndex, 0, false);
		int numCoumns = getColumnCount();
		int index = firstIndex;
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		index = lastIndex;
		for (int i = 0; i < numCoumns; i++) {
			dirtyRegion.add(getCellRect(index, i, false));
		}
		repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width,
				dirtyRegion.height);
	}
}
