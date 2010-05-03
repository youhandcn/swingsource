package jlist;

import javax.swing.Icon;

/**
 * the interface that the list can have icon.
 * 
 * @author zeyuphoenix <br>
 *         daily combox LineEnable.java <br>
 *         2008 2008/03/18 14:52:17 <br>
 */
public interface IconItem {
	/**
	 * set bean.
	 * 
	 * @param iconItem
	 *            iconItem
	 */
	public void setIcon(Icon iconItem);

	/**
	 * get bean.
	 * 
	 * @return iconItem
	 */
	public Icon getIcon();
}
