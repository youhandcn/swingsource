package cellspan;

import java.awt.Dimension;

/**
 * set cell Attribute font and so on.
 */
public class CustomCell implements ICellAttribute, ICellSpan {
    /** cell width. */
    private int rowSize = 0;
    /** cell height. */
    private int columnSize = 0;
    /** cell spans. */
    private int[][][] span = null;

    /**
     * set cell attribute.
     */
    public CustomCell() {
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
    public CustomCell(int rowSize, int columnSize) {
        setSize(new Dimension(columnSize, rowSize));
    }

    /**
     * @see see the interface.
     */
    @Override
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
    @Override
    public void setSpan(int[] span, int row, int column) {
        if (isOutOfBounds(row, column))
            return;
        this.span[row][column] = span;
    }

    /**
     * @see see the interface.
     */
    @Override
    public boolean isVisible(int row, int column) {
        if (isOutOfBounds(row, column))
            return false;
        if ((span[row][column][ICellSpan.COLUMN] < 1)
                || (span[row][column][ICellSpan.ROW] < 1))
            return false;
        return true;
    }

    /**
     * @see see the interface.
     */
    @Override
    public void combine(int[] rows, int[] columns) {
        if (isOutOfBounds(rows, columns))
            return;
        int rowSpan = rows.length;
        int columnSpan = columns.length;
        int startRow = rows[0];
        int startColumn = columns[0];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                if ((span[startRow + i][startColumn + j][ICellSpan.COLUMN] != 1)
                        || (span[startRow + i][startColumn + j][ICellSpan.ROW] != 1)) {
                    return;
                }
            }
        }
        for (int i = 0, ii = 0; i < rowSpan; i++, ii--) {
            for (int j = 0, jj = 0; j < columnSpan; j++, jj--) {
                span[startRow + i][startColumn + j][ICellSpan.COLUMN] = jj;
                span[startRow + i][startColumn + j][ICellSpan.ROW] = ii;
            }
        }
        span[startRow][startColumn][ICellSpan.COLUMN] = columnSpan;
        span[startRow][startColumn][ICellSpan.ROW] = rowSpan;

    }

    /**
     * @see see the interface.
     */
    @Override
    public void split(int row, int column) {
        if (isOutOfBounds(row, column))
            return;
        int columnSpan = span[row][column][ICellSpan.COLUMN];
        int rowSpan = span[row][column][ICellSpan.ROW];
        for (int i = 0; i < rowSpan; i++) {
            for (int j = 0; j < columnSpan; j++) {
                span[row + i][column + j][ICellSpan.COLUMN] = 1;
                span[row + i][column + j][ICellSpan.ROW] = 1;
            }
        }
    }

    /**
     * @see see the interface.
     */
    @Override
    public void addColumn() {
        int[][][] oldSpan = span;
        int numRows = oldSpan.length;
        int numColumns = oldSpan[0].length;
        span = new int[numRows][numColumns + 1][2];
        System.arraycopy(oldSpan, 0, span, 0, numRows);
        for (int i = 0; i < numRows; i++) {
            span[i][numColumns][ICellSpan.COLUMN] = 1;
            span[i][numColumns][ICellSpan.ROW] = 1;
        }
    }

    /**
     * @see see the interface.
     */
    @Override
    public void addRow() {
        int[][][] oldSpan = span;
        int numRows = oldSpan.length;
        int numColumns = oldSpan[0].length;
        span = new int[numRows + 1][numColumns][2];
        System.arraycopy(oldSpan, 0, span, 0, numRows);
        for (int i = 0; i < numColumns; i++) {
            span[numRows][i][ICellSpan.COLUMN] = 1;
            span[numRows][i][ICellSpan.ROW] = 1;
        }
    }

    /**
     * @see see the interface.
     */
    @Override
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
            span[row][i][ICellSpan.COLUMN] = 1;
            span[row][i][ICellSpan.ROW] = 1;
        }
    }

    /**
     * @see see the interface.
     */
    @Override
    public Dimension getSize() {
        return new Dimension(rowSize, columnSize);
    }

    /**
     * @see see the interface.
     */
    @Override
    public void setSize(Dimension size) {
        columnSize = size.width;
        rowSize = size.height;
        span = new int[rowSize][columnSize][2]; // 2: COLUMN,ROW
        initValue();
    }

    /**
     * set cell init.
     */
    private void initValue() {
        for (int i = 0; i < span.length; i++) {
            for (int j = 0; j < span[i].length; j++) {
                span[i][j][ICellSpan.COLUMN] = 1;
                span[i][j][ICellSpan.ROW] = 1;
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
}
