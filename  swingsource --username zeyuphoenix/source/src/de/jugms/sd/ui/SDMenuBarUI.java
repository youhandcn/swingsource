/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDMenuBarUI extends javax.swing.plaf.basic.BasicMenuBarUI {
    @Override
    public void paint(java.awt.Graphics g, javax.swing.JComponent menuBar) {
        super.paint(g, menuBar);
        int barWidth = menuBar.getWidth();
        int barHeight = menuBar.getHeight();

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, barHeight);

        final float[] FRACTIONS = { 0.0f, 0.1f, 0.4f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255), new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255) };

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        java.awt.LinearGradientPaint paint = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        g2.setPaint(paint);
        g2.fillRect(0, 0, barWidth, barHeight);

        g2.dispose();
    }

    @Override
    public String toString() {
        return "SDMenuBarUI";
    }
}
