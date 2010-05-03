/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.comboboxes;

/**
 * 
 * @author hansolo
 */
public class SDComboBoxCellRenderer extends javax.swing.DefaultListCellRenderer {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean glossy;
    private javax.swing.JToolTip toolTip;
    private de.jugms.sd.tools.RadiusType radiusType;
    private final java.awt.Font FONT;
    private final java.awt.Color FOREGROUND_COLOR;
    private javax.swing.JComboBox comboBox;

    public SDComboBoxCellRenderer(javax.swing.JComboBox comboBox) {
        super();
        this.glossy = true;
        this.FONT = new java.awt.Font("Verdana", 0, 12);
        this.FOREGROUND_COLOR = new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f);
        this.comboBox = comboBox;
        setOpaque(false);
        setForeground(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f));
        setPreferredSize(new java.awt.Dimension(150, 24));
        setBorder(new javax.swing.border.EmptyBorder(0, 10, 0, 8));
        toolTip = new de.jugms.sd.components.misc.SDToolTip();
        toolTip.setComponent(this);
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
        javax.swing.UIManager
                .put("ComboBox.background", new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawImage(createListCellImage(), 0, 0, this);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createListCellImage() {
        java.awt.image.BufferedImage listCellImage = new java.awt.image.BufferedImage(getWidth(),
                getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = listCellImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight());
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight() - 2);
        final java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(0, getHeight()
                - (getHeight() / 2.2f));

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.66f, 1.0f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.08f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.04f) };
        final java.awt.Color[] COLORS_DISABLED;

        if (comboBox.isEnabled()) {
            COLORS_BACKGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };
            if (comboBox.hasFocus()) {
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
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.fill(getBackgroundShape());

        // Draw foreground rectangle
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(
                FOREGROUND_START, FOREGROUND_STOP, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        if (isGlossy()) {
            // Draw highlight shape
            java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(
                    HIGHLIGHT_START, HIGHLIGHT_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
            g2.setPaint(paintHighlight);
            g2.fill(getHighlightShape());
        }

        // Draw disabled rectangle
        java.awt.LinearGradientPaint paintDisabled = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(paintDisabled);
        g2.fill(getForegroundShape());

        // Draw text
        g2.setFont(FONT);
        g2.setColor(FOREGROUND_COLOR);
        java.awt.FontMetrics metrics = g2.getFontMetrics();

        g2.drawString(getText(), 10, getHeight() - metrics.getHeight()
                - ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent());

        g2.dispose();

        return listCellImage;
    }

    private java.awt.Shape getBackgroundShape() {
        java.awt.geom.RoundRectangle2D rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0,
                getWidth(), getHeight(), getBackgroundRadius(), getBackgroundRadius());
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Float(getWidth()
                - getHeight(), 0, getWidth(), getHeight());
        java.awt.geom.Area shapeArea = new java.awt.geom.Area(rrect);
        shapeArea.add(new java.awt.geom.Area(rect));

        return shapeArea;
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        java.awt.geom.RoundRectangle2D rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1,
                getWidth() - 1, getHeight() - 2, foregroundRadius, foregroundRadius);
        java.awt.geom.Rectangle2D rect = new java.awt.geom.Rectangle2D.Float(getWidth()
                - getHeight() - 2, 1, getWidth(), getHeight() - 2);
        java.awt.geom.Area shapeArea = new java.awt.geom.Area(rrect);
        shapeArea.add(new java.awt.geom.Area(rect));

        return shapeArea;
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape rrectHighlight = getForegroundShape();
        java.awt.geom.Rectangle2D rectHighlight = new java.awt.geom.Rectangle2D.Float(1,
                getHeight() - (getHeight() / 2.2f), getWidth() - 2, getHeight() - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(rrectHighlight);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    public void setGlossy(boolean glossy) {
        this.glossy = glossy;
        repaint();
    }

    public boolean isGlossy() {
        return this.glossy;
    }

    @Override
    public javax.swing.JToolTip createToolTip() {
        return toolTip;
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
    public java.awt.Component getListCellRendererComponent(javax.swing.JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }

    @Override
    public String toString() {
        return "SDListCellRenderer";
    }
}
