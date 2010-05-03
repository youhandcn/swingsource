package jlist;

import java.util.Enumeration;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * model node check.
 * 
 * @author zeyuphoenix <br>
 *         daily jlist CheckNode.java <br>
 *         2008 2008/03/19 14:01:43 <br>
 */
public class CheckNode extends DefaultMutableTreeNode {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * NO SELECT.
	 */
	public final static int DIG_IN_SELECTION = 4;
	/**
	 * select model.
	 */
	private int selectionMode = 0;
	/**
	 * is select.
	 */
	private boolean isSelected = false;

	/**
	 * model node check.
	 */
	public CheckNode() {
		this(null);
	}

	/**
	 * model node check.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 */
	public CheckNode(Object userObject) {
		this(userObject, true, false);
	}

	/**
	 * model node check.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param isSelected
	 *            is select.
	 */
	public CheckNode(Object userObject, boolean isSelected) {
		this(userObject, true, isSelected);
	}

	/**
	 * model node check.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 * @param isSelected
	 *            is select.
	 */
	public CheckNode(Object userObject, boolean allowsChildren,
			boolean isSelected) {
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		setSelectionMode(DIG_IN_SELECTION);
	}

	/**
	 * set select model.
	 * 
	 * @param mode
	 *            model
	 */
	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	/**
	 * get select model.
	 * 
	 * @return model.
	 */
	public int getSelectionMode() {
		return selectionMode;
	}

	/**
	 * set it select.
	 * 
	 * @param isSelected
	 *            is select.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if ((selectionMode == DIG_IN_SELECTION) && (children != null)) {
			Enumeration<?> enumTemp = children.elements();
			while (enumTemp.hasMoreElements()) {
				CheckNode node = (CheckNode) enumTemp.nextElement();
				node.setSelected(isSelected);
			}
		}
	}

	/**
	 * is select.
	 * 
	 * @return is select.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * If you want to change "isSelected" by CellEditor.
	 */
	public void setUserObject(Object obj) {
		if (obj instanceof Boolean) {
			setSelected(((Boolean) obj).booleanValue());
		} else {
			super.setUserObject(obj);
		}
	}
}
