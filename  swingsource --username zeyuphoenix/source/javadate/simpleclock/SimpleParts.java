/*
 * Open source project: Smart Calendar.
 * 
 * @(#)SimpleParts.java  2006/02/21
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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.text.AttributedString;


/**
 * To implement a classical analog-type clock face.
 * And the hands are designed as strip rectangle.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class SimpleParts extends BasicParts
{
	/**
   * Default serial version ID.
   */
  private static final long serialVersionUID = 1L;

  /**
	 * 12 ticks.
	 */
	protected Shape tick;
	
	/**
	 * 
	 */
	private static Font font = null;
	
	/**
	 * Margin for numbers.
	 */
	private static float margin = 0.1f;
	
	/**
	 * Default constructor:
	 * Creates a regular classical circle face and hands.
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException 
	 */
	public SimpleParts()
	{
		radius = 50;
		x = radius;
		y = radius;
		colors = BasicColor.DEFAULT;
    // Initialize the 12 time punctualities
    numbers = BasicParts.ARABIC;
		
		initialize();
	}
	
	/**
	 * Constructor:
	 * Draws a classical clock at specific position with specific value.<br>
	 * 12 time punctualities can be normal style from "1" to "12",
	 * or Roman numerals from "I" to "XII", or any other styles.
	 * 
	 * @param x The x coordinate of the upper left corner of the boundary square of this clock.
	 * @param y The y coordinate of the upper left corner of the boundary square of this clock.
	 * @param width The width of the boundary square of this clock.
	 * @param numbers <b>MUST</b> be 12 numbers, to denote 12 time punctualities.
	 * @param colors color scheme for simple parts.
	 * 
	 * @throws com.jungleford.smartcalendar.clock.analog.PartsException if x or y or width is minus, or numbers less than 12 items.
	 */
	public SimpleParts(float x, float y, float width, String[] numbers, BasicColor colors) throws Exception
	{
		if (x < 0 || y < 0 || width < 0)
		{
			throw new Exception("Illegal coordinates or width: Minus value.");
		}
		if (numbers == null || numbers.length < 12)
		{
			throw new Exception("Illegal graduation numbers: Null or less than 12 numbers");
		}
		
		radius = width / 2;
		this.x = x + radius;
		this.y = y + radius;
		this.colors = colors;
    // Initialize the 12 time punctualities
    this.numbers = numbers;
		
		initialize();
	}
	
	/**
	 * Some preparations for constructor.
	 * For example, create the shape of clock and hands,
	 * set <code>trans</code>'s translate parameters,
	 * etc.
	 */
	protected void initialize()
	{
		// Initialize clock face
		dial = new Ellipse2D.Float(x - radius, y - radius, radius * 2, radius * 2);

		// Default position is 12:00:00
		
		// Initialize hour hand
		float hWidthOfRadius = .08f;// Width of the hour hand is 8% of the dail radius
		float hLengthOfRadius = .6f;// Length of the hour hand is 60% of the dail radius
		float hMarginOfRadius = .1f;// Margin length of the hour hand is 10% of the dail radius
		hourHand = new GeneralPath();
		createHand(hourHand, hWidthOfRadius, hLengthOfRadius, hMarginOfRadius);
		
		// Initialize minute hand
		float mWidthOfRadius = .04f;// Width of the minute hand is 4% of the dail radius
		float mLengthOfRadius = .85f;// Length of the minute hand is 85% of the dail radius
		float mMarginOfRadius = .15f;// Margin length of the minute hand is 15% of the dail radius
		minuteHand = new GeneralPath();
		createHand(minuteHand, mWidthOfRadius, mLengthOfRadius, mMarginOfRadius);
		
		// Initialize second hand
		float sWidthOfRadius = .02f;// Width of the second hand is 2% of the dail radius
		float sLengthOfRadius = 1.05f;// Length of the second hand is 105% of the dail radius
		float sMarginOfRadius = .25f;// Margin length of the second hand is 25% of the dail radius
		secondHand = new GeneralPath();
		createHand(secondHand, sWidthOfRadius, sLengthOfRadius, sMarginOfRadius);
		
		// Initialize hand transformation object.
		initTransform();
		
		// Initialize the first tick at 12:00
		float tickRadius = radius * .04f;// Radius of the tick is 4% of the dail radius
    tick = new Ellipse2D.Float(x - tickRadius, y - radius - tickRadius, tickRadius * 2, tickRadius * 2);
    
    // Initialize container size
		TextLayout layout12, layout3, layout6, layout9;
		layout12 = new TextLayout(new AttributedString(numbers[0]).getIterator(), Clock.frc);
		layout3 = new TextLayout(new AttributedString(numbers[3]).getIterator(), Clock.frc);
		layout6 = new TextLayout(new AttributedString(numbers[6]).getIterator(), Clock.frc);
		layout9 = new TextLayout(new AttributedString(numbers[9]).getIterator(), Clock.frc);
    float sizeMargin = (float)trans.getTranslateX() / radius;
    size.width = (int)(x + radius * (1 + sizeMargin) +
    		layout3.getBounds().getBounds().width + radius * margin +
    		layout9.getBounds().getBounds().width + radius * margin);
    size.height = (int)(y + radius * (1 + sizeMargin) +
    		layout12.getAscent() + layout12.getDescent() + layout12.getLeading() +
    		layout6.getAscent() + layout6.getDescent() + layout6.getLeading());
	}
	
	/**
	 * @see com.jungleford.smartcalendar.clock.analog.BasicParts#initTransform()
	 */
	protected void  initTransform()
	{
		super.initTransform();
		
		// Move all parts, to leave some margin
		float margin = 0.3f;// Margin is 30% of the dail radius
		trans = AffineTransform.getTranslateInstance(radius * margin, radius * margin);
		
	}
	
	/**
	 * Generates concrete hand shape.
	 * The hands are designed as strip rectangle.<br>
	 * This is a common method.
	 * 
	 * @param hand shape of the hand.
	 * @param x X coordinate of the rotate center.
	 * @param y Y coordinate of the rotate center.
	 * @param radius rotate radius.
	 * @param widthPercent width of the hand in proportion to the radius by percentage.
	 * @param lengthPercent length of the hand in proportion to the radius by percentage.
	 * @param marginPercent margin length of the hand in proportion to the radius by percentage.
	 * 
	 * @see java.awt.geom.GeneralPath#moveTo(float, float)
	 * @see java.awt.geom.GeneralPath#lineTo(float, float)
	 */
	public static void createHand(Shape hand, float x, float y, float radius, float widthPercent, float lengthPercent, float marginPercent)
	{
		GeneralPath h = (GeneralPath)hand;
		h.moveTo(x - radius * (widthPercent / 2), y + radius * marginPercent);
		h.lineTo(x + radius * (widthPercent / 2), y + radius * marginPercent);
		h.lineTo(x + radius * (widthPercent / 2), y - radius * lengthPercent);
		h.lineTo(x - radius * (widthPercent / 2), y - radius * lengthPercent);
		h.lineTo(x - radius * (widthPercent / 2), y + radius * marginPercent);
	}
	
	/**
   * Draws a hand with the center of the clock face.
   * 
	 * @param hand
	 * @param widthPercent
	 * @param lengthPercent
	 * @param marginPercent
	 */
	private void createHand(Shape hand, float widthPercent, float lengthPercent, float marginPercent)
	{
		createHand(hand, x, y, radius, widthPercent, lengthPercent, marginPercent);
	}
	
	/**
	 * Paints 12 round ticks and time punctualities.
	 * 
	 * @see com.jungleford.smartcalendar.clock.analog.Parts#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
    int max = 12;
		BasicColor c = (BasicColor)colors;
		
    // Draw 12 ticks and numbers
    drawNumbers(g, numbers, max, x, y, radius, margin, trans, font, c.numbers);
		
		// Draw 12 ticks
		drawTicks(g, tick, max, x, y, trans, c.tick);
	}
	
	/**
	 * An algorithm to locate time punctualities numbers on a round clock face.
	 * 
	 * @param g graphics environment.
	 * @param numbers time punctualities numbers.
	 * @param max amount of "numbers" elements.
	 * @param x X coordinate of the rotate center.
	 * @param y Y coordinate of the rotate center.
	 * @param radius rotate radius.
	 * @param marginPercent leave some margin between numbers and clock boundary, in proportion to the radius by percentage.
	 * @param trans an additional transform, usually translate.
	 * @param font font of the numbers.
	 * @param color color of the numbers.
	 * 
	 * @see com.jungleford.smartcalendar.clock.analog.BasicParts#drawNumber(java.awt.Graphics, java.text.AttributedString, float, float, java.awt.Color)
	 */
	public static void drawNumbers(Graphics g, String[] numbers, int max, float x, float y, float radius, float marginPercent, AffineTransform trans, Font font, Color color)
	{
		if (numbers != null && numbers.length >= max)
		{
			AttributedString num;
			TextLayout layout;
			// Because the following accurate values are not zero accually,
			// but very close to zero.
			float sZero1 = (float)Math.sin((2 * Math.PI / 12) * 0);
			float sZero2 = (float)Math.sin((2 * Math.PI / 12) * 6);
			float cZero1 = (float)Math.cos((2 * Math.PI / 12) * 3);
			float cZero2 = (float)Math.cos((2 * Math.PI / 12) * 9);
			
			for (int p = 0; p < max; p++)
			{
				num = new AttributedString(numbers[p]);
				if (font != null)
				{
					num.addAttribute(TextAttribute.FONT, font);
					layout = new TextLayout(numbers[p], font, Clock.frc);
				}
				else
				{
					layout = new TextLayout(num.getIterator(), Clock.frc);
				}
				
				float width = layout.getBounds().getBounds().width;
				float sin = (float)Math.sin((2 * Math.PI / 12) * p);
				float cos = (float)Math.cos((2 * Math.PI / 12) * p);
				// Mark point
				float px = (float)(x + trans.getTranslateX() + radius * (1 + marginPercent) * sin);
				float py = (float)(y + trans.getTranslateY() - radius * (1 + marginPercent) * cos);
				
				// To locate the number at accurate position
				if (sin < sZero1)
				{// Left side
					px -= width;
				}
				else if (sin == sZero1 || sin == sZero2)
				{// Top or bottom
					px -= width / 2;
				}
				else
				{// Right side
					// Do nothing
				}
				
				if (cos < cZero2)
				{// Underside
					py += layout.getAscent();
				}
				else if (cos == cZero2 || cos == cZero1)
				{// Most left or most right
					py += (layout.getAscent() - layout.getDescent()) / 2;
				}
				else
				{// Upside
					py -= layout.getDescent();
				}
				
		    drawNumber(g, num, px, py, color);
			}
		}
	}
	
	/**
	 * Draws ticks.
	 * 
	 * @param g Graphics environment.
	 * @param tick shape of the ticks.
	 * @param x X coordinate of the rotate center.
	 * @param y Y coordinate of the rotate center.
	 * @param color color used.
	 * @param trans an additional transform, usually translate.
	 * @param tickNumber numbers of the ticks, usually 12 or 60.
	 */
	public static void drawTicks(Graphics g, Shape tick, int tickNumber,
			float x, float y, AffineTransform trans, Color color)
	{
		Graphics2D g2 = (Graphics2D)g;
    AffineTransform at = AffineTransform.getRotateInstance(0, x, y);
    
		for (int p = 0; p < tickNumber; p++)
		{
			at = RotateParts.getRotateInstance(x, y, tickNumber, p);
			g2.setPaint(color);
			g2.fill(trans.createTransformedShape(at.createTransformedShape(tick)));
		}
	}
}
