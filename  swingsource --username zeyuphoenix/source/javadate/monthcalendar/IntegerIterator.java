/*
 * Open source project: Smart Calendar.
 * 
 * @(#)IntegerIterator.java  2006/02/21
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
package monthcalendar;

import java.io.Serializable;

/**
 * A iterator which can iterate integers in a specific range.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class IntegerIterator implements Iterator, Serializable
{
  /**
   * Default serial version ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The first number.
   */
  protected Integer first;
  
  /**
   * The last number.
   */
  protected Integer last;
  
  /**
   * Current number.
   */
  protected Integer current;
  
  /**
   * Rollback or not when reach the first or the last item.
   */
  protected boolean rollback;

	/**
   * How many items to skip when moving previous or next.
	 */
  protected int pace;
  
  /**
   * Default constructor:
   * Only applies to derived classes.
   * Initializes members: range is from the minimum integer to the maximum,
   * current position is zero,
   * and each move to next or previous element increases or decreases one.
   */
  protected IntegerIterator()
  {
    first = Integer.valueOf(Integer.MIN_VALUE);
    last = Integer.valueOf(Integer.MAX_VALUE);
    current = Integer.valueOf(0);
    rollback = false;
    pace = 1;
  }
  
  /**
   * Constructor:
   * Initializes members by specific parameters.<br><br>
   * 
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @param start first number of the range.
   * @param end last number of the range.
   * @param current current number when <code>IntegerIterator</code> initialized.
   * 
   * @throws com.jungleford.common.iterator.IterateException
   * if the last number is smaller than the first one,
   * or current is not among of the range,
   * or objects are null.
   */
  public IntegerIterator(Integer start, Integer end, Integer current) throws Exception
  {
    if (start == null || end == null || current == null)
      throw new Exception("Parameters NOT initialized.");
    if (start > end)
      throw new Exception("End object should NOT be smaller than the begin one.");
    if (current < start || current > end)
      throw new Exception("Current object should be bigger than the begin one and smaller than the end one.");
    this.first = start;
    this.last = end;
    this.current = current;
    this.rollback = false;
    this.pace = 1;
  }
  
  /**
   * Constructor:
   * Considers start number as current.<br><br>
   * 
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @param start first number of the range.
   * @param end last number of the range.
   * 
   * @throws com.jungleford.common.iterator.IterateException
   * if the last number is smaller than the first one, or objects are null.
   */
  public IntegerIterator(Integer start, Integer end) throws Exception
  {
    if (start == null || end == null)
      throw new Exception("Parameters NOT initialized.");
    if (start > end)
      throw new Exception("End object should NOT be smaller than the begin one.");
    this.first = start;
    this.last = end;
    this.current = start;
    this.rollback = false;
    this.pace = 1;
  }

  /**
   * @see com.jungleford.common.iterator.Iterator#getCurrentItem()
   */
  public Object getCurrentItem()
  {
    return current;
  }

	/**
	 * @see com.jungleford.common.iterator.Iterator#setCurrentItem(int)
	 */
	public void setCurrentItem(int index)
	{
		if (index >= first && index <= last)
		{
			current = index;
		}
		else if (rollback)
		{
    	int len = last - first + 1;
    	if (index < first)
    	{
    		current = last - ((first - index) % len) + 1;
    	}
    	else
    	{
    		current = first + ((index - last) % len) - 1;
    	}
		}
	}

  /**
   * If current is null, then return null.<br><br>
   * 
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @see com.jungleford.common.iterator.Iterator#getPreviousItem()
   */
  public Object getPreviousItem()
  {
    if (current - pace < first || // Reach head
    		current - pace > last) // Reach tail
    {
      if (rollback)
      {
      	int len = last - first + 1;
        return 
        	current - pace >= 0 ?
        	(current - pace) % len :
        	((current - pace) % len) + len;
      }
      else if (current - pace < 0)
      	return getFirstItem();
      else
      	return getLastItem();
    }
    else
    {
      return current - pace;
    }
  }

  /**
   * If current is null, then return null.<br><br>
   * 
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @see com.jungleford.common.iterator.Iterator#getNextItem()
   */
  public Object getNextItem()
  {
    if (current + pace > last || // Reach tail
    		current + pace < first) // Reach head
    {
      if (rollback)
      {
      	int len = last - first + 1;
        return 
        	current + pace >= 0 ?
        	(current + pace) % len :
        	((current + pace) % len) + len;
      }
      else if (current + pace > last)
      	return getLastItem();
      else
      	return getFirstItem();
    }
    else
    {
      return current + pace;
    }
  }

  /**
   * If current is null, then return null.
   * 
   * @see com.jungleford.common.iterator.Iterator#getFirstItem()
   */
  public Object getFirstItem()
  {
    return first;
  }

  /**
   * If current is null, then return null.
   * 
   * @see com.jungleford.common.iterator.Iterator#getLastItem()
   */
  public Object getLastItem()
  {
    return last;
  }

  /**
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @see com.jungleford.common.iterator.Iterator#movePrevious()
   */
  public void movePrevious()
  {
    if (current - pace < first || // Reach head
    		current - pace > last) // Reach tail
    {
      if (rollback)
      {
      	int len = last - first + 1;
      	current =
      		current - pace >= 0 ?
        	(current - pace) % len :
        	((current - pace) % len) + len;
      }
      else if (current - pace < first)
      	moveFirst();
      else
      	moveLast();
    }
    else
    {
    	current -= pace;
    }
  }

  /**
   * Notes: This method uses J2SE 5.0's new feature: auto-boxing and auto-unboxing.
   * 
   * @see com.jungleford.common.iterator.Iterator#moveNext()
   */
  public void moveNext()
  {
    if (current + pace > last || // Reach tail
    		current + pace < first) // Reach head
    {
      if (rollback)
      {
      	int len = last - first + 1;
      	current =
      		current + pace >= 0 ?
        	(current + pace) % len :
        	((current + pace) % len) + len;
      }
      else if (current + pace > last)
      	moveLast();
      else
      	moveFirst();
    }
    else
    {
    	current += pace;
    }
  }


  /**
   * @see com.jungleford.common.iterator.Iterator#moveFirst()
   */
  public void moveFirst()
  {
    current = first;
  }

  /**
   * @see com.jungleford.common.iterator.Iterator#moveLast()
   */
  public void moveLast()
  {
    current = last;
  }

  /**
   * @see com.jungleford.common.iterator.Iterator#isRollback()
   */
  public boolean isRollback()
  {
    return rollback;
  }

  /**
   * @see com.jungleford.common.iterator.Iterator#setRollback(boolean)
   */
  public void setRollback(boolean rollback)
  {
    this.rollback = rollback;
  }

  /**
   * Default pace is 1.
   * 
   * @see com.jungleford.common.iterator.Iterator#setStep(int)
   */
	public void setStep(int pace)
	{
		this.pace = pace;
	}
}
