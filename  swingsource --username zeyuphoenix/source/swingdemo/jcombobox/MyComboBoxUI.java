package jcombobox;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

/**
 * Metal UI for JComboBox.
 * 
 * @author zeyuphoenix <br>
 *         daily jcombobox MyComboBoxUI.java <br>
 *         2008 2008/03/18 15:57:19 <br>
 */
public class MyComboBoxUI extends MetalComboBoxUI {

	/**
	 * Creates the popup portion of the combo box.
	 * 
	 * @return an instance of <code>ComboPopup</code>
	 */
	protected ComboPopup createPopup() {
		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			/**
			 * UID.
			 */
			private static final long serialVersionUID = 1L;

			/**
			 * show the popup menu size.
			 */
			public void show() {
				Dimension popupSize = ((MyComboBox) comboBox).getPopupSize();
				// reset size.
				popupSize
						.setSize(popupSize.width,
								getPopupHeightForRowCount(comboBox
										.getMaximumRowCount()));
				Rectangle popupBounds = computePopupBounds(0, comboBox
						.getBounds().height, popupSize.width, popupSize.height);
				// set max and mini size.
				scroller.setMaximumSize(popupBounds.getSize());
				scroller.setPreferredSize(popupBounds.getSize());
				scroller.setMinimumSize(popupBounds.getSize());
				// Invalidates the container.
				list.invalidate();
				// set select.
				int selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex == -1) {
					list.clearSelection();
				} else {
					list.setSelectedIndex(selectedIndex);
				}
				list.ensureIndexIsVisible(list.getSelectedIndex());
				setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
				// show it.
				show(comboBox, popupBounds.x, popupBounds.y);
			}
		};
		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}

}
