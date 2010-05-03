/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.textfields;

/**
 * 
 * @author hansolo
 */
public class SDPasswordField extends javax.swing.JPasswordField {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final java.awt.image.BufferedImage KEY_IMAGE;
    private boolean glossy;
    private final javax.swing.border.AbstractBorder ICON_BORDER;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDPasswordField() {
        super();

        this.KEY_IMAGE = createKeyImage();
        this.glossy = true;
        this.ICON_BORDER = new javax.swing.border.EmptyBorder(0, 32, 0, 8);
        setOpaque(true);
        setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f));
        setCaretColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f));
        setCaretPosition(0);
        setSelectedTextColor(java.awt.Color.WHITE);
        setSelectionColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDarkTranspa());
        setPreferredSize(new java.awt.Dimension(100, 22));
        setMargin(new java.awt.Insets(0, 1, 0, 1));
        setBorder(this.ICON_BORDER);
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(createTextFieldImage(), 0, 0, this);

        setOpaque(false);
        super.paintComponent(g);
        setOpaque(true);
    }

    private java.awt.image.BufferedImage createTextFieldImage() {
        java.awt.image.BufferedImage textFieldImage = new java.awt.image.BufferedImage(getWidth(),
                (getHeight()), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = textFieldImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.66f, 1.0f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.16f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.08f) };
        final java.awt.Color[] COLORS_DISABLED;

        if (this.isEnabled()) {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };
            if (hasFocus()) {
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
        g2.fill(getBackgroundShape());

        // Draw foreground rectangle
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(start,
                stop, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        if (isGlossy()) {
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

        // Draw Icon
        setBorder(this.ICON_BORDER);
        int offX = 9;
        int offY = 5;
        g2.drawImage(KEY_IMAGE, offX, offY, null);

        g2.dispose();

        return textFieldImage;
    }

    private java.awt.Shape getBackgroundShape() {
        // return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(),
        // getHeight(), getHeight(), getHeight());
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
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

    public boolean isGlossy() {
        return this.glossy;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
        repaint();
    }

    private java.awt.image.BufferedImage createKeyImage() {
        java.awt.image.BufferedImage keyImage = new java.awt.image.BufferedImage(14, 14,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) keyImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f));
        java.awt.Stroke oldStroke = g2.getStroke();
        java.awt.Stroke stroke = new java.awt.BasicStroke(2.0f, java.awt.BasicStroke.CAP_ROUND,
                java.awt.BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);

        g2.setTransform(java.awt.geom.AffineTransform.getRotateInstance(Math.toRadians(-55), 7, 7));

        g2.drawOval(4, 0, 7, 5);
        g2.drawLine(8, 5, 8, 12);
        g2.setStroke(oldStroke);
        g2.fillRect(3, 9, 5, 3);
        g2.dispose();

        return keyImage;
    }

    private float getBackgroundRadius() {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            return (getHeight());
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
        return "SDPasswordField";
    }
}
