/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.panels;

/**
 * 
 * @author hansolo
 */
public class SDScrollPaneViewport extends javax.swing.JViewport {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean glossy = true;
    boolean focused = false;
    private de.jugms.sd.tools.RadiusType radiusType;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(createViewportImage(), 0, 0, this);
        super.paintChildren(g);
        g2.dispose();
    }

    private java.awt.image.BufferedImage createViewportImage() {
        java.awt.image.BufferedImage viewportImage = new java.awt.image.BufferedImage(getWidth(),
                (getHeight()), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = viewportImage.createGraphics();

        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float HIGHLIGHT_FRACTION = 1.0f - (16.0f) / (2 * getHeight());

        final float[] FRACTIONS_BACKGROUND = { 0.0f, HIGHLIGHT_FRACTION, 1.0f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.08f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.04f) };
        final java.awt.Color[] COLORS_DISABLED;

        if (this.isEnabled()) {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };
            if (focused) {
                COLORS_FOREGROUND = new java.awt.Color[] {
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getLight(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveTranspaGradient().getLight() };
            } else {
                COLORS_FOREGROUND = new java.awt.Color[] {
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                        new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f) };
            }
            COLORS_DISABLED = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };
        } else {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.5f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.05f) };
            COLORS_FOREGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.4f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.45f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.45f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.4f) };
            COLORS_DISABLED = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f) };
        }

        // Draw background rectangle
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(start,
                stop, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.draw(getBackgroundShape());

        // Draw foreground rectangle
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(start,
                stop, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        if (glossy) {
            // Draw highlight shape
            java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(start,
                    stop, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
            g2.setPaint(paintHighlight);
            g2.fill(getHighlightShape());
        }

        // Draw disabled rectangle
        java.awt.LinearGradientPaint paintDisabled = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(paintDisabled);
        g2.fill(getForegroundShape());

        g2.dispose();

        return viewportImage;
    }

    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - 1,
                getBackgroundRadius(), getBackgroundRadius());
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                foregroundRadius, foregroundRadius);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.geom.RoundRectangle2D rrectHighlight = (java.awt.geom.RoundRectangle2D) getForegroundShape();
        java.awt.geom.Rectangle2D rectHighlight = new java.awt.geom.Rectangle2D.Float(1,
                getHeight() - (getHeight() / 2.2f), getWidth() - 2, getHeight() - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(rrectHighlight);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    public void setView(javax.swing.JComponent view) {
        view.setOpaque(false);
        super.setView(view);
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    private float getBackgroundRadius() {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            return (getPreferredSize().height);
        default:
            return (10.0f);
        }
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    @Override
    public String toString() {
        return "SDScrollPaneViewport";
    }
}
