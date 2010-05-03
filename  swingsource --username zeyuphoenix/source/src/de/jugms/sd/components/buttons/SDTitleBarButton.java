/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.buttons;

public class SDTitleBarButton extends javax.swing.JButton implements java.awt.event.MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean armed;
    boolean enabled;
    final private java.awt.BasicStroke THICK_STROKE;
    private volatile java.awt.Color fadingColor;
    private Thread fader;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDTitleBarButton() {
        super();
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.THICK_STROKE = new java.awt.BasicStroke(2.0f, java.awt.BasicStroke.CAP_ROUND,
                java.awt.BasicStroke.JOIN_ROUND);
        this.setPreferredSize(new java.awt.Dimension(17, 16));

        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        java.awt.image.BufferedImage buttonImage = createButtonImage();

        if (!enabled) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                    0.5f));
        }
        g2.drawImage(buttonImage, 0, 0, this);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createButtonImage() {
        java.awt.image.BufferedImage buttonImage = new java.awt.image.BufferedImage(getWidth(),
                getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = buttonImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.33f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND;
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };
        final java.awt.Color[] COLORS_DISABLED;

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

        final java.awt.Color TOTAL_TRANSPARENT = new java.awt.Color(0.0f, 0.0f, 0.0f, 0.0f);

        // Draw background rectangle
        g2.setPaint(new java.awt.LinearGradientPaint(start, stop, FRACTIONS_BACKGROUND,
                COLORS_BACKGROUND));
        g2.fill(getBackgroundShape());

        // Draw foreground rectangle
        g2.setPaint(new java.awt.LinearGradientPaint(start, stop, FRACTIONS_FOREGROUND,
                COLORS_FOREGROUND));
        g2.fill(getForegroundShape());

        // Draw highlight shape
        java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(paintHighlight);
        g2.fill(getHighlightShape());

        // Draw disabled rectangle
        java.awt.LinearGradientPaint paintDisabled = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(paintDisabled);
        g2.fill(getForegroundShape());

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float((getWidth() / 2),
                    (getHeight() / 2));
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { fadingColor, TOTAL_TRANSPARENT };
            java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center,
                    getWidth(), dist, colors);
            g2.setPaint(gradient);
            g2.fill(getBackgroundShape());
        }

        // Draw Symbol
        g2.setStroke(THICK_STROKE);
        g2.setColor(java.awt.Color.WHITE);
        g2.drawLine(5, 5, 11, 11);
        g2.drawLine(11, 5, 5, 11);

        g2.dispose();

        return buttonImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                getBackgroundRadius(), getBackgroundRadius());
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                foregroundRadius, foregroundRadius);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape rrectHighlight = getForegroundShape();
        java.awt.geom.Rectangle2D rectHighlight = new java.awt.geom.Rectangle2D.Float(1,
                getHeight() - (getHeight() / 2.2f), getWidth() - 2, getHeight() - 2);
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

    @Override
    public java.awt.Dimension getSize() {
        return new java.awt.Dimension(16, 16);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(16, 16);
    }

    @Override
    public void setSize(java.awt.Dimension dim) {
        super.setSize(16, 16);
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
        repaint();
    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent event) {
        armed = false;
        repaint();
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

                    // Fade to transparent
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

                    // Fade to light
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

        if (fader == null) {
            return new Thread(new java.lang.Runnable() {
                @Override
                public void run() {
                    try {
                        int fadingAlpha = fadingColor.getAlpha();

                        // Fade to transparent
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

                        // Fade to light
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
        } else {
            return fader;
        }
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
        return "SDTitleBarButton";
    }
}
