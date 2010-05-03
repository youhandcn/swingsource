package jlist;

import javax.swing.Icon;

/**
 * list item.
 * 
 * @author zeyuphoenix <br>
 *         daily jlist ListItem.java <br>
 *         2008 2008/03/19 13:56:30 <br>
 */
public class MyListItem implements IconItem, IconEnable, SelectEnable,
		SelectItem {

	/**
	 * list value.
	 */
	private Object listItem = null;
	/**
	 * list is select.
	 */
	private boolean isSelected = false;
	/**
	 * list icon.
	 */
	private Icon icon = null;
	/**
	 * list icon enable.
	 */
	private boolean isIconEnable = true;
	/**
	 * list is select enable.
	 */
	private boolean isSelectEnable = true;

	/**
	 * list item.
	 * 
	 * @param str
	 *            list value.
	 */
	public MyListItem(Object listItem) {
		this(listItem, false, null, true, true);
	}

	/**
	 * the items that you want to show in list..
	 * 
	 * @param listItem
	 *            item.
	 * @param isSelectEnabled
	 *            is Select Enabled
	 */
	public MyListItem(Object listItem, boolean isSelectEnabled) {
		this(listItem, isSelectEnabled, null, true, true);
	}

	/**
	 * the items that you want to show in list..
	 * 
	 * @param listItem
	 *            item.
	 * @param isSelectEnabled
	 *            is Select Enabled
	 * @param isLineEnabled
	 *            is icon Enabled
	 */
	public MyListItem(Object listItem, boolean isSelectEnabled, Icon iconItem) {
		this(listItem, isSelectEnabled, iconItem, true, true);
	}

	/**
	 * the items that you want to show in list..
	 * 
	 * @param listItem
	 *            item.
	 * @param isSelectEnabled
	 *            is Select Enabled
	 * @param isLineEnabled
	 *            is icon Enabled
	 */
	public MyListItem(Object listItem, boolean isSelect, Icon iconItem,
			boolean isSelectEnabled, boolean isIconEnable) {
		this.listItem = listItem;
		this.isSelectEnable = isSelectEnabled;
		this.icon = iconItem;
		this.isIconEnable = isIconEnable;
		this.isSelected = isSelect;
	}

	/**
	 * get list value.
	 */
	public String toString() {
		return listItem == null ? "" : listItem.toString();
	}

	/**
	 * set list icon.
	 * 
	 * @param icon
	 *            list icon.
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * get list icon.
	 * 
	 * @return list icon.
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * list is select.
	 * 
	 * @param b
	 *            list is select.
	 */
	public boolean isSelectEnabled() {
		return isSelectEnable;
	}

	/**
	 * list is select.
	 * 
	 * @return list is select.
	 */
	public void setSelectEnabled(boolean isSelectEnable) {
		this.isSelectEnable = isSelectEnable;

	}

	@Override
	public boolean isIconEnabled() {
		return isIconEnable;
	}

	@Override
	public void setIconEnabled(boolean isIconEnable) {
		this.isIconEnable = isIconEnable;
	}

	@Override
	public boolean getSelect() {
		return isSelected;
	}

	@Override
	public void setSelect(boolean selectItem) {
		this.isSelected = selectItem;
	}
}
