package jcombobox;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * the JComboBox that it have some own method.
 * 
 * @author zeyuphoenix <br>
 *         daily combox MyCombox.java <br>
 *         2008 2008/03/18 15:13:36 <br>
 */
public class MyComboBox extends JComboBox {
	/**
	 * the size that popup width.
	 */
	protected int popupWidth = 0;
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a <code>JComboBox</code> that takes its items from an existing
	 * <code>ComboBoxModel</code>. Since the <code>ComboBoxModel</code> is
	 * provided, a combo box created using this constructor does not create a
	 * default combo box model and may impact how the insert, remove and add
	 * methods behave.
	 * 
	 * @param aModel
	 *            the <code>ComboBoxModel</code> that provides the displayed
	 *            list of items
	 * @see DefaultComboBoxModel
	 */
	public MyComboBox(ComboBoxModel aModel) {
		super(aModel);
		setUI(new MyComboBoxUI());
	}

	/**
	 * Creates a <code>JComboBox</code> that contains the elements in the
	 * specified array. By default the first item in the array (and therefore
	 * the data model) becomes selected.
	 * 
	 * @param items
	 *            an array of objects to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public MyComboBox(final Object[] items) {
		super(items);
		setUI(new MyComboBoxUI());
	}

	/**
	 * Creates a <code>JComboBox</code> that contains the elements in the
	 * specified Vector. By default the first item in the vector (and therefore
	 * the data model) becomes selected.
	 * 
	 * @param items
	 *            an array of vectors to insert into the combo box
	 * @see DefaultComboBoxModel
	 */
	public MyComboBox(Vector<?> items) {
		super(items);
		setUI(new MyComboBoxUI());
	}

	/**
	 * Creates a <code>JComboBox</code> with a default data model. The default
	 * data model is an empty list of objects. Use <code>addItem</code> to add
	 * items. By default the first item in the data model becomes selected.
	 * 
	 * @see DefaultComboBoxModel
	 */
	public MyComboBox() {
		super();
		setUI(new MyComboBoxUI());
	}

	/**
	 * set the combox popup menu size.
	 * 
	 * @param width
	 *            popup menu size
	 */
	public void setPopupWidth(int width) {
		popupWidth = width;
	}

	/**
	 * get the popup menu size.
	 * 
	 * @return popup menu size
	 */
	public Dimension getPopupSize() {
		Dimension size = getSize();
		// reset size.
		if (popupWidth < 1) {
			popupWidth = size.width;
		}
		return new Dimension(popupWidth, size.height);
	}

}
