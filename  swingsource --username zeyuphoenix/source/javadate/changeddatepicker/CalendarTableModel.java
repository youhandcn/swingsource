package changeddatepicker;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * CalendarTableModel
 * 
 * @author shuai.zhang
 * 
 */
public class CalendarTableModel implements TableModel, SpinnerModel {
	/** a concrete subclass of Calendar */
	private GregorianCalendar calendar = null;
	/** spinner */
	private Vector<ChangeListener> changeListeners = null;
	/** monthLabels */
	private Vector<JLabel> monthLabels = null;
	/** table listeners. */
	private Vector<TableModelListener> tableModelListeners = null;

	/**
	 * CalendarTableModel
	 */
	public CalendarTableModel() {
		calendar = new GregorianCalendar();
		changeListeners = new Vector<ChangeListener>();
		tableModelListeners = new Vector<TableModelListener>();
		monthLabels = new Vector<JLabel>();
	}

	/**
	 * calendar clone.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendarClone() {

		return (Calendar) calendar.clone();
	}

	/**
	 * Sets this Calendar's time with the given <code>Date</code>.
	 * 
	 * @param date
	 *            the given Date.
	 */
	public void setCalendar(Date date) {
		calendar.setTime(date);
		fireCalendarInvalidated();
	}

	/**
	 * Sets the given calendar field to the given value.
	 * 
	 * @param field
	 *            the given calendar field.
	 * @param value
	 *            the value to be set for the given calendar field.
	 */
	public void setCalendar(int field, int value) {
		calendar.set(field, value);
		fireCalendarInvalidated();
	}

	/**
	 * add month.
	 * 
	 * @param label
	 *            month.
	 */
	public void addMonthLabel(JLabel label) {
		monthLabels.add(label);
		fireMonthTextFieldsUpdate();
	}

	/**
	 * remove month
	 * 
	 * @param label
	 *            month.
	 */
	public void removeMonthLabel(JLabel label) {
		monthLabels.remove(label);
	}

	/**
	 * add day.
	 * 
	 * @param label
	 *            day
	 */
	public void addTodayButton(JButton button) {
		DateFormat df1 = DateFormat.getDateInstance(DateFormat.MEDIUM);
		button.setText("today is: " + df1.format(new Date()));
	}

	/**
	 * 
	 * Sets the values for the calendar fields YEAR, MONTH, and DAY_OF_MONTH.
	 * 
	 * @param year
	 *            the value used to set the <code>YEAR</code> calendar field.
	 * @param month
	 *            the value used to set the <code>MONTH</code> calendar field.
	 * @param date
	 *            the value used to set the <code>DAY_OF_MONTH</code> calendar
	 *            field.
	 */
	public void setCalendar(int year, int month, int date) {
		calendar.set(year, month, date);
		fireCalendarInvalidated();
	}

	/**
	 * Returns the number of rows in the model.
	 * 
	 * @return the number of rows in the model
	 */
	@Override
	public int getRowCount() {
		Calendar firstDayOfMonth = getCalendarClone();
		firstDayOfMonth.set(Calendar.DATE, 1);
		int nullDay = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
		return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + nullDay - 1) / 7 + 1;
	}

	/**
	 * Returns the number of columns in the model.
	 * 
	 * @return the number of columns in the model
	 */
	@Override
	public int getColumnCount() {
		return Calendar.DAY_OF_WEEK;
	}

	/**
	 * Returns the name of the column at <code>columnIndex</code>.
	 * 
	 * @param columnIndex
	 *            the index of the column
	 */
	@Override
	public String getColumnName(int columnIndex) {
		String columnName = "";
		switch (columnIndex + 1) {
		case Calendar.SUNDAY:
			columnName = "Sun";
			break;
		case Calendar.MONDAY:
			columnName = "Mon";
			break;
		case Calendar.TUESDAY:
			columnName = "Tus";
			break;
		case Calendar.WEDNESDAY:
			columnName = "Wed";
			break;
		case Calendar.THURSDAY:
			columnName = "Thu";
			break;
		case Calendar.FRIDAY:
			columnName = "Fri";
			break;
		case Calendar.SATURDAY:
			columnName = "Sat";
			break;
		default:
			break;
		}
		return columnName;
	}

	/**
	 * Returns the most specific superclass for all the cell values in the
	 * column.
	 * 
	 * @param columnIndex
	 *            the index of the column
	 * @return the common ancestor class of the object values in the model.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	/**
	 * Returns true if the cell at <code>rowIndex</code> and
	 * <code>columnIndex</code> is editable.
	 * 
	 * @param rowIndex
	 *            the row whose value to be queried
	 * @param columnIndex
	 *            the column whose value to be queried
	 * @return true if the cell is editable
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Returns the value for the cell at <code>columnIndex</code> and
	 * <code>rowIndex</code>.
	 * 
	 * @param rowIndex
	 *            the row whose value is to be queried
	 * @param columnIndex
	 *            the column whose value is to be queried
	 * @return the value Object at the specified cell
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Calendar firstDayOfMonth = getCalendarClone();
		firstDayOfMonth.set(Calendar.DATE, 1);
		int nullDay = firstDayOfMonth.get(Calendar.DAY_OF_WEEK);
		int value = columnIndex - nullDay + rowIndex * 7 + 2;
		return new Integer(value);
	}

	/**
	 * Sets the value in the cell at <code>columnIndex</code> and
	 * <code>rowIndex</code> to <code>aValue</code>.
	 * 
	 * @param aValue
	 *            the new value
	 * @param rowIndex
	 *            the row whose value is to be changed
	 * @param columnIndex
	 *            the column whose value is to be changed
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

	}

	/**
	 * Adds a listener to the list that is notified each time a change to the
	 * data model occurs.
	 * 
	 * @param l
	 *            the TableModelListener
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		tableModelListeners.add(l);
	}

	/**
	 * Removes a listener from the list that is notified each time a change to
	 * the data model occurs.
	 * 
	 * @param l
	 *            the TableModelListener
	 */
	@Override
	public void removeTableModelListener(TableModelListener l) {
		tableModelListeners.remove(l);
	}

	/**
	 * The <i>current element</i> of the sequence.
	 * 
	 * @return the current spinner value.
	 */
	@Override
	public Object getValue() {
		return Integer.toString(calendar.get(Calendar.YEAR));
	}

	/**
	 * Changes current value of the model, typically this value .
	 */
	@Override
	public void setValue(Object value) {
		int year = Integer.parseInt((String) value);
		setCalendar(Calendar.YEAR, year);
	}

	/**
	 * Return the object in the sequence that comes after the object returned by
	 * <code>getValue()</code>.
	 * 
	 * @return the next legal value or null if one doesn't exist
	 */
	@Override
	public Object getNextValue() {
		return Integer.toString(calendar.get(Calendar.YEAR) + 1);
	}

	/**
	 * Return the object in the sequence that comes before the object returned
	 * by <code>getValue()</code>.
	 * 
	 * @return the previous legal value or null if one doesn't exist
	 */
	@Override
	public Object getPreviousValue() {
		return Integer.toString(calendar.get(Calendar.YEAR) - 1);
	}

	/**
	 * Adds a <code>ChangeListener</code> to the model's listener list.
	 * 
	 * @param l
	 *            the ChangeListener to add
	 */
	@Override
	public void addChangeListener(ChangeListener l) {
		changeListeners.add(l);
	}

	/**
	 * Removes a <code>ChangeListener</code> from the model's listener list.
	 * 
	 * @param l
	 *            the ChangeListener to remove
	 */
	@Override
	public void removeChangeListener(ChangeListener l) {
		changeListeners.remove(l);
	}

	/**
	 * fire change.
	 */
	public void fireCalendarInvalidated() {
		fireCalendarChanged();
		fireTableModelEvent();
		fireMonthTextFieldsUpdate();
	}

	/**
	 * 
	 */
	private void fireCalendarChanged() {
		// notify the spinner view of a change
		Iterator<ChangeListener> i = changeListeners.iterator();
		while (i.hasNext()) {
			ChangeListener cl = (ChangeListener) i.next();
			cl.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * 
	 */
	private void fireMonthTextFieldsUpdate() {
		// change monthLabel text
		Iterator<JLabel> i = monthLabels.iterator();
		DateFormatSymbols df = new DateFormatSymbols();

		while (i.hasNext()) {
			JLabel label = (JLabel) i.next();
			label.setText(df.getMonths()[calendar.get(Calendar.MONTH)]);
		}
	}

	/**
	 * 
	 */
	private void fireTableModelEvent() {
		// notify the table view of a change
		Iterator<TableModelListener> i = tableModelListeners.iterator();
		while (i.hasNext()) {
			TableModelListener tl = (TableModelListener) i.next();
			tl.tableChanged(new TableModelEvent(this));
		}
	}
}
