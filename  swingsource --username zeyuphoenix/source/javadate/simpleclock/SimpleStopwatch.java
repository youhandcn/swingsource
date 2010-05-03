/*
 * Open source project: Smart Calendar.
 * 
 * @(#)SimpleStopwatch.java  2006/02/21
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

/**
 * To implement a concrete stopwatch.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class SimpleStopwatch extends Stopwatch
{
	/**
   * Default serial version ID.
   */
  private static final long serialVersionUID = 1L;
  /**
	 * 10 ticks.
	 */
	private GeneralPath tick;
	
	/**
	 * Default constructor:
	 * Creates a regular classical circle face and hands.
	 */
	public SimpleStopwatch()
	{
		radius = 15;
		x = 50 + radius;
		y = 80 + radius;
		colors = StopwatchColor.DEFAULT;
		
		initialize();
	}
	
	/**
	 * Constructor:
	 * Draws a classical clock at specific position with specific value.
	 * 
	 * @param x The x coordinate of the upper left corner of the boundary square of this stopwatch.
	 * @param y The y coordinate of the upper left corner of the boundary square of this stopwatch.
	 * @param width The width of the boundary square of this stopwatch.
	 * @param colors color scheme for simple parts.
	 * 
	 * @throws PartsException if x or y or width is minus.
	 */
	public SimpleStopwatch(float x, float y, float width, StopwatchColor colors) throws Exception
	{
		if (x < 0 || y < 0 || width < 0)
		{
			throw new Exception("Illegal coordinates or width: Minus value.");
		}
		
		radius = width / 2;
		this.x = x + radius;
		this.y = y + radius;
		this.colors = colors;
		
		initialize();
	}
	
	/**
	 * Creates stopwatch shape, deci-second hand and ticks.
	 */
	protected void initialize()
	{
		// Initialize clock face
		dial = new Ellipse2D.Float(x - radius, y - radius, radius * 2, radius * 2);
		
		// Default position is 12:00:00.000
		// Initialize decisecond hand
		float dWidthOfRadius = .04f;// Width of the decisecond hand is 4% of the dail radius
		float dLengthOfRadius = .85f;// Length of the decisecond hand is 85% of the dail radius
		float dMarginOfRadius = .1f;// Margin length of the decisecond hand is 10% of the dail radius
		decisecondHand = new GeneralPath();
		SimpleParts.createHand(decisecondHand, x, y, radius, dWidthOfRadius, dLengthOfRadius, dMarginOfRadius);

		// Initialize hand transformation object.
		initTransform();
		
		// Initialize the first tick
		float tWidthOfRadius = .04f;// Width of the decisecond hand is 4% of the dail radius
		float tLengthOfRadius = .1f;// Length of the decisecond hand is 10% of the dail radius
		tick = new GeneralPath();
		SimpleParts.createHand(tick, x, y - radius, radius, tWidthOfRadius, tLengthOfRadius /  2, tLengthOfRadius / 2);
    
    // initialize container size
    float sizeMarginOfRadius = .1f;
    size.width = (int)(x + radius * (1 + sizeMarginOfRadius));
    size.height = (int)(y + radius * (1 + sizeMarginOfRadius));
	}
	
	/**
	 * @see com.jungleford.smartcalendar.clock.analog.Stopwatch#initTransform()
	 */
	protected void initTransform()
	{
		super.initTransform();
		
		// Move all parts, to leave some margin
		float margin = 0;
		trans = AffineTransform.getTranslateInstance(radius * margin, radius * margin);
	}
	
	/**
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
    // Draw 10 ticks
		drawTicks(g, 10);
	}
	
	/**
	 * Draw ticks.
	 * 
	 * @param g
	 * @param tickNumber usually 10.
	 */
	protected void drawTicks(Graphics g, int tickNumber)
	{
		Graphics2D g2 = (Graphics2D)g;
		StopwatchColor swc = (StopwatchColor)colors;
    AffineTransform at = AffineTransform.getRotateInstance(0, x, y);
    
		for (int p = 0; p < tickNumber; p++)
		{
			at = getRotateInstance(tickNumber, p);
			g2.setPaint(swc.tick);
			g2.fill(trans.createTransformedShape(at.createTransformedShape(tick)));
		}
	}

	/**
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#isStopwatch()
	 */
	public boolean isStopwatch()
	{
		return true;
	}
}
