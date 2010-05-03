package monthcalendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A iterator which can iterate objects list or array.
 */
public class ListIterator implements Iterator, Serializable {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Item list.
	 */
	protected List<Object> list;

	/**
	 * Current item.
	 */
	protected Object current;

	/**
	 * A pointer integer to indicate current item.
	 */
	protected int currentIndex;

	/**
	 * Rollback or not when reach the first or the last item.
	 */
	protected boolean rollback;

	/**
	 * How many items to skip when moving previous or next.
	 */
	protected int pace;

	/**
	 * Default constructor: Only applies to derived classes.
	 */
	protected ListIterator() {
		this.list = new ArrayList<Object>();
		this.current = null;
		this.currentIndex = 0;
		this.rollback = false;
		this.pace = 1;
	}

	/**
	 * Constructor: Uses the specific list and current item.
	 * 
	 * @param list
	 *            the list to be iterated by this <code>Iterator</code>.
	 * @param current
	 *            current element in the list.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if current item is not among the given list.
	 */
	public ListIterator(List<Object> list, Object current) throws Exception {
		if (list == null)
			list = new ArrayList<Object>();
		if (current != null && !list.contains(current))
			throw new Exception("Current object is NOT among the given list.");
		this.list = list;
		this.current = current;
		this.currentIndex = list.indexOf(current);
		this.rollback = false;
		this.pace = 1;
	}

	/**
	 * Constructor: Considers the first item as current one. If list is empty or
	 * null, current will be null.
	 * 
	 * @param list
	 *            the list to be iterated by this <code>Iterator</code>.
	 */
	public ListIterator(List<Object> list) {
		if (list == null)
			list = new ArrayList<Object>();
		this.list = list;
		this.current = list.size() > 0 ? list.get(0) : null;
		this.currentIndex = 0;
		this.rollback = false;
		this.pace = 1;
	}

	/**
	 * Constructor: Converts array of objects to list.
	 * 
	 * @param list
	 *            the list to be iterated by this <code>Iterator</code>.
	 * @param current
	 *            current element in the list.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if current item is not among the given list.
	 */
	public ListIterator(Object[] list, Object current) throws Exception {
		List<Object> l = list == null ? new ArrayList<Object>() : Arrays
				.asList(list);
		if (current != null && !l.contains(current))
			throw new Exception("Current object is NOT among the given list.");
		this.list = l;
		this.current = current;
		this.currentIndex = l.indexOf(current);
		this.rollback = false;
		this.pace = 1;
	}

	/**
	 * Constructor: Converts array of objects to list. Considers the first item
	 * as current one. If list is empty or null, current will be null.
	 * 
	 * @param list
	 *            the list to be iterated by this <code>Iterator</code>.
	 */
	public ListIterator(Object[] list) {
		List<Object> l = list == null ? new ArrayList<Object>() : Arrays
				.asList(list);
		this.list = l;
		this.current = l.size() > 0 ? l.get(0) : null;
		this.currentIndex = 0;
		this.rollback = false;
		this.pace = 1;
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#getCurrentItem()
	 */
	public Object getCurrentItem() {
		return current;
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#setCurrentItem(int)
	 */
	public void setCurrentItem(int index) {
		if (index >= 0 && index < list.size()) {
			currentIndex = index;
		} else if (rollback) {
			if (index >= list.size()) {
				currentIndex = index % list.size();
			} else {
				currentIndex = list.size() + (index % list.size());
			}
		}
		current = list.get(currentIndex);
	}

	/**
	 * If current is null, then return null.
	 * 
	 * @see com.jungleford.common.iterator.Iterator#getPreviousItem()
	 */
	public Object getPreviousItem() {
		if (current == null)
			return null;

		if (currentIndex - pace < 0 || // Reach head
				currentIndex - pace > list.size() - 1) // Reach tail
		{
			if (rollback) {
				return list
						.get(currentIndex - pace >= 0 ? (currentIndex - pace)
								% list.size() : ((currentIndex - pace) % list
								.size())
								+ list.size());
			} else if (currentIndex - pace < 0)
				return getFirstItem();
			else
				return getLastItem();
		} else {
			return list.get(currentIndex - pace);
		}
	}

	/**
	 * If current is null, then return null.
	 * 
	 * @see com.jungleford.common.iterator.Iterator#getNextItem()
	 */
	public Object getNextItem() {
		if (current == null)
			return null;

		if (currentIndex + pace > list.size() - 1 || // Reach tail
				currentIndex + pace < 0) // Reach head
		{
			if (rollback) {
				return list
						.get(currentIndex + pace >= 0 ? (currentIndex + pace)
								% list.size() : ((currentIndex + pace) % list
								.size())
								+ list.size());
			} else if (currentIndex + pace > list.size() - 1)
				return getLastItem();
			else
				return getFirstItem();
		} else {
			return list.get(currentIndex + pace);
		}
	}

	/**
	 * If current is null, then return null.
	 * 
	 * @see com.jungleford.common.iterator.Iterator#getFirstItem()
	 */
	public Object getFirstItem() {
		if (current == null)
			return null;

		return list.get(0);
	}

	/**
	 * If current is null, then return null.
	 * 
	 * @see com.jungleford.common.iterator.Iterator#getLastItem()
	 */
	public Object getLastItem() {
		if (current == null)
			return null;

		return list.get(list.size() - 1);
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#movePrevious()
	 */
	public void movePrevious() {
		if (current != null) {
			if (currentIndex - pace < 0 || // Reach head
					currentIndex - pace > list.size() - 1) // Reach tail
			{
				if (rollback) {
					currentIndex = currentIndex - pace >= 0 ? (currentIndex - pace)
							% list.size()
							: ((currentIndex - pace) % list.size())
									+ list.size();
					current = list.get(currentIndex);
				} else if (currentIndex - pace < 0)
					moveFirst();
				else
					moveLast();
			} else {
				currentIndex -= pace;
				current = list.get(currentIndex);
			}
		}
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveNext()
	 */
	public void moveNext() {
		if (current != null) {
			if (currentIndex + pace > list.size() - 1 || // Reach tail
					currentIndex + pace < 0) // Reach head
			{
				if (rollback) {
					currentIndex = currentIndex + pace >= 0 ? (currentIndex + pace)
							% list.size()
							: ((currentIndex + pace) % list.size())
									+ list.size();
					current = list.get(currentIndex);
				} else if (currentIndex + pace > list.size() - 1)
					moveLast();
				else
					moveFirst();
			} else {
				currentIndex += pace;
				current = list.get(currentIndex);
			}
		}
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveFirst()
	 */
	public void moveFirst() {
		if (current != null) {
			currentIndex = 0;
			current = list.get(currentIndex);
		}
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveLast()
	 */
	public void moveLast() {
		currentIndex = list.size() - 1;
		current = list.get(currentIndex);
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#isRollback()
	 */
	public boolean isRollback() {
		return rollback;
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#setRollback(boolean)
	 */
	public void setRollback(boolean rollback) {
		this.rollback = rollback;
	}

	/**
	 * Default pace is 1.
	 * 
	 * @see com.jungleford.common.iterator.Iterator#setStep(int)
	 */
	public void setStep(int pace) {
		this.pace = pace;
	}
}
