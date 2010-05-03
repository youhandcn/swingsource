package jtable.tableverticalheader;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * set cell Attribute font and so on.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyTabelCell.java <br>
 *         2008 2008/03/28 17:22:53 <br>
 */
public class MyVerticalHeaderCell implements MyCellAttribute, MyCellColor,
		MyCellFont, MyCellSpan {
	/** cell width. */
	private int rowSize = 0;
	/** cell height. */
	private int columnSize = 0;
	/** cell spans. */
	private int[][][] span = null;
	/** cell fore color. */
	private Color[][] foreground = null;
	/** cell back color. */
	private Color[][] background = null;
	/** cell font. */
	private Font[][] font = null;

	/**
	 * set cell attribute.
	 */
	public MyVerticalHeaderCell() {
		this(1, 1);
	}

	/**
	 * set cell attribute.
	 * 
	 * @param rowSize
	 *            set cell height
	 * @param columnSize
	 *            set cell width
	 */
	public MyVerticalHeaderCell(int rowSize, int columnSize) {
		setSize(new Dimension(columnSize, rowSize));
	}

	/**
	 * @see see the interface.
	 */
	public int[] getSpan(int row, int column) {
		if (isOutOfBounds(row, column)) {
			int[] ret_code = { 1, 1 };
			return ret_code;
		}
		return span[row][column];
	}

	/**
	 * @see see the interface.
	 */
	public void setSpan(int[] span, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		this.span[row][column] = span;
	}

	/**
	 * @see see the interface.
	 */
	public boolean isVisible(int row, int column) {
		if (isOutOfBounds(row, column))
			return false;
		if ((span[row][column][MyCellSpan.COLUMN] < 1)
				|| (span[row][column][MyCellSpan.ROW] < 1))
			return false;
		return true;
	}

	/**
	 * @see see the interface.
	 */
	public void combine(int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		int rowSpan = rows.length;
		int columnSpan = columns.length;
		int startRow = rows[0];
		int startColumn = columns[0];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				if ((span[startRow + i][startColumn + j][MyCellSpan.COLUMN] != 1)
						|| (span[startRow + i][startColumn + j][MyCellSpan.ROW] != 1)) {
					return;
				}
			}
		}
		for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
			for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
				span[startRow + i][startColumn + j][MyCellSpan.COLUMN] = jj;
				span[startRow + i][startColumn + j][MyCellSpan.ROW] = ii;
			}
		}
		span[startRow][startColumn][MyCellSpan.COLUMN] = columnSpan;
		span[startRow][startColumn][MyCellSpan.ROW] = rowSpan;

	}

	/**
	 * @see see the interface.
	 */
	public void split(int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		int columnSpan = span[row][column][MyCellSpan.COLUMN];
		int rowSpan = span[row][column][MyCellSpan.ROW];
		for (int i = 0; i < rowSpan; i++) {
			for (int j = 0; j < columnSpan; j++) {
				span[row + i][column + j][MyCellSpan.COLUMN] = 1;
				span[row + i][column + j][MyCellSpan.ROW] = 1;
			}
		}
	}

	/**
	 * @see see the interface.
	 */
	public Color getForeground(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return foreground[row][column];
	}

	/**
	 * @see see the interface.
	 */
	public void setForeground(Color color, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		foreground[row][column] = color;
	}

	/**
	 * @see see the interface.
	 */
	public void setForeground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(foreground, color, rows, columns);
	}

	/**
	 * @see see the interface.
	 */
	public Color getBackground(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return background[row][column];
	}

	/**
	 * @see see the interface.
	 */
	public void setBackground(Color color, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		background[row][column] = color;
	}

	/**
	 * @see see the interface.
	 */
	public void setBackground(Color color, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(background, color, rows, columns);
	}

	/**
	 * @see see the interface.
	 */
	public Font getFont(int row, int column) {
		if (isOutOfBounds(row, column))
			return null;
		return font[row][column];
	}

	/**
	 * @see see the interface.
	 */
	public void setFont(Font font, int row, int column) {
		if (isOutOfBounds(row, column))
			return;
		this.font[row][column] = font;
	}

	/**
	 * @see see the interface.
	 */
	public void setFont(Font font, int[] rows, int[] columns) {
		if (isOutOfBounds(rows, columns))
			return;
		setValues(this.font, font, rows, columns);
	}

	/**
	 * @see see the interface.
	 */
	public void addColumn() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows][numColumns + 1][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numRows; i++) {
			span[i][numColumns][MyCellSpan.COLUMN] = 1;
			span[i][numColumns][MyCellSpan.ROW] = 1;
		}
	}

	/**
	 * @see see the interface.
	 */
	public void addRow() {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		System.arraycopy(oldSpan, 0, span, 0, numRows);
		for (int i = 0; i < numColumns; i++) {
			span[numRows][i][MyCellSpan.COLUMN] = 1;
			span[numRows][i][MyCellSpan.ROW] = 1;
		}
	}

	/**
	 * @see see the interface.
	 */
	public void insertRow(int row) {
		int[][][] oldSpan = span;
		int numRows = oldSpan.length;
		int numColumns = oldSpan[0].length;
		span = new int[numRows + 1][numColumns][2];
		if (0 < row) {
			System.arraycopy(oldSpan, 0, span, 0, row - 1);
		}
		System.arraycopy(oldSpan, 0, span, row, numRows - row);
		for (int i = 0; i < numColumns; i++) {
			span[row][i][MyCellSpan.COLUMN] = 1;
			span[row][i][MyCellSpan.ROW] = 1;
		}
	}

	/**
	 * @see see the interface.
	 */
	public Dimension getSize() {
		return new Dimension(rowSize, columnSize);
	}

	/**
	 * @see see the interface.
	 */
	public void setSize(Dimension size) {
		columnSize = size.width;
		rowSize = size.height;
		span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
		foreground = new Color[rowSize][columnSize];
		background = new Color[rowSize][columnSize];
		font = new Font[rowSize][columnSize];
		initValue();
	}

	/**
	 * set cell init.
	 */
	private void initValue() {
		for (int i = 0; i < span.length; i++) {
			for (int j = 0; j < span[i].length; j++) {
				span[i][j][MyCellSpan.COLUMN] = 1;
				span[i][j][MyCellSpan.ROW] = 1;
			}
		}
	}

	/**
	 * get the cell is out of bound.
	 * 
	 * @param row
	 *            cell row index.
	 * @param column
	 *            cell column index.
	 * @return is out of bound.
	 */
	private boolean isOutOfBounds(int row, int column) {
		if ((row < 0) || (rowSize <= row) || (column < 0)
				|| (columnSize <= column)) {
			return true;
		}
		return false;
	}

	/**
	 * get the cells is out of bounds.
	 * 
	 * @param rows
	 *            cell row indexes.
	 * @param columns
	 *            cell column indexes.
	 * @return is out of bounds.
	 */
	private boolean isOutOfBounds(int[] rows, int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			if ((rows[i] < 0) || (rowSize <= rows[i]))
				return true;
		}
		for (int i = 0; i < columns.length; i++) {
			if ((columns[i] < 0) || (columnSize <= columns[i]))
				return true;
		}
		return false;
	}

	/**
	 * set cell values.
	 * 
	 * @param target
	 *            svae cell bean.
	 * @param value
	 *            cell value.
	 * @param rows
	 *            cell row index.
	 * @param columns
	 *            cell column index.
	 */
	private void setValues(Object[][] target, Object value, int[] rows,
			int[] columns) {
		for (int i = 0; i < rows.length; i++) {
			int row = rows[i];
			for (int j = 0; j < columns.length; j++) {
				int column = columns[j];
				target[row][column] = value;
			}
		}
	}
}
