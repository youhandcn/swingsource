package jtree;

import java.awt.Component;
import java.util.Hashtable;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * the tree renderer that I rewrite it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTreeRenderer.java <br>
 *         2008 2008/03/25 17:41:12 <br>
 */
public class MyTreeRenderer extends DefaultTreeCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** leaf Icon. */
	private Icon myLeafIcon = null;
	/** open Icon. */
	private Icon myOpenIcon = null;
	/** closed Icon. */
	private Icon myClosedIcon = null;

	/**
	 * Returns a new instance of DefaultTreeCellRenderer.
	 */
	public MyTreeRenderer() {
		this(new MyNodeIcon(UIManager.getIcon("Tree.leafIcon")),
				new MyNodeIcon(UIManager.getIcon("Tree.openIcon")),
				new MyNodeIcon(UIManager.getIcon("Tree.closedIcon")));
	}

	/**
	 * Returns a new instance of DefaultTreeCellRenderer.
	 * 
	 * @param leafIcon
	 *            leaf Icon.
	 * @param openIcon
	 *            open Icon.
	 * @param closedIcon
	 *            closed Icon.
	 */
	public MyTreeRenderer(Icon leafIcon, Icon openIcon, Icon closedIcon) {
		setMyLeafIcon(leafIcon);
		setMyOpenIcon(openIcon);
		setMyClosedIcon(closedIcon);
	}

	/**
	 * Configures the renderer based on the passed in components. The value is
	 * set from messaging the tree with <code>convertValueToText</code>,
	 * which ultimately invokes <code>toString</code> on <code>value</code>.
	 * The foreground color is set based on the selection and the icon is set
	 * based on the <code>leaf</code> and <code>expanded</code> parameters.
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {

		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		String stringValue = tree.convertValueToText(value, sel, expanded,
				leaf, row, hasFocus);
		setText(stringValue);
		if (sel) {
			setForeground(getTextSelectionColor());
		} else {
			setForeground(getTextNonSelectionColor());
		}
		boolean treeIsEnabled = tree.isEnabled();
		boolean nodeIsEnabled = ((MyTreeNode) value).isEnabled();
		boolean isEnabled = (treeIsEnabled && nodeIsEnabled);
		setEnabled(isEnabled);

		if (isEnabled) {
			selected = sel;
			if (leaf) {
				setIcon(getLeafIcon());
			} else if (expanded) {
				setIcon(getOpenIcon());
			} else {
				setIcon(getClosedIcon());
			}
		} else {
			selected = false;
			if (leaf) {
				if (nodeIsEnabled) {
					setDisabledIcon(getLeafIcon());
				} else {
					setDisabledIcon(getMyLeafIcon());
				}
			} else if (expanded) {
				if (nodeIsEnabled) {
					setDisabledIcon(getOpenIcon());
				} else {
					setDisabledIcon(getMyOpenIcon());
				}
			} else {
				if (nodeIsEnabled) {
					setDisabledIcon(getClosedIcon());
				} else {
					setDisabledIcon(getMyClosedIcon());
				}
			}
		}
		Icon icon = ((MyTreeNode) value).getIcon();

		if (icon == null) {
			Hashtable<?, ?> icons = (Hashtable<?, ?>) tree
					.getClientProperty("JTree.icons");
			String name = ((MyTreeNode) value).getIconName();
			if ((icons != null) && (name != null)) {
				icon = (Icon) icons.get(name);
				if (icon != null) {
					setIcon(icon);
				}
			}
		} else {
			setIcon(icon);
		}
		return this;
	}

	/**
	 * set leaf Icon.
	 * 
	 * @param icon
	 *            leaf Icon.
	 */
	public void setMyLeafIcon(Icon icon) {
		myLeafIcon = icon;
	}

	/**
	 * set open Icon.
	 * 
	 * @param icon
	 *            open Icon.
	 */
	public void setMyOpenIcon(Icon icon) {
		myOpenIcon = icon;
	}

	/**
	 * set closed Icon.
	 * 
	 * @param icon
	 *            closed Icon.
	 */
	public void setMyClosedIcon(Icon icon) {
		myClosedIcon = icon;
	}

	/**
	 * Returns the icon used to represent non-leaf nodes that are expanded.
	 */
	public Icon getMyOpenIcon() {
		return myOpenIcon;
	}

	/**
	 * Returns the icon used to represent non-leaf nodes that are not expanded.
	 */
	public Icon getMyClosedIcon() {
		return myClosedIcon;
	}

	/**
	 * Returns the icon used to represent leaf nodes.
	 */
	public Icon getMyLeafIcon() {
		return myLeafIcon;
	}
}
