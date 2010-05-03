package clock;

import java.awt.Color;
import java.io.Serializable;

/**
 * Only set some fields to represent color of basic parts in a analog-type
 * clock.
 * 
 */
public class BasicColor implements Serializable {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default scheme.
	 */
	public static final BasicColor DEFAULT = new BasicColor(new Color(220, 220,
			220), Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
			Color.BLACK, Color.BLACK);

	/**
	 * Color of clock face.
	 */
	public Color dail;

	/**
	 * Color of the center.
	 */
	public Color center;

	/**
	 * Color of hour hand.
	 */
	public Color hourHand;

	/**
	 * Color of minute hand.
	 */
	public Color minuteHand;

	/**
	 * Color of second hand.
	 */
	public Color secondHand;

	/**
	 * Color of 12 time punctualities.
	 */
	public Color numbers;

	/**
	 * Color of 12 time ticks.
	 */
	public Color tick;

	/**
	 * Constructor: Initialize color scheme.
	 * 
	 * @param dail
	 * @param hourHand
	 * @param minuteHand
	 * @param secondHand
	 * @param numbers
	 * @param tick
	 */
	public BasicColor(Color dail, Color center, Color hourHand,
			Color minuteHand, Color secondHand, Color numbers, Color tick) {
		this.dail = dail;
		this.center = center;
		this.hourHand = hourHand;
		this.minuteHand = minuteHand;
		this.secondHand = secondHand;
		this.numbers = numbers;
		this.tick = tick;
	}
}
