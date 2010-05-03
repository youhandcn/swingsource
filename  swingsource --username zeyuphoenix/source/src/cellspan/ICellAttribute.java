package cellspan;

import java.awt.Dimension;

/**
 * the interface that about cell attribute.
 */
public interface ICellAttribute {
	/**
	 * add column to cell.
	 */
	public void addColumn();

	/**
	 * add row to cell.
	 */
	public void addRow();

	/**
	 * insert row to cell
	 * 
	 * @param row
	 *            the row index.
	 */
	public void insertRow(int row);

	/**
	 * get cell size.
	 * 
	 * @return cell size.
	 */
	public Dimension getSize();

	/**
	 * set cell size.
	 * 
	 * @param size
	 *            cell size.
	 */
	public void setSize(Dimension size);

}
