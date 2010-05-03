/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDMenuUI extends javax.swing.plaf.basic.BasicMenuUI {
    @Override
    protected void paintBackground(java.awt.Graphics g, javax.swing.JMenuItem menuItem,
            java.awt.Color bgColor) {
        super.paintBackground(g, menuItem, bgColor);

        menuItem.setBorderPainted(false);

        int itemWidth = menuItem.getWidth();
        int itemHeight = menuItem.getHeight();

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, itemHeight);

        final float[] FRACTIONS = { 0.0f, 0.1f, 0.4f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255), new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255) };

        final float[] FRACTIONS_ARMED = { 0.0f, 0.15f, 0.85f, 1.0f };

        final java.awt.Color[] COLORS_ARMED = new java.awt.Color[] {
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLightTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLightTranspa() };

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.LinearGradientPaint gradient = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        g2.setPaint(gradient);
        g2.fillRect(0, 0, itemWidth, itemHeight);

        if (menuItem.isSelected()) {
            java.awt.LinearGradientPaint gradientArmed = new java.awt.LinearGradientPaint(start,
                    stop, FRACTIONS_ARMED, COLORS_ARMED);
            g2.setPaint(gradientArmed);
            g2.fillRect(0, 2, itemWidth, itemHeight - 2);
        }

        // Draw MenuItem text
        java.awt.Font menuFont = new java.awt.Font("Verdana", 0, 12);
        java.awt.FontMetrics metrics = g2.getFontMetrics(menuFont);
        g2.setFont(menuFont);
        g2.setColor(java.awt.Color.WHITE);
        java.awt.geom.Rectangle2D textRect = metrics.getStringBounds(menuItem.getText(), g2);
        g2.drawString(menuItem.getText(), (int) ((itemWidth - textRect.getWidth()) / 2),
                (int) ((itemHeight - textRect.getHeight()) / 2 + textRect.getHeight()));

        g2.dispose();
    }

    @Override
    public String toString() {
        return "SDMenuUI";
    }
}
