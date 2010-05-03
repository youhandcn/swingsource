/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.comboboxes;

/**
 * 
 * @author hansolo
 */
public class SDComboPopup extends javax.swing.plaf.basic.BasicComboPopup implements
        java.awt.event.ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private javax.swing.JPopupMenu popupMenu = new javax.swing.JPopupMenu();
    private static final int LEFT_SHIFT = 22;

    public SDComboPopup(javax.swing.JComboBox combo) {
        super(combo);
        popupMenu.setUI(new de.jugms.sd.ui.SDPopupMenuUI());
        this.comboBox = combo;
        comboBox.addActionListener(this);
    }

    private void clearAndFillMenu() {
        popupMenu.removeAll();

        javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();

        // add the given items to the popup menu.
        for (int i = 0; i < comboBox.getModel().getSize(); i++) {
            Object item = comboBox.getModel().getElementAt(i);
            de.jugms.sd.components.misc.SDMenuItem menuItem = new de.jugms.sd.components.misc.SDMenuItem(
                    item.toString());
            menuItem.addActionListener(createMenuItemListener(item));
            buttonGroup.add(menuItem);
            popupMenu.add(menuItem);

            // if the current item is selected, make the menu item reflect that.
            if (item.equals(comboBox.getModel().getSelectedItem())) {
                menuItem.setSelected(true);
                popupMenu.setSelected(menuItem);
            }
        }

        popupMenu.pack();
        int popupWidth = comboBox.getWidth() + 5;
        int popupHeight = popupMenu.getPreferredSize().height;
        popupMenu.setPreferredSize(new java.awt.Dimension(popupWidth, popupHeight));
    }

    private java.awt.event.ActionListener createMenuItemListener(final Object comboBoxItem) {
        return new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent event) {
                comboBox.setSelectedItem(comboBoxItem);
            }
        };
    }

    @Override
    public void show() {
        clearAndFillMenu();
        java.awt.Rectangle popupBounds = calculateInitialPopupBounds();

        popupMenu.show(comboBox, popupBounds.x, popupBounds.y);
    }

    @Override
    public void hide() {
        popupMenu.setVisible(false);
    }

    @Override
    public boolean isVisible() {
        return popupMenu.isVisible();
    }

    private java.awt.Rectangle calculateInitialPopupBounds() {
        // grab the right most location of the button.
        int comboBoxRightEdge = comboBox.getWidth();

        // figure out how the height of a menu item.
        java.awt.Insets insets = popupMenu.getInsets();

        // calculate the x and y value at which to place the popup menu. by
        // default, this will place the selected menu item in the popup item
        // directly over the button.
        int x = comboBoxRightEdge - popupMenu.getPreferredSize().width - LEFT_SHIFT;
        int selectedItemIndex = popupMenu.getSelectionModel().getSelectedIndex();
        int componentCenter = comboBox.getHeight() / 2;
        int menuItemHeight = popupMenu.getComponent(selectedItemIndex).getPreferredSize().height;
        int menuItemCenter = insets.top + (selectedItemIndex * menuItemHeight) + menuItemHeight / 2;
        int y = componentCenter - menuItemCenter;

        return new java.awt.Rectangle(new java.awt.Point(x, y), popupMenu.getPreferredSize());
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        int popupWidth;
        int popupHeight;

        switch (comboBox.getSelectedIndex()) {
        case -1:
            comboBox.addItem(comboBox.getSelectedItem().toString());
            popupWidth = comboBox.getWidth() + 5;
            popupHeight = popupMenu.getSize().height
                    + popupMenu.getSubElements()[0].getComponent().getHeight();
            popupMenu.setPreferredSize(new java.awt.Dimension(popupWidth, popupHeight));
            break;
        default:
            if (comboBox.getSelectedItem().toString().isEmpty()) {
                comboBox.removeItemAt(comboBox.getSelectedIndex());
                popupWidth = comboBox.getWidth() + 5;
                popupHeight = popupMenu.getSize().height
                        + popupMenu.getSubElements()[0].getComponent().getHeight();
                popupMenu.setPreferredSize(new java.awt.Dimension(popupWidth, popupHeight));
            }
            break;
        }
    }

    @Override
    public String toString() {
        return "SDComboPopup";
    }
}
