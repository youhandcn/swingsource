/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDToolTip extends javax.swing.JToolTip {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean isTranslucencySupported;

    public SDToolTip() {
        super();

        isTranslucencySupported = de.jugms.sd.tools.SDHelper.INSTANCE.isTranslucencySupported();

        // Replace PopupFactory with SDTranslucentPopupFactory
        javax.swing.PopupFactory
                .setSharedInstance(new de.jugms.sd.components.misc.SDTranslucentPopupFactory());

        setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0.0f, 0.0f);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0.0f, getHeight());
        final float[] FRACTIONS = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(0.3f, 0.3f, 0.3f, 0.5f),
                new java.awt.Color(0.0f, 0.0f, 0.0f, 0.5f), };

        java.awt.LinearGradientPaint gradient = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        java.awt.Shape background;
        if (isTranslucencySupported) {
            background = new java.awt.geom.RoundRectangle2D.Float(4, 4, this.getWidth() - 1 - 8,
                    this.getHeight() - 1 - 8, 15, 15);
        } else {
            background = new java.awt.geom.Rectangle2D.Float(0, 0, this.getWidth(), this
                    .getHeight());
        }

        g2.setPaint(gradient);
        g2.fill(background);

        g2.setColor(new java.awt.Color(0.8f, 0.8f, 0.8f, 0.5f));
        g2.setStroke(new java.awt.BasicStroke(1));
        g2.draw(background);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        String text = this.getComponent().getToolTipText();
        if (text != null) {
            java.awt.FontMetrics fm = g2.getFontMetrics();
            int h = fm.getAscent();
            g2.setColor(java.awt.Color.white);
            g2.drawString(text, 10, (this.getHeight() + h) / 2);
        }
    }

    @Override
    public java.awt.Dimension getPreferredSize() {
        java.awt.Dimension dim = super.getPreferredSize();

        if (isTranslucencySupported) {
            return new java.awt.Dimension((int) dim.getWidth() + 20, (int) dim.getHeight() + 16);
        } else {
            return new java.awt.Dimension((int) dim.getWidth() + 10, (int) dim.getHeight() + 10);
        }
    }

    @Override
    public String toString() {
        return "SDToolTip";
    }
}
