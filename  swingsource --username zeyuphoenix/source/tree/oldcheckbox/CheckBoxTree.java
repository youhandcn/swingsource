package oldcheckbox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class CheckBoxTree extends JTree {

	private static final long serialVersionUID = -217950037507321241L;

	public CheckBoxTree(TreeModel newModel) {
		super(newModel);
        setCellRenderer(new CheckBoxTreeCellRenderer());
		addCheckingListener();
	}

	private void addCheckingListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int row = getRowForLocation(e.getX(), e.getY());
				TreePath treePath = getPathForRow(row);
				if (treePath == null) {
					return;
				}

				CheckBoxTreeNode node = ((CheckBoxTreeNode) treePath
						.getLastPathComponent());
				// if check , will uncheck.
				boolean checking = !node.isChecked();
				node.setChecked(checking);

				// repaint
				repaint();
			}
		});
	}
}
