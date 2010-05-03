/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDMenuItem extends javax.swing.JMenuItem {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SDMenuItem() {
        super();
    }

    public SDMenuItem(javax.swing.Action action) {
        super(action);
    }

    public SDMenuItem(javax.swing.Icon icon) {
        super(icon);
    }

    public SDMenuItem(String text) {
        super(text);
    }

    public SDMenuItem(String text, javax.swing.Icon icon) {
        super(text, icon);
    }

    public SDMenuItem(String text, int i) {
        super(text, i);
    }

    @Override
    public void setUI(javax.swing.plaf.MenuItemUI menuItemUI) {
        super.setUI(new de.jugms.sd.ui.SDMenuItemUI());
    }

    @Override
    public void setBorderPainted(boolean borderPainted) {
        super.setBorderPainted(false);
    }

    @Override
    public boolean isBorderPainted() {
        return false;
    }
}
