/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDRepaintManager extends javax.swing.RepaintManager {
    @Override
    public void addDirtyRegion(javax.swing.JComponent comp, int x, int y, int w, int h) {
        super.addDirtyRegion(comp, x, y, w, h);

        javax.swing.JComponent root = getRootJComponent(comp);
        if (comp != root) {
            super.addDirtyRegion(root, 0, 0, root.getWidth(), root.getHeight());
        }
    }

    private javax.swing.JComponent getRootJComponent(javax.swing.JComponent comp) {
        java.awt.Container parent = comp.getParent();
        if (parent instanceof javax.swing.JComponent) {
            return getRootJComponent((javax.swing.JComponent) parent);
        }
        return comp;
    }

    @Override
    public String toString() {
        return "SDRepaintManager";
    }
}
