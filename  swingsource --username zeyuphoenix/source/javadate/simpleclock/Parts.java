/*
 * Open source project: Smart Calendar.
 * 
 * @(#)Parts.java  2006/02/21
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
import java.awt.Shape;

import javax.swing.JComponent;


/**
 * To represent all modules which a analog-type clock consists of.
 * Including:<br>
 * <ul>
 *  <li>Clock face</li>
 *  <li>Hour hand</li>
 *  <li>Minute hand</li>
 *  <li>Second hand</li>
 * </ul>
 * and an abstract timing algorithm interface.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public abstract class Parts extends JComponent
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * Coloring scheme for the parts.
   */
  protected ColorScheme colors;
  
  /**
   * Size of this parts.
   */
  protected Dimension size;
  
	/**
	 * Clockface.
	 */
	protected Shape dial;
	
	/**
	 * Default constructor.
	 */
	protected Parts()
	{
		size = new Dimension();
	}
	
  /**
   * No special character currently,
   * but may be overridden in the future.
   * 
   * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
   */
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
  }
  
  /**
   * Changes positions of hour hand, minute hand, second hand and decisecond hand based on current time.
   * 
   * @param hour current hour (in 24-hour format)
   * @param minute current minute
   * @param second current second
   * @param millisecond current millisecond
   */
  public abstract void doTransform(int hour, int minute, int second, int millisecond);
  
  /**
   * @return true if this parts containing stopwatch.
   */
  public abstract boolean isStopwatch();
  
  /**
   * Gets the clock face.
   * 
   * @return the clock face.
   */
  public Shape getDial()
	{
		return dial;
	}

	/**
   * Sets the coloring scheme.
   * 
	 * @param colors
	 */
	public void setColors(ColorScheme colors)
	{
		this.colors = colors;
	}
  
  /**
   * @return size of this parts.
   */
  public Dimension getSize()
  {
    return size;
  }
}
