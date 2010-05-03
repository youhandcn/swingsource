package digitalclock;

import java.awt.Image;
import java.awt.font.FontRenderContext;
import java.util.Calendar;

import javax.swing.JComponent;

/**
 * This bean to define basic properties and behaviors of a clock, concrete
 * instances will be implemented by <code>DigitalClock</code> and others.
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
	private Calendar calendar;

	/**
	 * Background image of the clock.
	 */
	private Image bgImage;

	/**
	 * Default constructor: Creates a Clock instance by given type.
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
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}
