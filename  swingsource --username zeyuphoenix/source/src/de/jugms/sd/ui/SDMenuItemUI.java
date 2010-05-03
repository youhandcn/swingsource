package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDMenuItemUI extends javax.swing.plaf.basic.BasicMenuItemUI {
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent comp) {
        return (new de.jugms.sd.ui.SDMenuItemUI());
    }

    @Override
    protected void paintBackground(java.awt.Graphics g, javax.swing.JMenuItem menuItem,
            java.awt.Color bgColor) {
        int itemWidth = menuItem.getWidth();
        int itemHeight = menuItem.getHeight();

        final float[] FRACTIONS_ARMED = { 0.0f, 0.15f, 0.85f, 1.0f };

        final java.awt.Color[] COLORS_ARMED = new java.awt.Color[] {
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLightTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa(),
                de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLightTranspa() };

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        if (menuItem.isArmed()) {
            java.awt.LinearGradientPaint gradientArmed = new java.awt.LinearGradientPaint(
                    new java.awt.geom.Point2D.Float(0, 0), new java.awt.geom.Point2D.Float(0,
                            itemHeight), FRACTIONS_ARMED, COLORS_ARMED);
            g2.setPaint(gradientArmed);
            g2.fillRect(1, 0, itemWidth - 2, itemHeight);
        }

        // Draw MenuItem text
        java.awt.Font menuFont = new java.awt.Font("Verdana", 0, 12);
        java.awt.FontMetrics metrics = g2.getFontMetrics(menuFont);
        g2.setFont(menuFont);
        g2.setColor(java.awt.Color.WHITE);
        java.awt.geom.Rectangle2D textRect = metrics.getStringBounds(menuItem.getText(), g2);

        g2.drawString(menuItem.getText(), (menuItem.getHorizontalTextPosition() + menuItem
                .getIconTextGap()), (int) ((itemHeight - textRect.getHeight()) / 2 + textRect
                .getHeight()));

        g2.dispose();
    }

    @Override
    public String toString() {
        return "SDMenuItemUI";
    }
}
