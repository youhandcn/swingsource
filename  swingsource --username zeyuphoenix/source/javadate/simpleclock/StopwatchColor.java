/*
 * Open source project: Smart Calendar.
 * 
 * @(#)StopwatchColor.java  2006/02/21
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
 * To define a color scheme for stopwatch
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class StopwatchColor implements ColorScheme
{
	/**
   * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
   * Default scheme.
   */
  public static final StopwatchColor DEFAULT = new StopwatchColor(
  		new Color(220, 220, 220),
  		Color.BLACK,
  		Color.BLACK);
  
  /**
   * Color of stopwatch face.
   */
  public Color stopwatch;
  
  /**
   * Color of deci-second hand.
   */
  public Color decisecondHand;
  
  /**
   * Color of 12 time ticks.
   */
  public Color tick;
  
  /**
   * Constructor:
   * Initializes color scheme.
   *
   * @param stopwatch
   * @param decisecondHand
   * @param tick
   */
  public StopwatchColor(Color stopwatch, Color decisecondHand, Color tick)
  {
  	this.stopwatch = stopwatch;
  	this.decisecondHand = decisecondHand;
  	this.tick = tick;
  }
}
