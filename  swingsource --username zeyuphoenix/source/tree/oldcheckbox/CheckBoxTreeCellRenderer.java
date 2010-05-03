package oldcheckbox;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

public class CheckBoxTreeCellRenderer extends JCheckBox implements
		TreeCellRenderer {

	private static final long serialVersionUID = -6432020851855339311L;

	public CheckBoxTreeCellRenderer() {
		setOpaque(false);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// get tree node
		CheckBoxTreeNode node = ((CheckBoxTreeNode) value);
		// set check box text
		setText(node.toString());

		// when node check, box will check
		if (node.isChecked()) {
			setSelected(true);
			setForeground(Color.BLUE);
		} else {
			setSelected(false);
			setForeground(tree.getForeground());
		}
		return this;
	}
}
