package de.jugms.sd.components.panels;

/**
 * 
 * @author HanSolo
 */
public class SDLinearGradientPanel extends javax.swing.JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final java.awt.Color FRAME_COLOR = new java.awt.Color(0.0f, 0.0f, 0.0f, 0.8f);
    private java.awt.image.BufferedImage cache = null;
    private java.awt.Color color1 = new java.awt.Color(0, 0, 0, 255);
    private java.awt.Color color2 = new java.awt.Color(32, 53, 72, 255);
    private java.awt.Color color3 = new java.awt.Color(0, 0, 0, 255);
    private java.awt.Color color4 = new java.awt.Color(32, 53, 72, 255);
    private float positionColor1 = 0.0f;
    private float positionColor2 = 0.1f;
    private float positionColor3 = 0.4f;
    private float positionColor4 = 1.0f;

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        // Paint fast background gradient
        if (cache == null || cache.getHeight() != getHeight()) {
            cache = createBackgroundImage();
        }

        g2.drawImage(cache, 0, 0, getWidth(), getHeight(), null);

        g2.setColor(FRAME_COLOR);
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        g2.dispose();
    }

    public void setColor1(java.awt.Color color1) {
        this.color1 = color1;
        cache = null;
        repaint();
    }

    public java.awt.Color getColor1() {
        return this.color1;
    }

    public void setColor2(java.awt.Color color2) {
        this.color2 = color2;
        cache = null;
        repaint();
    }

    public java.awt.Color getColor2() {
        return this.color2;
    }

    public void setColor3(java.awt.Color color3) {
        this.color3 = color3;
        cache = null;
        repaint();
    }

    public java.awt.Color getColor3() {
        return this.color3;
    }

    public void setColor4(java.awt.Color color4) {
        this.color4 = color4;
        cache = null;
        repaint();
    }

    public java.awt.Color getColor4() {
        return this.color4;
    }

    public void setPositionColor1(float positionColor1) {
        if (positionColor1 < 0.0f || positionColor1 > 1.0f) {
            positionColor1 = 0.0f;
        }

        if (positionColor1 > this.positionColor2) {
            positionColor1 = this.positionColor2;
        }

        this.positionColor1 = positionColor1;
        cache = null;
        repaint();
    }

    public float getPositionColor1() {
        return this.positionColor1;
    }

    public void setPositionColor2(float positionColor2) {
        if (positionColor2 < 0.0f || positionColor2 > 1.0f) {
            positionColor2 = 0.1f;
        }

        if (positionColor2 < this.positionColor1) {
            positionColor2 = this.positionColor1;
        }

        if (positionColor2 > this.positionColor3) {
            positionColor2 = this.positionColor3;
        }

        this.positionColor2 = positionColor2;
        cache = null;
        repaint();
    }

    public float getPositionColor2() {
        return this.positionColor2;
    }

    public void setPositionColor3(float positionColor3) {
        if (positionColor3 < 0.0f || positionColor3 > 1.0f) {
            positionColor3 = 0.4f;
        }

        if (positionColor3 < this.positionColor2) {
            positionColor3 = this.positionColor2;
        }

        if (positionColor3 > this.positionColor4) {
            positionColor3 = this.positionColor4;
        }

        this.positionColor3 = positionColor3;
        cache = null;
        repaint();
    }

    public float getPositionColor3() {
        return this.positionColor3;
    }

    public void setPositionColor4(float positionColor4) {
        if (positionColor4 < 0.0f || positionColor4 > 1.0f) {
            positionColor4 = 1.0f;
        }

        if (positionColor4 < this.positionColor3) {
            positionColor4 = this.positionColor3;
        }

        this.positionColor4 = positionColor4;
        cache = null;
        repaint();
    }

    public float getPositionColor4() {
        return this.positionColor4;
    }

    private java.awt.image.BufferedImage createBackgroundImage() {
        cache = new java.awt.image.BufferedImage(2, getHeight(),
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = cache.createGraphics();

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float[] FRACTIONS = { positionColor1, positionColor2, positionColor3, positionColor4 };

        final java.awt.Color[] COLORS = { color1, color2, color3, color4 };

        java.awt.LinearGradientPaint bgndGradient = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        g2d.setPaint(bgndGradient);
        g2d.fillRect(0, 0, 2, getHeight());

        g2d.dispose();

        return cache;
    }

    @Override
    public String toString() {
        return "SDLinearGradientPanel";
    }
}