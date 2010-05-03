package jtable.tableverticalheader;

import java.awt.Font;

/**
 * the interface that about cell Font.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableVerticalHeader MyCellFont.java <br>
 *         2008 2008/03/28 17:08:40 <br>
 */
public interface MyCellFont {
	/**
	 * get cell font.
	 * 
	 * @param row
	 *            cell row index
	 * @param column
	 *            cell column index
	 * @return font
	 */
	public Font getFont(int row, int column);

	/**
	 * set cell font.
	 * 
	 * @param font
	 *            set font
	 * @param row
	 *            cell row index
	 * @param column
	 *            cell column index
	 */
	public void setFont(Font font, int row, int column);

	/**
	 * set cells font.
	 * 
	 * @param font
	 *            set font
	 * @param rows
	 *            cell row index
	 * @param columns
	 *            cell columns index
	 */
	public void setFont(Font font, int[] rows, int[] columns);

}
