/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jugms.sd.components.panels;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 
 * @author hansolo
 */
public class SDGlassPane extends JComponent {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean disabled = false;
    private Thread pulser = null;
    private volatile static java.awt.Color fadingColor = new java.awt.Color(0, 0, 0, 200);
    private volatile static boolean backgroundDark = false;

    public enum MARKER_TYPE {
        RED_TRIANGLE, YELLOW_TRIANGLE, RED_CIRCLE, YELLOW_FLASH
    };

    private static final int MARKER_SIZE = 15;
    private static final int HALF_MARKER_SIZE = MARKER_SIZE / 2;
    private static final int CHECK_MARKER_SIZE = 15;
    private static final int HALF_CHECK_MARKER_SIZE = CHECK_MARKER_SIZE / 2;
    private static final int OK_MARKER_WIDTH = 22;
    private static final int OK_MARKER_HEIGHT = 18;
    private static final Stroke BIG_STROKE = new BasicStroke(2.0f);
    private static final Stroke MEDIUM_STROKE = new BasicStroke(1.5f);
    private static final Stroke THIN_STROKE = new BasicStroke(1.0f);
    private final BufferedImage MARKER_IMAGE;
    private BufferedImage checkMarkerImage = createCheckMarkImage();
    private BufferedImage okMarkerImage = createOkMarkerImage();
    private static final int ALPHA = 180;
    private static final int LOGO_ALPHA = 255;
    private static float currentLogoAlpha = 1.0f;
    private static float currentLogoScale = 0.7f;
    private static float currentGlowAlpha = 0.0f;
    private Set<JComponent> markerComponentSet = new HashSet<JComponent>();
    private Set<JComponent> checkMarkerComponentSet = new HashSet<JComponent>();
    private Set<JComponent> okMarkerComponentSet = new HashSet<JComponent>();
    private Map<JComponent, Thread> removerThreadMap = new HashMap<JComponent, Thread>();
    private ConcurrentHashMap<JComponent, Float> checkMarkerAlphaMap = new ConcurrentHashMap<JComponent, Float>();
    private AlphaComposite alphaComp;
    private AlphaComposite stdComposite;
    private final RenderingHints HINTS;
    private final BufferedImage LOGO_IMAGE;
    private final BufferedImage REFLECTION_IMAGE;
    private final BufferedImage GLOW_IMAGE;
    private final BufferedImage REFLECTION_GLOW_IMAGE;
    private float currentAlpha = 0.9f;

    public SDGlassPane() {
        this(MARKER_TYPE.YELLOW_TRIANGLE);
    }

    public SDGlassPane(MARKER_TYPE markerType) {
        HINTS = new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
                RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        HINTS.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        switch (markerType) {
        case YELLOW_TRIANGLE:
            MARKER_IMAGE = createAttentionMarker(MARKER_TYPE.YELLOW_TRIANGLE);
            break;
        case RED_TRIANGLE:
            MARKER_IMAGE = createAttentionMarker(MARKER_TYPE.RED_TRIANGLE);
            break;
        case RED_CIRCLE:
            MARKER_IMAGE = createAttentionMarker(MARKER_TYPE.RED_CIRCLE);
            break;
        case YELLOW_FLASH:
            MARKER_IMAGE = createAttentionMarker(MARKER_TYPE.YELLOW_FLASH);
            break;
        default:
            MARKER_IMAGE = createAttentionMarker(MARKER_TYPE.YELLOW_TRIANGLE);
            break;
        }

        this.LOGO_IMAGE = createLogoImage();
        this.REFLECTION_IMAGE = createReflectionImage(LOGO_IMAGE);
        this.GLOW_IMAGE = createGlowImage();
        this.REFLECTION_GLOW_IMAGE = createReflectionImage(GLOW_IMAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        // Draw all AttentionMarker
        drawAttentionMarker(g2);

        // Draw all CheckMarker
        drawCheckMarker(g2);

        // Draw all OK-Marker
        drawOkMarker(g2);

        // Disabled
        if (disabled) {
            drawDisabled(g2);
        }

        if (backgroundDark && !disabled) {
            drawDisabled(g2);
        }

        g2.dispose();
    }

    private void drawAttentionMarker(Graphics2D g2) {
        JComponent tmpComponent;

        // Draw all Marker
        for (JComponent component : this.markerComponentSet) {
            int originX = 0;
            int originY = 0;
            tmpComponent = component;

            while (tmpComponent.getParent() != null) {
                if (tmpComponent.getParent() instanceof JFrame) {
                    break;
                }
                originX += tmpComponent.getX();
                originY += tmpComponent.getY();
                tmpComponent = (JComponent) tmpComponent.getParent();
            }

            g2.drawImage(MARKER_IMAGE, (originX - HALF_MARKER_SIZE), (originY - HALF_MARKER_SIZE),
                    null);
        }
    }

    private void drawCheckMarker(Graphics2D g2) {
        JComponent tmpComponent;
        stdComposite = (AlphaComposite) g2.getComposite();
        for (JComponent component : checkMarkerComponentSet) {
            int originX = 0;
            int originY = 0;
            float tmpAlpha;
            tmpComponent = component;
            while (tmpComponent.getParent() != null) {
                if (tmpComponent.getParent() instanceof JFrame) {
                    break;
                }
                originX += tmpComponent.getX();
                originY += tmpComponent.getY();
                tmpComponent = (JComponent) tmpComponent.getParent();
            }

            // Set the opacity for each CheckMarker
            tmpAlpha = currentAlpha;
            alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentAlpha);
            for (JComponent comp : checkMarkerAlphaMap.keySet()) {
                if (comp.equals(component)) {
                    if (component != null)
                        tmpAlpha = checkMarkerAlphaMap.get(component);
                    else
                        tmpAlpha = 1.0f;
                }
            }

            alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tmpAlpha);

            g2.setComposite(alphaComp);

            g2.drawImage(checkMarkerImage, (originX - HALF_CHECK_MARKER_SIZE),
                    (originY - HALF_CHECK_MARKER_SIZE), null);
        }
        // If there are no checkmarkers anymore
        // we remove the glasspane from the GlobalAnimationTimer
        if (checkMarkerComponentSet.isEmpty()) {
            de.jugms.sd.tools.SDGlobalAnimationTimer.INSTANCE.removeComponent(this);
        }
        g2.setComposite(stdComposite);
    }

    private void drawOkMarker(Graphics2D g2) {
        JComponent tmpComponent;
        for (JComponent component : this.okMarkerComponentSet) {
            int originX = 0;
            int originY = 0;
            tmpComponent = component;
            while (tmpComponent.getParent() != null) {
                if (tmpComponent.getParent() instanceof JFrame) {
                    break;
                }
                originX += tmpComponent.getX();
                originY += tmpComponent.getY();
                tmpComponent = (JComponent) tmpComponent.getParent();
            }

            g2.drawImage(okMarkerImage, (originX - OK_MARKER_WIDTH + component.getWidth()),
                    (originY - OK_MARKER_HEIGHT), null);
        }
    }

    private void drawDisabled(Graphics2D g2) {
        // Faded background
        g2.setColor(fadingColor);
        g2.fillRect(0, 0, getParent().getWidth(), getParent().getHeight());

        // JUG-LOGO
        int posX = (getWidth() - (int) (LOGO_IMAGE.getWidth() * currentLogoScale)) / 2;
        int posY = (getHeight() - ((int) (LOGO_IMAGE.getHeight() * currentLogoScale) + (int) ((int) (REFLECTION_IMAGE
                .getHeight() * currentLogoScale) * 0.3f))) / 2;
        int newW = (int) (LOGO_IMAGE.getWidth() * currentLogoScale);
        int newH = (int) (LOGO_IMAGE.getHeight() * currentLogoScale);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentGlowAlpha));
        g2.drawImage(GLOW_IMAGE, posX, posY, newW, newH, null);
        g2.drawImage(REFLECTION_GLOW_IMAGE, posX, posY
                + (int) (LOGO_IMAGE.getHeight() * currentLogoScale) + 2, newW, newH, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currentLogoAlpha));
        g2.drawImage(LOGO_IMAGE, posX, posY, newW, newH, null);
        g2.drawImage(REFLECTION_IMAGE, posX, posY
                + (int) (LOGO_IMAGE.getHeight() * currentLogoScale) + 2, newW, newH, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

    public void addMarker(JComponent component) {
        markerComponentSet.add(component);
        if (removerThreadMap.containsKey(component)) {
            removerThreadMap.get(component).interrupt();
        }
        repaint();
    }

    public void removeMarker(JComponent component) {
        if (markerComponentSet.contains(component)) {
            markerComponentSet.remove(component);
            checkMarkerComponentSet.add(component);
            checkMarkerAlphaMap.put(component, currentAlpha);
            de.jugms.sd.tools.SDGlobalAnimationTimer.INSTANCE.addComponent(this);
            createCheckMarkerRemover(component).start();
        }
    }

    public void removeMarkerWithoutCheckMarker(JComponent component) {
        if (markerComponentSet.contains(component)) {
            this.markerComponentSet.remove(component);
            repaint();
        }
    }

    private void removeCheckMarker(JComponent component) {
        if (checkMarkerComponentSet.contains(component)) {
            this.checkMarkerComponentSet.remove(component);
            checkMarkerAlphaMap.remove(component);
            repaint();
        }
    }

    public boolean hasMarker(JComponent component) {
        return markerComponentSet.contains(component);
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;

        // Fading Thread starten
        createFadingAnimation(disabled).start();

        // Pulsating Thread starten wenn disabled
        if (disabled) {
            SwingUtilities.invokeLater(new java.lang.Runnable() {

                @Override
                public void run() {
                    createPulsatingAnimation().start();
                }
            });
        }
    }

    public boolean isEverythingOK() {
        return this.markerComponentSet.isEmpty();
    }

    public void addOkMarker(JComponent component) {
        this.okMarkerComponentSet.add(component);
    }

    public void removeOkMarker(JComponent component) {
        this.okMarkerComponentSet.remove(component);
        repaint();
    }

    public void resetGlassPane() {
        markerComponentSet.clear();
        checkMarkerComponentSet.clear();
        okMarkerComponentSet.clear();
        repaint();
    }

    public void refresh() {
        checkMarkerImage = createCheckMarkImage();
        okMarkerImage = createOkMarkerImage();
    }

    private BufferedImage createAttentionMarker(MARKER_TYPE type) {
        BufferedImage markerImage = new BufferedImage(MARKER_SIZE + 4, MARKER_SIZE + 4,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) markerImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point origin = new Point(0, 0);
        GeneralPath shape;
        Color background;
        Color frame;
        Color light;
        Color dark;
        GradientPaint gradientBackground;
        RadialGradientPaint gradientShadow;

        switch (type) {
        case RED_TRIANGLE:
            shape = new GeneralPath();
            shape.moveTo(origin.x + MARKER_SIZE / 2, origin.y);
            shape.lineTo(origin.x + MARKER_SIZE - 1, origin.y + MARKER_SIZE - 1);
            shape.lineTo(origin.x, origin.y + MARKER_SIZE - 1);
            shape.closePath();

            background = new Color(255, 255, 255, ALPHA);
            frame = new Color(255, 0, 0, ALPHA);

            // Marker
            g2.setColor(background);
            g2.fill(shape);
            g2.setColor(frame);
            g2.setStroke(MEDIUM_STROKE);
            g2.draw(shape);
            g2.setStroke(THIN_STROKE);
            g2.setColor(Color.BLACK);
            g2.drawLine(origin.x + MARKER_SIZE / 2, origin.y + 4, origin.x + MARKER_SIZE / 2,
                    origin.y + MARKER_SIZE * 3 / 4 - 2);
            g2.drawLine(origin.x + MARKER_SIZE / 2, origin.y + MARKER_SIZE * 3 / 4 + 1, origin.x
                    + MARKER_SIZE / 2, origin.y + MARKER_SIZE - 4);
            break;

        case YELLOW_TRIANGLE:
            GeneralPath triangle = new GeneralPath();
            triangle.moveTo(origin.x + MARKER_SIZE / 2, origin.y);
            triangle.lineTo(origin.x + MARKER_SIZE - 1, origin.y + MARKER_SIZE - 1);
            triangle.lineTo(origin.x, origin.y + MARKER_SIZE - 1);
            triangle.closePath();

            background = new Color(255, 255, 0, ALPHA);
            frame = new Color(150, 150, 150, ALPHA);

            g2.setColor(background);
            g2.fill(triangle);
            g2.setColor(frame);
            g2.draw(triangle);
            g2.setColor(Color.BLACK);
            g2.drawLine(origin.x + MARKER_SIZE / 2, origin.y + 4, origin.x + MARKER_SIZE / 2,
                    origin.y + MARKER_SIZE * 3 / 4 - 2);
            g2.drawLine(origin.x + MARKER_SIZE / 2, origin.y + MARKER_SIZE * 3 / 4 + 1, origin.x
                    + MARKER_SIZE / 2, origin.y + MARKER_SIZE - 4);
            break;

        case RED_CIRCLE:
            light = de.jugms.sd.tools.SDHelper.INSTANCE.setAlpha(
                    de.jugms.sd.tools.SDTwoColorGradients.RED.getLight(), 180);
            dark = de.jugms.sd.tools.SDHelper.INSTANCE.setAlpha(
                    de.jugms.sd.tools.SDTwoColorGradients.RED.getDark(), 180);

            gradientBackground = new GradientPaint(new Point(origin.x + 2, origin.y + 2), light,
                    new Point(origin.x + 2, origin.y + 18), dark);
            gradientShadow = new RadialGradientPaint(new Point(origin.x + MARKER_SIZE / 2 + 3,
                    origin.y + MARKER_SIZE / 2 + 3), 7.5f, new float[] { 0.0f, 1.0f }, new Color[] {
                    new Color(0, 0, 0, 255), new Color(0, 0, 0, 20) });

            // Shadow
            g2.setPaint(gradientShadow);
            g2.fillOval(origin.x + 4, origin.y + 6, MARKER_SIZE, MARKER_SIZE);

            // Marker
            g2.setPaint(gradientBackground);
            g2.fillOval(origin.x + 2, origin.y + 2, MARKER_SIZE, MARKER_SIZE);
            g2.setPaint(Color.WHITE);
            g2.setStroke(MEDIUM_STROKE);
            g2.drawOval(origin.x + 1, origin.y + 1, MARKER_SIZE, MARKER_SIZE);

            g2.drawLine(origin.x + 8, origin.y + 5, origin.x + 8, origin.y + 9);
            g2.drawLine(origin.x + 8, origin.y + 12, origin.x + 8, origin.y + 12);
            break;

        case YELLOW_FLASH:
            light = de.jugms.sd.tools.SDHelper.INSTANCE.setAlpha(
                    de.jugms.sd.tools.SDTwoColorGradients.YELLOW.getLight(), 180);
            dark = de.jugms.sd.tools.SDHelper.INSTANCE.setAlpha(
                    de.jugms.sd.tools.SDTwoColorGradients.YELLOW.getDark(), 180);

            gradientBackground = new GradientPaint(new Point(origin.x + 2, origin.y + 2), light,
                    new Point(origin.x + 2, origin.y + 18), dark);
            gradientShadow = new RadialGradientPaint(new Point(origin.x + MARKER_SIZE / 2 + 3,
                    origin.y + MARKER_SIZE / 2 + 3), 7.5f, new float[] { 0.0f, 1.0f }, new Color[] {
                    new Color(0, 0, 0, 255), new Color(0, 0, 0, 20) });

            // Shadow
            g2.setPaint(gradientShadow);
            g2.fillOval(origin.x + 4, origin.y + 6, MARKER_SIZE, MARKER_SIZE);

            // Marker
            g2.setPaint(gradientBackground);
            g2.fillOval(origin.x + 2, origin.y + 2, MARKER_SIZE, MARKER_SIZE);
            g2.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.8f));
            g2.drawOval(origin.x + 2, origin.y + 2, MARKER_SIZE, MARKER_SIZE);
            g2.setStroke(MEDIUM_STROKE);

            GeneralPath flash = new GeneralPath();
            flash.moveTo(origin.x + 9, origin.y + 4);
            flash.lineTo(origin.x + 11, origin.y + 4);
            flash.lineTo(origin.x + 8, origin.y + 8);
            flash.lineTo(origin.x + 13, origin.y + 7);
            flash.lineTo(origin.x + 10, origin.y + 12);
            flash.lineTo(origin.x + 11, origin.y + 8);
            flash.lineTo(origin.x + 7, origin.y + 10);
            flash.lineTo(origin.x + 9, origin.y + 4);

            g2.setColor(Color.BLACK);
            g2.fill(flash);
            g2.draw(flash);
            break;

        default:
            break;
        }

        g2.dispose();

        return markerImage;
    }

    private BufferedImage createCheckMarkImage() {
        BufferedImage markerImage = new BufferedImage(CHECK_MARKER_SIZE + 4, CHECK_MARKER_SIZE + 4,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) markerImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Point origin = new Point(0, 0);

        Color light = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight();
        Color dark = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark();

        GradientPaint gradientBackground = new GradientPaint(new Point(origin.x + 2, origin.y + 2),
                light, new Point(origin.x + 2, origin.y + 18), dark);
        RadialGradientPaint gradientShadow = new RadialGradientPaint(new Point(origin.x
                + CHECK_MARKER_SIZE / 2 + 3, origin.y + CHECK_MARKER_SIZE / 2 + 3), 7.5f,
                new float[] { 0.0f, 1.0f }, new Color[] { new Color(0, 0, 0, 255),
                        new Color(0, 0, 0, 20) });

        // Shadow
        g2.setPaint(gradientShadow);
        g2.fillOval(origin.x + 4, origin.y + 6, CHECK_MARKER_SIZE, CHECK_MARKER_SIZE);

        // Marker
        g2.setPaint(gradientBackground);
        g2.fillOval(origin.x + 2, origin.y + 2, CHECK_MARKER_SIZE, CHECK_MARKER_SIZE);
        g2.setPaint(Color.WHITE);
        g2.setStroke(MEDIUM_STROKE);
        g2.drawOval(origin.x + 1, origin.y + 1, CHECK_MARKER_SIZE, CHECK_MARKER_SIZE);

        g2.drawLine(origin.x + 5, origin.y + 9, origin.x + 8, origin.y + 12);
        g2.drawLine(origin.x + 8, origin.y + 12, origin.x + 12, origin.y + 6);

        g2.dispose();

        return markerImage;
    }

    private BufferedImage createOkMarkerImage() {
        BufferedImage markerImage = new BufferedImage(OK_MARKER_WIDTH * 2, OK_MARKER_HEIGHT * 2,
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) markerImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.translate(OK_MARKER_WIDTH / 2, OK_MARKER_HEIGHT / 2);
        g2.rotate(20 * Math.PI / 180);

        Point origin = new Point(0, 0);

        Color light = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight();
        Color dark = de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark();

        GradientPaint gradientBackground = new GradientPaint(new Point(origin.x + 2, origin.y + 2),
                light, new Point(origin.x + 2, origin.y + 18), dark);

        // Shadow
        for (int i = 6; i > 0; i--) {
            g2.setColor(new Color(0, 0, 0, i * 33));
            g2.fillOval(origin.x + 4, origin.y + 5, OK_MARKER_WIDTH - i, OK_MARKER_HEIGHT - i);
        }

        g2.setPaint(gradientBackground);
        g2.fillOval(origin.x, origin.y, OK_MARKER_WIDTH, OK_MARKER_HEIGHT);
        g2.setColor(Color.WHITE);
        g2.setStroke(BIG_STROKE);
        g2.drawOval(origin.x, origin.y, OK_MARKER_WIDTH, OK_MARKER_HEIGHT);

        g2.setFont(new Font("Arial", 1, 11));
        FontMetrics metrics = g2.getFontMetrics();
        Rectangle2D textRect = metrics.getStringBounds("OK", g2);
        g2.drawString("OK", (int) (OK_MARKER_WIDTH - textRect.getWidth() / 2)
                - (OK_MARKER_WIDTH / 2) + 1, (int) ((OK_MARKER_HEIGHT - textRect.getHeight()) / 2
                + metrics.getHeight() - 2));

        g2.dispose();

        return markerImage;
    }

    private BufferedImage createLogoImage() {
        BufferedImage jugImage = new BufferedImage(460 + 40, 267 + 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = jugImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHints(HINTS);

        g2.translate(20, 20);

        Point2D start = new Point2D.Float(0, 102);
        Point2D stop = new Point2D.Float(0, 267);

        final float[] FRACTIONS = { 0.0f, 1.0f };

        final Color[] COLORS = { new Color(143, 210, 0, LOGO_ALPHA),
                new Color(56, 172, 0, LOGO_ALPHA) };

        LinearGradientPaint jugGradient = new LinearGradientPaint(start, stop, FRACTIONS, COLORS);

        GeneralPath j = new GeneralPath();
        j.moveTo(132, 102);
        j.quadTo(97, 102, 97, 136);
        j.lineTo(97, 184);
        j.quadTo(93, 229, 48, 234);
        j.lineTo(32, 234);
        j.quadTo(-1, 234, 0, 267);
        j.lineTo(48, 267);
        j.quadTo(127, 263, 132, 184);
        j.lineTo(132, 102);

        GeneralPath ug = new GeneralPath();
        ug.moveTo(139, 102);
        ug.lineTo(139, 184);
        ug.quadTo(142, 265, 223, 267);
        ug.quadTo(282, 265, 299, 217);
        ug.quadTo(318, 265, 376, 267);
        ug.quadTo(449, 268, 456, 197);
        ug.lineTo(460, 149);
        ug.quadTo(425, 150, 425, 184);
        ug.quadTo(423, 231, 376, 234);
        ug.quadTo(330, 231, 328, 184);
        ug.quadTo(330, 137, 376, 135);
        ug.lineTo(393, 135);
        ug.quadTo(425, 135, 425, 102);
        ug.lineTo(376, 102);
        ug.quadTo(318, 104, 306, 140);
        ug.lineTo(306, 102);
        ug.quadTo(272, 102, 272, 136);
        ug.lineTo(272, 184);
        ug.quadTo(269, 231, 223, 234);
        ug.quadTo(176, 231, 174, 184);
        ug.lineTo(174, 136);
        ug.quadTo(174, 102, 139, 102);

        GeneralPath beanLeft = new GeneralPath();
        beanLeft.moveTo(220, 117);
        beanLeft.lineTo(214, 117);
        beanLeft.quadTo(189, 122, 186, 146);
        beanLeft.lineTo(186, 192);
        beanLeft.quadTo(189, 216, 214, 222);
        beanLeft.lineTo(220, 222);
        beanLeft.lineTo(220, 117);

        GeneralPath beanRight = new GeneralPath();
        beanRight.moveTo(227, 117);
        beanRight.lineTo(227, 222);
        beanRight.lineTo(233, 222);
        beanRight.quadTo(257, 216, 261, 192);
        beanRight.lineTo(261, 146);
        beanRight.quadTo(258, 122, 233, 117);
        beanRight.lineTo(227, 117);

        GeneralPath m = new GeneralPath();
        m.moveTo(228, 0);
        m.quadTo(222, 14, 227, 28);
        m.quadTo(240, 50, 242, 69);
        m.quadTo(243, 94, 218, 107);
        m.quadTo(240, 82, 228, 55);
        m.lineTo(219, 42);
        m.lineTo(213, 55);
        m.lineTo(216, 62);
        m.quadTo(218, 71, 210, 82);
        m.quadTo(206, 88, 197, 95);
        m.quadTo(205, 84, 208, 72);
        m.quadTo(207, 62, 201, 55);
        m.lineTo(192, 45);
        m.quadTo(181, 72, 169, 83);
        m.quadTo(179, 72, 190, 41);
        m.quadTo(189, 32, 200, 16);
        m.quadTo(194, 29, 202, 40);
        m.lineTo(212, 53);
        m.lineTo(217, 38);
        m.lineTo(214, 30);
        m.quadTo(213, 24, 213, 19);
        m.quadTo(215, 7, 228, 0);

        GeneralPath s = new GeneralPath();
        s.moveTo(258, 12);
        s.quadTo(239, 18, 244, 36);
        s.quadTo(246, 40, 254, 52);
        s.quadTo(259, 60, 260, 70);
        s.quadTo(260, 82, 252, 93);
        s.quadTo(268, 79, 267, 63);
        s.quadTo(264, 51, 258, 44);
        s.quadTo(252, 37, 250, 28);
        s.quadTo(250, 21, 258, 12);

        g2.setPaint(jugGradient);
        g2.fill(j);
        g2.fill(ug);

        g2.setColor(new Color(106, 46, 17, LOGO_ALPHA));
        g2.fill(beanLeft);
        g2.fill(beanRight);

        g2.setColor(new Color(232, 53, 26, LOGO_ALPHA));
        g2.fill(m);
        g2.fill(s);

        return jugImage;
    }

    private BufferedImage createReflectionImage(BufferedImage sourceImage) {
        float opacity = 0.5f;
        float fadeHeight = 0.7f;

        BufferedImage reflectionImage = new BufferedImage(sourceImage.getWidth(), sourceImage
                .getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = (Graphics2D) reflectionImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2.translate(0, sourceImage.getHeight());
        g2.scale(1, -1);
        g2.drawRenderedImage(sourceImage, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
        g2
                .setPaint(new GradientPaint(0, sourceImage.getHeight() * fadeHeight, new Color(
                        0.0f, 0.0f, 0.0f, 0.0f), 0, sourceImage.getHeight(), new Color(0.0f, 0.0f,
                        0.0f, opacity)));
        g2.fillRect(0, 0, sourceImage.getWidth(), sourceImage.getHeight());
        g2.dispose();

        return reflectionImage;
    }

    private BufferedImage createGlowImage() {
        BufferedImage jugImage = new BufferedImage(460 + 40, 267 + 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = jugImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHints(HINTS);

        g2.translate(20, 20);

        // JUG Logo

        GeneralPath j = new GeneralPath();
        j.moveTo(132, 102);
        j.quadTo(97, 102, 97, 136);
        j.lineTo(97, 184);
        j.quadTo(93, 229, 48, 234);
        j.lineTo(32, 234);
        j.quadTo(-1, 234, 0, 267);
        j.lineTo(48, 267);
        j.quadTo(127, 263, 132, 184);
        j.lineTo(132, 102);

        GeneralPath ug = new GeneralPath();
        ug.moveTo(139, 102);
        ug.lineTo(139, 184);
        ug.quadTo(142, 265, 223, 267);
        ug.quadTo(282, 265, 299, 217);
        ug.quadTo(318, 265, 376, 267);
        ug.quadTo(449, 268, 456, 197);
        ug.lineTo(460, 149);
        ug.quadTo(425, 150, 425, 184);
        ug.quadTo(423, 231, 376, 234);
        ug.quadTo(330, 231, 328, 184);
        ug.quadTo(330, 137, 376, 135);
        ug.lineTo(393, 135);
        ug.quadTo(425, 135, 425, 102);
        ug.lineTo(376, 102);
        ug.quadTo(318, 104, 306, 140);
        ug.lineTo(306, 102);
        ug.quadTo(272, 102, 272, 136);
        ug.lineTo(272, 184);
        ug.quadTo(269, 231, 223, 234);
        ug.quadTo(176, 231, 174, 184);
        ug.lineTo(174, 136);
        ug.quadTo(174, 102, 139, 102);

        GeneralPath beanLeft = new GeneralPath();
        beanLeft.moveTo(220, 117);
        beanLeft.lineTo(214, 117);
        beanLeft.quadTo(189, 122, 186, 146);
        beanLeft.lineTo(186, 192);
        beanLeft.quadTo(189, 216, 214, 222);
        beanLeft.lineTo(220, 222);
        beanLeft.lineTo(220, 117);

        GeneralPath beanRight = new GeneralPath();
        beanRight.moveTo(227, 117);
        beanRight.lineTo(227, 222);
        beanRight.lineTo(233, 222);
        beanRight.quadTo(257, 216, 261, 192);
        beanRight.lineTo(261, 146);
        beanRight.quadTo(258, 122, 233, 117);
        beanRight.lineTo(227, 117);

        GeneralPath m = new GeneralPath();
        m.moveTo(228, 0);
        m.quadTo(222, 14, 227, 28);
        m.quadTo(240, 50, 242, 69);
        m.quadTo(243, 94, 218, 107);
        m.quadTo(240, 82, 228, 55);
        m.lineTo(219, 42);
        m.lineTo(213, 55);
        m.lineTo(216, 62);
        m.quadTo(218, 71, 210, 82);
        m.quadTo(206, 88, 197, 95);
        m.quadTo(205, 84, 208, 72);
        m.quadTo(207, 62, 201, 55);
        m.lineTo(192, 45);
        m.quadTo(181, 72, 169, 83);
        m.quadTo(179, 72, 190, 41);
        m.quadTo(189, 32, 200, 16);
        m.quadTo(194, 29, 202, 40);
        m.lineTo(212, 53);
        m.lineTo(217, 38);
        m.lineTo(214, 30);
        m.quadTo(213, 24, 213, 19);
        m.quadTo(215, 7, 228, 0);

        GeneralPath s = new GeneralPath();
        s.moveTo(258, 12);
        s.quadTo(239, 18, 244, 36);
        s.quadTo(246, 40, 254, 52);
        s.quadTo(259, 60, 260, 70);
        s.quadTo(260, 82, 252, 93);
        s.quadTo(268, 79, 267, 63);
        s.quadTo(264, 51, 258, 44);
        s.quadTo(252, 37, 250, 28);
        s.quadTo(250, 21, 258, 12);

        float strokeWidth = 20;

        for (float alpha = 0.0f; alpha <= 0.2f; alpha += 0.01f) {
            g2.setColor(new Color(0.5608f, 0.8235f, 0.0f, alpha));
            g2.setStroke(new BasicStroke(strokeWidth));
            g2.draw(j);
            g2.draw(ug);

            g2.setColor(new Color(0.4157f, 0.1804f, 0.0667f, alpha));
            g2.draw(beanLeft);
            g2.draw(beanRight);

            g2.setColor(new Color(0.9099f, 0.2078f, 0.1019f, alpha));
            g2.draw(m);
            g2.draw(s);
            strokeWidth -= 1.0f;
        }

        jugImage = getBlur().filter(jugImage, null);
        return jugImage;
    }

    private BufferedImageOp getBlur() {
        final float[] BLUR_KERNEL = { 1 / 12f, 1 / 12f, 1 / 12f, 1 / 12f, 1 / 12f, 1 / 12f,
                1 / 12f, 1 / 12f, 1 / 12f };
        return new ConvolveOp(new Kernel(3, 3, BLUR_KERNEL));
    }

    private Thread createCheckMarkerRemover(final JComponent component) {
        Thread remover = null;
        if (component != null) {
            remover = new Thread(new java.lang.Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        float tmpAlpha = 1.0f;

                        if (checkMarkerAlphaMap.get(component) != null)
                            tmpAlpha = checkMarkerAlphaMap.get(component);

                        while (tmpAlpha > 0) {
                            checkMarkerAlphaMap.put(component, tmpAlpha);

                            // Repaint here is only needed if we do not use
                            // the global animation timer
                            // SwingUtilities.invokeLater(new
                            // java.lang.Runnable()
                            // {
                            //
                            // @Override
                            // public void run()
                            // {
                            // repaint();
                            // }
                            // });
                            Thread.sleep(50);
                            tmpAlpha -= 0.1f;
                        }
                        removeCheckMarker(component);
                    } catch (java.lang.NullPointerException exception) {
                    } catch (InterruptedException exception) {
                        removeCheckMarker(component);
                    }
                }
            });
            removerThreadMap.put(component, remover);
            return remover;
        }

        return null;
    }

    private Thread createFadingAnimation(final boolean fadeToGray) {
        Thread fader = new Thread(new java.lang.Runnable() {
            @Override
            public void run() {
                try {
                    int fadingAlpha = fadingColor.getAlpha();

                    // Fade background to transparent
                    if (!fadeToGray) {
                        while (fadingAlpha > 0) {
                            Thread.sleep(50);
                            fadingColor = new Color(0, 0, 0, fadingAlpha);
                            fadingAlpha -= 10;
                            if (currentLogoAlpha > 0.0625f) {
                                currentLogoAlpha -= 0.0625f;
                                currentLogoScale -= 0.05;
                            }
                            repaint();
                        }
                        fadingColor = new Color(0, 0, 0, 0);
                        backgroundDark = false;
                        repaint();
                    }

                    // Fade background to gray
                    if (fadeToGray) {
                        while (fadingAlpha < 200) {
                            Thread.sleep(50);
                            fadingColor = new Color(0, 0, 0, fadingAlpha);
                            fadingAlpha += 10;
                            if (currentLogoAlpha < 0.9375f) {
                                currentLogoAlpha += 0.0625f;
                                currentLogoScale += 0.05;
                            }
                            repaint();
                        }
                        fadingColor = new Color(0, 0, 0, 200);
                        backgroundDark = true;
                        repaint();
                    }
                } catch (java.lang.NullPointerException exception) {
                } catch (InterruptedException exception) {
                }
            }
        });

        if (fader == null)
            return new Thread();
        else
            return fader;
    }

    private Thread createPulsatingAnimation() {
        if (pulser != null && pulser.isAlive()) {
            return pulser;
        } else {
            pulser = new Thread(new java.lang.Runnable() {
                @Override
                public void run() {
                    try {
                        float alphaAmount = 0.05f;
                        while (disabled) {
                            Thread.sleep(50);
                            if (currentGlowAlpha > 0.95f)
                                alphaAmount = -0.05f;
                            if (currentGlowAlpha < 0.05f)
                                alphaAmount = 0.05f;
                            currentGlowAlpha += alphaAmount;

                            repaint();
                        }
                    } catch (InterruptedException exception) {
                    }
                }
            });

            if (pulser == null)
                return new Thread();
            else
                return pulser;
        }
    }

    @Override
    public String toString() {
        return "SDGlassPane";
    }
}
