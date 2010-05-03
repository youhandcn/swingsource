package tree;

import java.awt.Component;
import java.util.Hashtable;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * the tree renderer that I rewrite it.
 */
public class MyTreeRenderer extends DefaultTreeCellRenderer {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Returns a new instance of DefaultTreeCellRenderer.
     */
    public MyTreeRenderer() {
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
            setDisabledIcon(getLeafIcon());
            setDisabledIcon(getOpenIcon());
            setDisabledIcon(getClosedIcon());
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
}
