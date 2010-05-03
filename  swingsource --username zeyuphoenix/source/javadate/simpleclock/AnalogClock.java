/*
 * Open source project: Smart Calendar.
 * 
 * @(#)AnalogClock.java  2006/02/21
 *
 * Copyright 2006 Samuel Lee.
 * 
 * Source code is free, but must be marked with
 * "Powered by Samuel Lee" if be redistributed, or
 * "With contributions from Samuel Lee" if be modified.
 * The author of the following codelines will NOT be
 * responsible for legal liabilities caused by
 * any redistribution with or without any modification.
 * 
 * CAUTION: THIS CODE MUST NOT BE USED FOR ANY COMMERCIAL PURPOSE WITHOUT THE AUTHOR'S PERMISSION!
 * ANY VIOLATION MAY BE FACED WITH LITIGATION!
 */
package simpleclock;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedString;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Timer;

/**
 * To implement a analog-type clock. This bean partially refers to Mitch
 * Goldstein's <a href="http://www-128.ibm.com/developerworks/cn/views/java/tutorials.jsp?cv_doc_id=84926"
 * >sample code</a>.<br>
 * <br>
 * 
 * <center>
 * <table>
 * <tr ALIGN="CENTER">
 * <td><img SRC="../../../resources/AnalogClock.jpg" alt="A analog-type clock.">
 * </td>
 * </tr>
 * 
 * <tr ALIGN="CENTER">
 * <td>Figure 1: A analog-type clock</td>
 * </tr>
 * </table>
 * </center>
 * 
 * @version 0.1
 * @author Mitch Goldstein
 * @author Samuel Lee
 */
public class AnalogClock extends Clock implements ActionListener {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ye old fudge factor to give a little space around the text.
	 */
	protected static final int MARGIN = 6;

	/**
	 * Parts to construct this clock.
	 */
	private Parts parts;

	/**
	 * A timer to run in a independent thread.
	 */
	private Timer timer;

	/**
	 * Attributed string containing message.
	 */
	protected AttributedString msg = null;

	/**
	 * Text layout of attributed string3.
	 */
	protected TextLayout layout = null;

	/**
	 * Constructor:<br>
	 * Available mode:
	 * <ul>
	 * <li><code>STOPWATCH_MODE</code>&nbsp;&nbsp;Displays stopwatch</li>
	 * </ul>
	 */
	public AnalogClock() {
		super(Clock.ANALOG_TYPE);
		timer = new Timer(1000, this);
		setStopwatch(true);

		parts = new Chromoneter();

		initialize();
	}

	/**
   * 
   */
	private void initialize() {
		actionPerformed(null);// Just to display current time when startup
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
	 * Do transformation based on current precise time when display.
	 */
	public void actionPerformed(ActionEvent e) {
		Date current = new Date();
		calendar.setTime(current);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millisecond = calendar.get(Calendar.MILLISECOND);

		Date alm = getAlarm();
		if (alm != null
				&& current.compareTo(alm) >= 0
				&& current
						.compareTo(new Date(alm.getTime() + alarmDelay * 1000)) <= 0) {// Trigger
			// alarm
			doAlarm();// Trigger additional behavior
		} else {// Reset alarm tag & timer delay
		}

		parts.doTransform(hour, minute, second, millisecond);

		// Render additional message.
		if (message != null && !message.equals("")) {
			msg = new AttributedString(message.toString());
			msg.addAttribute(TextAttribute.FONT, getFont());
			msg.addAttribute(TextAttribute.FOREGROUND, getForeground());
			layout = new TextLayout(msg.getIterator(), Clock.frc);
		} else {
			msg = null;
			layout = null;
		}

		repaint();
		setSize(getPreferredSize());// Resize this clock in time
	}

	/**
	 * Draws this clock.
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		parts.paintComponent(g);

		if (msg != null && layout != null) {
			g2.drawString(msg.getIterator(), 0, (int) (parts.getSize().height
					+ layout.getDescent() + MARGIN));
		}
	}

	/**
	 * @see java.awt.Component#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		Dimension size = getSize();
		Dimension size2 = layout == null ? null : layout.getBounds()
				.getBounds().getSize();
		size.width = size2 == null ? parts.getSize().width : Math.max(parts
				.getSize().width, size2.width);
		size.height = parts.getSize().height + MARGIN
				+ (size2 == null ? 0 : size2.height);
		return size;
	}

	/**
	 * @see study.Clock#doAlarm()
	 */
	public void doAlarm() {
	}

	/**
	 * @see study.Clock#setStopwatch(boolean)
	 */
	public void setStopwatch(boolean stopwatch) {
		super.setStopwatch(stopwatch);
		if (stopwatch) {
			timer.setDelay(100);
		} else {
			timer.setDelay(1000);
		}
	}

	/**
	 * Changes the parts.
	 * 
	 * @param parts
	 */
	public void setParts(Parts parts) {
		this.parts = parts;
		setStopwatch(parts.isStopwatch());
		actionPerformed(null);
	}
}
