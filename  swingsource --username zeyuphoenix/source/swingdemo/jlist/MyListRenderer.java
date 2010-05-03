package jlist;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

/**
 * JList cell renderer.
 * 
 * @author zeyuphoenix <br>
 *         daily jlist MyListRenderer.java <br>
 *         2008 2008/03/19 13:33:00 <br>
 */
public class MyListRenderer extends JPanel implements TreeCellRenderer,
		MouseListener, ListCellRenderer {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the check box in the list cell.
	 */
	private JCheckBox checkBox = null;
	/**
	 * the label in the list cell.
	 */
	private MyListLabel label = null;
	/**
	 * list action.
	 */
	private JList list = null;
	/**
	 * common icon.
	 */
	private Icon commonIcon = null;

	/**
	 * JList cell renderer.
	 */
	public MyListRenderer(JList list) {
		this.list = list;
		setLayout(null);
		add(checkBox = new JCheckBox());
		add(label = new MyListLabel());
		checkBox.setBackground(UIManager.getColor("Tree.textBackground"));
		label.setForeground(UIManager.getColor("Tree.textForeground"));
		commonIcon = UIManager.getIcon("Tree.leafIcon");
	}

	/**
	 * Sets the value of the current tree cell to <code>value</code>.
	 * 
	 * @return the Component that the renderer uses to draw the value
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		setEnabled(tree.isEnabled());
		checkBox.setSelected(((CheckNode) value).isSelected());
		label.setFont(tree.getFont());
		label.setText(stringValue);
		label.setSelected(isSelected);
		label.setFocus(hasFocus);
		if (leaf) {
			label.setIcon(UIManager.getIcon("Tree.leafIcon"));
		} else if (expanded) {
			label.setIcon(UIManager.getIcon("Tree.openIcon"));
		} else {
			label.setIcon(UIManager.getIcon("Tree.closedIcon"));
		}
		return this;
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
			int index, boolean isSelected, boolean cellHasFocus) {
		setEnabled(((SelectEnable) value).isSelectEnabled());
		checkBox.setSelected(((SelectItem) value).getSelect());
		label.setFont(list.getFont());
		label.setText(value.toString());
		label.setSelected(isSelected);
		label.setFocus(cellHasFocus);
		if (((IconEnable) value).isIconEnabled()) {
			Icon icon = ((IconItem) value).getIcon();
			if (icon == null) {
				icon = commonIcon;
			}
			label.setIcon(icon);
		}
		return this;
	}

	/**
	 * get panel size.
	 * 
	 * @return size
	 */
	public Dimension getPreferredSize() {
		Dimension d_check = checkBox.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
						: d_check.height));
	}

	/**
	 * set the panel layout.
	 */
	public void doLayout() {
		Dimension d_check = checkBox.getPreferredSize();
		Dimension d_label = label.getPreferredSize();
		int y_check = 0;
		int y_label = 0;
		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}
		checkBox.setLocation(0, y_check);
		checkBox.setBounds(0, y_check, d_check.width, d_check.height);
		label.setLocation(d_check.width, y_label);
		label.setBounds(d_check.width, y_label, d_label.width, d_label.height);
	}

	/**
	 * set panel back ground.
	 */
	public void setBackground(Color color) {
		if (color instanceof ColorUIResource) {
			color = null;
		}
		super.setBackground(color);
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on
	 * a component.
	 */
	public void mouseClicked(MouseEvent e) {

		int index = list.locationToIndex(e.getPoint());
		MyListItem item = (MyListItem) list.getModel().getElementAt(index);
		if (((SelectEnable) item).isSelectEnabled()) {
			item.setSelect(!((SelectItem) item).getSelect());
			Rectangle rect = list.getCellBounds(index, index);
			list.repaint(rect);
		}
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
