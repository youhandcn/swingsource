package checkbox;

import javax.swing.tree.TreePath;

/**
 * The model for checking/unchecking the nodes of a CheckboxTree.
 */
public abstract class TreeCheckingMode {

	protected DefaultTreeCheckingModel model;

	public TreeCheckingMode(DefaultTreeCheckingModel model) {
		this.model = model;
	}

	/**
	 * Checks the specified path and propagates the checking according to the
	 * strategy
	 * 
	 * @param path
	 *            the path to be added.
	 */

	public abstract void checkPath(TreePath path);

	/**
	 * Unchecks the specified path and propagates the checking according to the
	 * strategy
	 * 
	 * @param path
	 *            the path to be removed.
	 */
	public abstract void uncheckPath(TreePath path);

	/**
	 * Update the check of the given path after the insertion of some of its
	 * children, according to the strategy
	 * 
	 * @param path
	 */
	public abstract void updateCheckAfterChildrenInserted(TreePath path);

	/**
	 * Update the check of the given path after the removal of some of its
	 * children, according to the strategy
	 * 
	 * @param path
	 */
	public abstract void updateCheckAfterChildrenRemoved(TreePath path);

	/**
	 * Update the check of the given path after the structure change, according
	 * to the strategy
	 * 
	 * @param path
	 */
	public abstract void updateCheckAfterStructureChanged(TreePath path);

}
