/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.buttons;

/**
 * 
 * @author hansolo
 */
public class SDRadioButton extends javax.swing.JRadioButton implements java.awt.event.MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean enabled;
    private volatile java.awt.Color fadingColor;
    private final int SIZE;
    private final java.awt.Font FONT;
    Thread fader;

    public SDRadioButton(String text) {
        super(text);
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.FONT = new java.awt.Font("Verdana", 0, 12);
        this.SIZE = 14;
        this.setPreferredSize(new java.awt.Dimension(82, 16));
    }

    public SDRadioButton() {
        super();
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.FONT = new java.awt.Font("Verdana", 0, 12);
        this.SIZE = 14;
        this.setPreferredSize(new java.awt.Dimension(82, 16));
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
        g2.drawImage(buttonImage, 2, (int) ((getHeight() - SIZE) / 2.0f), this);

        // Draw text
        g2.setFont(FONT);
        java.awt.FontMetrics metrics = g2.getFontMetrics();

        if (enabled) {
            g2.setColor(java.awt.Color.WHITE);
        } else {
            g2.setColor(java.awt.Color.LIGHT_GRAY);
        }
        g2.drawString(getText(), 2 + SIZE + 4,
                (int) (getHeight() - metrics.getStringBounds(getText(), g2).getHeight()
                        + metrics.getHeight() / 2.0f + metrics.getDescent()));

        g2.dispose();
    }

    private java.awt.image.BufferedImage createButtonImage(String text) {
        java.awt.image.BufferedImage buttonImage = new java.awt.image.BufferedImage(SIZE, SIZE,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = buttonImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0, SIZE);
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0, SIZE - 1);

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.66f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND = new java.awt.Color[] {
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 1.0f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.08f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.04f) };

        final java.awt.Color[] COLORS_FOREGROUND;

        if (enabled) {
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
        } else {
            COLORS_FOREGROUND = new java.awt.Color[] {
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.9f),
                    new java.awt.Color(0.11372f, 0.12549f, 0.14117f, 0.8f) };
        }

        // Draw background shape
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.fill(getBackgroundShape());

        // Draw foreground shape
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(
                FOREGROUND_START, FOREGROUND_STOP, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(
                FOREGROUND_START, FOREGROUND_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(paintHighlight);
        g2.fill(getHighlightShape());

        // Draw selection shape
        if (isSelected()) {
            if (enabled) {
                g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f));
            } else {
                g2.setColor(java.awt.Color.LIGHT_GRAY);
            }

            g2
                    .setPaint(new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Float(
                            4.0f, 4.0f), new java.awt.geom.Point2D.Float(4.0f, getHeight() - 4),
                            FRACTIONS_HIGHLIGHT, de.jugms.sd.tools.SDGlobals.INSTANCE
                                    .getActiveGradient().getGradientDarkLight()));
            g2.fill(getSelectionShape());
            g2.setPaint(new java.awt.LinearGradientPaint(
                    new java.awt.geom.Point2D.Float(5.0f, 5.0f), new java.awt.geom.Point2D.Float(
                            5.0f, 7.0f), FRACTIONS_HIGHLIGHT, new java.awt.Color[] {
                            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.6f),
                            new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) }));
            g2.fill(getSelectionHighlightShape());
        }

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(
                    (float) getBackgroundShape().getBounds().getCenterX(),
                    (float) getBackgroundShape().getBounds().getCenterY());
            final float[] DIST = { 0.0f, 1.0f };

            final java.awt.Color[] COLORS = { fadingColor,
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };
            int radius = getHeight() / 3;
            java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center,
                    radius, DIST, COLORS);
            g2.setPaint(gradient);
            g2.fill(getBackgroundShape());
        }

        // Draw text
        // g2.setFont(FONT);
        // java.awt.FontMetrics metrics = g2.getFontMetrics();
        //
        // if (enabled)
        // {
        // g2.setColor(java.awt.Color.WHITE);
        // }
        // else
        // {
        // g2.setColor(java.awt.Color.LIGHT_GRAY);
        // }
        // g2.drawString(getText(), 2 + SIZE + 4, (int)(getHeight() -
        // metrics.getStringBounds(getText(), g2).getHeight() +
        // metrics.getHeight() / 2.0f + metrics.getDescent()));

        g2.dispose();

        return buttonImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.Ellipse2D.Float(0, 0, SIZE, SIZE);
    }

    private java.awt.Shape getForegroundShape() {
        return new java.awt.geom.Ellipse2D.Float(1, 1, SIZE - 2, SIZE - 2);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape highlightShape = getForegroundShape();
        java.awt.Shape rectHighlight = new java.awt.geom.Rectangle2D.Float(1, SIZE - (SIZE / 2.2f),
                SIZE - 2, SIZE - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(highlightShape);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    private java.awt.Shape getSelectionShape() {
        return new java.awt.geom.Ellipse2D.Float(3, 3, SIZE - 6, SIZE - 6);
    }

    private java.awt.Shape getSelectionHighlightShape() {
        return new java.awt.geom.Ellipse2D.Float(5, 4, SIZE - 10, SIZE / 3);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        this.enabled = enabled;
    }

    // MouseEventListener methods
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

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent event) {

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
        createFadingAnimation(false).start();
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
            return new Thread();
        } else {
            return fader;
        }
    }

    @Override
    public String toString() {
        return "SDRadioButton";
    }
}
