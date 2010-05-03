package checkbox;

import javax.swing.tree.TreePath;

/**
 * SimpleTreeCheckingMode defines a TreeCheckingMode without recursion. In this
 * simple mode the check state always changes only the current node: no
 * recursion.
 */
public class SimpleTreeCheckingMode extends TreeCheckingMode {

	SimpleTreeCheckingMode(DefaultTreeCheckingModel model) {
		super(model);
	}

	@Override
	public void checkPath(TreePath path) {
		this.model.addToCheckedPathsSet(path);
	}

	@Override
	public void uncheckPath(TreePath path) {
		this.model.removeFromCheckedPathsSet(path);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
	 * updateCheckAfterChildrenInserted(javax.swing.tree.TreePath)
	 */
	@Override
	public void updateCheckAfterChildrenInserted(TreePath parent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
	 * updateCheckAfterChildrenRemoved(javax.swing.tree.TreePath)
	 */
	@Override
	public void updateCheckAfterChildrenRemoved(TreePath parent) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeit.cnr.imaa.essi.lablib.gui.checkboxtree.TreeCheckingMode#
	 * updateCheckAfterStructureChanged(javax.swing.tree.TreePath)
	 */
	@Override
	public void updateCheckAfterStructureChanged(TreePath parent) {
	}

}