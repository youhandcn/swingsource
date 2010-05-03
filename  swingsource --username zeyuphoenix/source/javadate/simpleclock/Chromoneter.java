/*
 * Open source project: Smart Calendar.
 * 
 * @(#)Chromoneter.java  2006/02/21
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

/**
 * To implement a analog-type clock simplely with stopwatch.<br><br>
 * 
 * <center><table>
 * <tr ALIGN="CENTER">
 * <td><img SRC="../../../../../resources/Chromoneter.jpg"
 *      alt="A analog-type clock with stopwatch.">
 * </td>
 * </tr>
 * 
 * <tr ALIGN="CENTER">
 * <td>Figure 1: A analog-type clock with stopwatch</td>
 * </tr>
 * </table></center>
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class Chromoneter extends SimpleParts
{
	/**
   * Default serial version ID.
   */
  private static final long serialVersionUID = 1L;
  /**
	 * The stopwatch parts.
	 */
	private SimpleStopwatch stopwatch;
	
	/**
	 * Default constructor:
	 * Creates a simple stopwatch under the center of the clock.
	 */
  public Chromoneter()
  {
  	super();
  	initialize(55, 75, 25, StopwatchColor.DEFAULT);
  }
  
	/**
	 * Constructor:
	 * Draws a chromoneter clock with a stopwatch at specific position with specific value.
	 * 
	 * @param x The x coordinate of the upper left corner of the boundary square of this clock.
	 * @param y The y coordinate of the upper left corner of the boundary square of this clock.
	 * @param width The width of the boundary square of this clock.
	 * @param numbers 12 numbers to denote 12 time punctualities.
	 * @param basicColors color scheme for basic parts.
	 * @param sx The x coordinate of the upper left corner of the boundary square of stopwatch.
	 * @param sy The y coordinate of the upper left corner of the boundary square of stopwatch.
	 * @param sWidth The width of the boundary square of stopwatch.
	 * @param swColors color scheme for stopwatch.
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException if x or y or width is minus, or numbers less than 12 items.
	 */
	public Chromoneter(float x, float y, float width, String[] numbers, BasicColor basicColors,
										 float sx, float sy, float sWidth, StopwatchColor swColors) throws Exception
	{
		super(x, y, width, numbers, basicColors);
		if (x < 0 || y < 0 || width < 0)
		{
			throw new Exception("Illegal stopwatch coordinates or width: Minus value.");
		}
		
		initialize(sx, sy, sWidth, swColors);
    
    // initialize container size
    float margin = 0.1f;
    float r1 = width / 2;
    float r2 = sWidth / 2;
    float xc1 = x + r1;
    float yc1 = y + r1;
    float xc2 = sx + r2;
    float yc2 = sy + r2;
    float cd = centerDistance(xc1, yc1, xc2, yc2);
    
    if (cd <= Math.abs(r1 - r2))
    {
	    size.width = (int)(Math.max(width, sWidth) * (1 + margin));
	    size.height = size.width;
    }
    else
    {
    	if (x > sx)
    	{
	    	size.width = (int)(x + r1);
    	}
    	else if (x < sx)
    	{
    		size.width = (int)(sx + r2);
    	}
    	else
    	{
    		size.width = (int)(x + Math.max(r1, r2));
    	}
    	
    	if (y > sy)
    	{
	    	size.height = (int)(y + r1);
    	}
    	else if (y < sy)
    	{
    		size.height = (int)(sy + r2);
    	}
    	else
    	{
    		size.height = (int)(y + Math.max(r1, r2));
    	}
    	
	    size.width *= 1 + margin;
	    size.height *= 1 + margin;
    }
	}
	
	/**
	 * Computes the distance between two points.
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private float centerDistance(float x1, float y1, float x2, float y2)
	{
		return (float)Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	/**
	 * To construct stopwatch shape.
	 * 
	 * @param sx The x coordinate of the upper left corner of the boundary square of stopwatch.
	 * @param sy The y coordinate of the upper left corner of the boundary square of stopwatch.
	 * @param sWidth The width of the boundary square of stopwatch.
	 * @param swColors color scheme for stopwatch.
	 */
	private void initialize(float sx, float sy, float sWidth, StopwatchColor swColors)
	{
		try
		{
			stopwatch = new SimpleStopwatch(sx, sy, sWidth, swColors);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see com.jungleford.smartcalendar.clock.analog.BasicParts#doTransform(int, int, int, int)
	 * @see com.MyStopwatchParts.smartcalendar.clock.analog.SimpleStopwatch#doTransform(int, int, int, int)
	 */
	public void doTransform(int hour, int minute, int second, int millisecond)
	{
		super.doTransform(hour, minute, second, millisecond);
		stopwatch.doTransform(hour, minute, second, millisecond);
	}

	/**
	 * @return always <code>true</code>
	 */
	public boolean isStopwatch()
	{
		return true;
	}
	
	/**
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#paintComponent(Graphics)
	 * @see com.MyStopwatchParts.smartcalendar.clock.analog.SimpleStopwatch#paintComponent(Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		stopwatch.paintComponent(g);
	}
}
