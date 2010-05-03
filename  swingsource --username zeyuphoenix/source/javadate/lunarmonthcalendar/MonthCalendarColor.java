package lunarmonthcalendar;

import java.awt.Color;
import java.io.Serializable;

/**
 * Color render scheme for <code>MonthCalendar</code>.
 */
public class MonthCalendarColor implements Serializable {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default scheme by using classical settings.
	 */
	public static final MonthCalendarColor DEFAULT = new MonthCalendarColor(
			Color.LIGHT_GRAY, Color.BLACK, Color.LIGHT_GRAY, Color.BLUE
					.darker(), Color.RED, Color.LIGHT_GRAY, Color.GREEN
					.darker(), Color.LIGHT_GRAY, Color.RED.darker(), Color.GRAY);

	/**
	 * Background color of each day cell.
	 */
	public Color bgCell;

	/**
	 * Foreground color of each day cell.
	 */
	public Color fgCell;

	/**
	 * Background color of today cell.
	 */
	public Color bgToday;

	/**
	 * Foreground color of today cell.
	 */
	public Color fgToday;

	/**
	 * Border color of today cell.
	 */
	public Color borderToday;

	/**
	 * Background color of Saturday cell.
	 */
	public Color bgSaturday;

	/**
	 * Foreground color of Saturday cell.
	 */
	public Color fgSaturday;

	/**
	 * Background color of Sunday cell.
	 */
	public Color bgSunday;

	/**
	 * Foreground color of Sunday cell.
	 */
	public Color fgSunday;

	/**
	 * Background color of a selected cell of a day.
	 */
	public Color bgSelected;

	/**
	 * Constructor
	 */
	public MonthCalendarColor(Color bgCell, Color fgCell, Color bgToday,
			Color fgToday, Color borderToday, Color bgSaturday,
			Color fgSaturday, Color bgSunday, Color fgSunday, Color bgSelected) {
		this.bgCell = bgCell;
		this.fgCell = fgCell;
		this.bgToday = bgToday;
		this.fgToday = fgToday;
		this.borderToday = borderToday;
		this.bgSaturday = bgSaturday;
		this.fgSaturday = fgSaturday;
		this.bgSunday = bgSunday;
		this.fgSunday = fgSunday;
		this.bgSelected = bgSelected;
	}
}
