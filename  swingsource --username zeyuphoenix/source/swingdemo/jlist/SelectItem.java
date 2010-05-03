package jlist;

/**
 * the interface that the list can have select.
 * 
 * @author zeyuphoenix <br>
 *         daily combox LineEnable.java <br>
 *         2008 2008/03/18 14:52:17 <br>
 */
public interface SelectItem {
	/**
	 * set bean.
	 * 
	 * @param selectItem
	 *            selectItem
	 */
	public void setSelect(boolean selectItem);

	/**
	 * get bean.
	 * 
	 * @return selectItem
	 */
	public boolean getSelect();
}
