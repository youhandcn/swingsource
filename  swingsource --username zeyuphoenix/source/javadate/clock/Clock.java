package clock;

import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.util.Calendar;

import javax.swing.JComponent;

/**
 * This bean to define basic properties and behaviors of a clock, concrete
 * instances will be implemented by <code>AnalogClock</code> and
 * <code>DigitalClock</code>.
 * 
 */
public abstract class Clock extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	 * @see #getBgImage()
	 */
	protected Image bgImage;

	/**
	 * Default constructor: Creates a Clock instance by given type. Available
	 * type:
	 */
	protected Clock() {
		calendar = Calendar.getInstance();
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
}
