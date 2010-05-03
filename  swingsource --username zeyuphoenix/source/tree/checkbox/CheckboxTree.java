package checkbox;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * A tree whose nodes may be checked
 */
public class CheckboxTree extends JTree {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	private TreeCheckingModel checkingModel;

	private class NodeCheckListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			// we use mousePressed instead of mouseClicked for performance
			int x = e.getX();
			int y = e.getY();
			int row = getRowForLocation(x, y);
			if (row == -1) {
				// click outside any node
				return;
			}
			Rectangle rect = getRowBounds(row);
			if (rect == null) {
				// clic on an invalid node
				return;
			}
			if (((DefaultCheckboxTreeCellRenderer) getCellRenderer())
					.isOnHotspot(x - rect.x, y - rect.y)) {
				getCheckingModel().toggleCheckingPath(getPathForRow(row));
			}
		}
	}

	/**
	 * For GUI builders. It returns a CheckboxTree with a default tree model to
	 * show something interesting. Creates a CheckboxTree with visible handles,
	 * a default CheckboxTreeCellRenderer and a default TreeCheckingModel.
	 */
	public CheckboxTree() {
		super(getDefaultTreeModel());
		initialize();
	}

	/**
	 * Creates a CheckboxTree with visible handles, a default
	 * CheckboxTreeCellRenderer and a default TreeCheckingModel. The tree is
	 * created using the specified data model. Mouse clicks are validated
	 * against the cell renderer (via isOnCheckBox) and, eventually, passed on
	 * to the checking model.
	 * 
	 * @param root
	 *            the root of the tree
	 */
	public CheckboxTree(TreeNode root) {
		super(root);
		initialize();
	}

	/**
	 * Creates a CheckboxTree with visible handles, a default
	 * CheckboxTreeCellRenderer and a default TreeCheckingModel. The tree is
	 * created using the specified data model. Mouse clicks are validated
	 * against the cell renderer (via isOnCheckBox) and, eventually, passed on
	 * to the checking model.
	 */
	public CheckboxTree(TreeModel treemodel) {
		super(treemodel);
		initialize();
	}

	/**
	 * Convenience initialization method.
	 */
	private void initialize() {
		setCheckingModel(new DefaultTreeCheckingModel(this.treeModel));
		setCellRenderer(new DefaultCheckboxTreeCellRenderer());
		addMouseListener(new NodeCheckListener());
		this.selectionModel
				.setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		setShowsRootHandles(true);
		putClientProperty("JTree.lineStyle", "Angled");// for Metal L&F
	}

	/**
	 * Sets the TreeModel and links it to the existing checkingModel.
	 */
	@Override
	public void setModel(TreeModel newModel) {
		super.setModel(newModel);
		if (this.checkingModel != null) {
			this.checkingModel.setTreeModel(newModel);
		}
	}

	/**
	 * @return Returns the TreeCheckingModel.
	 */
	public TreeCheckingModel getCheckingModel() {
		return this.checkingModel;
	}

	/**
	 * Set the checking model of this CheckboxTree.
	 * 
	 * @param newCheckingModel
	 *            The new TreeCheckingModel.
	 */
	public void setCheckingModel(TreeCheckingModel newCheckingModel) {
		/*
		 * we must unlink the old TreeCheckingModel from the model of this tree
		 * and link the new one to it.
		 */
		TreeCheckingModel oldCheckingModel = this.checkingModel;
		if (oldCheckingModel != null) {
			// null the model in the old TreeCheckingModel to avoid dangling
			// pointers
			oldCheckingModel.setTreeModel(null);
		}
		// TODO: check newCheckingModel for == null and optionally substitute
		// with EmptyCheckingModel...
		this.checkingModel = newCheckingModel;
		if (newCheckingModel != null) {
			newCheckingModel.setTreeModel(getModel());
			// add a treeCheckingListener to repaint upon checking
			// modifications
			newCheckingModel
					.addTreeCheckingListener(new TreeCheckingListener() {
						public void valueChanged(TreeCheckingEvent e) {
							repaint();
						}
					});
		}
	}

	/**
	 * Return paths that are in the checking.
	 */
	public TreePath[] getCheckingPaths() {
		return getCheckingModel().getCheckingPaths();
	}

	/**
	 * @return Returns the paths that are in the checking set and are the
	 *         (upper) roots of checked trees.
	 */
	public TreePath[] getCheckingRoots() {
		return getCheckingModel().getCheckingRoots();
	}

	/**
	 * Clears the checking.
	 */
	public void clearChecking() {
		getCheckingModel().clearChecking();
	}

	/**
	 * Add paths in the checking.
	 */
	public void addCheckingPaths(TreePath[] paths) {
		getCheckingModel().addCheckingPaths(paths);
	}

	/**
	 * Add a path in the checking.
	 */
	public void addCheckingPath(TreePath path) {
		getCheckingModel().addCheckingPath(path);
	}

	/**
	 * Set path in the checking.
	 */
	public void setCheckingPath(TreePath path) {
		getCheckingModel().setCheckingPath(path);
	}

	/**
	 * Set paths that are in the checking.
	 */
	public void setCheckingPaths(TreePath[] paths) {
		getCheckingModel().setCheckingPaths(paths);
	}

	/**
	 * Adds a listener for <code>TreeChecking</code> events.
	 * 
	 * @param tsl
	 *            the <code>TreeCheckingListener</code> that will be notified
	 *            when a node is checked
	 */
	public void addTreeCheckingListener(TreeCheckingListener tsl) {
		this.checkingModel.addTreeCheckingListener(tsl);
	}

	/**
	 * Removes a <code>TreeChecking</code> listener.
	 * 
	 * @param tsl
	 *            the <code>TreeChckingListener</code> to remove
	 */
	public void removeTreeCheckingListener(TreeCheckingListener tsl) {
		this.checkingModel.removeTreeCheckingListener(tsl);
	}

	/**
	 * Expand completely a tree
	 */
	public void expandAll() {
		expandSubTree(getPathForRow(0));
	}

	private void expandSubTree(TreePath path) {
		expandPath(path);
		Object node = path.getLastPathComponent();
		int childrenNumber = getModel().getChildCount(node);
		TreePath[] childrenPath = new TreePath[childrenNumber];
		for (int childIndex = 0; childIndex < childrenNumber; childIndex++) {
			childrenPath[childIndex] = path.pathByAddingChild(getModel()
					.getChild(node, childIndex));
			expandSubTree(childrenPath[childIndex]);
		}
	}

	/**
	 * @return a string representation of the tree, including the checking,
	 *         enabling and greying sets.
	 */
	@Override
	public String toString() {
		String retVal = super.toString();
		TreeCheckingModel tcm = getCheckingModel();
		if (tcm != null) {
			return retVal + "\n" + tcm.toString();
		}
		return retVal;
	}
}