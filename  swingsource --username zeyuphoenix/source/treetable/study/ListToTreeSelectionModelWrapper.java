package study;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;

/**
 * ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel to listen
 * for changes in the ListSelectionModel it maintains. Once a change in the
 * ListSelectionModel happens, the paths are updated in the
 * DefaultTreeSelectionModel.
 */
class ListToTreeSelectionModelWrapper extends DefaultTreeSelectionModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Set to true when we are updating the ListSelectionModel. */
	protected boolean updatingListSelectionModel;

	private JTreeTable treeTable = null;

	public ListToTreeSelectionModelWrapper(JTreeTable treeTable) {
		super();
		getListSelectionModel().addListSelectionListener(
				createListSelectionListener());
		this.treeTable = treeTable;
	}

	/**
	 * Returns the list selection model. ListToTreeSelectionModelWrapper listens
	 * for changes to this model and updates the selected paths accordingly.
	 */
	ListSelectionModel getListSelectionModel() {
		return listSelectionModel;
	}

	/**
	 * This is overridden to set <code>updatingListSelectionModel</code> and
	 * message super. This is the only place DefaultTreeSelectionModel alters
	 * the ListSelectionModel.
	 */
	public void resetRowSelection() {
		if (!updatingListSelectionModel) {
			updatingListSelectionModel = true;
			try {
				super.resetRowSelection();
			} finally {
				updatingListSelectionModel = false;
			}
		}
	}

	/**
	 * Creates and returns an instance of ListSelectionHandler.
	 */
	protected ListSelectionListener createListSelectionListener() {
		return new ListSelectionHandler();
	}

	/**
	 * If <code>updatingListSelectionModel</code> is false, this will reset the
	 * selected paths from the selected rows in the list selection model.
	 */
	protected void updateSelectedPathsFromSelectedRows() {
		if (!updatingListSelectionModel) {
			updatingListSelectionModel = true;
			try {
				// This is way expensive, ListSelectionModel needs an
				// enumerator for iterating.
				int min = listSelectionModel.getMinSelectionIndex();
				int max = listSelectionModel.getMaxSelectionIndex();

				clearSelection();
				if (min != -1 && max != -1) {
					for (int counter = min; counter <= max; counter++) {
						if (listSelectionModel.isSelectedIndex(counter)) {
							TreePath selPath = treeTable.tree
									.getPathForRow(counter);

							if (selPath != null) {
								addSelectionPath(selPath);
							}
						}
					}
				}
			} finally {
				updatingListSelectionModel = false;
			}
		}
	}

	/**
	 * Class responsible for calling updateSelectedPathsFromSelectedRows when
	 * the selection of the list changse.
	 */
	class ListSelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			updateSelectedPathsFromSelectedRows();
		}
	}
}
