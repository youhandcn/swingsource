/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.sd.components.panels;

/**
 * 
 * @author hansolo
 */
public class SDBubblePanel extends SDRadialGradientPanel implements java.awt.event.ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private volatile boolean formComplete;
    private static final java.util.Random RND = new java.util.Random();
    private final java.awt.RenderingHints HINTS;
    private static final int MAX_BUBBLE_RADIUS = 20;
    private static final int AMOUNT_OF_BUBBLES = 13;
    private java.util.ArrayList<java.awt.image.BufferedImage> bubbleList = new java.util.ArrayList<java.awt.image.BufferedImage>(
            AMOUNT_OF_BUBBLES);
    private java.util.ArrayList<Integer> xSpeedList = new java.util.ArrayList<Integer>(
            AMOUNT_OF_BUBBLES);
    private java.util.ArrayList<Integer> ySpeedList = new java.util.ArrayList<Integer>(
            AMOUNT_OF_BUBBLES);
    private java.util.ArrayList<java.awt.Point> positionList = new java.util.ArrayList<java.awt.Point>(
            AMOUNT_OF_BUBBLES);
    private java.util.ArrayList<Float> alphaList = new java.util.ArrayList<Float>(AMOUNT_OF_BUBBLES);
    private java.util.ArrayList<de.jugms.sd.tools.SDTwoColorGradients> colorList = new java.util.ArrayList<de.jugms.sd.tools.SDTwoColorGradients>(
            5);
    private final java.awt.image.BufferedImage TEXT_IMAGE = createTextImage("Feedback", 24,
            java.awt.Color.WHITE);
    private final java.awt.image.BufferedImage REFLECTED_TEXT_IMAGE = createReflectionImage(TEXT_IMAGE);
    private java.awt.Point insertionPoint;
    int counter;
    int x;
    int y;

    public SDBubblePanel() {
        formComplete = false;
        generateAlphaList();
        generateBubbleList();
        generateXSpeedList();
        generateYSpeedList();
        generatePositionList();
        HINTS = new java.awt.RenderingHints(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION,
                java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        HINTS.put(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        HINTS.put(java.awt.RenderingHints.KEY_COLOR_RENDERING,
                java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        HINTS.put(java.awt.RenderingHints.KEY_INTERPOLATION,
                java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        HINTS.put(java.awt.RenderingHints.KEY_RENDERING,
                java.awt.RenderingHints.VALUE_RENDER_QUALITY);
        colorList.add(de.jugms.sd.tools.SDTwoColorGradients.BLUE_TRANSPA);
        colorList.add(de.jugms.sd.tools.SDTwoColorGradients.GREEN_TRANSPA);
        colorList.add(de.jugms.sd.tools.SDTwoColorGradients.ORANGE_TRANSPA);
        colorList.add(de.jugms.sd.tools.SDTwoColorGradients.RED_TRANSPA);
        colorList.add(de.jugms.sd.tools.SDTwoColorGradients.GRAY_TRANSPA);
        insertionPoint = new java.awt.Point((int) (Math.random() * getPreferredSize().width),
                getPreferredSize().height);

        // Start the animation timer for which we have to implement
        // the ActionListener interface. Only for standalone Bubblepanel
        // without GlobalAnimationTimer
        // javax.swing.Timer animation = new javax.swing.Timer(50, this);
        // animation.start();

        de.jugms.sd.tools.SDGlobalAnimationTimer.INSTANCE.addComponent(this);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        // Repaint is only needed for standalone
        // BubblePanel without GlobalAnimationTimer
        // repaint(this.getVisibleRect());
    }

    public boolean isFormComplete() {
        return this.formComplete;
    }

    public void setFormComplete(boolean formComplete) {
        this.formComplete = formComplete;
    }

    @Override
    public void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.setRenderingHints(HINTS);
        super.paintComponent(g2);

        drawBubbles(g2);

        g2.dispose();
    }

    private void drawBubbles(java.awt.Graphics2D g2) {
        counter = 0;

        for (java.awt.image.BufferedImage bubbleImage : bubbleList) {
            // Set the alpha value for the current BUBBLE image
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                    alphaList.get(counter)));

            // Recalculate the new position of the BUBBLE with their specific
            // speed
            x = positionList.get(counter).x;
            y = positionList.get(counter).y;
            x -= xSpeedList.get(counter);
            y -= ySpeedList.get(counter);

            // Draw the BUBBLE without a blur effect
            g2.drawImage(bubbleImage, x, y, null);

            // Check if BUBBLE is outside the panel and create a new BUBBLE if
            // needed
            if (x < -MAX_BUBBLE_RADIUS || x > (getWidth() + MAX_BUBBLE_RADIUS)
                    || y < -MAX_BUBBLE_RADIUS || y > (getHeight() + MAX_BUBBLE_RADIUS)) {
                insertionPoint = new java.awt.Point((int) (Math.random() * getWidth()), getHeight());
                positionList.set(counter, insertionPoint);

                // Draw smileys if the form was completed, else draw bubbles
                if (formComplete) {
                    bubbleList.set(counter,
                            createSmileyImage(5 + (int) (Math.random() * MAX_BUBBLE_RADIUS)));
                } else {
                    bubbleList.set(counter,
                            createBubbleImage(5 + (int) (Math.random() * MAX_BUBBLE_RADIUS)));
                }
            } else {
                insertionPoint = new java.awt.Point(x, y);
                positionList.set(counter, insertionPoint);
            }
            counter++;
        }

        // Reset the alpha value for additional drawings to 1.0f
        g2
                .setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER,
                        1.0f));

        // Draw text as image in the CENTER of the panel
        g2.drawImage(TEXT_IMAGE, (getWidth() - TEXT_IMAGE.getWidth()) / 2,
                (getHeight() - TEXT_IMAGE.getHeight()) / 2, null);

        // Draw reflected text as image below the text
        g2.drawImage(REFLECTED_TEXT_IMAGE, (getWidth() - TEXT_IMAGE.getWidth()) / 2,
                (getHeight() - TEXT_IMAGE.getHeight()) / 2 + 11, null);

        g2.dispose();
    }

    private void generateBubbleList() {
        for (int i = 0; i < AMOUNT_OF_BUBBLES; i++) {
            // bubbleList.add(createBubbleImage((5 + (int) (Math.random() *
            // MAX_BUBBLE_RADIUS))));
            bubbleList.add(createBubbleImage((5 + (int) (Math.random() * MAX_BUBBLE_RADIUS))));
        }
    }

    private void generateXSpeedList() {
        final int LOW = -1;
        final int HIGH = 1;
        for (int i = 0; i < AMOUNT_OF_BUBBLES; i++) {
            xSpeedList.add(RND.nextInt(HIGH - LOW + 1) + LOW);
        }
    }

    private void generateYSpeedList() {
        for (int i = 0; i < AMOUNT_OF_BUBBLES; i++) {
            ySpeedList.add((int) (1 + Math.random() * 2.5f));
        }
    }

    private void generatePositionList() {
        for (int i = 0; i < AMOUNT_OF_BUBBLES; i++) {
            positionList.add(new java.awt.Point((int) (Math.random() * 350),
                    (int) (Math.random() * 70)));
        }
    }

    private void generateAlphaList() {
        for (int i = 0; i < AMOUNT_OF_BUBBLES; i++) {
            alphaList.add((float) (0.6f * Math.random()) + 0.2f);
        }
    }

    private java.awt.image.BufferedImage createBubbleImage(int bubbleSize) {
        java.awt.image.BufferedImage bubbleImage = new java.awt.image.BufferedImage(
                bubbleSize + 14, bubbleSize + 14, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) bubbleImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a GRADIENT that makes the sphere look like a BUBBLE
        final java.awt.geom.Point2D CENTER = new java.awt.geom.Point2D.Float((bubbleSize + 14) / 2,
                (bubbleSize + 14) / 2);

        final float RADIUS = bubbleSize / 2;

        final float[] FRACTIONS = { 0.0f, 0.8f, 1.0f };

        // Define the COLORS_BUBBLE for the BUBBLE
        // dependent on the variable formComplete
        final java.awt.Color[] COLORS_BUBBLE;
        final java.awt.Color[] COLORS_BUBBLE_HIGHLIGHT;
        // White BUBBLE
        COLORS_BUBBLE = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 0.1f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.9f) };

        // White HIGHLIGHT
        COLORS_BUBBLE_HIGHLIGHT = new java.awt.Color[] {
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.7f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f) };

        final java.awt.RadialGradientPaint GRADIENT = new java.awt.RadialGradientPaint(CENTER,
                RADIUS, FRACTIONS, COLORS_BUBBLE);

        final java.awt.geom.Ellipse2D BUBBLE = new java.awt.geom.Ellipse2D.Double(7, 7, bubbleSize,
                bubbleSize);

        g2.setPaint(GRADIENT);
        g2.fill(BUBBLE);

        // Create a HIGHLIGHT effect on top of BUBBLE
        final java.awt.Point START = new java.awt.Point(0, 8);
        final java.awt.Point STOP = new java.awt.Point(0, (int) (8 + RADIUS - 2));

        final java.awt.GradientPaint GRADIENT_HIGHLIGHT = new java.awt.GradientPaint(START,
                COLORS_BUBBLE_HIGHLIGHT[0], STOP, COLORS_BUBBLE_HIGHLIGHT[1]);
        final java.awt.geom.Ellipse2D HIGHLIGHT = new java.awt.geom.Ellipse2D.Double(9, 8,
                bubbleSize - 4, (bubbleSize / 2));
        g2.setPaint(GRADIENT_HIGHLIGHT);
        g2.fill(HIGHLIGHT);

        // Creates a randomly colored bubble if the form is completed
        // if (formComplete)
        // {
        // g2.setColor((colorList.get((int) (Math.random() * 4))).getLight());
        // g2.fill(BUBBLE);
        // }

        g2.dispose();

        return bubbleImage;
    }

    private java.awt.image.BufferedImage createTextImage(String text, int fontSize,
            java.awt.Color fontColor) {
        // Calculate string size
        java.awt.image.BufferedImage tmpImage = new java.awt.image.BufferedImage(
                getPreferredSize().width, getPreferredSize().height,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = (java.awt.Graphics2D) tmpImage.getGraphics();
        g.setFont(new java.awt.Font("Verdana", 1, fontSize));
        java.awt.FontMetrics metrics = g.getFontMetrics();
        g.dispose();
        tmpImage.flush();

        // Create image with size of string
        java.awt.image.BufferedImage txtImage = new java.awt.image.BufferedImage((int) metrics
                .getStringBounds(text, g).getWidth(), metrics.getHeight(),
                java.awt.image.BufferedImage.TYPE_INT_ARGB);

        java.awt.Graphics2D g2 = (java.awt.Graphics2D) txtImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fontColor);
        g2.setFont(new java.awt.Font("Verdana", 1, fontSize));

        g2.drawString(text, 0, 20);

        g2.dispose();

        return txtImage;
    }

    private java.awt.image.BufferedImage createSmileyImage(int smileySize) {
        java.awt.image.BufferedImage smileyImage = new java.awt.image.BufferedImage(smileySize + 4,
                smileySize + 4, java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) smileyImage.getGraphics();
        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL,
                java.awt.RenderingHints.VALUE_STROKE_PURE);
        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a GRADIENT that makes the sphere look like a BUBBLE
        final java.awt.geom.Point2D CENTER = new java.awt.geom.Point2D.Float((smileySize + 4) / 2,
                (smileySize + 4) / 2);

        final float RADIUS = smileySize / 2;

        // Background fractions
        final float[] FRACTIONS_BACKGROUND1 = { 0.0f, 0.75f, 0.85f, 1.0f };

        final float[] FRACTIONS_BACKGROUND2 = { 0.0f, 1.0f };

        final float[] FRACTIONS_HIGHLIGHT = { 0.0f, 0.5f, 0.85f, 1.0f };

        // Define the COLORS_BUBBLE for the BUBBLE
        // dependent on the variable formComplete
        final java.awt.Color[] COLORS_BACKGROUND1;
        final java.awt.Color[] COLORS_BACKGROUND2;

        final java.awt.Color[] COLORS_HIGHLIGHT;
        // Yellow radial gradient
        COLORS_BACKGROUND1 = new java.awt.Color[] { new java.awt.Color(253, 227, 46, 255),
                new java.awt.Color(253, 227, 46, 255), new java.awt.Color(239, 205, 0, 255),
                new java.awt.Color(195, 158, 26, 255) };

        // Yellow linear gradient
        COLORS_BACKGROUND2 = new java.awt.Color[] { new java.awt.Color(255, 227, 46, 255),
                new java.awt.Color(255, 227, 46, 0) };

        // White HIGHLIGHT
        COLORS_HIGHLIGHT = new java.awt.Color[] { new java.awt.Color(1.0f, 1.0f, 1.0f, 1.0f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.0f),
                new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f) };

        final java.awt.RadialGradientPaint GRADIENT_BACKGROUND1 = new java.awt.RadialGradientPaint(
                CENTER, RADIUS, FRACTIONS_BACKGROUND1, COLORS_BACKGROUND1);
        final java.awt.LinearGradientPaint GRADIENT_BACKGROUND2 = new java.awt.LinearGradientPaint(
                new java.awt.geom.Point2D.Float(0, 2), new java.awt.geom.Point2D.Float(0,
                        smileySize), FRACTIONS_BACKGROUND2, COLORS_BACKGROUND2);

        // Draw Background
        final java.awt.geom.Ellipse2D SMILEY = new java.awt.geom.Ellipse2D.Double(2, 2, smileySize,
                smileySize);
        g2.setPaint(GRADIENT_BACKGROUND1);
        g2.fill(SMILEY);

        g2.setPaint(GRADIENT_BACKGROUND2);
        g2.fill(SMILEY);

        // Draw eyes
        g2.setColor(java.awt.Color.BLACK);
        // left
        g2.fillOval((int) (smileySize / 3.6184) + 3, (smileySize / 5) + 3,
                (int) (smileySize / 7.6389), (int) (smileySize / 4.2308));
        // right
        g2.fillOval((int) (smileySize / 1.6871) + 3, (smileySize / 5) + 3,
                (int) (smileySize / 7.6389), (int) (smileySize / 4.2308));

        // Draw mouth
        g2.setStroke(new java.awt.BasicStroke(smileySize / 22.92f));
        java.awt.geom.GeneralPath mouth = new java.awt.geom.GeneralPath();
        mouth.moveTo((int) (smileySize / 7.0513) + 3, (int) (smileySize / 1.76282) + 2);
        mouth.quadTo((int) (smileySize / 2.037) + 2, (int) (smileySize / 0.95) + 2,
                (int) (smileySize / 1.1752) + 2, (int) (smileySize / 1.76282) + 2);
        g2.draw(mouth);

        // Create a HIGHLIGHT effect on top of SMILEY
        final java.awt.LinearGradientPaint GRADIENT_HIGHLIGHT = new java.awt.LinearGradientPaint(
                new java.awt.geom.Point2D.Float(0, 2), new java.awt.geom.Point2D.Float(0,
                        smileySize - 2), FRACTIONS_HIGHLIGHT, COLORS_HIGHLIGHT);
        final java.awt.geom.Ellipse2D HIGHLIGHT = new java.awt.geom.Ellipse2D.Double(3, 3,
                smileySize - 3, smileySize - 3);
        g2.setPaint(GRADIENT_HIGHLIGHT);
        g2.fill(HIGHLIGHT);

        g2.dispose();

        return smileyImage;
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
    public String toString() {
        return "SDBubblePanel";
    }
}
