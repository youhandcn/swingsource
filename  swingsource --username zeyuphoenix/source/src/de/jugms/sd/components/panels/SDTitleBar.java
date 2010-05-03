package de.jugms.sd.components.panels;

/**
 * 
 * @author HanSolo
 */
public class SDTitleBar extends javax.swing.JPanel implements java.awt.event.ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public String title;
    java.awt.LinearGradientPaint gradient;
    java.awt.image.BufferedImage cache = null;
    public javax.swing.JButton closeButton;

    public SDTitleBar() {
        this.title = "title";
        setPreferredSize(new java.awt.Dimension(100, 24));
        closeButton = new de.jugms.sd.components.buttons.SDTitleBarButton();
        setLayout(null);
        add(closeButton);
        closeButton.addActionListener(this);
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

        // Draw title
        g2.setFont(new java.awt.Font("Verdana", 1, 12));
        g2.setColor(java.awt.Color.WHITE);
        g2.drawString(title, 5, 17);

        java.awt.Dimension size = closeButton.getPreferredSize();
        closeButton.setBounds(getWidth() - size.width - 4, 4, size.width, size.height);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createBackgroundImage() {
        cache = new java.awt.image.BufferedImage(2, getHeight(),
                java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics2D g2d = cache.createGraphics();

        java.awt.geom.Point2D start = new java.awt.geom.Point2D.Float(0, 0);
        java.awt.geom.Point2D stop = new java.awt.geom.Point2D.Float(0, getHeight());

        final float[] FRACTIONS = { 0.0f, 0.1f, 0.4f, 1.0f };

        final java.awt.Color[] COLORS = { new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255), new java.awt.Color(0, 0, 0, 255),
                new java.awt.Color(32, 53, 72, 255) };

        java.awt.LinearGradientPaint paint = new java.awt.LinearGradientPaint(start, stop,
                FRACTIONS, COLORS);

        g2d.setPaint(paint);
        g2d.fillRect(0, 0, 2, getHeight());

        g2d.dispose();

        return cache;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    // ActionListener methods
    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        if (event.getSource() == closeButton) {
            javax.swing.JComponent comp = this;
            while (comp.getParent() != null) {
                if (comp.getParent() instanceof java.awt.Window) {
                    break;
                }
                comp = (javax.swing.JComponent) comp.getParent();
            }

            // Fire a window closing event on the parent window
            if (comp.getParent() instanceof java.awt.Window) {
                java.awt.event.WindowEvent evt = new java.awt.event.WindowEvent(
                        (javax.swing.JFrame) comp.getParent(),
                        java.awt.event.WindowEvent.WINDOW_CLOSING);
                ((java.awt.Window) comp.getParent()).dispatchEvent(evt);
            }
        }
    }

    @Override
    public String toString() {
        return "SDTitleBar";
    }
}