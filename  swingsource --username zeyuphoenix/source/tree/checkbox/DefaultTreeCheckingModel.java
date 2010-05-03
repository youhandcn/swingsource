package checkbox;

import java.util.HashSet;
import java.util.Vector;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * The default tree checking model provides: - methods for store checked
 * TreePaths and retrieve them. It doesn't provide the implementation of
 * addCheckingPath and removeCheckingPath methods (are delegated to
 * CheckingMode). This implementation is based on TreePath only and does not
 * take advantage of TreeNode convenience methods. Alternative implementation
 * may assume that the tree nodes be TreeNode instances.
 */
public class DefaultTreeCheckingModel implements TreeCheckingModel {

	private HashSet<TreePath> checkedPathsSet;

	private PropagateCheckingListener propagateCheckingListener;

	protected TreeCheckingMode checkingMode;

	protected TreeModel model;

	/** Event listener list. */
	protected EventListenerList listenerList = new EventListenerList();

	/**
	 * Creates a DefaultTreeCheckingModel with PropagateTreeCheckingMode.
	 */
	public DefaultTreeCheckingModel(TreeModel model) {
		this.model = model;
		this.checkedPathsSet = new HashSet<TreePath>();
		this.propagateCheckingListener = new PropagateCheckingListener();
		this.setCheckingMode(CheckingMode.PROPAGATE);
	}

	private class PropagateCheckingListener implements TreeModelListener {
		/**
		 * Updates the check of the just inserted nodes.
		 */
		public void treeNodesInserted(TreeModelEvent e) {
			TreePath path = e.getTreePath();
			DefaultTreeCheckingModel.this.checkingMode
					.updateCheckAfterChildrenInserted(path);
		}

		/**
		 * Nothing to do if nodes were removed.
		 */
		public void treeNodesRemoved(TreeModelEvent e) {
			TreePath path = e.getTreePath();
			DefaultTreeCheckingModel.this.checkingMode
					.updateCheckAfterChildrenRemoved(path);
		}

		/**
		 * Updates the tree greyness in case of nodes changes.
		 */
		public void treeNodesChanged(TreeModelEvent e) {
		}

		/**
		 * Updates the tree greyness in case of structure changes.
		 */
		public void treeStructureChanged(TreeModelEvent e) {
			TreePath path = e.getTreePath();
			DefaultTreeCheckingModel.this.checkingMode
					.updateCheckAfterStructureChanged(path);
		}
	}

	public boolean isPathChecked(TreePath path) {
		return this.checkedPathsSet.contains(path);
	}

	void addToCheckedPathsSet(TreePath path) {
		this.checkedPathsSet.add(path);
	}

	void removeFromCheckedPathsSet(TreePath path) {
		this.checkedPathsSet.remove(path);
	}

	/**
	 * Checks the subtree with root path.
	 * 
	 * @param path
	 *            root of the tree to be checked
	 */
	public void checkSubTree(final TreePath path) {
		addToCheckedPathsSet(path);
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			checkSubTree(childPath);
		}
	}

	/**
	 * Unchecks the subtree with root path.
	 * 
	 * @param path
	 *            root of the tree to be unchecked
	 */
	public void uncheckSubTree(TreePath path) {
		removeFromCheckedPathsSet(path);
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			uncheckSubTree(childPath);
		}
	}

	/**
	 * Delegates to the current checkingMode the toggling style, using the
	 * Strategy Pattern.
	 */
	public void toggleCheckingPath(TreePath path) {
		if (isPathChecked(path)) {
			removeCheckingPath(path);
		} else {
			addCheckingPath(path);
		}

	}

	/**
	 * Sets the checking to path.
	 */
	public void setCheckingPath(TreePath path) {
		clearChecking();
		addCheckingPath(path);
	}

	/**
	 * Sets the checking to paths.
	 */
	public void setCheckingPaths(TreePath[] paths) {
		clearChecking();
		for (TreePath path : paths) {
			addCheckingPath(path);
		}
	}

	/**
	 * Clears the checking.
	 */
	public void clearChecking() {
		this.checkedPathsSet.clear();
		fireValueChanged(new TreeCheckingEvent(new TreePath(model.getRoot())));
	}

	/**
	 * @return Returns the paths that are in the checking.
	 */
	public TreePath[] getCheckingPaths() {
		return checkedPathsSet.toArray(new TreePath[checkedPathsSet.size()]);
	}

	/**
	 * @return Returns the paths that are in the checking set and are the
	 *         (upper) roots of checked trees.
	 */
	public TreePath[] getCheckingRoots() {
		Vector<TreePath> roots = getCheckingRoots(new TreePath(this.model
				.getRoot()));
		return roots.toArray(new TreePath[] {});
	}

	/**
	 * @param path
	 * @return
	 */
	private Vector<TreePath> getCheckingRoots(TreePath path) {
		Vector<TreePath> roots = new Vector<TreePath>();
		if (isPathChecked(path)) {
			roots.add(path);
		}
		return roots;
	}

	/**
	 * @return The CheckingMode.
	 */
	public CheckingMode getCheckingMode() {
		if (this.checkingMode instanceof SimpleTreeCheckingMode) {
			return CheckingMode.SIMPLE;
		}
		if (this.checkingMode instanceof PropagateTreeCheckingMode) {
			return CheckingMode.PROPAGATE;
		}
		if (this.checkingMode instanceof PropagatePreservingCheckTreeCheckingMode) {
			return CheckingMode.PROPAGATE_PRESERVING_CHECK;
		}
		if (this.checkingMode instanceof PropagatePreservingUncheckTreeCheckingMode) {
			return CheckingMode.PROPAGATE_PRESERVING_UNCHECK;
		}
		return null;
	}

	/**
	 * Sets the specified checking mode. The consistence of the existing
	 * checking is not enforced nor controlled.
	 */
	public void setCheckingMode(CheckingMode mode) {
		/*
		 * CheckingMode implements togglePath method. (Strategy Pattern was
		 * used).
		 */
		switch (mode) {
		case SIMPLE:
			this.checkingMode = new SimpleTreeCheckingMode(this);
			break;
		case PROPAGATE:
			this.checkingMode = new PropagateTreeCheckingMode(this);
			break;
		case PROPAGATE_PRESERVING_CHECK:
			this.checkingMode = new PropagatePreservingCheckTreeCheckingMode(
					this);
			break;
		case PROPAGATE_PRESERVING_UNCHECK:
			this.checkingMode = new PropagatePreservingUncheckTreeCheckingMode(
					this);
			break;
		default:
			break;
		}
	}

	/**
	 * Sets the specified checking mode. The consistence of the existing
	 * checking is not enforced nor controlled.
	 */
	public void setCheckingMode(TreeCheckingMode mode) {
		this.checkingMode = mode;
	}

	/**
	 * Adds the paths to the checked paths set
	 * 
	 * @param paths
	 *            the paths to be added.
	 */
	public void addCheckingPaths(TreePath[] paths) {
		for (TreePath path : paths) {
			addCheckingPath(path);
		}
	}

	/**
	 * Adds a path to the checked paths set
	 * 
	 * @param path
	 *            the path to be added.
	 */
	public void addCheckingPath(TreePath path) {
		this.checkingMode.checkPath(path);
		TreeCheckingEvent event = new TreeCheckingEvent(path);
		fireValueChanged(event);
	}

	/**
	 * Removes a path from the checked paths set
	 * 
	 * @param path
	 *            the path to be removed
	 */
	public void removeCheckingPath(TreePath path) {
		this.checkingMode.uncheckPath(path);
		TreeCheckingEvent event = new TreeCheckingEvent(path);
		fireValueChanged(event);
	}

	/**
	 * Removes the paths from the checked paths set
	 * 
	 * @param paths
	 *            the paths to be removed
	 */
	public void removeCheckingPaths(TreePath[] paths) {
		for (TreePath path : paths) {
			removeCheckingPath(path);
		}
	}

	/**
	 * Notifies all listeners that are registered for tree selection events on
	 * this object.
	 * 
	 * @see #addTreeCheckingListener
	 * @see EventListenerList
	 */
	protected void fireValueChanged(TreeCheckingEvent e) {
		// Guaranteed to return a non-null array
		Object[] listeners = this.listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == TreeCheckingListener.class) {
				((TreeCheckingListener) listeners[i + 1]).valueChanged(e);
			}
		}
	}

	/**
	 * Adds x to the list of listeners that are notified each time the set of
	 * checking TreePaths changes.
	 * 
	 * @param x
	 *            the new listener to be added
	 */
	public void addTreeCheckingListener(TreeCheckingListener x) {
		this.listenerList.add(TreeCheckingListener.class, x);
	}

	/**
	 * Removes x from the list of listeners that are notified each time the set
	 * of checking TreePaths changes.
	 * 
	 * @param x
	 *            the listener to remove
	 */
	public void removeTreeCheckingListener(TreeCheckingListener x) {
		this.listenerList.remove(TreeCheckingListener.class, x);
	}

	public enum ChildrenChecking {
		ALL_CHECKED, HALF_CHECKED, ALL_UNCHECKED, NO_CHILDREN
	}

	public ChildrenChecking getChildrenChecking(TreePath path) {
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		boolean someChecked = false;
		boolean someUnchecked = false;
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			// not greyed
			if (isPathChecked(childPath)) {
				if (someUnchecked) {
					return ChildrenChecking.HALF_CHECKED;
				}
				someChecked = true;
			} else {
				if (someChecked) {
					return ChildrenChecking.HALF_CHECKED;
				}
				someUnchecked = true;
			}
		}
		if (someChecked) {
			return ChildrenChecking.ALL_CHECKED;
		}
		if (someUnchecked) {
			return ChildrenChecking.ALL_UNCHECKED;
		}
		return ChildrenChecking.NO_CHILDREN;
	}

	/**
	 * Note: The checking and the greyness of children MUST be consistent to
	 * work properly.
	 * 
	 * @return true if exists an unchecked node in the subtree of path.
	 * @param path
	 *            the root of the subtree to be checked.
	 */
	public boolean pathHasUncheckedChildren(TreePath path) {
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			if (!isPathChecked(childPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true if exists a checked node in the subtree of path.
	 * @param path
	 *            the root of the subtree to be checked.
	 */
	public boolean pathHasCheckedChildren(TreePath path) {
		return pathHasChildrenWithValue(path, true);
	}

	/**
	 * @return true if exists a node with checked status value in the subtree of
	 *         path.
	 * @param path
	 *            the root of the subtree to be searched.
	 * @param value
	 *            the value to be found.
	 */
	protected boolean pathHasChildrenWithValue(TreePath path, boolean value) {
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			if (isPathChecked(childPath) == value) {
				return true;
			}
		}
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			TreePath childPath = path.pathByAddingChild(this.model.getChild(
					node, childIndex));
			if (pathHasChildrenWithValue(childPath, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true if exists a child of node with a value different from
	 *         itself.
	 * @param path
	 *            the root path of the tree to be checked.
	 */
	public boolean hasDifferentChildren(TreePath path) {
		return pathHasChildrenWithValue(path, !isPathChecked(path));
	}

	/**
	 * Return the paths that are children of path, using methods of TreeModel.
	 * Nodes don't have to be of type TreeNode.
	 * 
	 * @param path
	 *            the parent path
	 * @return the array of children path
	 */
	protected TreePath[] getChildrenPath(TreePath path) {
		Object node = path.getLastPathComponent();
		int childrenNumber = this.model.getChildCount(node);
		TreePath[] childrenPath = new TreePath[childrenNumber];
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			childrenPath[childIndex] = path.pathByAddingChild(this.model
					.getChild(node, childIndex));
		}
		return childrenPath;
	}

	public TreeModel getTreeModel() {
		return this.model;
	}

	/**
	 * Sets the specified tree model. The current cheking is cleared.
	 */
	public void setTreeModel(TreeModel newModel) {
		TreeModel oldModel = this.model;
		if (oldModel != null) {
			oldModel.removeTreeModelListener(this.propagateCheckingListener);
		}
		this.model = newModel;
		if (newModel != null) {
			newModel.addTreeModelListener(this.propagateCheckingListener);
		}
		clearChecking();
	}

	/**
	 * Return a string that describes the tree model including the values of
	 * checking, enabling, greying.
	 */
	@Override
	public String toString() {
		return toString(new TreePath(this.model.getRoot()));
	}

	/**
	 * Convenience method for getting a string that describes the tree starting
	 * at path.
	 * 
	 * @param path
	 *            the treepath root of the tree
	 */
	private String toString(TreePath path) {
		String checkString = "n";
		String greyString = "n";
		String enableString = "n";
		if (isPathChecked(path)) {
			checkString = "y";
		}
		String description = "Path checked: " + checkString + " greyed: "
				+ greyString + " enabled: " + enableString + " Name: "
				+ path.toString() + "\n";
		for (TreePath childPath : getChildrenPath(path)) {
			description += toString(childPath);
		}
		return description;
	}

}