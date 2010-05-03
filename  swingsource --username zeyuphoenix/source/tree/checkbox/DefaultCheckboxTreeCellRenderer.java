package checkbox;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

/**
 */
public class DefaultCheckboxTreeCellRenderer extends JPanel implements
		TreeCellRenderer {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	protected JCheckBox checkBox = new JCheckBox();

	protected DefaultTreeCellRenderer label = new DefaultTreeCellRenderer();

	public DefaultCheckboxTreeCellRenderer() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		add(this.checkBox);
		add(this.label);
		this.checkBox.setBackground(UIManager.getColor("Tree.textBackground"));
		this.setBackground(UIManager.getColor("Tree.textBackground"));
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d_check = this.checkBox.getPreferredSize();
		Dimension d_label = this.label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
						: d_check.height));
	}

	/**
	 * Decorates this renderer based on the passed in components.
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object object,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		/*
		 * most of the rendering is delegated to the wrapped
		 * DefaultTreeCellRenderer, the rest depends on the TreeCheckingModel
		 */
		this.label.getTreeCellRendererComponent(tree, object, selected,
				expanded, leaf, row, hasFocus);
		if (tree instanceof CheckboxTree) {
			TreeCheckingModel checkingModel = ((CheckboxTree) tree)
					.getCheckingModel();
			TreePath path = tree.getPathForRow(row);
			boolean checked = checkingModel.isPathChecked(path);
			this.checkBox.setSelected(checked);
		}
		return this;
	}

	/**
	 * Checks if the (x,y) coordinates are on the Checkbox.
	 * 
	 * @return boolean
	 * @param x
	 * @param y
	 */
	public boolean isOnHotspot(int x, int y) {
		return (this.checkBox.getBounds().contains(x, y));
	}
}