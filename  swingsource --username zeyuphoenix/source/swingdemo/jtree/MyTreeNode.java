package jtree;

import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * the tree node that I rewrite it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTreeNode.java <br>
 *         2008 2008/03/25 17:38:58 <br>
 */
public class MyTreeNode extends DefaultMutableTreeNode {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** is select or not. */
	private boolean isSelected = false;
	/** is enable. */
	private boolean enabled = false;
	/** is visible or not. */
	private boolean isVisible = false;
	/** it's icon. */
	private Icon icon = null;
	/** icon name. */
	private String iconName = null;
	/** select model. */
	private int selectionMode = 0;

	/**
	 * Creates a tree node that has no parent and no children, but which allows
	 * children.
	 */
	public MyTreeNode() {
		this(null, true, false, true, true, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 */
	public MyTreeNode(Object userObject) {
		this(userObject, true, false, true, true, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 */
	public MyTreeNode(Object userObject, boolean allowsChildren) {
		this(userObject, allowsChildren, false, true, true, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 * @param isSelected
	 *            is select or not.
	 */
	public MyTreeNode(Object userObject, boolean allowsChildren,
			boolean isSelected) {
		this(userObject, allowsChildren, isSelected, true, true, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 * @param isSelected
	 *            is select or not.
	 * @param enabled
	 *            is enable.
	 */
	public MyTreeNode(Object userObject, boolean allowsChildren,
			boolean isSelected, boolean enabled) {
		this(userObject, allowsChildren, isSelected, enabled, true, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 * @param isSelected
	 *            is select or not.
	 * @param enabled
	 *            is enable.
	 * @param isVisible
	 *            is visible or not.
	 */
	public MyTreeNode(Object userObject, boolean allowsChildren,
			boolean isSelected, boolean enabled, boolean isVisible) {
		this(userObject, allowsChildren, isSelected, enabled, enabled, null);
	}

	/**
	 * Creates a tree node with no parent, no children, initialized with the
	 * specified user object, and that allows children only if specified.
	 * 
	 * @param userObject
	 *            an Object provided by the user that constitutes the node's
	 *            data
	 * @param allowsChildren
	 *            if true, the node is allowed to have child nodes -- otherwise,
	 *            it is always a leaf node
	 * @param isSelected
	 *            is select or not.
	 * @param enabled
	 *            is enable.
	 * @param isVisible
	 *            is visible or not.
	 * @param icon
	 *            it's icon.
	 */
	public MyTreeNode(Object userObject, boolean allowsChildren,
			boolean isSelected, boolean enabled, boolean isVisible, Icon icon) {
		super(userObject, allowsChildren);
		this.isSelected = isSelected;
		this.enabled = enabled;
		this.isVisible = isVisible;
		this.icon = icon;
		setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
	}

	/**
	 * set select model.
	 * 
	 * @param mode
	 *            select model.
	 */
	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	/**
	 * get select model.
	 * 
	 * @return select model.
	 */
	public int getSelectionMode() {
		return selectionMode;
	}

	/**
	 * set the node select.
	 * 
	 * @param isSelected
	 *            node select or not.
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;

		if ((selectionMode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION)
				&& (children != null)) {
			Enumeration<?> enumTemp = children.elements();
			while (enumTemp.hasMoreElements()) {
				MyTreeNode node = (MyTreeNode) enumTemp.nextElement();
				node.setSelected(isSelected);
			}
		}
	}

	/**
	 * get the node is select.
	 * 
	 * @return is select.
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * change "isSelected" by CellEditor.
	 * 
	 * @param obj
	 *            cell editor.
	 */
	public void setUserObject(Object obj) {
		if (obj instanceof Boolean) {
			setSelected(((Boolean) obj).booleanValue());
		} else {
			super.setUserObject(obj);
		}
	}

	/**
	 * get node's child count.
	 * 
	 * @return the node's child count.
	 */
	public int getChildCount() {
		if (enabled) {
			return super.getChildCount();
		} else {
			return 0;
		}
	}

	/**
	 * is leaf or not.
	 * 
	 * @return is leaf.
	 */
	public boolean isLeaf() {
		return (super.getChildCount() == 0);
	}

	/**
	 * set the node is enable.
	 * 
	 * @param enabled
	 *            is enable.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * get the node is enable.
	 * 
	 * @return is enable.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * set node icon.
	 * 
	 * @param icon
	 *            node icon.
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * get node icon.
	 * 
	 * @return node icon.
	 */
	public Icon getIcon() {
		return icon;
	}

	/**
	 * get icon name.
	 * 
	 * @return icon name.
	 */
	public String getIconName() {
		if (iconName != null) {
			return iconName;
		} else {
			String str = userObject.toString();
			int index = str.lastIndexOf(".");
			if (index != -1) {
				return str.substring(++index);
			} else {
				return null;
			}
		}
	}

	/**
	 * set icon name.
	 * 
	 * @param name
	 *            icon name.
	 */
	public void setIconName(String name) {
		iconName = name;
	}

	/**
	 * set is the node and it's child visible or not.
	 * 
	 * @param visible
	 *            visible or not.
	 */
	public void setVisible(boolean visible) {
		this.isVisible = visible;
	}

	/**
	 * get is the node and it's child visible or not.
	 * 
	 * @return visible or not.
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Returns the child at the specified index in this node's child array.
	 * 
	 * @param index
	 *            an index into this node's child array
	 * @param filterIsActive
	 *            the node is enable or not.
	 * @exception ArrayIndexOutOfBoundsException
	 *                if <code>index</code> is out of bounds
	 * @return the TreeNode in this node's child array at the specified index
	 */
	public TreeNode getChildAt(int index, boolean filterIsActive) {
		if (!filterIsActive) {
			return super.getChildAt(index);
		}
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException("node has no children");
		}

		int realIndex = -1;
		int visibleIndex = -1;
		Enumeration<?> enumTemp = children.elements();
		while (enumTemp.hasMoreElements()) {
			MyTreeNode node = (MyTreeNode) enumTemp.nextElement();
			if (node.isVisible()) {
				visibleIndex++;
			}
			realIndex++;
			if (visibleIndex == index) {
				return (TreeNode) children.elementAt(realIndex);
			}
		}

		throw new ArrayIndexOutOfBoundsException("index unmatched");
	}

	/**
	 * get child count.
	 * 
	 * @param filterIsActive
	 *            the node is enable or not.
	 * @return giving the number of children of this node
	 */
	public int getChildCount(boolean filterIsActive) {
		if (!filterIsActive) {
			return super.getChildCount();
		}
		if (children == null) {
			return 0;
		}
		int count = 0;
		Enumeration<?> enumTemp = children.elements();
		while (enumTemp.hasMoreElements()) {
			MyTreeNode node = (MyTreeNode) enumTemp.nextElement();
			if (node.isVisible()) {
				count++;
			}
		}
		return count;
	}
}
