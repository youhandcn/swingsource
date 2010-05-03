/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AnimatedProgressBar.java
 *
 * Created on 07.01.2009, 16:54:23
 */
package de.jugms.sd.components.misc;

/**
 * 
 * @author hansolo
 */
public class SDGlassProgressBar extends javax.swing.JPanel implements
        java.awt.event.ComponentListener, java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    boolean reflection;
    float reflectionDivisor;
    private volatile boolean running;
    private int barWidth;
    private int barHeight;
    private int cornerRadius;
    private boolean roundedCorners;
    private final int BORDER;
    private double maxValue;
    volatile private double value;
    volatile private double percent;
    private boolean showValue;
    private boolean showPercent;
    private boolean tilted;
    private boolean infinite;
    private String infiniteText;
    private boolean showInfiniteText;
    private java.awt.Color infiniteTextColor;
    private double factor;
    private float posX;
    private float posY;
    private float period;
    private float cyclePosX;
    private java.awt.Color color;
    private java.awt.Color cycleFirstColor;
    private java.awt.Color cycleSecondColor;
    private java.awt.Color[] cycleColors;
    private java.awt.Shape roundRectVariableBar;
    private java.awt.Shape roundRectFixedBar;
    private java.awt.geom.Area variableBar;
    private java.awt.geom.Area fixedBar;
    private java.awt.LinearGradientPaint frameGradient;
    private float[] fractionsHighlight;
    private java.awt.Color[] colorsHighlight;
    private java.awt.LinearGradientPaint gradientHighlight;
    private float[] fractionsBackgroundFixedBar;
    private java.awt.Color[] colorsBackgroundFixedBar;
    private java.awt.LinearGradientPaint gradientBackgroundFixedBar;
    private float[] fractionsFixedBar;
    private java.awt.Color[] colorsFixedBar;
    private java.awt.LinearGradientPaint gradientFixedBar;
    private java.awt.LinearGradientPaint cyclicJava6;
    private float[] distStandard;
    private float[] distTilted;
    private float[] distFinished;
    private java.awt.geom.Point2D startPoint;
    private java.awt.geom.Point2D endPoint;
    private java.awt.Font stdFont;
    private java.awt.Color foregroundColor;
    java.awt.geom.Rectangle2D textBoundary;
    java.awt.FontMetrics metrics;
    java.awt.Stroke frameStroke;
    java.awt.geom.RoundRectangle2D highLight;
    private Thread cycleThread;
    private java.awt.image.BufferedImage barImage;

    /** Creates new form AnimatedProgressBar */
    public SDGlassProgressBar() {
        super.setOpaque(false);
        setBackground(new java.awt.Color(0, 0, 0, 0));
        addComponentListener(this);
        setPreferredSize(new java.awt.Dimension(102, 22));
        initComponents();
        setRoundedCorners(true);
        setCycleFirstColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getLight());
        setCycleSecondColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient().getDark());
        this.BORDER = 4;
        this.reflection = false;
        this.reflectionDivisor = 1.0f;
        prepareForms();
    }

    private void initComponents() {
        running = false;
        barWidth = getPreferredSize().width - 2;
        barHeight = getPreferredSize().height - 6;

        cornerRadius = 0;
        roundedCorners = false;

        maxValue = 100;
        value = 0;
        percent = 0;
        showValue = false;
        showPercent = false;
        tilted = false;

        infinite = false;
        infiniteText = "";
        showInfiniteText = false;
        infiniteTextColor = new java.awt.Color(1.0f, 1.0f, 1.0f, 1.0f);

        factor = 1;

        posX = 2;
        posY = 2;
        period = 10;

        cyclePosX = posX;
        color = new java.awt.Color(88, 154, 227, 225);
        cycleFirstColor = new java.awt.Color(88, 154, 227, 128);
        cycleSecondColor = new java.awt.Color(50, 130, 222, 128);

        cycleColors = new java.awt.Color[] { cycleFirstColor, cycleFirstColor, cycleSecondColor,
                cycleSecondColor };

        fractionsBackgroundFixedBar = new float[] { 0.0f, 1.0f };

        colorsBackgroundFixedBar = de.jugms.sd.tools.SDTwoColorGradients.BUTTON_FOREGROUND_ENABLED
                .getGradientDarkLight();

        fractionsFixedBar = new float[] { 0.0f, 0.5f, 1.0f };

        // Colors with background
        colorsFixedBar = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };

        fractionsHighlight = new float[] { 0.0f, 1.0f };

        colorsHighlight = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f) };

        distStandard = new float[] { 0.0f, 0.01f, 0.99f, 1.0f };

        distTilted = new float[] { 0.0f, 0.01f, 0.99f, 1.0f };

        distFinished = new float[] { 0.0f, 0.1f, 0.9f, 1.0f };

        stdFont = new java.awt.Font(java.awt.Font.SANS_SERIF, 0,
                (int) (barHeight - barHeight * 0.3));
        foregroundColor = java.awt.Color.DARK_GRAY;

        if (getGrayScaleIntensity(this.cycleFirstColor) > 128) {
            foregroundColor = java.awt.Color.DARK_GRAY;
        } else {
            foregroundColor = java.awt.Color.WHITE;
        }

        frameStroke = new java.awt.BasicStroke(1.0f);

        createCycleThread();
    }

    @Override
    protected void paintComponent(final java.awt.Graphics g) {
        super.paintComponent(g);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;

        barImage = createBarImage();
        g2.drawImage(barImage, 0, 0, this);
        if (reflection) {
            g2.drawImage(createReflectionImage(barImage), 0, barImage.getHeight(), this);
        }

        g2.dispose();

        // Stop the animation if it is not infinite
        if (!infinite) {
            if (value >= (maxValue * factor)) {
                running = false;
            }
        }
    }

    private java.awt.image.BufferedImage createBarImage() {
        java.awt.image.BufferedImage localBarImage = new java.awt.image.BufferedImage(getWidth(),
                (int) (getHeight() / reflectionDivisor), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = localBarImage.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING,
                java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);

        // Draw background of the glass bar
        g2.setPaint(gradientBackgroundFixedBar);
        g2.fill(roundRectFixedBar);

        // Paint cyclic gradient bar
        if (running) {
            if (tilted) {
                startPoint = new java.awt.geom.Point2D.Float(cyclePosX, posY);
                endPoint = new java.awt.geom.Point2D.Double((cyclePosX + period * Math.sqrt(2)),
                        posY + period / 2 / Math.sqrt(2));
                cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, distTilted,
                        cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
            } else {
                startPoint = new java.awt.geom.Point2D.Float(cyclePosX, posY);
                endPoint = new java.awt.geom.Point2D.Float(cyclePosX + period, posY);
                cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, distStandard,
                        cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
            }
        } else {
            startPoint = new java.awt.geom.Point2D.Float(posX, posY);
            endPoint = new java.awt.geom.Point2D.Float(posX, posY + barHeight);
            cyclicJava6 = new java.awt.LinearGradientPaint(startPoint, endPoint, distFinished,
                    cycleColors, java.awt.MultipleGradientPaint.CycleMethod.REFLECT);
        }
        g2.setPaint(cyclicJava6);

        roundRectVariableBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY, value,
                barHeight, cornerRadius, cornerRadius);
        variableBar = new java.awt.geom.Area(roundRectVariableBar);
        fixedBar = new java.awt.geom.Area(roundRectFixedBar);
        fixedBar.intersect(variableBar);
        g2.fill(fixedBar);

        // Draw main glass bar
        g2.setPaint(gradientFixedBar);
        g2.fill(roundRectFixedBar);

        // Paint topLight effect
        g2.setPaint(gradientHighlight);
        g2.fill(highLight);

        // Draw frame
        java.awt.Stroke oldStroke = g2.getStroke();
        g2.setStroke(frameStroke);
        g2.setPaint(frameGradient);
        g2.draw(roundRectFixedBar);
        g2.setStroke(oldStroke);

        // Show value as number
        if (showValue) {
            g2.setColor(foregroundColor);
            g2.setFont(stdFont);

            metrics = g2.getFontMetrics();
            textBoundary = metrics.getStringBounds(Integer.toString((int) (value / factor)), g2);

            g2.drawString(Integer.toString((int) (value / factor)),
                    (int) (posX + (barWidth - textBoundary.getWidth()) / 2), (int) (posY
                            + (barHeight - metrics.getHeight() / reflectionDivisor) / 2
                            + metrics.getHeight() - (BORDER / 2)));
        }

        // Show value as percentage
        if (showPercent) {
            g2.setColor(foregroundColor);
            g2.setFont(stdFont);

            metrics = g2.getFontMetrics();
            textBoundary = metrics.getStringBounds(Integer.toString((int) (percent)) + " %", g2);

            g2.drawString(Integer.toString((int) (percent)) + " %",
                    (int) (posX + (barWidth - textBoundary.getWidth()) / 2), (int) (posY
                            + (barHeight - metrics.getHeight() / reflectionDivisor) / 2
                            + metrics.getHeight() / reflectionDivisor - (BORDER / 2)));
        }

        // Show infinite text
        if (infinite && showInfiniteText) {
            g2.setColor(infiniteTextColor);
            g2.setFont(stdFont);

            metrics = g2.getFontMetrics();
            textBoundary = metrics.getStringBounds(infiniteText, g2);

            g2
                    .drawString(infiniteText,
                            (int) (posX + (barWidth - textBoundary.getWidth()) / 2),
                            (int) (posY + (barHeight - metrics.getHeight()) / 2
                                    + metrics.getHeight() - (BORDER / 2)));
        }

        g2.dispose();

        return localBarImage;
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

    @Override
    public void setSize(final java.awt.Dimension size) {
        super.firePropertyChange("size", super.getSize(), size);
        super.setSize(size);
        setWidth(size.width);
        this.barHeight = size.height - BORDER;
        setRoundedCorners(roundedCorners);
    }

    /**
     * Set the width of the bar in pixels
     * 
     * @param width
     */
    public void setWidth(final int width) {
        super.firePropertyChange("width", super.getWidth(), width);
        this.barWidth = width;
        this.setPreferredSize(new java.awt.Dimension(width + 2, barHeight + 4));

        prepareForms();
    }

    @Override
    public int getWidth() {
        return this.barWidth;
    }

    /**
     * Prepare all forms for the given width
     */
    private void prepareForms() {
        // Main effect
        gradientBackgroundFixedBar = new java.awt.LinearGradientPaint(
                new java.awt.geom.Point2D.Float(posX, posY), new java.awt.geom.Point2D.Float(posX,
                        posY + barHeight), fractionsBackgroundFixedBar, colorsBackgroundFixedBar);

        gradientFixedBar = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Float(posX,
                posY), new java.awt.geom.Point2D.Float(posX, posY + barHeight), fractionsFixedBar,
                colorsFixedBar);

        // Bar
        roundRectVariableBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY + 1, value,
                barHeight - 3, cornerRadius, cornerRadius);
        roundRectFixedBar = new java.awt.geom.RoundRectangle2D.Double(posX, posY,
                barWidth - BORDER, barHeight, cornerRadius, cornerRadius);
        variableBar = new java.awt.geom.Area(roundRectVariableBar);
        fixedBar = new java.awt.geom.Area(roundRectFixedBar);

        // New Highlight
        highLight = new java.awt.geom.RoundRectangle2D.Double((posX + 2), (posY + 1), barWidth
                - BORDER - 4, (barHeight * 0.421f), cornerRadius, cornerRadius);
        gradientHighlight = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Double(
                highLight.getBounds2D().getX(), highLight.getBounds2D().getY()),
                new java.awt.geom.Point2D.Double(highLight.getBounds2D().getX(), highLight
                        .getBounds2D().getY()
                        + highLight.getBounds2D().getHeight()), fractionsHighlight, colorsHighlight);

        // Frame gradient
        frameGradient = new java.awt.LinearGradientPaint(new java.awt.geom.Point2D.Double(
                roundRectFixedBar.getBounds2D().getX(), roundRectFixedBar.getBounds2D().getY()),
                new java.awt.geom.Point2D.Double(roundRectFixedBar.getBounds2D().getX(),
                        roundRectFixedBar.getBounds2D().getY()
                                + roundRectFixedBar.getBounds2D().getHeight()), new float[] { 0.0f,
                        0.2f, 0.6f, 0.9f, 1.0f }, new java.awt.Color[] {
                        new java.awt.Color(1.0f, 1.0f, 1.0f, 1.0f),
                        new java.awt.Color(0.0f, 0.0f, 0.0f, 1.0f),
                        new java.awt.Color(0.4f, 0.4f, 0.4f, 1.0f),
                        new java.awt.Color(0.0f, 0.0f, 0.0f, 1.0f),
                        new java.awt.Color(0.4f, 0.4f, 0.4f, 1.0f) });

        // Recalculate factor between width and maxValue
        factor = (double) barWidth / maxValue;

        repaint();
    }

    /**
     * Set the maximum value for the bar
     * 
     * @param maxValue
     */
    public void setMaxValue(final double maxValue) {
        this.maxValue = maxValue;
        factor = calcFactor();
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    private double calcFactor() {
        return ((double) barWidth / maxValue);
    }

    /**
     * Reset the bar so that you can restart it with different parameters again.
     * Also recreate the forms and the animation thread.
     */
    public void reset() {
        setValue(0);
        running = false;
        prepareForms();
        createCycleThread();
    }

    /**
     * Enable or disable a tilted gradient
     * 
     * @param tilted
     */
    public void setTilted(final boolean tilted) {
        this.tilted = tilted;
    }

    public boolean isTilted() {
        return this.tilted;
    }

    public void setReflection(boolean reflection) {
        this.reflection = reflection;
        if (reflection) {
            this.reflectionDivisor = 2.0f;
            this.setPreferredSize(new java.awt.Dimension(102, 44));
        } else {
            this.reflectionDivisor = 1.0f;
            this.setPreferredSize(new java.awt.Dimension(102, 22));
        }
    }

    public boolean hasReflection() {
        return this.reflection;
    }

    /**
     * Enable or disable the infinite progress which means it is tilted, there
     * is no value and it is only a 100%
     * 
     * @param infinite
     */
    public void setInfinite(final boolean infinite) {
        if (infinite) {
            setShowValue(false);
            setShowPercent(false);
            setMaxValue(100);
            setValue(100);
        } else {
            reset();
        }
        this.infinite = infinite;
        running = infinite;
    }

    public boolean isInfinite() {
        return this.infinite;
    }

    public String getInfiniteText() {
        return this.infiniteText;
    }

    public void setInfiniteText(final String infiniteText) {
        this.infiniteText = infiniteText;
    }

    public void setShowInfiniteText(final boolean showInfiniteText) {
        this.showInfiniteText = showInfiniteText;
    }

    public boolean isShowInfiniteText() {
        return this.showInfiniteText;
    }

    /**
     * Set the current value of the bar
     * 
     * @param value
     */
    public void setValue(double value) {
        if (value >= maxValue) {
            value = maxValue;
        }
        if (value < 0) {
            value = 0;
        }
        // super.firePropertyChange("value", this.value, value);

        this.value = value * factor;
        this.percent = (value * 100 / maxValue);

        // Start animation thread if not running
        if (!running) {
            if (!cycleThread.isAlive()) {
                createCycleThread();
            }
            running = true;
            if (!cycleThread.isAlive()) {
                cycleThread.start();
            }
        }
    }

    /**
     * Get the current value of the bar
     * 
     * @return
     */
    public double getValue() {
        return this.value;
    }

    private void recalc() {
        final double unscaledValue = this.value / this.factor;
        this.factor = calcFactor();
        setValue(unscaledValue);
    }

    /**
     * Enable or disable the visibility of the value as a number in the center
     * of the bar. If enabled the showPercent variable will be disabled.
     * 
     * @param showValue
     */
    public void setShowValue(final boolean showValue) {
        this.showPercent = false;
        this.showValue = showValue;
    }

    public boolean isShowValue() {
        return this.showValue;
    }

    /**
     * Enable or disable the visibility of the value as a percentage value in
     * the center of the bar. If enabled the showValue variable will be
     * disabled.
     * 
     * @param showPercent
     */
    public void setShowPercent(final boolean showPercent) {
        this.showValue = false;
        this.showPercent = showPercent;
    }

    public boolean isShowPercent() {
        return this.showPercent;
    }

    /**
     * Set the current value of the bar as a percentage value (0 - 100).
     * 
     * @param percent
     */
    public void setPercent(int percent) {
        if (percent >= 100) {
            percent = 100;
        }
        if (percent < 0) {
            percent = 0;
        }
        this.value = (((double) percent * maxValue) / 100 * factor);
        this.percent = ((value * 100 * factor / maxValue));
    }

    public double getPercent() {
        return this.percent;
    }

    /**
     * Set the radius of the rounded rectangle which is used for the bar
     * 
     * @param cornerRadius
     */
    public void setCornerRadius(final int cornerRadius) {
        this.cornerRadius = cornerRadius;
        if (this.cornerRadius != this.barHeight) {
            this.roundedCorners = false;
        }
        prepareForms();
    }

    /**
     * Get the radius of the rounded rectangle which is used for the bar.
     * 
     * @return
     */
    public int getCornerRadius() {
        return this.cornerRadius;
    }

    public void setRoundedCorners(boolean roundedCorners) {
        this.roundedCorners = roundedCorners;
        if (roundedCorners) {
            setCornerRadius(this.barHeight);
        } else {
            setCornerRadius(0);
        }
    }

    public boolean hasRoundedCorners() {
        return this.roundedCorners;
    }

    /**
     * Redefine the start position of the gradient paint and repaint the bar in
     * dependence of showValue and showPercent variable. If no text is shown in
     * the bar we only need to repaint the bar itself without the frame.
     * 
     * @param cyclePosX
     */
    private void setCyclicStart(final float cyclePosX) {
        this.cyclePosX = cyclePosX;
        repaint();
    }

    /**
     * Creates a new Thread for the color cycling. This is allways needed when
     * the value equals 0 or 100
     */
    private void createCycleThread() {
        cycleThread = new Thread(new java.lang.Runnable() {
            int x = 0;

            @Override
            public void run() {
                double cycleLimit;
                boolean fadeOut = true;
                int alpha = 255;

                while (running) {
                    if (tilted) {
                        cycleLimit = period * Math.sqrt(2) * 2;
                    } else {
                        cycleLimit = period * 2;
                    }

                    try {
                        setCyclicStart(period - x);
                        x++;
                        if (x >= cycleLimit) {
                            x = 0;
                        }
                        if (infinite && showInfiniteText) {
                            if (fadeOut) {
                                alpha -= 4;
                            } else {
                                alpha += 4;
                            }
                            if (alpha < 0) {
                                fadeOut = false;
                                alpha = 0;
                            }
                            if (alpha >= 255) {
                                fadeOut = true;
                                alpha = 255;
                            }
                            infiniteTextColor = new java.awt.Color(255, 255, 255, alpha);
                        }
                        Thread.sleep(50);
                    } catch (InterruptedException exception) {
                        //
                    }
                }
                repaint();
            }
        });
    }

    private int getGrayScaleIntensity(final java.awt.Color color) {
        int grayScaleIntensity = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114
                * color.getBlue() + .5);

        return grayScaleIntensity;
    }

    /**
     * Set the first color of the gradient.
     * 
     * @param cycleFirstColor
     */
    public void setCycleFirstColor(final java.awt.Color cycleFirstColor) {
        // Java 6
        this.cycleColors[0] = cycleFirstColor;
        this.cycleColors[1] = cycleFirstColor;
    }

    public java.awt.Color getCycleFirstColor() {
        return this.cycleFirstColor;
    }

    /**
     * Set the second color of the gradient.
     * 
     * @param cycleSecondColor
     */
    public void setCycleSecondColor(final java.awt.Color cycleSecondColor) {
        // Java 6
        this.cycleColors[2] = cycleSecondColor;
        this.cycleColors[3] = cycleSecondColor;
    }

    public java.awt.Color getCycleSecondColor() {
        return this.cycleSecondColor;
    }

    /**
     * Set the color of the gradient by using the given color as
     * cycleSecondColor and a darkened version of the given color as
     * cycleFirstColor.
     * 
     * @param color
     */
    public void setColor(final java.awt.Color color) {
        // Java 6
        this.color = color;
        this.cycleColors[0] = color.darker();
        this.cycleColors[1] = color.darker();
        this.cycleColors[2] = color;
        this.cycleColors[3] = color;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Set the color of the text to the given value
     * 
     * @param foregroundColor
     */
    public void setForegroundColor(final java.awt.Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public java.awt.Color getForegroundColor() {
        return this.foregroundColor;
    }

    public void refresh() {
        this
                .setCycleFirstColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                        .getLight());
        this
                .setCycleSecondColor(de.jugms.sd.tools.SDGlobals.INSTANCE.getActiveGradient()
                        .getDark());
    }

    @Override
    public void componentResized(final java.awt.event.ComponentEvent event) {
        barWidth = super.getWidth() - 2;
        barHeight = (int) (super.getHeight() / reflectionDivisor - 6);
        setRoundedCorners(this.roundedCorners);
        if (isInfinite()) {
            setInfinite(true);
        }
        recalc();

        this.prepareForms();
    }

    @Override
    public void componentMoved(final java.awt.event.ComponentEvent event) {
        // not used right now
    }

    @Override
    public void componentShown(final java.awt.event.ComponentEvent event) {
        // not used right now
    }

    @Override
    public void componentHidden(final java.awt.event.ComponentEvent event) {
        // not used right now
    }

    @Override
    public String toString() {
        return "SDGlassProgressBar";
    }
}
