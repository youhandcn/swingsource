package simpleclock;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;

/**
 * This bean to define basic properties and behaviors of a clock, concrete
 * instances will be implemented by <code>AnalogClock</code> and
 * <code>DigitalClock</code>.
 */
public abstract class Clock extends JComponent implements SmartCalendar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defines a analog-type clock.
	 */
	public static final int ANALOG_TYPE = 0;

	/**
	 * Defines a digital-type clock.
	 */
	public static final int DIGITAL_TYPE = 1;

	/**
	 * Default mode: Hide am/pm, stopwatch, and timezone, display as hour24
	 * format.
	 */
	public static final int DEFAULT_MODE = 0;

	/**
	 * First bit, stopwatch mode:<br>
	 * 0 Hide stopwatch<br>
	 * 1 Show stopwatch
	 */
	public static final int STOPWATCH_MODE = 1;

	/**
	 * Font rendering context - assumes no default transform, anti-aliasing
	 * active and fractional metrics allowed.
	 */
	public static final FontRenderContext frc = new FontRenderContext(null,
			true, true);

	/**
	 * The calendar instance for this clock.
	 */
	protected Calendar calendar;

	/**
	 * Additional message for this clock.
	 */
	protected Object message;

	/**
	 * @see #getType()
	 */
	protected int type;

	/**
	 * Display mode of this clock.
	 */
	protected int mode;

	/**
	 * @see #getBgImage()
	 */
	protected Image bgImage;

	/**
	 * @see #isStopwatch()
	 */
	protected boolean stopwatch;

	/**
	 * @see #getAlarm()
	 */
	protected Date alarm;

	/**
	 * @see #getAlarmDelay()
	 */
	protected int alarmDelay;

	/**
	 * Default constructor: Creates a Clock instance by given type. Available
	 * type:
	 * <ul>
	 * <li><code>ANALOG_TYPE</code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Analog-type
	 * clock</li>
	 * <li><code>DIGITAL_TYPE</code>&nbsp;&nbsp;&nbsp;Digital-type clock</li>
	 * </ul>
	 * 
	 * @param type
	 *            clock type.
	 */
	protected Clock(int type) {
		this.type = type;
		mode = DEFAULT_MODE;
		calendar = Calendar.getInstance();
		message = null;
		initialize();
	}

	/**
	 * This method initializes GUI.
	 * 
	 * @return void
	 */
	private void initialize() {
		repaint();
	}

	/**
	 * Checks clock type, if not available, just display a piece of info.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		switch (type) {
		case ANALOG_TYPE:
		case DIGITAL_TYPE:
			super.paintComponent(g);
			break;
		default:
			Graphics2D g2 = (Graphics2D) g;
			if (message == null)
				message = "Clock type " + type + " is not available now.";
			AttributedString attString = new AttributedString((String) message);
			attString.addAttribute(TextAttribute.FONT, getFont());
			attString.addAttribute(TextAttribute.FOREGROUND, getForeground());
			TextLayout textLayout = new TextLayout(attString.getIterator(), frc);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawString(attString.getIterator(), 1, (int) (textLayout
					.getAscent()));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		}
	}

	/**
	 * Alarm time. If null, then do not have alarm function.
	 * 
	 * @return the actual time when alarming.
	 */
	public Date getAlarm() {
		if (alarm == null)
			return null;

		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		String time = df.format(alarm);
		df.applyPattern("yyyy-MM-dd");
		String day = df.format(new Date());
		df.applyPattern("yyyy-MM-dd HH:mm:ss");
		try {
			alarm = df.parse(day + " " + time);
			return alarm;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Sets alarm time for delay seconds.
	 * 
	 * @param alarm
	 * @param delay
	 *            alarm delay seconds
	 */
	public void setAlarm(Date alarm, int delay) {
		this.alarm = alarm;
		this.alarmDelay = delay;
	}

	/**
	 * Alarm delay seconds.
	 * 
	 * @return alarm delay seconds.
	 */
	public int getAlarmDelay() {
		return alarmDelay;
	}

	/**
	 * Sets alarm delay seconds.
	 * 
	 * @param delay
	 */
	public void setAlarmDelay(int delay) {
		this.alarmDelay = delay;
	}

	/**
	 * Do something when alarm is triggered. Will be implemented by concrete
	 * derived class.
	 */
	public abstract void doAlarm();

	/**
	 * Background image.
	 */
	public Image getBgImage() {
		return bgImage;
	}

	/**
	 * Sets background image.
	 * 
	 * @param bgImage
	 */
	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
	}

	/**
	 * This clock has stopwatch function or not.
	 */
	public boolean isStopwatch() {
		return stopwatch;
	}

	/**
	 * Turns on/off the stopwatch.
	 * 
	 * @param stopwatch
	 */
	public void setStopwatch(boolean stopwatch) {
		if (stopwatch && !isStopwatch() || !stopwatch && isStopwatch()) {// Invoke
																			// only
																			// when
																			// stopwatch
																			// tag
																			// changed.
			this.stopwatch = stopwatch;
			mode ^= STOPWATCH_MODE; // Clear or set the tag.
		}
	}

	/**
	 * Type of this clock, now only <code>ANALOG_TYPE</code> and
	 * <code>DIGITAL_TYPE</code> are available.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the clock type. Available type:<br>
	 * <code>ANALOG_TYPE</code>, <code>DIGITAL_TYPE</code>
	 * 
	 * @param type
	 */
	protected void setType(int type) {
		this.type = type;
	}

	/**
	 * Current mode used. Digital-type and analog-type clock have differnet
	 * modes. For digital-type, can display stopwatch, a.m./p.m., or timezone
	 * separately as accessories.
	 * 
	 * @param mode
	 */
	public void setMode(int mode) {
		this.mode = mode;
	}

	/**
	 * Gets the mode of this clock bean.
	 * 
	 * @return the mode of this clock bean.
	 */
	public int getMode() {
		return mode;
	}

	/**
	 * @see study.SmartCalendar#getCalendar()
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @see study.SmartCalendar#setCalendar(java.util.Calendar)
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	/**
	 * @see study.SmartCalendar#getMessage()
	 */
	public Object getMessage() {
		return message;
	}

	/**
	 * @see study.SmartCalendar#setMessage(java.lang.Object)
	 */
	public void setMessage(Object message) {
		this.message = message;
	}
}
