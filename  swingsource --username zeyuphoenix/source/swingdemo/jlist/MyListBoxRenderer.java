package jlist;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

/**
 * * the list cell renderer with check box.
 * 
 * @author zeyuphoenix <br>
 *         daily jlist MyListBoxRenderer.java <br>
 *         2008 2008/03/19 13:53:44 <br>
 */
public class MyListBoxRenderer extends JCheckBox implements ListCellRenderer,
		MouseListener {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * list action.
	 */
	private JList list = null;

	/**
	 * the list cell with check box.
	 */
	public MyListBoxRenderer(JList list) {
		this.list = list;
		setBackground(UIManager.getColor("List.textBackground"));
		setForeground(UIManager.getColor("List.textForeground"));
	}

	/**
	 * Return a component that has been configured to display the specified
	 * value.
	 * 
	 * @param list
	 *            The JList we're painting.
	 * @param value
	 *            The value returned by list.getModel().getElementAt(index).
	 * @param index
	 *            The cells index.
	 * @param isSelected
	 *            True if the specified cell was selected.
	 * @param cellHasFocus
	 *            True if the specified cell has the focus.
	 * @return A component whose paint() method will render the specified value.
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		setEnabled(((MyListItem) value).isSelectEnabled());
		setSelected(((MyListItem) value).getSelect());
		setFont(list.getFont());
		setText(value == null ? "" : value.toString());
		return this;
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on
	 * a component.
	 */
	public void mouseClicked(MouseEvent e) {

		int index = list.locationToIndex(e.getPoint());
		MyListItem item = (MyListItem) list.getModel().getElementAt(index);
		item.setSelectEnabled(!((SelectEnable) item).isSelectEnabled());
		Rectangle rect = list.getCellBounds(index, index);
		list.repaint(rect);

	}

	/**
	 * Invoked when a mouse button has been pressed on a component.
	 */
	public void mousePressed(MouseEvent e) {

	}

	/**
	 * Invoked when a mouse button has been released on a component.
	 */
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse enters a component.
	 */
	public void mouseEntered(MouseEvent e) {

	}

	/**
	 * Invoked when the mouse exits a component.
	 */
	public void mouseExited(MouseEvent e) {

	}
}
