/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.ui;

/**
 * 
 * @author hansolo
 */
public class SDButtonUI extends javax.swing.plaf.basic.BasicButtonUI implements
        java.awt.event.MouseListener {
    boolean armed;
    boolean enabled;
    private volatile java.awt.Color fadingColor;
    private Thread fader;
    private final java.awt.Font FONT;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDButtonUI(javax.swing.AbstractButton button) {
        this.fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.fader = null;
        this.enabled = true;
        this.FONT = new java.awt.Font("Verdana", 1, 12);
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.getRootPane().putClientProperty("Window.shadow", Boolean.FALSE);
        button.setFocusable(false);
        button.addMouseListener(this);
    }

    @Override
    public void paint(java.awt.Graphics g, javax.swing.JComponent comp) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        java.awt.RenderingHints rh = g2.getRenderingHints();
        rh
                .put(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        java.awt.image.BufferedImage buttonImage = createButtonImage(
                (javax.swing.AbstractButton) comp, ((javax.swing.AbstractButton) comp).getText());

        if (!enabled) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                    0.5f));
        }
        g2.drawImage(buttonImage, 0, 0, null);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createButtonImage(javax.swing.AbstractButton button,
            String text) {
        java.awt.image.BufferedImage buttonImage = new java.awt.image.BufferedImage(button
                .getWidth(), button.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = buttonImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, button.getHeight());

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

        // Draw background rectangle
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(start,
                stop, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.fill(getBackgroundShape(button));

        // Draw foreground rectangle
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(start,
                stop, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape(button));

        // Draw highlight shape
        java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(paintHighlight);
        g2.fill(getForegroundShape(button));

        // Draw disabled rectangle
        java.awt.LinearGradientPaint paintDisabled = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS_BACKGROUND, COLORS_DISABLED);
        g2.setPaint(paintDisabled);
        g2.fill(getForegroundShape(button));

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float((button.getWidth() / 2),
                    (button.getHeight() / 2));
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { fadingColor, new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };
            int radius = button.getWidth() / 3;
            java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center,
                    radius, dist, colors);
            g2.setPaint(gradient);
            g2.fill(getBackgroundShape(button));
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
        g2.drawString(text,
                (int) ((button.getWidth() - textBoundary.getWidth()) / 2) + armedOffset, button
                        .getHeight()
                        - metrics.getHeight()
                        - ((button.getHeight() - metrics.getHeight()) / 2)
                        + metrics.getAscent() + armedOffset);

        // Draw text
        if (enabled) {
            g2.setColor(java.awt.Color.WHITE);
        } else {
            g2.setColor(java.awt.Color.LIGHT_GRAY);
        }
        g2.drawString(text,
                (int) ((button.getWidth() - textBoundary.getWidth()) / 2 + armedOffset - 1), button
                        .getHeight()
                        - metrics.getHeight()
                        - ((button.getHeight() - metrics.getHeight()) / 2)
                        + metrics.getAscent() + armedOffset - 1);

        g2.dispose();

        return buttonImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape(javax.swing.AbstractButton button) {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, button.getWidth(),
                button.getHeight(), getBackgroundRadius(button), getBackgroundRadius(button));
    }

    private java.awt.Shape getForegroundShape(javax.swing.AbstractButton button) {
        float foregroundRadius = getBackgroundRadius(button) - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, button.getWidth() - 2, button
                .getHeight() - 2, foregroundRadius, foregroundRadius);
    }

    @Override
    protected void paintButtonPressed(java.awt.Graphics g, javax.swing.AbstractButton button) {
        setArmed(true);
        paint(g, button);
        button.repaint();
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public boolean isArmed() {
        return this.armed;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // Skip mouse events outside the shape
    public boolean contains(javax.swing.AbstractButton button, int x, int y) {
        return button.getBounds().contains(x, y);
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent event) {
    }

    @Override
    public void mousePressed(final java.awt.event.MouseEvent event) {
        if (enabled) {
            armed = true;
        }
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                // Handler logic
                ((javax.swing.AbstractButton) event.getSource()).repaint();
            }
        });
    }

    @Override
    public void mouseReleased(final java.awt.event.MouseEvent event) {
        armed = false;
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                // Handler logic
                ((javax.swing.AbstractButton) event.getSource()).repaint();
            }
        });
    }

    @Override
    public void mouseEntered(final java.awt.event.MouseEvent event) {
        // Start HighlightAnimation
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                createFadingAnimation((javax.swing.AbstractButton) event.getSource(), true).start();
            }
        });
    }

    @Override
    public void mouseExited(final java.awt.event.MouseEvent event) {
        // Stop HighlightAnimation
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {
            @Override
            public void run() {
                createFadingAnimation((javax.swing.AbstractButton) event.getSource(), false)
                        .start();
            }
        });
    }

    private Thread createFadingAnimation(final javax.swing.AbstractButton button,
            final boolean fadeIn) {
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
                            button.repaint();
                        }
                        fadingColor = new java.awt.Color(255, 255, 255, 0);
                        button.repaint();
                    }

                    // Fade to light
                    if (fadeIn) {
                        while (fadingAlpha < 100) {
                            Thread.sleep(50);
                            fadingColor = new java.awt.Color(255, 255, 255, fadingAlpha);
                            fadingAlpha += 15;
                            button.repaint();
                        }
                        fadingColor = new java.awt.Color(255, 255, 255, 100);
                        button.repaint();
                    }
                } catch (java.lang.NullPointerException exception) {
                } catch (InterruptedException exception) {
                }
            }
        });

        return fader;
    }

    private float getBackgroundRadius(javax.swing.AbstractButton button) {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            return (button.getHeight());
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
        return "SDButtonUI";
    }
}
