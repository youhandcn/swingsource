package jtable.cell;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * This is an implementation of <code>TableModel</code> that uses a
 * <code>Vector</code> of <code>Vectors</code> to store the cell value
 * objects.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.celltest MyProgressTableModel.java <br>
 *         2008 2008/04/01 11:20:58 <br>
 */
public class MyProgressTableModel extends DefaultTableModel {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/** the progress's max value */
	public static final int MAX = 100;
	/** the progress's min value */
	public static final int MIN = 0;

	/**
	 * This is an implementation of <code>TableModel</code> that uses a
	 * <code>Vector</code> of <code>Vectors</code> to store the cell value
	 * objects.
	 */
	public MyProgressTableModel() {
		super();
		createListener();
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
	public MyProgressTableModel(Vector<?> data, Vector<?> columnNames) {
		super(data, columnNames);
		createListener();
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
	 */
	public MyProgressTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
		createListener();
	}

	/**
	 * create table model listener.
	 */
	private void createListener() {
		this.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int col = e.getColumn();
					if (col == 1) {
						int row = e.getFirstRow();
						TableModel model = (TableModel) e.getSource();
						Integer value = (Integer) model.getValueAt(row, col);
						model.setValueAt(checkMinMax(value), row, ++col);
					}
				}
			}
		});
	}

	/**
	 * Returns <code>Object.class</code> regardless of
	 * <code>columnIndex</code>.
	 * 
	 * @param columnIndex
	 *            the column being queried
	 * @return the Object.class
	 */
	public Class<?> getColumnClass(int col) {
		switch (col) {
		case 0:
			return String.class;
		case 1:
			return Integer.class;
		case 2:
			return Integer.class;
		default:
			return Object.class;
		}
	}

	/**
	 * Returns false. This is the default implementation for all cells.
	 * 
	 * @param rowIndex
	 *            the row being queried
	 * @param columnIndex
	 *            the column being queried
	 * @return false
	 */
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 2:
			return false;
		default:
			return true;
		}
	}

	/**
	 * This empty implementation is provided so users don't have to implement
	 * this method if their data model is not edit able.
	 * 
	 * @param aValue
	 *            value to assign to cell
	 * @param rowIndex
	 *            row of cell
	 * @param columnIndex
	 *            column of cell
	 */
	public void setValueAt(Object obj, int row, int col) {
		if (col != 1) {
			super.setValueAt(obj, row, col);
			return;
		}
		try {
			Integer integer = new Integer(obj.toString());
			super.setValueAt(checkMinMax(integer), row, col);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * check the value.
	 * 
	 * @param value
	 *            progress value.
	 * @return true value.
	 */
	private Integer checkMinMax(Integer value) {
		int intValue = value.intValue();
		if (intValue < MIN) {
			intValue = MIN;
		} else if (MAX < intValue) {
			intValue = MAX;
		}
		return new Integer(intValue);
	}
}
