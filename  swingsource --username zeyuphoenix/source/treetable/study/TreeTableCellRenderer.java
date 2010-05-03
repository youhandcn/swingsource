package study;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;

/**
 * A TreeCellRenderer that displays a JTree.
 */
public class TreeTableCellRenderer extends JTree implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Last table/tree row asked to renderer. */
	protected int visibleRow;
	/**
	 * Border to draw around the tree, if this is non-null, it will be painted.
	 */
	protected Border highlightBorder;

	private JTreeTable treeTable = null;

	public TreeTableCellRenderer(TreeModel model, JTreeTable treeTable) {
		super(model);
		this.treeTable = treeTable;
	}

	/**
	 * updateUI is overridden to set the colors of the Tree's renderer to match
	 * that of the table.
	 */
	@Override
	public void updateUI() {
		super.updateUI();
		// Make the tree's cell renderer use the table's cell selection
		// colors.
		TreeCellRenderer tcr = getCellRenderer();
		if (tcr instanceof DefaultTreeCellRenderer) {
			DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
			// For 1.1 uncomment this, 1.2 has a bug that will cause an
			// exception to be thrown if the border selection color is
			// null.
			// dtcr.setBorderSelectionColor(null);
			dtcr.setTextSelectionColor(UIManager
					.getColor("Table.selectionForeground"));
			dtcr.setBackgroundSelectionColor(UIManager
					.getColor("Table.selectionBackground"));
		}
	}

	/**
	 * Sets the row height of the tree, and forwards the row height to the
	 * table.
	 */
	@Override
	public void setRowHeight(int rowHeight) {
		if (rowHeight > 0) {
			super.setRowHeight(rowHeight);
			if (treeTable != null && treeTable.getRowHeight() != rowHeight) {
				treeTable.setRowHeight(getRowHeight());
			}
		}
	}

	/**
	 * This is overridden to set the height to match that of the JTable.
	 */
	@Override
	public void setBounds(int x, int y, int w, int h) {
		super.setBounds(x, 0, w, treeTable.getHeight());
	}

	/**
	 * Sublcassed to translate the graphics such that the last visible row will
	 * be drawn at 0,0.
	 */
	@Override
	public void paint(Graphics g) {
		g.translate(0, -visibleRow * getRowHeight());
		super.paint(g);
		// Draw the Table border if we have focus.
		if (highlightBorder != null) {
			highlightBorder.paintBorder(this, g, 0,
					visibleRow * getRowHeight(), getWidth(), getRowHeight());
		}
	}

	/**
	 * TreeCellRenderer method. Overridden to update the visible row.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Color background;
		Color foreground;

		if (isSelected) {
			background = table.getSelectionBackground();
			foreground = table.getSelectionForeground();
		} else {
			background = table.getBackground();
			foreground = table.getForeground();
		}
		highlightBorder = null;
		if (treeTable.realEditingRow() == row
				&& treeTable.getEditingColumn() == column) {
			background = UIManager.getColor("Table.focusCellBackground");
			foreground = UIManager.getColor("Table.focusCellForeground");
		} else if (hasFocus) {
			highlightBorder = UIManager
					.getBorder("Table.focusCellHighlightBorder");
			if (treeTable.isCellEditable(row, column)) {
				background = UIManager.getColor("Table.focusCellBackground");
				foreground = UIManager.getColor("Table.focusCellForeground");
			}
		}

		visibleRow = row;
		setBackground(background);

		TreeCellRenderer tcr = getCellRenderer();
		if (tcr instanceof DefaultTreeCellRenderer) {
			DefaultTreeCellRenderer dtcr = ((DefaultTreeCellRenderer) tcr);
			if (isSelected) {
				dtcr.setTextSelectionColor(foreground);
				dtcr.setBackgroundSelectionColor(background);
			} else {
				dtcr.setTextNonSelectionColor(foreground);
				dtcr.setBackgroundNonSelectionColor(background);
			}
		}
		return this;
	}
}
