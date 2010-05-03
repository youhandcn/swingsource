/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI implements
        javax.swing.SwingConstants {
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDScrollBarUI() {
        super();
        trackColor = new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f);
        setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
    }

    @Override
    protected void paintThumb(java.awt.Graphics g, javax.swing.JComponent comp,
            java.awt.Rectangle thumbBounds) {
        if (scrollbar.getOrientation() == HORIZONTAL) {
            if (thumbBounds.width < trackRect.height) {
                thumbBounds.width = trackRect.height;
            }
        } else {
            if (thumbBounds.height < trackRect.width) {
                thumbBounds.height = trackRect.width;
            }
        }

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(
                thumbBounds.x, thumbBounds.y);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(
                thumbBounds.x, thumbBounds.y + thumbBounds.height);
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(
                thumbBounds.x, thumbBounds.y + 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(
                thumbBounds.x, thumbBounds.y + thumbBounds.height - 2);
        final java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Float(
                thumbBounds.x, thumbBounds.y + 1);
        final java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(thumbBounds.x,
                thumbBounds.y + thumbBounds.height - (thumbBounds.height / 2.2f));

        final float HIGHLIGHT_FRACTION = (float) thumbBounds.width / (2 * thumbBounds.height);

        final float[] FRACTIONS_BACKGROUND = { 0.0f, HIGHLIGHT_FRACTION, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND = { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(0.0f, 0.0f, 0.0f, 1.0f),
                new java.awt.Color(0.0f, 0.0f, 0.0f, 1.0f) };

        g2.setPaint(new java.awt.LinearGradientPaint(BACKGROUND_START, BACKGROUND_STOP,
                FRACTIONS_BACKGROUND, COLORS_BACKGROUND));
        g2.fill(getThumbBackgroundShape(thumbBounds));

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_ENABLED
                .getGradientDarkLight();

        g2.setPaint(new java.awt.LinearGradientPaint(FOREGROUND_START, FOREGROUND_STOP,
                FRACTIONS_FOREGROUND, COLORS_FOREGROUND));
        g2.fill(getThumbForegroundShape(thumbBounds));

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };

        g2.setPaint(new java.awt.LinearGradientPaint(HIGHLIGHT_START, HIGHLIGHT_STOP,
                FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT));
        g2.fill(getThumbHighlightShape(thumbBounds));

        g2.dispose();
    }

    private java.awt.Shape getThumbBackgroundShape(java.awt.Rectangle thumbBounds) {
        if (scrollbar.getOrientation() == HORIZONTAL) {
            // Horizontal
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x, thumbBounds.y + 2,
                    thumbBounds.width, thumbBounds.height - 4);
        } else {
            // Vertical
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x + 2, thumbBounds.y,
                    thumbBounds.width - 4, thumbBounds.height);
        }
    }

    private java.awt.Shape getThumbForegroundShape(java.awt.Rectangle thumbBounds) {
        if (scrollbar.getOrientation() == HORIZONTAL) {
            // Horizontal
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x + 1, thumbBounds.y + 3,
                    thumbBounds.width - 2, thumbBounds.height - 6);
        } else {
            // Vertical
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x + 3, thumbBounds.y + 1,
                    thumbBounds.width - 6, thumbBounds.height - 2);
        }
    }

    private java.awt.Shape getThumbHighlightShape(java.awt.Rectangle thumbBounds) {
        if (scrollbar.getOrientation() == HORIZONTAL) {
            // Horizontal
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x + 1, thumbBounds.y + 3,
                    thumbBounds.width - 2, (thumbBounds.height - 6) / 2.0f);
        } else {
            // Vertical
            return new java.awt.geom.Rectangle2D.Float(thumbBounds.x + 3, thumbBounds.y + 1,
                    thumbBounds.width - 6, (thumbBounds.height - 2) / 2.0f);
        }
    }

    @Override
    protected void paintTrack(java.awt.Graphics g, javax.swing.JComponent comp,
            java.awt.Rectangle trackBounds) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        final float[] FRACTIONS = { 0.0f, 0.15f, 0.85f, 1.0f };

        final java.awt.Color[] COLORS_BACK = {
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f),
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f) };

        final java.awt.Color[] COLORS = { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };

        final java.awt.geom.Point2D START = new java.awt.geom.Point2D.Float(trackBounds.x,
                trackBounds.y);
        final java.awt.geom.Point2D STOP;

        if (scrollbar.getOrientation() == HORIZONTAL) {
            // Horizontal
            STOP = new java.awt.geom.Point2D.Float(trackBounds.x, trackBounds.y
                    + trackBounds.height);
        } else {
            // Vertical
            STOP = new java.awt.geom.Point2D.Float(trackBounds.x + trackBounds.width, trackBounds.y);
        }
        g2.setPaint(new java.awt.LinearGradientPaint(START, STOP, FRACTIONS, COLORS_BACK));
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height + 1);
        g2.setPaint(new java.awt.LinearGradientPaint(START, STOP, FRACTIONS, COLORS));
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height + 1);

        this.paintThumb(g, comp, super.thumbRect);
    }

    @Override
    protected javax.swing.JButton createDecreaseButton(int direction) {
        de.jugms.sd.components.buttons.SDArrowButton button = new de.jugms.sd.components.buttons.SDArrowButton(
                direction, NORTH);
        button.setRadiusType(this.radiusType);
        switch (scrollbar.getOrientation()) {
        case HORIZONTAL:
            button = new de.jugms.sd.components.buttons.SDArrowButton(direction, WEST);
            button.setRadiusType(this.radiusType);
            break;

        case VERTICAL:
            button = new de.jugms.sd.components.buttons.SDArrowButton(direction, NORTH);
            button.setRadiusType(this.radiusType);
            break;
        }
        return button;
    }

    @Override
    protected javax.swing.JButton createIncreaseButton(int direction) {
        de.jugms.sd.components.buttons.SDArrowButton button = new de.jugms.sd.components.buttons.SDArrowButton(
                direction, SOUTH);
        button.setRadiusType(this.radiusType);
        switch (scrollbar.getOrientation()) {
        case HORIZONTAL:
            button = new de.jugms.sd.components.buttons.SDArrowButton(direction, EAST);
            button.setRadiusType(this.radiusType);
            break;

        case VERTICAL:
            button = new de.jugms.sd.components.buttons.SDArrowButton(direction, SOUTH);
            button.setRadiusType(this.radiusType);
            break;
        }
        return button;
    }

    public de.jugms.sd.tools.RadiusType getRadiusType() {
        return this.radiusType;
    }

    public void setRadiusType(de.jugms.sd.tools.RadiusType radiusType) {
        this.radiusType = radiusType;
    }

    @Override
    public String toString() {
        return "SDScrollBarUI";
    }
}
