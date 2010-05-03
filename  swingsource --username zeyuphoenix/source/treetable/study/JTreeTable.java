package study;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.EventObject;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LookAndFeel;

public class JTreeTable extends JTable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /** A subclass of JTree. */
    protected TreeTableCellRenderer tree;

    public JTreeTable(TreeTableModel treeTableModel) {
        super();

        // Creates the tree. It will be used as a renderer and editor.
        tree = new TreeTableCellRenderer(treeTableModel, this);

        // Installs a tableModel representing the visible rows in the tree.
        super.setModel(new TreeTableModelAdapter(treeTableModel, tree));

        // Forces the JTable and JTree to share their row selection models.
        ListToTreeSelectionModelWrapper selectionWrapper = new ListToTreeSelectionModelWrapper(
                this);
        tree.setSelectionModel(selectionWrapper);
        setSelectionModel(selectionWrapper.getListSelectionModel());

        // Installs the tree editor renderer and editor.
        setDefaultRenderer(TreeTableModel.class, tree);
        setDefaultEditor(TreeTableModel.class, new TreeTableCellEditor(this));

        // No grid.
        setShowGrid(false);

        // No intercell spacing
        setIntercellSpacing(new Dimension(0, 0));

        // And update the height of the trees row to match that of
        // the table.
        if (tree.getRowHeight() < 1) {
            // Metal looks better like this.
            setRowHeight(20);
        }
    }

    /**
     * Overridden to message super and forward the method to the tree. Since the
     * tree is not actually in the component hierarchy it will never receive
     * this unless we forward it in this manner.
     */
    @Override
    public void updateUI() {
        super.updateUI();
        if (tree != null) {
            tree.updateUI();
            // Do this so that the editor is referencing the current renderer
            // from the tree. The renderer can potentially change each time
            // laf changes.
            setDefaultEditor(TreeTableModel.class,
                    new TreeTableCellEditor(this));
        }
        // Use the tree's default foreground and background colors in the
        // table.
        LookAndFeel.installColorsAndFont(this, "Tree.background",
                "Tree.foreground", "Tree.font");
    }

    /**
     * Workaround for BasicTableUI anomaly. Make sure the UI never tries to
     * resize the editor. The UI currently uses different techniques to paint
     * the renderers and editors; overriding setBounds() below is not the right
     * thing to do for an editor. Returning -1 for the editing row in this case,
     * ensures the editor is never painted.
     */
    @Override
    public int getEditingRow() {
        return (getColumnClass(editingColumn) == TreeTableModel.class) ? -1
                : editingRow;
    }

    /**
     * Returns the actual row that is editing as <code>getEditingRow</code> will
     * always return -1.
     */
    public int realEditingRow() {
        return editingRow;
    }

    /**
     * This is overridden to invoke super's implementation, and then, if the
     * receiver is editing a Tree column, the editor's bounds is reset. The
     * reason we have to do this is because JTable doesn't think the table is
     * being edited, as <code>getEditingRow</code> returns -1, and therefore
     * doesn't automatically resize the editor for us.
     */
    @Override
    public void sizeColumnsToFit(int resizingColumn) {
        super.sizeColumnsToFit(resizingColumn);
        if (getEditingColumn() != -1
                && getColumnClass(editingColumn) == TreeTableModel.class) {
            Rectangle cellRect = getCellRect(realEditingRow(),
                    getEditingColumn(), false);
            Component component = getEditorComponent();
            component.setBounds(cellRect);
            component.validate();
        }
    }

    /**
     * Overridden to pass the new rowHeight to the tree.
     */
    @Override
    public void setRowHeight(int rowHeight) {
        super.setRowHeight(rowHeight);
        if (tree != null && tree.getRowHeight() != rowHeight) {
            tree.setRowHeight(getRowHeight());
        }
    }

    /**
     * Returns the tree that is being shared between the model.
     */
    public JTree getTree() {
        return tree;
    }

    /**
     * Overridden to invoke repaint for the particular location if the column
     * contains the tree. This is done as the tree editor does not fill the
     * bounds of the cell, we need the renderer to paint the tree in the
     * background, and then draw the editor over it.
     */
    @Override
    public boolean editCellAt(int row, int column, EventObject e) {
        boolean retValue = super.editCellAt(row, column, e);
        if (retValue && getColumnClass(column) == TreeTableModel.class) {
            repaint(getCellRect(row, column, false));
        }
        return retValue;
    }

    /**
     * Component used by TreeTableCellEditor. The only thing this does is to
     * override the <code>reshape</code> method, and to ALWAYS make the x
     * location be <code>offset</code>.
     */
    static class TreeTableTextField extends JTextField {
        /**
		 * 
		 */
        private static final long serialVersionUID = 1L;
        public int offset;

        @Override
        public void reshape(int x, int y, int w, int h) {
            int newX = Math.max(x, offset);
            super.setBounds(newX, y, w - (newX - x), h);
        }
    }
}
