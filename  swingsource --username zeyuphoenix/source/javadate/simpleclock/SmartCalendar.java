package simpleclock;

import java.util.Calendar;

/**
 * A common interface to all calendar/date/time bean in this project.
 * 
 */
public interface SmartCalendar {
	/**
	 * Gets the primary calendar object of this bean.
	 * 
	 * @return the <code>Calendar</code> object this bean uses.
	 */
	public Calendar getCalendar();

	/**
	 * Sets the primary calendar object of this bean.
	 * 
	 * @param calendar
	 *            the primary calendar object of this bean.
	 */
	public void setCalendar(Calendar calendar);

	/**
	 * @return an additional message this bean uses.
	 */
	public Object getMessage();

	/**
	 * Sets an additional message this bean uses.
	 * 
	 * @param msg
	 *            an additional message this bean uses.
	 */
	public void setMessage(Object msg);
}
