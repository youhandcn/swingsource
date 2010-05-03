package study;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

/**
 * An editor that can be used to edit the tree column. This extends
 * DefaultCellEditor and uses a JTextField (actually, TreeTableTextField) to
 * perform the actual editing.
 */
public class TreeTableCellEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTreeTable treeTable = null;

	public TreeTableCellEditor(JTreeTable treeTable) {
		super(new JTreeTable.TreeTableTextField());
		this.treeTable = treeTable;
	}

	/**
	 * Overridden to determine an offset that tree would place the editor at.
	 * The offset is determined from the <code>getRowBounds</code> JTree method,
	 * and additionally from the icon DefaultTreeCellRenderer will use.
	 * <p>
	 * The offset is then set on the TreeTableTextField component created in the
	 * constructor, and returned.
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int r, int c) {
		Component component = super.getTableCellEditorComponent(table, value,
				isSelected, r, c);
		JTree t = treeTable.getTree();
		boolean rv = t.isRootVisible();
		int offsetRow = rv ? r : r - 1;
		Rectangle bounds = t.getRowBounds(offsetRow);
		int offset = bounds.x;
		TreeCellRenderer tcr = t.getCellRenderer();
		if (tcr instanceof DefaultTreeCellRenderer) {
			Object node = t.getPathForRow(offsetRow).getLastPathComponent();
			Icon icon;
			if (t.getModel().isLeaf(node))
				icon = ((DefaultTreeCellRenderer) tcr).getLeafIcon();
			else if (treeTable.tree.isExpanded(offsetRow))
				icon = ((DefaultTreeCellRenderer) tcr).getOpenIcon();
			else
				icon = ((DefaultTreeCellRenderer) tcr).getClosedIcon();
			if (icon != null) {
				offset += ((DefaultTreeCellRenderer) tcr).getIconTextGap()
						+ icon.getIconWidth();
			}
		}
		((JTreeTable.TreeTableTextField) getComponent()).offset = offset;
		return component;
	}

	/**
	 * This is overridden to forward the event to the tree. This will return
	 * true if the click count >= 3, or the event is null.
	 */
	@Override
	public boolean isCellEditable(EventObject e) {
		if (e instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) e;
			// If the modifiers are not 0 (or the left mouse button),
			// tree may try and toggle the selection, and table
			// will then try and toggle, resulting in the
			// selection remaining the same. To avoid this, we
			// only dispatch when the modifiers are 0 (or the left mouse
			// button).
			if (me.getModifiers() == 0
					|| me.getModifiers() == InputEvent.BUTTON1_MASK) {
				for (int counter = treeTable.getColumnCount() - 1; counter >= 0; counter--) {
					if (treeTable.getColumnClass(counter) == TreeTableModel.class) {
						MouseEvent newME = new MouseEvent(treeTable.tree, me
								.getID(), me.getWhen(), me.getModifiers(), me
								.getX()
								- treeTable.getCellRect(0, counter, true).x, me
								.getY(), me.getClickCount(), me
								.isPopupTrigger());
						treeTable.tree.dispatchEvent(newME);
						break;
					}
				}
			}
			if (me.getClickCount() >= 3) {
				return true;
			}
			return false;
		}
		if (e == null) {
			return true;
		}
		return false;
	}

	@Override
	public void addCellEditorListener(CellEditorListener l) {
		listenerList.add(CellEditorListener.class, l);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		listenerList.remove(CellEditorListener.class, l);
	}

	/**
	 * Notify all listeners that have registered interest for notification on
	 * this event type.
	 * 
	 * @see EventListenerList
	 */
	@Override
	protected void fireEditingStopped() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				((CellEditorListener) listeners[i + 1])
						.editingStopped(new ChangeEvent(this));
			}
		}
	}

	/**
	 * Notify all listeners that have registered interest for notification on
	 * this event type.
	 * 
	 * @see EventListenerList
	 */
	@Override
	protected void fireEditingCanceled() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == CellEditorListener.class) {
				((CellEditorListener) listeners[i + 1])
						.editingCanceled(new ChangeEvent(this));
			}
		}
	}
}