/*
 * Open source project: Smart Calendar.
 * 
 * @(#)RotateParts.java  2006/02/21
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

import java.awt.geom.AffineTransform;

/**
 * This class defines a classical clock behavior by using rotation pattern,
 * as we all know in common sense.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public abstract class RotateParts extends Parts
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * X coordinate of the center.
	 */
	protected float x;
	
	/**
	 * Y coordinate of the center.
	 */
	protected float y;
	
	/**
	 * Radius of the clock face.
	 */
	protected float radius;
	
	/**
	 * @return a rotation instance from 12 o'clock direction.
   * 
   * @see java.awt.geom.AffineTransform#getRotateInstance(double, double, double)
	 */
	public AffineTransform getTransform()
	{
		return AffineTransform.getRotateInstance(0, x, y);
	}
	
	/**
	 * Sets rotatation algorithm by given value.
	 * 
	 * @param af a rotatation transform.
	 * @param value a given value, usually current time.
	 * @param grad the amount of graduations, 12 for hour hand, 60 for minute hand, etc.
   * 
   * @see java.awt.geom.AffineTransform#setToRotation(double, double, double)
	 */
	public void setToRotation(AffineTransform af, double value, int grad)
	{
		af.setToRotation(value * (2 * Math.PI / grad), x, y);
	}
	
	/**
	 * Gets a rotation transform by given parameters.
	 * 
	 * @param grad the amount of graduations.
	 * @param seq current graduation reached.
   * 
   * @return a rotation instance with an angle leaving 12 o'clock direction.
   * 
   * @see #getRotateInstance(float, float, int, int)
	 */
	public AffineTransform getRotateInstance(int grad, int seq)
	{
		return getRotateInstance(x, y, grad, seq);
	}
	
	/**
	 * Get a rotation transform by given parameters.
	 * 
   * @param x X coordinate of the rotate center.
   * @param y Y coordinate of the rotate center.
	 * @param grad the amount of graduations.
	 * @param seq current graduation reached.
   * 
   * @return a rotation instance with an angle leaving 12 o'clock direction.
   * 
   * @see java.awt.geom.AffineTransform#getRotateInstance(double, double, double)
	 */
	public static AffineTransform getRotateInstance(float x, float y, int grad, int seq)
	{
		return AffineTransform.getRotateInstance((2 * Math.PI / grad) * seq, x, y);
	}
}
