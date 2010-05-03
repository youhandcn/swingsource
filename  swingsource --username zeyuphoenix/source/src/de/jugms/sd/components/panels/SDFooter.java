package de.jugms.sd.components.panels;

/**
 * 
 * @author HanSolo
 */
public class SDFooter extends javax.swing.JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    java.awt.LinearGradientPaint gradient;
    private java.awt.Rectangle resizerHotSpot;
    private java.awt.Rectangle optionHotSpot;
    java.awt.image.BufferedImage cache = null;

    public SDFooter() {
        setPreferredSize(new java.awt.Dimension(100, 24));
        resizerHotSpot = new java.awt.Rectangle(getWidth() - 14, 10, 12, 12);
        optionHotSpot = new java.awt.Rectangle(4, 10, 12, 12);
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        java.awt.RenderingHints rh = g2.getRenderingHints();
        rh
                .put(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        // Paint fast background gradient
        if (cache == null || cache.getHeight() != getHeight()) {
            cache = createBackgroundImage();
        }
        g2.drawImage(cache, 0, 0, getWidth(), getHeight(), null);

        // Draw resizer image
        g2.drawImage(createResizerImage(), getWidth() - 14, 10, null);
        resizerHotSpot = new java.awt.Rectangle(getWidth() - 14, 10, 12, 12);

        // Draw option image
        g2.drawImage(createOptionImage(), 4, 4, null);
        optionHotSpot = new java.awt.Rectangle(4, 4, 17, 15);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createBackgroundImage() {
        cache = new java.awt.image.BufferedImage(2, getHeight(),
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = cache.createGraphics();

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float[] FRACTIONS = { 0.0f, 0.1f, 0.4f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255), new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255) };

        java.awt.LinearGradientPaint paint = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        g2.setPaint(paint);
        g2.fillRect(0, 0, 2, getHeight());

        g2.dispose();

        return cache;
    }

    private java.awt.image.BufferedImage createResizerImage() {
        java.awt.image.BufferedImage resizerImage = new java.awt.image.BufferedImage(12, 12,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = resizerImage.createGraphics();

        java.awt.RenderingHints rh = g2.getRenderingHints();
        rh
                .put(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        // g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.5f));
        // g2.fillOval(9, 9, 2, 2);
        // g2.fillOval(5, 9, 2, 2);
        // g2.fillOval(1, 9, 2, 2);
        // g2.fillOval(9, 5, 2, 2);
        // g2.fillOval(5, 5, 2, 2);
        // g2.fillOval(9, 1, 2, 2);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f));
        g2.fillOval(8, 8, 2, 2);
        g2.fillOval(4, 8, 2, 2);
        g2.fillOval(0, 8, 2, 2);
        g2.fillOval(8, 4, 2, 2);
        g2.fillOval(4, 4, 2, 2);
        g2.fillOval(8, 0, 2, 2);

        g2.dispose();

        return resizerImage;
    }

    private java.awt.image.BufferedImage createOptionImage() {
        java.awt.image.BufferedImage optionImage = new java.awt.image.BufferedImage(17, 15,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2 = optionImage.createGraphics();

        java.awt.RenderingHints rh = g2.getRenderingHints();
        rh
                .put(java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        g2.setColor(new java.awt.Color(1.0f, 1.0f, 1.0f, 0.3f));
        g2.fillRect(1, 1, 3, 3);
        g2.fillRect(1, 6, 3, 3);
        g2.fillRect(1, 11, 3, 3);
        g2.drawLine(7, 2, 16, 2);
        g2.drawLine(7, 7, 16, 7);
        g2.drawLine(7, 12, 16, 12);

        g2.dispose();

        return optionImage;
    }

    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, 24);
    }

    @Override
    public void setSize(java.awt.Dimension dim) {
        this.setSize(dim.width, 24);
    }

    public java.awt.Rectangle getResizerHotSpot() {
        return this.resizerHotSpot;
    }

    public java.awt.Rectangle getOptionHotSpot() {
        return this.optionHotSpot;
    }

    @Override
    public String toString() {
        return "SDFooter";
    }
}