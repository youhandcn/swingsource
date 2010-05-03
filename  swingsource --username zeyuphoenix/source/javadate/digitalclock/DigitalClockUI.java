package digitalclock;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.PanelUI;

/**
 * 
 */
public class DigitalClockUI extends PanelUI implements ActionListener {
	//
	protected static final int SHRINK = 5;

	// Ye old fudge factor to give a little space around the text
	protected static final int MARGIN = 6;

	// Attributed string containing current time
	protected AttributedString timeString = null;

	// Text layout of attributed string
	protected TextLayout textLayout = null;

	// Attributed string containing current timezone.
	protected AttributedString timezoneString = null;

	// Text layout of attributed timezone.
	protected TextLayout textTimezoneLayout = null;

	// One-second timer to keep clock current
	protected Timer timer;

	// Reference to panel that this delegate is for
	protected DigitalClock panel;

	/**
	 * Blink tag.
	 */
	protected long startTime = 0;

	protected transient int delay = 0;
	protected transient static final int BLINK_INTERVAL = 1000;

	// Constructor - store the panel for which this delegate
	// was created
	private DigitalClockUI(DigitalClock panel) {

		// Store the panel and set the background color to black
		this.panel = panel;
		// stopwatch
		timer = new Timer(10, this);
		// The line of code just to display current time
		// when startup
		actionPerformed(null);
	}

	public void start() {
		delay = timer.getDelay();
		startTime = (System.currentTimeMillis() / 1000) * 1000;
		timer.start();
	}

	public void stop() {
		timer.stop();
	}

	// Create an instance of the UI delegate for this component
	public static ComponentUI createUI(DigitalClock component) {
		return new DigitalClockUI(component);
	}

	// Paint the component
	@Override
	public void paint(Graphics g, JComponent c) {
		// If there are no time or layouts yet, we can't paint
		if (this.timeString == null || this.textLayout == null)
			return;

		// Get a reference to the <Graphics2D> instance and set the
		// anti-aliasing on
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the string in the position dictated by the layout.
		g2.drawString(timeString.getIterator(), 1, (int) (textLayout
				.getAscent()));

		if (timezoneString != null && textTimezoneLayout != null) {
			g2.drawString(timezoneString.getIterator(), 0, (int) (textLayout
					.getAscent()
					+ textLayout.getDescent()
					+ textLayout.getLeading()
					+ textTimezoneLayout.getAscent() + MARGIN));
		}
	}

	// Invoked by timer when interval ends
	@Override
	public void actionPerformed(ActionEvent event) {
		Font font = panel.getFont();
		// Create a new attributed string with the new time
		Date current = new Date();
		Calendar cal = panel.getCalendar();
		cal.setTime(current);

		long blink = ((System.currentTimeMillis() - startTime) / (BLINK_INTERVAL)) % 2;

		// To format current time using specific pattern.
		SimpleDateFormat df;

		// ////////////////////////////////////
		// Draw standard digital-type clock //
		// ////////////////////////////////////

		// Hour24 mode
		df = new SimpleDateFormat("HH" + panel.getPadding1() + "mm"
				+ panel.getPadding1() + "ss", panel.getLocale());

		String str = df.format(current);

		String temp = "";
		int tmpLen1 = 0;

		df.applyPattern("SSS");
		temp = " " + panel.getPadding2() + df.format(current).substring(0, 3);

		tmpLen1 = temp.length();

		str += temp;
		temp = "";

		// //////////////
		// Draw AM/PM //
		// //////////////
		int tmpLen2 = 0;

		// mode
		df.applyPattern("a");
		temp = " " + df.format(current);
		tmpLen2 = temp.length();

		str += temp;
		temp = "";

		// timezone
		df.applyPattern("Z");
		StringBuffer sb = new StringBuffer("GMT");
		sb.append(df.format(current));
		sb.insert(6, ":");
		df.applyPattern("zzzz");
		temp = " " + df.format(current) + " (" + df.getTimeZone().getID()
				+ ", " + sb + ")";

		String str2 = temp;
		temp = "";

		// //////////////////////////
		// Format the time string //
		// //////////////////////////
		timeString = null;
		timeString = new AttributedString(str);
		// Render main time area
		timeString.addAttribute(TextAttribute.FONT, font, 0, 6 + 2 * panel
				.getPadding1().length());
		timeString
				.addAttribute(TextAttribute.FOREGROUND, panel.getForeground());
		// Do blinking if reach alarm point
		timeString.addAttribute(TextAttribute.FOREGROUND,
				panel.getForeground(), 0, 6 + 2 * panel.getPadding1().length());
		// Render padding1, do blinking
		timeString.addAttribute(TextAttribute.FOREGROUND, blink == 0 ? panel
				.getForeground() : panel.getBackground(), 2, 2 + panel
				.getPadding1().length());
		timeString.addAttribute(TextAttribute.FOREGROUND, blink == 0 ? panel
				.getForeground() : panel.getBackground(), 2 + panel
				.getPadding1().length() + 2, 4 + 2 * panel.getPadding1()
				.length());

		// Render stopwatch
		timeString.addAttribute(TextAttribute.FONT, new Font(font.getName(),
				font.getStyle(), font.getSize() - SHRINK > 1 ? font.getSize()
						- SHRINK : 1), 6 + 2 * panel.getPadding1().length(), 6
				+ 2 * panel.getPadding1().length() + tmpLen1);

		// Render am/pm area
		timeString
				.addAttribute(TextAttribute.FONT, new Font(font.getName(), font
						.getStyle(), font.getSize() - SHRINK - 4 > 1 ? font
						.getSize()
						- SHRINK - 4 : 1), str.length() - tmpLen2, str.length());

		// Create a new text layout and signal the panel that it needs
		// repainting
		textLayout = null;
		textLayout = new TextLayout(timeString.getIterator(), Clock.frc);

		// Render timezone, drawing at another line.
		timezoneString = null;
		timezoneString = new AttributedString(str2);
		timezoneString.addAttribute(TextAttribute.FONT, new Font(font.getName(),
				font.getStyle(), font.getSize() - SHRINK - 4 > 1 ? font
						.getSize()
						- SHRINK - 4 : 1));
		timezoneString.addAttribute(TextAttribute.FOREGROUND, panel
				.getForeground());
		textTimezoneLayout = null;
		textTimezoneLayout = new TextLayout(timezoneString.getIterator(), Clock.frc);

		// To keep the clock size fit for
		// actual size in time.
		panel.setSize(getPreferredSize(panel));
		panel.repaint();
		current = null;
		df = null;
	}

	// Return the preferred size for the component
	@Override
	public Dimension getPreferredSize(JComponent c) {
		// Get the size dictated by the text layout as a
		// <Rectangle>, then get the <Dimension> from that.
		Dimension size = new Dimension();

		Dimension size1 = textLayout.getBounds().getBounds().getSize();
		Dimension size2 = textTimezoneLayout == null ? null : textTimezoneLayout.getBounds()
				.getBounds().getSize();

		int width1 = size1.width;
		int width2 = textTimezoneLayout == null ? 0 : size2.width;

		int height1 = (int) (textLayout.getAscent() + textLayout.getDescent() + textLayout
				.getLeading());
		int height2 = (int) (textTimezoneLayout == null ? 0 : textTimezoneLayout.getAscent()
				+ textTimezoneLayout.getDescent() + textTimezoneLayout.getLeading());

		// Add a little height and width to leave some
		// space around the text. We add '2' to leave one
		// extra pixel on the left and right so the text isn't
		// hitting the border when the frame is packed.
		size.height = (height1 + MARGIN) + (height2 + MARGIN);
		int max = width1;
		if (width2 > max)
			max = width2;

		size.width = max + MARGIN + 2;
		return size;
	}
}
