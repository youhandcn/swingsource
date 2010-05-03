/*
 * Open source project: Smart Calendar.
 * 
 * @(#)Stopwatch.java  2006/02/21
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

/**
 * To define all modules which a analog-type stopwatch consists of.
 * Including:<br>
 * <ul>
 *  <li>Stopwatch face</li>
 *  <li>Decisecond hand</li>
 * </ul>
 * and an abstract timing algorithm interface.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public abstract class Stopwatch extends RotateParts
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Deci-second hand.
	 */
	protected Shape decisecondHand;

	/**
	 * Deci-second hand behavior controller.
	 */
  protected AffineTransform decisecondTransform;

	/**
	 * Moves all parts, to leave some margin.
	 */
	protected transient AffineTransform trans;
	
	/**
	 * Initializes hand transformation.
	 * You can set your own transformation by overriding this method.
	 */
	protected void initTransform()
	{
		decisecondTransform = getTransform();
	}
	
	/**
   * Changes positions of decisecond hand based on current time.
   * 
   * @param hour no use here.
   * @param minute no use here.
   * @param second no use here.
   * @param millisecond
   * 
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#doTransform(int, int, int, int)
	 */
	public void doTransform(int hour, int minute, int second, int millisecond)
	{
		setToRotation(decisecondTransform,
									millisecond / 100.0,
									10);
	}
	
	/**
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
    super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		StopwatchColor swc = (StopwatchColor)colors;
		
		if (trans != null && swc != null &&
				decisecondHand != null && decisecondTransform != null)
		{
			if (dial != null)
			{// Draw face
		    g2.setPaint(swc.stopwatch);
		    g2.fill(trans.createTransformedShape(dial));
		    g2.setPaint(Color.BLACK);
		    g2.draw(trans.createTransformedShape(dial));
			}
			
	    // Draw deci-second hand
	    g2.setPaint(swc.decisecondHand);  
	    g2.fill(
	    	trans.createTransformedShape(
	    			decisecondTransform.createTransformedShape(decisecondHand)));
		}
	}

	/**
	 * Gets the rotate alogrithm for the deci-second hand.
	 * 
	 * @return the rotate alogrithm for the deci-second hand.
	 */
	public AffineTransform getDecisecondTransform()
	{
		return decisecondTransform;
	}
}
