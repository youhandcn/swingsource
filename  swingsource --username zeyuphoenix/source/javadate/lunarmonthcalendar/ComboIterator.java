package lunarmonthcalendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

/**
 * To implement a helper for combo box.
 */
public class ComboIterator extends ListIterator implements ActionListener {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Item list. Here is a <code>JComboBox</code> component.
	 */
	private JComboBox component;

	/**
	 * Default constructor: Creates a helper for the given
	 * <code>JComboBox</code> component.
	 * 
	 * @param component
	 *            a combo box which will be formatted.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if the combo box does not be initialized.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#ListIterator()
	 */
	public ComboIterator(JComboBox component) throws Exception {
		if (component == null)
			throw new Exception("ComboBox has NOT been initialized.");
		this.component = component;
		if (component.getSelectedItem() == null) {
			component.setSelectedItem(component.getItemAt(0));
		}
		setList(component);
		current = component.getSelectedItem();
		initLisenters(component);
	}

	/**
	 * Constructor: Creates a iterator from specific combo box, and specify the
	 * current item. If the given current item is not contained in the combo
	 * box, add it to the box.
	 * 
	 * @param component
	 *            a combo box which will be formatted.
	 * @param current
	 *            the object displayed when intialized.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if the combo box does not be initialized.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#ListIterator()
	 */
	public ComboIterator(JComboBox component, Object current) throws Exception {
		if (component == null)
			throw new Exception("ComboBox has NOT been initialized.");
		this.component = component;
		setList(component);
		if (list.contains(current)) {
			component.setSelectedItem(current);
		} else {
			component.addItem(current);
			component.setSelectedItem(current);
		}
		this.current = current;
		initLisenters(component);
	}

	/**
	 * @param component
	 *            combo box.
	 */
	private void initLisenters(final JComboBox component) {
		component.addActionListener(this);
		component.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				ComboIterator.super
						.setCurrentItem(component.getSelectedIndex());
			}
		});
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		this.setList(component);
		current = component.getSelectedItem();
	}

	/**
	 * @param component
	 *            combo box.
	 */
	private void setList(JComboBox component) {// Add items to list one by one.
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < component.getItemCount(); i++) {
			list.add(component.getItemAt(i));
		}
		this.list = list;
	}

	/**
	 * Sets helper to current value. And sets the value as the selected item of
	 * the combo box.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#setCurrentItem(int)
	 */
	public void setCurrentItem(int index) {
		super.setCurrentItem(index);
		component.setSelectedIndex(index);
	}

	/**
	 * Sets helper to previous value. And sets the prvious value as the selected
	 * item of the combo box.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#movePrevious()
	 */
	public void movePrevious() {
		super.movePrevious();
		component.setSelectedIndex(currentIndex);
	}

	/**
	 * Sets helper to next value. And sets the next value as the selected item
	 * of the combo box.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#moveNext()
	 */
	public void moveNext() {
		super.moveNext();
		component.setSelectedIndex(currentIndex);
	}

	/**
	 * Sets helper to first value. And sets the first value as the selected item
	 * of the combo box.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#moveFirst()
	 */
	public void moveFirst() {
		super.moveFirst();
		component.setSelectedIndex(currentIndex);
	}

	/**
	 * Sets helper to last value. And sets the last value as the selected item
	 * of the combo box.
	 * 
	 * @see com.jungleford.common.iterator.ListIterator#moveLast()
	 */
	public void moveLast() {
		super.moveLast();
		component.setSelectedIndex(currentIndex);
	}
}
