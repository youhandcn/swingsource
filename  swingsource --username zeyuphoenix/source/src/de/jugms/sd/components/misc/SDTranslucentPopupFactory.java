/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDTranslucentPopupFactory extends javax.swing.PopupFactory {
    @Override
    public javax.swing.Popup getPopup(java.awt.Component owner, java.awt.Component contents, int x,
            int y) throws IllegalArgumentException {
        return new de.jugms.sd.components.misc.SDTranslucentPopup(owner, contents, x, y);
    }

    @Override
    public String toString() {
        return "SDTranslucentPopupFactory";
    }
}
