package jtree;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * A simple tree data model that uses TreeNodes.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTreeModel.java <br>
 *         2008 2008/03/26 13:14:04 <br>
 */
public class MyTreeModel extends DefaultTreeModel {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** is active. */
	private boolean filterIsActive = false;

	/**
	 * Creates a tree specifying whether any node can have children, or whether
	 * only certain nodes can have children.
	 * 
	 * @param root
	 *            a TreeNode object that is the root of the tree
	 */
	public MyTreeModel(TreeNode root) {
		this(root, false);
	}

	/**
	 * Creates a tree specifying whether any node can have children, or whether
	 * only certain nodes can have children.
	 * 
	 * @param root
	 *            a TreeNode object that is the root of the tree
	 * @param asksAllowsChildren
	 *            a boolean, false if any node can have children, true if each
	 *            node is asked to see if it can have children
	 */
	public MyTreeModel(TreeNode root, boolean asksAllowsChildren) {
		this(root, false, false);
	}

	/**
	 * Creates a tree specifying whether any node can have children, or whether
	 * only certain nodes can have children.
	 * 
	 * @param root
	 *            a TreeNode object that is the root of the tree
	 * @param asksAllowsChildren
	 *            a boolean, false if any node can have children, true if each
	 *            node is asked to see if it can have children
	 * @param filterIsActive
	 *            is active.
	 */
	public MyTreeModel(TreeNode root, boolean asksAllowsChildren,
			boolean filterIsActive) {
		super(root, asksAllowsChildren);
		this.filterIsActive = filterIsActive;
	}

	/**
	 * set active.
	 * 
	 * @param newValue
	 */
	public void activateFilter(boolean newValue) {
		filterIsActive = newValue;
	}

	/**
	 * get is active.
	 * 
	 * @return
	 */
	public boolean isActivatedFilter() {
		return filterIsActive;
	}

	/**
	 * Returns the child of <I>parent</I> at index <I>index</I> in the
	 * parent's child array. <I>parent</I> must be a node previously obtained
	 * from this data source.
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the child of <I>parent</I> at index <I>index</I>
	 */
	public Object getChild(Object parent, int index) {
		if (filterIsActive) {
			if (parent instanceof MyTreeNode) {
				return ((MyTreeNode) parent).getChildAt(index, filterIsActive);
			}
		}
		return ((TreeNode) parent).getChildAt(index);
	}

	/**
	 * Returns the number of children of <I>parent</I>. Returns 0 if the node
	 * is a leaf or if it has no children. <I>parent</I> must be a node
	 * previously obtained from this data source.
	 * 
	 * @param parent
	 *            a node in the tree, obtained from this data source
	 * @return the number of children of the node <I>parent</I>
	 */
	public int getChildCount(Object parent) {
		if (filterIsActive) {
			if (parent instanceof MyTreeNode) {
				return ((MyTreeNode) parent).getChildCount(filterIsActive);
			}
		}
		return ((TreeNode) parent).getChildCount();
	}

}
