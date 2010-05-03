/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.buttons;

/**
 * 
 * @author hansolo
 */
public class SDCheckBox extends javax.swing.JCheckBox implements java.awt.event.MouseListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean enabled;
    final int BOX_WIDTH;
    final int BOX_HEIGHT;
    final int BUTTON_SIZE;
    final java.awt.Font BOX_FONT;
    private final java.awt.Font FONT;
    private volatile java.awt.Color fadingColor;
    private Thread fader;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDCheckBox(String text) {
        super(text);
        fader = null;
        setOpaque(false);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.FONT = new java.awt.Font("Verdana", 0, 12);
        this.BOX_WIDTH = 28;
        this.BOX_HEIGHT = 14;
        this.BUTTON_SIZE = 10;
        this.BOX_FONT = new java.awt.Font("Verdana", 1, 10);
        this.setPreferredSize(new java.awt.Dimension(82, 16));
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    public SDCheckBox() {
        super();
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.FONT = new java.awt.Font("Verdana", 0, 12);
        this.BOX_WIDTH = 28;
        this.BOX_HEIGHT = 14;
        this.BUTTON_SIZE = 10;
        this.BOX_FONT = new java.awt.Font("Verdana", 1, 10);
        this.setPreferredSize(new java.awt.Dimension(82, 16));
        setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        java.awt.RenderingHints rh = g2.getRenderingHints();
        rh
                .put(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        java.awt.image.BufferedImage buttonImage = createButtonImage(getText());

        if (!enabled) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                    0.5f));
        }
        g2.drawImage(buttonImage, 0, (getHeight() - BOX_HEIGHT) / 2, this);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createButtonImage(String text) {
        java.awt.image.BufferedImage buttonImage = new java.awt.image.BufferedImage(getWidth(),
                getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = buttonImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0, BOX_HEIGHT);
        final java.awt.geom.Point2D FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                BOX_HEIGHT - 1);
        final java.awt.geom.Point2D HIGHLIGHT_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(0, BOX_HEIGHT
                - (BOX_HEIGHT / 2.2f));

        final java.awt.geom.Point2D BUTTON_BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 3);
        final java.awt.geom.Point2D BUTTON_BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                BOX_HEIGHT - 3);
        final java.awt.geom.Point2D BUTTON_FOREGROUND_START = new java.awt.geom.Point2D.Float(0, 4);
        final java.awt.geom.Point2D BUTTON_FOREGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                BOX_HEIGHT - 4);
        final java.awt.geom.Point2D BUTTON_HIGHLIGHT_START = new java.awt.geom.Point2D.Float(0, 1);
        final java.awt.geom.Point2D BUTTON_HIGHLIGHT_STOP = new java.awt.geom.Point2D.Float(0,
                BUTTON_SIZE - (BUTTON_SIZE / 2.2f));

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
            if (isSelected()) {
                COLORS_FOREGROUND = new java.awt.Color[] {
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark(),
                        de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight() };
            } else if (hasFocus()) {
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

        final float[] FRACTIONS_BUTTON_BACKGROUND = { 0.0f, 0.33f };

        final float[] FRACTIONS_BUTTON_FOREGROUND = { 0.0f, 1.0f };

        final float[] FRACTIONS_BUTTON_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BUTTON_BACKGROUND;
        final java.awt.Color[] COLORS_BUTTON_FOREGROUND;
        final java.awt.Color[] COLORS_BUTTON_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };

        if (enabled) {
            COLORS_BUTTON_BACKGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_BACKGROUND_ENABLED
                    .getGradientLightDark();
            COLORS_BUTTON_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_ENABLED
                    .getGradientDarkLight();
        } else {
            COLORS_BUTTON_BACKGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_BACKGROUND_DISABLED
                    .getGradientLightDark();
            COLORS_BUTTON_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_DISABLED
                    .getGradientDarkLight();
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
                HIGHLIGHT_START, HIGHLIGHT_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(paintHighlight);
        g2.fill(getHighlightShape());

        // Draw selection shape
        g2.setPaint(new java.awt.LinearGradientPaint(BUTTON_BACKGROUND_START,
                BUTTON_BACKGROUND_STOP, FRACTIONS_BUTTON_BACKGROUND, COLORS_BUTTON_BACKGROUND));
        g2.fill(getButtonBackgroundShape());
        g2.setPaint(new java.awt.LinearGradientPaint(BUTTON_FOREGROUND_START,
                BUTTON_FOREGROUND_STOP, FRACTIONS_BUTTON_FOREGROUND, COLORS_BUTTON_FOREGROUND));
        g2.fill(getButtonForegroundShape());
        g2.setPaint(new java.awt.LinearGradientPaint(BUTTON_HIGHLIGHT_START, BUTTON_HIGHLIGHT_STOP,
                FRACTIONS_BUTTON_HIGHLIGHT, COLORS_BUTTON_HIGHLIGHT));
        g2.fill(getButtonHighlightShape());

        // Draw 0 or 1
        g2.setFont(BOX_FONT);
        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f));
        if (isSelected()) {
            g2.drawString("1", 17, 10);
        } else {
            g2.drawString("0", 5, 10);
        }

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(
                    (float) getBackgroundShape().getBounds().getCenterX(),
                    (float) getBackgroundShape().getBounds().getCenterY());
            final float[] DIST = { 0.0f, 1.0f };

            final java.awt.Color[] COLORS = { fadingColor,
                    new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };
            int radius = getHeight() / 2;
            java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center,
                    radius, DIST, COLORS);
            g2.setPaint(gradient);
            g2.fill(getBackgroundShape());
        }

        // Draw text
        g2.setFont(FONT);
        java.awt.FontMetrics metrics = g2.getFontMetrics();

        if (enabled) {
            g2.setColor(java.awt.Color.WHITE);
        } else {
            g2.setColor(java.awt.Color.LIGHT_GRAY);
        }
        g2.drawString(text, 2 + BOX_WIDTH + 4,
                (int) (getHeight() - metrics.getStringBounds(text, g2).getHeight()
                        + metrics.getHeight() / 2.0f + metrics.getDescent() / 2.0f));
        g2.dispose();

        return buttonImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape() {
        return new java.awt.geom.RoundRectangle2D.Float(0, 0, BOX_WIDTH, BOX_HEIGHT,
                getBackgroundRadius(), getBackgroundRadius());
    }

    private java.awt.Shape getForegroundShape() {
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        return new java.awt.geom.RoundRectangle2D.Float(1, 1, BOX_WIDTH - 2, BOX_HEIGHT - 2,
                foregroundRadius, foregroundRadius);
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape highlightShape = getForegroundShape();
        java.awt.Shape rectHighlight = new java.awt.geom.Rectangle2D.Float(1, BOX_HEIGHT
                - (BOX_HEIGHT / 2.2f), BOX_WIDTH - 2, BOX_HEIGHT - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(highlightShape);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
    }

    private java.awt.Shape getButtonBackgroundShape() {
        if (isSelected()) {
            return new java.awt.geom.Ellipse2D.Float(2, 2, BUTTON_SIZE, BUTTON_SIZE);
        } else {
            return new java.awt.geom.Ellipse2D.Float(16, 2, BUTTON_SIZE, BUTTON_SIZE);
        }
    }

    private java.awt.Shape getButtonForegroundShape() {
        if (isSelected()) {
            return new java.awt.geom.Ellipse2D.Float(3, 3, BUTTON_SIZE - 2, BUTTON_SIZE - 2);
        } else {
            return new java.awt.geom.Ellipse2D.Float(17, 3, BUTTON_SIZE - 2, BUTTON_SIZE - 2);
        }
    }

    private java.awt.Shape getButtonHighlightShape() {
        java.awt.Shape highlightShape = getButtonForegroundShape();
        java.awt.Shape rectHighlight = new java.awt.geom.Rectangle2D.Float(1, BOX_HEIGHT
                - (BOX_HEIGHT / 2.2f), BOX_WIDTH - 2, BOX_HEIGHT - 2);
        java.awt.geom.Area shapeHighlight = new java.awt.geom.Area(highlightShape);
        shapeHighlight.subtract(new java.awt.geom.Area(rectHighlight));
        return shapeHighlight;
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

    }

    @Override
    public void mouseReleased(java.awt.event.MouseEvent event) {

    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent event) {
        // Start HighlightAnimation
        createFadingAnimation(true).start();
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
        return "SDCheckBox";
    }
}
