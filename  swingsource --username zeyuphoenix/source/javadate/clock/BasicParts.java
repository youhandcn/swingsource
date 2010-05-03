/*
 * Open source project: Smart Calendar.
 * 
 * @(#)BasicParts.java  2006/02/21
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
package clock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.text.AttributedString;

/**
 * To implement a classical analog-type clock face, except definitely describing
 * the hands shape.<br>
 * So you can define your own shapes of the hands by extending this class.
 * 
 */
public abstract class BasicParts extends RotateParts {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Arabic time punctualities.
	 */
	public static final String[] ARABIC = { "12", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11" };

	/**
	 * Roman time punctualities.
	 */
	public static final String[] ROMAN = { "XII", "I", "II", "III", "IV", "V",
			"VI", "VII", "VIII", "IX", "X", "XI" };

	/**
	 * Hour hand.
	 */
	protected Shape hourHand;

	/**
	 * Minute hand.
	 */
	protected Shape minuteHand;

	/**
	 * Second hand.
	 */
	protected Shape secondHand;

	/**
	 * Hour hand behavior controller.
	 */
	protected AffineTransform hourTransform;

	/**
	 * Minute hand behavior controller.
	 */
	protected AffineTransform minuteTransform;

	/**
	 * Second hand behavior controller.
	 */
	protected AffineTransform secondTransform;

	/**
	 * 12 time punctualities.
	 */
	protected String[] numbers;

	/**
	 * Moves all parts, to leave some margin.
	 */
	protected transient AffineTransform trans;

	/**
	 * Default constructor.
	 */
	protected BasicParts() {
	}

	/**
	 * Constructor: Joins every parts in a entire analog-type clock.
	 * 
	 * @param dial
	 *            the clock face.
	 * @param hourHand
	 *            the hour hand.
	 * @param minuteHand
	 *            the minute hand.
	 * @param secondHand
	 *            the second hand.
	 * @param numbers
	 *            the 12 time punctualities.
	 * @param colors
	 *            the coloring scheme.
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if any of the given component is not initialized, or time
	 *             punctualities is less than 12.
	 */
	protected BasicParts(Shape dial, Shape hourHand, Shape minuteHand,
			Shape secondHand, String[] numbers, BasicColor colors)
			throws Exception {
		if (dial == null || hourHand == null || minuteHand == null
				|| secondHand == null) {
			throw new Exception(
					"Illegal parts: Every parts MUST be initialized.");
		}
		if (numbers == null || numbers.length < 12) {
			throw new Exception(
					"Illegal graduation numbers: Null or less than 12 numbers");
		}
		if (colors == null) {
			throw new Exception(
					"Illegal color scheme: Color scheme MUST be initialized.");
		}

		this.dial = dial;
		this.hourHand = hourHand;
		this.minuteHand = minuteHand;
		this.secondHand = secondHand;
		this.numbers = numbers;
		this.colors = colors;
	}

	/**
	 * Initializes hand transformation. You can set your own transformation by
	 * overriding this method.
	 */
	protected void initTransform() {
		hourTransform = getTransform();
		minuteTransform = getTransform();
		secondTransform = getTransform();
	}

	/**
	 * Default algorithm for hands's action trace. You can set your own
	 * algorithm by overriding this method. Here millisecond does not work
	 * indeed.
	 */
	@Override
	public void doTransform(int hour, int minute, int second, int millisecond) {
		if (hourTransform != null && minuteTransform != null
				&& secondTransform != null) {
			setToRotation(hourTransform,
					hour + (minute + second / 60.0) / 60.0, 12);
			setToRotation(minuteTransform, minute + second / 60.0, 60);
			setToRotation(secondTransform, second, 60);
		}
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		BasicColor c = (BasicColor) colors;

		if (trans != null && c != null && hourHand != null
				&& minuteHand != null && secondHand != null
				&& hourTransform != null && minuteTransform != null
				&& secondTransform != null) {
			if (dial != null) {
				// Draw face
				g2.setPaint(c.dail);
				g2.fill(trans.createTransformedShape(dial));
				g2.setPaint(Color.BLACK);
				g2.draw(trans.createTransformedShape(dial));
			}

			// Draw hour hand
			g2.setPaint(c.hourHand);
			g2.fill(trans.createTransformedShape(hourTransform
					.createTransformedShape(hourHand)));

			// Draw minute hand
			g2.setPaint(c.minuteHand);
			g2.fill(trans.createTransformedShape(minuteTransform
					.createTransformedShape(minuteHand)));

			// Draw second hand
			g2.setPaint(c.secondHand);
			g2.fill(trans.createTransformedShape(secondTransform
					.createTransformedShape(secondHand)));
		}
	}

	/**
	 * Draws 12 time punctualities. Can be overridden by derived classes. For
	 * example, you can use AttributedString for decoration in your own clock
	 * parts.
	 * 
	 * @param g
	 *            graphics environment.
	 * @param number
	 *            the string to draw.
	 * @param x
	 *            X coordinate of the rotate center.
	 * @param y
	 *            Y coordinate of the rotate center.
	 * @param color
	 *            the coloring scheme.
	 */
	public static void drawNumber(Graphics g, AttributedString number, float x,
			float y, Color color) {
		if (number != null) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setPaint(color);
			g2.drawString(number.getIterator(), x, y);
		}
	}

	/**
	 * Draws a number at 12 o'clock.
	 * 
	 * @param g
	 *            graphics environment.
	 * @param number
	 *            the string to draw.
	 * @param font
	 *            the font used.
	 */
	protected void drawNumber(Graphics g, String number, Font font) {
		BasicColor c = (BasicColor) colors;
		AttributedString num = new AttributedString(number);
		if (font != null) {
			num.addAttribute(TextAttribute.FONT, font);
		}
		drawNumber(g, num, x, y - radius, c.numbers);
	}

	/**
	 * Gets 12 time punctualities.
	 * 
	 * @return 12 time punctualities.
	 */
	public String[] getNumbers() {
		return numbers;
	}

	/**
	 * Sets 12 time punctualities.
	 * 
	 * @param numbers
	 *            <b>MUST</b> be 12 numbers, to denote 12 time punctualities.
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             numbers less than 12 items.
	 */
	public void setNumbers(String[] numbers) throws Exception {
		if (numbers == null || numbers.length < 12) {
			throw new Exception(
					"Illegal graduation numbers: Null or less than 12 numbers");
		} else {
			this.numbers = numbers;
		}
	}

	/**
	 * Changes the shape of hour hand.
	 * 
	 * @param hourHand
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if hand is not initialized.
	 */
	public void setHourHand(Shape hourHand) throws Exception {
		if (hourHand == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.hourHand = hourHand;
	}

	/**
	 * Changes the rotation algorithm of hour hand.
	 * 
	 * @param hourTransform
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if transform scheme is not initialized.
	 */
	public void setHourTransform(AffineTransform hourTransform)
			throws Exception {
		if (hourTransform == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.hourTransform = hourTransform;
	}

	/**
	 * Changes the shape of minute hand.
	 * 
	 * @param minuteHand
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if hand is not initialized.
	 */
	public void setMinuteHand(Shape minuteHand) throws Exception {
		if (minuteHand == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.minuteHand = minuteHand;
	}

	/**
	 * Change the rotation algorithm of minute hand.
	 * 
	 * @param minuteTransform
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if transform scheme is not initialized.
	 */
	public void setMinuteTransform(AffineTransform minuteTransform)
			throws Exception {
		if (minuteTransform == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.minuteTransform = minuteTransform;
	}

	/**
	 * Change the shape of second hand.
	 * 
	 * @param secondHand
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if hand is not initialized.
	 */
	public void setSecondHand(Shape secondHand) throws Exception {
		if (secondHand == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.secondHand = secondHand;
	}

	/**
	 * Change the rotation algorithm of second hand.
	 * 
	 * @param secondTransform
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException
	 *             if transform scheme is not initialized.
	 */
	public void setSecondTransform(AffineTransform secondTransform)
			throws Exception {
		if (secondTransform == null) {
			throw new Exception("Hour hand MUST not be null.");
		}
		this.secondTransform = secondTransform;
	}
}
