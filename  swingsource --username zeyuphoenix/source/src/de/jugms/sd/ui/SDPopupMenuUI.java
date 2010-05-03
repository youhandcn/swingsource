/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDPopupMenuUI extends javax.swing.plaf.basic.BasicPopupMenuUI {
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
        return (new de.jugms.sd.ui.SDPopupMenuUI());
    }

    @Override
    public void paint(java.awt.Graphics g, javax.swing.JComponent comp) {
        super.paint(g, comp);

        g.setColor(new java.awt.Color(0.0f, 0.0f, 0.0f, 0.8f));
        g.fillRect(0, 0, comp.getWidth(), comp.getHeight());
        g.setColor(java.awt.Color.DARK_GRAY);
        g.drawRect(0, 0, comp.getWidth() - 1, comp.getHeight() - 1);
    }

    @Override
    public void installUI(javax.swing.JComponent comp) {
        super.installUI(comp);
        popupMenu.setOpaque(false);
        popupMenu.setBorderPainted(false);
    }

    @Override
    public javax.swing.Popup getPopup(javax.swing.JPopupMenu popup, int x, int y) {
        javax.swing.Popup pp = super.getPopup(popup, x, y);

        javax.swing.JPanel panel = (javax.swing.JPanel) popup.getParent();
        panel.setOpaque(false);

        return pp;
    }

    @Override
    public String toString() {
        return "SDPopupMenuUI";
    }
}
