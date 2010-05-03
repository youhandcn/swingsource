package jcombobox;

/**
 * the items that you want to show in JComboBox.
 * 
 * @author zeyuphoenix <br>
 *         daily jcombobox MyComboBoxItem.java <br>
 *         2008 2008/03/18 17:21:25 <br>
 */
public class MyComboBoxItem implements SelectEnable, LineEnable {
	/**
	 * JComboBox items.
	 */
	private Object comboxItem = null;
	/**
	 * is select enabled.
	 */
	boolean isSelectEnabled = true;
	/**
	 * is line enabled.
	 */
	boolean isLineEnabled = false;

	/**
	 * the items that you want to show in JComboBox.
	 * 
	 * @param comboxItem
	 *            item.
	 * @param isSelectEnabled
	 *            is Select Enabled
	 * @param isLineEnabled
	 *            is Line Enabled
	 */
	public MyComboBoxItem(Object comboxItem, boolean isSelectEnabled,
			boolean isLineEnabled) {
		this.comboxItem = comboxItem;
		this.isSelectEnabled = isSelectEnabled;
		this.isLineEnabled = isLineEnabled;
	}

	/**
	 * the items that you want to show in JComboBox.
	 * 
	 * @param comboxItem
	 *            item.
	 * @param isSelectEnabled
	 *            is Select Enabled
	 */
	public MyComboBoxItem(Object comboxItem, boolean isSelectEnabled) {
		this(comboxItem, isSelectEnabled, false);
	}

	/**
	 * the items that you want to show in JComboBox.
	 * 
	 * @param comboxItem
	 *            item.
	 */
	public MyComboBoxItem(Object comboxItem) {
		this(comboxItem, true, false);
	}

	/**
	 * get enable.
	 * 
	 * @return is Select enable
	 */
	public boolean isSelectEnabled() {
		return isSelectEnabled;
	}

	/**
	 * set enable.
	 * 
	 * @param isSelectEnable
	 *            is Select enable
	 */
	public void setSelectEnabled(boolean isSelectEnable) {
		this.isSelectEnabled = isSelectEnable;
	}

	/**
	 * get enable.
	 * 
	 * @return is Line enable
	 */
	public boolean isLineEnabled() {
		return isLineEnabled;
	}

	/**
	 * set enable.
	 * 
	 * @param isLineEnable
	 *            is Line enable
	 */
	public void setLineEnabled(boolean isLineEnable) {
		this.isLineEnabled = isLineEnable;
	}

	/**
	 * override toString.
	 */
	public String toString() {
		return ((comboxItem == null) ? "" : comboxItem.toString());
	}
}
