package simplecheckbox;

import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 * the tree node that I rewrite it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTreeNode.java <br>
 *         2008 2008/03/25 17:38:58 <br>
 */
public class MyTreeNode extends DefaultMutableTreeNode {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;
    /** is select or not. */
    private boolean isSelected = false;
    /** select model. */
    private int selectionMode = 0;

    /**
     * Creates a tree node that has no parent and no children, but which allows
     * children.
     */
    public MyTreeNode() {
        this(null, true, false, true, true, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     */
    public MyTreeNode(Object userObject) {
        this(userObject, true, false, true, true, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     * @param allowsChildren
     *            if true, the node is allowed to have child nodes -- otherwise,
     *            it is always a leaf node
     */
    public MyTreeNode(Object userObject, boolean allowsChildren) {
        this(userObject, allowsChildren, false, true, true, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     * @param allowsChildren
     *            if true, the node is allowed to have child nodes -- otherwise,
     *            it is always a leaf node
     * @param isSelected
     *            is select or not.
     */
    public MyTreeNode(Object userObject, boolean allowsChildren,
            boolean isSelected) {
        this(userObject, allowsChildren, isSelected, true, true, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     * @param allowsChildren
     *            if true, the node is allowed to have child nodes -- otherwise,
     *            it is always a leaf node
     * @param isSelected
     *            is select or not.
     * @param enabled
     *            is enable.
     */
    public MyTreeNode(Object userObject, boolean allowsChildren,
            boolean isSelected, boolean enabled) {
        this(userObject, allowsChildren, isSelected, enabled, true, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     * @param allowsChildren
     *            if true, the node is allowed to have child nodes -- otherwise,
     *            it is always a leaf node
     * @param isSelected
     *            is select or not.
     * @param enabled
     *            is enable.
     * @param isVisible
     *            is visible or not.
     */
    public MyTreeNode(Object userObject, boolean allowsChildren,
            boolean isSelected, boolean enabled, boolean isVisible) {
        this(userObject, allowsChildren, isSelected, enabled, enabled, null);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with the
     * specified user object, and that allows children only if specified.
     * 
     * @param userObject
     *            an Object provided by the user that constitutes the node's
     *            data
     * @param allowsChildren
     *            if true, the node is allowed to have child nodes -- otherwise,
     *            it is always a leaf node
     * @param isSelected
     *            is select or not.
     * @param enabled
     *            is enable.
     * @param isVisible
     *            is visible or not.
     * @param icon
     *            it's icon.
     */
    public MyTreeNode(Object userObject, boolean allowsChildren,
            boolean isSelected, boolean enabled, boolean isVisible, Icon icon) {
        super(userObject, allowsChildren);
        this.isSelected = isSelected;
    }

    /**
     * set the node select.
     * 
     * @param isSelected
     *            node select or not.
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        if ((selectionMode == TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION)
                && (children != null)) {
            Enumeration<?> enumTemp = children.elements();
            while (enumTemp.hasMoreElements()) {
                MyTreeNode node = (MyTreeNode) enumTemp.nextElement();
                node.setSelected(isSelected);
            }
        }
    }

    /**
     * get the node is select.
     * 
     * @return is select.
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * change "isSelected" by CellEditor.
     * 
     * @param obj
     *            cell editor.
     */
    public void setUserObject(Object obj) {
        if (obj instanceof Boolean) {
            setSelected(((Boolean) obj).booleanValue());
        } else {
            super.setUserObject(obj);
        }
    }
}
