package jtable.tableverticalheader;

/**
 * the interface that about cell span.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyCellSpan.java <br>
 *         2008 2008/03/28 17:08:00 <br>
 */
public interface MyCellSpan {
	/**
	 * Span start Row.
	 */
	public static final int ROW = 0;
	/**
	 * span column.
	 */
	public static final int COLUMN = 1;

	/**
	 * get cell Span.
	 * 
	 * @param row
	 *            row index.
	 * @param column
	 *            column index.
	 * @return spans.
	 */
	public int[] getSpan(int row, int column);

	/**
	 * set cell span.
	 * 
	 * @param span
	 *            spans
	 * @param row
	 *            row index.
	 * @param column
	 *            column index.
	 */
	public void setSpan(int[] span, int row, int column);

	/**
	 * is cell visible.
	 * 
	 * @param row
	 *            row index.
	 * @param column
	 *            column index.
	 * @return is Visible
	 */
	public boolean isVisible(int row, int column);

	/**
	 * where combine.
	 * 
	 * @param rows
	 *            split row
	 * @param columns
	 *            split column
	 */
	public void combine(int[] rows, int[] columns);

	/**
	 * where split.
	 * 
	 * @param row
	 *            split row
	 * @param column
	 *            split column
	 */
	public void split(int row, int column);

}
