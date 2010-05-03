package yearcalendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Timer;

/**
 * A timer encapsulating <code>YearCalendar</code>. It will refresh "today" with
 * time passing.<br>
 * <br>
 */
public class YearTimer extends YearCalendar.YearChooser implements
		ActionListener {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A timer.
	 */
	private Timer timer;

	/**
	 * The date of today
	 */
	private int today;

	/**
	 * A temporary <code>Calendar</code> object.
	 */
	private Calendar tmpCal;

	/**
	 * Default constructor.
	 */
	public YearTimer() {
		this(new YearCalendar());
	}

	/**
	 * Constructor: Creates a timer for specific <code>YearCalendar</code>.
	 * 
	 * @param cal
	 */
	public YearTimer(YearCalendar cal) {
		super(cal);
		timer = new Timer(1000, this);// Refresh every second
		today = cal.calendar.get(Calendar.DATE);
		tmpCal = Calendar.getInstance();
		initialize();
	}

	/**
	 * This method initializes this.
	 */
	private void initialize() {
		actionPerformed(null);// Just to display current date when startup
	}

	/**
	 * @see java.awt.Component#addNotify()
	 */
	public void addNotify() {
		super.addNotify();
		timer.start();
	}

	/**
	 * @see java.awt.Component#removeNotify()
	 */
	public void removeNotify() {
		timer.stop();
		super.removeNotify();
	}

	/**
	 * This operation executes once after a second.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Date current = new Date();
		tmpCal.setTime(current);
		if (tmpCal.get(Calendar.DATE) != today) {// If you change the calendar
													// by choosing a year,
			// it will resume automatically only when the next day coming.
			try {
				restore(current);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
