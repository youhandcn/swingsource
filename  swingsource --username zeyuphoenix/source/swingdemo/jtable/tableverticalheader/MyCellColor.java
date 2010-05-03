package jtable.tableverticalheader;

import java.awt.Color;

/**
 * the interface that about cell Color.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyCellColor.java <br>
 *         2008 2008/03/28 17:07:30 <br>
 */
public interface MyCellColor {
	/**
	 * get cell Fore ground color.
	 * 
	 * @param row
	 *            cell row index.
	 * @param column
	 *            cell column index.
	 * @return cell Fore ground color.
	 */
	public Color getForeground(int row, int column);

	/**
	 * set cell Fore ground color.
	 * 
	 * @param color
	 *            cell Fore ground color.
	 * @param row
	 *            cell row index.
	 * @param column
	 *            cell column index.
	 */
	public void setForeground(Color color, int row, int column);

	/**
	 * set cell Fore ground color.
	 * 
	 * @param color
	 *            cell Fore ground color.
	 * @param rows
	 *            cell row index.
	 * @param columns
	 *            cell column index.
	 */
	public void setForeground(Color color, int[] rows, int[] columns);

	/**
	 * get cell back ground color.
	 * 
	 * @param row
	 *            cell row index.
	 * @param column
	 *            cell column index.
	 * @return back ground color
	 */
	public Color getBackground(int row, int column);

	/**
	 * set cell back ground color.
	 * 
	 * @param color
	 *            cell back ground color.
	 * @param row
	 *            cell row index.
	 * @param column
	 *            cell column index.
	 */
	public void setBackground(Color color, int row, int column);

	/**
	 * set cell back ground color.
	 * 
	 * @param color
	 *            cell back ground color.
	 * @param rows
	 *            cell row index.
	 * @param columns
	 *            cell column index.
	 */
	public void setBackground(Color color, int[] rows, int[] columns);

}
