/*
 * Open source project: Smart Calendar.
 * 
 * @(#)BasicColor.java  2006/02/21
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

import java.awt.Color;


/**
 * Only set some fields to represent color of basic parts in a analog-type clock.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class BasicColor implements ColorScheme
{
	/**
   * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
   * Default scheme.
   */
  public static final BasicColor DEFAULT = new BasicColor(
  		new Color(220, 220, 220),
  		Color.BLACK,
  		Color.BLACK,
  		Color.BLACK,
  		Color.BLACK,
  		Color.BLACK,
  		Color.BLACK);
  
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
   * Constructor:
   * Initialize color scheme.
   * 
   * @param dail
   * @param hourHand
   * @param minuteHand
   * @param secondHand
   * @param numbers
   * @param tick
   */
  public BasicColor(
  		Color dail,
  		Color center,
  	  Color hourHand,
  	  Color minuteHand,
  	  Color secondHand,
  	  Color numbers,
  	  Color tick)
  {
  	this.dail = dail;
  	this.center = center;
  	this.hourHand = hourHand;
  	this.minuteHand = minuteHand;
  	this.secondHand = secondHand;
  	this.numbers = numbers;
  	this.tick = tick;
  }
}
