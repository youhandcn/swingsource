/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.buttons;

/**
 * 
 * @author hansolo
 */
public class SDArrowButton extends javax.swing.plaf.basic.BasicArrowButton implements
        java.awt.event.MouseListener, javax.swing.SwingConstants {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean armed;
    boolean enabled;
    private int orientation;
    private volatile java.awt.Color fadingColor;
    private Thread fader;
    private de.jugms.sd.tools.RadiusType radiusType;

    public SDArrowButton(int direction) {
        this(direction, EAST);
    }

    public SDArrowButton(int direction, int orientation) {
        super(direction);
        fader = null;
        setOpaque(false);
        addMouseListener(this);
        fadingColor = new java.awt.Color(255, 255, 255, 0);
        this.enabled = true;
        this.orientation = orientation;
        setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
    }

    @Override
    public void paint(java.awt.Graphics g) {
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

        final java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Float(0, 0);
        final java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Float(0,
                getHeight());

        java.awt.geom.Point2D startBackground = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stopBackground;

        switch (orientation) {
        case EAST:
            stopBackground = new java.awt.geom.Point2D.Float(0, getHeight());
            break;

        case WEST:
            stopBackground = new java.awt.geom.Point2D.Float(0, getHeight());
            break;

        case NORTH:
            stopBackground = new java.awt.geom.Point2D.Float(0, getWidth());
            break;

        case SOUTH:
            stopBackground = new java.awt.geom.Point2D.Float(0, getWidth());
            break;

        default:
            stopBackground = new java.awt.geom.Point2D.Float(0, getHeight());
            break;
        }

        final float[] FRACTIONS_BACKGROUND = { 0.0f, 0.15f, 0.85f, 1.0f };

        final float[] FRACTIONS_FOREGROUND = { 0.0f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 1.0f };

        final java.awt.Color[] COLORS_BACKGROUND = { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f) };
        final java.awt.Color[] COLORS_FOREGROUND;
        final java.awt.Color[] COLORS_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.4f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.2f) };

        if (armed) {
            COLORS_FOREGROUND = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                    .getGradientDarkLight();
        } else {
            if (enabled) {
                COLORS_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_ENABLED
                        .getGradientDarkLight();
            } else {
                COLORS_FOREGROUND = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_DISABLED
                        .getGradientDarkLight();
            }
        }

        // Draw background shape
        java.awt.LinearGradientPaint paintBackground = new java.awt.LinearGradientPaint(
                startBackground, stopBackground, FRACTIONS_BACKGROUND, COLORS_BACKGROUND);
        g2.setPaint(paintBackground);
        g2.fill(getBackgroundShape());

        // Draw foreground shape
        java.awt.LinearGradientPaint paintForeground = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_FOREGROUND, COLORS_FOREGROUND);
        g2.setPaint(paintForeground);
        g2.fill(getForegroundShape());

        // Draw highlight shape
        java.awt.LinearGradientPaint paintHighlight = new java.awt.LinearGradientPaint(
                BACKGROUND_START, BACKGROUND_STOP, FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        g2.setPaint(paintHighlight);
        g2.fill(getHighlightShape());

        // DrawMouseOverHighlight
        if (enabled) {
            java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float((getWidth() / 2),
                    (getHeight() / 2));
            float[] dist = { 0.0f, 1.0f };
            java.awt.Color[] colors = { fadingColor, new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };

            int radius;
            switch (orientation) {
            case EAST:
                radius = getWidth() / 2;
                break;
            case WEST:
                radius = getWidth() / 2;
                break;
            case NORTH:
                radius = getHeight() / 2;
                break;
            case SOUTH:
                radius = getHeight() / 2;
                break;
            default:
                radius = getWidth() / 2;
                break;
            }

            java.awt.RadialGradientPaint gradient = new java.awt.RadialGradientPaint(center,
                    radius, dist, colors);
            g2.setPaint(gradient);
            g2.fill(getBackgroundShape());
        }

        // Draw arrow
        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.8f));
        g2.fill(getArrowShape());

        g2.dispose();

        return buttonImage;
    }

    // Define the new shape for component
    private java.awt.Shape getBackgroundShape() {
        java.awt.Shape rrect;
        java.awt.Shape rect;
        java.awt.geom.Area shapeArea;
        switch (orientation) {
        case EAST:
            rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    getBackgroundRadius(), getBackgroundRadius());
            rect = new java.awt.geom.Rectangle2D.Float(0, 0, getWidth() / 2, getHeight());
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case WEST:
            rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    getBackgroundRadius(), getBackgroundRadius());
            rect = new java.awt.geom.Rectangle2D.Float(getWidth() / 2, 0, getWidth() / 2,
                    getHeight());
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case NORTH:
            rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    getBackgroundRadius(), getBackgroundRadius());
            rect = new java.awt.geom.Rectangle2D.Float(0, getHeight() / 2, getWidth(),
                    getHeight() / 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case SOUTH:
            rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    getBackgroundRadius(), getBackgroundRadius());
            rect = new java.awt.geom.Rectangle2D.Float(0, 0, getWidth(), getHeight() / 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        default:
            rrect = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    getHeight(), getHeight());
            rect = new java.awt.geom.Rectangle2D.Float(0, 0, getWidth() / 2, getHeight());
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        }

        return shapeArea;
    }

    private java.awt.Shape getForegroundShape() {
        java.awt.Shape rrect;
        java.awt.Shape rect;
        java.awt.geom.Area shapeArea;
        float foregroundRadius = getBackgroundRadius() - 1.0f;
        switch (orientation) {
        case EAST:
            rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                    foregroundRadius, foregroundRadius);
            rect = new java.awt.geom.Rectangle2D.Float(1, 1, (getWidth() - 2) / 2, getHeight() - 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case WEST:
            rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                    foregroundRadius, foregroundRadius);
            rect = new java.awt.geom.Rectangle2D.Float((getWidth() - 2) / 2, 1,
                    (getWidth() - 2) / 2, getHeight() - 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case NORTH:
            rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                    foregroundRadius, foregroundRadius);
            rect = new java.awt.geom.Rectangle2D.Float(1, (getHeight()) / 2, getWidth() - 2,
                    (getHeight()) / 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        case SOUTH:
            rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                    foregroundRadius, foregroundRadius);
            rect = new java.awt.geom.Rectangle2D.Float(1, 1, getWidth() - 2, (getHeight() - 2) / 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        default:
            rrect = new java.awt.geom.RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2,
                    foregroundRadius, foregroundRadius);
            rect = new java.awt.geom.Rectangle2D.Float(1, 1, (getWidth() - 2) / 2, getHeight() - 2);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.add(new java.awt.geom.Area(rect));
            break;
        }

        return shapeArea;
    }

    private java.awt.Shape getHighlightShape() {
        java.awt.Shape rrect;
        java.awt.Shape rect;
        java.awt.geom.Area shapeArea;

        switch (orientation) {
        case EAST:
            rrect = getForegroundShape();
            rect = new java.awt.geom.Rectangle2D.Float(1, getHeight() - (getHeight() / 2.2f),
                    getWidth() - 2, getHeight() - 1);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.subtract(new java.awt.geom.Area(rect));
            break;
        case WEST:
            rrect = getForegroundShape();
            rect = new java.awt.geom.Rectangle2D.Float(1, getHeight() - (getHeight() / 2.2f),
                    getWidth() - 2, getHeight() - 1);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.subtract(new java.awt.geom.Area(rect));
            break;
        case NORTH:
            rrect = getForegroundShape();
            rect = new java.awt.geom.Rectangle2D.Float(1, getHeight() - (getHeight() / 2.2f),
                    getWidth() - 2, getHeight() - 1);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.subtract(new java.awt.geom.Area(rect));
            break;
        case SOUTH:
            rrect = getForegroundShape();
            rect = new java.awt.geom.Rectangle2D.Float(1, getHeight() - (getHeight() / 2.2f),
                    getWidth() - 2, getHeight() - 1);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.subtract(new java.awt.geom.Area(rect));
            break;
        default:
            rrect = getForegroundShape();
            rect = new java.awt.geom.Rectangle2D.Float(1, getHeight() - (getHeight() / 2.2f),
                    getWidth() - 2, getHeight() - 1);
            shapeArea = new java.awt.geom.Area(rrect);
            shapeArea.subtract(new java.awt.geom.Area(rect));
            break;
        }

        return shapeArea;
    }

    private java.awt.Shape getArrowShape() {
        java.awt.geom.GeneralPath arrow;
        float arrowWidth;
        float arrowHeight;
        float originX;
        float originY;

        switch (getDirection()) {
        case EAST:
            arrow = new java.awt.geom.GeneralPath();
            arrowWidth = getHeight() / 2.4f;
            arrowHeight = arrowWidth;
            originX = (getWidth() - arrowHeight) / 2;
            originY = (getHeight() - arrowWidth) / 2;
            arrow.moveTo(originX, originY);
            arrow.lineTo(originX, originY + arrowWidth);
            arrow.lineTo(getWidth() / 2 + arrowHeight / 2, getHeight() / 2);
            arrow.closePath();
            break;

        case WEST:
            arrow = new java.awt.geom.GeneralPath();
            arrowWidth = getHeight() / 2.4f;
            arrowHeight = arrowWidth;
            originX = (getWidth() + arrowHeight) / 2;
            originY = (getHeight() - arrowWidth) / 2;
            arrow.moveTo(originX, originY);
            arrow.lineTo(originX, originY + arrowWidth);
            arrow.lineTo(getWidth() / 2 - arrowHeight / 2, getHeight() / 2);
            arrow.closePath();
            break;

        case NORTH:
            arrow = new java.awt.geom.GeneralPath();
            arrowWidth = getWidth() / 2.4f;
            arrowHeight = arrowWidth;
            originX = (getWidth() - arrowWidth) / 2;
            originY = (getHeight() / 2 + arrowHeight / 2);
            arrow.moveTo(originX, originY);
            arrow.lineTo(originX + arrowWidth, originY);
            arrow.lineTo((getWidth() / 2), getHeight() / 2 - arrowHeight / 2);
            arrow.closePath();
            break;

        case SOUTH:
            arrow = new java.awt.geom.GeneralPath();
            arrowWidth = getWidth() / 2.4f;
            arrowHeight = arrowWidth;
            originX = (getWidth() - arrowWidth) / 2;
            originY = (getHeight() / 2 - arrowHeight / 2);
            arrow.moveTo(originX, originY);
            arrow.lineTo(originX + arrowWidth, originY);
            arrow.lineTo((getWidth() / 2), getHeight() / 2 + arrowHeight / 2);
            arrow.closePath();
            break;

        default:
            arrow = new java.awt.geom.GeneralPath();
            arrowWidth = getWidth() / 2;
            arrowHeight = arrowWidth;
            originX = (getWidth() - arrowWidth) / 2;
            originY = (getHeight() / 2 - arrowHeight / 2);
            arrow.moveTo(originX, originY);
            arrow.lineTo(originX + arrowWidth, originY);
            arrow.lineTo((getWidth() / 2), getHeight() / 2 + arrowHeight / 2);
            arrow.closePath();
            break;
        }

        return arrow;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
        repaint();
    }

    public boolean isArmed() {
        return this.armed;
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
        armed = false;
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

        return fader;
    }

    private float getBackgroundRadius() {
        switch (this.radiusType) {
        case SHARP:
            return (0.0f);
        case ROUNDED:
            return (10.0f);
        case PILL:
            switch (orientation) {
            case EAST:
                return (getHeight());
            case WEST:
                return (getHeight());
            case NORTH:
                return (getHeight());
            case SOUTH:
                return (getHeight());
            default:
                return (getHeight());
            }
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
        return "SDArrowButton";
    }
}
