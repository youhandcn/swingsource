/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.buttons;

public class SDButton extends javax.swing.JButton implements java.awt.event.MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean armed;
    boolean enabled;
    boolean reflection;
    float reflectionDivisor;
    private volatile java.awt.Color fadingColor;
    private Thread fader;
    private final java.awt.Font FONT;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDButton(String text) {
        super(text);
        fader = null;
        setOpaque(false);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.reflection = false;
        this.reflectionDivisor = 1.0f;
        this.FONT = new java.awt.Font("Verdana", 1, 12);
        this.setPreferredSize(new java.awt.Dimension(82, 22));
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    public SDButton() {
        super();
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.reflection = false;
        this.reflectionDivisor = 1.0f;
        this.FONT = new java.awt.Font("Verdana", 1, 12);
        this.setPreferredSize(new java.awt.Dimension(82, 22));
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        java.awt.image.BufferedImage buttonImage = createButtonImage(getText());

        if (!enabled) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                    0.5f));
        }
        g2.drawImage(buttonImage, 0, 0, this);

        if (reflection) {
            g2.drawImage(createReflectionImage(buttonImage), 0, buttonImage.getHeight(), this);
        }

        g2.dispose();
    }

    private java.awt.image.BufferedImage createButtonImage(String text) {
        java.awt.image.BufferedImage buttonImage = new java.awt.image.BufferedImage(getWidth(),
                (int) (getHeight() / reflectionDivisor), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = buttonImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight() / reflectionDivisor);
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight() / reflectionDivisor - 2);
        final java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(0, getHeight()
                - (getHeight() / 2.2f));

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.33f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };
        final java.awt.Color[] COLORS_DISABLED;

        final java.awt.Color TOTAL_TRANSPARENT = new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f);

        if (armed) {
            COLORS_BACKGROUND = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                    .getGradientLightDark();
            COLORS_FOREGROUND = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                    .getGradientDarkLight();
            COLORS_DISABLED = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_DISABLED_ARMED
                    .getGradientLightDark();
        } else {
            if (enabled) {
                COLORS_BACKGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_BACKGROUND_ENABLED
                        .getGradientLightDark();
                COLORS_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_ENABLED
                        .getGradientDarkLight();
                COLORS_DISABLED = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_DISABLED_ENABLED
                        .getGradientLightDark();
            } else {
                COLORS_BACKGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_BACKGROUND_DISABLED
                        .getGradientLightDark();
                COLORS_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_DISABLED
                        .getGradientDarkLight();
                COLORS_DISABLED = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_DISABLED_DISABLED
                        .getGradientLightDark();
            }
        }

        // Draw background rectangle
        final java.awt.LinearGradientPaint PAINT_BACKGROUND = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(PAINT_BACKGROUND);
        g2.fill(getBackgroundShape());

        // Draw foreground rectangle
        final java.awt.LinearGradientPaint PAINT_FOREGROUND = new java.awt.LinearGradientPaint(
                FOREGROUND_START, FOREGROUND_STOP, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(PAINT_FOREGROUND);
        g2.fill(getForegroundShape());

        // Draw highlight shape
        final java.awt.LinearGradientPaint PAINT_HIGHLIGHT = new java.awt.LinearGradientPaint(
                HIGHLIGHT_START, HIGHLIGHT_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(PAINT_HIGHLIGHT);
        g2.fill(getHighlightShape());

        // Draw disabled rectangle
        final java.awt.LinearGradientPaint PAINT_DISABLED = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(PAINT_DISABLED);
        g2.fill(getForegroundShape());

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float((getWidth() / 2),
                    (getHeight() / (reflectionDivisor * 2)));
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { fadingColor, TOTAL_TRANSPARENT };
            int radius = getWidth() / 3;
            final java.awt.RadialGradientPaint PAINT_MOUSEOVER_HIGHLIGHT = new java.awt.RadialGradientPaint(
                    center, radius, dist, colors);
            g2.setPaint(PAINT_MOUSEOVER_HIGHLIGHT);
            g2.fill(getBackgroundShape());
        }

        // Draw text
        g2.setFont(FONT);
        java.awt.FontMetrics metrics = g2.getFontMetrics();
        java.awt.geom.Rectangle2D textBoundary = metrics.getStringBounds(text, g2);

        int armedOffset;
        if (armed) {
            armedOffset = 1;
        } else {
            armedOffset = 0;
        }

        // Draw text shadow
        g2.setColor(java.awt.Color.DARK_GRAY);
        g2.drawString(text, (int) ((getWidth() - textBoundary.getWidth()) / 2) + armedOffset,
                getHeight() / reflectionDivisor - metrics.getHeight()
                        - ((getHeight() / reflectionDivisor - metrics.getHeight()) / 2)
                        + metrics.getAscent() + armedOffset);

        // Draw text
        if (enabled) {
            g2.setColor(java.awt.Color.WHITE);
        } else {
            g2.setColor(java.awt.Color.LIGHT_GRAY);
        }
        g2.drawString(text, (int) ((getWidth() - textBoundary.getWidth()) / 2 + armedOffset - 1),
                getHeight() / reflectionDivisor - metrics.getHeight()
                        - ((getHeight() / reflectionDivisor - metrics.getHeight()) / 2)
                        + metrics.getAscent() + armedOffset - 1);

        g2.dispose();

        return buttonImage;
    }

    private java.awt.image.BufferedImage createReflectionImage(
            java.awt.image.BufferedImage sourceImage) {
        float opacity = 0.6f;
        float fadeHeight = 0.4f;

        java.awt.image.BufferedImage reflectionImage = new java.awt.image.BufferedImage(sourceImage
                .getWidth(), sourceImage.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) reflectionImage.getGraphics();

        g2.translate(0, sourceImage.getHeight());
        g2.scale(1, -1);
        g2.drawRenderedImage(sourceImage, null);
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.DST_IN));
        g2.setPaint(new java.awt.GradientPaint(0, sourceImage.getHeight() * fadeHeight,
                new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f), 0, sourceImage.getHeight(),
                new java.awt.Color(0.0f, 0.0f, 0.0f, opacity)));
        g2.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        g2.dispose();

        return reflectionImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight()
                / reflectionDivisor, getBackgroundRadius(), getBackgroundRadius());
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight()
                / reflectionDivisor - 2, foregroundRadius, foregroundRadius);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape rrectHighlight = (java.awt.geom.RoundRectangle2D) getForegroundShape();
        java.awt.Shape rectHighlight = new java.awt.geom.Rectangle2D.Float(1,
                (getHeight() / reflectionDivisor) - (getHeight() / (reflectionDivisor * 2.2f)),
                getWidth() - 2, getHeight() / reflectionDivisor - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(rrectHighlight);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
        repaint();
    }

    public boolean isArmed() {
        return this.armed;
    }

    public void setReflection(boolean reflection) {
        this.reflection = reflection;
        if (reflection) {
            this.setPreferredSize(new java.awt.Dimension(82, 50));
            this.reflectionDivisor = 2.0f;
        } else {
            this.setPreferredSize(new java.awt.Dimension(82, 25));
            this.reflectionDivisor = 1.0f;
        }
    }

    public boolean hasReflection() {
        return this.reflection;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
    }

    // Skip mouse events outside the shape
    @Override
    public boolean contains(int x, int y) {
        return getBackgroundShape().contains(x, y);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent event) {
    }

    @Override
    public void mousePressed(java.awt.event.MouseEvent event) {
        if (enabled) {
            armed = true;
        }
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                // Handler logic
                repaint();
            }
        });
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent event) {
        armed = false;
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                // Handler logic
                repaint();
            }
        });
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent event) {
        // Start HighlightAnimation
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                createFadingAnimation(true).start();
            }
        });
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent event) {
        // Stop HighlightAnimation
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                createFadingAnimation(false).start();
            }
        });
    }

    private Thread createFadingAnimation(final boolean fadeIn) {
        if (fader != null && fader.isAlive()) {
            fader.interrupt();
        }

        fader = new Thread(new java.lang.Runnable() {
            @Override
            public void run() {
                try {
                    int fadingAlpha = fadingColor.getAlpha();

                    // Fade out
                    if (!fadeIn) {
                        while (fadingAlpha > 0) {
                            Thread.sleep(50);
                            fadingColor = new java.awt.Color(255, 255, 255, fadingAlpha);
                            fadingAlpha -= 15;
                            repaint();
                        }
                        fadingColor = new java.awt.Color(255, 255, 255, 0);
                        repaint();
                    }

                    // Fade in
                    if (fadeIn) {
                        while (fadingAlpha < 100) {
                            Thread.sleep(50);
                            fadingColor = new java.awt.Color(255, 255, 255, fadingAlpha);
                            fadingAlpha += 15;
                            repaint();
                        }
                        fadingColor = new java.awt.Color(255, 255, 255, 100);
                        repaint();
                    }
                } catch (java.lang.NullPointerException exception) {
                } catch (InterruptedException exception) {
                }
            }
        });

        return fader;
    }

    private float getBackgroundRadius() {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            return (getHeight() / reflectionDivisor);
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
        return "SDButton";
    }
}
