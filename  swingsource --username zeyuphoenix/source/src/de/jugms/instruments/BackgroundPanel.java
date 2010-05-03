/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.jugms.instruments;

/**
 *
 * @author hansolo
 */
public class BackgroundPanel extends javax.swing.JPanel
{
    private java.awt.image.BufferedImage backgroundImage;

    public BackgroundPanel()
    {
        this.setPreferredSize(new java.awt.Dimension(148, 64));
        this.setSize(new java.awt.Dimension(148, 64));
        setOpaque(false);
        init();
    }

    private void init()
    {
        this.backgroundImage = null;
    }

    @Override
    public void setSize(int width, int height)
    {
        super.setSize(width, height);
        this.backgroundImage = null;
        repaint();
    }


    @Override
    public void setSize(java.awt.Dimension dim)
    {
        super.setSize(dim);
        this.backgroundImage = null;
        repaint();
    }


    @Override
    protected void paintComponent(java.awt.Graphics g)
    {
        super.paintComponent(g);
        
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();

        if (this.backgroundImage == null)
        {
            this.backgroundImage = createBackgroundImage();
        }

        g2.drawImage(backgroundImage, 0, 0, null);

        g2.dispose();
    }

    private java.awt.image.BufferedImage createBackgroundImage()
    {
        java.awt.GraphicsConfiguration gfxConf = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        final java.awt.image.BufferedImage IMAGE = gfxConf.createCompatibleImage(getWidth(), getHeight(), java.awt.Transparency.TRANSLUCENT);

        java.awt.Graphics2D g2 = IMAGE.createGraphics();

        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION, java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//        g2.setRenderingHint(java.awt.RenderingHints.KEY_COLOR_RENDERING, java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//        g2.setRenderingHint(java.awt.RenderingHints.KEY_STROKE_CONTROL, java.awt.RenderingHints.VALUE_STROKE_PURE);


        java.awt.geom.Point2D BACKGROUND_START = new java.awt.geom.Point2D.Double(0, 0);
        java.awt.geom.Point2D BACKGROUND_STOP = new java.awt.geom.Point2D.Double(0, getHeight());

        final float[] BACKGROUND_FRACTIONS =
        {
            0.0f,
            0.99f,
            1.0f
        };

        final java.awt.Color[] BACKGROUND_COLORS =
        {
            new java.awt.Color(0xE5DED3),
            new java.awt.Color(0x87898C),
            new java.awt.Color(0x100E0E)
        };

        final java.awt.Shape BACKGROUND = new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10);

        final java.awt.LinearGradientPaint BACKGROUND_GRADIENT = new java.awt.LinearGradientPaint(BACKGROUND_START, BACKGROUND_STOP, BACKGROUND_FRACTIONS, BACKGROUND_COLORS );

        g2.setPaint(BACKGROUND_GRADIENT);
        g2.fill(BACKGROUND);

        g2.dispose();

        return IMAGE;
    }
}
