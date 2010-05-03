/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDScrollBar extends javax.swing.JScrollBar implements javax.swing.SwingConstants {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SDScrollBar() {
        super();
        setOpaque(false);
        de.jugms.sd.ui.SDScrollBarUI scrollBarUI = new de.jugms.sd.ui.SDScrollBarUI();
        setUI(scrollBarUI);
    }

    public SDScrollBar(int orientation) {
        super(orientation);
        setOpaque(false);
        de.jugms.sd.ui.SDScrollBarUI scrollBarUI = new de.jugms.sd.ui.SDScrollBarUI();
        setUI(scrollBarUI);
    }

    public SDScrollBar(int orientation, int value, int extent, int min, int max) {
        super(orientation, value, extent, min, max);
        setOpaque(false);
        de.jugms.sd.ui.SDScrollBarUI scrollBarUI = new de.jugms.sd.ui.SDScrollBarUI();
        setUI(scrollBarUI);
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        ((de.jugms.sd.ui.SDScrollBarUI) getUI()).setRadiusType(radiusType);
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
        de.jugms.sd.ui.SDScrollBarUI scrollBarUI = new de.jugms.sd.ui.SDScrollBarUI();
        setUI(scrollBarUI);
    }

    @Override
    public String toString() {
        return "SDScrollBar";
    }
}
