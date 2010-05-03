package jcombobox;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 * JComboBox cell renderer.
 * 
 * @author zeyuphoenix <br>
 *         daily jcombobox MyComboBoxRenderer.java <br>
 *         2008 2008/03/18 16:56:30 <br>
 */
public class MyComboBoxRenderer extends JLabel implements ListCellRenderer,
		ActionListener {

	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * JComboBox that have action.
	 */
	private JComboBox combox = null;
	/**
	 * the item that select.
	 */
	private Object currentItem = null;

	/**
	 * JComboBox cell renderer.
	 */
	public MyComboBoxRenderer(JComboBox combox) {
		setOpaque(true);
		setBorder(new EmptyBorder(1, 1, 1, 1));
		this.combox = combox;
		combox.setSelectedIndex(0);
		this.currentItem = combox.getSelectedItem();
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

		String item = (value == null) ? "" : value.toString();
		if (((LineEnable) value).isLineEnabled()) {
			return new JSeparator(JSeparator.HORIZONTAL);
		}

		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());

			if (-1 < index) {
				if (((SelectEnable) value).isSelectEnabled()) {
					list.setToolTipText("You select is : " + item);
				} else {
					list.setToolTipText("You cn't select : " + item
							+ ", It select unEnable.");
				}
			}

		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		if (!((SelectEnable) value).isSelectEnabled()) {
			setBackground(list.getBackground());
			setForeground(UIManager.getColor("Label.disabledForeground"));
		}
		setFont(list.getFont());
		setText(item);
		return this;
	}

	/**
	 * The listener interface for receiving action events.
	 * 
	 * @param e
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		Object tempItem = combox.getSelectedItem();
		if (((LineEnable) tempItem).isLineEnabled()) {
			combox.setSelectedItem(currentItem);
		} else {
			currentItem = tempItem;
		}
		if (!((SelectEnable) tempItem).isSelectEnabled()) {
			combox.setSelectedItem(currentItem);
		} else {
			currentItem = tempItem;
		}
	}

}
