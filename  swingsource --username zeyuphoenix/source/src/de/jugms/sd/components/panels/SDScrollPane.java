/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.panels;

/**
 * 
 * @author hansolo
 */
public class SDScrollPane extends javax.swing.JScrollPane implements javax.swing.SwingConstants {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDScrollPane() {
        super();
        setOpaque(false);
        setBorder(new javax.swing.border.EmptyBorder(1, 1, 1, 1));
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    public void setHorizontalScrollBar(javax.swing.JScrollBar horizontalScrollBar) {
        super.setHorizontalScrollBar(new de.jugms.sd.components.misc.SDScrollBar(HORIZONTAL));
    }

    @Override
    public void setVerticalScrollBar(javax.swing.JScrollBar verticalScrollBar) {
        super.setVerticalScrollBar(new de.jugms.sd.components.misc.SDScrollBar(VERTICAL));
    }

    @Override
    public javax.swing.JScrollBar createHorizontalScrollBar() {
        de.jugms.sd.components.misc.SDScrollBar bar = new de.jugms.sd.components.misc.SDScrollBar();
        bar.setOrientation(HORIZONTAL);
        bar.setRadiusType(this.radiusType);

        return bar;
    }

    @Override
    public javax.swing.JScrollBar createVerticalScrollBar() {
        de.jugms.sd.components.misc.SDScrollBar bar = new de.jugms.sd.components.misc.SDScrollBar();
        bar.setOrientation(VERTICAL);
        bar.setRadiusType(this.radiusType);

        return bar;
    }

    @Override
    protected javax.swing.JViewport createViewport() {
        return new de.jugms.sd.components.panels.SDScrollPaneViewport();
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    @Override
    public String toString() {
        return "SDScrollPane";
    }
}
