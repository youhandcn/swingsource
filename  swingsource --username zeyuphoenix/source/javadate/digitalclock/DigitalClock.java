package digitalclock;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;

/**
 * This bean to implemente a digital-type clock.<br>
 */
public class DigitalClock extends Clock {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * divide time.
	 */
	private String padding1 = ""; 
	private String padding2 = "";

	/**
	 * Default construct: Uses default format and appearance.
	 */
	public DigitalClock() {
		super();
		setFont(new Font("Arial", Font.BOLD, 20));
		setOpaque(true);
		padding1 = ":";
		padding2 = ".";
		setUI(DigitalClockUI.createUI(this));
	}

	/**
	 * Constructor: Initializes a digital-type clock by using given parameters.
	 * 
	 * @param padding1
	 *            padding characters between h/m/s, for example, colon in
	 *            "14:27:30".
	 * @param padding2
	 *            padding characters between s/sw, for example, single quote in
	 *            "14:27:30'567".
	 * @param font
	 * @param fg
	 *            foreground color.
	 * @param bg
	 *            background color.
	 * @param locale
	 */
	public DigitalClock(String padding1, String padding2, Font font,
			Color fg, Color bg, Locale locale) {
		super();

		setFont(font);
		setForeground(fg);
		setBackground(bg);
		setLocale(locale);
		setOpaque(true);
		this.padding1 = padding1;
		this.padding2 = padding2;
		setUI(DigitalClockUI.createUI(this));
	}

	/**
	 * Sets padding characters between h/m/s, for example, colon in "14:27:30".
	 */
	public void setPadding1(String padding1) {
		this.padding1 = padding1;
	}

	/**
	 * Gets padding characters between h/m/s, for example, colon in "14:27:30".
	 * 
	 * @return the padding characters between h/m/s.
	 */
	public String getPadding1() {
		return padding1;
	}

	/**
	 * Sets padding characters between s/sw, for example, single quote in
	 * "14:27:30'567".
	 */
	public void setPadding2(String padding2) {
		this.padding2 = padding2;
	}

	/**
	 * Gets padding characters between s/sw, for example, single quote in
	 * "14:27:30'567".
	 * 
	 * @return the padding characters between s/sw.
	 */
	public String getPadding2() {
		return padding2;
	}

	/**
	 * Gets the UI agent of this bean.
	 * 
	 * @return UI agent of this bean.
	 */
	public DigitalClockUI getUI() {
		return (DigitalClockUI) ui;
	}

	/**
	 * Invoked when panel is added to a container.
	 * 
	 * @see javax.swing.JComponent#addNotify()
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		getUI().start();
	}

	/**
	 * Invoked when panel is removed from a container.
	 * 
	 * @see javax.swing.JComponent#removeNotify()
	 */
	@Override
	public void removeNotify() {
		getUI().stop();
		super.removeNotify();
	}
}
