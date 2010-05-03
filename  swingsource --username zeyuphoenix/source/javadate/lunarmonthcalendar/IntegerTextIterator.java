/*
 * Open source project: Smart Calendar.
 * 
 * @(#)IntegerTextIterator.java  2006/02/21
 *
 * Copyright 2006 Samuel Lee.
 * 
 * Source code is free, but must be marked with
 * "Powered by Samuel Lee" if be redistributed, or
 * "With contributions from Samuel Lee" if be modified.
 * The author of the following codelines will NOT be
 * responsible for legal liabilities caused by
 * any redistribution with or without any modification.
 * 
 * CAUTION: THIS CODE MUST NOT BE USED FOR ANY COMMERCIAL PURPOSE WITHOUT THE AUTHOR'S PERMISSION!
 * ANY VIOLATION MAY BE FACED WITH LITIGATION!
 */
package lunarmonthcalendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

/**
 * To implement a helper for text component which displays integer.
 * 
 * @version 0.1
 * @author Samuel Lee
 */
public class IntegerTextIterator extends IntegerIterator {
	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Item list. Here is a <code>JFormattedTextField</code> component.
	 */
	private JFormattedTextField component;

	/**
	 * Constructor: Creates a helper for the given
	 * <code>JFormattedTextField</code> component. Text field is formatted to
	 * display only integer.
	 * 
	 * @param component
	 *            a text field which will be formatted.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if the text field does not be initialized.
	 * 
	 * @see com.jungleford.common.iterator.IntegerIterator#IntegerIterator()
	 */
	public IntegerTextIterator(JFormattedTextField component) throws Exception {
		if (component == null)
			throw new Exception("TextField has NOT been initialized.");
		this.component = component;
		if (component.getValue() == null
				|| !(component.getValue() instanceof Integer)) {
			component.setValue(current);
		} else {
			current = (Integer) component.getValue();
		}
		formatComponent(component);
	}

	/**
	 * Constructor: Creates a helper for the given
	 * <code>JFormattedTextField</code> component by using a specific integer as
	 * initial value.
	 * 
	 * @param component
	 *            a text field which will be formatted.
	 * @param current
	 *            the integer displayed when intialized.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if the text field does not be initialized.
	 * 
	 * @see com.jungleford.common.iterator.IntegerIterator#IntegerIterator()
	 */
	public IntegerTextIterator(JFormattedTextField component, Integer current)
			throws Exception {
		if (component == null)
			throw new Exception("TextField has NOT been initialized.");
		this.component = component;
		component.setValue(current);
		this.current = current;
		formatComponent(component);
	}

	/**
	 * Constructor: Text field is formatted to display only integer.
	 * 
	 * @param component
	 *            a text field which will be formatted.
	 * @param start
	 *            the smallest integer of the range.
	 * @param end
	 *            the biggest integer of the range.
	 * @param current
	 *            the integer displayed when intialized.
	 * 
	 * @throws com.jungleford.common.iterator.IterateException
	 *             if the text field does not be initialized.
	 * 
	 * @see com.jungleford.common.iterator.IntegerIterator#IntegerIterator(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	public IntegerTextIterator(JFormattedTextField component, Integer start,
			Integer end, Integer current) throws Exception {
		super(start, end, current);
		if (component == null)
			throw new Exception("TextField has NOT been initialized.");
		this.component = component;
		component.setValue(current);
		formatComponent(component);
	}

	/**
	 * Initializes the <code>JFormattedTextField</code> component. To make sure
	 * that the component only recognizes positive integer.
	 * 
	 * @param component
	 *            a text field which will be formatted.
	 */
	private void formatComponent(final JFormattedTextField component) {
		if (component.getFormatter() == null) {
			DefaultFormatter fmt = new NumberFormatter(new DecimalFormat("#"));
			fmt.setValueClass(component.getValue().getClass());
			DefaultFormatterFactory fmtFactory = new DefaultFormatterFactory(
					fmt, fmt, fmt);
			component.setFormatterFactory(fmtFactory);
		}
		component.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// When input a integer
														// and press enter, this
														// method will be
														// triggered
				if (component.getValue() instanceof Integer) {
					IntegerTextIterator.super
							.setCurrentItem((Integer) component.getValue());
				}
				if (component.getValue() instanceof Long) {
					IntegerTextIterator.super.setCurrentItem(((Long) component
							.getValue()).intValue());
				}
			}
		});
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#setCurrentItem(int)
	 */
	public void setCurrentItem(int index) {
		super.setCurrentItem(index);
		component.setValue(super.getCurrentItem());
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#movePrevious()
	 */
	public void movePrevious() {
		super.movePrevious();
		component.setValue(super.getCurrentItem());
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveNext()
	 */
	public void moveNext() {
		super.moveNext();
		component.setValue(super.getCurrentItem());
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveFirst()
	 */
	public void moveFirst() {
		super.moveFirst();
		component.setValue(super.getCurrentItem());
	}

	/**
	 * @see com.jungleford.common.iterator.Iterator#moveLast()
	 */
	public void moveLast() {
		super.moveLast();
		component.setValue(super.getCurrentItem());
	}
}
