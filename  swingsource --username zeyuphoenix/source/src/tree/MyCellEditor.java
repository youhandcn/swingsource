package tree;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;

/**
 * A <code>TreeCellEditor</code>. You need to supply an instance of
 * <code>DefaultTreeCellRenderer</code> so that the icons can be obtained.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyCellEditor.java <br>
 *         2008 2008/03/26 13:34:34 <br>
 */
public class MyCellEditor extends DefaultTreeCellEditor {
	/**
	 * Constructs a <code>DefaultTreeCellEditor</code> object for a
	 * <code>JTree</code> using the specified renderer and the specified
	 * editor. (Use this constructor for specialized editing.)
	 * 
	 * @param tree��
	 *            a <code>JTree</code> object
	 * @param renderer
	 *            a <code>DefaultTreeCellRenderer</code> object
	 */
	public MyCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		this(tree, renderer, null);
	}

	/**
	 * Constructs a <code>DefaultTreeCellEditor</code> object for a
	 * <code>JTree</code> using the specified renderer and the specified
	 * editor. (Use this constructor for specialized editing.)
	 * 
	 * @param tree
	 *            a <code>JTree</code> object
	 * @param renderer
	 *            a <code>DefaultTreeCellRenderer</code> object
	 * @param editor
	 *            a <code>TreeCellEditor</code> object
	 */
	public MyCellEditor(final JTree tree,
			final DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
		super(tree, renderer, editor);
		editingContainer = new MyEditorContainer();
	}

	/**
	 * Configures the editor. Passed onto the <code>realEditor</code>.
	 */
	public Component getTreeCellEditorComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row) {
		Component c = super.getTreeCellEditorComponent(tree, value, isSelected,
				expanded, leaf, row);
		((MyEditorContainer) editingContainer).setLocalCopy(tree, lastPath,
				offset, editingComponent);
		return c;
	}

	/**
	 * Container responsible for placing the <code>editingComponent</code>.
	 */
	private class MyEditorContainer extends
			DefaultTreeCellEditor.EditorContainer {
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * father tree.
		 */
		private JTree tree = null;
		/**
		 * offset path.
		 */
		private TreePath lastPath = null;
		/**
		 * offset.
		 */
		private int offset = 0;
		private Component editingComponent = null;

		/**
		 * Lays out this <code>Container</code>. If editing, the editor will
		 * be placed at <code>offset</code> in the x direction and 0 for y.
		 */
		public void doLayout() {
			if (editingComponent != null) {
				Dimension cSize = getSize();
				Dimension eSize = editingComponent.getPreferredSize();
				int n = lastPath.getPathCount();
				Rectangle r = new Rectangle();
				r = tree.getBounds(r);
				eSize.width = r.width - (offset * n);
				editingComponent.setSize(eSize);
				editingComponent.setLocation(offset, 0);
				editingComponent
						.setBounds(offset, 0, eSize.width, cSize.height);
				setSize(new Dimension(eSize.width + offset, cSize.height));
			}
		}

		/**
		 * set the tree copy one to other.
		 * 
		 * @param tree
		 *            tree
		 * @param lastPath
		 *            tree last path.
		 * @param offset
		 *            offset.
		 * @param editingComponent
		 *            component.
		 */
		public void setLocalCopy(JTree tree, TreePath lastPath, int offset,
				Component editingComponent) {
			this.tree = tree;
			this.lastPath = lastPath;
			this.offset = offset;
			this.editingComponent = editingComponent;
		}
	}
}
