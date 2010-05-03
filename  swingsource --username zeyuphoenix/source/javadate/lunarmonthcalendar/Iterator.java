package lunarmonthcalendar;

/**
 * To describe a kind of behavior named "roll" or "iterate":<br>
 * Based on text or list component. Can find current item, previous item, next
 * item, the first item, and the last item, and can move current item for a unit
 * (a pace).<br>
 */
public interface Iterator {
	/**
	 * Do rolling with integer.
	 */
	public static final int INTEGER_ALGORITHM = 1;

	/**
	 * Do rolling with float.
	 */
	public static final int DECIMAL_ALGORITHM = 2;

	/**
	 * Do rolling with list.
	 */
	public static final int LIST_ALGORITHM = 3;

	/**
	 * Default algorithm: return null.
	 */
	public static final int DEFAULT_ALGORITHM = 0;

	/**
	 * For <code>IterateAlgorithm</code>, <code>get()</code>#get() or
	 * <code>move()</code> to "next".
	 */
	public static final boolean INCREASE = true;

	/**
	 * For <code>IterateAlgorithm</code>, <code>get()</code>#get() or
	 * <code>move()</code> to "previous".
	 */
	public static final boolean DECREASE = false;

	/**
	 * @return current item.
	 */
	public Object getCurrentItem();

	/**
	 * @param index
	 *            index of the item to set.
	 */
	public void setCurrentItem(int index);

	/**
	 * @return the previous item.
	 */
	public Object getPreviousItem();

	/**
	 * @return the next item.
	 */
	public Object getNextItem();

	/**
	 * @return the first item.
	 */
	public Object getFirstItem();

	/**
	 * @return the last item.
	 */
	public Object getLastItem();

	/**
	 * Makes the previous item as current one.
	 */
	public void movePrevious();

	/**
	 * Makes the next item as current one.
	 */
	public void moveNext();

	/**
	 * Makes the first item as current one.
	 */
	public void moveFirst();

	/**
	 * Makes the last item as current one.
	 */
	public void moveLast();

	/**
	 * Determines whether the current pointer can roll back. If current item
	 * returns to the first one or not when it reachs the end, otherwise it
	 * stays at the end.
	 * 
	 * @return true if current item returns to first when it reachs the last
	 *         one.
	 */
	public boolean isRollback();

	/**
	 * Sets roll back policy: true if current item returns to the first one when
	 * it reachs the end; false if it stays at the end.
	 * 
	 * @param rollback
	 *            true if current item returns to the first one when it reachs
	 *            the end.
	 */
	public void setRollback(boolean rollback);

	/**
	 * Sets the span when a moving previous or next action occurs.
	 * 
	 * @param pace
	 *            how many units to skip when move to next unit.
	 */
	public void setStep(int pace);
}
